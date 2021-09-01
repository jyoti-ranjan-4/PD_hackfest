package io.qiot.manufacturing.datacenter.plantmanager.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.datacenter.plantmanager.domain.dto.MachineryDTO;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.MachineryBean;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.MachineryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.util.converter.MachineryConverter;

@ApplicationScoped
public class MachineryService {

    @Inject
    Logger LOGGER;

    // @Inject
    // GeometryFactory gfactory;

    @Inject
    MachineryRepository repository;

    @Inject
    MachineryConverter converter;

    public String add(String serial, String name, UUID factoryId) {
        MachineryBean machineryBean = new MachineryBean();
        machineryBean.serial = serial;
        machineryBean.name = name;
        

        repository.persist(machineryBean);
        return machineryBean.id.toString();
    }

    public MachineryDTO getById(UUID id) {
        MachineryBean machineryBean = repository.findById(id);
        return converter.sourceToDest(machineryBean);
    }

    public List<MachineryDTO> getAllStations() {
        List<MachineryDTO> machineryDTOs = null;
        List<MachineryBean> machineryBeans = repository.findAll().list();
        machineryDTOs = converter.allSourceToDest(machineryBeans);
        return machineryDTOs;
    }
}
