package com.nutcore.nut.correlationid;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by davidecerbo on 07/11/2016.
 */
public class CorrelationIdFilter implements Filter
{

    public static final String CORRELATION_ID_FILTER = "correlationIDFilter";

    public static final int CORRELLATION_ID_MAX_LENGTH = 36;

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static final String CORRELATION_ID_SOURCE_HEADER = "X-Correlation-Id-source";

    public static final String CORRELATION_ID_TIME_HEADER = "X-Correlation-Id-time";

    private static final String CORELLATION_ID_LOG_KEY = "correlationId";
    private static final String CORELLATION_ID_SOURCE_LOG_KEY = "correlationIdSource";
    private static final String CORELLATION_ID_TIME_LOG_KEY = "correlationIdTime";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String correlationId = httpServletRequest.getHeader(CORRELATION_ID_HEADER);
        String source = httpServletRequest.getHeader(CORRELATION_ID_SOURCE_HEADER);
        Long creationDate = getCreationTime(httpServletRequest);
        if (correlationId == null || correlationId.isEmpty())
        {
            correlationId = generateCorrelationID();
            source = getFullURL(httpServletRequest);
            creationDate = System.currentTimeMillis();
        }
        CorrelationId correlationIdObj = new CorrelationId(correlationId, source, creationDate);
        setCorellationId((HttpServletResponse) servletResponse, correlationIdObj);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    protected String generateCorrelationID()
    {
        return UUID.randomUUID().toString();
    }

    private void setCorellationId(HttpServletResponse servletResponse, CorrelationId correlationIdObj)
    {
        correlationIdObj = truncIfTooLong(correlationIdObj);
        setInHeader(servletResponse, correlationIdObj);
        setInLog(correlationIdObj);
        setInThreadLocal(correlationIdObj);
    }

    private void setInThreadLocal(CorrelationId correlationIdObj)
    {
        CorrelationIdUtil.setId(correlationIdObj);
    }

    private void setInHeader(HttpServletResponse servletResponse, CorrelationId correlationIdObj)
    {
        servletResponse.setHeader(CORRELATION_ID_HEADER, correlationIdObj.getId());
        servletResponse.setHeader(CORRELATION_ID_SOURCE_HEADER, correlationIdObj.getSource());
        servletResponse.setHeader(CORRELATION_ID_TIME_HEADER, correlationIdObj.getCreationTime().toString());
    }

    private void setInLog(CorrelationId correlationIdObj)
    {
        Map<String, String> map = new HashMap<>();
        map.put(CORELLATION_ID_LOG_KEY, correlationIdObj.getId());
        map.put(CORELLATION_ID_SOURCE_LOG_KEY, correlationIdObj.getSource());
        map.put(CORELLATION_ID_TIME_LOG_KEY, correlationIdObj.getCreationTime().toString());
        MDC.setContextMap(map);
    }

    private String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    private CorrelationId truncIfTooLong(CorrelationId correlationIdObj)
    {
        String correlationId = correlationIdObj.getId();
        if (correlationId.length() > CORRELLATION_ID_MAX_LENGTH)
        {
            return new CorrelationId(correlationId.substring(0, CORRELLATION_ID_MAX_LENGTH), correlationIdObj.getSource(), correlationIdObj.getCreationTime());
        }
        return correlationIdObj;
    }

    private long getCreationTime(HttpServletRequest httpServletRequest)
    {
        long time = 0;
        try
        {
            time = Long.parseLong(httpServletRequest.getHeader(CORRELATION_ID_TIME_HEADER));
        } catch (NumberFormatException e)
        {
            //log
        }
        return time;
    }

    @Override
    public void destroy()
    {

    }
}
