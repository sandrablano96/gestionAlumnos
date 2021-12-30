/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Alumnos;
import modelo.Crud;

/**
 *
 * @author Sandra
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {
final int NUM_LINEAS_PAGINA = 5;
 int pagina=1;
 int offset=0;
 int num_paginas=0;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         List<Alumnos> listado = Crud.getAlumnos();
        String operacion = request.getParameter("op");

        /**
         * LISTAR
         */
        switch (operacion) {
            case "listar":
                listar(request, response);
                /**
                 * INSERTAR DATOS
                 */     break;
            case "insertar":
                request.setAttribute("operacion", "insertarDatos");
                request.getRequestDispatcher("actualizar.jsp").forward(request, response);
                break;
            case "insertarDatos":
                
                    Alumnos alum = new Alumnos();
                    alum.setNombre(request.getParameter("nombre"));
                    alum.setApellido(request.getParameter("apellido"));
                    alum.setAsignatura(request.getParameter("asignatura"));
                    Crud.insertaAlumno(alum);
                    
                    listar(request, response);
                    break;
                
            case "borrar":
                
                    int id = Integer.parseInt(request.getParameter("id"));
                    if (Crud.borrarAlumno(id) != 0) { 
                        request.setAttribute("mensaje", "Alumno con id " + id + " borrado");
                       
                    } else {
                        request.setAttribute("mensaje", "No se ha podido eliminar");
                       
                    }
                    listar(request,response);
                   
                    /**
                     * ACTUALIZAR
                     */     break;
                
            case "actualizar":
                
                    id = Integer.parseInt(request.getParameter("id"));
                    alum = Crud.getAlumno(id);
                    request.setAttribute("operacion", "actualizarDatos");
                    request.setAttribute("alumno", alum);
                    request.getRequestDispatcher("actualizar.jsp").forward(request, response);
                    break;
                 /**
                 * ACTUALIZAR DATOS
                 */
            case "actualizarDatos":
                
                    id = Integer.parseInt(request.getParameter("id"));
                    String nombre = request.getParameter("nombre");
                    String apellido = request.getParameter("apellido");
                    String asignatura = request.getParameter("asignatura");
                    alum = new Alumnos(id, nombre, apellido, asignatura);
                    if (Crud.actualizarAlumno(alum) != 0) {
                        request.setAttribute("mensaje", "Alumno con id " + id + " actualizado");
                        request.setAttribute("alumno", alum);
                        request.getRequestDispatcher("actualizar.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensaje", "No se ha podido actualizar");
                        request.getRequestDispatcher("actualizar.jsp").forward(request, response);
                    }       break;
                

        }
        
    }
    
     protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                     
            List<Alumnos> listaAlumnos = Crud.getAlumnos();
            /* cálculos para la paginación */

            
            if ( request.getParameter("pagina")!=null){
                pagina = Integer.parseInt(request.getParameter("pagina"));
                offset = ( pagina-1 ) * NUM_LINEAS_PAGINA;
            }
            num_paginas = ( int ) Math.ceil(listaAlumnos.size() / ( double ) NUM_LINEAS_PAGINA);
            listaAlumnos = Crud.getAlumnosPaginado(offset, NUM_LINEAS_PAGINA);
            
            request.setAttribute("alumnos", listaAlumnos);
            request.setAttribute("pagina", pagina);
            request.setAttribute("num_paginas", String.valueOf(num_paginas));
 
            request.getRequestDispatcher("verAlumnos.jsp").forward(request,response);
         
     }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
