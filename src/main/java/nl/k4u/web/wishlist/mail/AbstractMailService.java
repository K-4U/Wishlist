package nl.k4u.web.wishlist.mail;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import jakarta.mail.internet.MimeMessage;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;

/**
 * @author Koen Beckers (K-4U)
 */
public abstract class AbstractMailService {
	private static final String IDENTIFIER_PREFIX = "cid:";
	private static final String IDENTIFIER = "identifier";
	// Log instance
	private static final Logger LOG = LogManager.getLogger(AbstractMailService.class);
	// Cache to prevent excessive calls being made
	private static Map<String, Resource> cachedImages = Collections.synchronizedMap(new HashMap<String, Resource>(0));
	@Autowired
	private JavaMailSender mailSender;
	@Value("${mail.from.address:server@triangulation.nl}")
	private String fromAddress;
	@Value("${mail.from.addressee:'Wishlist'}")
	private String fromAddressee;

	/**
	 * Send an email to an addressee from the default from address.
	 *
	 * @param to      The addressee list
	 * @param message The message to be sent
	 *
	 * @throws Exception
	 */
	public void sendMail(List<MailAddressee> to, MailMessage message) throws Exception {
		sendMailInternally(factorFromAddressee(), to, message);
	}

	/**
	 * Send an email to an addressee from the default from address.
	 *
	 * @param to      The addressee list
	 * @param message The message to be sent
	 *
	 * @throws Exception
	 */
	public void sendMail(List<MailAddressee> to, List<MailAddressee> bcc, MailMessage message) throws Exception {
		sendMailInternally(factorFromAddressee(), to, bcc, message);
	}

	public void sendMail(MailAddressee to, MailMessage message) throws Exception {
		List<MailAddressee> addressees = new ArrayList<>();
		addressees.add(to);
		sendMailInternally(factorFromAddressee(), addressees, message);
	}

	/**
	 * Send an email to an addresse list with a different from address
	 *
	 * @param from    The 'sender' which needs to be specified in the email
	 * @param to      The addressee list of recipients
	 * @param message The message
	 *
	 * @throws Exception
	 */
	public void sendMail(MailAddressee from, List<MailAddressee> to, MailMessage message)
			throws Exception {
		sendMailInternally(from, to, message);
	}

	/**
	 * @param from The 'sender' of the mail
	 * @param to   The list of recipients
	 * @param msg  The actual message
	 *
	 * @throws Exception
	 * @See sendMailInternally(DevoMailAddressee from, List < DevoMailAddressee > to, List < DevoMailAddressee > bcc, DevoMailMessage msg)
	 */
	private void sendMailInternally(MailAddressee from, List<MailAddressee> to, MailMessage msg)
			throws Exception {
		sendMailInternally(from, to, null, msg);
	}

	/**
	 * Actual worker method that for constructing and sending the email address.
	 * This will take of rendering the message object to plain / html email. If
	 * the html text of the message is non-null, we will attempt to 'inline' the
	 * images referenced. This should make sure it appear in all the browsers
	 * without nasty 'image
	 *
	 * @param from The 'sender' of the mail
	 * @param to   The list of recipients
	 * @param bcc  The list of BCC recipients
	 * @param msg  The actual message
	 *
	 * @throws Exception
	 */
	private void sendMailInternally(MailAddressee from, List<MailAddressee> to, List<MailAddressee> bcc, MailMessage msg)
			throws Exception {
		// Construct message
		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		List<String> outEmails = new ArrayList<>();

		// use the true flag to indicate you need a multipart message, false
		// otherwise
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
		if (from == null) {
			from = factorFromAddressee();
		}
		helper.setFrom(from.getEmail(), from.getDisplayName());

		if (StringUtils.hasText(msg.getSubject())) {
			helper.setSubject(msg.getSubject());
		}

		for (MailAddressee ma : to) {
			helper.addTo(ma.getEmail(), ma.getDisplayName());
			outEmails.add(ma.getEmail());
		}

		if (null != bcc) {
			for (MailAddressee ma : bcc) {
				helper.addBcc(ma.getEmail(), ma.getDisplayName());
			}
		}

		Map<String, String> localIdentifiers = new HashMap<String, String>(0);
		String bodyHtml = msg.getHtmlText();
		String bodyHtmlParsed = processHtmlText(bodyHtml, localIdentifiers);

		LOG.info(bodyHtmlParsed);

		if (StringUtils.hasText(bodyHtmlParsed)) {
			if (StringUtils.hasText(msg.getPlainText())) {
				helper.setText(msg.getPlainText(), bodyHtmlParsed);
			} else {
				helper.setText(bodyHtmlParsed, true);
			}
		} else {
			helper.setText(msg.getPlainText(), false);
		}
		if (StringUtils.hasText(bodyHtmlParsed)) {
			if (localIdentifiers.keySet().size() > 0) {
				for (String key : localIdentifiers.keySet()) {
					helper.addInline(key, cachedImages.get(localIdentifiers.get(key)), "image/png");
				}
			}
		}

		if (null != msg.getAttachments()) {
			if (msg.getAttachments().size() > 0) {
				for (MailAttachment attachment : msg.getAttachments()) {
					helper.addAttachment(attachment.getFileName(), attachment.getByteResource(), attachment.getMimeType());
				}
			}
		}

		((JavaMailSenderImpl) mailSender).send(message);
	}

