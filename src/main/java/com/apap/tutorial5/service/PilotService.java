package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;

public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	PilotModel getPilotDetailById(long id);
	List<FlightModel> getListFlightByLicenseNumber(String licenseNumber);
	void addPilot(PilotModel pilot);
	void updatePilot(PilotModel pilot);
	void deletePilot(PilotModel pilot);
}
