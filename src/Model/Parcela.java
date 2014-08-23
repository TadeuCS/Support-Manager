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
@Table(name = "parcela")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parcela.findAll", query = "SELECT p FROM Parcela p"),
    @NamedQuery(name = "Parcela.findByCodparcela", query = "SELECT p FROM Parcela p WHERE p.codparcela = :codparcela"),
    @NamedQuery(name = "Parcela.findByPercentual", query = "SELECT p FROM Parcela p WHERE p.percentual = :percentual")})
public class Parcela implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODPARCELA")
    private Integer codparcela;
    @Basic(optional = false)
    @Column(name = "PERCENTUAL")
    private double percentual;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codparcela")
    private List<Cliente> clienteList;

    public Parcela() {
    }

    public Parcela(Integer codparcela) {
        this.codparcela = codparcela;
    }

    public Parcela(Integer codparcela, double percentual) {
        this.codparcela = codparcela;
        this.percentual = percentual;
    }

    public Integer getCodparcela() {
        return codparcela;
    }

    public void setCodparcela(Integer codparcela) {
        this.codparcela = codparcela;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
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
        hash += (codparcela != null ? codparcela.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parcela)) {
            return false;
        }
        Parcela other = (Parcela) object;
        if ((this.codparcela == null && other.codparcela != null) || (this.codparcela != null && !this.codparcela.equals(other.codparcela))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Parcela[ codparcela=" + codparcela + " ]";
    }
    
}
