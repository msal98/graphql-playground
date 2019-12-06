package com.msalvatore.graphqlplayground.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.msalvatore.graphqlplayground.entity.Vehicle;
import com.msalvatore.graphqlplayground.service.VehicleService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VehicleMutation implements GraphQLMutationResolver {

    private final VehicleService vehicleService;

    public VehicleMutation(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public Vehicle createVehicle(String type, String modelCode, String brandName, LocalDate launchDate) {
        return vehicleService.createVehicle(type, modelCode, brandName, launchDate);
    }

    public boolean deleteVehicle(int id) {
        vehicleService.deleteVehicle(id);
        // can't have void return type in a mutation, so just return true
        return true;
    }

}
