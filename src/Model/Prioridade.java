/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tadeu
 */
@Entity
@Table(name = "prioridade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prioridade.findAll", query = "SELECT p FROM Prioridade p"),
    @NamedQuery(name = "Prioridade.findByCodprioridade", query = "SELECT p FROM Prioridade p WHERE p.codprioridade = :codprioridade"),
    @NamedQuery(name = "Prioridade.findByDescricao", query = "SELECT p FROM Prioridade p WHERE p.descricao = :descricao")})
public class Prioridade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODPRIORIDADE")
    private Integer codprioridade;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codprioridade")
    private List<Atendimento> atendimentoList;

    public Prioridade() {
    }

    public Prioridade(Integer codprioridade) {
        this.codprioridade = codprioridade;
    }

    public Prioridade(Integer codprioridade, String descricao) {
        this.codprioridade = codprioridade;
        this.descricao = descricao;
    }

    public Integer getCodprioridade() {
        return codprioridade;
    }

    public void setCodprioridade(Integer codprioridade) {
        this.codprioridade = codprioridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Atendimento> getAtendimentoList() {
        return atendimentoList;
    }

    public void setAtendimentoList(List<Atendimento> atendimentoList) {
        this.atendimentoList = atendimentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codprioridade != null ? codprioridade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prioridade)) {
            return false;
        }
        Prioridade other = (Prioridade) object;
        if ((this.codprioridade == null && other.codprioridade != null) || (this.codprioridade != null && !this.codprioridade.equals(other.codprioridade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Prioridade[ codprioridade=" + codprioridade + " ]";
    }
    
}
