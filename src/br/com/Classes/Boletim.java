/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "boletim")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boletim.findAll", query = "SELECT b FROM Boletim b"),
    @NamedQuery(name = "Boletim.findByIdBoletim", query = "SELECT b FROM Boletim b WHERE b.idBoletim = :idBoletim"),
    @NamedQuery(name = "Boletim.findByNota", query = "SELECT b FROM Boletim b WHERE b.nota = :nota")})
public class Boletim implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idBoletim")
    private Integer idBoletim;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nota")
    private Float nota;

    public Boletim() {
    }

    public Boletim(Integer idBoletim) {
        this.idBoletim = idBoletim;
    }

    public Integer getIdBoletim() {
        return idBoletim;
    }

    public void setIdBoletim(Integer idBoletim) {
        this.idBoletim = idBoletim;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBoletim != null ? idBoletim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boletim)) {
            return false;
        }
        Boletim other = (Boletim) object;
        if ((this.idBoletim == null && other.idBoletim != null) || (this.idBoletim != null && !this.idBoletim.equals(other.idBoletim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Boletim[ idBoletim=" + idBoletim + " ]";
    }
    
}
