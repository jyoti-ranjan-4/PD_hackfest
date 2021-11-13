//package io.qiot.manufacturing.datacenter.plantmanager.rest;
//
//import java.util.List;
//import java.util.UUID;
//
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import org.slf4j.Logger;
//
//import io.qiot.manufacturing.all.commons.domain.landscape.MachineryDTO;
//import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
//import io.qiot.manufacturing.datacenter.commons.domain.subscription.MachinerySubscriptionRequest;
//import io.qiot.manufacturing.datacenter.plantmanager.service.machinery.MachineryService;
//
///**
// * Validation through hibernate validator:
// * https://quarkus.io/guides/validation#rest-end-point-validation
// * 
// * @author andreabattaglia
// *
// */
//@Path("/machinery")
//public class MachineryResource {
//
//    @Inject
//    Logger LOGGER;
//
//    @Inject
//    MachineryService service;
//
//    @GET
//    @Path("/all")
//    public List<MachineryDTO> getAll() {
//        return service.getAllStations();
//    }
//
//    @GET
//    @Path("/id/{id}")
//    public MachineryDTO getById(@PathParam("id") @NotNull UUID id) {
//        return service.getById(id);
//    }
//
//    @Transactional
//    @POST
//    public SubscriptionResponse subscribe(
//            @Valid MachinerySubscriptionRequest request) {
//            LOGGER.info("Received new Machinery subscription request: {}", request);
//
//        SubscriptionResponse response = service.subscribe(request);
//
//        LOGGER.info("Subscription of the new machinery completed successfully: {}", response);
//
//        return response;
//    }
//
//}