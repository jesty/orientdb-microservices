package com.nutcore.nut.correlationid;

import javax.enterprise.inject.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by davidecerbo on 14/11/2016.
 */
public class ClientProducer
{
    @Produces
    public Client newClient()
    {
        return ClientBuilder
                .newClient()
                .register(new CorrelationIdClientRequestFilter());
    }
}
