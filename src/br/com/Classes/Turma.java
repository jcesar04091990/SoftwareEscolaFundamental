/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Classes;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "turma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turma.findAll", query = "SELECT t FROM Turma t"),
    @NamedQuery(name = "Turma.findByPeriodo", query = "SELECT t FROM Turma t WHERE t.periodo = :periodo"),
    @NamedQuery(name = "Turma.findBySerie", query = "SELECT t FROM Turma t WHERE t.serie = :serie"),
    @NamedQuery(name = "Turma.findByDataAbertura", query = "SELECT t FROM Turma t WHERE t.dataAbertura = :dataAbertura"),
    @NamedQuery(name = "Turma.findByDataEncerramento", query = "SELECT t FROM Turma t WHERE t.dataEncerramento = :dataEncerramento"),
    @NamedQuery(name = "Turma.findByIdTurma", query = "SELECT t FROM Turma t WHERE t.idTurma = :idTurma")})
public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "periodo")
    private String periodo;
    @Column(name = "serie")
    private Integer serie;
    @Column(name = "dataAbertura")
    @Temporal(TemporalType.DATE)
    private Date dataAbertura;
    @Column(name = "dataEncerramento")
    @Temporal(TemporalType.DATE)
    private Date dataEncerramento;
    @Id
    @Basic(optional = false)
    @Column(name = "idTurma")
    private Integer idTurma;

    public Turma() {
    }

    public Turma(Integer idTurma) {
        this.idTurma = idTurma;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurma != null ? idTurma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.idTurma == null && other.idTurma != null) || (this.idTurma != null && !this.idTurma.equals(other.idTurma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Turma[ idTurma=" + idTurma + " ]";
    }
    
}
