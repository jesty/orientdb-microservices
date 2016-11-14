package com.nutcore.nut.correlationid;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by davidecerbo on 14/11/2016.
 */
public class CorrelationIdClientRequestFilter implements ClientRequestFilter
{

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException
    {
        CorrelationId correlationId = CorrelationIdUtil.getId();
        if(correlationId != null){
            MultivaluedMap<String, Object> headers = requestContext.getHeaders();
            headers.put(CorrelationIdFilter.CORRELATION_ID_HEADER, Arrays.asList(correlationId.getId()));
            headers.put(CorrelationIdFilter.CORRELATION_ID_SOURCE_HEADER, Arrays.asList(correlationId.getSource()));
            headers.put(CorrelationIdFilter.CORRELATION_ID_TIME_HEADER, Arrays.asList(correlationId.getCreationTime()));
        }
    }

}
