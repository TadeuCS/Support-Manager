/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Tadeu
 */
@Entity
@Table(name = "informacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Informacao.findAll", query = "SELECT i FROM Informacao i"),
    @NamedQuery(name = "Informacao.findByCodinformacao", query = "SELECT i FROM Informacao i WHERE i.codinformacao = :codinformacao"),
    @NamedQuery(name = "Informacao.findByObservacao", query = "SELECT i FROM Informacao i WHERE i.observacao = :observacao"),
    @NamedQuery(name = "Informacao.findByData", query = "SELECT i FROM Informacao i WHERE i.data = :data")})
public class Informacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODINFORMACAO")
    private Integer codinformacao;
    @Basic(optional = false)
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Basic(optional = false)
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "CODUSUARIO", referencedColumnName = "CODUSUARIO")
    @ManyToOne(optional = false)
    private Usuario codusuario;
    @JoinColumn(name = "CODTIPOINFORMACAO", referencedColumnName = "CODTIPOINFORMACAO")
    @ManyToOne(optional = false)
    private TipoInformacao codtipoinformacao;

    public Informacao() {
    }

    public Informacao(Integer codinformacao) {
        this.codinformacao = codinformacao;
    }

    public Informacao(Integer codinformacao, String observacao, Date data) {
        this.codinformacao = codinformacao;
        this.observacao = observacao;
        this.data = data;
    }

    public Integer getCodinformacao() {
        return codinformacao;
    }

    public void setCodinformacao(Integer codinformacao) {
        this.codinformacao = codinformacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Usuario codusuario) {
        this.codusuario = codusuario;
    }

    public TipoInformacao getCodtipoinformacao() {
        return codtipoinformacao;
    }

    public void setCodtipoinformacao(TipoInformacao codtipoinformacao) {
        this.codtipoinformacao = codtipoinformacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codinformacao != null ? codinformacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Informacao)) {
            return false;
        }
        Informacao other = (Informacao) object;
        if ((this.codinformacao == null && other.codinformacao != null) || (this.codinformacao != null && !this.codinformacao.equals(other.codinformacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Informacao[ codinformacao=" + codinformacao + " ]";
    }
    
}
