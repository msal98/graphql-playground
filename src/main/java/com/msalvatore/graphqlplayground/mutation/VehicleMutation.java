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
        // TODO: Can we accept a LocalDate instead of a String?
        return vehicleService.createVehicle(type, modelCode, brandName, launchDate);
    }
}
