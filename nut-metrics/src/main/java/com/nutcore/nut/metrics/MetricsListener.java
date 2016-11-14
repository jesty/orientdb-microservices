package com.nutcore.nut.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by davidecerbo on 09/11/2016.
 */
public class MetricsListener implements Filter
{

    public static final String METRICS_LISTENER = "MetricsListener";

    @Inject
    private MetricRegistry metricRegistry;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        ServletContext context = filterConfig.getServletContext();
        context.setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy()
    {

    }
}
