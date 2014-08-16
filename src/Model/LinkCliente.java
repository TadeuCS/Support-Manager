/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "LinkCliente.findByLinkcodlink", query = "SELECT l FROM LinkCliente l WHERE l.linkClientePK.linkcodlink = :linkcodlink"),
    @NamedQuery(name = "LinkCliente.findByClientecodcliente", query = "SELECT l FROM LinkCliente l WHERE l.linkClientePK.clientecodcliente = :clientecodcliente"),
    @NamedQuery(name = "LinkCliente.findByQuantidade", query = "SELECT l FROM LinkCliente l WHERE l.quantidade = :quantidade")})
public class LinkCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LinkClientePK linkClientePK;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @JoinColumn(name = "LINKCODLINK", referencedColumnName = "CODLINK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Link link;
    @JoinColumn(name = "CLIENTECODCLIENTE", referencedColumnName = "CODCLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;

    public LinkCliente() {
    }

    public LinkCliente(LinkClientePK linkClientePK) {
        this.linkClientePK = linkClientePK;
    }

    public LinkCliente(LinkClientePK linkClientePK, int quantidade) {
        this.linkClientePK = linkClientePK;
        this.quantidade = quantidade;
    }

    public LinkCliente(int linkcodlink, int clientecodcliente) {
        this.linkClientePK = new LinkClientePK(linkcodlink, clientecodcliente);
    }

    public LinkClientePK getLinkClientePK() {
        return linkClientePK;
    }

    public void setLinkClientePK(LinkClientePK linkClientePK) {
        this.linkClientePK = linkClientePK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (linkClientePK != null ? linkClientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkCliente)) {
            return false;
        }
        LinkCliente other = (LinkCliente) object;
        if ((this.linkClientePK == null && other.linkClientePK != null) || (this.linkClientePK != null && !this.linkClientePK.equals(other.linkClientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.LinkCliente[ linkClientePK=" + linkClientePK + " ]";
    }
    
}
