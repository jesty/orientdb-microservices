#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.resource;

import ${package}.domain.User;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Api("Hello")
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource
{

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
        return result;
    }

    @GET
    public List<User> getAll()
    {
        return db.command(new OSQLSynchQuery<User>("select * from User")).execute();
    }


}
