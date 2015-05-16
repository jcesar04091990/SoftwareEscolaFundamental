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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findByDataEntradaEscola", query = "SELECT a FROM Aluno a WHERE a.dataEntradaEscola = :dataEntradaEscola"),
    @NamedQuery(name = "Aluno.findByEmail", query = "SELECT a FROM Aluno a WHERE a.email = :email"),
    @NamedQuery(name = "Aluno.findByDataNasc", query = "SELECT a FROM Aluno a WHERE a.dataNasc = :dataNasc"),
    @NamedQuery(name = "Aluno.findBySetor", query = "SELECT a FROM Aluno a WHERE a.setor = :setor"),
    @NamedQuery(name = "Aluno.findByIdAluno", query = "SELECT a FROM Aluno a WHERE a.idAluno = :idAluno"),
    @NamedQuery(name = "Aluno.findByEndAluno", query = "SELECT a FROM Aluno a WHERE a.endAluno = :endAluno"),
    @NamedQuery(name = "Aluno.findByRgAluno", query = "SELECT a FROM Aluno a WHERE a.rgAluno = :rgAluno"),
    @NamedQuery(name = "Aluno.findByNomeAluno", query = "SELECT a FROM Aluno a WHERE a.nomeAluno = :nomeAluno"),
    @NamedQuery(name = "Aluno.findByTelAluno", query = "SELECT a FROM Aluno a WHERE a.telAluno = :telAluno"),
    @NamedQuery(name = "Aluno.findByIdResponsavel", query = "SELECT a FROM Aluno a WHERE a.idResponsavel = :idResponsavel")})
public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "dataEntradaEscola")
    @Temporal(TemporalType.DATE)
    private Date dataEntradaEscola;
    @Column(name = "email")
    private String email;
    @Column(name = "dataNasc")
    @Temporal(TemporalType.DATE)
    private Date dataNasc;
    @Column(name = "setor")
    private String setor;
    @Id
    @Basic(optional = false)
    @Column(name = "idAluno")
    private Integer idAluno;
    @Column(name = "endAluno")
    private String endAluno;
    @Column(name = "rgAluno")
    private String rgAluno;
    @Column(name = "nomeAluno")
    private String nomeAluno;
    @Column(name = "telAluno")
    private String telAluno;
    @Column(name = "idResponsavel")
    private Integer idResponsavel;
    @JoinColumn(name = "codFunc", referencedColumnName = "codFunc")
    @ManyToOne
    private Funcionario codFunc;

    public Aluno() {
    }

    public Aluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Date getDataEntradaEscola() {
        return dataEntradaEscola;
    }

    public void setDataEntradaEscola(Date dataEntradaEscola) {
        this.dataEntradaEscola = dataEntradaEscola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public String getEndAluno() {
        return endAluno;
    }

    public void setEndAluno(String endAluno) {
        this.endAluno = endAluno;
    }

    public String getRgAluno() {
        return rgAluno;
    }

    public void setRgAluno(String rgAluno) {
        this.rgAluno = rgAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getTelAluno() {
        return telAluno;
    }

    public void setTelAluno(String telAluno) {
        this.telAluno = telAluno;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Funcionario getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Funcionario codFunc) {
        this.codFunc = codFunc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAluno != null ? idAluno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.idAluno == null && other.idAluno != null) || (this.idAluno != null && !this.idAluno.equals(other.idAluno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Aluno[ idAluno=" + idAluno + " ]";
    }

    void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
