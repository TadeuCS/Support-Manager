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
@Table(name = "log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
    @NamedQuery(name = "Log.findByCodlog", query = "SELECT l FROM Log l WHERE l.codlog = :codlog"),
    @NamedQuery(name = "Log.findByDataEvento", query = "SELECT l FROM Log l WHERE l.dataEvento = :dataEvento"),
    @NamedQuery(name = "Log.findByDescricao", query = "SELECT l FROM Log l WHERE l.descricao = :descricao"),
    @NamedQuery(name = "Log.findByTabela", query = "SELECT l FROM Log l WHERE l.tabela = :tabela")})
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODLOG")
    private Integer codlog;
    @Basic(optional = false)
    @Column(name = "DATA_EVENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEvento;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "TABELA")
    private String tabela;
    @JoinColumn(name = "CODUSUARIO", referencedColumnName = "CODUSUARIO")
    @ManyToOne(optional = false)
    private Usuario codusuario;

    public Log() {
    }

    public Log(Integer codlog) {
        this.codlog = codlog;
    }

    public Log(Integer codlog, Date dataEvento, String descricao, String tabela) {
        this.codlog = codlog;
        this.dataEvento = dataEvento;
        this.descricao = descricao;
        this.tabela = tabela;
    }

    public Integer getCodlog() {
        return codlog;
    }

    public void setCodlog(Integer codlog) {
        this.codlog = codlog;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public Usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Usuario codusuario) {
        this.codusuario = codusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codlog != null ? codlog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.codlog == null && other.codlog != null) || (this.codlog != null && !this.codlog.equals(other.codlog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Log[ codlog=" + codlog + " ]";
    }
    
}
