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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOSEPH
 */
@Entity
@Table(name = "VWCronSanitasCargaArchivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronSanitasCargaArchivos.findAll", query = "SELECT v FROM VWCronSanitasCargaArchivos v")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findById", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.id = :id")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findByNombreArchivo", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findByNombreArchivoRenombrado", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.nombreArchivoRenombrado = :nombreArchivoRenombrado")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findByUsuarioFTP", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.usuarioFTP = :usuarioFTP")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findByPasswordFTP", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.passwordFTP = :passwordFTP")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findByUsuarioRedLocal", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.usuarioRedLocal = :usuarioRedLocal")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findByPasswordRedLocal", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.passwordRedLocal = :passwordRedLocal")
    , @NamedQuery(name = "VWCronSanitasCargaArchivos.findByIp", query = "SELECT v FROM VWCronSanitasCargaArchivos v WHERE v.ip = :ip")})
public class VWCronSanitasCargaArchivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @Id
    private Integer id;    
    @Column(name = "url")
    private String url;    
    @Column(name = "nombreArchivo")
    private String nombreArchivo;    
    @Column(name = "nombreArchivoRenombrado")
    private String nombreArchivoRenombrado;    
    @Column(name = "usuarioFTP")
    private String usuarioFTP;    
    @Column(name = "passwordFTP")
    private String passwordFTP;    
    @Column(name = "usuarioRedLocal")
    private String usuarioRedLocal;    
    @Column(name = "passwordRedLocal")
    private String passwordRedLocal;    
    @Column(name = "ip")
    private String ip;

    public VWCronSanitasCargaArchivos() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNombreArchivoRenombrado() {
        return nombreArchivoRenombrado;
    }

    public void setNombreArchivoRenombrado(String nombreArchivoRenombrado) {
        this.nombreArchivoRenombrado = nombreArchivoRenombrado;
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
