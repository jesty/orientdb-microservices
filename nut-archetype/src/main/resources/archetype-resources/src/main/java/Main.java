#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.nutcore.nut.correlationid.CorrelationIdFilter;
import com.nutcore.nut.metrics.MetricsListener;
import com.nutcore.orientdb.javaee.orientdb.OrientDBFilter;
import com.nutcore.orientdb.javaee.orientdb.OrientDBJacksonProvider;
import com.nutcore.orientdb.javaee.orientdb.OrientDBServletContextListener;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;

import javax.servlet.DispatcherType;
import java.util.Arrays;

import static com.nutcore.nut.correlationid.CorrelationIdFilter.CORRELATION_ID_FILTER;
import static com.nutcore.nut.metrics.MetricsListener.METRICS_LISTENER;

public class Main
{
    private static final String ORIENTDB_FILTER = "orientdb";

    public static void main(String[] args) throws Exception
    {
        startWebServer(args);
    }

    private static void startWebServer(String[] args)
    {
        String host = "0.0.0.0";
        int port = 8080;
        if (args.length > 0)
        {
            host = args[0];
        }
        if (args.length > 1)
        {
            port = Integer.parseInt(args[1]);
        }

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setProviderClasses(Arrays.asList(OrientDBJacksonProvider.class.getName()));
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
        deployment.setApplicationClass(MyApplication.class.getName());

        Undertow.Builder builder = Undertow
                .builder()
                .addHttpListener(port, host);

        UndertowJaxrsServer server = new UndertowJaxrsServer().start(builder);
        DeploymentInfo deploymentInfo = server
                .undertowDeployment(deployment)
                .setClassLoader(Main.class.getClassLoader())
                .addListener(Servlets.listener(OrientDBServletContextListener.class))
                .addFilter(Servlets.filter(METRICS_LISTENER, MetricsListener.class))
                .addFilterUrlMapping(METRICS_LISTENER, "/*", DispatcherType.REQUEST)
                .addListeners(Servlets.listener(Listener.class))
                .addServlets(
                        Servlets.servlet(com.codahale.metrics.servlets.MetricsServlet.class)
                                .addMapping("/metrics"))
                .addFilter(new FilterInfo(ORIENTDB_FILTER, OrientDBFilter.class))
                .addFilterUrlMapping(ORIENTDB_FILTER, "/*", DispatcherType.REQUEST)
                .addFilter(new FilterInfo(CORRELATION_ID_FILTER, CorrelationIdFilter.class))
                .addFilterUrlMapping(CORRELATION_ID_FILTER, "/*", DispatcherType.REQUEST)
                .setContextPath("/api")
                .setDeploymentName("${artifactId}");
                        
        server.deploy(deploymentInfo);

    }

}
