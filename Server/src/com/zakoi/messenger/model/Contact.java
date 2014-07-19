package com.zakoi.messenger.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;

@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String chatId;
	private String regId;
	private String name;
	
	public Contact() {}
	
	public Contact(String chatId, String regId) {
		this.chatId = chatId;
		this.regId = regId;
	}
	
	public Contact(String chatId, String regId, String name) {
		this.chatId = this.chatId;
		this.regId = regId;
		this.name = name;
	}
	public static Contact find(String chatId, EntityManager em) {
		Query q = em.createQuery("select c from Contact c where c.chatId = :chatId");
		q.setParameter("chatId", chatId);
		List<Contact> result = q.getResultList();
		
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	public Long getId() {
		return id;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getName() {
		return regId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
