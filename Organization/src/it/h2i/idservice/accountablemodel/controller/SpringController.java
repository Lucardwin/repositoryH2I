package it.h2i.idservice.accountablemodel.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import it.h2i.idservice.accountablemodel.connection.Entity;
import it.h2i.idservice.accountablemodel.connection.Utility;
import it.h2i.idservice.accountablemodel.model.Appertain;
import it.h2i.idservice.accountablemodel.model.Organization;
import it.h2i.idservice.accountablemodel.model.Token;
import it.h2i.idservice.accountablemodel.model.User;
import it.h2i.idservice.accountablemodel.security.OnRegistrationCompleteEvent;


@Controller
public class SpringController {
	final static Logger logger = Logger.getLogger(SpringController.class); 
	public static String mailTemp=null;
	boolean pageMailSendFlag=true;
	String vistaUserCorrente="";
	String currentOrganization;
	List<User> currentUsers;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	User user;

	private MessageSource messages;



	@RequestMapping("/")
	public String handleRequest(HttpServletRequest request,HttpServletResponse response, Model model) {
		return "login";
	}
	@RequestMapping("/testList")
	public ModelAndView testList() {

		Entity e = new Entity();
		ModelAndView map = new ModelAndView("testList");
		currentUsers=null;
		currentUsers= new LinkedList<User>();
		currentUsers.addAll(e.getAllUser());
		String field ="";	
		int i=0;
		for(User u :currentUsers) {
			i++;
			field+=	"      <tr>" + 
					"        <td class='id' style='display:none;'>"+i+"</td>" + 
					"        <td class='name'style='color:white;'>"+u.getName()+"</td>" + 
					"        <td class='surname'style='color:white;'>"+u.getSurname()+"</td>" + 
					"        <td class='mail'style='color:white;'>"+u.getMail()+"</td>\r\n" + 
					"        <td class='edit'><button class=\"edit-item-btn\">Edit</button></td>\r\n" + 
					"        <td class='remove'><button class=\"remove-item-btn\">Remove</button></td>\r\n" + 
					"        <td style=color: white;>"+((u.getEnable() ? "<a href='enable?mail="+u.getMail()+
							"' style='color: red;'>Disable</a>" : "<a href='enable?mail="+u.getMail()+"' style='color: green;'>Enable</a>"))+
					"      </tr>";

		}


		map.addObject("users", field);
		vistaUserCorrente="testList";
		return map;
	}

