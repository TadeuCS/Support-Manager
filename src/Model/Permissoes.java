/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Tadeu
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Permissoes.findByTipoUsuario", query = "SELECT p FROM Permissoes p WHERE p.codTipoUsuario = :tipoUsuario")})
public class Permissoes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codPermissao;
    private boolean cadAplicativo;
    private boolean cadLinks;
    private boolean cadAtendimento;
    private boolean cadPrioridade;
    private boolean cadOrigem;
    private boolean cadStatusAtendimento;
    private boolean cadTipoAtendimento;
    private boolean cadCliente;
    private boolean cadSegmento;
    private boolean cadParcela;
    private boolean cadSalario;
    private boolean cadContato;
    private boolean cadGrupo;
    private boolean cadUsuario;
    private boolean cadTipoUsuario;
    private boolean cadEmpresa;
    private boolean consAtendimentoAbertos;
    private boolean consAtendimentoExecutando;
    private boolean consAtendimentoConcluidos;
    private boolean consAtendimentoPendentes;
    private boolean consClientes;
    private boolean consContatos;
    private boolean consUsuarios;
    private boolean RelAtendimento;
    private boolean RelClientes;
    private boolean UtiEnviarEmail;
    private boolean UtiEmitirRecibo;
    private boolean UtiInformacao;
    private boolean UtiTipoInformacao;
    private boolean UtiTrocaUsuario;
    private boolean UtiPermissoes;
    @ManyToOne
    private TipoUsuario codTipoUsuario;

    public TipoUsuario getcodTipoUsuario() {
        return codTipoUsuario;
    }

    public void setcodTipoUsuario(TipoUsuario tipoUsuario) {
        this.codTipoUsuario = tipoUsuario;
    }
    
    public int getCodPermissao() {
        return codPermissao;
    }

    public void setCodPermissao(int codPermissao) {
        this.codPermissao = codPermissao;
    }

    public boolean getCadAplicativo() {
        return cadAplicativo;
    }

    public void setCadAplicativo(boolean cadAplicativo) {
        this.cadAplicativo = cadAplicativo;
    }

    public boolean getCadLinks() {
        return cadLinks;
    }

    public void setCadLinks(boolean cadLinks) {
        this.cadLinks = cadLinks;
    }

    public boolean getCadAtendimento() {
        return cadAtendimento;
    }

    public void setCadAtendimento(boolean cadAtendimento) {
        this.cadAtendimento = cadAtendimento;
    }

    public boolean getCadPrioridade() {
        return cadPrioridade;
    }

    public void setCadPrioridade(boolean cadPrioridade) {
        this.cadPrioridade = cadPrioridade;
    }

    public boolean getCadOrigem() {
        return cadOrigem;
    }

    public void setCadOrigem(boolean cadOrigem) {
        this.cadOrigem = cadOrigem;
    }

    public boolean getCadStatusAtendimento() {
        return cadStatusAtendimento;
    }

    public void setCadStatusAtendimento(boolean cadStatusAtendimento) {
        this.cadStatusAtendimento = cadStatusAtendimento;
    }

    public boolean getCadTipoAtendimento() {
        return cadTipoAtendimento;
    }

    public void setCadTipoAtendimento(boolean cadTipoAtendimento) {
        this.cadTipoAtendimento = cadTipoAtendimento;
    }

    public boolean getCadCliente() {
        return cadCliente;
    }

    public void setCadCliente(boolean cadCliente) {
        this.cadCliente = cadCliente;
    }

    public boolean getCadSegmento() {
        return cadSegmento;
    }

    public void setCadSegmento(boolean cadSegmento) {
        this.cadSegmento = cadSegmento;
    }

    public boolean getCadParcela() {
        return cadParcela;
    }

    public void setCadParcela(boolean cadParcela) {
        this.cadParcela = cadParcela;
    }

    public boolean getCadSalario() {
        return cadSalario;
    }

    public void setCadSalario(boolean cadSalario) {
        this.cadSalario = cadSalario;
    }

    public boolean getCadContato() {
        return cadContato;
    }

    public void setCadContato(boolean cadContato) {
        this.cadContato = cadContato;
    }

    public boolean getCadGrupo() {
        return cadGrupo;
    }

    public void setCadGrupo(boolean cadGrupo) {
        this.cadGrupo = cadGrupo;
    }

    public boolean getCadUsuario() {
        return cadUsuario;
    }

    public void setCadUsuario(boolean cadUsuario) {
        this.cadUsuario = cadUsuario;
    }

    public boolean getCadTipoUsuario() {
        return cadTipoUsuario;
    }

    public void setCadTipoUsuario(boolean cadTipoUsuario) {
        this.cadTipoUsuario = cadTipoUsuario;
    }

    public boolean getCadEmpresa() {
        return cadEmpresa;
    }

    public void setCadEmpresa(boolean cadEmpresa) {
        this.cadEmpresa = cadEmpresa;
    }

    public boolean getConsAtendimentoAbertos() {
        return consAtendimentoAbertos;
    }

    public void setConsAtendimentoAbertos(boolean consAtendimentoAbertos) {
        this.consAtendimentoAbertos = consAtendimentoAbertos;
    }

    public boolean getConsAtendimentoExecutando() {
        return consAtendimentoExecutando;
    }

    public void setConsAtendimentoExecutando(boolean consAtendimentoExecutando) {
        this.consAtendimentoExecutando = consAtendimentoExecutando;
    }

    public boolean getConsAtendimentoConcluidos() {
        return consAtendimentoConcluidos;
    }

    public void setConsAtendimentoConcluidos(boolean consAtendimentoConcluidos) {
        this.consAtendimentoConcluidos = consAtendimentoConcluidos;
    }

    public boolean getConsAtendimentoPendentes() {
        return consAtendimentoPendentes;
    }

    public void setConsAtendimentoPendentes(boolean consAtendimentoPendentes) {
        this.consAtendimentoPendentes = consAtendimentoPendentes;
    }

    public boolean getConsClientes() {
        return consClientes;
    }

    public void setConsClientes(boolean consClientes) {
        this.consClientes = consClientes;
    }

    public boolean getConsContatos() {
        return consContatos;
    }

    public void setConsContatos(boolean consContatos) {
        this.consContatos = consContatos;
    }

    public boolean getConsUsuarios() {
        return consUsuarios;
    }

    public void setConsUsuarios(boolean consUsuarios) {
        this.consUsuarios = consUsuarios;
    }

    public boolean getRelAtendimento() {
        return RelAtendimento;
    }

    public void setRelAtendimento(boolean RelAtendimento) {
        this.RelAtendimento = RelAtendimento;
    }

    public boolean getRelClientes() {
        return RelClientes;
    }

    public void setRelClientes(boolean RelClientes) {
        this.RelClientes = RelClientes;
    }

    public boolean getUtiEnviarEmail() {
        return UtiEnviarEmail;
    }

    public void setUtiEnviarEmail(boolean UtiEnviarEmail) {
        this.UtiEnviarEmail = UtiEnviarEmail;
    }

    public boolean getUtiEmitirRecibo() {
        return UtiEmitirRecibo;
    }

    public void setUtiEmitirRecibo(boolean UtiEmitirRecibo) {
        this.UtiEmitirRecibo = UtiEmitirRecibo;
    }

    public boolean getUtiInformacao() {
        return UtiInformacao;
    }

    public void setUtiInformacao(boolean UtiInformacao) {
        this.UtiInformacao = UtiInformacao;
    }

    public boolean getUtiTipoInformacao() {
        return UtiTipoInformacao;
    }

    public void setUtiTipoInformacao(boolean UtiTipoInformacao) {
        this.UtiTipoInformacao = UtiTipoInformacao;
    }

    public boolean getUtiTrocaUsuario() {
        return UtiTrocaUsuario;
    }

    public void setUtiTrocaUsuario(boolean UtiTrocaUsuario) {
        this.UtiTrocaUsuario = UtiTrocaUsuario;
    }

    public boolean getUtiPermissoes() {
        return UtiPermissoes;
    }

    public void setUtiPermissoes(boolean UtiPermissoes) {
        this.UtiPermissoes = UtiPermissoes;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Permissoes other = (Permissoes) obj;
        if (this.codPermissao != other.codPermissao) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Permissoes[ id=" + codPermissao + " ]";
    }

}
