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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "LabFinProcesamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LabFinProcesamiento.findAll", query = "SELECT l FROM LabFinProcesamiento l")
    , @NamedQuery(name = "LabFinProcesamiento.findById", query = "SELECT l FROM LabFinProcesamiento l WHERE l.id = :id")
    , @NamedQuery(name = "LabFinProcesamiento.findByIdPeticion", query = "SELECT l FROM LabFinProcesamiento l WHERE l.idPeticion = :idPeticion")
    , @NamedQuery(name = "LabFinProcesamiento.findByAdaptador", query = "SELECT l FROM LabFinProcesamiento l WHERE l.adaptador = :adaptador")
    , @NamedQuery(name = "LabFinProcesamiento.findByFlowCell", query = "SELECT l FROM LabFinProcesamiento l WHERE l.flowCell = :flowCell")
    , @NamedQuery(name = "LabFinProcesamiento.findByLinea", query = "SELECT l FROM LabFinProcesamiento l WHERE l.linea = :linea")
    , @NamedQuery(name = "LabFinProcesamiento.findByMontaje", query = "SELECT l FROM LabFinProcesamiento l WHERE l.montaje = :montaje")
    , @NamedQuery(name = "LabFinProcesamiento.findByRuta", query = "SELECT l FROM LabFinProcesamiento l WHERE l.ruta = :ruta")
    , @NamedQuery(name = "LabFinProcesamiento.findByNombreArchivoCompleto", query = "SELECT l FROM LabFinProcesamiento l WHERE l.nombreArchivoCompleto = :nombreArchivoCompleto")
    , @NamedQuery(name = "LabFinProcesamiento.findByProcesado", query = "SELECT l FROM LabFinProcesamiento l WHERE l.procesado = :procesado")
    , @NamedQuery(name = "LabFinProcesamiento.findByPorcentaje", query = "SELECT l FROM LabFinProcesamiento l WHERE l.porcentaje = :porcentaje")
    , @NamedQuery(name = "LabFinProcesamiento.findByEstado", query = "SELECT l FROM LabFinProcesamiento l WHERE l.estado = :estado")})
public class LabFinProcesamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idPeticion")
    private Integer idPeticion;
    @Column(name = "adaptador")
    private Integer adaptador;
    @Size(max = 100)
    @Column(name = "flowCell")
    private String flowCell;
    @Size(max = 100)
    @Column(name = "linea")
    private String linea;
    @Size(max = 100)
    @Column(name = "montaje")
    private String montaje;
    @Size(max = 100)
    @Column(name = "ruta")
    private String ruta;
    @Size(max = 100)
    @Column(name = "nombreArchivoCompleto")
    private String nombreArchivoCompleto;
    @Size(max = 1)
    @Column(name = "procesado")
    private String procesado;
    @Column(name = "porcentaje")
    private Integer porcentaje;
    @Size(max = 100)
    @Column(name = "estado")
    private String estado;

    public LabFinProcesamiento() {
    }

    public LabFinProcesamiento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Integer idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Integer getAdaptador() {
        return adaptador;
    }

    public void setAdaptador(Integer adaptador) {
        this.adaptador = adaptador;
    }

    public String getFlowCell() {
        return flowCell;
    }

    public void setFlowCell(String flowCell) {
        this.flowCell = flowCell;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getMontaje() {
        return montaje;
    }

    public void setMontaje(String montaje) {
        this.montaje = montaje;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombreArchivoCompleto() {
        return nombreArchivoCompleto;
    }

    public void setNombreArchivoCompleto(String nombreArchivoCompleto) {
        this.nombreArchivoCompleto = nombreArchivoCompleto;
    }

    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabFinProcesamiento)) {
            return false;
        }
        LabFinProcesamiento other = (LabFinProcesamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gencell.croncargaarchivos.entities.LabFinProcesamiento[ id=" + id + " ]";
    }
    
}
