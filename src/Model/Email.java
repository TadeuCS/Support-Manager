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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e"),
    @NamedQuery(name = "Email.findByCodemail", query = "SELECT e FROM Email e WHERE e.codemail = :codemail"),
    @NamedQuery(name = "Email.findByEmail", query = "SELECT e FROM Email e WHERE e.email = :email"),
    @NamedQuery(name = "Email.findBySmtp", query = "SELECT e FROM Email e WHERE e.smtp = :smtp"),
    @NamedQuery(name = "Email.findByPorta", query = "SELECT e FROM Email e WHERE e.porta = :porta"),
    @NamedQuery(name = "Email.findByNome", query = "SELECT e FROM Email e WHERE e.nome = :nome"),
    @NamedQuery(name = "Email.findBySenha", query = "SELECT e FROM Email e WHERE e.senha = :senha")})
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODEMAIL")
    private Integer codemail;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "SMTP")
    private String smtp;
    @Column(name = "SERVIDOR_SSL")
    private String ssl;
    @Basic(optional = false)
    @Column(name = "PORTA")
    private int porta;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "SENHA")
    private String senha;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codemail")
//    @OneToMany()
//    private List<Empresa> empresaList= new ArrayList<>();

    public Email() {
    }

    public Email(Integer codemail) {
        this.codemail = codemail;
    }

    public Email(Integer codemail, String email, String smtp, int porta, String nome, String senha) {
        this.codemail = codemail;
        this.email = email;
        this.smtp = smtp;
        this.porta = porta;
        this.nome = nome;
        this.senha = senha;
    }

    public Integer getCodemail() {
        return codemail;
    }

    public void setCodemail(Integer codemail) {
        this.codemail = codemail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSsl() {
        return ssl;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }
//
//    @XmlTransient
//    public List<Empresa> getEmpresaList() {
//        return empresaList;
//    }
//
//    public void setEmpresaList(List<Empresa> empresaList) {
//        this.empresaList = empresaList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codemail != null ? codemail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.codemail == null && other.codemail != null) || (this.codemail != null && !this.codemail.equals(other.codemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Email[ codemail=" + codemail + " ]";
    }
    
}
