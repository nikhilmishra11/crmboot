package com.nam.crm.web.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nam.crm.web.model.ComplainMasterObject;
import com.nam.crm.web.model.Country;
import com.nam.crm.web.model.NoSaleObject;
import com.nam.crm.web.model.PNJComplainMaster;
import com.nam.crm.web.model.PNJCustomerDetailsMaster;
import com.nam.crm.web.model.PNJCustomerFeedback;
import com.nam.crm.web.model.PNJCustomerNoSaleDetails;
import com.nam.crm.web.model.PNJCustomerSaleMaster;
import com.nam.crm.web.model.PNJDailyTracker;
import com.nam.crm.web.model.SaleObject;
import com.nam.crm.web.model.SearchObject;
import com.nam.crm.web.model.User;
import com.nam.crm.web.model.UserProfile;
import com.nam.crm.web.service.ComplainMasterObjectService;
import com.nam.crm.web.service.CountryServices;
import com.nam.crm.web.service.PCFBService;
import com.nam.crm.web.service.PNJComplainMasterService;
import com.nam.crm.web.service.PNJCustomerDetailsMasterService;
import com.nam.crm.web.service.PNJCustomerFeedbackService;
import com.nam.crm.web.service.PNJCustomerNoSaleDetailsService;
import com.nam.crm.web.service.PNJCustomerSaleDetailsService;
import com.nam.crm.web.service.PNJPaymentMasterService;
import com.nam.crm.web.service.PNJTrackerService;
import com.nam.crm.web.service.UserProfileService;
import com.nam.crm.web.service.UserService;



@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	UserService userService;
	
	@Autowired
	PCFBService pcfbService;
	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	CountryServices countryServices;
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	 @Autowired
	 PNJComplainMasterService pnjComplainMasterService;
	
	 @Autowired
	 PNJPaymentMasterService pnjPaymentMasterService;
	 
	 @Autowired
	 ComplainMasterObjectService complainMasterObjectService;
	
	 @Autowired
	 PNJCustomerFeedbackService feedbackService;
	 
	 @Autowired
	 PNJCustomerNoSaleDetailsService no_details_service;
	 
	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userslist";
	}
	
	/* public static String replaceLastFour(String s) {
	        int length = s.length();
	        if (length < 4) return "Error: The provided string is not greater than four characters long.";
	        return s.substring(0, length - 4) + "****";
	    }*/
	
	
		/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		User u=new User();
		
		u.setId(PNJUtill.generatedId());
		u.setFirstName(user.getFirstName().toUpperCase());
		u.setLastName(user.getLastName().toUpperCase());
		u.setPassword(user.getPassword());
		u.setEmail(user.getEmail());
		u.setSsoId(user.getSsoId().toLowerCase());
		u.setUserProfiles(user.getUserProfiles());
		/*
		 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
		 * and applying it on field [sso] of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}
		
		userService.saveUser(u);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		//return "success";
		return "registrationsuccess";
	}


	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		System.out.println("---> "+ssoId);
		User user = userService.findBySSO(ssoId);
	    model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}
	
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String ssoId) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/


		userService.updateUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	
	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "redirect:/list";
	}
	
	@RequestMapping(value = { "/view-user-{ssoId}" }, method = RequestMethod.GET)
	public String ViewUser(@PathVariable String ssoId, ModelMap model) {
		User user = userService.findBySSO(ssoId);
		model.addAttribute("user", user);
		model.addAttribute("edit1", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}
	
	

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}
	
	/*@ModelAttribute("support")
	public Map<String,String> list_a(){
		Map< String, String > a = new HashMap<String, String>();
        a.put("ISSUED", "ISSUED");
        a.put("RESOLVED", "RESOLVED");
        a.put("RE-ISSUED", "RE-ISSUED");
       return a;
	}*/
	
	
		
	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests.
	 * If users is already logged-in and tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "newLogin";
	    } else {
	    	return "redirect:/home";
	    	//return "redirect:/testJSP";
	    }
	}
		/**
		 * For Home Page
		 * @param model
		 * @return
		 */
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homeMenu(ModelMap model) {

		/*List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());*/
		
		/* long countResult =pnjCustomerDetailsMasterService.countNoSaleResult();
		 System.out.println("No-Sale :: "+countResult);*/
		 model.addAttribute("no_sale",getNoSaleData());
		 model.addAttribute("sale",getSaleData());	
		 
		 model.addAttribute("customized_no_sale",getNoSaleData(getPrincipal()));
		 model.addAttribute("customized_sale",getSaleData(getPrincipal()));	
		 
	//	 System.out.println(">>>>>>>>>>>>>>>>> "+getAllSaleData().toString());
		 
		 model.addAttribute("all_sale_data",getAllSaleData());
		 model.addAttribute("daily_all_sale_data",getDailyAllSaleData());
		 
		 model.addAttribute("all_nosale_data",getAllNoSaleData());
		 model.addAttribute("daily_all_nosale_data",getDailyAllNoSaleData());
		 
		 
		 /*long countResult1 =pnjCustomerDetailsMasterService.countSaleResult();
		 System.out.println("Sale :: "+countResult1);*/
		 
	//	model.addAttribute("sale",countResult1);
		
		//model.addAttribute(countResult);
		
		model.addAttribute("loggedinuser", getPrincipal());
		return "home";
	}

	/**
	 * This method handles logout requests.
	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }
	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName.toUpperCase();
	}
	
	
	private long getNoSaleData() {
		 //long countResult =pnjCustomerDetailsMasterService.countNoSaleResult();
		long countResult =pnjNosaleService.countNoSaleResult();
		return countResult;
	}
	
	private long getSaleData() {
		// long countResult =pnjCustomerDetailsMasterService.countSaleResult();
		long countResult =pnjsaleservice.countSaleResult();
		return countResult;
	}
	
	private long getNoSaleData(String id) {
		// long countResult =pnjCustomerDetailsMasterService.countNoSaleResult(id);
		long countResult =pnjNosaleService.countNoSaleResult(id);
		System.out.println("Customized Sale ::"+countResult);
		return countResult;
	}
	
	private long getSaleData(String id) {
		// long countResult =pnjCustomerDetailsMasterService.countSaleResult(id);
		long countResult =pnjsaleservice.countSaleResult(id);
		 System.out.println("Customized No-Sale ::"+countResult);
		return countResult;
	}
	
	
	private List<SaleObject> getAllSaleData() {
		List<SaleObject> count= pnjsaleservice.saleCount();
		return count;
	}
	
	private List<SaleObject> getDailyAllSaleData() {
		List<SaleObject> count= pnjsaleservice.dailySaleCount();
		return count;
	}
	
	private List<NoSaleObject> getAllNoSaleData() {
		List<NoSaleObject> count= pnjsaleservice.noSaleCount();
		return count;
	}
	
	private List<NoSaleObject> getDailyAllNoSaleData() {
		List<NoSaleObject> count= pnjsaleservice.dailyNoSaleCount();
		return count;
	}
	
	
	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}
	
	/**
	 * This is my first testing spring controller code..
	 * 
	 */
	@RequestMapping(value = { "/forgot" }, method = RequestMethod.GET)
	public String forgotUser() {
		return "forgot";
	}
	
	/**
	 * This is Dashboard index testing spring controller code..
	 * 
	 */
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	/**
	 * This is PopUp form index testing spring controller code..
	 * 
	 */
	@RequestMapping(value = { "/popupform" }, method = RequestMethod.GET)
	public String popup() {
		return "popupform";
	}
	
	/**
	 * This is Feedback form index testing spring controller code..
	 * 
	 */
	@RequestMapping(value = { "/fb" }, method = RequestMethod.GET)
	public String feedback() {
		//pcfbService.savePCFB(pcfb);
		return "countryDetails";
	}
	
	
