package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByCodusuario", query = "SELECT u FROM Usuario u WHERE u.codusuario = :codusuario"),
    @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findByCpf", query = "SELECT u FROM Usuario u WHERE u.cpf = :cpf"),
    @NamedQuery(name = "Usuario.findBySexo", query = "SELECT u FROM Usuario u WHERE u.sexo = :sexo"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODUSUARIO")
    private Integer codusuario;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "CPF",unique = true)
    private String cpf;
    @Basic(optional = false)
    @Column(name = "SEXO")
    private Character sexo;
    @Basic(optional = false)
    @Column(name = "EMAIL",unique = true)
    private String email;
    @Basic(optional = false)
    @Column(name = "USUARIO",unique = true)
    private String usuario;
    @Basic(optional = false)
    @Column(name = "SENHA")
    private String senha;
    @JoinColumn(name = "CODTIPOUSUARIO", referencedColumnName = "CODTIPOUSUARIO")
    @ManyToOne(optional = false)
    private TipoUsuario codtipousuario;
    @JoinColumn(name = "CODSTATUSPESSOA", referencedColumnName = "CODSTATUSPESSOA")
    @ManyToOne(optional = false)
    private StatusPessoa codstatuspessoa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codusuario")
    private List<Informacao> informacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codusuario")
    private List<Atendimento> atendimentoList;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "codusuario")
    private List<Telefone> telefoneList=new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Integer codusuario) {
        this.codusuario = codusuario;
    }

    public Usuario(Integer codusuario, String nome, String cpf, Character sexo, String email, String usuario, String senha) {
        this.codusuario = codusuario;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Integer getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Integer codusuario) {
        this.codusuario = codusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getCodtipousuario() {
        return codtipousuario;
    }

    public void setCodtipousuario(TipoUsuario codtipousuario) {
        this.codtipousuario = codtipousuario;
    }

    public StatusPessoa getCodstatuspessoa() {
        return codstatuspessoa;
    }

    public void setCodstatuspessoa(StatusPessoa codstatuspessoa) {
        this.codstatuspessoa = codstatuspessoa;
    }

    @XmlTransient
    public List<Informacao> getInformacaoList() {
        return informacaoList;
    }

    public void setInformacaoList(List<Informacao> informacaoList) {
        this.informacaoList = informacaoList;
    }

    @XmlTransient
    public List<Atendimento> getAtendimentoList() {
        return atendimentoList;
    }

    public void setAtendimentoList(List<Atendimento> atendimentoList) {
        this.atendimentoList = atendimentoList;
    }

    @XmlTransient
    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codusuario != null ? codusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.codusuario == null && other.codusuario != null) || (this.codusuario != null && !this.codusuario.equals(other.codusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Usuario[ codusuario=" + codusuario + " ]";
    }
    
}
