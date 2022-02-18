package com.getpass.Getpass.servicios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.getpass.Getpass.entidades.Token;
import com.getpass.Getpass.entidades.Usuario;
import com.getpass.Getpass.enums.Role;
import com.getpass.Getpass.excepciones.ErrorService;
import com.getpass.Getpass.repositorio.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationService emailService;
	@Autowired
	TokenService tokenService;
	
	@Transactional
	public void createUser(String nick, String name, String lastName, String email, String password, String confirmPassword, String phone, 
			Integer dni, String date) throws ErrorService {
		
		if (userExists(email)) {
			
			check(nick, name, lastName, email, password, confirmPassword, phone, dni, date);
			
			Usuario user = new Usuario();
			
			user.setNick(nick);
			user.setName(name);
			user.setLastName(lastName);
			user.setEmail(email);
			
			String encript = new BCryptPasswordEncoder().encode(password);
			
			user.setPassword(encript);
			user.setPhone(phone);
			user.setDni(dni);
			
			/* SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
			String datee = sdf.format(date); */
			
			user.setDate(date);
			user.setRole(Role.USER);
			
			userRepository.save(user);
			
			Token token = tokenService.buildToken(email, 0);
			
			emailService.sendMail("Getpass: confirma tu cuenta.", user.getEmail(),  
					"¡Felicidades! Se registro con éxito en nuestra pagina, ingrese al siguiente link para confirmar su registro: "
					+ "http://localhost:8080/confirmar-cuenta/" + token.getId());
			
		} else {
			throw new ErrorService("Ya existe ese usuario");
		}
		
	}
	
	
	@Transactional
	public void delate(String id) throws ErrorService {
		
		Usuario user = userId(id);
		userRepository.delete(user);
	}
	
	@Transactional(readOnly = true)
	public Usuario userId(String id) throws ErrorService {
		Optional<Usuario> result = userRepository.findById(id);
		
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new ErrorService("No se pudo encontrar el usuario.");
		}
	}
	
	@Transactional
	public void changeCondition(String id) throws ErrorService {
		Usuario user = userId(id);
		
		user.setEnabled(!user.getEnabled());
	}
	
	public void editUser(String id, String nick, String name, String lastName, String email, String password, String confirmPassword, String phone, 
			Integer dni, String date) throws ErrorService {
		
		check(nick, name, lastName, email, password, confirmPassword, phone, dni, date);
		
		Usuario user = userId(id);
		
		user.setNick(nick);
		user.setName(lastName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setPhone(phone);
		
		/* SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
		String datee = sdf.format(date); */
		
		user.setDate(date);
		
		userRepository.save(user);
		
	}
	
	public Boolean userExists(String email) {
		
		Usuario user = userRepository.UserByEmail(email);
		
		return user == null;			
	}
	
	public void check(String nick, String name, String lastName, String email, String password, String confirmPassword, String phone, 
			Integer dni, String date) throws ErrorService {
		
		if (nick == null || nick.isEmpty()) {
			throw new ErrorService("Error en el nick: esta vacio.");
		}

		if (name == null || name.isEmpty()) {
			throw new ErrorService("Error en el nombre: esta vacio.");
		}
		
		if (lastName == null || lastName.isEmpty()) {
			throw new ErrorService("Error en el apellido: esta vacio.");
		}
		
		if (email == null || email.isEmpty()) {
			throw new ErrorService("Error en el email: esta vacio.");
		}

		if (password.isEmpty() || password.length() < 6) {
			throw new ErrorService("Error la clave: esta vacia");
		}

		if (password.compareTo(confirmPassword) != 0) {
			throw new ErrorService("Error la clave: no coincide con la confirmación");
		}
		
		if (phone == null || phone.isEmpty()) {
			throw new ErrorService("Error en el telefono: esta vacio.");
		}
		
		if (dni == null || (dni - 999999) <= 0) {
			throw new ErrorService("Error en el DNI: esta vacio o es inexistente.");
		}

		if (date== null) {
			throw new ErrorService("Error en el fecha: esta vacio o es posterior a la fecha actual.");
		}
	}
	
	@Transactional
	public void passwordChange(Usuario user, String password) {
		
		String encript = new BCryptPasswordEncoder().encode(password);
		
		user.setPassword(encript);
		userRepository.save(user);	
	}
	
	@Transactional
	public void passwordCheck(Usuario user, String lastPassword, String password, 
			String confrmPassword) throws ErrorService {
		
		if (lastPassword != null && !new BCryptPasswordEncoder().matches(lastPassword, user.getPassword())) {
			throw new ErrorService("La contraseña que va a ser cambiada no coincide con la base de datos.");
		}
		
		if (password == null || password.isEmpty() || password.length() < 6) {
			throw new ErrorService("Error la clave: esta vacia o tiene menos de 6 caracteres.");
		}
		
		if (password.compareTo(confrmPassword) != 0) {
			throw new ErrorService("Error la clave: no coincide con la confirmación.");
		}
		
	}
	
	@Transactional()
	public void grantWithdrawMembership(String email) throws ErrorService {
		
		Usuario user = userByEmail(email);
		
		if (user.getEnabled() == false) {
			user.setEnabled(true);
			userRepository.save(user);
		} else {
			user.setEnabled(false);
			userRepository.save(user);
		}
		
	}
	
	@Transactional(readOnly = true)
	public Usuario userByEmail(String email) throws ErrorService {
		
		Optional<Usuario> result = userRepository.findById(email);
		
		if (result.isPresent()) {
			Usuario user = result.get();
			return user;
		} else {
			throw new ErrorService("No se encontro usuario");
		}
	}
	
	@Transactional(readOnly = true)
	public Usuario UserByNick(String nick) throws ErrorService {
		
		Usuario usuario = userRepository.UserByNick(nick);
		
		if (usuario == null) {
			throw new ErrorService("No se encontro usuario");
		} else {
			return usuario;
		}
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario user = userRepository.UserByEmail(email);

		if (user != null && user.getEnabled() == true) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());
			permissions.add(p);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("user", user);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					permissions);
		}
		return null;
	}

	
}
