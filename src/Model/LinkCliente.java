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
    @NamedQuery(name = "LinkCliente.findByCodlinkCliente", query = "SELECT l FROM LinkCliente l WHERE l.codlinkCliente = :codlinkCliente"),
    @NamedQuery(name = "LinkCliente.findByQuantidade", query = "SELECT l FROM LinkCliente l WHERE l.quantidade = :quantidade")})
public class LinkCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODLINK_CLIENTE")
    private Integer codlinkCliente;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @JoinColumn(name = "CODCLIENTE", referencedColumnName = "CODCLIENTE")
    @ManyToOne(optional = false)
    private Cliente codcliente;
    @JoinColumn(name = "CODLINK", referencedColumnName = "CODLINK")
    @ManyToOne(optional = false)
    private Link codlink;

    public LinkCliente() {
    }

    public LinkCliente(Integer codlinkCliente) {
        this.codlinkCliente = codlinkCliente;
    }

    public LinkCliente(Integer codlinkCliente, int quantidade) {
        this.codlinkCliente = codlinkCliente;
        this.quantidade = quantidade;
    }

    public Integer getCodlinkCliente() {
        return codlinkCliente;
    }

    public void setCodlinkCliente(Integer codlinkCliente) {
        this.codlinkCliente = codlinkCliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Cliente getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(Cliente codcliente) {
        this.codcliente = codcliente;
    }

    public Link getCodlink() {
        return codlink;
    }

    public void setCodlink(Link codlink) {
        this.codlink = codlink;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codlinkCliente != null ? codlinkCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkCliente)) {
            return false;
        }
        LinkCliente other = (LinkCliente) object;
        if ((this.codlinkCliente == null && other.codlinkCliente != null) || (this.codlinkCliente != null && !this.codlinkCliente.equals(other.codlinkCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.LinkCliente[ codlinkCliente=" + codlinkCliente + " ]";
    }
    
}
