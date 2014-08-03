/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tadeu
 */
@Entity
@Table(name = "link_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinkCliente.findAll", query = "SELECT l FROM LinkCliente l"),
    @NamedQuery(name = "LinkCliente.findByCodlinkcliente", query = "SELECT l FROM LinkCliente l WHERE l.codlinkcliente = :codlinkcliente"),
    @NamedQuery(name = "LinkCliente.findByQuantidade", query = "SELECT l FROM LinkCliente l WHERE l.quantidade = :quantidade")})
public class LinkCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODLINKCLIENTE")
    private Integer codlinkcliente;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @JoinColumn(name = "CODLINK", referencedColumnName = "CODLINK")
    @ManyToOne(optional = false)
    private Link codlink;
    @JoinColumn(name = "CODCLIENTE", referencedColumnName = "CODCLIENTE")
    @ManyToOne(optional = false)
    private Cliente codcliente;

    public LinkCliente() {
    }

    public LinkCliente(Integer codlinkcliente) {
        this.codlinkcliente = codlinkcliente;
    }

    public LinkCliente(Integer codlinkcliente, int quantidade) {
        this.codlinkcliente = codlinkcliente;
        this.quantidade = quantidade;
    }

    public Integer getCodlinkcliente() {
        return codlinkcliente;
    }

    public void setCodlinkcliente(Integer codlinkcliente) {
        this.codlinkcliente = codlinkcliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Link getCodlink() {
        return codlink;
    }

    public void setCodlink(Link codlink) {
        this.codlink = codlink;
    }

    public Cliente getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(Cliente codcliente) {
        this.codcliente = codcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codlinkcliente != null ? codlinkcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkCliente)) {
            return false;
        }
        LinkCliente other = (LinkCliente) object;
        if ((this.codlinkcliente == null && other.codlinkcliente != null) || (this.codlinkcliente != null && !this.codlinkcliente.equals(other.codlinkcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.LinkCliente[ codlinkcliente=" + codlinkcliente + " ]";
    }
    
}
