package io.qiot.manufacturing.datacenter.plantmanager.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.all.commons.domain.landscape.MachineryDTO;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.MachineryBean;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.FactoryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.MachineryRepository;
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

    public UUID add(String serial, String name, UUID factoryId) {
        MachineryBean machineryBean = new MachineryBean();
        machineryBean.serial = serial;
        machineryBean.name = name;
        machineryBean.factory=factoryRepository.findById(factoryId);

        machineryRepository.persist(machineryBean);
        return machineryBean.id;
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