	/**
	 * If no mail sender address is defined, fall back from the default one.
	 *
	 * @return A mailaddressee object
	 */
	private MailAddressee factorFromAddressee() {
		return factorAddressee(fromAddressee, fromAddress);
	}

	public MailAddressee factorAddressee(BeckersUser user) {
		return factorAddressee(user.getName(), user.getEmail());
	}


	/**
	 * Given a mail address and the display name, factor an addresee
	 *
	 * @param displayName The display name to be used
	 * @param email       The actual mail address
	 *
	 * @return A mailaddressee object
	 */
	public MailAddressee factorAddressee(String displayName, String email) {
		return new MailAddressee(displayName, email);
	}

	/**
	 * Construct a mail message object, to be used for sending. download
	 * prevent' boxes.
	 *
	 * @param subject   The subject
	 * @param htmlText  The html version of the mail
	 * @param plainText The plain text version of the mail
	 *
	 * @return A devo mail message
	 */
	public MailMessage factorMessage(String subject, String htmlText, String plainText) {
		MailMessage m = new MailMessage();
		m.setSubject(subject);
		m.setHtmlText(htmlText);
		m.setPlainText(plainText);
		return m;
	}

	protected String processHtmlText(String toProcess, Map<String, String> localIds) {
		// Find images in the body
		Map<String, String> tmpIds = new HashMap<String, String>(0);
		Pattern imgTagFinder = Pattern.compile("<img src=[^>]+>", Pattern.CASE_INSENSITIVE);
		Pattern imgSrcFinder = Pattern.compile("src=\"[^\"]+", Pattern.CASE_INSENSITIVE);
		Matcher tagMatcher = imgTagFinder.matcher(toProcess);

		// Fetch all the references URLS
		while (tagMatcher.find()) {
			String imageTag = toProcess.substring(tagMatcher.start(), tagMatcher.end());
			Matcher srcMatcher = imgSrcFinder.matcher(imageTag);
			while (srcMatcher.find()) {
				String imageTagSrc = imageTag.substring(srcMatcher.start() + 5, srcMatcher.end());
				addLocalIdentifier(imageTagSrc, tmpIds);
			}
		}
		for (String key : tmpIds.keySet()) {
			LOG.info("Image [" + key + "] => [" + tmpIds.get(key) + "]");
			if (getGlobalResource(key) != null) {
				// Replace with internal identifier
				toProcess = toProcess.replace(key, IDENTIFIER_PREFIX + tmpIds.get(key));
				localIds.put(tmpIds.get(key), key);
			} else {
				// Global image not found, don't attempt to replace
			}
		}
		return toProcess;
	}

	/**
	 * An
	 *
	 * <pre>
	 * &lt;img src="" ...
	 * </pre>
	 * <p>
	 * tag has been found. the SRC part is passed to this method, along with a
	 * map of already found image tags. This ensures the same image is not
	 * included multiple times
	 *
	 * @param urlName  image source, something like http://www.vivid-europe.com/...
	 * @param localIds Map of already found identifiers
	 */
	private void addLocalIdentifier(String urlName, Map<String, String> localIds) {
		String identifier = IDENTIFIER;
		if (!localIds.containsKey(urlName)) {
			identifier += localIds.size();
			localIds.put(urlName, identifier);
		} else {
			LOG.debug("Image [" + urlName + "] already registered");
		}
	}

