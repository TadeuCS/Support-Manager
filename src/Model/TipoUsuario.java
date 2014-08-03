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
@Table(name = "tipo_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t"),
    @NamedQuery(name = "TipoUsuario.findByCodtipousuario", query = "SELECT t FROM TipoUsuario t WHERE t.codtipousuario = :codtipousuario"),
    @NamedQuery(name = "TipoUsuario.findByDescricao", query = "SELECT t FROM TipoUsuario t WHERE t.descricao = :descricao")})
public class TipoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODTIPOUSUARIO")
    private Integer codtipousuario;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;

    public TipoUsuario() {
    }

    public TipoUsuario(Integer codtipousuario) {
        this.codtipousuario = codtipousuario;
    }

    public TipoUsuario(Integer codtipousuario, String descricao) {
        this.codtipousuario = codtipousuario;
        this.descricao = descricao;
    }

    public Integer getCodtipousuario() {
        return codtipousuario;
    }

    public void setCodtipousuario(Integer codtipousuario) {
        this.codtipousuario = codtipousuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codtipousuario != null ? codtipousuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.codtipousuario == null && other.codtipousuario != null) || (this.codtipousuario != null && !this.codtipousuario.equals(other.codtipousuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TipoUsuario[ codtipousuario=" + codtipousuario + " ]";
    }
    
}
