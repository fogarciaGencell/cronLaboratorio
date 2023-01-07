/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOSEPH
 */
@Entity
@Table(name = "VWCronArchivosCarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronArchivosCarga.findAll", query = "SELECT v FROM VWCronArchivosCarga v")
    , @NamedQuery(name = "VWCronArchivosCarga.findByIdResultadosOrden", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.idResultadosOrden = :idResultadosOrden")
    , @NamedQuery(name = "VWCronArchivosCarga.findByIdOrden", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.idOrden = :idOrden")
    , @NamedQuery(name = "VWCronArchivosCarga.findByCreatedAt", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.createdAt = :createdAt")
    , @NamedQuery(name = "VWCronArchivosCarga.findByUpdatedAt", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.updatedAt = :updatedAt")
    , @NamedQuery(name = "VWCronArchivosCarga.findByNombreUsuarioCreacion", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.nombreUsuarioCreacion = :nombreUsuarioCreacion")
    , @NamedQuery(name = "VWCronArchivosCarga.findByUsuarioCreacion", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "VWCronArchivosCarga.findByNombreUsuarioEdicion", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.nombreUsuarioEdicion = :nombreUsuarioEdicion")
    , @NamedQuery(name = "VWCronArchivosCarga.findByUsuarioEdicion", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.usuarioEdicion = :usuarioEdicion")
    , @NamedQuery(name = "VWCronArchivosCarga.findByUrlResultado", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.urlResultado = :urlResultado")
    , @NamedQuery(name = "VWCronArchivosCarga.findByEstadoCarga", query = "SELECT v FROM VWCronArchivosCarga v WHERE v.estadoCarga = :estadoCarga")})
public class VWCronArchivosCarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idResultadosOrden")
    @Id
    private Integer idResultadosOrden;    
    @Column(name = "idOrden")
    private Integer idOrden;    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;    
    @Column(name = "nombreUsuarioCreacion")
    private String nombreUsuarioCreacion;    
    @Column(name = "usuarioCreacion")
    private String usuarioCreacion;    
    @Column(name = "nombreUsuarioEdicion")
    private String nombreUsuarioEdicion;    
    @Column(name = "usuarioEdicion")
    private String usuarioEdicion;    
    @Column(name = "urlResultado")
    private String urlResultado;
    @Column(name = "estadoCarga")
    private String estadoCarga;

    public VWCronArchivosCarga() {
    }

    public Integer getIdResultadosOrden() {
        return idResultadosOrden;
    }

    public void setIdResultadosOrden(Integer idResultadosOrden) {
        this.idResultadosOrden = idResultadosOrden;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNombreUsuarioCreacion() {
        return nombreUsuarioCreacion;
    }

    public void setNombreUsuarioCreacion(String nombreUsuarioCreacion) {
        this.nombreUsuarioCreacion = nombreUsuarioCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getNombreUsuarioEdicion() {
        return nombreUsuarioEdicion;
    }

    public void setNombreUsuarioEdicion(String nombreUsuarioEdicion) {
        this.nombreUsuarioEdicion = nombreUsuarioEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public String getUrlResultado() {
        return urlResultado;
    }

    public void setUrlResultado(String urlResultado) {
        this.urlResultado = urlResultado;
    }

    public String getEstadoCarga() {
        return estadoCarga;
    }

    public void setEstadoCarga(String estadoCarga) {
        this.estadoCarga = estadoCarga;
    }
    
}
