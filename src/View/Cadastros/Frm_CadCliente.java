package View.Cadastros;

import Controller.AplicativoDAO;
import Controller.CidadesDAO;
import Controller.ClienteDAO;
import Controller.EnderecoDAO;
import Controller.EstadosDAO;
import Controller.GrupoDAO;
import Controller.LinksDAO;
import Controller.ParcelaDAO;
import Controller.SalarioDAO;
import Controller.SegmentoDAO;
import Controller.StatusPessoaDAO;
import Controller.TelefoneDAO;
import Controller.TipoPessoaDAO;
import Controller.UsuarioDAO;
import Model.Aplicativo;
import Model.Cliente;
import Model.Endereco;
import Model.Link;
import Model.LinkCliente;
import Model.StatusPessoa;
import Model.Telefone;
import Model.TipoPessoa;
import Model.Usuario;
import Util.Classes.Data;
import Util.Classes.FixedLengthDocument;
import Util.Classes.IntegerDocument;
import Util.Classes.Mascaras;
import Util.Classes.ValidaEmail;
import Util.Classes.ValidarCGCCPF;
import View.Consultas.Frm_ConClientes;
import View.Home.Frm_Login;
import java.awt.Event;
import java.util.Date;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class Frm_CadCliente extends javax.swing.JFrame {

    ClienteDAO clienteDAO;
    EstadosDAO estadosDAO;
    TelefoneDAO telefoneDAO;
    SegmentoDAO segmentoDAO;
    StatusPessoaDAO statusPessoaDAO;
    CidadesDAO cidadesDAO;
    ParcelaDAO parcelaDAO;
    LinksDAO linksDAO;
    GrupoDAO grupoDAO;
    SalarioDAO salarioDAO;
    TipoPessoaDAO tipoPessoaDAO;
    AplicativoDAO aplicativoDAO;
    EnderecoDAO enderecoDAO;
    Data data;
    Cliente cliente;
    Endereco endereco;
    Telefone telefone;
    Link link;
    TipoPessoa tipo;
    LinkCliente linksClientes;
    DefaultTableModel model;
    private static int codigoCliente;

    public Frm_CadCliente() {
        initComponents();
        setVisible(true);
        codigoCliente = 0;
        txt_nomeFantasia.setDocument(new FixedLengthDocument(255));
        txt_razaoSocial.setDocument(new FixedLengthDocument(255));
        txt_rua.setDocument(new FixedLengthDocument(255));
        txt_bairro.setDocument(new FixedLengthDocument(255));
        txt_complemento.setDocument(new FixedLengthDocument(255));
        txt_inscEstadual.setDocument(new FixedLengthDocument(100));
        txt_responsavel.setDocument(new FixedLengthDocument(100));
        txt_contato.setDocument(new FixedLengthDocument(100));
        txt_codigo.setDocument(new IntegerDocument(4));
        txt_referencia.setDocument(new IntegerDocument(8));
        txt_numero.setDocument(new IntegerDocument(5));
        txt_quantidade.setDocument(new IntegerDocument(2));
        abas.setEnabled(false);

        carregaSegmentos();
        carregaTipos();
        carregaEstados();
        carregaCidades();
        carregaAplicativos();
        carregaParcelas();
        carregaSalarios();
        Mascaras.setMascaraCPF(txt_cpf, cbx_tipo);
        setEnabledFields(false);
        cbx_estados.setSelectedItem("MG");
    }

    //Início das validações de interface.
    public static int getCodigoCliente() {
        return codigoCliente;
    }

    public static void setCodigoCliente(int codigoCliente) {
        Frm_CadCliente.codigoCliente = codigoCliente;
    }

    private void setEnabledFields(boolean valor) {
        txt_cpf.setEnabled(valor);
        txt_inscEstadual.setEnabled(valor);
        txt_razaoSocial.setEnabled(valor);
        txt_nomeFantasia.setEnabled(valor);
        txt_referencia.setEnabled(valor);
        cbx_parcela.setEnabled(valor);
        txt_responsavel.setEnabled(valor);
        txt_email.setEnabled(valor);
        txt_data.setEnabled(valor);
        cbx_tipo.setEnabled(valor);
        cbx_segmento.setEnabled(valor);
        chx_bloqueado.setEnabled(valor);
        btn_proximoDados.setEnabled(valor);
        btn_cancelar.setEnabled(valor);
        btn_cadSegmento.setEnabled(valor);
    }

    private void setEnabledButtons(boolean valor) {
        btn_inclusao.setEnabled(valor);
        btn_alteracao.setEnabled(valor);
        btn_exclusao.setEnabled(valor);
        btn_consulta.setEnabled(valor);
        if (valor == false) {
            btn_cancelar.setEnabled(true);
        } else {
            btn_cancelar.setEnabled(false);
        }
    }

    public void limpaCampos() {
        txt_codigo.setText(null);
        txt_cpf.setText(null);
        txt_inscEstadual.setText(null);
        txt_razaoSocial.setText(null);
        txt_nomeFantasia.setText(null);
        txt_referencia.setText(null);
        txt_responsavel.setText(null);
        txt_email.setText(null);
        txt_data.setText(null);
        txt_rua.setText(null);
        txt_bairro.setText(null);
        txt_cep.setText(null);
        txt_numero.setText(null);
        txt_complemento.setText(null);
        txt_contato.setText(null);
        txt_telefone.setText(null);
        txt_quantidade.setText(null);
        chx_bloqueado.setSelected(false);
        txt_operacao.setText(null);
        limpaTabela((DefaultTableModel) tb_links.getModel());
        limpaTabela((DefaultTableModel) tb_telefones.getModel());
    }

    public void limpaLista(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }
    }

    public void proximo() {
        abas.setSelectedIndex(abas.getSelectedIndex() + 1);
    }

    public void anterior() {
        abas.setSelectedIndex(abas.getSelectedIndex() - 1);
    }

    public void limpaTabela(DefaultTableModel model) {
        try {
            while (0 < model.getRowCount()) {
                model.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    private void carregaTipos() {
        tipoPessoaDAO = new TipoPessoaDAO();
        try {
            int i = 0;
            cbx_tipo.removeAllItems();
            while (i < tipoPessoaDAO.lista().size()) {
                cbx_tipo.addItem(tipoPessoaDAO.lista().get(i).getDescricao());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void carregaAplicativos() {
        aplicativoDAO = new AplicativoDAO();
        try {
            int i = 0;
            cbx_aplicativo.removeAllItems();
            while (i < aplicativoDAO.lista().size()) {
                cbx_aplicativo.addItem(aplicativoDAO.lista().get(i).getDescricao());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void carregaLinks(Aplicativo app) {
        linksDAO = new LinksDAO();
        try {
            int i = 0;
            cbx_links.removeAllItems();
            while (i < linksDAO.listaLinksByAplicativo(app).size()) {
                cbx_links.addItem(linksDAO.listaLinksByAplicativo(app).get(i).getDescricao());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void carregaSegmentos() {
        segmentoDAO = new SegmentoDAO();
        try {
            cbx_segmento.removeAllItems();
            int i = 0;
            while (i < segmentoDAO.lista().size()) {
                cbx_segmento.addItem(segmentoDAO.lista().get(i).getDescricao());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Segmentos");
        }
    }

    private void carregaCidades() {
        cidadesDAO = new CidadesDAO();
        try {
            int i = 0;
            cbx_cidades.removeAllItems();
            while (i < cidadesDAO.lista().size()) {
                cbx_cidades.addItem(cidadesDAO.lista().get(i).getDescricao());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Cidades");
        }

    }

    private void carregaEstados() {
        estadosDAO = new EstadosDAO();
        try {
            int i = 0;
            cbx_estados.removeAllItems();
            while (i < estadosDAO.lista().size()) {
                cbx_estados.addItem(estadosDAO.lista().get(i).getSigla());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao carregar Estados");
        }

    }

    private void carregaParcelas() {
        parcelaDAO = new ParcelaDAO();
        try {
            int i = 0;
            cbx_parcela.removeAllItems();
            while (i < parcelaDAO.lista().size()) {
                cbx_parcela.addItem(parcelaDAO.lista().get(i).getPercentual());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao carregar Parcelas");
        }
    }

    private void carregaSalarios() {
        salarioDAO = new SalarioDAO();
        try {
            int i = 0;
            cbx_ano.removeAllItems();
            while (i < salarioDAO.lista().size()) {
                cbx_ano.addItem(salarioDAO.lista().get(i).getValor());
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao carregar Anos");
        }
    }

    public void getStatusPessoa(Cliente cliente) {
        try {
            if (cliente.getCodstatuspessoa().getDescricao().equals("BLOQUEADO") == true) {
                chx_bloqueado.setSelected(true);
            } else {
                chx_bloqueado.setSelected(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao selecionar Status");
        }
    }
    //Fim das validações de interface.

    public void getCliente(Cliente cliente) {
        //dados da aba dados pessoais
        try {
            if (cliente.getCodcliente() != null) {
                txt_codigo.setText(cliente.getCodcliente() + "");
            }
            cbx_tipo.setSelectedItem(cliente.getCodtipopessoa().getDescricao());
            txt_cpf.setText(cliente.getCnpjCpf());
            txt_inscEstadual.setText(cliente.getInscricaoEstadual());
            txt_razaoSocial.setText(cliente.getRazaoSocial());
            txt_nomeFantasia.setText(cliente.getNomeFantasia());
            txt_referencia.setText(cliente.getReferencia() + "");
            txt_responsavel.setText(cliente.getResponsavel());
            txt_email.setText(cliente.getEmail());
            if (cliente.getDataAtualizacao() != null) {
                txt_data.setText(Data.getDataByDate(cliente.getDataAtualizacao(), "dd/MM/yyyy"));
            }
            cbx_segmento.setSelectedItem(cliente.getCodsegmento().getDescricao());
            getStatusPessoa(cliente);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar dados Pessoais de cliente");
        }
        try {
            //dados da aba endereco
            txt_cep.setText(cliente.getEnderecoList().get(0).getCep());
            txt_rua.setText(cliente.getEnderecoList().get(0).getRua());
            cbx_cidades.setSelectedItem(cliente.getEnderecoList().get(0).getCodcidade().getDescricao());
            cbx_estados.setSelectedItem(cliente.getEnderecoList().get(0).getCodcidade().getCoduf().getSigla());
            txt_bairro.setText(cliente.getEnderecoList().get(0).getBairro());
            txt_numero.setText(cliente.getEnderecoList().get(0).getNumero() + "");
            txt_complemento.setText(cliente.getEnderecoList().get(0).getComplemento());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar Endereço de cliente");
        }

        //dados da aba telefones
        listaTelefones(cliente);
        txt_contato.setText(null);
        txt_telefone.setText(null);

        //dados da aba links
        listaLinks(cliente);
        txt_quantidade.setText(null);
    }

    public void instanciaControladores() {
        tipoPessoaDAO = new TipoPessoaDAO();
        parcelaDAO = new ParcelaDAO();
        segmentoDAO = new SegmentoDAO();
        statusPessoaDAO = new StatusPessoaDAO();
        cidadesDAO = new CidadesDAO();
    }

    public int validaReferencia() {
        if (txt_referencia.getText().equals("") == false) {
            return Integer.parseInt(txt_referencia.getText());
        } else {
            return 0;
        }
    }

    public Date validaDataAtualizacao() {
        if (txt_data.getText().equals("  /  /    ") == true) {
            return null;
        } else {
            return Data.getDataByTexto(txt_data.getText(), "dd/MM/yyyy");
        }
    }

    public StatusPessoa validaBloqueado() {
        if (chx_bloqueado.getSelectedObjects() != null) {
            return statusPessoaDAO.buscaStatusPessoa("BLOQUEADO");
        } else {
            return statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO");
        }
    }

    public void setEndereco() {
        if (txt_operacao.getText().equals("INCLUSÃO") == true) {
            cliente.getEnderecoList().add(Frm_CadCliente.this.setEndereco(cliente));
        }
        if (txt_operacao.getText().equals("ALTERAÇÃO") == true) {
            cliente.getEnderecoList().get(0).setBairro(txt_bairro.getText());
            cliente.getEnderecoList().get(0).setCep(txt_cep.getText());
            cliente.getEnderecoList().get(0).setCodcidade(cidadesDAO.consulta(cbx_cidades.getSelectedItem().toString()));
            if (!txt_complemento.getText().isEmpty()) {
                cliente.getEnderecoList().get(0).setComplemento(txt_complemento.getText());
            }
            cliente.getEnderecoList().get(0).setNumero(Integer.parseInt(txt_numero.getText()));
            cliente.getEnderecoList().get(0).setRua(txt_rua.getText());
        }
    }

    public void setCliente() {
        instanciaControladores();
        try {
            if (txt_codigo.getText().equals("") == false) {
                cliente.setCodcliente(Integer.parseInt(txt_codigo.getText()));
            }
            cliente.setCodtipopessoa(tipoPessoaDAO.buscaTipoPessoa(cbx_tipo.getSelectedItem().toString()));
            cliente.setCnpjCpf(txt_cpf.getText());
            cliente.setInscricaoEstadual(txt_inscEstadual.getText());
            cliente.setRazaoSocial(txt_razaoSocial.getText());
            cliente.setNomeFantasia(txt_nomeFantasia.getText());
            cliente.setReferencia(validaReferencia());
            cliente.setResponsavel(txt_responsavel.getText());
            cliente.setEmail(txt_email.getText());
            cliente.setDataAtualizacao(validaDataAtualizacao());
            cliente.setCodparcela(parcelaDAO.consulta(cbx_parcela.getSelectedItem().toString()));
            cliente.setCodsegmento(segmentoDAO.buscaSegmento(cbx_segmento.getSelectedItem().toString()));
            cliente.setCodstatuspessoa(validaBloqueado());
            setEndereco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar dados do cliente.\n" + e);
        }
    }

    public void setTelefones(Cliente cliente) {
        try {
            telefone = new Telefone();
            model = (DefaultTableModel) tb_telefones.getModel();
            telefone.setDescricao(txt_contato.getText());
            telefone.setTelefone(txt_telefone.getText());
            cliente.getTelefoneList().add(telefone);
            String[] linha = new String[]{telefone.getDescricao(), telefone.getTelefone()};
            model.addRow(linha);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o Telefone na lista!");
        }
    }

    public void setLinks(Cliente cliente) {
        try {
            linksClientes = new LinkCliente();
            aplicativoDAO = new AplicativoDAO();
            link = new Link();
            model = (DefaultTableModel) tb_links.getModel();
            link.setCodaplicativo(aplicativoDAO.buscaAplicativo(cbx_aplicativo.getSelectedItem().toString()));
            link.setDescricao(cbx_links.getSelectedItem().toString());
            linksClientes.setLink(link);
            linksClientes.setQuantidade(Integer.parseInt(txt_quantidade.getText()));
            cliente.getLinkClienteList().add(linksClientes);

            String[] linha = new String[]{link.getDescricao(), txt_quantidade.getText()};
            model.addRow(linha);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o Links na lista!");
        }
    }

    public Endereco setEndereco(Cliente cliente) {
        endereco = new Endereco();
        cidadesDAO = new CidadesDAO();
        try {
            endereco.setCodcliente(cliente);
            endereco.setBairro(txt_bairro.getText());
            endereco.setCep(txt_cep.getText());
            if (!txt_complemento.getText().isEmpty()) {
                endereco.setComplemento(txt_complemento.getText());
            }
            endereco.setNumero(Integer.parseInt(txt_numero.getText()));
            endereco.setRua(txt_rua.getText());
            endereco.setCodcidade(cidadesDAO.consulta(cbx_cidades.getSelectedItem().toString()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar endereço do cliente.\n" + e.getMessage());
        }
        return endereco;
    }

    public void listaTelefones(Cliente cliente) {
        try {
            model = (DefaultTableModel) tb_telefones.getModel();
            limpaTabela(model);
            for (int i = 0; i < cliente.getTelefoneList().size(); i++) {
                String[] linha = new String[]{
                    cliente.getTelefoneList().get(i).getDescricao(),
                    cliente.getTelefoneList().get(i).getTelefone()};
                model.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar Telefones do cliente: " + cliente);
            System.out.println(e);
        }
    }

    public void listaLinks(Cliente cliente) {
        try {
            model = (DefaultTableModel) tb_links.getModel();
            limpaTabela(model);
            for (int i = 0; i < cliente.getLinkClienteList().size(); i++) {
                String[] linha = new String[]{
                    cliente.getLinkClienteList().get(i).getLink().getDescricao(),
                    cliente.getLinkClienteList().get(i).getQuantidade() + ""};
                model.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar Links do cliente: " + cliente);
        }
    }

    public void validaDadosPessoais(String cpf, String inscEstadual, String razaoSocial, String nomeFantasia, String responsavel, String email, String segmento) {
        if ((cbx_tipo.getSelectedItem().toString().equals("FISICA") == true) && (ValidarCGCCPF.validaCPF(cpf) == false)) {
            JOptionPane.showMessageDialog(null, "CPF Inválido!");
            txt_cpf.requestFocus();
        } else {
            if ((cbx_tipo.getSelectedItem().toString().equals("JURIDICA") == true) && (ValidarCGCCPF.validaCNPJ(cpf) == false)) {
                JOptionPane.showMessageDialog(null, "CNPJ Inválido!");
                txt_cpf.requestFocus();
            } else {
                if ((cbx_tipo.getSelectedItem().toString().equals("JURIDICA") == true) && (inscEstadual.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Inscrição Estadual Inválida!");
                    txt_inscEstadual.requestFocus();
                } else {
                    if (razaoSocial.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Razão Social Inválida!");
                        txt_razaoSocial.requestFocus();
                    } else {
                        if (nomeFantasia.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nome Fantasia Inválido!");
                            txt_nomeFantasia.requestFocus();
                        } else {
                            if (responsavel.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Responsável Inválido!");
                                txt_responsavel.requestFocus();
                            } else {
                                if (ValidaEmail.validarEmail(email) == false) {
                                    JOptionPane.showMessageDialog(null, "Email Inválido!");
                                    txt_email.requestFocus();
                                } else {
                                    if (cbx_segmento.getSelectedItem().toString().equals("") == true) {
                                        JOptionPane.showMessageDialog(null, "Selecione um segmento para o Cliente!");
                                        cbx_segmento.requestFocus();
                                    } else {
                                        proximo();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean existeEmail(String email) {
        try {
            clienteDAO = new ClienteDAO();
            clienteDAO.buscaClienteByEmail(email);
            JOptionPane.showMessageDialog(null, "Email já cadastrado!");
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existeCNPJ(String cnpj) {
        try {
            clienteDAO = new ClienteDAO();
            clienteDAO.buscaClienteByCNPJ(cnpj);
            if (cnpj.length() > 14) {
                JOptionPane.showMessageDialog(null, "CNPJ já cadastrado!");
            } else {
                JOptionPane.showMessageDialog(null, "CPF já cadastrado!");
            }
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existeRazao(String razao) {
        try {
            clienteDAO = new ClienteDAO();
            clienteDAO.buscaClienteByRazao(razao);
            JOptionPane.showMessageDialog(null, "Razão Social já cadastrada!");
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public void validaEndereco(String cep, String rua, String numero, String bairro, String cidade) {
        if (cep.replaceAll("-", "").trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CEP Inválido");
            txt_cep.requestFocus();
        } else {
            if (txt_rua.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "LOGRADOURO Inválido");
                txt_rua.requestFocus();
            } else {
                if (txt_numero.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "NÚMERO Inválido");
                    txt_numero.requestFocus();
                } else {
                    if (txt_bairro.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "BAIRRO Inválido");
                        txt_bairro.requestFocus();
                    } else {
                        if (cbx_cidades.getSelectedItem().toString().equals("") == true) {
                            JOptionPane.showMessageDialog(null, "CIDADE Inválida");
                            cbx_cidades.requestFocus();
                        } else {
                            proximo();
                        }
                    }
                }
            }
        }
    }

    public void buscaCEP(String cep) {
        endereco = new Endereco();
        enderecoDAO = new EnderecoDAO();
        try {
            endereco = enderecoDAO.findEnderecoByCEP(cep);
            if (endereco.getCep() != null) {
                txt_rua.setText(endereco.getRua());
                txt_bairro.setText(endereco.getBairro());
                cbx_cidades.setSelectedItem(endereco.getCodcidade().getDescricao());
                cbx_estados.setSelectedItem(endereco.getCodcidade().getCoduf().getSigla());
                txt_numero.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CEP não Encontrado, Preencha os campos obrigatórios!");
            txt_rua.requestFocus();
        }
    }

    public void validaTelefones(int qtde) {
        if (qtde == 0) {
            JOptionPane.showMessageDialog(null, "Insira ao menos UM Contato na lista!");
            txt_contato.requestFocus();
        } else {
            proximo();
        }
    }

    public void validaTelefoneExistente(String grupo, String contato, String numTelefone) {
        grupoDAO = new GrupoDAO();
        telefoneDAO = new TelefoneDAO();
        telefone = new Telefone();
        telefone.setCodgrupo(grupoDAO.consulta(grupo));
        telefone.setTelefone(numTelefone);
        telefone.setDescricao(contato);
        boolean existe = false;
        try {
            telefoneDAO.busca(numTelefone);
            JOptionPane.showMessageDialog(null, "Telefone ja existe!");
            txt_telefone.requestFocus();
        } catch (NoResultException e) {
            for (int i = 0; i < tb_telefones.getRowCount(); i++) {
                if (tb_telefones.getValueAt(i, 1).equals(numTelefone) == true) {
                    JOptionPane.showMessageDialog(null, numTelefone + " já está na lista");
                    txt_telefone.requestFocus();
                    existe = true;
                }
            }
            if (existe == false) {
                insereTelefoneNalista(telefone);
                txt_telefone.setText(null);
                txt_contato.setText(null);
                txt_contato.requestFocus();
            }
        }
    }

    public void insereTelefoneNalista(Telefone telefone) {
        try {
            telefone.setCodcliente(cliente);
            cliente.getTelefoneList().add(telefone);
            model = (DefaultTableModel) tb_telefones.getModel();
            String[] linha = new String[]{telefone.getDescricao(), telefone.getTelefone()};
            model.addRow(linha);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Telefone na Lista\n" + e);
        } finally {
            txt_contato.setText(null);
            txt_telefone.setText(null);
            txt_contato.requestFocus();
        }
    }

    public void carregaGrupos() {
        int i = 0;
        cbx_grupo.removeAllItems();
        try {
            grupoDAO = new GrupoDAO();
            while (i < grupoDAO.lista().size()) {
                String linha = grupoDAO.lista().get(i).getDescricao();
                cbx_grupo.addItem(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Grupos");
        }

    }

    public void removeTelefone(String numeroTelefone) {
        try {
            model = (DefaultTableModel) tb_telefones.getModel();
            for (int i = 0; i < cliente.getTelefoneList().size(); i++) {
                if (cliente.getTelefoneList().get(i).getTelefone().equals(numeroTelefone) == true) {
                    cliente.getTelefoneList().remove(i);
                    model.removeRow(tb_telefones.getSelectedRow());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Remover Telefone: " + numeroTelefone);
            System.out.println(e);
        }
    }

    public void removeLink(String link) {
        try {
            model = (DefaultTableModel) tb_links.getModel();
            for (int i = 0; i < cliente.getLinkClienteList().size(); i++) {
                if (cliente.getLinkClienteList().get(i).getLink().getDescricao().equals(link) == true) {
                    if (txt_operacao.getText().equals("ALTERAÇÃO") == true) {
                        linksDAO = new LinksDAO();
                        linksDAO.removerLinkCliente(linksDAO.findLinkClienteByCodigo(cliente.getLinkClienteList().get(i).getCodLinkCliente()));
                        cliente.getLinkClienteList().remove(i);
                        model.removeRow(tb_links.getSelectedRow());
                    } else {
                        cliente.getLinkClienteList().remove(i);
                        model.removeRow(tb_links.getSelectedRow());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro ao Remover Link: " + link);
        }

    }

    public void salvar() {
        try {
            setCliente();
            clienteDAO = new ClienteDAO();
            clienteDAO.salvar(cliente);
            JOptionPane.showMessageDialog(null, "Cliente Salvo com Sucesso!");
            btn_cancelar.doClick();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar Cliente!\n" + e);
        }
    }

    public void buscar() {
        try {
            clienteDAO = new ClienteDAO();
            cliente = new Cliente();
            cliente = clienteDAO.buscaClienteByCodigo(codigoCliente);
            getCliente(cliente);
            txt_operacao.setText("CONSULTA");
            setEnabledFields(false);
            setEnabledButtons(true);
            btn_inclusao.setEnabled(false);
            btn_consulta.setEnabled(false);
            btn_cancelar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao Buscar Cliente: " + codigoCliente);
        }
    }

    public void validaLinks(int qtde) {
        if (qtde == 0) {
            JOptionPane.showMessageDialog(null, "Insira ao menos UM Link na lista!");
        } else {
            proximo();
        }
    }

    public void validaLinkExistente(String aplicativo, String link, String qtde) {
        boolean existe = false;
        for (int i = 0; i < tb_links.getRowCount(); i++) {
            if (tb_links.getValueAt(i, 0).equals(link) == true) {
                JOptionPane.showMessageDialog(null, link + " já está na lista");
                tb_links.requestFocus();
                existe = true;
            }
        }
        if (existe == false) {
            insereLinkNalista(aplicativo, link, qtde);
        }
    }

    public void insereLinkNalista(String aplicativo, String link, String qtde) {
        try {
            linksClientes = new LinkCliente();
            linksDAO = new LinksDAO();
            linksClientes.setCliente(cliente);
            linksClientes.setLink(linksDAO.buscaLink(link));
            linksClientes.setQuantidade(Integer.parseInt(qtde));
            cliente.getLinkClienteList().add(linksClientes);
            model = (DefaultTableModel) tb_links.getModel();
            String[] linha = new String[]{linksClientes.getLink().getDescricao(), linksClientes.getQuantidade() + ""};
            model.addRow(linha);
            txt_quantidade.setText(null);
            cbx_links.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Link na Lista\n" + e);
        } finally {
            txt_contato.setText(null);
            txt_telefone.setText(null);
            txt_contato.requestFocus();
        }
    }

    public void calcularMensalidade(double parcela, double salario) {
        double mensalidade = (parcela * salario) / 100;
        txt_mensalidade.setText("R$ " + mensalidade);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abas = new javax.swing.JTabbedPane();
        pnl_dadosCliente = new javax.swing.JPanel();
        btn_proximoDados = new javax.swing.JButton();
        pnl_dadosPessoais = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbx_tipo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txt_cpf = new javax.swing.JFormattedTextField();
        txt_inscEstadual = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_razaoSocial = new javax.swing.JTextField();
        txt_nomeFantasia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        txt_referencia = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txt_responsavel = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        cbx_segmento = new javax.swing.JComboBox();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        chx_bloqueado = new javax.swing.JCheckBox();
        txt_data = new javax.swing.JFormattedTextField();
        btn_cadSegmento = new javax.swing.JButton();
        pnl_endereco = new javax.swing.JPanel();
        btn_proximoEndereco = new javax.swing.JButton();
        btn_anteriorEndereco = new javax.swing.JButton();
        pnl_dadosEndereco = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txt_cep = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cbx_estados = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        txt_bairro = new javax.swing.JTextField();
        txt_complemento = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_numero = new javax.swing.JTextField();
        txt_rua = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cbx_cidades = new javax.swing.JComboBox();
        btn_cadCidade = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        pnl_telefones = new javax.swing.JPanel();
        btn_proximoTelefones = new javax.swing.JButton();
        btn_anteriorTelefones = new javax.swing.JButton();
        pnl_dados_telefones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_telefones = new javax.swing.JTable();
        btn_inserirTelefone = new javax.swing.JButton();
        btn_removerTelefone = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cbx_grupo = new javax.swing.JComboBox();
        pnl_cadTelefones = new javax.swing.JPanel();
        txt_telefone = new javax.swing.JFormattedTextField();
        jLabel45 = new javax.swing.JLabel();
        txt_contato = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btn_cadGrupo = new javax.swing.JButton();
        pnl_links = new javax.swing.JPanel();
        btn_anterior = new javax.swing.JButton();
        pnl_dadosLinks = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_links = new javax.swing.JTable();
        pnl_CadLinks = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbx_aplicativo = new javax.swing.JComboBox();
        cbx_links = new javax.swing.JComboBox();
        jLabel48 = new javax.swing.JLabel();
        txt_quantidade = new javax.swing.JTextField();
        btn_inserirLinks = new javax.swing.JButton();
        btn_removerLinks = new javax.swing.JButton();
        btn_proximoLinks = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        pnl_dadosLinks1 = new javax.swing.JPanel();
        pnl_CadLinks1 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txt_mensalidade = new javax.swing.JTextField();
        cbx_ano = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        cbx_parcela = new javax.swing.JComboBox();
        jLabel54 = new javax.swing.JLabel();
        btn_calcularMensalidade = new javax.swing.JButton();
        btn_cadParcela = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();
        btn_salvar1 = new javax.swing.JButton();
        pnl_botoes = new javax.swing.JPanel();
        btn_inclusao = new javax.swing.JButton();
        btn_alteracao = new javax.swing.JButton();
        btn_exclusao = new javax.swing.JButton();
        btn_consulta = new javax.swing.JButton();
        txt_operacao = new javax.swing.JTextField();
        btn_cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Clientes");
        setResizable(false);

        btn_proximoDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximoDados.setText("Proximo");
        btn_proximoDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoDadosActionPerformed(evt);
            }
        });

        pnl_dadosPessoais.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código :");

        txt_codigo.setEnabled(false);

        jLabel2.setText("Tipo *:");

        cbx_tipo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_tipoFocusGained(evt);
            }
        });
        cbx_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_tipoActionPerformed(evt);
            }
        });

        jLabel3.setText("CPF/CNPJ *:");

        txt_cpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_inscEstadual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_inscEstadualFocusGained(evt);
            }
        });

        jLabel4.setText("Insc Estadual :");

        jLabel5.setText("Razão Social/Nome *:");

        jLabel6.setText("Nome Fantasia *:");

        jLabel46.setText("Email *:");

        jLabel49.setText("Referência :");

        jLabel50.setText("Responsavel *:");

        jLabel51.setText("Segmento *:");

        cbx_segmento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_segmentoFocusGained(evt);
            }
        });
        cbx_segmento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_segmentoActionPerformed(evt);
            }
        });

        jLabel52.setText("Atualização :");

        jLabel53.setText("Bloqueado :");

        try {
            txt_data.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_data.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_data.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataKeyPressed(evt);
            }
        });

        btn_cadSegmento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cadastro.png"))); // NOI18N
        btn_cadSegmento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadSegmentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosPessoaisLayout = new javax.swing.GroupLayout(pnl_dadosPessoais);
        pnl_dadosPessoais.setLayout(pnl_dadosPessoaisLayout);
        pnl_dadosPessoaisLayout.setHorizontalGroup(
            pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel6)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel53)
                            .addComponent(jLabel49))
                        .addGap(18, 18, 18)))
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nomeFantasia)
                    .addComponent(txt_razaoSocial)
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_tipo, 0, 125, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_inscEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chx_bloqueado)
                            .addComponent(txt_referencia, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_responsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_data, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_email))
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbx_segmento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_cadSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnl_dadosPessoaisLayout.setVerticalGroup(
            pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txt_inscEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_razaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(txt_referencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(txt_responsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cadSegmento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_data)
                    .addComponent(cbx_segmento, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chx_bloqueado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout pnl_dadosClienteLayout = new javax.swing.GroupLayout(pnl_dadosCliente);
        pnl_dadosCliente.setLayout(pnl_dadosClienteLayout);
        pnl_dadosClienteLayout.setHorizontalGroup(
            pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dadosPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosClienteLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_proximoDados)))
                .addContainerGap())
        );
        pnl_dadosClienteLayout.setVerticalGroup(
            pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_proximoDados)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        abas.addTab("Dados Pessoais", pnl_dadosCliente);

        btn_proximoEndereco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximoEndereco.setText("Proximo");
        btn_proximoEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoEnderecoActionPerformed(evt);
            }
        });

        btn_anteriorEndereco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_anteriorEndereco.setText("Anterior");
        btn_anteriorEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorEnderecoActionPerformed(evt);
            }
        });

        pnl_dadosEndereco.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("CEP *:");

        try {
            txt_cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_cep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cepKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cepKeyReleased(evt);
            }
        });

        jLabel17.setText("Cidade *:");

        jLabel18.setText("Estado *:");

        cbx_estados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_estadosFocusGained(evt);
            }
        });
        cbx_estados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_estadosActionPerformed(evt);
            }
        });

        jLabel19.setText("Bairro *:");

        jLabel20.setText("Complemento :");

        jLabel21.setText("Número *:");

        txt_numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numeroActionPerformed(evt);
            }
        });

        jLabel22.setText("Logradouro *:");

        cbx_cidades.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_cidadesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbx_cidadesFocusLost(evt);
            }
        });
        cbx_cidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_cidadesActionPerformed(evt);
            }
        });

        btn_cadCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cadastro.png"))); // NOI18N
        btn_cadCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadCidadeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosEnderecoLayout = new javax.swing.GroupLayout(pnl_dadosEndereco);
        pnl_dadosEndereco.setLayout(pnl_dadosEnderecoLayout);
        pnl_dadosEnderecoLayout.setHorizontalGroup(
            pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_rua)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbx_cidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_bairro))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(btn_cadCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbx_estados, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnl_dadosEnderecoLayout.setVerticalGroup(
            pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(txt_rua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addGap(18, 18, 18)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txt_bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbx_cidades, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbx_estados, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cadCidade)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 0, 0));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Com o CEP preenchido, clique em ENTER para buscar o endereço pelo CEP");

        javax.swing.GroupLayout pnl_enderecoLayout = new javax.swing.GroupLayout(pnl_endereco);
        pnl_endereco.setLayout(pnl_enderecoLayout);
        pnl_enderecoLayout.setHorizontalGroup(
            pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_enderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_enderecoLayout.createSequentialGroup()
                        .addComponent(btn_anteriorEndereco)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_proximoEndereco))
                    .addComponent(pnl_dadosEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_enderecoLayout.setVerticalGroup(
            pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_enderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proximoEndereco)
                    .addComponent(btn_anteriorEndereco)
                    .addComponent(jLabel23))
                .addGap(11, 11, 11))
        );

        abas.addTab("Endereço", pnl_endereco);

        btn_proximoTelefones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximoTelefones.setText("Proximo");
        btn_proximoTelefones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoTelefonesActionPerformed(evt);
            }
        });

        btn_anteriorTelefones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_anteriorTelefones.setText("Anterior");
        btn_anteriorTelefones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorTelefonesActionPerformed(evt);
            }
        });

        pnl_dados_telefones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_telefones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Contato", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_telefones);

        btn_inserirTelefone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/inserir.png"))); // NOI18N
        btn_inserirTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inserirTelefoneActionPerformed(evt);
            }
        });

        btn_removerTelefone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/remover.png"))); // NOI18N
        btn_removerTelefone.setToolTipText("");
        btn_removerTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerTelefoneActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setText("Grupo *:");

        cbx_grupo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_grupoFocusGained(evt);
            }
        });
        cbx_grupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_grupoActionPerformed(evt);
            }
        });

        pnl_cadTelefones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        try {
            txt_telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_telefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telefone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_telefoneFocusLost(evt);
            }
        });

        jLabel45.setText("Telefone *:");

        jLabel11.setText("Contato *:");

        javax.swing.GroupLayout pnl_cadTelefonesLayout = new javax.swing.GroupLayout(pnl_cadTelefones);
        pnl_cadTelefones.setLayout(pnl_cadTelefonesLayout);
        pnl_cadTelefonesLayout.setHorizontalGroup(
            pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cadTelefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_contato)
                    .addGroup(pnl_cadTelefonesLayout.createSequentialGroup()
                        .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 114, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_cadTelefonesLayout.setVerticalGroup(
            pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_cadTelefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addContainerGap())
        );

        btn_cadGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cadastro.png"))); // NOI18N
        btn_cadGrupo.setToolTipText("Adicionar Contato");
        btn_cadGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_cadTelefones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_grupo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cadGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_grupo)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_cadGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_cadTelefones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout pnl_dados_telefonesLayout = new javax.swing.GroupLayout(pnl_dados_telefones);
        pnl_dados_telefones.setLayout(pnl_dados_telefonesLayout);
        pnl_dados_telefonesLayout.setHorizontalGroup(
            pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_removerTelefone)
                    .addComponent(btn_inserirTelefone))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_dados_telefonesLayout.setVerticalGroup(
            pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btn_inserirTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_removerTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_telefonesLayout = new javax.swing.GroupLayout(pnl_telefones);
        pnl_telefones.setLayout(pnl_telefonesLayout);
        pnl_telefonesLayout.setHorizontalGroup(
            pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dados_telefones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_telefonesLayout.createSequentialGroup()
                        .addComponent(btn_anteriorTelefones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_proximoTelefones)))
                .addContainerGap())
        );
        pnl_telefonesLayout.setVerticalGroup(
            pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dados_telefones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proximoTelefones)
                    .addComponent(btn_anteriorTelefones))
                .addGap(11, 11, 11))
        );

        abas.addTab("Telefones", pnl_telefones);

        btn_anterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_anterior.setText("Anterior");
        btn_anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorActionPerformed(evt);
            }
        });

        pnl_dadosLinks.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_links.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Link", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_links);

        pnl_CadLinks.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel47.setText("Link *:");

        jLabel8.setText("Aplicativo *:");

        cbx_aplicativo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_aplicativoFocusGained(evt);
            }
        });

        cbx_links.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_linksFocusGained(evt);
            }
        });

        jLabel48.setText("Quantidade *:");

        javax.swing.GroupLayout pnl_CadLinksLayout = new javax.swing.GroupLayout(pnl_CadLinks);
        pnl_CadLinks.setLayout(pnl_CadLinksLayout);
        pnl_CadLinksLayout.setHorizontalGroup(
            pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_CadLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47)
                    .addComponent(jLabel8)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_aplicativo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbx_links, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_CadLinksLayout.createSequentialGroup()
                        .addComponent(txt_quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 78, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_CadLinksLayout.setVerticalGroup(
            pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_CadLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbx_aplicativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(cbx_links, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txt_quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_inserirLinks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/inserir.png"))); // NOI18N
        btn_inserirLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inserirLinksActionPerformed(evt);
            }
        });

        btn_removerLinks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/remover.png"))); // NOI18N
        btn_removerLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerLinksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosLinksLayout = new javax.swing.GroupLayout(pnl_dadosLinks);
        pnl_dadosLinks.setLayout(pnl_dadosLinksLayout);
        pnl_dadosLinksLayout.setHorizontalGroup(
            pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_CadLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_inserirLinks, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_removerLinks, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_dadosLinksLayout.setVerticalGroup(
            pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLinksLayout.createSequentialGroup()
                .addGap(0, 36, Short.MAX_VALUE)
                .addComponent(pnl_CadLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLinksLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_inserirLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_removerLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        btn_proximoLinks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximoLinks.setText("Proximo");
        btn_proximoLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoLinksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_linksLayout = new javax.swing.GroupLayout(pnl_links);
        pnl_links.setLayout(pnl_linksLayout);
        pnl_linksLayout.setHorizontalGroup(
            pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_linksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_dadosLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_linksLayout.createSequentialGroup()
                        .addComponent(btn_anterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_proximoLinks)))
                .addContainerGap())
        );
        pnl_linksLayout.setVerticalGroup(
            pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_linksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_anterior)
                    .addComponent(btn_proximoLinks))
                .addContainerGap())
        );

        abas.addTab("Links", pnl_links);

        pnl_dadosLinks1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pnl_CadLinks1.setBorder(javax.swing.BorderFactory.createTitledBorder("Calculo de Mensalidade"));

        jLabel55.setText("Salário: *:");

        jLabel56.setText("Mensalidade:");

        txt_mensalidade.setEditable(false);
        txt_mensalidade.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txt_mensalidade.setForeground(new java.awt.Color(102, 0, 0));
        txt_mensalidade.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        cbx_ano.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_anoFocusGained(evt);
            }
        });
        cbx_ano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_anoActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cadastro.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_CadLinks1Layout = new javax.swing.GroupLayout(pnl_CadLinks1);
        pnl_CadLinks1.setLayout(pnl_CadLinks1Layout);
        pnl_CadLinks1Layout.setHorizontalGroup(
            pnl_CadLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_CadLinks1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_CadLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel56)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_CadLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbx_ano, 0, 82, Short.MAX_VALUE)
                    .addComponent(txt_mensalidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_CadLinks1Layout.setVerticalGroup(
            pnl_CadLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_CadLinks1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_CadLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_CadLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel55)
                        .addComponent(cbx_ano, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(pnl_CadLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txt_mensalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        cbx_parcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_parcelaFocusGained(evt);
            }
        });
        cbx_parcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_parcelaActionPerformed(evt);
            }
        });

        jLabel54.setText("% Parcela *:");

        btn_calcularMensalidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/carregar.png"))); // NOI18N
        btn_calcularMensalidade.setText("Calcular");
        btn_calcularMensalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calcularMensalidadeActionPerformed(evt);
            }
        });

        btn_cadParcela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cadastro.png"))); // NOI18N
        btn_cadParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadParcelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosLinks1Layout = new javax.swing.GroupLayout(pnl_dadosLinks1);
        pnl_dadosLinks1.setLayout(pnl_dadosLinks1Layout);
        pnl_dadosLinks1Layout.setHorizontalGroup(
            pnl_dadosLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLinks1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLinks1Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_parcela, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cadParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 382, Short.MAX_VALUE)
                        .addComponent(pnl_CadLinks1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosLinks1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_calcularMensalidade)))
                .addContainerGap())
        );
        pnl_dadosLinks1Layout.setVerticalGroup(
            pnl_dadosLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLinks1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLinks1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel54)
                        .addComponent(cbx_parcela, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cadParcela))
                    .addComponent(pnl_CadLinks1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_calcularMensalidade)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/salvar.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_salvar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_salvar1.setText("Anterior");
        btn_salvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dadosLinks1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_salvar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosLinks1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_salvar1))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        abas.addTab("Parcela", jPanel2);

        pnl_botoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_inclusao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/adicionar.png"))); // NOI18N
        btn_inclusao.setText("Inclusão");
        btn_inclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inclusaoActionPerformed(evt);
            }
        });

        btn_alteracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/alterar.png"))); // NOI18N
        btn_alteracao.setText("Alteração");
        btn_alteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alteracaoActionPerformed(evt);
            }
        });

        btn_exclusao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/excluir.png"))); // NOI18N
        btn_exclusao.setText("Exclusão");
        btn_exclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exclusaoActionPerformed(evt);
            }
        });

        btn_consulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/buscar.png"))); // NOI18N
        btn_consulta.setText("Consulta");
        btn_consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultaActionPerformed(evt);
            }
        });

        txt_operacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_operacao.setEnabled(false);

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_botoesLayout = new javax.swing.GroupLayout(pnl_botoes);
        pnl_botoes.setLayout(pnl_botoesLayout);
        pnl_botoesLayout.setHorizontalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_inclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_alteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_exclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_operacao)
                .addGap(18, 18, 18)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_botoesLayout.setVerticalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_inclusao)
                    .addComponent(btn_alteracao)
                    .addComponent(btn_exclusao)
                    .addComponent(btn_consulta)
                    .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(abas)
                    .addComponent(pnl_botoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(pnl_botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_proximoDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoDadosActionPerformed
        try {
            if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                if (cbx_segmento.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um segmento!");
                } else {
                    if ((existeCNPJ(txt_cpf.getText()) == false) && (existeRazao(txt_razaoSocial.getText()) == false) && (existeEmail(txt_email.getText()) == false)) {
                        validaDadosPessoais(txt_cpf.getText(), txt_inscEstadual.getText(), txt_razaoSocial.getText(),
                                txt_nomeFantasia.getText(), txt_responsavel.getText(), txt_email.getText(), cbx_segmento.getSelectedItem().toString());
                    }
                }
            } else {
                validaDadosPessoais(txt_cpf.getText(), txt_inscEstadual.getText(), txt_razaoSocial.getText(),
                        txt_nomeFantasia.getText(), txt_responsavel.getText(), txt_email.getText(), cbx_segmento.getSelectedItem().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_proximoDadosActionPerformed

    private void btn_proximoEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoEnderecoActionPerformed
        try {
            if (cbx_cidades.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Selecione uma Cidade!");
            } else {
                validaEndereco(txt_cep.getText(), txt_rua.getText(), txt_numero.getText(), txt_bairro.getText(), cbx_cidades.getSelectedItem().toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_proximoEnderecoActionPerformed

    private void btn_proximoTelefonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoTelefonesActionPerformed
        validaTelefones(tb_telefones.getRowCount());
    }//GEN-LAST:event_btn_proximoTelefonesActionPerformed

    private void btn_anteriorTelefonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorTelefonesActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorTelefonesActionPerformed

    private void btn_anteriorEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorEnderecoActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorEnderecoActionPerformed

    private void btn_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorActionPerformed

    private void cbx_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_tipoActionPerformed
        Mascaras.setMascaraCPF(txt_cpf, cbx_tipo);
    }//GEN-LAST:event_cbx_tipoActionPerformed

    private void cbx_estadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_estadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_estadosActionPerformed

    private void txt_numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numeroActionPerformed

    private void btn_inserirTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inserirTelefoneActionPerformed
        if (txt_contato.getText().equals("") == true) {
            JOptionPane.showMessageDialog(null, "Contato Inválido");
            txt_contato.requestFocus();
        } else {
            if (txt_telefone.getText().equals("(  )     -    ") == true) {
                JOptionPane.showMessageDialog(null, "Telefone Inválido");
                txt_telefone.requestFocus();
            } else {
                if (cbx_grupo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um Grupo");
                } else {
                    validaTelefoneExistente(cbx_grupo.getSelectedItem().toString(),
                            txt_contato.getText(),
                            txt_telefone.getText());
                }
            }
        }
    }//GEN-LAST:event_btn_inserirTelefoneActionPerformed

    private void btn_removerTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerTelefoneActionPerformed
        if (tb_telefones.getSelectedRowCount() == 1) {
            String tel = tb_telefones.getValueAt(tb_telefones.getSelectedRow(), 1).toString();
            try {
                telefoneDAO = new TelefoneDAO();
                telefoneDAO.busca(tel);
                if (telefoneDAO.busca(tel).getCodtelefone() != null) {
                    try {
                        telefoneDAO.apagar(telefoneDAO.busca(tel));
                        removeTelefone(tel);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover telefone " + tel);
                    }

                }
            } catch (NoResultException e) {
                removeTelefone(tel);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione Um telefone na lista para remover");
        }

    }//GEN-LAST:event_btn_removerTelefoneActionPerformed

    private void btn_inserirLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inserirLinksActionPerformed
        if (cbx_aplicativo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Selecione um Aplicativo");
        } else {
            if (cbx_links.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Selecione um Link");
            } else {
                if (txt_quantidade.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Quantidade inválida!");
                    txt_quantidade.requestFocus();
                } else {
                    validaLinkExistente(cbx_aplicativo.getSelectedItem().toString(),
                            cbx_links.getSelectedItem().toString(),
                            txt_quantidade.getText());
                }
            }
        }
    }//GEN-LAST:event_btn_inserirLinksActionPerformed

    private void btn_removerLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerLinksActionPerformed
        if (tb_links.getSelectedRowCount() != 1) {
            JOptionPane.showMessageDialog(null, "Selecione UM Link na lista para remover!");
        } else {
            removeLink(tb_links.getValueAt(tb_links.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_btn_removerLinksActionPerformed

    private void cbx_segmentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_segmentoActionPerformed

    }//GEN-LAST:event_cbx_segmentoActionPerformed

    private void txt_dataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            data = new Data();
            txt_data.setText(data.completaData(txt_data.getText(), "dd/MM/yyyy"));
        }
    }//GEN-LAST:event_txt_dataKeyPressed

    private void cbx_cidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_cidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_cidadesActionPerformed

    private void btn_inclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inclusaoActionPerformed
        limpaCampos();
        cliente = new Cliente();
        txt_operacao.setText("INCLUSÃO");
        txt_cpf.requestFocus();
        setEnabledFields(true);
        setEnabledButtons(false);
        chx_bloqueado.setEnabled(false);
    }//GEN-LAST:event_btn_inclusaoActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        while (abas.getSelectedIndex() != 0) {
            abas.setSelectedIndex(abas.getSelectedIndex() - 1);
        }
        limpaCampos();
        setEnabledFields(false);
        setEnabledButtons(true);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void cbx_cidadesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_cidadesFocusGained
        carregaCidades();
    }//GEN-LAST:event_cbx_cidadesFocusGained

    private void cbx_cidadesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_cidadesFocusLost
        try {
            if (cbx_cidades.getSelectedItem() != null) {
                cbx_estados.setSelectedItem(cidadesDAO.consulta(cbx_cidades.getSelectedItem().toString()).getCoduf().getSigla());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar Estado da cidade: " + cbx_cidades.getSelectedItem().toString());
        }

    }//GEN-LAST:event_cbx_cidadesFocusLost

    private void txt_cepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cepKeyReleased

    }//GEN-LAST:event_txt_cepKeyReleased

    private void txt_cepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cepKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            if (txt_cep.getText().replaceAll("-", "").trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "CEP não Cadastrado!");
            } else {
                buscaCEP(txt_cep.getText());
            }
        }
    }//GEN-LAST:event_txt_cepKeyPressed

    private void cbx_grupoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_grupoFocusGained
        carregaGrupos();
    }//GEN-LAST:event_cbx_grupoFocusGained

    private void cbx_grupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_grupoActionPerformed

    }//GEN-LAST:event_cbx_grupoActionPerformed

    private void btn_cadGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadGrupoActionPerformed
        Frm_CadGrupo f = new Frm_CadGrupo();
    }//GEN-LAST:event_btn_cadGrupoActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        if (cbx_parcela.getSelectedItem() != null) {
            salvar();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um percentual da parcela do Cliente");
            cbx_parcela.requestFocus();
        }
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void cbx_linksFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_linksFocusGained
        if (cbx_aplicativo.getSelectedItem() != null) {
            carregaLinks(aplicativoDAO.buscaAplicativo(cbx_aplicativo.getSelectedItem().toString()));
        }
    }//GEN-LAST:event_cbx_linksFocusGained

    private void cbx_parcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_parcelaActionPerformed
    }//GEN-LAST:event_cbx_parcelaActionPerformed

    private void cbx_anoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_anoActionPerformed
    }//GEN-LAST:event_cbx_anoActionPerformed

    private void btn_proximoLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoLinksActionPerformed
        validaLinks(tb_links.getRowCount());
    }//GEN-LAST:event_btn_proximoLinksActionPerformed

    private void btn_calcularMensalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calcularMensalidadeActionPerformed
        if (cbx_parcela.getSelectedObjects() == null) {
            JOptionPane.showMessageDialog(null, "Selecione um percentual da parcela do Cliente");
            cbx_parcela.requestFocus();
        } else {
            if (cbx_ano.getSelectedObjects() == null) {
                JOptionPane.showMessageDialog(null, "Selecione um ano para calculo do salario do Cliente");
                cbx_ano.requestFocus();
            } else {
                calcularMensalidade(Double.parseDouble(cbx_parcela.getSelectedItem().toString()),
                        Double.parseDouble(cbx_ano.getSelectedItem().toString()));
            }
        }

    }//GEN-LAST:event_btn_calcularMensalidadeActionPerformed

    private void txt_telefoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_telefoneFocusLost
        btn_inserirTelefone.requestFocus();
    }//GEN-LAST:event_txt_telefoneFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Frm_CadSalario f = new Frm_CadSalario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_cadSegmentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadSegmentoActionPerformed
        Frm_CadSegmento f = new Frm_CadSegmento();
    }//GEN-LAST:event_btn_cadSegmentoActionPerformed

    private void cbx_segmentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_segmentoFocusGained
        carregaSegmentos();
    }//GEN-LAST:event_cbx_segmentoFocusGained

    private void btn_cadCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadCidadeActionPerformed
        Frm_CadCidade f = new Frm_CadCidade();
    }//GEN-LAST:event_btn_cadCidadeActionPerformed

    private void btn_cadParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadParcelaActionPerformed
        Frm_CadParcela f = new Frm_CadParcela();
    }//GEN-LAST:event_btn_cadParcelaActionPerformed

    private void cbx_parcelaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_parcelaFocusGained
        carregaParcelas();
    }//GEN-LAST:event_cbx_parcelaFocusGained

    private void cbx_anoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_anoFocusGained
        carregaSalarios();
    }//GEN-LAST:event_cbx_anoFocusGained

    private void cbx_tipoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_tipoFocusGained
        carregaTipos();
    }//GEN-LAST:event_cbx_tipoFocusGained

    private void cbx_estadosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_estadosFocusGained
    }//GEN-LAST:event_cbx_estadosFocusGained

    private void btn_salvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar1ActionPerformed
        anterior();
    }//GEN-LAST:event_btn_salvar1ActionPerformed

    private void btn_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultaActionPerformed
        Frm_ConClientes f = new Frm_ConClientes();
        dispose();
    }//GEN-LAST:event_btn_consultaActionPerformed

    private void btn_alteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alteracaoActionPerformed
        if (txt_codigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente atraves do botão consultar para poder altera-lo");
        } else {
            setEnabledButtons(false);
            setEnabledFields(true);
            txt_operacao.setText("ALTERAÇÃO");
        }
    }//GEN-LAST:event_btn_alteracaoActionPerformed

    private void btn_exclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exclusaoActionPerformed
        if (txt_codigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente atraves do botão consultar para poder excluí-lo");
        } else {
            txt_operacao.setText("EXCLUSÃO");
            try {
                if (JOptionPane.showConfirmDialog(null, "Deseja realmente apagar o cliente: " + cliente.getNomeFantasia(), "Alerta", 0) == 0) {
                    clienteDAO.remover(cliente);
                    JOptionPane.showMessageDialog(null, "Cliente " + cliente.getNomeFantasia() + " removido com sucesso!");
                    setEnabledButtons(true);
                    setEnabledFields(false);
                    limpaCampos();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Impossivel remover este Cliente pois o mesmo ja teve movimentações!");
            }

        }
    }//GEN-LAST:event_btn_exclusaoActionPerformed

    private void cbx_aplicativoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_aplicativoFocusGained
        carregaAplicativos();
    }//GEN-LAST:event_cbx_aplicativoFocusGained

    private void txt_inscEstadualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_inscEstadualFocusGained
        if (cbx_tipo.getSelectedItem().equals("FISICA") == true) {
            txt_inscEstadual.setEnabled(false);
            txt_razaoSocial.requestFocus();
        }
    }//GEN-LAST:event_txt_inscEstadualFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_CadCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CadCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CadCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CadCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_CadCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btn_alteracao;
    private javax.swing.JButton btn_anterior;
    private javax.swing.JButton btn_anteriorEndereco;
    private javax.swing.JButton btn_anteriorTelefones;
    private javax.swing.JButton btn_cadCidade;
    private javax.swing.JButton btn_cadGrupo;
    private javax.swing.JButton btn_cadParcela;
    private javax.swing.JButton btn_cadSegmento;
    private javax.swing.JButton btn_calcularMensalidade;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_consulta;
    private javax.swing.JButton btn_exclusao;
    private javax.swing.JButton btn_inclusao;
    private javax.swing.JButton btn_inserirLinks;
    private javax.swing.JButton btn_inserirTelefone;
    private javax.swing.JButton btn_proximoDados;
    private javax.swing.JButton btn_proximoEndereco;
    private javax.swing.JButton btn_proximoLinks;
    private javax.swing.JButton btn_proximoTelefones;
    private javax.swing.JButton btn_removerLinks;
    private javax.swing.JButton btn_removerTelefone;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JButton btn_salvar1;
    private javax.swing.JComboBox cbx_ano;
    private javax.swing.JComboBox cbx_aplicativo;
    private javax.swing.JComboBox cbx_cidades;
    private javax.swing.JComboBox cbx_estados;
    private javax.swing.JComboBox cbx_grupo;
    private javax.swing.JComboBox cbx_links;
    private javax.swing.JComboBox cbx_parcela;
    private javax.swing.JComboBox cbx_segmento;
    private javax.swing.JComboBox cbx_tipo;
    private javax.swing.JCheckBox chx_bloqueado;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnl_CadLinks;
    private javax.swing.JPanel pnl_CadLinks1;
    private javax.swing.JPanel pnl_botoes;
    private javax.swing.JPanel pnl_cadTelefones;
    private javax.swing.JPanel pnl_dadosCliente;
    private javax.swing.JPanel pnl_dadosEndereco;
    private javax.swing.JPanel pnl_dadosLinks;
    private javax.swing.JPanel pnl_dadosLinks1;
    private javax.swing.JPanel pnl_dadosPessoais;
    private javax.swing.JPanel pnl_dados_telefones;
    private javax.swing.JPanel pnl_endereco;
    private javax.swing.JPanel pnl_links;
    private javax.swing.JPanel pnl_telefones;
    private javax.swing.JTable tb_links;
    private javax.swing.JTable tb_telefones;
    private javax.swing.JTextField txt_bairro;
    private javax.swing.JFormattedTextField txt_cep;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_complemento;
    private javax.swing.JTextField txt_contato;
    private javax.swing.JFormattedTextField txt_cpf;
    private javax.swing.JFormattedTextField txt_data;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_inscEstadual;
    private javax.swing.JTextField txt_mensalidade;
    private javax.swing.JTextField txt_nomeFantasia;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_operacao;
    private javax.swing.JTextField txt_quantidade;
    private javax.swing.JTextField txt_razaoSocial;
    private javax.swing.JTextField txt_referencia;
    private javax.swing.JTextField txt_responsavel;
    private javax.swing.JTextField txt_rua;
    private javax.swing.JFormattedTextField txt_telefone;
    // End of variables declaration//GEN-END:variables
}
