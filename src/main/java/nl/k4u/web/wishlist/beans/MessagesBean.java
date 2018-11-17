package nl.k4u.web.wishlist.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Koen Beckers (K-4U)
 */
@Component("messagesBean")
public class MessagesBean {

	private static final Map<String, List<Message>> messages = new HashMap<>();

	private HttpSession getSession() {

		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		HttpServletRequest request = attributes.getRequest();
		return request.getSession(true);
	}

	public void addInfoMessage(String message) {

		addMessage(new Message("info", message));
	}

	public void addErrorMessage(String message) {
		addMessage(new Message("danger", message));
	}

	public void addSuccesssMessage(String message) {
		addMessage(new Message("success", message));
	}


	private void addMessage(Message message) {
		getMessages().add(message);
	}

	public void clearMessages() {
		getMessages().clear();
	}

	@SuppressWarnings("unchecked")
	public List<Message> getMessages() {
		if (null == getSession().getAttribute("messages")) {
			getSession().setAttribute("messages", new ArrayList<Message>());
		}
		return ((ArrayList<Message>) getSession().getAttribute("messages"));
	}

	public static class Message {
		private String type;
		private String message;

		public Message(String type, String message) {
			this.type = type;
			this.message = message;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
