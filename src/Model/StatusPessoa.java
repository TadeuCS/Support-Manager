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
@Table(name = "status_pessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusPessoa.findAll", query = "SELECT s FROM StatusPessoa s"),
    @NamedQuery(name = "StatusPessoa.findByCodstatuspessoa", query = "SELECT s FROM StatusPessoa s WHERE s.codstatuspessoa = :codstatuspessoa"),
    @NamedQuery(name = "StatusPessoa.findByDescricao", query = "SELECT s FROM StatusPessoa s WHERE s.descricao = :descricao")})
public class StatusPessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODSTATUSPESSOA")
    private Integer codstatuspessoa;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codstatuspessoa")
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codstatuspessoa")
    private List<Cliente> clienteList;

    public StatusPessoa() {
    }

    public StatusPessoa(Integer codstatuspessoa) {
        this.codstatuspessoa = codstatuspessoa;
    }

    public StatusPessoa(Integer codstatuspessoa, String descricao) {
        this.codstatuspessoa = codstatuspessoa;
        this.descricao = descricao;
    }

    public Integer getCodstatuspessoa() {
        return codstatuspessoa;
    }

    public void setCodstatuspessoa(Integer codstatuspessoa) {
        this.codstatuspessoa = codstatuspessoa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        hash += (codstatuspessoa != null ? codstatuspessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusPessoa)) {
            return false;
        }
        StatusPessoa other = (StatusPessoa) object;
        if ((this.codstatuspessoa == null && other.codstatuspessoa != null) || (this.codstatuspessoa != null && !this.codstatuspessoa.equals(other.codstatuspessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.StatusPessoa[ codstatuspessoa=" + codstatuspessoa + " ]";
    }
    
}
