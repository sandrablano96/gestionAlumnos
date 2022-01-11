/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Alumnos;
import modelo.Crud;

/**
 *
 * @author Sandra
 */
@MultipartConfig( fileSizeThreshold=1024*1024*10, 
            maxFileSize=1024*1024*50, maxRequestSize=1024*1024*10)

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {
final int NUM_LINEAS_PAGINA = 5;
int pagina=1;
int offset=0;
int num_paginas=0;
String path = "";

@Override
public void init(ServletConfig config){
path = config.getServletContext().getRealPath("").
                        concat(File.separator).concat("ficheros");
}

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
                    alum.setAsignatura(request.getParameter("asignatura"));
                    String imagen = this.subirArchivo(request,response);
                    alum.setImagen(imagen);
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
                    imagen = this.subirArchivo(request,response);
                    alum = new Alumnos(id, nombre, apellido, asignatura, imagen);
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
String subirArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // path = request.getServletContext().getRealPath("").concat(File.separator).concat("ficheros");
    Part filePart = request.getPart("imagen"); // Obtiene el archivo el input en el form se llama imagen
    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

    //InputStream fileContent = filePart.getInputStream(); //Lo transforma en InputStream

    //String path="/archivos/";
    File uploads = new File(path); //Carpeta donde se guardan los archivos
    uploads.mkdirs(); //Crea los directorios necesarios
    File file = File.createTempFile("cod"+""+"-", "-"+fileName, uploads); //Evita que hayan dos archivos con el mismo nombre

    try (InputStream input = filePart.getInputStream()){
        Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    //return file.getPath();
    String archivo = file.getName();
    return archivo;
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
