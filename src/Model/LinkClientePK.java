/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Tadeu
 */
@Embeddable
public class LinkClientePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "LINKCODLINK")
    private int linkcodlink;
    @Basic(optional = false)
    @Column(name = "CLIENTECODCLIENTE")
    private int clientecodcliente;

    public LinkClientePK() {
    }

    public LinkClientePK(int linkcodlink, int clientecodcliente) {
        this.linkcodlink = linkcodlink;
        this.clientecodcliente = clientecodcliente;
    }

    public int getLinkcodlink() {
        return linkcodlink;
    }

    public void setLinkcodlink(int linkcodlink) {
        this.linkcodlink = linkcodlink;
    }

    public int getClientecodcliente() {
        return clientecodcliente;
    }

    public void setClientecodcliente(int clientecodcliente) {
        this.clientecodcliente = clientecodcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) linkcodlink;
        hash += (int) clientecodcliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkClientePK)) {
            return false;
        }
        LinkClientePK other = (LinkClientePK) object;
        if (this.linkcodlink != other.linkcodlink) {
            return false;
        }
        if (this.clientecodcliente != other.clientecodcliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.LinkClientePK[ linkcodlink=" + linkcodlink + ", clientecodcliente=" + clientecodcliente + " ]";
    }
    
}
