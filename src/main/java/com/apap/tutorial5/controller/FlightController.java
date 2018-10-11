package com.apap.tutorial5.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value="licenseNumber") String licenseNumber,Model model) {
		model.addAttribute("title", "Add Flight");
		PilotModel pilot = new PilotModel();
		pilot.setPilotFlight(new ArrayList<FlightModel>());
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		model.addAttribute("licenseNumber", licenseNumber);
		return "addFlight";
		
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method=RequestMethod.POST, params={"addRow"})
	public String addRow(@PathVariable(value="licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot, Model model) {
		model.addAttribute("title", "Add Flight");
		pilot.getPilotFlight().add(new FlightModel());
	    model.addAttribute("pilot", pilot);
	    model.addAttribute("licenseNumber", licenseNumber);
	    return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method=RequestMethod.POST,params={"removeRow"})
	public String removeRow(
			@PathVariable(value="licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot, Model model, 
	        final HttpServletRequest req) {
		model.addAttribute("title", "Add Flight");
	    Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    pilot.getPilotFlight().remove(rowId.intValue());
	    model.addAttribute("pilot", pilot);
	    model.addAttribute("licenseNumber", licenseNumber);
	    return "addFlight";
	}

	

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params= {"save"})
	private String addFlightSubmit(@PathVariable(value = "licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot, Model model) {
		PilotModel beforePilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("licenseNumber", licenseNumber);
		for (FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(beforePilot);
			flightService.addFlight(flight);
		}
		return "add";
	}
	
	@RequestMapping(value="/flight/update/{id}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable("id") String id, Model model) {
		model.addAttribute("title", "Update Flight");
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		System.out.println(id);
		model.addAttribute("flight", flight);
		model.addAttribute("newFlight", new FlightModel());
		return "update-flight";
	}
	
	@RequestMapping(value="/flight/update", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute FlightModel newflight) {
		flightService.updateFlight(newflight);
		return "update";
	}
	
	@RequestMapping(value="/flight/view/{id}", method = RequestMethod.GET)
	private String viewFlight(@PathVariable(value="id") String id, Model model) {
		model.addAttribute("title",  "View Flight");
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(flight.getPilot().getLicenseNumber());
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", pilot);
		return "view-flight";
	}
	
	
	@RequestMapping(value="/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		return "delete";
	}

}
