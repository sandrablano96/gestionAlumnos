/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Restfull;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Alumnos;
import modelo.Crud;

/**
 * REST Web Service
 *
 * @author Sandra
 */
@Path("generico")
public class GenericoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericoResource
     */
    public GenericoResource() {
    }
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }


    @GET
    @Path("/alumnos/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumnos> getAlumnos(){
       List<Alumnos> misProductos = Crud.getAlumnos();
       return misProductos;
    }

    @GET
    @Path("/alumno/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Alumnos getAlumno(@PathParam("id") int id){
       Alumnos alumno = Crud.getAlumno(id);
       return alumno;
    }

    @PUT
    @Path("/alumno/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Alumnos updateAlumno(Alumnos alumno){
       Crud.actualizarAlumno(alumno);
       return alumno;
    }

    @POST
    @Path("/alumno/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void setAlumno(Alumnos alumno) {
        Crud.insertaAlumno(alumno);
            }

     @DELETE
     @Path("/alumno/{id}")
     public void borraAlumno(@PathParam("id") int id){
        Crud.borrarAlumno(id);
     }


    /**
     * Retrieves representation of an instance of controlador.GenericoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericoResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
