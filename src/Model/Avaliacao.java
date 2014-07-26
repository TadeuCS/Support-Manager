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
@Table(name = "avaliacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Avaliacao.findAll", query = "SELECT a FROM Avaliacao a"),
    @NamedQuery(name = "Avaliacao.findByCodavaliacao", query = "SELECT a FROM Avaliacao a WHERE a.codavaliacao = :codavaliacao"),
    @NamedQuery(name = "Avaliacao.findByNotaAtendimento", query = "SELECT a FROM Avaliacao a WHERE a.notaAtendimento = :notaAtendimento"),
    @NamedQuery(name = "Avaliacao.findByNotaAplicativo", query = "SELECT a FROM Avaliacao a WHERE a.notaAplicativo = :notaAplicativo")})
public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODAVALIACAO")
    private Integer codavaliacao;
    @Basic(optional = false)
    @Column(name = "NOTA_ATENDIMENTO")
    private int notaAtendimento;
    @Basic(optional = false)
    @Column(name = "NOTA_APLICATIVO")
    private int notaAplicativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codavaliacao")
    private List<Atendimento> atendimentoList;

    public Avaliacao() {
    }

    public Avaliacao(Integer codavaliacao) {
        this.codavaliacao = codavaliacao;
    }

    public Avaliacao(Integer codavaliacao, int notaAtendimento, int notaAplicativo) {
        this.codavaliacao = codavaliacao;
        this.notaAtendimento = notaAtendimento;
        this.notaAplicativo = notaAplicativo;
    }

    public Integer getCodavaliacao() {
        return codavaliacao;
    }

    public void setCodavaliacao(Integer codavaliacao) {
        this.codavaliacao = codavaliacao;
    }

    public int getNotaAtendimento() {
        return notaAtendimento;
    }

    public void setNotaAtendimento(int notaAtendimento) {
        this.notaAtendimento = notaAtendimento;
    }

    public int getNotaAplicativo() {
        return notaAplicativo;
    }

    public void setNotaAplicativo(int notaAplicativo) {
        this.notaAplicativo = notaAplicativo;
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
        hash += (codavaliacao != null ? codavaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacao)) {
            return false;
        }
        Avaliacao other = (Avaliacao) object;
        if ((this.codavaliacao == null && other.codavaliacao != null) || (this.codavaliacao != null && !this.codavaliacao.equals(other.codavaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Avaliacao[ codavaliacao=" + codavaliacao + " ]";
    }
    
}
