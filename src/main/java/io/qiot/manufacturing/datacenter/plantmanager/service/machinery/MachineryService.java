package io.qiot.manufacturing.datacenter.plantmanager.service.machinery;

import java.util.List;
import java.util.UUID;

import io.qiot.manufacturing.all.commons.domain.landscape.MachineryDTO;
import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.datacenter.commons.domain.subscription.MachinerySubscriptionRequest;

public interface MachineryService {

//    SubscriptionResponse subscribe(MachinerySubscriptionRequest request);

    MachineryDTO getById(UUID id);

    List<MachineryDTO> getAllStations();

}