/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.controller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import gencell.croncargaarchivos.ejb.SessionBeanBaseFachada;
import gencell.croncargaarchivos.ejb.SessionBeanBaseFachadaLocal;
import gencell.croncargaarchivos.entities.Cron;
import gencell.croncargaarchivos.entities.LabFinProcesamiento;
import gencell.croncargaarchivos.entities.VWCronSelfFileId;
import gencell.croncargaarchivos.entities.VWCronSelfdecodeBorrar;
import gencell.croncargaarchivos.entities.VWCronSelfdecodeCargaArchivos;
import gencell.croncargaarchivos.entities.VWCronSelfdecodeListos;
import gencell.croncargaarchivos.selfdecode.ProfilePersonaSelfdecode;
import gencell.croncargaarchivos.selfdecode.SelfdecodeServiceProcess;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Fredy G
 */
@Named(value = "MBControllerLaboratorio")
@SessionScoped
public class MBControllerLaboratorio implements Serializable {

    @EJB
    private SessionBeanBaseFachadaLocal sessionBeanBaseFachada;
    private Cron monitorCron;
    public static final String PATH_ARCHIVOS_DEV = "public/files/dev/laboratorio_gp/ngs";
    //public static final String PATH_ARCHIVOS_SELFDECODE_DEV = "d:\\";
    //public static final String PATH_ARCHIVOS_SELFDECODE = "/var/www/html/files_selfdecode/";
    public static final String PATH_ARCHIVOS_LOCAL = "C:\\Users\\devjava\\Desktop\\laboratorio\\";
    public static final String HOST = "192.168.0.242";
    public static final String USUARIO = "linux";
    public static final String CONTRASENA = "linux";

    /**
     * Creates a new instance of MBController
     */
    public MBControllerLaboratorio() {
        sessionBeanBaseFachada = new SessionBeanBaseFachada();
        if (sessionBeanBaseFachada == null) {
            System.out.println("****************  fachada null Cron CARGA ARCHIVOS: ");
            sessionBeanBaseFachada = this.lookupSessionBeanBaseFachadaLocal();
            System.out.println("Fachada Obtenida");
            if (sessionBeanBaseFachada != null) {
                System.out.println("****************  fachada obtenida Cron CARGA ARCHIVOS: ");
            }
        }
    }

