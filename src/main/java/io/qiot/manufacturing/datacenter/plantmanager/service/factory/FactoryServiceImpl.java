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
import io.qiot.manufacturing.datacenter.commons.domain.registration.FactoryRegisterRequest;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.FactoryBean;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.FactoryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.service.registration.RegistrationServiceClient;
import io.qiot.manufacturing.datacenter.plantmanager.util.converter.FactoryConverter;

@ApplicationScoped
class FactoryServiceImpl implements FactoryService {

    @Inject
    Logger LOGGER;

    @Inject
    FactoryRepository repository;

    @Inject
    FactoryConverter converter;

    @Inject
    @RestClient
    RegistrationServiceClient registrationServiceClient;

    @Override
    public SubscriptionResponse subscribe(FactoryRegisterRequest request) {
        /*
         * persist & flush the new entity (and get the generated ID)
         */
        FactoryBean factoryBean = new FactoryBean();
        factoryBean.serial = request.serial;
        factoryBean.name = request.name;

        repository.persistAndFlush(factoryBean);

        /*
         * Get certificates
         */
        FactoryCertificateRequest certificateRequest = new FactoryCertificateRequest();
        certificateRequest.factoryId = factoryBean.id;
        certificateRequest.serial = request.serial;
        certificateRequest.name = request.name;
        certificateRequest.keyStorePassword = request.keyStorePassword;
        try {
            CertificateResponse certificateResponse = registrationServiceClient
                    .subscribeFactory(certificateRequest);

            /*
             * Return generated content
             */
            SubscriptionResponse response = new SubscriptionResponse();
            response.id = factoryBean.id;
            response.keystore = certificateResponse.keystore;
            response.truststore = certificateResponse.truststore;
            return response;
        } catch (Exception e) {
            repository.delete(factoryBean);
            // TODO: improve exception handling
            throw new RuntimeException(e);
        }
    }

    @Override
    public FactoryDTO getById(UUID id) {
        FactoryBean factoryBean = repository.findById(id);
        return converter.sourceToDest(factoryBean);
    }

    @Override
    public List<FactoryDTO> getAllStations() {
        List<FactoryDTO> factoryDTOs = null;
        List<FactoryBean> factoryBeans = repository.findAll().list();
        factoryDTOs = converter.allSourceToDest(factoryBeans);
        return factoryDTOs;
    }
}
