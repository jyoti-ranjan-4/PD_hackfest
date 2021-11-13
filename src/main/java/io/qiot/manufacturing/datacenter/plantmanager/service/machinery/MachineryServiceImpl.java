package io.qiot.manufacturing.datacenter.plantmanager.service.machinery;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.all.commons.domain.landscape.MachineryDTO;
import io.qiot.manufacturing.datacenter.commons.domain.subscription.MachinerySubscriptionRequest;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.MachineryBean;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.FactoryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.MachineryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.util.converter.MachineryConverter;
import io.qiot.ubi.all.registration.client.RegistrationServiceClient;
import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
class MachineryServiceImpl implements MachineryService {

    @Inject
    Logger LOGGER;
    
    @Inject
    ObjectMapper MAPPER;

    @Inject
    FactoryRepository factoryRepository;

    @Inject
    MachineryRepository machineryRepository;

    @Inject
    MachineryConverter converter;

    @Inject
    @RestClient
    RegistrationServiceClient registrationServiceClient;

//    @Override
//    public SubscriptionResponse subscribe(
//            MachinerySubscriptionRequest request) {
//        MachineryBean machineryBean = new MachineryBean();
//        machineryBean.serial = request.serial;
//        machineryBean.name = request.name;
//        machineryBean.factory = factoryRepository.findById(request.factoryId);
//
//        machineryRepository.persist(machineryBean);
//
//        LOGGER.debug("Factory entity persisted, machinery ID assigned: {}",
//                machineryBean.id);
//
////        /*
////         * Get certificates
////         */
////        CertificateRequest certificateRequest = new CertificateRequest();
////        certificateRequest.factoryId = request.factoryId;
////        certificateRequest.machineryId = machineryBean.id;
////        certificateRequest.serial = request.serial;
////        certificateRequest.name = request.name;
////        certificateRequest.keyStorePassword = request.keyStorePassword;
////        try {
////            CertificateResponse certificateResponse = registrationServiceClient
////                    .provisionCertificate(certificateRequest);
////
////            LOGGER.debug("Certificates for the new Machinery created:"//
////                    + "\nKEYSTORE:\n{}"//
////                    + "\n"//
////                    + "\nTRUSTSTORE:\n{}", //
////                    certificateResponse.keystore,
////                    certificateResponse.truststore);
//
//            /*
//             * Return generated content
//             */
//            SubscriptionResponse response = new SubscriptionResponse();
//            response.id = machineryBean.id;
////            response.keystore = certificateResponse.keystore;
////            response.truststore = certificateResponse.truststore;
//            response.subscribedOn = machineryBean.registeredOn;
//            return response;
////        } catch (Exception e) {
////            machineryRepository.delete(machineryBean);
////            LOGGER.error(
////                    "An error occurred retrieving the certificates for the factory.",
////                    e);
////            // TODO: improve exception handling
////            throw new SubscriptionException(e);
////        }
//    }
    


    @Incoming("machinerysubscription")
    @Blocking
    @Transactional
    public void process(MachinerySubscriptionRequest request) throws JsonProcessingException {
        LOGGER.info("Consumed message from the Machinery Subscription stream: {}",
                MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(request));
      MachineryBean machineryBean = new MachineryBean();
      machineryBean.id=request.id;
      machineryBean.serial = request.serial;
      machineryBean.name = request.name;
      machineryBean.registeredOn=request.subscribedOn;
      machineryBean.factory = factoryRepository.findById(request.factoryId);

      machineryRepository.persist(machineryBean);
    }

    @Override
    public MachineryDTO getById(UUID id) {
        MachineryBean machineryBean = machineryRepository.findById(id);
        return converter.sourceToDest(machineryBean);
    }

    @Override
    public List<MachineryDTO> getAllStations() {
        List<MachineryDTO> machineryDTOs = null;
        List<MachineryBean> machineryBeans = machineryRepository.findAll()
                .list();
        machineryDTOs = converter.allSourceToDest(machineryBeans);
        return machineryDTOs;
    }
}
