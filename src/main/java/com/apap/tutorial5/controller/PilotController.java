package com.apap.tutorial5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home Page");
		return "home";
	}
	
	@RequestMapping(value="/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("title", "Add Pilot");
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping(value="/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	
	@RequestMapping(value="/pilot/view", method= RequestMethod.GET)
	private String viewPilot(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		model.addAttribute("title", "View Pilot");
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		List<FlightModel> pilotFlight = pilot.getPilotFlight();
		model.addAttribute("pilotFlight", pilotFlight);
		return "view-pilot";
		
	}
	
	@RequestMapping(value="/pilot/update/{id}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable(value="id") String id, Model model) {
		model.addAttribute("title", "Update Pilot");
		PilotModel pilot = pilotService.getPilotDetailById(Long.parseLong(id));
		model.addAttribute("pilot", pilot);
		model.addAttribute("newPilot", new PilotModel());
		return "update-pilot";
	}
	
	@RequestMapping(value="/pilot/update", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PilotModel newpilot) {
		pilotService.updatePilot(newpilot);
		return "update";
	}
	
	@RequestMapping(value="/pilot/delete/{id}", method = RequestMethod.GET)
	private String deletePilot(@PathVariable(value="id") String id) {
		PilotModel pilot = pilotService.getPilotDetailById(Long.parseLong(id));
		pilotService.deletePilot(pilot);
		return "delete";
	}

}
