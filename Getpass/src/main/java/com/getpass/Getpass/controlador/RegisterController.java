package com.getpass.Getpass.controlador;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.getpass.Getpass.excepciones.ErrorService;
import com.getpass.Getpass.servicios.UserService;

@Controller
@RequestMapping("/")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@GetMapping("register")
	public String registrationPage() {
		return "register.html";
	}
	
	@PostMapping("/registerUser")
	public String userRegister(ModelMap model, @RequestParam String email, @RequestParam String name, @RequestParam String lastName, 
			@RequestParam String password, @RequestParam String confirmPassword, @RequestParam String nick, 
			@RequestParam String date, @RequestParam String phone, @RequestParam Integer dni, RedirectAttributes redirect) {
		try {
			
		userService.createUser(nick, name, lastName, email, password, confirmPassword, phone , dni, date);		
		
		redirect.addFlashAttribute("success", "Se registro con exito, recuerder ingresar al link que enviamos a su email para poder habilitar la cuenta");
		return "redirect:/login";
		} catch (ErrorService e) {
			redirect.addFlashAttribute("error", e.getMessage());
			redirect.addFlashAttribute("email", email);
			redirect.addFlashAttribute("name", name);
			redirect.addFlashAttribute("lastName", lastName);
			redirect.addFlashAttribute("nick", nick);
			redirect.addFlashAttribute("phone", phone);
			redirect.addFlashAttribute("dni", dni);
			return "redirect:/register";
		}
	}
	
	@GetMapping("/register/condiciones")
	public String terms() {
		return "Condiciones";
	}
	
	@GetMapping("/register/politicas-datos")
	public String dataPolicy() {
		return "Politica-de-datos";
	}
	
	@GetMapping("/register/politicas-cookies")
	public String cookiesPolicy() {
		return "Politica-de-cookies";
	}
}
