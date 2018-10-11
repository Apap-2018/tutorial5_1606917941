package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;

public interface FlightService {
	FlightModel getFlightDetailById(long id);
	void addFlight(FlightModel flight);
	void updateFlight(FlightModel flight);
	void deleteFlight(FlightModel flight);
	void deleteFlightById(long id);

}
