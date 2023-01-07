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
@Table(name = "VWCronSelfdecodeListos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronSelfdecodeListos.findAll", query = "SELECT v FROM VWCronSelfdecodeListos v")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findById", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.id = :id")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByIdPeticion", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.idPeticion = :idPeticion")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByIdentificador", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.identificador = :identificador")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByUrl", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.url = :url")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByNombreArchivo", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByUsuarioFTP", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.usuarioFTP = :usuarioFTP")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByPasswordFTP", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.passwordFTP = :passwordFTP")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByUsuarioRedLocal", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.usuarioRedLocal = :usuarioRedLocal")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByPasswordRedLocal", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.passwordRedLocal = :passwordRedLocal")
    , @NamedQuery(name = "VWCronSelfdecodeListos.findByIp", query = "SELECT v FROM VWCronSelfdecodeListos v WHERE v.ip = :ip")})
public class VWCronSelfdecodeListos implements Serializable {

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
    @Size(max = 45)
    @Column(name = "identificador")
    private String identificador;
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
    @Size(max = 45)
    @Column(name = "ip")
    private String ip;

    public VWCronSelfdecodeListos() {
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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
