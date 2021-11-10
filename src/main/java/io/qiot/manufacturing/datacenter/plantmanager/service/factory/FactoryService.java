package io.qiot.manufacturing.datacenter.plantmanager.service.factory;

import java.util.List;
import java.util.UUID;

import io.qiot.manufacturing.all.commons.domain.landscape.FactoryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.datacenter.commons.domain.subscription.FactorySubscriptionRequest;

/**
 * @author andreabattaglia
 *
 */
public interface FactoryService {

    SubscriptionResponse subscribe(FactorySubscriptionRequest request) throws Exception;

    FactoryDTO getById(UUID id);

    List<FactoryDTO> getAllStations();

}