/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "VWCronSelfFileId")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronSelfFileId.findAll", query = "SELECT v FROM VWCronSelfFileId v")
    , @NamedQuery(name = "VWCronSelfFileId.findByIdPeticion", query = "SELECT v FROM VWCronSelfFileId v WHERE v.idPeticion = :idPeticion")
    , @NamedQuery(name = "VWCronSelfFileId.findByEstado", query = "SELECT v FROM VWCronSelfFileId v WHERE v.estado = :estado")
    , @NamedQuery(name = "VWCronSelfFileId.findByIdGenomeFile", query = "SELECT v FROM VWCronSelfFileId v WHERE v.idGenomeFile = :idGenomeFile")})
public class VWCronSelfFileId implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "idPeticion")
    @Id
    private Integer idPeticion;
    @Size(max = 20)
    @Column(name = "estado")
    private String estado;
    @Size(max = 200)
    @Column(name = "idGenomeFile")
    private String idGenomeFile;

    public VWCronSelfFileId() {
    }

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Integer idPeticion) {
        this.idPeticion = idPeticion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdGenomeFile() {
        return idGenomeFile;
    }

    public void setIdGenomeFile(String idGenomeFile) {
        this.idGenomeFile = idGenomeFile;
    }
    
}
