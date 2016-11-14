#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.resource.HelloResource;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import com.nutcore.nut.httputils.CORSFilter;

public class MyApplication extends Application
{

    public MyApplication()
    {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("${package}.resource");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(HelloResource.class);
        classes.add(CORSFilter.class);
        classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        return classes;
    }
}