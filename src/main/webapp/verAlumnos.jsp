<%-- 
    Document   : verAlumnos
    Created on : 01-dic-2021, 12:42:10
    Author     : Sandra
--%>

<%@page import="java.util.List"%>
<%@page import="modelo.Alumnos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style>
            td {
                width: 30%;
            }
        </style>
    </head>
    <body> 
        <div class="container">
        <h1>Listado de alumnos</h1>
        <a class="btn btn-primary" href="Controlador?op=insertar">Nuevo Alumno</a>
        <% 
            List <Alumnos> listado = (List<Alumnos>) request.getAttribute("alumnos");
            String mensaje = (String) request.getAttribute("mensaje");
        %>
   
        <% if (mensaje != null){ %>
                <h2 class="alert alert-success"> <%= mensaje %> </h2>
                  
        <% }     
            for (Alumnos a: listado){ %>
        <table class="table table-striped">
            <tr>
                <td> <%=a.getId() %> </td>
                <td> <%=a.getNombre() %> </td>
                <td> <%= a.getApellido() %> </td>
                <td> <%= a.getAsignatura() %></td>
                <td><a href="Controlador?op=borrar&id=<%=a.getId() %>" onclick="return Confirmation()">Borrar</a></td>
                <td><a href="Controlador?op=actualizar&id=<%=a.getId() %>">Actualizar</a></td>
             </tr>
            <% } %>
        </table>
         <p>Mostrando página ${pagina} de ${num_paginas}</p>
         <%
            String num_paginasStr = ( String )  request.getAttribute("num_paginas");
            int num_paginas = Integer.parseInt(num_paginasStr);
            for ( int p=1;p<=num_paginas;p++ ) {
          %>
                 <a href="Controlador?op=listar&pagina=<%=p%>" ><%=p%></a> 
           <%      
             }
             %>
        </div>
        <script>
                         function Confirmation(){
                 if (confirm("¿Está seguro/a de que quiere eliminarlo?")  ) {
                    alert("El registro de eliminará");
                    return true;
                } else {
                    return false;
                }
                 
         }
        </script>
    </body>
</html>