/*	@RequestMapping(value = { "/addEmp" }, method = RequestMethod.GET)
	public String newEmp(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("Employee", employee);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "employee";
	}*/

	@RequestMapping(value = { "/addEmp" }, method = RequestMethod.GET)
	public String newEmp() {
		return "employee";
	}
	
	 @RequestMapping(value = { "/emplist" }, method = RequestMethod.GET)
	    public String listEmployees() {
	 
	        return "allemployees";
	    }
	 @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	    public String newEmployee() {
	       
	        return "empregistration";
	    }
	
	 
	/**
	 * For Country ...... 
	 */
	
	 @RequestMapping(value = { "/newuser1" }, method = RequestMethod.GET)
		public String listCountries(ModelMap model) {

			List<Country> country=countryServices.findAllCountries();
			model.addAttribute("users", country);
			model.addAttribute("loggedinuser", getPrincipal());
			return "countryList";
		//	return "country"; /addCountry
		}
	
	 
	   /**
		 * This method will provide the medium to update an existing country 1.
		 */
	 
	 
	 @RequestMapping(value = { "/newcountry" }, method = RequestMethod.GET)
		public String newCountryAdd(Model model) {
	     model.addAttribute("country", new Country()); 
	     model.addAttribute("loggedinuser", getPrincipal());
		 return "countryfrom";
		}
	 
	 @RequestMapping(value = { "/newcountry" }, method = RequestMethod.POST)
		public String saveCountry11(@Validated Country country,BindingResult result,
				ModelMap model) {

			if (result.hasErrors()) {
				return "countryfrom";
			}

			
			countryServices.save(country);
			
			model.addAttribute("success", "Country " + country.getCountryName() + " registered successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			//return "success";
			return "redirect:/newuser2";
		}
	 
	 
	 @RequestMapping(value = { "/newuser2" }, method = RequestMethod.GET)
		public String newCountry1(ModelMap model) {
		    List<Country> user=countryServices.findAllCountries();
			model.addAttribute("users", user);
			model.addAttribute("edit", false);
			model.addAttribute("loggedinuser", getPrincipal());
			return "country1";
		}
	 
	
	 
	
		@RequestMapping(value = { "/edit-country1-{id}" }, method = RequestMethod.GET)
		public String editCountry1(@PathVariable String id, ModelMap model) {
			Country country=countryServices.findById(Integer.valueOf(id));
			model.addAttribute("country", country);
			model.addAttribute("edit", true);
			
			Country c=countryServices.findById(Integer.valueOf(id));
			model.addAttribute("test",c);
			
			model.addAttribute("loggedinuser", getPrincipal());
			return "countryfrom";
		}
		
		@RequestMapping(value = { "/edit-country1-{id}" }, method = RequestMethod.POST)
		public String updateCountry1(Country country, BindingResult result,
				ModelMap model, @PathVariable String id) {

			if (result.hasErrors()) {
				return "countryfrom";
			}

			
			
			
			
			countryServices.updateUser(country);
			System.out.println("::>> Country Edit <<:: "+countryServices.findById(Integer.valueOf(id)));
			
			model.addAttribute("success", "Country " + country.getCountryName()+ " updated successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			return "countryfrom";
		}	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
		
		@RequestMapping(value = { "/addCountry" }, method = RequestMethod.GET)
		public String newCountry(ModelMap model) {
			Country user = new Country();
			model.addAttribute("country", user);
			model.addAttribute("edit", false);
			model.addAttribute("loggedinuser", getPrincipal());
			return "country";
		}
		
		
		@RequestMapping(value = { "/addCountry" }, method = RequestMethod.POST)
		public String saveCountry(Country country,BindingResult result,
				ModelMap model) {

			if (result.hasErrors()) {
				return "country";
			}

			
			countryServices.save(country);
			
			model.addAttribute("success", "Country " + country.getCountryName() + " registered successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			//return "success";
			return "countrysuccess";
		}
		
		
		/**
		 * This method will provide the medium to update an existing country.
		 */
		@RequestMapping(value = { "/edit-country-{id}" }, method = RequestMethod.GET)
		public String editCountry(@PathVariable String id, ModelMap model) {
			
			Country country=countryServices.findById(Integer.valueOf(id));
			model.addAttribute("country", country);
			model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "country";
		}
		
		@RequestMapping(value = { "/edit-country-{id}" }, method = RequestMethod.POST)
		public String updateCountry(Country country, BindingResult result,
				ModelMap model, @PathVariable String id) {

			if (result.hasErrors()) {
				return "country";
			}

			countryServices.updateUser(country);
			
			model.addAttribute("success", "Country " + country.getCountryName()+ " updated successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			return "countrysuccess";
		}
		
		/**
		 * This method will delete a country.
		 */
		@RequestMapping(value = { "/delete-country-{id}" }, method = RequestMethod.GET)
		public String deleteCountry(@PathVariable String id) {
			countryServices.deleteById(Integer.valueOf(id));
			return "redirect:/newuser1";
		}
		
		
		
		/**
		 * Goto Home Page
		 */
		
	/*	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
		public String home() {
			return "home";
		}
*/
		
		
		@RequestMapping(value = { "/customer1" }, method = RequestMethod.GET)
		public String customer1(ModelMap model) {
			   /*  PNJCustomerDetailsMaster pnj=new PNJCustomerDetailsMaster();
			     model.addAttribute("PNJCustomerDetailsMaster", pnj); //PNJCustomerDetailsMaster is bean object information provide to controller...
			 //	 model.addAttribute("edit", true);
			     model.addAttribute("loggedinuser", getPrincipal());*/
				 return "advancecustomerdetailsmaster";
			
		}
		
		@RequestMapping(value = { "/customerdetails" }, method = RequestMethod.GET)
		public String customerDetails(ModelMap model) {
			 PNJCustomerDetailsMaster pnj=new PNJCustomerDetailsMaster();
		     model.addAttribute("PNJCustomerDetailsMaster", pnj); //PNJCustomerDetailsMaster is bean object information provide to controller...
		 	 model.addAttribute("edit", false);
		     model.addAttribute("loggedinuser", getPrincipal());
				 return "customerdetails";
			
		}
		
		
		
		@Autowired
		PNJCustomerDetailsMasterService pnjCustomerDetailsMasterService;
		
	/*	@Autowired
		PNJComplainMasterService pnjComplainMasterService;
		
		@Autowired
		PNJCustomerFeedbackService pnjCustomerFeedbackService;
		
		@Autowired
		PNJPaymentMasterService pnjPaymentMasterService;*/
		
		
		 @RequestMapping(value = { "/newuser3" }, method = RequestMethod.GET)
			public String newPNJ(ModelMap model) {
			 
			  String str="";
				// System.out.println(cuds.loadUserByUsername(getPrincipal()));
				 User user1 = userService.findBySSO(getPrincipal());
				 Iterator<UserProfile> itr=user1.getUserProfiles().iterator();
				 while(itr.hasNext()) {
					 str = (itr.next().getType().toString());
				 }
				 
				 System.out.println("->-"+pnjComplainMasterService.Serach_All_Complain_Details(getPrincipal()));
				 
				 if(str.equalsIgnoreCase("AGENT")){
					 List<PNJCustomerDetailsMaster> user=pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMasterUserwise(getPrincipal(),"YES");
						model.addAttribute("PNJCustomerDetailsMaster", user);
				 }
				 else{
					 List<PNJCustomerDetailsMaster> user=pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster1("YES");
					 model.addAttribute("PNJCustomerDetailsMaster", user);
				 }
			 
			    model.addAttribute("edit", true);
				model.addAttribute("loggedinuser", getPrincipal());
				return "customermasterdetails";//"pnjcustomerdetailsmaster";
			}
		 
		 @RequestMapping(value = { "/nosale" }, method = RequestMethod.GET)
			public String NoSale(ModelMap model) {
			 
			  String str="";
				// System.out.println(cuds.loadUserByUsername(getPrincipal()));
				 User user1 = userService.findBySSO(getPrincipal());
				 Iterator<UserProfile> itr=user1.getUserProfiles().iterator();
				 while(itr.hasNext()) {
					 str = (itr.next().getType().toString());
				 }
				 if(str.equalsIgnoreCase("AGENT")){
					 List<PNJCustomerDetailsMaster> user=pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMasterUserwise(getPrincipal(),"NO");
						model.addAttribute("PNJCustomerDetailsMaster", user);
				 }
				 else{
					 List<PNJCustomerDetailsMaster> user=pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster1("NO");
					 model.addAttribute("PNJCustomerDetailsMaster", user);
				 }
			 
			   model.addAttribute("edit", true);
				model.addAttribute("loggedinuser", getPrincipal());
				return "customermasterdetails";//"pnjcustomerdetailsmaster";
			}
		 
		 
		 @RequestMapping(value = { "/salecustomer" }, method = RequestMethod.GET)
			public String customersale(ModelMap model) {
				     PNJCustomerSaleMaster pnj=new PNJCustomerSaleMaster();
				     model.addAttribute("PNJCustomerSaleMaster", pnj); //PNJCustomerDetailsMaster is bean object information provide to controller...
				 	 model.addAttribute("edit", false);
				     model.addAttribute("loggedinuser", getPrincipal());
				 	 return "ztestsaledetails";
			} 
		 
		 @Autowired
		 PNJCustomerSaleDetailsService pnjsaleservice;
		 
		 @RequestMapping(value = { "/salecustomer" }, method = RequestMethod.POST)
			public String saveSaleCustomer(PNJCustomerSaleMaster cmo,BindingResult result,Model model) {
			 java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			 PNJCustomerSaleMaster pnj=new PNJCustomerSaleMaster();
			 
			 String str=null;
			 User user = userService.findBySSO(getPrincipal());
			 Iterator<UserProfile> itr=user.getUserProfiles().iterator();
			 while(itr.hasNext()) {
				 str = (itr.next().getType().toString());
			 }
			 System.out.println("Type--> "+str);
			
			
				pnj.setId(PNJUtill.generateUUId());
				pnj.setCustomer_id(PNJUtill.generateCustomerId());
				pnj.setCustomer_name(cmo.getCustomer_name().toUpperCase());
				pnj.setCustomer_contact_no(cmo.getCustomer_contact_no());
				pnj.setCustomer_contact_no_alternative(cmo.getCustomer_contact_no_alternative().toUpperCase());
				pnj.setCustomer_address(cmo.getCustomer_address().toUpperCase());
				pnj.setCustomer_email_id(cmo.getCustomer_email_id());
				pnj.setBest_time_to_reach(cmo.getBest_time_to_reach().toUpperCase());
				pnj.setCustomer_remarks(cmo.getCustomer_remarks().toUpperCase());
			//	pnj.setNo_sale(pnjCustomerDetailsMaster.getNo_sale().toUpperCase());
				pnj.setCustomer_created_on(date.toString());
				pnj.setDetails_filled_by(getPrincipal().toUpperCase());
				
				
				
				
				
				
				pnj.setSale_register_date(date.toString());
				pnj.setService_offered(cmo.getService_offered().toUpperCase());
				pnj.setSale_remarks(cmo.getSale_remarks().toUpperCase().trim());
				pnj.setAssign_technician(cmo.getAssign_technician().toUpperCase().trim());
				pnj.setPayment_amount(cmo.getPayment_amount());
				pnj.setPayment_status(cmo.getPayment_status().toUpperCase());
				pnj.setPayment_mode(cmo.getPayment_mode().toUpperCase());
				pnj.setPayment_date(cmo.getPayment_date());
				pnj.setPayment_account_number(cmo.getPayment_account_number());
				pnj.setPayment_remarks(cmo.getPayment_remarks().toUpperCase());
				pnj.setSale_type(cmo.getSale_type().toUpperCase());
				pnj.setDetails_filled_by(getPrincipal().toUpperCase());
				pnj.setSale_status("ISSUED");
				
				pnj.setUser_type(str);
			//	pnj.setAdmin_remark("");
			//	pnj.setFeedbk(cmo.getFeedbk());
				
				pnj.setName_on_card(cmo.getName_on_card().toUpperCase());
				pnj.setCard_number(cmo.getCard_number());
				pnj.setCard_exp_date(cmo.getCard_exp_date());
				pnj.setCard_cvv(cmo.getCard_cvv());
				
				pnj.setE_chq_name(cmo.getE_chq_name().toUpperCase());
				pnj.setE_chq_account(cmo.getE_chq_account());
				pnj.setE_chq_no(cmo.getE_chq_no());
				pnj.setE_chq_routing_no(cmo.getE_chq_routing_no());
				
				pnj.setP_chq_name(cmo.getP_chq_name().toUpperCase());
				pnj.setP_chq_account(cmo.getP_chq_account());
				pnj.setP_chq_no(cmo.getP_chq_no());
				pnj.setP_chq_routing_no(cmo.getP_chq_routing_no());
				
				pnj.setOnline_bill_pay(cmo.getOnline_bill_pay().toUpperCase());
				pnj.setMoney_order(cmo.getMoney_order().toUpperCase());
				pnj.setOther_payment_details(cmo.getOther_payment_details().toUpperCase());
			 
				pnjsaleservice.save(pnj);
				
				model.addAttribute("success", "Customer Id :: " + pnj.getCustomer_id()+ " Created successfully");
				model.addAttribute("loggedinuser", getPrincipal());
		
				return "successsaledetails";
			}
		 
		 
		 
		 
		 @RequestMapping(value = { "/nosalecustomer" }, method = RequestMethod.GET)
			public String customernosale(ModelMap model) {
				     PNJCustomerNoSaleDetails pnj=new PNJCustomerNoSaleDetails();
				     model.addAttribute("PNJCustomerNoSaleDetails", pnj); //PNJCustomerDetailsMaster is bean object information provide to controller...
				 	 model.addAttribute("edit", false);
				     model.addAttribute("loggedinuser", getPrincipal());
				 	 return "ztestnosaledetails";
				
			} 
		 
		 
		
		 
			@RequestMapping(value = { "/nosalecustomer" }, method = RequestMethod.POST)
			public String savecustomernosale(PNJCustomerNoSaleDetails pnjCustomerDetailsMaster,BindingResult result,ModelMap model) {
			    java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				
				if (result.hasErrors()) {
					return "ztestnosaledetails";
				}
				System.out.println(">>> "+pnjCustomerDetailsMaster+" <<<");
				PNJCustomerNoSaleDetails pnj=new PNJCustomerNoSaleDetails();
				
				getPrincipal();
				
				pnj.setId(PNJUtill.generateUUId());
				//pnj.setCustomer_id(PNJUtill.generateCustomerId());
				pnj.setCustomer_name(pnjCustomerDetailsMaster.getCustomer_name().toUpperCase());
				pnj.setCustomer_contact_no(pnjCustomerDetailsMaster.getCustomer_contact_no());
				pnj.setCustomer_contact_no_alternative(pnjCustomerDetailsMaster.getCustomer_contact_no_alternative().toUpperCase());
				pnj.setCustomer_address(pnjCustomerDetailsMaster.getCustomer_address().toUpperCase());
				pnj.setCustomer_email_id(pnjCustomerDetailsMaster.getCustomer_email_id());
				pnj.setBest_time_to_reach(pnjCustomerDetailsMaster.getBest_time_to_reach().toUpperCase());
				pnj.setCustomer_remarks(pnjCustomerDetailsMaster.getCustomer_remarks().toUpperCase());
			//	pnj.setNo_sale(pnjCustomerDetailsMaster.getNo_sale().toUpperCase());
				pnj.setCustomer_created_on(date.toString());
				pnj.setDetails_filled_by(getPrincipal().toUpperCase());
					
				no_details_service.save(pnj);
				
			//    pnjCustomerDetailsMasterService.savePNJCustomerDetailsMaster(pnj);
				model.addAttribute("success", "Customer Created successfully");
				model.addAttribute("loggedinuser", getPrincipal());
				//return "success";
				return "successnosaledetails";
		}
		 
		
		@RequestMapping(value = { "/customer" }, method = RequestMethod.GET)
		public String customer(ModelMap model) {
			     PNJCustomerDetailsMaster pnj=new PNJCustomerDetailsMaster();
			     model.addAttribute("PNJCustomerDetailsMaster", pnj); //PNJCustomerDetailsMaster is bean object information provide to controller...
			 	 model.addAttribute("edit", false);
			     model.addAttribute("loggedinuser", getPrincipal());
			 	 return "customerdetailsmaster";
			
		}
		
		
		@RequestMapping(value = { "/saledetails" }, method = RequestMethod.GET)
		public String sale(ModelMap model) {
			 	 return "saledetails";
			
		}
	
	
		@RequestMapping(value = { "/customer" }, method = RequestMethod.POST)
			public String saveCountry11(PNJCustomerDetailsMaster pnjCustomerDetailsMaster,BindingResult result,
					ModelMap model) {
			    java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				
				if (result.hasErrors()) {
					return "customerdetailsmaster";
				}
				System.out.println(">>> "+pnjCustomerDetailsMaster+" <<<");
				PNJCustomerDetailsMaster pnj=new PNJCustomerDetailsMaster();
				
				getPrincipal();
				
				pnj.setId(PNJUtill.generateUUId());
				pnj.setCustomer_id(PNJUtill.generateCustomerId());
				pnj.setCustomer_name(pnjCustomerDetailsMaster.getCustomer_name().toUpperCase());
				pnj.setCustomer_contact_no(pnjCustomerDetailsMaster.getCustomer_contact_no());
				pnj.setCustomer_contact_no_alternative(pnjCustomerDetailsMaster.getCustomer_contact_no_alternative().toUpperCase());
				pnj.setCustomer_address(pnjCustomerDetailsMaster.getCustomer_address().toUpperCase());
				pnj.setCustomer_email_id(pnjCustomerDetailsMaster.getCustomer_email_id());
				pnj.setBest_time_to_reach(pnjCustomerDetailsMaster.getBest_time_to_reach().toUpperCase());
				pnj.setCustomer_remarks(pnjCustomerDetailsMaster.getCustomer_remarks().toUpperCase());
				pnj.setNo_sale(pnjCustomerDetailsMaster.getNo_sale().toUpperCase());
				pnj.setCustomer_created_on(date.toString());
				pnj.setDetails_filled_by(getPrincipal().toUpperCase());
					
				
				
			    pnjCustomerDetailsMasterService.savePNJCustomerDetailsMaster(pnj);
				model.addAttribute("success", "Customer Id :: " + pnj.getCustomer_id()+ " Created successfully");
				model.addAttribute("loggedinuser", getPrincipal());
				//return "success";
				return "customersuccess";
		}
		 
	/**
	 * Code For Pagination	
	 */
		
		
	 @RequestMapping(value = "/newuser6", method = RequestMethod.GET)
	    public String home(Model model) throws JsonGenerationException, JsonMappingException, IOException {
		 
		  String str="";
			// System.out.println(cuds.loadUserByUsername(getPrincipal()));
			 User user1 = userService.findBySSO(getPrincipal());
			 Iterator<UserProfile> itr=user1.getUserProfiles().iterator();
			 while(itr.hasNext()) {
				 str = (itr.next().getType().toString());
			 }
			 if(str.equalsIgnoreCase("AGENT")){
				 List<PNJCustomerDetailsMaster> user=pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMasterUserwise(getPrincipal(),"YES");
					model.addAttribute("PNJCustomerDetailsMaster", user);
			 }
			 else{
				 List<PNJCustomerDetailsMaster> user=pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster1("YES");
				 model.addAttribute("PNJCustomerDetailsMaster", user);
			 }
		 
		    model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
		 
		 
			 model.addAttribute("xxx",pnjComplainMasterService.Serach_All_Complain_Details(getPrincipal()));
		 
		 
		 
	/*        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(">>> "+mapper.writeValueAsString(countryServices.findAllCountries()));
	        model.addAttribute("employeeList", mapper.writeValueAsString(countryServices.findAllCountries()));
	*/      /*  List<Country> country=countryServices.findAllCountries();
	        model.addAttribute("users",country);*/
		    return "newtestmainpage";
	    }
	 
	 
	 @RequestMapping(value = "/newuser7", method = RequestMethod.GET)
	    public String mainpage(Model model) throws JsonGenerationException, JsonMappingException, IOException {
	      ObjectMapper mapper = new ObjectMapper();
	      //  System.out.println("-----> "+mapper.writeValueAsString(complainMasterObjectService.findAllMasterDetails()));
	       List<ComplainMasterObject> comp= complainMasterObjectService.findAllMasterDetails();
	       //System.out.println("-----> "+mapper.writeValueAsString(comp));
	       model.addAttribute("mainList", mapper.writeValueAsString(comp));
	       return "mainpage";
	    }
	 
	 @RequestMapping(value = "/saledeshboard", method = RequestMethod.GET)
	    public String superDeshBoadr(Model model) throws JsonGenerationException, JsonMappingException, IOException {
	      ObjectMapper mapper = new ObjectMapper();
	      //  System.out.println("-----> "+mapper.writeValueAsString(complainMasterObjectService.findAllMasterDetails()));
	       List<PNJCustomerSaleMaster> comp= pnjsaleservice.find_All_PNJSaleMasterDetails();//complainMasterObjectService.findAllMasterDetails();
	       //System.out.println("-----> "+mapper.writeValueAsString(comp));
	       model.addAttribute("mainList", mapper.writeValueAsString(comp));
	   	  model.addAttribute("loggedinuser", getPrincipal());
	       return "mainpagetest";
	    }
	 
	@Autowired
	PNJCustomerNoSaleDetailsService pnjNosaleService;
	 
	 @RequestMapping(value = "/nosaledetails", method = RequestMethod.GET)
	    public String nosaleDeails(Model model) throws JsonGenerationException, JsonMappingException, IOException {
	      ObjectMapper mapper = new ObjectMapper();
	      //  System.out.println("-----> "+mapper.writeValueAsString(complainMasterObjectService.findAllMasterDetails()));
	       List<PNJCustomerNoSaleDetails> comp= pnjNosaleService.find_All_PNJNoSaleMasterDetails();//complainMasterObjectService.findAllMasterDetails();
	       //System.out.println("-----> "+mapper.writeValueAsString(comp));
	       model.addAttribute("mainList", mapper.writeValueAsString(comp));
	   	  model.addAttribute("loggedinuser", getPrincipal());
	       return "nosalemainpage";
	    }
	 
	 private List<ComplainMasterObject> getData() {
		 return complainMasterObjectService.findAllMasterDetails();
		// return pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster(getPrincipal());
	 }
	 
	 
	 /**
	  * @param model
	  * @return
	  * @throws JsonGenerationException
	  * @throws JsonMappingException
	  * @throws IOException
	  */
	 
	 @RequestMapping(value = "/complain", method = RequestMethod.GET)
	    public String complain(Model model) {
		 PNJComplainMaster pnj=new PNJComplainMaster();
	     model.addAttribute("PNJComplainMaster", pnj); //PNJComplainMaster is bean object information provide to controller...
	 	 model.addAttribute("edit", false);
	     model.addAttribute("loggedinuser", getPrincipal());
	        return "complainmaster";
	    }
	 // 
	 @RequestMapping(value = "/addcomplain", method = RequestMethod.GET)
	    public String complainM(Model model) {
		// ComplainMasterObject pnj=new ComplainMasterObject();
		 PNJComplainMaster pnj=new PNJComplainMaster();
		 System.out.println("Inside Comaplain Object :: ");
	     model.addAttribute("PNJComplainMaster", pnj); //PNJComplainMaster is bean object information provide to controller...
	     model.addAttribute("edit", false);
	     model.addAttribute("loggedinuser", getPrincipal());
	        return "complainmaster";
	    }
	 
	
	 @RequestMapping(value = { "/addcomplain" }, method = RequestMethod.POST)
		public String saveComplain(PNJComplainMaster cmo,BindingResult result) {
		 System.out.println("Inside Comaplain Object :: For POST Request");
		 java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		 System.out.println("Date :: >>> "+date.toString());
		 System.out.println("--> "+cmo);
		 String str=null;
		 String custName=null;
		 User user = userService.findBySSO(getPrincipal());
		 Iterator<UserProfile> itr=user.getUserProfiles().iterator();
		 while(itr.hasNext()) {
			 str = (itr.next().getType().toString());
		 }
		 System.out.println("Type--> "+str);
		 System.out.println("Customer-Id -->"+PNJUtill.removeLeftWhiteSpace(cmo.getCustomer_id().toUpperCase()));
		 List<PNJCustomerDetailsMaster> username=pnjCustomerDetailsMasterService.findCustomerName(PNJUtill.removeLeftWhiteSpace(cmo.getCustomer_id().toUpperCase()));
		 Iterator<PNJCustomerDetailsMaster> itrt=username.iterator();
		 while(itrt.hasNext()) {
			 custName=itrt.next().getCustomer_name();
		 }System.out.println("Customer Name --> "+custName);
		 
		 
		 PNJComplainMaster pnj = new PNJComplainMaster();
		 
		 
		    pnj.setId(PNJUtill.generateUUId());
			//pnj.setCustomer_id(cmo.getCustomer_id());
			pnj.setCustomer_id(PNJUtill.removeLeftWhiteSpace(cmo.getCustomer_id().trim()).toUpperCase());
		
			if((custName.toUpperCase().trim()!=null) || (!custName.toUpperCase().trim().isEmpty())){
			    pnj.setCustomer_name(custName.toUpperCase().trim());
			}else {
				pnj.setCustomer_name("");
			}
			pnj.setComplain_id(PNJUtill.generateCompalinId());
			pnj.setComplain_register_date(date.toString());
			pnj.setService_offered(cmo.getService_offered().toUpperCase());
			pnj.setComplain_remarks(cmo.getComplain_remarks().toUpperCase().trim());
			pnj.setAssign_technician(cmo.getAssign_technician().toUpperCase().trim());
			pnj.setPayment_amount(cmo.getPayment_amount());
			pnj.setPayment_status(cmo.getPayment_status().toUpperCase());
			pnj.setPayment_mode(cmo.getPayment_mode().toUpperCase());
			pnj.setPayment_date(cmo.getPayment_date());
			pnj.setPayment_account_number(cmo.getPayment_account_number());
			pnj.setPayment_remarks(cmo.getPayment_remarks().toUpperCase());
			pnj.setComplain_type(cmo.getComplain_type().toUpperCase());
			pnj.setDetails_filled_by(getPrincipal().toUpperCase());
			pnj.setComplain_status("ISSUED");
			pnj.setUser_type(str);
		//	pnj.setAdmin_remark("");
		//	pnj.setFeedbk(cmo.getFeedbk());
			
			pnj.setName_on_card(cmo.getName_on_card().toUpperCase());
			pnj.setCard_number(cmo.getCard_number());
			pnj.setCard_exp_date(cmo.getCard_exp_date());
			pnj.setCard_cvv(cmo.getCard_cvv());
			
			pnj.setE_chq_name(cmo.getE_chq_name().toUpperCase());
			pnj.setE_chq_account(cmo.getE_chq_account());
			pnj.setE_chq_no(cmo.getE_chq_no());
			pnj.setE_chq_routing_no(cmo.getE_chq_routing_no());
			
			pnj.setP_chq_name(cmo.getP_chq_name().toUpperCase());
			pnj.setP_chq_account(cmo.getP_chq_account());
			pnj.setP_chq_no(cmo.getP_chq_no());
			pnj.setP_chq_routing_no(cmo.getP_chq_routing_no());
			
			pnj.setOnline_bill_pay(cmo.getOnline_bill_pay().toUpperCase());
			pnj.setMoney_order(cmo.getMoney_order().toUpperCase());
			pnj.setOther_payment_details(cmo.getOther_payment_details().toUpperCase());
			
	    	 pnjComplainMasterService.save(pnj);
			return "complainsuccess";
		}
	 
	 
	 
	 
	 
	 
	/* @RequestMapping(value = "/feedbacklist", method = RequestMethod.GET)
	    public String feedbackList(Model model) {
		 List<PNJCustomerFeedback> users=feedbackService.find_All_PNJCustomerFeedbackDetails(); 
		 System.out.println("Feedback List "+users);
		 model.addAttribute("PNJCustomerFeedback", users); //PNJComplainMaster is bean object information provide to controller...
	 	 model.addAttribute("edit", false);
	     model.addAttribute("loggedinuser", getPrincipal());
	        return "feedbacklist";
	    }*/
	 
	 
	 
	/* 
	 @RequestMapping(value = "/addfeedback", method = RequestMethod.GET)
	    public String FeedbackComplain(Model model) {
		 PNJCustomerFeedback pnj=new PNJCustomerFeedback();
		 System.out.println("Inside Feedback Comaplain Object :: "+pnj);
	     model.addAttribute("PNJCustomerFeedback", pnj); //PNJComplainMaster is bean object information provide to controller...
	     model.addAttribute("edit", false);
	     model.addAttribute("loggedinuser", getPrincipal());
	        return "feedback";
	    }*/
	 
	
	/* @RequestMapping(value = { "/addfeedback" }, method = RequestMethod.POST)
		public String SaveFeedbackComplain(PNJCustomerFeedback cmo,BindingResult result) {
		 java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		 PNJCustomerFeedback pnj = new PNJCustomerFeedback();
		 	pnj.setId(PNJUtill.generateUUId());
		 	pnj.setCustomer_id(cmo.getCustomer_id());
			pnj.setComplain_id(cmo.getComplain_id());
		    pnj.setCustomer_feedback(cmo.getCustomer_feedback().toUpperCase());
		    pnj.setCustomer_feedback_time(date.toString());
		    feedbackService.save(pnj);
		    return "feedbacksuccess";
		}*/
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/* @RequestMapping(value = { "/edit-comp-{id}" }, method = RequestMethod.GET)
		public String editComplainDetails(@PathVariable String id, ModelMap model) {
			
			PNJComplainMaster country=pnjComplainMasterService.findById(Integer.valueOf(id));
			model.addAttribute("PNJComplainMaster", country);
			model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "complainmaster";
		}
	*/ 
	 
	 	/**
		 * This method will provide the medium to update an existing user.
		 */
		@RequestMapping(value = { "/edit-complains-{Id}" }, method = RequestMethod.GET)
		public String editComplain(@PathVariable String Id, ModelMap model) {
			PNJComplainMaster user = pnjComplainMasterService.findById((Id));
			/*System.out.println("#################################");
			System.out.println("Step 1 :: For Inside Edit-Get ");
			System.out.println(user.getComplain_id()+" "+user.getService_offered()+" ::FB:: "+user.getCustomer_feedback());
			System.out.println("Inside Edit Values :: "+user);
			System.out.println("#################################");*/
			model.addAttribute("PNJComplainMaster", user);
			model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "complainmaster";
		}
		
		@RequestMapping(value = { "/view-complains-{Id}" }, method = RequestMethod.GET)
		public String viewComplain(@PathVariable String Id, ModelMap model) {
			PNJComplainMaster user = pnjComplainMasterService.findById((Id));
			model.addAttribute("PNJComplainMaster", user);
			model.addAttribute("edit1", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "complainmasterview";
		}
		
		@RequestMapping(value = { "/view-payment-{Id}" }, method = RequestMethod.GET)
		public String viewPayment(@PathVariable String Id, ModelMap model) {
			PNJComplainMaster user = pnjComplainMasterService.findById((Id));
			System.out.println("View Payment "+user);
			model.addAttribute("payments", user);
			model.addAttribute("loggedinuser", getPrincipal());
			return "paymentdetail";
		}
		
		/*@RequestMapping(value = { "/re-assign-{Id}" }, method = RequestMethod.GET)
		public String viewPaymentDetails(@PathVariable String Id, ModelMap model) {
			PNJComplainMaster user = pnjComplainMasterService.findById(Id);
			//model.addAttribute("payments", user);
			//model.addAttribute("edit1", false);
			model.addAttribute("loggedinuser", getPrincipal());
			return "paymentdetail";
		}*/
		
		
		/**
		 * This method will be called on form submission, handling POST request for
		 * updating user in database. It also validates the user input
		 */
		@RequestMapping(value = { "/edit-complains-{Id}" }, method = RequestMethod.POST)
		public String updateComplain(@Valid PNJComplainMaster user, BindingResult result,
				ModelMap model, @PathVariable String Id) {
			
		/*	System.out.println("#################################");
			System.out.println("Step 2 :: For Inside Edit-Post ");
			System.out.println(user.getFeedbk()+" ::FB:: "+user.getCustomer_feedback());
			System.out.println("Inside Edit Values :: "+user);
			System.out.println("#################################");*/
			  String str="";
				// System.out.println(cuds.loadUserByUsername(getPrincipal()));
				 User us = userService.findBySSO(getPrincipal());
				 Iterator<UserProfile> itr=us.getUserProfiles().iterator();
				 while(itr.hasNext()) {
					 str = (itr.next().getType().toString());
				 }
				 System.out.println("Type--> "+str);
			
			

			if (result.hasErrors()) {
				return "complainmaster";
			}

			//pnjComplainMasterService.updatePNJComplainMaster(user);
			pnjComplainMasterService.updatePNJComplainMaster(user, str);
			//model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			return "complainsuccess";
		}
	 
	 
	 
		@RequestMapping(value = { "/edit-customer-{Id}" }, method = RequestMethod.GET)
		public String editCustomer(@PathVariable String Id, ModelMap model) {
			//PNJComplainMaster user = pnjComplainMasterService.findById(Integer.valueOf(Id));
			PNJCustomerDetailsMaster user = pnjCustomerDetailsMasterService.findById((Id));
			model.addAttribute("PNJCustomerDetailsMaster", user);
			model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "customerdetailsmaster";
		}
		
		@RequestMapping(value = { "/view-customer-{Id}" }, method = RequestMethod.GET)
		public String viewCustomer(@PathVariable String Id, ModelMap model) {
			PNJCustomerDetailsMaster user = pnjCustomerDetailsMasterService.findById((Id));
			model.addAttribute("PNJCustomerDetailsMaster", user);
			model.addAttribute("edit1", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "customerdetailsmasterview";
		}
		
		/**
		 * This method will be called on form submission, handling POST request for
		 * updating user in database. It also validates the user input
		 */
		@RequestMapping(value = { "/edit-customer-{Id}" }, method = RequestMethod.POST)
		public String updateCustomer(@Valid PNJCustomerDetailsMaster user, BindingResult result,
				ModelMap model, @PathVariable String Id) {

			if (result.hasErrors()) {
				return "customerdetailsmaster";
			}

			//pnjComplainMasterService.updatePNJComplainMaster(user);
			pnjCustomerDetailsMasterService.updatePNJCustomerDetailsMaster(user);
			
			//model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			return "customerdetailsuccess";
		}
	 
	 
		 
	 
	 
	 
	 
	 
	 
	 
	 @RequestMapping(value = "/testLogic", method = RequestMethod.GET)
	    public String TestLogic() {
		 ComplainMasterObject cm=new ComplainMasterObject();
	//	 List<ComplainMasterObject> list= complainMasterObjectService.findAllMasterDetails("PNJ20170810391");
		 
		List<PNJCustomerDetailsMaster> list = pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster("9911408870");
		 
		 System.out.println(list);
		 System.out.println(list.toString());
		        return "home";
	    }
	 
	 
	 
	 @RequestMapping(value = { "/search" }, method = RequestMethod.GET)
		public String searchId(Model model) {
		 
	//	 SearchObject so=new SearchObject();
		/* List<PNJCustomerDetailsMaster> list = pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster();
		 System.out.println(list);
		 System.out.println(list.toString());*/
	     model.addAttribute("SearchObject",new SearchObject());
	 //    model.addAttribute("edit", true);
	     model.addAttribute("loggedinuser", getPrincipal());
		 return "searchtest";
	}
	 
	 
	 @RequestMapping(value = "/search", method = RequestMethod.POST)
	    public String TestLogicSearch(Model model,SearchObject cmo,BindingResult result) {
	//	 ComplainMasterObject cm=new ComplainMasterObject();
	//	 List<ComplainMasterObject> list= complainMasterObjectService.findAllMasterDetails("PNJ20170810391");
		// SearchObject se= new SearchObject();
		// pnj = new PNJCustomerDetailsMaster();
		 
		List<PNJCustomerDetailsMaster> list = pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster(cmo.getCustomer_id());
		 
		 System.out.println(list);
		 model.addAttribute("PNJCustomerDetailsMaster",list);
		 model.addAttribute("SearchObject",cmo);
		     return "searchtest";
	    }
	 
	 
	 
	 @RequestMapping(value = { "/reporting" }, method = RequestMethod.GET)
		public String searchReporting(Model model) {
		 
	//	 SearchObject so=new SearchObject();
		/* List<PNJCustomerDetailsMaster> list = pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster();
		 System.out.println(list);
		 System.out.println(list.toString());*/
	     model.addAttribute("SearchObject",new SearchObject());
	 //    model.addAttribute("edit", true);
	     model.addAttribute("loggedinuser", getPrincipal());
		 return "reportdaily";
	}
	 
	 
	 @RequestMapping(value = "/reporting", method = RequestMethod.POST)
	    public String ReportingSearch(Model model,SearchObject cmo,BindingResult result) {
	//	 ComplainMasterObject cm=new ComplainMasterObject();
	//	 List<ComplainMasterObject> list= complainMasterObjectService.findAllMasterDetails("PNJ20170810391");
		// SearchObject se= new SearchObject();
		// pnj = new PNJCustomerDetailsMaster();
		 
		List<PNJCustomerDetailsMaster> list = pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster(cmo.getCustomer_id());
		 
		 System.out.println(list);
		 model.addAttribute("PNJCustomerDetailsMaster",list);
		 model.addAttribute("SearchObject",cmo);
		 
		 model.addAttribute("xxx",pnjComplainMasterService.Serach_All_Complain_Details(cmo.getCustomer_id()));
		 
		 model.addAttribute("users", pnjComplainMasterService.Serach_All_Complain_Details(cmo.getCustomer_id()));
		 
		     return "reportdaily";
	    }
	 
	 
	 
	 @RequestMapping(value = { "/reporting1" }, method = RequestMethod.GET)
		public String searchReporting1(Model model) {
	     model.addAttribute("SearchObject",new SearchObject());
	     model.addAttribute("loggedinuser", getPrincipal());
		 return "ReportForSaleDetail";
	}
	 
	 
	 @RequestMapping(value = "/reporting1", method = RequestMethod.POST)
	    public String ReportingSearch1(Model model,SearchObject cmo,BindingResult result) {
		 
		List<PNJCustomerDetailsMaster> list = pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMaster(cmo.getCustomer_id());
		 
		 System.out.println(list);
		 model.addAttribute("PNJCustomerDetailsMaster",list);
		 model.addAttribute("SearchObject",cmo);
		 
		 model.addAttribute("xxx",pnjComplainMasterService.Serach_All_Complain_Details(cmo.getCustomer_id()));
		 
		 model.addAttribute("users", pnjComplainMasterService.Serach_All_Complain_Details(cmo.getCustomer_id()));
		 
		 List<PNJCustomerDetailsMaster> user=pnjCustomerDetailsMasterService.find_All_PNJCustomerDetailsMasterUserwise1(cmo.getCustomer_id(),cmo.getNo_sale());
			model.addAttribute("PNJCustomerDetailsMaster", user);
		 
		     return "ReportForSaleDetail";
	    }
	 
	 
	 
	/* *//**
	  * Search Feedback
	  * @param model
	  * @return
	  */
	 @RequestMapping(value = { "/searchfeedback" }, method = RequestMethod.GET)
		public String searchFeedback(Model model) {
		 
	     model.addAttribute("SearchObject",new SearchObject());
	 //    model.addAttribute("edit", true);
	     model.addAttribute("loggedinuser", getPrincipal());
		 return "searchFeedback";
	}
	 
	 
	 @RequestMapping(value = "/searchfeedback", method = RequestMethod.POST)
	    public String FeedBackSearch(Model model,SearchObject cmo,BindingResult result) {
		List<PNJCustomerFeedback> list = feedbackService.find_All_PNJCustomerFeedbackDetails(cmo.getCustomer_id());//pnjComplainMasterService.find_All_PNJComplainMasterDetails(cmo.getCustomer_id());
		 model.addAttribute("PNJCustomerFeedback",list);
		 model.addAttribute("SearchObject",cmo);
		  return "searchFeedback";
	    }
	 
	 
	 @RequestMapping(value = { "/searchcomplain" }, method = RequestMethod.GET)
		public String searchComplainId(Model model) {
		 
	     model.addAttribute("SearchObject",new SearchObject());
	 //    model.addAttribute("edit", true);
	     model.addAttribute("loggedinuser", getPrincipal());
		 return "searchcomplain";
	}
	 
	 
	 @RequestMapping(value = "/searchcomplain", method = RequestMethod.POST)
	    public String ComplainSearch(Model model,SearchObject cmo,BindingResult result) {
		List<PNJComplainMaster> list = pnjComplainMasterService.find_All_PNJComplainMasterDetails(cmo.getCustomer_id());
		 model.addAttribute("PNJComplainMaster",list);
		 model.addAttribute("SearchObject",cmo);
		     return "searchcomplain";
	    }
	 
	 
	 @RequestMapping(value = { "/complains" }, method = RequestMethod.GET)
		public String listComplains(ModelMap model) {
           String str="";
		// System.out.println(cuds.loadUserByUsername(getPrincipal()));
		 User user = userService.findBySSO(getPrincipal());
		 Iterator<UserProfile> itr=user.getUserProfiles().iterator();
		 while(itr.hasNext()) {
			 str = (itr.next().getType().toString());
		 }
		 System.out.println("Type--> "+str);
  		 
  		 if(str.equalsIgnoreCase("ADMIN")){
  			 List<PNJComplainMaster> users = pnjComplainMasterService.find_All_PNJComplainMasterDetails();
  			 model.addAttribute("users", users);
			
			 
  		 }else if(str.equalsIgnoreCase("TECH")){
  			List<PNJComplainMaster> users = pnjComplainMasterService.find_All_User_WisePNJComplainMasterForTech(getPrincipal());
 			 model.addAttribute("users", users);
			
			
  		 }if(str.equalsIgnoreCase("SUPPORT")){
  			List<PNJComplainMaster> users = pnjComplainMasterService.find_All_PNJComplainMasterDetails();
 			 model.addAttribute("users", users);
			
			
  		 }if(str.equalsIgnoreCase("AGENT")){
  			 List<PNJComplainMaster> users = pnjComplainMasterService.find_All_User_WisePNJComplainMaster(getPrincipal(),str);
 			 model.addAttribute("users", users);
			
			
  		 }
  		 
  		 model.addAttribute("loggedinuser", getPrincipal()); 
			
  		return "complainlist";
			
		}
	 
	 
	 @RequestMapping(value = { "/edit-complain-{id}" }, method = RequestMethod.GET)
		public String editComplains(@PathVariable String id, ModelMap model) {
		 
		    PNJComplainMaster users = new PNJComplainMaster();
		    System.out.println("Id-"+id);
		    users =	pnjComplainMasterService.findById((id));
		    System.out.println(users);
		//	Country country=countryServices.findById(Integer.valueOf(id));
			model.addAttribute("users", users);
			model.addAttribute(new PNJComplainMaster());
			model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "complainlist";
		}
	 
	 
	/* @RequestMapping(value = { "/edit-complain-{id}" }, method = RequestMethod.POST)
		public String updateComplains(PNJComplainMaster pnj, BindingResult result,
				ModelMap model, @PathVariable String id) {

			if (result.hasErrors()) {
				return "complain";
			}

			pnjComplainMasterService.updatePNJComplainMaster(pnj);
			
	//		model.addAttribute("success", "Country " + country.getCountryName()+ " updated successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			return "complain";
		}*/
	 
	 @RequestMapping(value = { "/notepad" }, method = RequestMethod.GET)
		public String NotePad() {
			return "notepad";
		}
	 
	 @Autowired
	 PNJTrackerService trackerService;
	 
	 @RequestMapping(value = { "/dailytracker" }, method = RequestMethod.GET)
		public String dailyTracker(ModelMap model) {
		 
		 PNJDailyTracker pnj=new PNJDailyTracker();
		 model.addAttribute("PNJDailyTracker", pnj);
		 model.addAttribute("edit", true);
		 model.addAttribute("loggedinuser", getPrincipal());
			return "dailytracker";
		}
	 
	 
	 
	 @RequestMapping(value = "/dailytracker", method = RequestMethod.POST)
	    public String dailyTrackerDetails(PNJDailyTracker tracker,BindingResult result) {
		 java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		   String str="";
			// System.out.println(cuds.loadUserByUsername(getPrincipal()));
			 User user = userService.findBySSO(getPrincipal());
			 Iterator<UserProfile> itr=user.getUserProfiles().iterator();
			 while(itr.hasNext()) {
				 str = (itr.next().getType().toString());
			 }
			 
		 PNJDailyTracker pnj=new PNJDailyTracker();
		 pnj.setId(PNJUtill.generateUUId());
		 pnj.setEmp_name(getPrincipal().toUpperCase());
		 pnj.setEmp_type(str);
		 pnj.setTracker_date(date.toString());
		 pnj.setEmp_daily_tracker(tracker.getEmp_daily_tracker());
		 pnj.setAdmin_tracker("");
		 
		 trackerService.save(pnj);
		 
		  return "trackersuccess";
	    }
	 
	 
	 @RequestMapping(value = { "/dailytrackerdetails" }, method = RequestMethod.GET)
		public String viewdailyTracker(ModelMap model) {
		 
		
		     model.addAttribute("SearchObject",new SearchObject());
		 //    model.addAttribute("edit", true);
		     model.addAttribute("loggedinuser", getPrincipal());
		     
			return "searchdailytracker";
		}
	 
	 @RequestMapping(value = "/dailytrackerdetails", method = RequestMethod.POST)
	    public String SearchTracker(Model model,SearchObject cmo,BindingResult result) {
		List<PNJDailyTracker> list = trackerService.find_All_TrackerDetails(cmo.getCustomer_name());//pnjComplainMasterService.find_All_PNJComplainMasterDetails(cmo.getCustomer_name());
		 model.addAttribute("PNJComplainMaster",list);
		 model.addAttribute("SearchObject",cmo);
		  model.addAttribute("loggedinuser", getPrincipal());
		   
		     return "searchdailytracker";
	    }
	 
	/* @Autowired
	 ComplainMasterObjectService cserv;
	 
	 @RequestMapping(value = { "/all" }, method = RequestMethod.GET)
		public String allDeatilsMaster(ModelMap model) {
		  //  List<Object> alldata = pnjComplainMasterService.AllMasterDeatils();
		  List<ComplainMasterObject> alldata = cserv.findAllMasterDetails();
			
		    System.out.println("\n:: All Master Data ::\n"+alldata);
		    
		    //	model.addAttribute("users", users);
			model.addAttribute("loggedinuser", getPrincipal());
			return "home";
		}*/
	 
	 
	 
	 
	 @RequestMapping(value = { "/no_sale_data" }, method = RequestMethod.GET)
		public String NoSaleDetails() {
			return "saledetails";
		}
	 
	 
	 @RequestMapping(value = { "/no_sale_details" }, method = RequestMethod.GET)
		public String ZNoSaleDetails() {
			return "ztestnosaledetails";
		}
	 
	 @RequestMapping(value = { "/editnosale" }, method = RequestMethod.GET)
		public String EditNoSaleDetails(Model model) {
				return "saledetails";
		}
	 
	 @RequestMapping(value = { "/editsale" }, method = RequestMethod.GET)
		public String EditSaleDetails(Model model) {
		  String str="";
			// System.out.println(cuds.loadUserByUsername(getPrincipal()));
			 User user1 = userService.findBySSO(getPrincipal());
			 Iterator<UserProfile> itr=user1.getUserProfiles().iterator();
			 while(itr.hasNext()) {
				 str = (itr.next().getType().toString());
			 }
			 
		//	 System.out.println("->-"+pnjComplainMasterService.Serach_All_Complain_Details(getPrincipal()));
			  
			 
			 
			 if(str.equalsIgnoreCase("ADMIN")){
				 List<PNJCustomerSaleMaster> user=pnjsaleservice.find_All_PNJSaleMasterDetails();//
				 model.addAttribute("PNJCustomerDetailsMaster", user);
	  		 }else if(str.equalsIgnoreCase("TECH")){
		  			List<PNJCustomerSaleMaster> user = pnjsaleservice.find_All_User_WisePNJSaleMasterForTech(getPrincipal());
  					model.addAttribute("PNJCustomerDetailsMaster", user);
  					System.out.println("::=> "+user);
  			 }else if(str.equalsIgnoreCase("SUPPORT")){
	  			 List<PNJCustomerSaleMaster> user=pnjsaleservice.find_All_PNJSaleMasterDetails();//
					model.addAttribute("PNJCustomerDetailsMaster", user);
	  		 }else if(str.equalsIgnoreCase("AGENT")){
	  			 List<PNJCustomerSaleMaster> user = pnjsaleservice.find_All_PNJSaleMaster(getPrincipal());
	 			 model.addAttribute("PNJCustomerDetailsMaster", user);
	  		 }
			
				
				 
		    model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "salemasterdetails";//"pnjcustomerdetailsmaster";
		}
	 
	 @RequestMapping(value = { "/edit-sale-{Id}" }, method = RequestMethod.GET)
		public String editSale(@PathVariable String Id, ModelMap model) {
		 PNJCustomerSaleMaster user = pnjsaleservice.findById((Id));
			model.addAttribute("PNJCustomerSaleMaster", user);
			model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
			return "ztestsaledetails";
		}
	 
		@RequestMapping(value = { "/edit-sale-{Id}" }, method = RequestMethod.POST)
		public String editSale(@Valid PNJCustomerSaleMaster user, BindingResult result,
				ModelMap model, @PathVariable String Id) {
			  String str="";
				 User us = userService.findBySSO(getPrincipal());
				 Iterator<UserProfile> itr=us.getUserProfiles().iterator();
				 while(itr.hasNext()) {
					 str = (itr.next().getType().toString());
				 }

			if (result.hasErrors()) {
				return "ztestsaledetails";
			}

			//pnjComplainMasterService.updatePNJComplainMaster(user);
			//pnjComplainMasterService.updatePNJComplainMaster(user, str);
			pnjsaleservice.updatePNJCustomerSaleMaster(user, str);
			//model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
			model.addAttribute("loggedinuser", getPrincipal());
			return "successupdatesaledetails";
		}
	 
		
		 @RequestMapping(value = { "/salelist" }, method = RequestMethod.GET)
			public String listSales(ModelMap model) {
	     		 List<PNJCustomerSaleMaster> users = pnjsaleservice.find_All_PNJSaleMasterDetails();
	  			 model.addAttribute("users", users);
		    	 model.addAttribute("loggedinuser", getPrincipal()); 
				
	  		return "salelist";
				
			}
		
		
		
 }