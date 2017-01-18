package intf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pagination.PaginatedListWrapper;
import data.Person;

@Stateless
@ApplicationPath("/resources")
@Path( "player" )
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PlayerRESTResourceProxy extends Serializable {
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper<Person> listPersons(@DefaultValue("1")
                                                    @QueryParam("page")
                                                    Integer page,
                                                    @DefaultValue("id")
                                                    @QueryParam("sortFields")
                                                    String sortFields,
                                                    @DefaultValue("asc")
                                                    @QueryParam("sortDirections")
                                                    String sortDirections);
    
    @GET
    @Path("{id}")
    public Person getPerson( @PathParam("id") Long id);
    
    @POST
    public Person savePerson(Person person);
    
    @DELETE
    @Path("{id}")
    public void deletePerson(@PathParam("id") Long id);

}