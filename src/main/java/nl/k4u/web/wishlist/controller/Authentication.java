package nl.k4u.web.wishlist.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.PasswordToken;
import nl.k4u.jpa.wishlist.service.LoginService;
import nl.k4u.web.wishlist.mail.MailService;
import nl.k4u.web.wishlist.security.PasswordSupport;

/**
 * @author Koen Beckers (K-4U)
 */
@Controller
@RequestMapping("public")
public class Authentication extends BaseController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginPage(String error, String logout) {
		if (null != error) {
			messagesBean.addErrorMessage("Verkeerde combinatie gebruikersnaam/wachtwoord");
		}
		if (null != logout) {
			messagesBean.addSuccesssMessage("Uitgelogd");
		}

		return "login";
	}

	@RequestMapping(value = "reset-pass", method = RequestMethod.GET)
	public String resetPassPage() {
		//Ask for their email address

		return "reset-pass-page";
	}

	@RequestMapping(value = "reset-pass", method = RequestMethod.POST)
	public String resetPassSubmit(@RequestParam("email") String emailAddress) throws NoSuchAlgorithmException {
		//Find the user:
		BeckersUser userByEmail = loginService.getUserByEmail(emailAddress);
		if (null != userByEmail) {
			PasswordToken token = loginService.createToken(userByEmail);

			mailService.sendToken(token);
			messagesBean.addSuccesssMessage("Email verstuurd!");
			return "redirect:/public/login";
		} else {
			messagesBean.addErrorMessage("Gebruiker niet gevonden");
			return "reset-pass-page";
		}
	}

	@RequestMapping(value = "reset-pass/{token}")
	public String resetPassPage(Model model, @PathVariable String token) {
		//Validate if the token is correct:
		PasswordToken pwToken = loginService.getTokenByToken(token);
		if (null == pwToken) {
			messagesBean.addErrorMessage("Code niet herkend!");
			return "redirect:/public/login";
		}

		model.addAttribute("token", token);
		return "reset-pass-enter-pass";
	}

	@RequestMapping(value = "reset-pass/submit", method = RequestMethod.POST)
	public String savePass(Model model, @RequestParam("password") String newPass, @RequestParam("passwordRepeat") String passRepeat, @RequestParam("token") String token) throws InvalidKeySpecException, NoSuchAlgorithmException {
		model.addAttribute("token", token);
		if (newPass.isEmpty()) {
			messagesBean.addErrorMessage("Lege wachtwoorden zijn niet toegstaan");
			return "reset-pass-enter-pass";
		}

		if (!passRepeat.equals(newPass)) {
			messagesBean.addErrorMessage("Wachtwoorden komen niet overeen");
			return "reset-pass-enter-pass";
		}

		PasswordToken pwToken = loginService.getTokenByToken(token);
		pwToken.getUser().setPassHash(PasswordSupport.generate(newPass));

		loginService.save(pwToken.getUser());

		messagesBean.addSuccesssMessage("Wachtwoord gewijzigd!");

		return "redirect:/public/login";
	}
}
