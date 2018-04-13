package it.h2i.idservice.accountablemodel.connection;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import it.h2i.idservice.accountablemodel.model.Appertain;
import it.h2i.idservice.accountablemodel.model.Organization;
import it.h2i.idservice.accountablemodel.model.Role;
import it.h2i.idservice.accountablemodel.model.Token;
import it.h2i.idservice.accountablemodel.model.User;



public class Entity {

	Connection c;


	public Entity(){
		c=new Connection();
	}

	public User getUserByMail(String mail) {
		if(mail==null ||mail.equals("")) {
			return null;
		}
		EntityManager em=c.getEntity();
		List l=em.createQuery("From User u Where u.mail='"+mail+"'").getResultList();
		User u;
		if(l.isEmpty()) {
			return null;
		}else {
			u=(User) l.get(0);
		}
		
		return u;

	}
	public Organization getOrganization(String piva) {
		if(piva==null ||piva.equals("")) {
			return null;
		}
		EntityManager em=c.getEntity();
		List l=em.createQuery("From Organization o Where o.piva='"+piva+"'").getResultList();
		Organization o;
		if(l.isEmpty()) {
			return null;
		}else {
			o=(Organization) l.get(0);
		}
		
		return o;

	}
	
	public List<User> getAllUser() {
	
		EntityManager em=c.getEntity();
		List l=em.createQuery("From User ").getResultList();
		
		if(l.isEmpty()) {
			return null;
		}else {
			return l;
		}

	}
	public List<User> getAllUserOfOrganization(Organization organization) {
		
		EntityManager em=c.getEntity();
		List l=em.createQuery("From Appertain a where a.appertain_idorganization='"+organization.getIdorganization()+"'").getResultList();
		
		if(l.isEmpty()) {
			return null;
		}else {
			List<User> lu= new LinkedList();
			for(Object a: l) {
				lu.add(((Appertain) a).getUser());								
			}
			return lu;
		}

	}
	
	public List<Organization> getAllOrganizations() {
		
		EntityManager em=c.getEntity();
		List l=em.createQuery("From Organization ").getResultList();
		
		if(l.isEmpty()) {
			return null;
		}else {
			return l;
		}		

	}
	
	public void setRole(Organization organization, User u, Role role) {
		
		
	}
	
	


	public Token getToken(String token) {
		if(token==null ||token.equals("")) {
			return null;
		}
		EntityManager em=c.getEntity();
		List l=em.createQuery("From Token t Where t.token='"+token+"'").getResultList();
		Token to;
		
		if(l.isEmpty()) {
			return null;
		}else {
			to=(Token) l.get(0);
		}
		
		return to;

	}
	public Boolean DeleteToken(Token token) {
		EntityManager em=c.getEntity();

		  em.getTransaction().begin();
		  em.remove(token);
		  em.getTransaction().commit();
		  return true;
		
		}
	

	public boolean ValidateRegistration(String mail) {
		EntityManager em=c.getEntity();
		Boolean ret =em.createQuery("From User u Where u.mail='"+mail+"'").getResultList().isEmpty();
		return ret;


	}


	public boolean Insert(User u) {
		EntityManager em=c.getEntity();
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		return true;	
	}
	public boolean merge(User u) {
		EntityManager em=c.getEntity();
		em.getTransaction().begin();
		em.merge(u);
		em.getTransaction().commit();
		return true;

	}	

	public boolean Insert(Token token) {
		EntityManager em=c.getEntity();
		em.getTransaction().begin();
		em.persist(token);
		em.getTransaction().commit();
		return true;	
	}




}