	@RequestMapping("/organizationManager")
	public ModelAndView organizations() {

		Entity e = new Entity();

		List<Organization> o = e.getAllOrganizations();

		ModelAndView map = new ModelAndView("organizationManager");
		map.addObject("organizations", o);
		vistaUserCorrente="allUserOrganization";
		return map;
	}
	@RequestMapping("/ViewUsers")
	public ModelAndView ViewUsers(@RequestParam("piva") String piva,HttpServletResponse response) {
		vistaUserCorrente="allUserOrganization";
		Entity e = new Entity();
		Organization o= e.getOrganization(piva);
		currentOrganization=o.getPiva();		
		currentUsers=null;


		Set appertains = e.getOrganization(currentOrganization).getAppertains();
		currentUsers= new LinkedList<User>();
		for( Object a: appertains) {
			currentUsers.add(((Appertain) a).getUser());			
		}				
		try {
			response.sendRedirect(vistaUserCorrente);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ModelAndView map = new ModelAndView(vistaUserCorrente);
		map.addObject("users", currentUsers);
		map.addObject("organization", o.getName());


		return map;
	}



	@RequestMapping("/enable")
	public ModelAndView enable(@RequestParam("mail") String mail,HttpServletResponse response) {

		Entity e = new Entity();
		User user = e.getUserByMail(mail);

		if (user.getEnable()) {
			user.setEnable(false);
		} else {
			user.setEnable(true);
		}

		e.merge(user);
		currentUsers=null;
		currentUsers= new LinkedList<User>();
		if(vistaUserCorrente.equals("allUser")) {
		}else if(vistaUserCorrente.equals("allUserOrganization")) {
			Set appertains = e.getOrganization(currentOrganization).getAppertains();
			for( Object a: appertains) {
				currentUsers.add(((Appertain) a).getUser());			
			}

		}
		try {
			response.sendRedirect(vistaUserCorrente);
		} catch (IOException e1) {
			e1.printStackTrace();
		}				
		ModelAndView map = new ModelAndView(vistaUserCorrente);
		map.addObject("users", currentUsers);

		return map;
	}




	@RequestMapping("/allUser")
	public ModelAndView handleRequest() {
		Entity e = new Entity();
		ModelAndView map = new ModelAndView("allUser");
		currentUsers=null;
		currentUsers= new LinkedList<User>();
		currentUsers.addAll(e.getAllUser());
		map.addObject("users", currentUsers);
		vistaUserCorrente="allUser";
		return map;

	}
	@RequestMapping("/allUserOrganization")
	public ModelAndView allUserOrganization() {

		ModelAndView map = new ModelAndView("allUserOrganization");
		map.addObject("users", currentUsers);
		vistaUserCorrente="allUserOrganization";
		return map;

	}

	@RequestMapping(value = "/loginEffettuata", method = RequestMethod.GET)
	public ModelAndView adminPage() {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return new ModelAndView("loginEffettuata","a",auth.getName());
	}



	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}



	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()) {
			ModelAndView model = new ModelAndView("loginEffettuata");;

		}

		ModelAndView model = new ModelAndView("login");;
		return model;
	}
	@RequestMapping(value = "/loginError", method = RequestMethod.GET)
	public ModelAndView loginError() {

		ModelAndView model = new ModelAndView("login","errors","Parametri errati");;
		return model;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		return new ModelAndView("register");
	}

	@RequestMapping(value = "/mailResend", method = RequestMethod.GET)
	public ModelAndView MailResend(HttpServletResponse response, 
			HttpServletRequest request) {
		if(mailTemp!=null) {
			Entity e=new Entity();
			User user =e.getUserByMail(mailTemp);
			if(user!=null) {
				try {
					StringBuffer appUrl = request.getRequestURL();
					int i=0;
					if (request.getQueryString() != null) {
						appUrl.append("?").append(request.getQueryString());
					}
					String completeURL = appUrl.toString();
					StringTokenizer st= new StringTokenizer(completeURL,"/", true);		
					String appUrls = "";
					while (st.hasMoreElements() && i<7) {
						appUrls+= st.nextToken();
						i++;
					}

					Token t=user.getToken();
					String token=t.getToken();
					String recipientAddress = user.getMail();
					String subject = "Registration Confirmation";
					String confirmationUrl = ""+appUrls + "regitrationConfirm?token=" + token;
					String message=" Clicca sul link per confermare la registrazione:  ";
					String text=message + "/n "  + confirmationUrl;
					new Utility().SendJavaMail("lucah2ialfino@gmail.com", "springmvc", recipientAddress, subject, text);
				} catch (Exception me) {
					logger.error("eventooooooooooooooooooooooooooooo",me);
					return new ModelAndView("login", "mail", "Errore invio mail");
				}
				mailTemp=null;
				return new ModelAndView("login", "mail", "mail inviata nuovamente");

			}
		}

		return new ModelAndView("login");
	}



	@RequestMapping(value = "/pageMailSend", method = RequestMethod.GET)
	public ModelAndView pageMailSend() {
		if(pageMailSendFlag) {

		}

		return new ModelAndView("pageMailSend","user","Ops! Pare che tu non abbia verificato il tuo account!");
	}
	@RequestMapping(value = "/samlListener", method = RequestMethod.POST)
	public ModelAndView errorPage(HttpServletResponse response, 
			HttpServletRequest request) throws IOException {

		System.out.println("1"+request.getHeader("Content-type"));
		System.out.println("2"+request.getContentType());

		Enumeration parameterNames = request.getParameterNames();
		String encoded=request.getParameter("SAMLResponse");

		String decode=new Utility().Base64(encoded);
		String userid=new Utility().getUserIDsaml(decode, "emailAddress");
		String status=new Utility().getStatusSaml(decode);

		Entity e=new Entity();

		User u=e.getUserByMail(userid); // se user id non è nel db.
		if(u==null) {


			String name = new Utility().getNameSaml(decode);
			String surname = new Utility().getSurnameSaml(decode);
			String password = new Utility().getPasswordSaml(decode);


			u = new User(name,surname,userid,password);
			e.Insert(u);


			return new ModelAndView("samlListener","status", "<br><br><br><br><br><h2 style=\"color: white;\">Ok!</h2><h2 style=\"color: white;\">Utente registrato!</h2>");
		}
		Authentication auth = new UsernamePasswordAuthenticationToken(u.getMail(), null,null);
		SecurityContextHolder.getContext().setAuthentication(auth);



		mailTemp=u.getMail();

		if(u.getEnable()) {
			response.sendRedirect("loginEffettuata");
			ModelAndView mav=new ModelAndView("loginEffettuata","a",auth.getName());
			return mav;
		}
		else {
			response.sendRedirect("pageMailSend");
			return new ModelAndView("pageMailSend", "user", "Abbiamo inviato una mail all'indirizzo: " + mailTemp);
		}
	}


	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView controlla(@RequestParam String name, 
			@RequestParam String surname,
			@RequestParam String password,
			@RequestParam String mail,
			HttpServletResponse response, 
			HttpServletRequest request) throws IOException, NoSuchAlgorithmException{

		String mess="Sei stato registrato. Login: "+name;
		if(name==null ||surname==null || password==null || mail==null) {
			return new ModelAndView("register","errors","parametri errati o incompleti");
		}else if(name.length()==0 || surname.length()==0|| password.length()==0 || mail.length()==0)
			return new ModelAndView("register","errors","parametri errati o incompleti");
		Entity entity= new Entity();
		if(!entity.ValidateRegistration(mail)) {
			return new ModelAndView("register","errors","Utente gia presente");

		}
		Utility ut= new Utility();
		if(!ut.isValidPassword(password)) {
			return new ModelAndView("register","errors","La Password deve essere di almeno 6 caratteri");
		}
		if(!ut.isValidEmailAddress(mail)) {
			return new ModelAndView("register","errors","Non hai inserito un indirizzo mail valido");
		}
		password=new BCryptPasswordEncoder().encode(password);
		User registered=new User(name,surname,mail, password);
		entity.Insert(registered);

		try {
			StringBuffer appUrl = request.getRequestURL();
			int i=0;
			if (request.getQueryString() != null) {
				appUrl.append("?").append(request.getQueryString());
			}
			String completeURL = appUrl.toString();
			StringTokenizer st= new StringTokenizer(completeURL,"/", true);		
			String appUrls = "";
			while (st.hasMoreElements() && i<7) {
				appUrls+= st.nextToken();
				i++;
			}

			eventPublisher.publishEvent(new OnRegistrationCompleteEvent
					(registered, request.getLocale(), appUrls));
		} catch (Exception me) {
			logger.error("eventooooooooooooooooooooooooooooo",me);
			return new ModelAndView("register", "errors", "si e verificato un errore sull'invio della mail");
		}		
		entity.merge(registered);
		mailTemp=mail;
		return new ModelAndView("pageMailSend", "user", "Abbiamo inviato una mail all'indirizzo: " + mail);

	}
	@RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
	public ModelAndView confirmRegistration
	(WebRequest request, Model model, @RequestParam("token") String token) {


		Locale locale = request.getLocale();
		Entity en=new Entity();


		Token tok = en.getToken(token);

		if (tok == null) {
			String messageValue = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", messageValue);
			return new ModelAndView("errorPage", "errors", "errore imprevisto non esiste utente: ");
		}

		User user = tok.getUser();
		Calendar cal = Calendar.getInstance();
		if ((tok.getDate().getTime() - cal.getTime().getTime()) <= 0) {
			String messageValue = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("errors", messageValue);
			return new ModelAndView("errorPage", "errors", "errore imprevisto il tempo e scaduto");
		} 

		user.setEnable(true);
		user.resetToken();
		en.DeleteToken(tok);
		en.merge(user);
		return new ModelAndView("login", "user", "ACCOUNT ATTIVATO. PUOI FARE LA LOGIN");
	}





}