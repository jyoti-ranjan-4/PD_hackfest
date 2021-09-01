package io.qiot.manufacturing.datacenter.plantmanager.persistence;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.datacenter.plantmanager.domain.pojo.FactoryBean;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class FactoryRepository
        implements PanacheRepositoryBase<FactoryBean, UUID> {

    @Inject
    Logger LOGGER;

}
