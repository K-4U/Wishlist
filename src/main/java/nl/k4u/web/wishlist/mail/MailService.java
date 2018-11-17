package nl.k4u.web.wishlist.mail;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import nl.k4u.jpa.wishlist.pojo.PasswordToken;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
public class MailService extends AbstractMailService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private Environment environment;

	public void sendToken(PasswordToken token) {

		logger.info("Sending password reset token to " + token.getUser().getEmail());

		String url = environment.getProperty("application.url");
		url = String.format("%s/public/reset-pass/%s", url, token.getToken());

		MailAddressee to = factorAddressee(token.getUser());
		StringBuilder sb = new StringBuilder("Beste ").append(token.getUser().getName()).append(",<br /><br />\r\n")
				.append("Je krijgt deze email omdat je een wachtwoord reset hebt aangevraagd voor de wishlist.<br />\r\n")
				.append("Klik op de volgende link om je wachtwoord te resetten:<br />\r\n<br />\r\n")
				.append("<a href=\"").append(url).append("\">").append(url).append("</a>");


		try {
			sendMail(to, factorMessage("Wachtwoord reset", sb.toString(), sb.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
