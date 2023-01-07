/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.selfdecode;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gencell.croncargaarchivos.ejb.SessionBeanBaseFachadaLocal;
import gencell.croncargaarchivos.entities.LogCargueArchivosSelf;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.FileEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.util.Date;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Fredy G
 */
public class SelfdecodeServiceProcess {

    @EJB
    private SessionBeanBaseFachadaLocal sessionBeanBaseFachada;
    private static String URL = "";
    private String URLCrearGenomeFile = "";
    private HttpPost post;
    private HttpPut put;
    private HttpGet get;
    private HttpDelete delete;
    private HttpPatch patch;
    private DefaultHttpClient client;
    private HttpResponse response;
    private String token = "";
    private String code = "";

    public SelfdecodeServiceProcess() {
        
        if (sessionBeanBaseFachada == null) {
            System.out.println("****************  fachada null Cron CARGA ARCHIVOS: ");
            sessionBeanBaseFachada = this.lookupSessionBeanBaseFachadaLocal();
            if (sessionBeanBaseFachada != null) {
                System.out.println("****************  fachada obtenida Cron CARGA ARCHIVOS: ");
            }
        }
        
        try {
            client = getDefaultHttpClient();
            response = null;
            URL = "https://qa-selfdecode.com/service/";//pruebas
            token = "token 3434ab21989cea68ab23b95af38e0d55d76849e6";
        } catch (Exception ex) {
            System.out.println("Error en el consumo del service " + ex);
        }
    }

