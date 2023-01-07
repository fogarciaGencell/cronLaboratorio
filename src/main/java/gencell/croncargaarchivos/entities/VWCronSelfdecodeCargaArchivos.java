/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "VWCronSelfdecodeCargaArchivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findAll", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findById", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.id = :id")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findByIdPeticion", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.idPeticion = :idPeticion")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findByUrl", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.url = :url")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findByNombreArchivo", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findByUsuarioFTP", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.usuarioFTP = :usuarioFTP")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findByPasswordFTP", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.passwordFTP = :passwordFTP")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findByUsuarioRedLocal", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.usuarioRedLocal = :usuarioRedLocal")
    , @NamedQuery(name = "VWCronSelfdecodeCargaArchivos.findByPasswordRedLocal", query = "SELECT v FROM VWCronSelfdecodeCargaArchivos v WHERE v.passwordRedLocal = :passwordRedLocal")})
public class VWCronSelfdecodeCargaArchivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @Id
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPeticion")
    private int idPeticion;
    @Size(max = 46)
    @Column(name = "URL")
    private String url;
    @Size(max = 200)
    @Column(name = "nombreArchivo")
    private String nombreArchivo;
    @Size(max = 45)
    @Column(name = "usuarioFTP")
    private String usuarioFTP;
    @Size(max = 100)
    @Column(name = "passwordFTP")
    private String passwordFTP;
    @Size(max = 45)
    @Column(name = "usuarioRedLocal")
    private String usuarioRedLocal;
    @Size(max = 100)
    @Column(name = "passwordRedLocal")
    private String passwordRedLocal;
    @Column(name = "ip")
    private String ip;

    public VWCronSelfdecodeCargaArchivos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(int idPeticion) {
        this.idPeticion = idPeticion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getUsuarioFTP() {
        return usuarioFTP;
    }

    public void setUsuarioFTP(String usuarioFTP) {
        this.usuarioFTP = usuarioFTP;
    }

    public String getPasswordFTP() {
        return passwordFTP;
    }

    public void setPasswordFTP(String passwordFTP) {
        this.passwordFTP = passwordFTP;
    }

    public String getUsuarioRedLocal() {
        return usuarioRedLocal;
    }

    public void setUsuarioRedLocal(String usuarioRedLocal) {
        this.usuarioRedLocal = usuarioRedLocal;
    }

    public String getPasswordRedLocal() {
        return passwordRedLocal;
    }

    public void setPasswordRedLocal(String passwordRedLocal) {
        this.passwordRedLocal = passwordRedLocal;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
}
