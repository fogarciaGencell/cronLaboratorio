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
@Table(name = "VWCronSelfdecodeBorrar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronSelfdecodeBorrar.findAll", query = "SELECT v FROM VWCronSelfdecodeBorrar v")
    , @NamedQuery(name = "VWCronSelfdecodeBorrar.findById", query = "SELECT v FROM VWCronSelfdecodeBorrar v WHERE v.id = :id")
    , @NamedQuery(name = "VWCronSelfdecodeBorrar.findByIdPeticion", query = "SELECT v FROM VWCronSelfdecodeBorrar v WHERE v.idPeticion = :idPeticion")
    , @NamedQuery(name = "VWCronSelfdecodeBorrar.findByIdentificador", query = "SELECT v FROM VWCronSelfdecodeBorrar v WHERE v.identificador = :identificador")
    , @NamedQuery(name = "VWCronSelfdecodeBorrar.findByNombreArchivo", query = "SELECT v FROM VWCronSelfdecodeBorrar v WHERE v.nombreArchivo = :nombreArchivo")})
public class VWCronSelfdecodeBorrar implements Serializable {

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
    @Size(max = 200)
    @Column(name = "nombreArchivo")
    private String nombreArchivo;

    public VWCronSelfdecodeBorrar() {
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

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
}
