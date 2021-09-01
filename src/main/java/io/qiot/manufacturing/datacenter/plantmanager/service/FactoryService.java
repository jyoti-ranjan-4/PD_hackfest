package io.qiot.manufacturing.datacenter.plantmanager.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.all.commons.domain.landscape.FactoryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.FactoryBean;
import io.qiot.manufacturing.datacenter.plantmanager.persistence.FactoryRepository;
import io.qiot.manufacturing.datacenter.plantmanager.util.converter.FactoryConverter;

@ApplicationScoped
public class FactoryService {

    @Inject
    Logger LOGGER;

    // @Inject
    // GeometryFactory gfactory;

    @Inject
    FactoryRepository repository;

    @Inject
    FactoryConverter converter;

    public SubscriptionResponse subscribe(String serial, String name) {
        FactoryBean factoryBean = new FactoryBean();
        factoryBean.serial = serial;
        factoryBean.name = name;

        repository.persist(factoryBean);

        //TODO: implement the call to the Registration Service
        SubscriptionResponse response=new SubscriptionResponse();
        response.id=factoryBean.id;
        return response;
    }

    public FactoryDTO getById(UUID id) {
        FactoryBean factoryBean = repository.findById(id);
        return converter.sourceToDest(factoryBean);
    }

    public List<FactoryDTO> getAllStations() {
        List<FactoryDTO> factoryDTOs = null;
        List<FactoryBean> factoryBeans = repository.findAll().list();
        factoryDTOs = converter.allSourceToDest(factoryBeans);
        return factoryDTOs;
    }
}
