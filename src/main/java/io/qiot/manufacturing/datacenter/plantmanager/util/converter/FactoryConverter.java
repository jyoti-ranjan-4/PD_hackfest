package io.qiot.manufacturing.datacenter.plantmanager.util.converter;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.all.commons.domain.landscape.FactoryDTO;
import io.qiot.manufacturing.all.commons.util.converter.DataObjectConverter;
import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.FactoryBean;

@ApplicationScoped
public class FactoryConverter
        implements DataObjectConverter<FactoryBean, FactoryDTO> {

    @Override
    public FactoryDTO sourceToDest(FactoryBean src) {
        FactoryDTO dto = new FactoryDTO();
        dto.id = src.id;
        dto.serial = src.serial;
        dto.name = src.name;
        dto.registeredOn = src.registeredOn;
        return dto;
    }

    @Override
    public FactoryBean destToSource(FactoryDTO dest) {
        FactoryBean src = new FactoryBean();
        src.id = dest.id;
        src.serial = dest.serial;
        src.name = dest.name;
        src.registeredOn = src.registeredOn;
        return src;
    }

    @Override
    public List<FactoryDTO> allSourceToDest(List<FactoryBean> srcs) {
        List<FactoryDTO> factoryDTOs = new ArrayList<>(srcs.size());
        for (FactoryBean factoryBean : srcs)
            factoryDTOs.add(sourceToDest(factoryBean));
        return factoryDTOs;
    }

    @Override
    public List<FactoryBean> allDestToSource(List<FactoryDTO> dests) {
        // TODO Auto-generated method stub
        return null;
    }
}
