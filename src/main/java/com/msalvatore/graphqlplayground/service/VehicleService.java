package com.msalvatore.graphqlplayground.service;

import com.msalvatore.graphqlplayground.entity.Vehicle;
import com.msalvatore.graphqlplayground.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(final VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Vehicle createVehicle(String type, String modelCode, String brandName, LocalDate launchDate) {
        return vehicleRepository.save(updateVehicle(new Vehicle(), type, modelCode, brandName, launchDate));
    }

    @Transactional
    public Vehicle updateVehicle(int id, String type, String modelCode, String brandName, LocalDate launchDate) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle with id " + id + " not found"));
        return vehicleRepository.save(updateVehicle(vehicle, type, modelCode, brandName, launchDate));
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles(int count) {
        return vehicleRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicle(int id) {
        return vehicleRepository.findById(id)
                // TODO: Improve error handling
                .orElseThrow(() -> new RuntimeException("Vehicle with id " + id + " not found"));
    }

    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }

    private static Vehicle updateVehicle(Vehicle vehicle, String type, String modelCode, String brandName, LocalDate launchDate) {
        vehicle.setType(type);
        vehicle.setModelCode(modelCode);
        vehicle.setBrandName(brandName);
        vehicle.setLaunchDate(launchDate);

        return vehicle;
    }
}
