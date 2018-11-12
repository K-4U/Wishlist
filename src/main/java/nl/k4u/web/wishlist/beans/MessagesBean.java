package nl.k4u.web.wishlist.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Koen Beckers (K-4U)
 */
@Component("messagesBean")
public class MessagesBean {

	private final List<Message> messages = new ArrayList<>();

	public void addInfoMessage(String message) {
		messages.add(new Message("info", message));
	}

	public void addErrorMessage(String message) {
		messages.add(new Message("error", message));
	}

	public void addSuccesssMessage(String message) {
		messages.add(new Message("success", message));
	}

	public void clearMessages() {
		messages.clear();
	}

	public List<Message> getMessages() {
		return messages;
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
