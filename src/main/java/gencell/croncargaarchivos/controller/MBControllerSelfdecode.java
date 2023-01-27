/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.controller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
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
import org.eclipse.persistence.sdo.helper.extension.SDOUtil;

@Named(value = "MBControllerSelfdecode")
@SessionScoped
public class MBControllerSelfdecode implements Serializable {

    @EJB
    private SessionBeanBaseFachadaLocal sessionBeanBaseFachada;
    private Cron monitorCron;
    //public static final String PATH_ARCHIVOS_DEV = "public/files/dev/laboratorio_gp/ngs";
    //public static final String PATH_ARCHIVOS_SELFDECODE_DEV = "d:\\";
    //public static final String PATH_ARCHIVOS_SELFDECODE = "/var/www/html/files_selfdecode/";
    public static final String PATH_ARCHIVOS_LOCAL = "C:\\Users\\devjava\\Desktop\\laboratorio\\";
    public static final String HOST_LINUX = "192.168.0.243";
    public static final String USUARIO_LINUX = "linux";
    public static final String CONTRASENA_LINUX = "linux";
    public static final String HOST_SECUENCIADOR = "200.91.224.181";
    public static final String USUARIO_SECUENCIADOR = "processmarki";
    public static final String CONTRASENA_SECUENCIADOR = "serverBio2022*";

    /**
     * Creates a new instance of MBController
     */
    public MBControllerSelfdecode() {
        if (sessionBeanBaseFachada == null) {
            System.out.println("****************  fachada null Cron CARGA ARCHIVOS: ");
            sessionBeanBaseFachada = this.lookupSessionBeanBaseFachadaLocal();
            if (sessionBeanBaseFachada != null) {
                System.out.println("****************  fachada obtenida Cron CARGA ARCHIVOS: ");
            }
        }
    }

    public void ejecutarTareaCargaArchivosSelfdecode() {
        try {
            //List<String> archivos = new ArrayList<>();
            List<LabFinProcesamiento> archivos = new ArrayList<>();
            //archivos.add("V350097149_L01_58_1.fq.gz");
            //archivos.add("V350097149_L01_58_2.fq.gz");
            //archivos.add("V350097149_L03_81_2.fq.gz");
            //archivos.add("V350097149_L03_83_1.fq.gz");
            
          //  this.crearArchivos("/home/linux/Descargas/fas", "C:\\Users\\devjava\\Desktop\\laboratorio\\25088\\V350097149_L01_58_25088_1.fq.gz", "Creado.fq.gz");
            
            archivos = sessionBeanBaseFachada.consultarArchivosCopiar();

            if (archivos != null && !archivos.isEmpty()) {
                System.out.println("**************** ENCUENTRA ARCHIVOS PARA CARGAR: " + archivos.size());
                this.cargarArchivosFasq(archivos);
            } else {
                System.out.println("No hay archivos para copiar");
            }

            System.out.println("Renombrando y Ordenando ....... ");
            this.renombrarOrdenar();
            System.out.println("COPIANDO AL DE ALMACENAMIENTO");
            this.copiarHastaAlmacenamiento("/home/linux/Descargas/fas", "C:\\Users\\devjava\\Desktop\\laboratorio\\25088\\V350097032_L01_45_25088_1.fq.gz", "ArchivoCopiado.fq.gz");
            //this.eliminarDelSecuenciador();
        } catch (Exception e) {
            System.out.println("Error al inicio");
        }
    }

