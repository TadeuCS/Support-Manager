package Model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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

@Entity
@Table(name = "link_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinkCliente.findAll", query = "SELECT l FROM LinkCliente l"),
    @NamedQuery(name = "LinkCliente.findByLinkcodlink", query = "SELECT l FROM LinkCliente l WHERE l.codLink = :linkcodlink"),
    @NamedQuery(name = "LinkCliente.findByClientecodcliente", query = "SELECT l FROM LinkCliente l WHERE l.codCliente = :clientecodcliente"),
    @NamedQuery(name = "LinkCliente.findByQuantidade", query = "SELECT l FROM LinkCliente l WHERE l.quantidade = :quantidade")})
public class LinkCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODLINK_CLIENTE")
    private Integer codLinkCliente;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @JoinColumn(name = "CODCLIENTE", referencedColumnName = "CODCLIENTE")
    @ManyToOne(optional = false)
    private Cliente codCliente;
    @JoinColumn(name = "CODLINK", referencedColumnName = "CODLINK")
    @ManyToOne(optional = false)
    private Link codLink;

    public LinkCliente() {
    }

    public int getCodLinkCliente() {
        return codLinkCliente;
    }

    public void setCodLinkCliente(int codLinkCliente) {
        this.codLinkCliente = codLinkCliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Cliente getCliente() {
        return codCliente;
    }

    public void setCliente(Cliente cliente) {
        this.codCliente = cliente;
    }

    public Link getLink() {
        return codLink;
    }

    public void setLink(Link link) {
        this.codLink = link;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.codLinkCliente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LinkCliente other = (LinkCliente) obj;
        if (!Objects.equals(this.codLinkCliente, other.codLinkCliente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LinkCliente{" + "codLinkCliente=" + codLinkCliente + '}';
    }

}
