#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.resource;

import ${package}.domain.User;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.nutcore.nut.correlationid.CorrelationIdClientRequestFilter;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api("Hello")
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource
{

    private Logger logger = LoggerFactory.getLogger(HelloResource.class);

    @Inject
    OObjectDatabaseTx db;

    @GET
    @Path("/{user}")
    public User get(@PathParam("user") String username)
    {
        User user = new User();
        user.setName(username);
        User result = db.save(user);
        db.commit();
        Client client = ClientBuilder
                .newClient()
                .register(new CorrelationIdClientRequestFilter());

        logger.info("The correlation ID in this line must be equals to the next line");
        client.target("http://localhost:8080/api/")
                .path("hello")
                .path(username)
                .path("correlationId")
                .request(MediaType.APPLICATION_JSON)
                .get();

        return result;
    }

    @GET
    @Path("/{user}/correlationId")
    public void correlationIdTest(@PathParam("user") String username)
    {
        logger.info("The correlation ID in this line must be equals to the previous line");
    }

    @GET
    public List<User> getAll()
    {
        return db.command(new OSQLSynchQuery<User>("select * from User")).execute();
    }


}