    private void cargarArchivosFasq(List<LabFinProcesamiento> archivos) {
        for (int i = 0; i < archivos.size(); i++) {
            sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento(archivos.get(i).getId(), "PREPARADO PARA COPIAR", 10);// Cambia el estado para que el proximo cron no seleccione los mismos que estan en proceso
        }
        
        //Recorre la lista para empezar a copiar 
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
                sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento(archivos.get(i).getId(), "ERROR COPIANDO", 10);
            }
        }

    }

    private Boolean obtenerByteFile(LabFinProcesamiento archivoCar) throws Exception {

        JSch jsch = new JSch();
        ChannelSftp sftp = null;
        Session session = null;
        String archivo = archivoCar.getNombreArchivoCompleto();
        try {
            session = jsch.getSession(USUARIO_SECUENCIADOR, HOST_SECUENCIADOR, 22);
            session.setConfig("PreferredAuthentications", "password");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(CONTRASENA_SECUENCIADOR);
            session.connect();
            Channel channel = session.openChannel("sftp");
            sftp = (ChannelSftp) channel;
            sftp.connect();
            sftp.cd("/var/www/html/files_varsome");
            sftp.get(archivo, PATH_ARCHIVOS_LOCAL + archivo);
            sftp.disconnect();
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la conexión  para subir al server");
            e.printStackTrace();
            sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento(archivoCar.getId(), "ERROR SUBIDA A SERVER", 60);
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

    private void comprobarSize(LabFinProcesamiento archivoCar) throws Exception {
        JSch jsch = new JSch();
        ChannelSftp sftp = null;
        Session session = null;
        String archivo = archivoCar.getNombreArchivoCompleto();
        try {
            File tmp = new File(PATH_ARCHIVOS_LOCAL + archivo);
            if (!tmp.exists()) {
                sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento( archivoCar.getId(), "NO EXISTE EN EL SERVIDOR", 50);
                return;
            }

            session = jsch.getSession(USUARIO_SECUENCIADOR, HOST_SECUENCIADOR, 22);
            session.setConfig("PreferredAuthentications", "password");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(CONTRASENA_SECUENCIADOR);
            session.connect();
            Channel channel = session.openChannel("sftp");
            sftp = (ChannelSftp) channel;
            sftp.connect();
            java.util.Vector<ChannelSftp.LsEntry> flLst = sftp.ls("/var/www/html/files_varsome");
            for (int j = 0; j < flLst.size(); j++) {
                ChannelSftp.LsEntry entry = flLst.get(j);
                SftpATTRS attr = entry.getAttrs();
                if (entry.getFilename().equals(archivo)) {
                    if (tmp.length() == attr.getSize()) {
                        System.out.println("Archivo en el Seuenciador: " + entry);
                        sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento(archivoCar.getId(), "CARGADO-SERVER", 90);
                       // System.out.println("Archivo " + archivoCar + " Copiado");
                        //this.sendPost(archivoCar);
                        break;
                    } else {
                        sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento(archivoCar.getId(), "ERROR-VALIDANDO", 65);
                        //tmp.delete();
                        break;
                    }
                }
            }
            return;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la conexión para comprobar archivo");
            e.printStackTrace();
            sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento(archivoCar.getId(), "ERROR COMPROBANDO ARCHIVO", 65);
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

    public void eliminarDelSecuenciador() {
        System.out.println("SECUENCIADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
       List<LabFinProcesamiento> lstSecuenciadorBorrar = new ArrayList<>();
       lstSecuenciadorBorrar = sessionBeanBaseFachada.consultarBorrarSecuenciador();
       String ruta; // pendiente definir la ruta desde el excel
       String nombreArchivo = "";
       File rutaBorrar;
       Path pathArchivoBorrar ;
       try{
       for(LabFinProcesamiento lista: lstSecuenciadorBorrar){
           nombreArchivo = lista.getNombreArchivoCompleto();
           ruta = "/home/linux/Descargas/fas/" + nombreArchivo;
           rutaBorrar = new File(ruta);
           String archivoBorrar = rutaBorrar.getAbsolutePath();
           System.out.println("RUTA sECUENCIADOR: " +  rutaBorrar);
           if(rutaBorrar.exists()){
               
               System.out.println("Se encontro el archivo");
               pathArchivoBorrar = Paths.get(archivoBorrar);
               Files.delete(pathArchivoBorrar);
               System.out.println("Archivo BORRADO DEL SECUENCIADOR: " + pathArchivoBorrar);
               sessionBeanBaseFachada.actualizarEstadoPorcentajeLabFinProcesamiento(lista.getId(), "FINALIZADO", 100);
           }
           
       }
       }catch(Exception e){
           System.out.println("Ocurrio un Error Eliminando el archivo");
       }
    
    }

    public void renombrarOrdenar() throws IOException {

        List<LabFinProcesamiento> listaLabFinProcesamiento;
        //listaLabFinProcesamiento = sessionBeanBaseFachada.consultarArchivosFinProcesamiento();
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
       // Integer contadorExiste = 0;
        
        Path in = Paths.get(rutaInicial);
        Path out;

        //Files.createDirectory(out); // Crea la carpeta donde se va a pegar la informacion
        try {
            listaLabFinProcesamiento = sessionBeanBaseFachada.consultarArchivosFinProcesamiento();
            for (int i = 0; i < directorio.length; i++) {

                for (LabFinProcesamiento lista : listaLabFinProcesamiento) {
                    contador = 0 ;
                    idArchivo = lista.getId();
                    peticion = lista.getIdPeticion();
                    nombreArchivoLab = lista.getNombreArchivoCompleto();
                    pegar = new File("C:" + File.separator + "Users" + File.separator + "devjava" + File.separator + "Desktop" + File.separator + "laboratorio" + File.separator + peticion);
                    rutaFinal = pegar.getAbsolutePath();
                    out = Paths.get(pegar.getAbsolutePath());
                    
                    while (contador < directorio.length) {
                        if (directorio[contador].equals(nombreArchivoLab)) {

                           if(pegar.exists()){
                               
                               
                            primero = directorio[contador].substring(0, 17);
                            segundo = directorio[contador].substring(17, directorio[contador].length());
                            System.out.println("RENOMBRADO: " + primero + "_" + peticion + segundo);
                            in = Paths.get(rutaInicial + File.separator + directorio[contador]);
                            out = Paths.get(rutaFinal + File.separator + primero + "_" + peticion + segundo);
                           // System.out.println("Ruta Copiar: " + in);
                           // System.out.println("Ruta Pegar: " + out);

                            Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                           // System.out.println("COPIADO");
                           if (Files.size(in) == Files.size(out)){ 
                           Files.delete(in);
                            System.out.println("ARCHIVO BORRADO " + in + " ----- " + out);
                           }else{
                               System.out.println("No se puede borrar el archivo");
                           }
                            sessionBeanBaseFachada.actualizarLabFinProcesamiento(idArchivo);
                            listaLabFinProcesamiento = sessionBeanBaseFachada.consultarArchivosFinProcesamiento();
                           }else {
                           
                           
                            Files.createDirectory(out); // Crea la carpeta donde se va a pegar la informacion 
                            //String[] directorioCreado = pegar.list();
                            // ** Se agrega informacion a las variables, se reescriben
                            
                            
                            primero = directorio[contador].substring(0, 17);
                            segundo = directorio[contador].substring(17, directorio[contador].length());
                            System.out.println("RENOMBRADO: " + primero + "_" + peticion + segundo);
                            in = Paths.get(rutaInicial + File.separator + directorio[contador]);
                            out = Paths.get(rutaFinal + File.separator + primero + "_" + peticion + segundo);
                           // System.out.println("Ruta Copiar: " + in);
                           // System.out.println("Ruta Pegar: " + out);

                            Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                            //System.out.println("COPIADO");
                            if(Files.size(in) == Files.size(out)){ // Validacion de tamaño para borrar el archivo copiado a la carpeta
                            Files.delete(in);                            
                            System.out.println("ARCHIVO BORRADO " + in + " ----- " + out);
                            }else{
                                System.out.println("No se puede Borrar el archivo");
                            }
                            sessionBeanBaseFachada.actualizarLabFinProcesamiento(idArchivo);
                            listaLabFinProcesamiento = sessionBeanBaseFachada.consultarArchivosFinProcesamiento();
                           }
                            System.out.println("Archivo Guardado");
                           } else {
                            System.out.println("No Se Encontro Coincidencia");
                        }
                        contador = contador + 1;
                    }
                    //contador = contador + 1;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("La Carpeta existe o viene un Null");
            
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

    public void actualizarTablaLab() {
        sessionBeanBaseFachada.actualizarLabFinProcesamiento(1);

    }
    
    public void copiarHastaAlmacenamiento(String rutaDestino, String rutaDirectorioCopiar, String nombreFinal)throws IllegalAccessException, IOException,
        SftpException, JSchException{
    
    JSch jsch = new JSch();
    ChannelSftp sftp = null;
    Session session = null;
    String archivo = "creada.txt";
        try {
            //File tmp = new File(PATH_ARCHIVOS_LOCAL + archivo);

            session = jsch.getSession(USUARIO_LINUX, HOST_LINUX, 22);
            session.setConfig("PreferredAuthentications", "password");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(CONTRASENA_LINUX);
            session.connect();
            
            if (session != null && session.isConnected()) {
                
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                System.out.println("Ruta : " + HOST_LINUX+rutaDestino);
                //channelSftp.cd(ftpPath);
                channelSftp.connect();
                channelSftp.cd(rutaDestino);
                channelSftp.mkdir("Copiado");
                channelSftp.cd(rutaDestino+"/Copiado");
                channelSftp.put(rutaDirectorioCopiar, nombreFinal);
                
                channelSftp.exit();
                channelSftp.disconnect();
                
            }else{
                System.out.println("No existe sesion activa");
            }
            
            
        } catch (Exception e) {
            System.out.println("No se pudo realizar la conexión para comprobar archivo");
            e.printStackTrace();
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
    
}
