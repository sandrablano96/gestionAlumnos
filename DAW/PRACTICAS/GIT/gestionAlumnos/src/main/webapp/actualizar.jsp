<%-- 
    Document   : actualizar
    Created on : 01-dic-2021, 12:42:18
    Author     : Sandra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            form{
                margin:10%; 
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <% String mensaje = (String) request.getAttribute("mensaje"); %>
        <% String operacion = (String) request.getAttribute("operacion"); %>
        <h1>Formulario gestion de alumnado</h1>
        <% if (mensaje != null) {%>
        <h2 class="alert alert-success"> <%= mensaje%> </h2>
        <% }%>
        <form action="Controlador?op=<%=operacion%>" method="post">
            <div class="row">
                <div class="col-12 col-sm-6 col-lg-30">
                    <p>Id:<input type="text" name="id" value="${alumno.id}" readonly class="form-control"> </p>
                </div>
                <div class="col-12 col-sm-6 col-lg-30">
                    <p>Nombre:<input type="text" name="nombre" value="${alumno.nombre}" class="form-control"> </p>
                </div>
                <div class="col-12 col-sm-6 col-lg-30">
                    <p>Apellido: <input type="text" name="apellido" value="${alumno.apellido}" class="form-control"></p>
                </div>
                <div class="col-12 col-sm-6 col-lg-30">
                    <p>Asignatura: <input type="text" name="asignatura" value="${alumno.asignatura}" class="form-control"></p>
                </div>
            </div>
            <input type="submit" value="Enviar"><a href="Controlador?op=listar"> Volver </a>
        </form>
        
    </body>
</html>
