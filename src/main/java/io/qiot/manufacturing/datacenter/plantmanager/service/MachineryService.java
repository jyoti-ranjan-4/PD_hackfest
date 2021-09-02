package io.qiot.manufacturing.datacenter.plantmanager.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import io.qiot.manufacturing.all.commons.domain.landscape.MachineryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.all.commons.domain.registration.MachineryRegisterRequest;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.MachineryBean;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.FactoryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.MachineryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.registration.RegistrationServiceClient;
import io.qiot.manufacturing.datacenter.plantmanager.util.converter.MachineryConverter;

@ApplicationScoped
public class MachineryService {

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

    public SubscriptionResponse subscribe(MachineryRegisterRequest request) {
        MachineryBean machineryBean = new MachineryBean();
        machineryBean.serial = request.serial;
        machineryBean.name = request.name;
        machineryBean.factory=factoryRepository.findById(request.factoryId);
        
        
        //TODO: implement the call to the Registration Service
        SubscriptionResponse response=new SubscriptionResponse();
        response.id=machineryBean.id;
        return response;
    }

    public MachineryDTO getById(UUID id) {
        MachineryBean machineryBean = machineryRepository.findById(id);
        return converter.sourceToDest(machineryBean);
    }

    public List<MachineryDTO> getAllStations() {
        List<MachineryDTO> machineryDTOs = null;
        List<MachineryBean> machineryBeans = machineryRepository.findAll().list();
        machineryDTOs = converter.allSourceToDest(machineryBeans);
        return machineryDTOs;
    }
}