    public String createGenomeFile(String profile) {
        String message = "";
        try {
            // Variables
            Gson gson = new Gson();
            post = new HttpPost(URL + "genome-files/file/");
            post.setHeader("Accept", "*/*");
            post.setHeader("Content-Type", "application/json;");
            post.setHeader("Authorization", this.token);
            String error = "";
            response = null;

            FileDAO fileDAO = new FileDAO();
            fileDAO.setProfile_id(profile);;
            fileDAO.setUpload_provider(this.token);

            String jsonData = gson.toJson(fileDAO);
            post.setEntity(new StringEntity(jsonData));
            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(post);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (result != null && !result.toString().isEmpty()) {

                if (response.getStatusLine().getStatusCode() == 201) {

                    JSONObject object = new JSONObject(result.toString());
                    message = object.get("id").toString();
                }
                if (response.getStatusLine().getStatusCode() == 400) {
                    JSONObject object = new JSONObject(result.toString());
                    error = object.get("profile_id").toString();
                    System.out.println("ERROR 400 creando el File : " + error);
                    message = null;
                }
                if (response.getStatusLine().getStatusCode() == 403) {
                    JSONObject object = new JSONObject(result.toString());
                    error = object.get("detail").toString();
                    System.out.println("ERROR 403 creando el File: " + error);
                    message = null;
                }
            } else {
                System.out.println(result.toString());
                message = null;
            }
            // System.out.println("ID Genome File: " + message);
        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    // -------------------------------------------------------------------------------------------------
    public StartUploadDAO startUpload(String filename, String filenameReverse, String genomeFile) {
        StartUploadDAO startUploadDAO = new StartUploadDAO();
        try {
            // Variables
            Gson gson = new Gson();
            post = new HttpPost(URL + "genome-files/upload-raw-reads/");
            post.setHeader("Accept", "*/*");
            post.setHeader("Content-Type", "application/json;");
            post.setHeader("Authorization", this.token);
            String id = "";
            response = null;
            FileDAO fileDAO = new FileDAO();

            fileDAO.setFilename(filename);
            fileDAO.setGenome_file(genomeFile);
            fileDAO.setFilename_reverse(filenameReverse);

            String jsonData = gson.toJson(fileDAO);
            post.setEntity(new StringEntity(jsonData));
            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(post);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (result != null && !result.toString().isEmpty()) {

                if (response.getStatusLine().getStatusCode() == 201) {

                    JSONObject object = new JSONObject(result.toString());
                    JSONArray array = new JSONArray(object.get("upload_url").toString());
                    startUploadDAO.setId(object.get("id").toString());
                    startUploadDAO.setGenomeFile(object.get("genome_file").toString());
                    startUploadDAO.setUploadUrl(object.get("upload_url").toString());
                    startUploadDAO.setUrl1(array.getString(0));
                    startUploadDAO.setUrl2(array.getString(1));
                    startUploadDAO.setExpiresAt(object.get("expires_at").toString());
                } else {
                    startUploadDAO.setUploadUrl("ERROR");
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return startUploadDAO;

    }

    //--------------------------------------------------------------------------------------------------
    public void uploadFile(String fileName, String url) {
        //System.out.println("INICIA UPLOAD FILE ");
        try {
            // Variables
            Gson gson = new Gson();
            put = new HttpPut(url);
            put.setHeader("Accept", "*/*");
            String id = "";
            response = null;
            
            File file = new File(fileName);
            FileEntity mpEntity = new FileEntity(file);
            
            put.setEntity(mpEntity);
            System.out.println("Por favor Espere... \n Enviando Archivo " );
            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(put);
            //System.out.println("EJECUTO EL CARGUE DEL ARCHIVO POR PUT");

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (result != null && !result.toString().isEmpty()) {

                if (response.getStatusLine().getStatusCode() == 201) {

                    JSONObject object = new JSONObject(result.toString());

                }


            }
        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String completeUpload(String uploadId) {
        //Variables
        String retorno = "";
        String mensaje = "";
        String estado = "";
        String codigo = "";
        try {
            // Variables
            Gson gson = new Gson();
            delete = new HttpDelete(URL + "genome-files/upload-raw-reads/" + uploadId + "/");
            delete.setHeader("Accept", "*/*");
            //delete.setHeader("id", uploadId);
            delete.setHeader("Content-Type", "application/json;");
            delete.setHeader("Authorization", this.token);

            response = null;

            delete.started();
            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(delete);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (result != null && !result.toString().isEmpty()) {

                if (response.getStatusLine().getStatusCode() == 200) {
                    JSONObject object = new JSONObject(result.toString());
                    codigo = ("" + response.getStatusLine().getStatusCode());
                    mensaje = result.toString();
                    estado = (object.get("did_succeed").toString());
                    //System.out.println("Codigo " + codigo);
                    System.out.println("Complete Upload: " + mensaje);
                    retorno = estado;
                } else {
                    retorno = "false";
                    System.out.println(result.toString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("FINALIZA COMPLETE UPLOAD");

        return retorno;
    }

    public String startScanJob(String fileId) {

        String id = "";

        //System.out.println("INICIA START_SCAN_JOB");
        try {
            // Variables
            Gson gson = new Gson();
            post = new HttpPost(URL + "genome-files/scan-job/");
            post.setHeader("Accept", "*/*");
            post.setHeader("Content-Type", "application/json;");
            post.setHeader("Authorization", this.token);
            response = null;
            FileDAO fileDAO = new FileDAO();

            fileDAO.setGenome_file(fileId);

            String jsonData = gson.toJson(fileDAO);
            post.setEntity(new StringEntity(jsonData));
            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(post);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (result != null && !result.toString().isEmpty()) {

                if (response.getStatusLine().getStatusCode() == 201) {

                    JSONObject object = new JSONObject(result.toString());
                    id = object.get("id").toString();
                    //System.out.println("Respuesta SCAN: " + result.toString());

                } else {
                    id = "not found";
                    //System.out.println("Respuesta SCAN: " + result.toString());
                }

            }
            //System.out.println("Respuesta: " + id);
        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("ID start_scan_job : " + id );
        //System.out.println(URL + "genome-files/scan-job/");
        //System.out.println("FINALIZA START_SCAN_JOB");

        return id;
    }

    // --------------------------------------------------------------------------------------------------
    // Proceso Main
    public void iniciarEnvioArchivoSelfdecode(String profile, String forwardReads, String reverseReads, Integer idPersona, Integer idPeticion)  {
       
        // VARIABLES    
        Gson gson = new Gson();
        String fileId = "";
        String fileIdConsulta = "";
        String uploadId = "";
        String url1 = "";
        String url2 = "";
        String estadoScan = "";
        String mensaje = "";
        String idScanJob = "";
        String estadoFile = "";
        String succedd = "";
        StartUploadDAO startUploadDAO = new StartUploadDAO();
        LogCargueArchivosSelf archivosSelf = new LogCargueArchivosSelf();
        EnviarArchivosSelfdecode enviarArchivosSelfdecode = new EnviarArchivosSelfdecode();
        
        //System.out.println("Inicia Proceso ");
        fileId = createGenomeFile(profile);

        //Variables de los datos enviados a selfdecode
        enviarArchivosSelfdecode.setProfile(profile);
        enviarArchivosSelfdecode.setForwardReads(forwardReads);
        enviarArchivosSelfdecode.setReverseReads(reverseReads);
        
        String jsonData = gson.toJson(enviarArchivosSelfdecode);
        
        // Valida si se creo el file_id de selfdecode para continuar
        if (fileId != null) {

            startUploadDAO = startUpload(forwardReads, reverseReads, fileId);

            uploadId = startUploadDAO.getId();
            url1 = startUploadDAO.getUrl1();
            url2 = startUploadDAO.getUrl2();
            //System.out.println("Inicia Cargue de Archivos");
            uploadFile(forwardReads, url1);
            uploadFile(reverseReads, url2);
            //System.out.println("Por favor espere.....");
            succedd = completeUpload(uploadId);

            if (succedd == "true") {
                mensaje = "Exitoso";
                sessionBeanBaseFachada.actualizarEstadoBiolabSelfdecode(idPeticion, "ENVIADO-SELFDECODE", profile, "80");
            }
            if (succedd == "false") {
                mensaje = "Fallido";
                sessionBeanBaseFachada.actualizarEstadoBiolabSelfdecode(idPeticion, "UPLOADING-ERROR", profile, "85");
            }

            idScanJob = startScanJob(fileId);
            estadoScan = obtenerEstadoFile(fileId);

            //System.out.println("Estado File: " + estadoScan);
            
            // Guarda datos en la tabla LogCargueArchivosSelf como registro 
            archivosSelf.setIdProfileSelfdecode(profile);
            archivosSelf.setIdGenomeFile(fileId);
            archivosSelf.setEstado(estadoScan);
            archivosSelf.setIdPersonas(idPersona);
            archivosSelf.setIdPeticion(idPeticion);
            archivosSelf.setDatosEnviados(jsonData);
            archivosSelf.setFechaEnvio(new Date().toString());
            archivosSelf.setFechaEstadoSelfdecode("");
            
            
            //System.out.println("Guardando....");
            sessionBeanBaseFachada.Crear(archivosSelf);
            //System.out.println("Guardado");

        } else {
            
            mensaje = "No se creo el id del GenomeFile";
            System.out.println("No se creo el idFile");
            fileIdConsulta = obtenerIdFile(profile); // Devuelve el Id del File proporcionando el profile 
            estadoFile = obtenerEstadoFile(fileIdConsulta); // Para consultar el estado de un File
            //System.out.println("File: " + fileIdConsulta + " en estado " + estadoFile);
            //System.out.println("Procediendo a eliminar el File......");
            deleteFile(fileIdConsulta); // Elimina el File 

            fileId = createGenomeFile(profile);

            if (fileId != null) {

                startUploadDAO = startUpload(forwardReads, reverseReads, fileId);

                uploadId = startUploadDAO.getId();
                url1 = startUploadDAO.getUrl1();
                url2 = startUploadDAO.getUrl2();
                uploadFile(forwardReads, url1);
                uploadFile(reverseReads, url2);

                succedd = completeUpload(uploadId);
           
                if (succedd == "true") {
                    mensaje = "Exitoso";
                    sessionBeanBaseFachada.actualizarEstadoBiolabSelfdecode(idPeticion, "ENVIADO-SELFDECODE", profile, "80");
                }
                if (succedd == "false") {
                    mensaje = "Fallido";
                    sessionBeanBaseFachada.actualizarEstadoBiolabSelfdecode(idPeticion, "UPLOADING-ERROR", profile, "85");
                }

                idScanJob = startScanJob(fileId);
                estadoScan = obtenerEstadoFile(fileId);

                //System.out.println("Estado File: " + estadoScan);

                // Guarda datos en la tabla como registro 
                archivosSelf.setIdProfileSelfdecode(profile);
                archivosSelf.setIdGenomeFile(fileId);
                archivosSelf.setEstado(estadoScan);
                archivosSelf.setIdPersonas(idPersona);
                archivosSelf.setIdPeticion(idPeticion);
                archivosSelf.setDatosEnviados(jsonData);
                archivosSelf.setFechaEnvio(new Date().toString());
                archivosSelf.setFechaEstadoSelfdecode("");

                sessionBeanBaseFachada.Crear(archivosSelf);

            } else {
                System.out.println("Error Fatal");

            }

        }
    }
    

    // Envio con curl2
    public void ejecutarCurl2(String ruta, String urlCurl) {
        //System.out.println("INICIA EJECUTARcURL WINDOWS");
        String curlSelf = "curl --upload-file " + (char) 34 + ruta + (char) 34 + " \"" + urlCurl + "\"";
        try {

            String command = (curlSelf);
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor(60L, TimeUnit.SECONDS);
            //Thread.sleep(120000);
            p.destroyForcibly();
            System.out.println("Fin del cargue");

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";

            while ((line = reader.readLine()) != null) {

                System.out.println(line + "\n");

            }
        } catch (Exception ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("FINALIZA EJECUTAR CURL WINDOWS");
    }

    public void ejecutarCurlLinux(String ruta, String urlCurl) {

        //String curlSelf = "curl --upload-file " + (char) 34 + "/home/linux/Descargas/V350097149_L01_1_1.fq.gz" + (char) 34 + " \ http://loquesigue \"";
        String curlSelf = "curl --upload-file " + (char) 34 + ruta + (char) 34 + " \"" + urlCurl + "\"";

        try {

            String[] command = {"sh", "-c", curlSelf};
            System.out.println("Comando Curl: " + curlSelf);
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";

            while ((line = reader.readLine()) != null) {

                //output.append(line + "\n");
                System.out.println(line + "\n");

            }

        } catch (Exception ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String obtenerEstadoFile(String id_file) {

        String estado = "";

        try {

            // Variables
            get = new HttpGet(URL + "genome-files/file/" + id_file + "/");
            get.setHeader("Accept", "*/*");
            get.setHeader("Content-Type", "application/json;");
            get.setHeader("Authorization", this.token);
            response = null;

            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(get);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (result != null && !result.toString().isEmpty()) {

                if (response.getStatusLine().getStatusCode() == 200) {

                    JSONObject object = new JSONObject(result.toString());
                    estado = object.get("status").toString();
                    //System.out.println("Result Obtener Estado: " + result.toString());
                } else {
                    System.out.println("Result Obtener Estado: " + result.toString());
                }
            }

            //System.out.println("Estado: " + estado );
        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return estado;
    }

     public void deleteFile(String fileId) {
        String retorno = "";
        try {
            // Variables
            Gson gson = new Gson();
            delete = new HttpDelete(URL + "genome-files/file/" + fileId + "/");
            delete.setHeader("Accept", "*/*");
            delete.setHeader("Content-Type", "application/json;");
            delete.setHeader("Authorization", this.token);

            response = null;

            delete.started();
            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(delete);
            
            System.out.println("File Eliminado");

        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("FINALIZA COMPLETE UPLOAD");

    }
     
      public String obtenerIdFile(String profile) {

        String retorno = "";

        try {

            // Variables
            get = new HttpGet(URL + "genome-files/file/");
            get.setHeader("Accept", "*/*");
            get.setHeader("Content-Type", "application/json;");
            get.setHeader("Authorization", this.token);
            response = null;

            String line = "";
            StringBuffer result = new StringBuffer();
            response = client.execute(get);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (result != null && !result.toString().isEmpty()) {

                if (response.getStatusLine().getStatusCode() == 200) {
                    JsonParser parser = new JsonParser();
                    JsonArray gsonArr = parser.parse(result.toString()).getAsJsonArray();
                    for (JsonElement obj : gsonArr) {
                    JsonObject gsonObj = obj.getAsJsonObject();
                    String idFile = gsonObj.get("id").getAsString();
                    String idProfile = gsonObj.get("profile_id").getAsString();
                    if (idProfile.equals(profile)){
                        retorno = idFile;
                    }
                    }
                } else {
                    System.out.println("Result Obtener Estado: " + result.toString());
                }
            }

            //System.out.println("Estado: " + estado );
        } catch (IOException ex) {
            Logger.getLogger(SelfdecodeServiceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    private static DefaultHttpClient getDefaultHttpClient() throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        SSLContext ssl_ctx = SSLContext.getInstance("TLS");
        TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String t) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String t) {
            }
        }};
        ssl_ctx.init(null, certs, new SecureRandom());
        SSLSocketFactory ssf = new SSLSocketFactory(ssl_ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = httpClient.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
        return new DefaultHttpClient(ccm, httpClient.getParams());
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
