package com.nutcore.nut.metrics;

import com.codahale.metrics.Meter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

public class MeterInterceptor implements ContainerRequestFilter {

    private final Meter meter;

    public MeterInterceptor(Meter meter) {
        this.meter = meter;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(meter != null) meter.mark();
    }
}
