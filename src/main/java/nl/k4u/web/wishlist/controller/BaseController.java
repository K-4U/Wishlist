package nl.k4u.web.wishlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import nl.k4u.web.wishlist.beans.MessagesBean;

/**
 * @author Koen Beckers (K-4U)
 */
public abstract class BaseController {

	@Autowired
	public MessagesBean messagesBean;

	@ModelAttribute("messages")
	public List<MessagesBean.Message> getMessages() {
		return messagesBean.getMessages();
	}

}
