package io.qiot.manufacturing.datacenter.plantmanager.rest;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.all.commons.domain.landscape.FactoryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.datacenter.commons.domain.subscription.FactorySubscriptionRequest;
import io.qiot.manufacturing.datacenter.plantmanager.service.factory.FactoryService;

/**
 * Validation through hibernate validator:
 * https://quarkus.io/guides/validation#rest-end-point-validation
 * 
 * @author andreabattaglia
 *
 */
@Path("/factory")
public class FactoryResource {

    @Inject
    Logger LOGGER;

    @Inject
    ObjectMapper MAPPER;

    @Inject
    FactoryService service;

    @GET
    @Path("/all")
    public List<FactoryDTO> getAll() {
        return service.getAllStations();
    }

    @GET
    @Path("/id/{id}")
    public FactoryDTO getById(@PathParam("id") @NotNull UUID id) {
        return service.getById(id);
    }

    @Transactional
    @POST
    public SubscriptionResponse subscribe(
            @Valid FactorySubscriptionRequest request) throws Exception {
        LOGGER.info(
                "Received subscription request from factory \"{}\" with data \n{}",
                request.name, MAPPER.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(request));

        SubscriptionResponse response = service.subscribe(request);
        
        LOGGER.info("Sending response back to the caller: \n{}",
                MAPPER.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(response));

        return response;
    }

}