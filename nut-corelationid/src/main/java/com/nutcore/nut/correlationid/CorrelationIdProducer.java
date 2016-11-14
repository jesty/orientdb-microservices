package com.nutcore.nut.correlationid;

import javax.enterprise.inject.Produces;


/**
 * Created by davidecerbo on 05/11/2016.
 */
public class CorrelationIdProducer
{
    @Produces
    public CorrelationId getCorrelationId() {
        return CorrelationIdUtil.getId();
    }

}
