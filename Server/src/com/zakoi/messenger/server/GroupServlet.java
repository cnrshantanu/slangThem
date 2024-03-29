package com.zakoi.messenger.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zakoi.messenger.model.EMFService;
import com.zakoi.messenger.model.Group;

@SuppressWarnings("serial")
public class GroupServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(RegisterServlet.class.getCanonicalName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Create a new group.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EntityManager em = EMFService.get().createEntityManager();
		Group group;
		String chatId;
		
		try {
			//generate chat ID
			do {
				chatId = Util.generateUID(2);
				group = Group.find(chatId, em);
			} while(group != null);			
			
			//create a group
			group = new Group(chatId);
			
			em.persist(group);
			//logger.log(Level.WARNING, "Created group: " + chatId);
		} finally {
			em.close();
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println(chatId);
	}	
	
}
