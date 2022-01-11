/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author DAW-A
 */
public class Crud {
    public static List<Alumnos> getAlumnos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_persistence_unit");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT * FROM alumnos";
        Query q = manager.createNativeQuery(sql,Alumnos.class); //método para consultas en SQL
        List<Alumnos> alumnosBD= q.getResultList();

        return alumnosBD;        
        }
        
    public static List<Alumnos> getAlumnosPaginado(int offset, int lineas_pagina) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_persistence_unit");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT * FROM alumnos";
        Query q = manager.createNativeQuery(sql,Alumnos.class); //método para consultas en SQL
        q.setFirstResult(offset);
        q.setMaxResults(lineas_pagina);
        List<Alumnos> alumnosBD = q.getResultList();

        return alumnosBD;        
        }
    public static int borrarAlumno(int id){
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_persistence_unit");
        EntityManager manager = factory.createEntityManager();
        String sql = "DELETE from Alumnos a WHERE a.id = " + id;
        Query q = manager.createQuery(sql); 
        manager.getTransaction().begin();
        int filasAfectadas = q.executeUpdate();
        manager.getTransaction().commit();
        return filasAfectadas;
    }
    
    public static Alumnos getAlumno(int id){
         EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_persistence_unit");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT a FROM Alumnos a WHERE a.id=" + id; //consulta en JPQL 
        Query q = manager.createQuery(sql,Alumnos.class); //método para consultas en JPQL
        Alumnos alum = (Alumnos) q.getSingleResult();
        return alum;
    }
    
    public static int actualizarAlumno(Alumnos alum){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_persistence_unit");
        EntityManager manager = factory.createEntityManager();
        String sql = "UPDATE Alumnos a SET a.nombre = :nombre, a.apellido = :apellido, a.asignatura = :asignatura, a.imagen = :imagen WHERE a.id = :id";
        Query q = manager.createQuery(sql,Alumnos.class);
        q.setParameter("asignatura", alum.getAsignatura());
        q.setParameter("nombre", alum.getNombre());
        q.setParameter("apellido", alum.getApellido());
        q.setParameter("imagen", alum.getImagen());
        q.setParameter("id", alum.getId());
        
        manager.getTransaction().begin();
        int filasAfectadas = q.executeUpdate();
        manager.getTransaction().commit();
        //manager.close();
        return filasAfectadas;      
    }
     public static void insertaAlumno(Alumnos alum) {
     EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_persistence_unit");
        EntityManager manager = factory.createEntityManager();
         manager.getTransaction().begin();
        manager.merge(alum);
        manager.getTransaction().commit();
        }
}