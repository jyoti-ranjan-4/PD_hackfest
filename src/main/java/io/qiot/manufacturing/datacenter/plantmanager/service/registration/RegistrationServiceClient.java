package io.qiot.manufacturing.datacenter.plantmanager.service.registration;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.qiot.manufacturing.datacenter.commons.domain.registration.CertificateResponse;
import io.qiot.manufacturing.datacenter.commons.domain.registration.FactoryCertificateRequest;
import io.qiot.manufacturing.datacenter.commons.domain.registration.MachineryCertificateRequest;

@Path("/v1/register")
@RegisterRestClient(configKey = "registration-service-api")
public interface RegistrationServiceClient {

    @POST
    @Path("/factory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateResponse registerFactory(FactoryCertificateRequest data);

    @POST
    @Path("/machinery")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateResponse registerMachinery(
             MachineryCertificateRequest data);

}