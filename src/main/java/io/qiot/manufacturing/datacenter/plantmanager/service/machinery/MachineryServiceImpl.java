package io.qiot.manufacturing.datacenter.plantmanager.service.machinery;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import io.qiot.manufacturing.all.commons.domain.landscape.MachineryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.datacenter.commons.domain.registration.CertificateResponse;
import io.qiot.manufacturing.datacenter.commons.domain.registration.MachineryCertificateRequest;
import io.qiot.manufacturing.datacenter.commons.domain.registration.MachinerySubscriptionRequest;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.MachineryBean;
import io.qiot.manufacturing.datacenter.plantmanager.exception.SubscriptionException;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.FactoryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.MachineryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.service.registration.RegistrationServiceClient;
import io.qiot.manufacturing.datacenter.plantmanager.util.converter.MachineryConverter;

@ApplicationScoped
class MachineryServiceImpl implements MachineryService {

    @Inject
    Logger LOGGER;

    @Inject
    FactoryRepository factoryRepository;

    @Inject
    MachineryRepository machineryRepository;

    @Inject
    MachineryConverter converter;

    @Inject
    @RestClient
    RegistrationServiceClient registrationServiceClient;

    @Override
    public SubscriptionResponse subscribe(MachinerySubscriptionRequest request) {
        MachineryBean machineryBean = new MachineryBean();
        machineryBean.serial = request.serial;
        machineryBean.name = request.name;
        machineryBean.factory=factoryRepository.findById(request.factoryId);
        
        machineryRepository.persistAndFlush(machineryBean);

        LOGGER.debug("Factory entity persisted, machinery ID assigned: {}",
                machineryBean.id);

        /*
         * Get certificates
         */
        MachineryCertificateRequest certificateRequest = new MachineryCertificateRequest();
        certificateRequest.factoryId = request.factoryId;
        certificateRequest.machineryId = machineryBean.id;
        certificateRequest.serial = request.serial;
        certificateRequest.name = request.name;
        certificateRequest.keyStorePassword = request.keyStorePassword;
        try {
            CertificateResponse certificateResponse = registrationServiceClient
                    .registerMachinery(certificateRequest);

            LOGGER.debug("Certificates for the new Machinery created:"//
                    + "\nKEYSTORE:\n{}"//
                    + "\n"//
                    + "\nTRUSTSTORE:\n{}", //
                    certificateResponse.keystore,
                    certificateResponse.truststore);

            /*
             * Return generated content
             */
            SubscriptionResponse response = new SubscriptionResponse();
            response.id = machineryBean.id;
            response.keystore = certificateResponse.keystore;
            response.truststore = certificateResponse.truststore;
            return response;
        } catch (Exception e) {
            machineryRepository.delete(machineryBean);
            LOGGER.error(
                    "An error occurred retrieving the certificates for the factory.",
                    e);
            // TODO: improve exception handling
            throw new SubscriptionException(e);
        }
    }

    @Override
    public MachineryDTO getById(UUID id) {
        MachineryBean machineryBean = machineryRepository.findById(id);
        return converter.sourceToDest(machineryBean);
    }

    @Override
    public List<MachineryDTO> getAllStations() {
        List<MachineryDTO> machineryDTOs = null;
        List<MachineryBean> machineryBeans = machineryRepository.findAll().list();
        machineryDTOs = converter.allSourceToDest(machineryBeans);
        return machineryDTOs;
    }
}
