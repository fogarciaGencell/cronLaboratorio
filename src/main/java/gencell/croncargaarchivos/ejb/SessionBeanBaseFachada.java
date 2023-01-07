/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.ejb;

import gencell.croncargaarchivos.entities.LabFinProcesamiento;
import gencell.croncargaarchivos.entities.VWCronArchivosCarga;
import gencell.croncargaarchivos.entities.VWCronSelfdecodeBorrar;
import gencell.croncargaarchivos.entities.VWCronSelfdecodeListos;
import gencell.croncargaarchivos.selfdecode.ProfilePersonaSelfdecode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JOSEPH
 */
@Stateless
public class SessionBeanBaseFachada implements SessionBeanBaseFachadaLocal {

    @PersistenceContext(unitName = "CronCargaArchivos-PU")
    private EntityManager em;

    // Generales
    public <T> T BuscarPorID(Class<T> entityClass, Object primaryKey) {
        em.getEntityManagerFactory().getCache().evictAll();
        return em.find(entityClass, primaryKey);
    }

    public List BuscarTodos(Class entityClass) {
        em.getEntityManagerFactory().getCache().evictAll();
        return em.createQuery("select o "
                + "from " + entityClass.getSimpleName() + " o").getResultList();
    }

    public List BuscarTodosPorId(Class entityClass, List ids) {
        em.getEntityManagerFactory().getCache().evictAll();
        List entitys = new ArrayList();
        for (Object id : ids) {
            entitys.add(BuscarPorID(entityClass, id));
        }
        return entitys;
    }

    public void Crear(Object entity) {
        em.persist(entity);
        em.flush();
    }

    public void Borrar(Object entity) {
        em.remove(em.merge(entity));
        em.flush();
    }

    public <T> T Editar(T entity) {
        try {
            return em.merge(entity);
        } catch (Exception err) {
            return null;
        }
    }

    public void Refrescar(Object entity) {
        em.refresh(entity);
    }

    public List<VWCronArchivosCarga> obtenerArchivosCarga() {
        em.getEntityManagerFactory().getCache().evictAll();
        String query = "select * from VWCronArchivosCarga;";
        Query q = em.createNativeQuery(query, VWCronArchivosCarga.class);
        if (q.getResultList() == null || q.getResultList().isEmpty()) {
            return null;
        } else {
            return (List<VWCronArchivosCarga>) q.getResultList();
        }
    }

    public void actualizarEstadoResultadosOrden(Integer idResultadoOrden, String estado) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE ResultadosOrden SET estadoCarga = '" + estado + "' WHERE idResultadosOrden = '" + idResultadoOrden + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarResultadosOrden " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarResultadosOrden " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    public void actualizarURLResultadosOrden(Integer idResultadoOrden, String url) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE ResultadosOrden SET urlResultado = '" + url + "' WHERE idResultadosOrden = '" + idResultadoOrden + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarURLResultadosOrden " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarURLResultadosOrden " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    public void actualizarEstadoPeticionBioLab(Integer id, String estado, String porcentaje) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE PeticionBioLab SET estado = '" + estado + "' , porcentaje='" + porcentaje + "' WHERE id = '" + id + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoPeticionBioLab " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoPeticionBioLab " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    public void actualizarEstadoYDescPeticionBioLab(Integer id, String estado, String descripcion, String porcentaje) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE PeticionBioLab SET estado = '" + estado + "' , descripcion = '" + descripcion + "' , porcentaje='" + porcentaje + "' WHERE id = '" + id + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoPeticionBioLab " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoPeticionBioLab " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
          
        }
    }

    public void actualizarEstadoPeticionIdVarsomeBioLab(Integer id, String estado, String idVarsome, String porcentaje) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE PeticionBioLab SET estado = '" + estado + "', idVarsome = '" + idVarsome + "' , porcentaje='" + porcentaje + "' WHERE id = '" + id + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoPeticionBioLab " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoPeticionBioLab " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    public void actualizarEstadoSanitasBioLab(Integer id, String estado, String porcentaje) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE SanitasBioLab SET estado = '" + estado + "' , porcentaje='" + porcentaje + "' WHERE id = '" + id + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoSanitasBioLab " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoSanitasBioLab " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    public void actualizarEstadoYDescSanitasBioLab(Integer id, String estado, String descripcion, String porcentaje) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE SanitasBioLab SET estado = '" + estado + "' , descripcion = '" + descripcion + "' , porcentaje='" + porcentaje + "' WHERE id = '" + id + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoYDescSanitasBioLab " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoYDescSanitasBioLab " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    public void actualizarEstadoSanitasIdVarsomeBioLab(Integer id, String estado, String idVarsome, String porcentaje) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE SanitasBioLab SET estado = '" + estado + "', idVarsome = '" + idVarsome + "' , porcentaje='" + porcentaje + "' WHERE id = '" + id + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoSanitasIdVarsomeBioLab " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoSanitasIdVarsomeBioLab " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    // Consultas proceso Selfdecode
    @Override
    public ProfilePersonaSelfdecode consultarProfile(Integer idPeticion) {

        em.getEntityManagerFactory().getCache().evictAll();
        String query = "SELECT     pps.idPaciente,\n"
                + "pps.idProfileSelfdecode \n"
                + "FROM       PeticionBioLab pbl \n"
                + "JOIN       Peticion pet \n"
                + "ON         pbl.idPeticion=pet.idPeticion \n"
                + "JOIN       ProfilePersonaSelfdecode pps \n"
                + "ON         pps.idPaciente=pet.idPaciente \n"
                + "WHERE      pbl.idPeticion=" + idPeticion + ";";
        Query q = em.createNativeQuery(query, ProfilePersonaSelfdecode.class);
        if (q.getResultList() == null || q.getResultList().isEmpty()) {
            return null;
        } else {
            return (ProfilePersonaSelfdecode) q.getResultList().get(0);
        }

    }

