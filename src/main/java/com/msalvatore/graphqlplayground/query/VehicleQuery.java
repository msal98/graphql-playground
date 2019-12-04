package com.msalvatore.graphqlplayground.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.msalvatore.graphqlplayground.entity.Vehicle;
import com.msalvatore.graphqlplayground.service.VehicleService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleQuery implements GraphQLQueryResolver {

    private final VehicleService vehicleService;

    public VehicleQuery(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public List<Vehicle> getVehicles(int count) {
        return vehicleService.getAllVehicles(count);
    }
    public Vehicle getVehicle(int id) {
        return vehicleService.getVehicle(id);
    }
}
