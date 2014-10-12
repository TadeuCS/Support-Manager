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
@Table(name = "segmento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segmento.findAll", query = "SELECT s FROM Segmento s"),
    @NamedQuery(name = "Segmento.findByCodsegmento", query = "SELECT s FROM Segmento s WHERE s.codsegmento = :codsegmento"),
    @NamedQuery(name = "Segmento.findByDescricao", query = "SELECT s FROM Segmento s WHERE s.descricao = :descricao")})
public class Segmento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODSEGMENTO")
    private Integer codsegmento;
    @Basic(optional = false)
    @Column(name = "DESCRICAO",unique = true)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codsegmento")
    private List<Cliente> clienteList;

    public Segmento() {
    }

    public Segmento(Integer codsegmento) {
        this.codsegmento = codsegmento;
    }

    public Segmento(Integer codsegmento, String descricao) {
        this.codsegmento = codsegmento;
        this.descricao = descricao;
    }

    public Integer getCodsegmento() {
        return codsegmento;
    }

    public void setCodsegmento(Integer codsegmento) {
        this.codsegmento = codsegmento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codsegmento != null ? codsegmento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segmento)) {
            return false;
        }
        Segmento other = (Segmento) object;
        if ((this.codsegmento == null && other.codsegmento != null) || (this.codsegmento != null && !this.codsegmento.equals(other.codsegmento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Segmento[ codsegmento=" + codsegmento + " ]";
    }
    
}
