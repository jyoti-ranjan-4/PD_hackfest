//package io.qiot.manufacturing.datacenter.plantmanager.service.machinery;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.event.Event;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//
//import org.eclipse.microprofile.reactive.messaging.Incoming;
//import org.slf4j.Logger;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.qiot.manufacturing.datacenter.commons.domain.telemetry.production.StageProductionValidationTelemetryDTO;
//
///**
// * @author andreabattaglia
// *
// */
//@ApplicationScoped
//public class MachinerySubscriptionStreamConsumer {
//
//    @Inject
//    Logger LOGGER;
//
//    @Inject
//    ObjectMapper MAPPER;
//
//    @Inject
//    Event<StageProductionValidationTelemetryDTO> measurementReceivedEvent;
//
//    @Transactional
//    @Incoming("machinerysubscription")
//    public void process(StageProductionValidationTelemetryDTO telemetry) {
//        LOGGER.info("Consumed message from the Production Telemetry stream: {}",
//                telemetry);
//        measurementReceivedEvent.fire(telemetry);
//    }
//
//}
