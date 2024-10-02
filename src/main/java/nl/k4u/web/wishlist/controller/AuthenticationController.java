package nl.k4u.web.wishlist.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.k4u.jpa.wishlist.service.LoginService;
import nl.k4u.web.wishlist.api.pojo.JwtResponse;
import nl.k4u.web.wishlist.api.pojo.LoginRequest;
import nl.k4u.web.wishlist.mail.MailService;
import nl.k4u.web.wishlist.security.AuthSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Koen Beckers (K-4U)
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController extends BaseController {

	private final LoginService loginService;
	private final MailService mailService;
	private final AuthSupport authSupport;

	@CrossOrigin
	@PostMapping("login")
	public ResponseEntity<JwtResponse> doLogin(@Valid @RequestBody LoginRequest request) {
		JwtResponse jwtResponse = authSupport.authenticate(request);

		return ResponseEntity.ok(jwtResponse);
	}

	@RequestMapping(value = "reset-pass", method = RequestMethod.GET)
	public String resetPassPage() {
		//Ask for their email address

		return "reset-pass-page";
	}
/*
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
	}*/
}
