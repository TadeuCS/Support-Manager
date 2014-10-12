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
@Table(name = "status_atendimento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusAtendimento.findAll", query = "SELECT s FROM StatusAtendimento s"),
    @NamedQuery(name = "StatusAtendimento.findByCodstatusatendimento", query = "SELECT s FROM StatusAtendimento s WHERE s.codstatusatendimento = :codstatusatendimento"),
    @NamedQuery(name = "StatusAtendimento.findByDescricao", query = "SELECT s FROM StatusAtendimento s WHERE s.descricao = :descricao")})
public class StatusAtendimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODSTATUSATENDIMENTO")
    private Integer codstatusatendimento;
    @Basic(optional = false)
    @Column(name = "DESCRICAO",unique = true)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codstatusatendimento")
    private List<Atendimento> atendimentoList;

    public StatusAtendimento() {
    }

    public StatusAtendimento(Integer codstatusatendimento) {
        this.codstatusatendimento = codstatusatendimento;
    }

    public StatusAtendimento(Integer codstatusatendimento, String descricao) {
        this.codstatusatendimento = codstatusatendimento;
        this.descricao = descricao;
    }

    public Integer getCodstatusatendimento() {
        return codstatusatendimento;
    }

    public void setCodstatusatendimento(Integer codstatusatendimento) {
        this.codstatusatendimento = codstatusatendimento;
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
        hash += (codstatusatendimento != null ? codstatusatendimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusAtendimento)) {
            return false;
        }
        StatusAtendimento other = (StatusAtendimento) object;
        if ((this.codstatusatendimento == null && other.codstatusatendimento != null) || (this.codstatusatendimento != null && !this.codstatusatendimento.equals(other.codstatusatendimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.StatusAtendimento[ codstatusatendimento=" + codstatusatendimento + " ]";
    }
    
}