//    @Override
//    public List<VWCronSelfdecodeCargaArchivos> obtenerArchivosSelfdecode(Integer idPeticion) {
//        em.getEntityManagerFactory().getCache().evictAll();
//        String query = "select * from VWCronSelfdecodeCargaArchivos where idPeticion = "+ idPeticion+ " limit 2;";
//        Query q = em.createNativeQuery(query, VWCronSelfdecodeCargaArchivos.class);
//        if (q.getResultList() == null || q.getResultList().isEmpty()) {
//            return null;
//        } else {
//            return (List<VWCronSelfdecodeCargaArchivos>) q.getResultList();
//        }
//        
//    }
    @Override
    public List<VWCronSelfdecodeListos> obtenerArchivosListos(Integer idPeticion) {
        em.getEntityManagerFactory().getCache().evictAll();
        String query = "select * from VWCronSelfdecodeListos where idPeticion = " + idPeticion + " limit 2;";
        Query q = em.createNativeQuery(query, VWCronSelfdecodeListos.class);
        if (q.getResultList() == null || q.getResultList().isEmpty()) {
            return null;
        } else {
            return (List<VWCronSelfdecodeListos>) q.getResultList();
        }
    }

    @Override
    public void actualizarEstadoBiolabSelfdecode(Integer idPeticion, String estado, String profile, String porcentaje) {

        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE PeticionBioLab SET estado = '" + estado + "', idVarsome = '" + profile + "' , porcentaje='" + porcentaje + "' WHERE idPeticion = '" + idPeticion + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoBiolabSelfdecode " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoBiolabSelfdecode " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    @Override
    public void actualizarEstadoYDescPeticionBioLabSelfdecode(Integer idPeticion, String estado, String descripcion, String porcentaje) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE PeticionBioLab SET estado = '" + estado + "' , descripcion = '" + descripcion + "' , porcentaje='" + porcentaje + "' WHERE idPeticion = '" + idPeticion + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoPeticionBioLab Selfdecode " + retorno);
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoPeticionBioLab Selfdecode" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<VWCronSelfdecodeBorrar> obtenerArchivosSelfBorrar(Integer idPeticion) {
        em.getEntityManagerFactory().getCache().evictAll();
        String query = "select * from VWCronSelfdecodeBorrar where idPeticion = " + idPeticion + " limit 2;";
        Query q = em.createNativeQuery(query, VWCronSelfdecodeBorrar.class);
        if (q.getResultList() == null || q.getResultList().isEmpty()) {
            return null;
        } else {
            return (List<VWCronSelfdecodeBorrar>) q.getResultList();
        }
    }

    @Override
    public void actualizarEstadoSelfTablaLog(String genomeFile, String estado, String fechaSelf) {
         try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE LogCargueArchivosSelf SET estado = '" + estado + "', fechaEstadoSelfdecode = '" + fechaSelf + "' WHERE idGenomeFile = '" + genomeFile + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoSelfTablaLog " + retorno);
            //return retorno.toString();
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoSelfTablaLog " + e.getMessage());
            e.printStackTrace();
            //return "Error: " + e.getMessage();
        }
    }

    @Override
    public void actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo(Integer id, String estado, String descripcion, String porcentaje) {

         try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "UPDATE PeticionBioLab SET estado = '" + estado + "' , descripcion = '" + descripcion + "' , porcentaje='" + porcentaje + "' WHERE id = '" + id + "';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo Selfdecode " + retorno);
        } catch (Exception e) {
            System.out.print("Error en: actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo Selfdecode" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<LabFinProcesamiento> consultarArchivosFinProcesamiento() {
        System.out.println("Realizan Consulta ¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        em.getEntityManagerFactory().getCache().evictAll();
        String query = "select * from LabFinProcesamiento where procesado='N';";
        Query q = em.createNativeQuery(query, LabFinProcesamiento.class);
        if (q.getResultList() == null || q.getResultList().isEmpty()) {
            System.out.println("CERO RESULTADOS");
            return null;
        } else {
            System.out.println("HAY RESULTADOS");
            return (List<LabFinProcesamiento>) q.getResultList();
        }



    }

    @Override
    public void actualizarLabFinProcesamiento(Integer id) {
         try {
            em.getEntityManagerFactory().getCache().evictAll();
            String query = "update LabFinProcesamiento set procesado ='S' where id = '"+id+"';";
            Query q = em.createNativeQuery(query);
            Integer retorno = q.executeUpdate();
            System.out.print("Resultado actualizarLabFinProcesamiento " + retorno);
        } catch (Exception e) {
            System.out.print("Error en: actualizarLabFinProcesamiento " + e.getMessage());
            e.printStackTrace();
        }
    }
}
