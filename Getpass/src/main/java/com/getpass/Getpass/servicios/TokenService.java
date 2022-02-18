package com.getpass.Getpass.servicios;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.getpass.Getpass.entidades.Token;
import com.getpass.Getpass.entidades.Usuario;
import com.getpass.Getpass.excepciones.ErrorService;
import com.getpass.Getpass.repositorio.TokenRepository;

@Service
public class TokenService {
	
	@Autowired
	TokenRepository tokenRepository;
	@Autowired
	UserService userService;
	
	
	@Transactional
	public Token buildToken(String email, int option) throws ErrorService {
		
		Token token = new Token();
		Date creation = new Date();
		Usuario user;
		
		try {
			
			user = userService.userByEmail(email);
			token.setUser(user);
			
		} catch (ErrorService e) {
			throw new ErrorService("No se encontro un usuario con ese email.");
		}
		
		if (option == 1) {
			token.setExpire(new Date(creation.getTime() + (1000 * 60 * 60 * 48)));
		}
		
		token.setCreation(creation);
		token.setCompleted(false);
		
		return tokenRepository.save(token);
	}
	
	
	@Transactional
	public Token findToken(String id) throws ErrorService {
		Optional<Token> result = tokenRepository.findById(id);
		
		if (result.isEmpty()) {
			throw new ErrorService("No se encontro el token.");
		} else {
			return result.get();
		}
	}
	
	
	@Transactional
	public void authorizeUser(String id) throws ErrorService {
		
		Token token = findToken(id);
		
		checkToken(token);
		
		userService.grantWithdrawMembership(token.getUser().getEmail());
		token.setCompleted(true);
		tokenRepository.save(token);
		
	}
	
	@Transactional
	public void checkToken(Token token) throws ErrorService {
		
		if (token.getCompleted() == true) {
			throw new ErrorService("Este link ya no es valido.");
		}
		
		if (token.getExpire() != null && token.getExpire().before(new Date())){
			token.setCompleted(true);
			tokenRepository.save(token);
			throw new ErrorService("El tiempo de espera ha caducado, vuelva a solicitar la peticion");
		}
	}
	
	
	@Transactional
	public void recoveryPassword(String tokenID, String password, String confirmPassword) throws ErrorService {
		
		Token token = findToken(tokenID);
		checkToken(token);
		
		userService.passwordCheck(token.getUser(), null, password, confirmPassword);
		userService.passwordChange(token.getUser(), password);
		
		token.setCompleted(true);
		tokenRepository.save(token);
	}

}
