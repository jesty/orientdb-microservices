package com.nutcore.nut.metrics;

import com.codahale.metrics.Timer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class TimedInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    private final Timer timer;

    private static final ThreadLocal<Timer.Context> threadLocal = new ThreadLocal<>();


    public TimedInterceptor(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        threadLocal.set(timer.time());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        threadLocal.get().stop();
    }
}
