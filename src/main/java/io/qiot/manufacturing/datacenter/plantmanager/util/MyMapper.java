package io.qiot.manufacturing.datacenter.plantmanager.util;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyMapper implements ExceptionMapper<ClientErrorException> {

    @Override
    public Response toResponse(ClientErrorException e) {
        return Response.fromResponse(e.getResponse()).entity(e.getMessage()).build();
    }
}