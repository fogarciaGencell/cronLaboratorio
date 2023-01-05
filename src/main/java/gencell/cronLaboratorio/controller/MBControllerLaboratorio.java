/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.cronLaboratorio.controller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import gencell.cronLaboratorio.ejb.SessionBeanBaseFachadaLocal;
import gencell.cronLaboratorio.entities.Cron;
import gencell.cronLaboratorio.entities.VWCronSelfFileId;
import gencell.cronLaboratorio.entities.VWCronSelfdecodeBorrar;
import gencell.cronLaboratorio.entities.VWCronSelfdecodeCargaArchivos;
import gencell.cronLaboratorio.entities.VWCronSelfdecodeListos;
import gencell.cronLaboratorio.selfdecode.ProfilePersonaSelfdecode;
import gencell.cronLaboratorio.selfdecode.SelfdecodeServiceProcess;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;

@Named(value = "MBControllerLaboratorio")
@SessionScoped
public class MBControllerLaboratorio implements Serializable {

    @EJB
    private SessionBeanBaseFachadaLocal sessionBeanBaseFachada;
    private Cron monitorCron;
    public static final String PATH_ARCHIVOS_DEV = "public/files/dev/laboratorio_gp/ngs";
    //public static final String PATH_ARCHIVOS_SELFDECODE_DEV = "d:\\";
    //public static final String PATH_ARCHIVOS_SELFDECODE = "/var/www/html/files_selfdecode/";
    public static final String PATH_ARCHIVOS_LOCAL = "C:\\Users\\devjava\\Desktop\\fas\\";
    public static final String HOST = "192.168.0.242";
    public static final String USUARIO = "linux";
    public static final String CONTRASENA = "linux";

    /**
     * Creates a new instance of MBController
     */
    public MBControllerLaboratorio() {
        if (sessionBeanBaseFachada == null) {
            System.out.println("****************  fachada null Cron CARGA ARCHIVOS: ");
            sessionBeanBaseFachada = this.lookupSessionBeanBaseFachadaLocal();
            if (sessionBeanBaseFachada != null) {
                System.out.println("****************  fachada obtenida Cron CARGA ARCHIVOS: ");
            }
        }
    }

