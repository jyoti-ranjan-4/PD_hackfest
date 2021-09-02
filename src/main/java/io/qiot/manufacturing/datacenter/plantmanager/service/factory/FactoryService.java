package io.qiot.manufacturing.datacenter.plantmanager.service.factory;

import java.util.List;
import java.util.UUID;

import io.qiot.manufacturing.all.commons.domain.landscape.FactoryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.datacenter.commons.domain.registration.FactoryRegisterRequest;

public interface FactoryService {

    SubscriptionResponse subscribe(FactoryRegisterRequest request);

    FactoryDTO getById(UUID id);

    List<FactoryDTO> getAllStations();

}