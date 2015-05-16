/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Classes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "funcionario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByHoraEntrada", query = "SELECT f FROM Funcionario f WHERE f.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "Funcionario.findByHoraSaida", query = "SELECT f FROM Funcionario f WHERE f.horaSaida = :horaSaida"),
    @NamedQuery(name = "Funcionario.findBySalarioInicial", query = "SELECT f FROM Funcionario f WHERE f.salarioInicial = :salarioInicial"),
    @NamedQuery(name = "Funcionario.findByCtps", query = "SELECT f FROM Funcionario f WHERE f.ctps = :ctps"),
    @NamedQuery(name = "Funcionario.findByTituloEleitor", query = "SELECT f FROM Funcionario f WHERE f.tituloEleitor = :tituloEleitor"),
    @NamedQuery(name = "Funcionario.findByRg", query = "SELECT f FROM Funcionario f WHERE f.rg = :rg"),
    @NamedQuery(name = "Funcionario.findByCpf", query = "SELECT f FROM Funcionario f WHERE f.cpf = :cpf"),
    @NamedQuery(name = "Funcionario.findByCnh", query = "SELECT f FROM Funcionario f WHERE f.cnh = :cnh"),
    @NamedQuery(name = "Funcionario.findByDtNascFunc", query = "SELECT f FROM Funcionario f WHERE f.dtNascFunc = :dtNascFunc"),
    @NamedQuery(name = "Funcionario.findByEndFunc", query = "SELECT f FROM Funcionario f WHERE f.endFunc = :endFunc"),
    @NamedQuery(name = "Funcionario.findByNomeFunc", query = "SELECT f FROM Funcionario f WHERE f.nomeFunc = :nomeFunc"),
    @NamedQuery(name = "Funcionario.findByCodFunc", query = "SELECT f FROM Funcionario f WHERE f.codFunc = :codFunc"),
    @NamedQuery(name = "Funcionario.findByAdmissao", query = "SELECT f FROM Funcionario f WHERE f.admissao = :admissao"),
    @NamedQuery(name = "Funcionario.findByIdEstCivil", query = "SELECT f FROM Funcionario f WHERE f.idEstCivil = :idEstCivil")})
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "horaEntrada")
    @Temporal(TemporalType.TIME)
    private Date horaEntrada;
    @Column(name = "horaSaida")
    @Temporal(TemporalType.TIME)
    private Date horaSaida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salarioInicial")
    private Float salarioInicial;
    @Column(name = "ctps")
    private String ctps;
    @Column(name = "tituloEleitor")
    private String tituloEleitor;
    @Column(name = "rg")
    private String rg;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "cnh")
    private String cnh;
    @Column(name = "dtNascFunc")
    @Temporal(TemporalType.DATE)
    private Date dtNascFunc;
    @Column(name = "endFunc")
    private String endFunc;
    @Column(name = "nomeFunc")
    private String nomeFunc;
    @Id
    @Basic(optional = false)
    @Column(name = "codFunc")
    private Integer codFunc;
    @Column(name = "admissao")
    @Temporal(TemporalType.DATE)
    private Date admissao;
    @Column(name = "idEstCivil")
    private Integer idEstCivil;
    @OneToMany(mappedBy = "codFunc")
    private Collection<Telfuncionario> telfuncionarioCollection;
    @OneToMany(mappedBy = "codFunc")
    private Collection<Colaborador> colaboradorCollection;
    @OneToMany(mappedBy = "codFunc")
    private Collection<Aluno> alunoCollection;
    @OneToMany(mappedBy = "codFunc")
    private Collection<Professor> professorCollection;

    public Funcionario() {
    }

    public Funcionario(Integer codFunc) {
        this.codFunc = codFunc;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Date horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Float getSalarioInicial() {
        return salarioInicial;
    }

    public void setSalarioInicial(Float salarioInicial) {
        this.salarioInicial = salarioInicial;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public Date getDtNascFunc() {
        return dtNascFunc;
    }

    public void setDtNascFunc(Date dtNascFunc) {
        this.dtNascFunc = dtNascFunc;
    }

    public String getEndFunc() {
        return endFunc;
    }

    public void setEndFunc(String endFunc) {
        this.endFunc = endFunc;
    }

    public String getNomeFunc() {
        return nomeFunc;
    }

    public void setNomeFunc(String nomeFunc) {
        this.nomeFunc = nomeFunc;
    }

    public Integer getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Integer codFunc) {
        this.codFunc = codFunc;
    }

    public Date getAdmissao() {
        return admissao;
    }

    public void setAdmissao(Date admissao) {
        this.admissao = admissao;
    }

    public Integer getIdEstCivil() {
        return idEstCivil;
    }

    public void setIdEstCivil(Integer idEstCivil) {
        this.idEstCivil = idEstCivil;
    }

    @XmlTransient
    public Collection<Telfuncionario> getTelfuncionarioCollection() {
        return telfuncionarioCollection;
    }

    public void setTelfuncionarioCollection(Collection<Telfuncionario> telfuncionarioCollection) {
        this.telfuncionarioCollection = telfuncionarioCollection;
    }

    @XmlTransient
    public Collection<Colaborador> getColaboradorCollection() {
        return colaboradorCollection;
    }

    public void setColaboradorCollection(Collection<Colaborador> colaboradorCollection) {
        this.colaboradorCollection = colaboradorCollection;
    }

    @XmlTransient
    public Collection<Aluno> getAlunoCollection() {
        return alunoCollection;
    }

    public void setAlunoCollection(Collection<Aluno> alunoCollection) {
        this.alunoCollection = alunoCollection;
    }

    @XmlTransient
    public Collection<Professor> getProfessorCollection() {
        return professorCollection;
    }

    public void setProfessorCollection(Collection<Professor> professorCollection) {
        this.professorCollection = professorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codFunc != null ? codFunc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.codFunc == null && other.codFunc != null) || (this.codFunc != null && !this.codFunc.equals(other.codFunc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Funcionario[ codFunc=" + codFunc + " ]";
    }
    
}
