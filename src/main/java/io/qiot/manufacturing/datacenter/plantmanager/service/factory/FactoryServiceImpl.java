package io.qiot.manufacturing.datacenter.plantmanager.service.factory;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import io.qiot.manufacturing.all.commons.domain.landscape.FactoryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.datacenter.commons.domain.registration.CertificateResponse;
import io.qiot.manufacturing.datacenter.commons.domain.registration.FactoryCertificateRequest;
import io.qiot.manufacturing.datacenter.commons.domain.registration.FactorySubscriptionRequest;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.FactoryBean;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.FactoryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.service.registration.RegistrationServiceClient;
import io.qiot.manufacturing.datacenter.plantmanager.util.converter.FactoryConverter;

@ApplicationScoped
class FactoryServiceImpl implements FactoryService {

    @Inject
    Logger LOGGER;

    @Inject
    FactoryRepository factoryRepository;

    @Inject
    FactoryConverter converter;

    @Inject
    @RestClient
    RegistrationServiceClient registrationServiceClient;

    @Override
    public SubscriptionResponse subscribe(FactorySubscriptionRequest request) {
        /*
         * persist & flush the new entity (and get the generated ID)
         */
        FactoryBean factoryBean = new FactoryBean();
        factoryBean.serial = request.serial;
        factoryBean.name = request.name;

        factoryRepository.persistAndFlush(factoryBean);

        LOGGER.debug("Factory entity persisted, factory ID assigned: {}",
                factoryBean.id);

        /*
         * Get certificates
         */
        FactoryCertificateRequest certificateRequest = new FactoryCertificateRequest();
        certificateRequest.factoryId = factoryBean.id;
        certificateRequest.serial = request.serial;
        certificateRequest.name = request.name;
        certificateRequest.keyStorePassword = request.keyStorePassword;

        CertificateResponse certificateResponse = null;

        while (certificateResponse == null) {
            // TODO: put sleep time in application.properties
            long sleepTime = 2000;
            try {

                certificateResponse = registrationServiceClient
                        .registerFactory(certificateRequest);
            } catch (Exception e) {
                // TODO: improve exception handling
                LOGGER.info(
                        "An error occurred registering the factory. "
                                + "Retrying in {} millis.\n Error message: {}",
                        sleepTime, e.getMessage());
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        LOGGER.debug("Certificates for the new Factory created:"//
                + "\nKEYSTORE:\n{}"//
                + "\n"//
                + "\nTRUSTSTORE:\n{}", //
                certificateResponse.keystore, certificateResponse.truststore);

        /*
         * Return generated content
         */
        SubscriptionResponse response = new SubscriptionResponse();
        response.id = factoryBean.id;
        response.keystore = certificateResponse.keystore;
        response.truststore = certificateResponse.truststore;
        response.subscribedOn=factoryBean.registeredOn;
        return response;
    }

    @Override
    public FactoryDTO getById(UUID id) {
        FactoryBean factoryBean = factoryRepository.findById(id);
        return converter.sourceToDest(factoryBean);
    }

    @Override
    public List<FactoryDTO> getAllStations() {
        List<FactoryDTO> factoryDTOs = null;
        List<FactoryBean> factoryBeans = factoryRepository.findAll().list();
        factoryDTOs = converter.allSourceToDest(factoryBeans);
        return factoryDTOs;
    }
}