	/**
	 * Given a url which was defined as a src: attempt to preload it
	 *
	 * @param fullUrl The url which needs to be inline included
	 *
	 * @return A 'resource' which will be used when rendering the mails.
	 */
	private Resource getGlobalResource(String fullUrl) {
		if (!cachedImages.containsKey(fullUrl)) {
			try {
				CloseableHttpClient httpClient = HttpClients.createMinimal();
				HttpGet httpGet = new HttpGet(fullUrl);
				CloseableHttpResponse proxiedResponse = null;
				try {
					proxiedResponse = httpClient.execute(httpGet);
					LOG.debug(proxiedResponse.getStatusLine());

					HttpEntity entity = proxiedResponse.getEntity();
					cachedImages.put(fullUrl, new ByteArrayResource(EntityUtils.toByteArray(entity)));
				} catch (Exception e) {
					// Error fetching the content
					LOG.debug(e);
				} finally {
					if (proxiedResponse != null) {
						try {
							proxiedResponse.close();
						} catch (IOException e) {
							// Ignore
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.debug(e);
			}
		}
		return cachedImages.get(fullUrl);
	}

	protected void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public static class MailAddressee implements Serializable {

		private static final long serialVersionUID = -3664935415913825253L;

		private String email;

		private String displayName = "";

		public MailAddressee() {
		}

		public MailAddressee(String displayName, String email) {
			this();
			this.displayName = displayName;
			this.email = email;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}

	public static class MailAttachment implements Serializable {

		private static final long serialVersionUID = -7257141609032970662L;

		private String fileName;

		private String mimeType;

		private byte[] content;

		/**
		 * Instantiates an empty mail attachment.
		 */
		public MailAttachment() {
		}

		/**
		 * Instantiates a new mail attachment with all values set.
		 *
		 * @param fileName The file name to use when the users downloads the file.
		 * @param mimeType A valid mime type that describes the content.
		 * @param content  The binary content of the attachment.
		 */
		public MailAttachment(String fileName, String mimeType, byte[] content) {
			this.fileName = fileName;
			this.mimeType = mimeType;
			this.content = content;
		}

		/**
		 * Get the file name to use when the users downloads the file.
		 *
		 * @return The file name of the attachment.
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * Set the file name to use when the users downloads the file.
		 *
		 * @param fileName The new file name of the attachment.
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * Get the mime type that describes the content.
		 *
		 * @return The mime type of the attachment.
		 */
		public String getMimeType() {
			return mimeType;
		}

		/**
		 * Set the mime type that describes the content.
		 *
		 * @param mimeType A valid mime type that describes the content.
		 */
		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}

		/**
		 * Get the binary content of the attachment.
		 *
		 * @return The content of the attachment.
		 */
		public byte[] getcontent() {
			return content;
		}

		/**
		 * Set the binary content of the attachment.
		 *
		 * @param content The new content for the attachment.
		 */
		public void setcontent(byte[] content) {
			this.content = content;
		}

		public ByteArrayResource getByteResource() throws IOException {
			return new ByteArrayResource(content);
		}

	}

	public static class MailMessage implements Serializable {

		private static final long serialVersionUID = 3604645600684524279L;

		private String plainText;

		private String htmlText;

		private String subject;

		private List<MailAttachment> attachments;

		public MailMessage() {
		}

		public MailMessage(String subject, String htmlText, String plainText) {
			this.subject = subject;
			this.htmlText = htmlText;
			this.plainText = plainText;
		}

		public String getPlainText() {
			return plainText;
		}

		public void setPlainText(String plainText) {
			this.plainText = plainText;
		}

		public String getHtmlText() {
			return htmlText;
		}

		public void setHtmlText(String htmlText) {
			this.htmlText = htmlText;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public List<MailAttachment> getAttachments() {
			return attachments;
		}

		public void setAttachments(List<MailAttachment> attachments) {
			this.attachments = attachments;
		}

		public void addAttachment(MailAttachment attachment) {
			if (null == attachments) {
				attachments = new ArrayList<>();
			}
			attachments.add(attachment);
		}
	}
}
