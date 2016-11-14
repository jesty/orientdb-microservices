package com.nutcore.nut.metrics;

import com.codahale.metrics.MetricRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


/**
 * Created by davidecerbo on 05/11/2016.
 */
public class MetricRegistryProducer
{
    @Produces
    @ApplicationScoped
    public MetricRegistry getMetricRegistry() {
        return new MetricRegistry();
    }

}