    public void ejecutarTareaCargaArchivosLaboratorio() {
        try {
            if (sessionBeanBaseFachada == null) {
                System.out.println("**************** fachada null en ejecutar tarea CARGA ARCHIVOS");
                return;
            }
            Date fechaInicio = new Date();
            //monitorCron = sessionBeanBaseFachada.BuscarPorID(Cron.class, 5);
            List<VWCronSelfdecodeCargaArchivos> listArchivosCargaSelfdecode;
            listArchivosCargaSelfdecode = sessionBeanBaseFachada.BuscarTodos(VWCronSelfdecodeCargaArchivos.class);
            if (listArchivosCargaSelfdecode != null && !listArchivosCargaSelfdecode.isEmpty()) {
                System.out.println("**************** ENCUENTRA ARCHIVOS SELFDECODE PARA CARGAR: " + listArchivosCargaSelfdecode.size());
                this.cargarArchivosSelfdecode(listArchivosCargaSelfdecode);
            }else{
                System.out.println("No hay archivos para copiar");
            }
            this.sendPost(); // Ejecutara el envio a Selfdecode 
            this.eliminarArchivosSelfdecode();// Elimina los archivos que estan en estado Complete SELFDECODE
//            if (monitorCron != null) {
//                monitorCron.setFechaInicio(fechaInicio);
//                monitorCron.setFechaFinal(new Date());
//                sessionBeanBaseFachada.Editar(monitorCron);
//            }

        } catch (Exception e) {
            System.out.println("Excepcion ejecutarTareaCargaArchivosSelfdecode" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarArchivosSelfdecode(List<VWCronSelfdecodeCargaArchivos> listArchivosCargaSelfdecode) {
        for (int i = 0; i < listArchivosCargaSelfdecode.size(); i++) {
            sessionBeanBaseFachada.actualizarEstadoPeticionBioLab(listArchivosCargaSelfdecode.get(i).getId(), "CARGANDO-SERVER", "10");
        }
        for (int i = 0; i < listArchivosCargaSelfdecode.size(); i++) {
            VWCronSelfdecodeCargaArchivos archivoCargaSelf = listArchivosCargaSelfdecode.get(i);
            try {
                Boolean flag = this.obtenerByteFile(archivoCargaSelf);
                if (flag != null && flag) {
                    this.comprobarSize(archivoCargaSelf);
                }
            } catch (Exception ex) {
                System.out.println("**************** ERROR AL CARGAR EL ARCHIVO DE CON ID: " + archivoCargaSelf.getId());
                ex.printStackTrace();
                //sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCarga.getId(), "ERROR", ex.getMessage(), "12");
            }
        }

    }

    private Boolean obtenerByteFile(VWCronSelfdecodeCargaArchivos archivoCar) throws Exception {

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
            sftp.get(archivoCar.getNombreArchivo(), PATH_ARCHIVOS_LOCAL + archivoCar.getNombreArchivo());
            sftp.disconnect();
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la conexión  para subir al server");
            e.printStackTrace();
            sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCar.getId(), "ERROR SUBIDA A SERVER", e.getMessage(), "15");
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

    private void comprobarSize(VWCronSelfdecodeCargaArchivos archivoCar) throws Exception {
        JSch jsch = new JSch();
        ChannelSftp sftp = null;
        Session session = null;
        try {
            File tmp = new File(PATH_ARCHIVOS_LOCAL + archivoCar.getNombreArchivo());
            if (!tmp.exists()) {
                sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCar.getId(), "INICIAL", "EL ARCHIVO EN EL SERVIDOR NO EXISTE", "50");
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
                if (entry.getFilename().equals(archivoCar.getNombreArchivo())) {
                    if (tmp.length() == attr.getSize()) {
                        sessionBeanBaseFachada.actualizarEstadoPeticionBioLab(archivoCar.getId(), "CARGADO-SERVER", "50");
                        //this.sendPost(archivoCar);
                        break;
                    } else {
                        sessionBeanBaseFachada.actualizarEstadoPeticionBioLab(archivoCar.getId(), "INICIAL", "10");
                        tmp.delete();
                        break;
                    }
                }
            }
            return;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la conexión para comprobar archivo");
            e.printStackTrace();
            sessionBeanBaseFachada.actualizarEstadoYDescPeticionBioLab(archivoCar.getId(), "ERROR COMPROBACION ARCHIVO", e.getMessage(), "15");
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

    private void sendPost() {
        //Variables
        String forward_reads = "";
        String reverse_reads = "";
        Integer idPeticion = 0;
        String profile;
        Integer idPaciente;
        ProfilePersonaSelfdecode profileSelfdecode;
        SelfdecodeServiceProcess selfdecodeserviceprocess = new SelfdecodeServiceProcess();
        List<VWCronSelfdecodeListos> listArchivosListosSelf;
        List<VWCronSelfdecodeListos> archivosOkForwardAndReverse;

        listArchivosListosSelf = sessionBeanBaseFachada.BuscarTodos(VWCronSelfdecodeListos.class);// Crea una lista con los objetos que estan listos 4 por consulta 2 envios a selfdecode por hilo
        if (!listArchivosListosSelf.isEmpty() || listArchivosListosSelf != null) { // Verifica si se obtiene resultados listos para enviar 
            for (int i = 0; i < listArchivosListosSelf.size(); i++) {
               
               // Limpiar Variables 
               forward_reads = ""; 
               reverse_reads = "";
               profile = "";
               
                if (idPeticion != listArchivosListosSelf.get(i).getIdPeticion()) { // Valida si la peticion ya fue procesada

                    idPeticion = listArchivosListosSelf.get(i).getIdPeticion(); // Obtiene la peticion 
                    archivosOkForwardAndReverse = sessionBeanBaseFachada.obtenerArchivosListos(idPeticion); // consulta los que estan estado cargado-server, trae 2 registros o 1 

                    // si aca despues de hacer la consulta no vienen los 2 no se ejecuta 
                    if (archivosOkForwardAndReverse.size() == 2) {
                        if (archivosOkForwardAndReverse.get(0).getIdentificador().equals("1")) {
                            //listArchivosCargaSelfdecode = sessionBeanBaseFachada.obtenerArchivosSelfdecode(idPeticion); // deberia obtener solo 2 objetos
                            forward_reads = PATH_ARCHIVOS_LOCAL + archivosOkForwardAndReverse.get(0).getNombreArchivo();
                            reverse_reads = PATH_ARCHIVOS_LOCAL + archivosOkForwardAndReverse.get(1).getNombreArchivo();
                            //System.out.println("Asignacion archivos 1");
                        } else if (archivosOkForwardAndReverse.get(1).getIdentificador().equals("1")) {
                            forward_reads = PATH_ARCHIVOS_LOCAL + archivosOkForwardAndReverse.get(1).getNombreArchivo();
                            reverse_reads = PATH_ARCHIVOS_LOCAL + archivosOkForwardAndReverse.get(0).getNombreArchivo();
                            //System.out.println("Asignacion Archivos 2");
                        }

                        profileSelfdecode = sessionBeanBaseFachada.consultarProfile(idPeticion); // Consulta el idPaciente y el Profile

                        profile = profileSelfdecode.getIdProfileSelfdecode();
                        idPaciente = profileSelfdecode.getIdPaciente();

                        // Validación para que no este ningun parametro en nulo
                        if ((profile != null) && (!forward_reads.equals("")) && (!reverse_reads.equals("")) && (idPaciente != null) && (idPeticion != null)) {
                            sessionBeanBaseFachada.actualizarEstadoBiolabSelfdecode(idPeticion, "ENVIANDO-SELFDECODE", profile, "70");

                            // Inicia el envio de datos a Selfdecode
                            selfdecodeserviceprocess.iniciarEnvioArchivoSelfdecode(profile, forward_reads, reverse_reads, idPaciente, idPeticion);
                        } else {
                            System.out.println("Verifique que no tenga datos nulos");
                        }
                    } else {
                        System.out.println("LOS ARCHIVOS NO ESTAN LISTOS O YA FUERON ENVIADOS");
                    }
                } else {
                    System.out.println("La Peticion ya fue procesada");
                }
            }
        } else {
            System.out.println("Los Archivos no estan cargados");
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

    private SessionBeanBaseFachadaLocal lookupSessionBeanBaseFachadaLocal() {
        try {
            Context c = new InitialContext();
            return (SessionBeanBaseFachadaLocal) c.lookup("java:global/CronCargaArchivos/SessionBeanBaseFachada!gencell.croncargaarchivos.ejb.SessionBeanBaseFachadaLocal");
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER EL BEAN DE CRON CARGA ARCHIVOS: ");
            e.printStackTrace();
            return null;
        }
    }

}
