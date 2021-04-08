package com.herbology.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.herbology.models.Plant;
import com.herbology.models.User;
import com.herbology.services.PlantService;
import com.herbology.services.UserService;

@Controller
public class AppController {

	@Autowired
	private UserService userServ;
	@Autowired
	private PlantService plantServ;

//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	String currentPrincipalName = authentication.getName();
//	User currentUser = userServ.findByEmail(currentPrincipalName);

	@GetMapping("") // splash page, require login/registration to redirect to home page
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // need to add securities dependency to
																				// implement this
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userServ.save(user);

		return "register_success";
	}

	@GetMapping("/users") // make this homepage
	public String listUsers(Model model, Principal principal) {
		String username = principal.getName();
		User currentUser = userServ.findByEmail(username);

		model.addAttribute("username", username);

		List<User> listUsers = userServ.listAll();
		model.addAttribute("listUsers", listUsers);

		List<Plant> listPlants = plantServ.listAll();
		model.addAttribute("listPlants", listPlants);

		List<Plant> listUsersPlants = currentUser.getPlantList();
		model.addAttribute("listUsersPlants", listUsersPlants);

		return "users";
	}

	@RequestMapping("/new")
	public String showNewPlantPage(Model model) {
		Plant plant = new Plant();// change to plant object matching track call
		model.addAttribute("plant", plant);
		return "new_plant";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savePlant(@ModelAttribute("data") Plant plant) {
		plantServ.save(plant);
		return "redirect:/users";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditPlantPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_plant");
		Plant plant = plantServ.get(id);
		mav.addObject("plant", plant);
		return mav;
	}

	@RequestMapping("/delete/{id}/{username}")
	public String deletePlant(@PathVariable(name = "id") int id, @PathVariable(name = "username") String username) {
		User currentUser = userServ.findByEmail(username);
		List<Plant> start = currentUser.getPlantList();
		Plant plant = plantServ.get(id);

		start.remove(plant);
		currentUser.setPlantList(start);
		userServ.delete(currentUser.getId());
		userServ.save(currentUser);
		return "redirect:/users";
	}

	@RequestMapping("/track/{id}/{username}")
	public String trackPlant(@PathVariable(name = "id") int id, @PathVariable(name = "username") String username) {
		Plant plant = plantServ.get(id);
		User currentUser = userServ.findByEmail(username);
		System.out.println(currentUser.getEmail());
		List<Plant> start = currentUser.getPlantList();
	
		boolean match = false;
		while (!match) {
			for (int i = 0; i < start.size(); i++) {
				if (start.get(i).getId() == plant.getId()) {
					match = true;
					System.out.println("Already tracking this plant!");
					return "redirect:/users";
				} else {
					continue;
				}
			}
			start.add(plant);
			currentUser.setPlantList(start);
			userServ.delete(currentUser.getId());
			userServ.save(currentUser);
			match = true;
		}
		return "redirect:/users";

	}

	@RequestMapping("/contact")
	public String showContactPage(Model model) {
		return "contact";
	}

	@RequestMapping("/about")
	public String showAboutPage(Model model) {
		return "about";
	}
	
	@RequestMapping("/plant/{id}")
	public ModelAndView viewPlant(@PathVariable(name = "id") int id, Model model) {
		Plant plant = plantServ.get(id);
		model.addAttribute("plant", plant);
		ModelAndView mav = new ModelAndView("plant");
		return mav;
		
	}
}