    public void ejecutarTareaCargaArchivosLaboratorio() {
        try {
//            if (sessionBeanBaseFachada == null) {
//                System.out.println("**************** fachada null en ejecutar tarea CARGA ARCHIVOS");
//                return;
//            }
            Date fechaInicio = new Date();
            List<VWCronSelfdecodeCargaArchivos> listArchivosCargaSelfdecode;
            // CREAR UNA LISTA 
            // listArchivosCargaSelfdecode = sessionBeanBaseFachada.BuscarTodos(VWCronSelfdecodeCargaArchivos.class);

            List<String> archivos = new ArrayList<>();
            archivos.add("V350097149_L1_1.fq.gz");
            archivos.add("V350097149_L1_2.fq.gz");

            if (archivos != null && !archivos.isEmpty()) {
                System.out.println("**************** ENCUENTRA ARCHIVOS PARA CARGAR: " + archivos.size());
                this.cargarArchivosFasq(archivos);
            } else {
                System.out.println("No hay archivos para copiar");
            }
            //this.sendPost(); // Ejecutara el envio a Selfdecode 
            //this.eliminarArchivosSelfdecode();// Elimina los archivos que estan en estado Complete SELFDECODE
//            if (monitorCron != null) {
//                monitorCron.setFechaInicio(fechaInicio);
//                monitorCron.setFechaFinal(new Date());
//                sessionBeanBaseFachada.Editar(monitorCron);
//            }
            System.out.println("Renombrando y Ordenando ....... ");
            this.renombrarOrdenar();

        } catch (Exception e) {
            System.out.println("Excepcion ejecutarTareaCargaArchivosLaboratorio" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarArchivosFasq(List<String> archivos) {
//        for (int i = 0; i < listArchivosCargaSelfdecode.size(); i++) {
//            sessionBeanBaseFachada.actualizarEstadoPeticionBioLab(listArchivosCargaSelfdecode.get(i).getId(), "CARGANDO-SERVER", "10");
//        }
        for (int i = 0; i < archivos.size(); i++) {
            //VWCronSelfdecodeCargaArchivos archivoCargaSelf = listArchivosCargaSelfdecode.get(i);
            try {
                Boolean flag = this.obtenerByteFile(archivos.get(i));
                if (flag != null && flag) {
                    this.comprobarSize(archivos.get(i));
                } else {
                    System.out.println(archivos.get(i) + " NO SE PUEDE COPIAR");
                }
            } catch (Exception ex) {
                System.out.println("**************** ERROR AL CARGAR EL ARCHIVO : " + archivos.get(i));
                ex.printStackTrace();
                //sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCarga.getId(), "ERROR", ex.getMessage(), "12");
            }
        }

    }

    private Boolean obtenerByteFile(String archivoCar) throws Exception {

        JSch jsch = new JSch();
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = jsch.getSession(USUARIO, HOST, 22);
            session.setConfig("PreferredAuthentications", "password");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(CONTRASENA);
            session.connect();
            Channel channel = session.openChannel("sftp");
            sftp = (ChannelSftp) channel;
            sftp.connect();
            sftp.cd("/home/linux/Descargas/fas");
            sftp.get(archivoCar, PATH_ARCHIVOS_LOCAL + archivoCar);
            sftp.disconnect();
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la conexión  para subir al server");
            //e.printStackTrace();
            //sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCar.getId(), "ERROR SUBIDA A SERVER", e.getMessage(), "15");
            return false;
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    private void comprobarSize(String archivoCar) throws Exception {
        JSch jsch = new JSch();
        ChannelSftp sftp = null;
        Session session = null;
        try {
            File tmp = new File(PATH_ARCHIVOS_LOCAL + archivoCar);
            if (!tmp.exists()) {
                //sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCar.getId(), "INICIAL", "EL ARCHIVO EN EL SERVIDOR NO EXISTE", "50");
                return;
            }

            session = jsch.getSession(USUARIO, HOST, 22);
            session.setConfig("PreferredAuthentications", "password");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(CONTRASENA);
            session.connect();
            Channel channel = session.openChannel("sftp");
            sftp = (ChannelSftp) channel;
            sftp.connect();
            java.util.Vector<ChannelSftp.LsEntry> flLst = sftp.ls("/home/linux/Descargas/fas");
            for (int j = 0; j < flLst.size(); j++) {
                ChannelSftp.LsEntry entry = flLst.get(j);
                SftpATTRS attr = entry.getAttrs();
                if (entry.getFilename().equals(archivoCar)) {
                    if (tmp.length() == attr.getSize()) {
                        //sessionBeanBaseFachada.actualizarEstadoPeticionBioLab(archivoCar.getId(), "CARGADO-SERVER", "50");
                        System.out.println("Archivo " + archivoCar + " Copiado");
                        //this.sendPost(archivoCar);
                        break;
                    } else {
                        // sessionBeanBaseFachada.actualizarEstadoPeticionBioLab(archivoCar.getId(), "INICIAL", "10");
                        tmp.delete();
                        break;
                    }
                }
            }
            return;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la conexión para comprobar archivo");
            e.printStackTrace();
            //sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCar.getId(), "ERROR COMPROBACION ARCHIVO", e.getMessage(), "15");
            return;
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    public void eliminarArchivosSelfdecode() {
        // System.out.println("ELIMINAR ARCHIVOS");
        List<VWCronSelfFileId> listaFileId;
        List<VWCronSelfdecodeBorrar> archivosBorrar; // Para obtener el nombre del archivo a eliminar 
        SelfdecodeServiceProcess consultarEstado = new SelfdecodeServiceProcess();
        String estado;
        String file;
        Integer idPeticion;

        listaFileId = sessionBeanBaseFachada.BuscarTodos(VWCronSelfFileId.class); // Crea una lista con los objetos maximo 6

        for (VWCronSelfFileId fileIdSelfdecode : listaFileId) {

            idPeticion = fileIdSelfdecode.getIdPeticion();
            file = fileIdSelfdecode.getIdGenomeFile();
            estado = consultarEstado.obtenerEstadoFile(file);
            archivosBorrar = sessionBeanBaseFachada.obtenerArchivosSelfBorrar(idPeticion); // trae 2 resultados
            //System.out.println("idPEticion: " + idPeticion );
            //System.out.println("Estado: " + estado);
            if (archivosBorrar != null && archivosBorrar.size() == 2) {// valida si vienen los dos archivos para eliminar forward y reverse
                if (estado != null && estado.equals("COMPLETED")) {
                    // Actualiza la fecha y el estado obtenido de selfdecode en la tabla del log
                    String fechaSelfOk = "";
                    fechaSelfOk = new Date().toString();
                    sessionBeanBaseFachada.actualizarEstadoSelfTablaLog(file, estado, fechaSelfOk);

                    File fichero1 = new File(PATH_ARCHIVOS_LOCAL + archivosBorrar.get(0).getNombreArchivo());
                    File fichero2 = new File(PATH_ARCHIVOS_LOCAL + archivosBorrar.get(1).getNombreArchivo());

                    // Valida existencia de fichero 1
                    if (!fichero1.exists()) {
                        sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo(archivosBorrar.get(0).getId(), "FINALIZADO", "", "100");
                        System.out.println("El fichero " + archivosBorrar.get(0).getNombreArchivo() + " ya no existe");
                    }

                    // valida existencia de fichero 2
                    if (!fichero2.exists()) {
                        sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo(archivosBorrar.get(1).getId(), "FINALIZADO", "", "100");
                        System.out.println("El fichero " + archivosBorrar.get(1).getNombreArchivo() + " ya no existe");
                        return;
                    }

                    // Elimina Fichero 1
                    if (fichero1.delete()) {
                        sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo(archivosBorrar.get(0).getId(), "FINALIZADO", "", "100");
                        System.out.println("El fichero " + archivosBorrar.get(0).getNombreArchivo() + " ha sido borrados satisfactoriamente");
                    } else {
                        System.out.println("El fichero " + archivosBorrar.get(0).getNombreArchivo() + " no puede ser borrado");
                        sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo(archivosBorrar.get(0).getId(), "", "ERROR BORRANDO ARCHIVO", "95");
                    }

                    // Elimina Fichero 2 
                    if (fichero2.delete()) {
                        sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo(archivosBorrar.get(1).getId(), "FINALIZADO", "", "100");
                        System.out.println("El fichero " + archivosBorrar.get(1).getNombreArchivo() + " ha sido borrados satisfactoriamente");
                    } else {
                        System.out.println("El fichero " + archivosBorrar.get(1).getNombreArchivo() + " no puede ser borrado");
                        sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLabSelfdecodeArchivo(archivosBorrar.get(1).getId(), "", "ERROR BORRANDO ARCHIVO", "95");
                    }
                }

                if (estado != null && estado.equals("ERROR")) {
                    // Actualiza la fecha y el estado obtenido de selfdecode en la tabla del log
                    String fechaSelf = "";
                    fechaSelf = new Date().toString();
                    sessionBeanBaseFachada.actualizarEstadoSelfTablaLog(file, estado, fechaSelf);
                    sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLabSelfdecode(idPeticion, "ERROR EN ANÁLISIS", "", "85");
                }
            } else {
                System.out.println("ERROR OBTENIENDO LA INFORMACIÓN PARA ELIMINAR, tienen que ser 2 archivos por petición");
            }

        }
    }

    public void renombrarOrdenar() throws IOException {

        List<LabFinProcesamiento> listaLabFinProcesamiento;
        List<VWCronSelfFileId> listaFileId;
        listaFileId = sessionBeanBaseFachada.BuscarTodos(VWCronSelfFileId.class);
        sessionBeanBaseFachada.actualizarLabFinProcesamiento(1);
        listaLabFinProcesamiento = sessionBeanBaseFachada.consultarArchivosFinProcesamiento();
        File copiar = new File("C:" + File.separator + "Users" + File.separator + "devjava" + File.separator + "Desktop" + File.separator + "laboratorio");
        File pegar;

        String rutaInicial = copiar.getAbsolutePath();
        String rutaFinal;
        String[] directorio = copiar.list();
        String primero = "";
        String segundo = "";
        String nombreArchivoLab = "";
        Integer peticion = 0;
        Integer idArchivo = 0;
        Integer contador = 0;

        Path in = Paths.get(rutaInicial);
        Path out;

        //Files.createDirectory(out); // Crea la carpeta donde se va a pegar la informacion
        for (int i = 0; i < directorio.length; i++) {

            for (LabFinProcesamiento lista : listaLabFinProcesamiento) {

                idArchivo = lista.getId();
                peticion = lista.getIdPeticion();
                nombreArchivoLab = lista.getNombreArchivoCompleto();
                pegar = new File("C:" + File.separator + "Users" + File.separator + "devjava" + File.separator + "Desktop" + File.separator + "laboratorio" + File.separator + peticion);
                rutaFinal = pegar.getAbsolutePath();
                out = Paths.get(pegar.getAbsolutePath());
                for (String archivo : directorio) {

                    if (lista.equals(archivo)) {
                        System.out.println("ES IGUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAL ");

                        // Pendiente revisar el contador
                        //if(directorio[contador].equals(nombreArchivoLab)){
                        Files.createDirectory(out); // Crea la carpeta donde se va a pegar la informacion    
                        // ** Se agrega informacion a las variables, se reescriben
                        primero = archivo.substring(0, 13);
                        segundo = archivo.substring(13, directorio[i].length());
                        System.out.println("RENOMBRADO: " + primero + "_" + peticion + segundo);
                        in = Paths.get(rutaInicial + File.separator + archivo);
                        out = Paths.get(rutaFinal + File.separator + primero + "_" + peticion + segundo);
                        System.out.println("Ruta Copiar: " + in);
                        System.out.println("Ruta Pegar: " + out);

                        Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("COPIADO");
                        sessionBeanBaseFachada.actualizarLabFinProcesamiento(idArchivo);
                    } else {
                        System.out.println("No Se Encontro Coincidencia");
                    }
                }
            }
        }
    }

    private SessionBeanBaseFachadaLocal lookupSessionBeanBaseFachadaLocal() {
        try {
            Context c = new InitialContext();
            return (SessionBeanBaseFachadaLocal) c.lookup("java:global/CronLaboratorio/SessionBeanBaseFachada!gencell.cronLaboratorio.ejb.SessionBeanBaseFachadaLocal");
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER EL BEAN DE CRON CARGA ARCHIVOS: ");
            e.printStackTrace();
            return null;
        }
    }
}