package com.apap.tutorial5.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;

@Repository
public interface PilotDB extends JpaRepository<PilotModel, Long> {
	PilotModel findByLicenseNumber(String licenseNumber);
	PilotModel findById(long id);
	

}
