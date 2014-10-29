/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import Controller.CidadesDAO;
import Controller.EmpresaDAO;
import Controller.EnderecoDAO;
import Controller.EstadosDAO;
import Controller.GrupoDAO;
import Controller.TelefoneDAO;
import Controller.TipoPessoaDAO;
import Model.Email;
import Model.Empresa;
import Model.Endereco;
import Model.Telefone;
import Util.Classes.FixedLengthDocument;
import Util.Classes.IntegerDocument;
import Util.Classes.UpperCaseDocument;
import Util.Classes.ValidaEmail;
import Util.Classes.ValidarCGCCPF;
import View.Consultas.Frm_ConEmpresa;
import java.awt.Event;
import java.text.ParseException;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Tadeu
 */
public class Frm_CadEmpresa extends javax.swing.JFrame {

    TipoPessoaDAO tipoPessoaDAO;
    EmpresaDAO empresaDAO;
    EnderecoDAO enderecoDAO;
    TelefoneDAO telefoneDAO;
    CidadesDAO cidadesDAO;
    GrupoDAO grupoDAO;
    EstadosDAO estadosDAO;
    Email email;
    Empresa empresa;
    Endereco endereco;
    Telefone telefone;
    private int codigoEmpresa;

    public Frm_CadEmpresa() {
        initComponents();
        setVisible(true);
        txt_nomeFantasia.setDocument(new FixedLengthDocument(255));
        txt_contato.setDocument(new UpperCaseDocument());
        txt_rua.setDocument(new UpperCaseDocument());
        txt_bairro.setDocument(new UpperCaseDocument());
        txt_complemento.setDocument(new UpperCaseDocument());
        txt_porta.setDocument(new IntegerDocument(3));
        txt_numero.setDocument(new IntegerDocument(5));
        carregaTipos();
        carregaEstados();
        carregaCidades();
        txt_nomeFantasia.requestFocus();
        abas.setEnabled(false);
        btn_cancelar.doClick();
        codigoEmpresa = 0;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public void carregaTipos() {
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

    public void carregaCidades() {
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

    public void carregaEstados() {
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

    public void proximo() {
        abas.setSelectedIndex(abas.getSelectedIndex() + 1);
    }

    public void anterior() {
        abas.setSelectedIndex(abas.getSelectedIndex() - 1);
    }

    public void setEnabledFields(boolean valor) {
        txt_nomeFantasia.setEnabled(valor);
        cbx_tipo.setEnabled(valor);
        txt_cpf.setEnabled(valor);
        btn_proximoEmpresa.setEnabled(valor);
        btn_cancelar.setEnabled(valor);
    }

    public void setEnabledButtons(boolean valor) {
        btn_inclusao.setEnabled(valor);
        btn_alteracao.setEnabled(valor);
        btn_consulta.setEnabled(valor);
    }

    public void limpaCampos() {
        txt_nomeFantasia.setText(null);
        txt_cpf.setText(null);
        txt_cep.setText(null);
        txt_rua.setText(null);
        txt_numero.setText(null);
        txt_bairro.setText(null);
        txt_complemento.setText(null);
        txt_contato.setText(null);
        txt_telefone.setText(null);
        txt_email.setText(null);
        txt_usuario.setText(null);
        txt_senha.setText(null);
        txt_smtp.setText(null);
        txt_porta.setText(null);
        txt_operacao.setText(null);
        chx_ssl.setSelected(false);
    }

    public void buscar() {
        try {
            empresaDAO = new EmpresaDAO();
            empresa = new Empresa();
            empresa = empresaDAO.findByCodigo(codigoEmpresa);
            getDados(empresa);
            txt_operacao.setText("CONSULTA");
            setEnabledFields(false);
            setEnabledButtons(true);
            btn_inclusao.setEnabled(false);
            btn_consulta.setEnabled(false);
            btn_cancelar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao Buscar Empresa: " + codigoEmpresa);
        }
    }

    public void trocaMascara() throws ParseException {
        if (cbx_tipo.getSelectedIndex() == 0) {
            txt_cpf.setValue(null);
            MaskFormatter cpf = new MaskFormatter("###.###.###-##");
            txt_cpf.setFormatterFactory(
                    new DefaultFormatterFactory(cpf));
        } else {
            txt_cpf.setValue(null);
            MaskFormatter cnpj = new MaskFormatter("##.###.###/####-##");
            txt_cpf.setFormatterFactory(
                    new DefaultFormatterFactory(cnpj));
        }
        txt_cpf.requestFocus();
    }

    public void validaCamposEmpresa(String nomeFantasia, String cpf, String telefone) {
        if (txt_nomeFantasia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome Fantasia inválido!");
            txt_nomeFantasia.requestFocus();
        } else {
            ValidarCGCCPF v = new ValidarCGCCPF();
            if ((cbx_tipo.getSelectedItem().toString().equals("FISICA") == true) && (v.validarCpf(cpf) == false)) {
                JOptionPane.showMessageDialog(null, "CPF Inválido!");
                txt_cpf.requestFocus();
            } else {
                if ((cbx_tipo.getSelectedItem().toString().equals("JURIDICA") == true) && (v.validaCNPJ(cpf) == false)) {
                    JOptionPane.showMessageDialog(null, "CNPJ Inválido!");
                    txt_cpf.requestFocus();
                } else {
                    if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                        if ((existeCNPJ(cpf) == false) && (existeNomeFantasia(nomeFantasia) == false)) {
                            proximo();
                        }
                    } else {
                        proximo();
                    }
                }
            }
        }
    }

    public boolean existeNomeFantasia(String nomeFantasia) {
        try {
            empresaDAO = new EmpresaDAO();
            empresaDAO.findByNomeFantasia(nomeFantasia);
            JOptionPane.showMessageDialog(this, "Existe uma empresa já cadastrada com este Nome Fantasia");
            txt_nomeFantasia.requestFocus();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existeCNPJ(String cnpj) {
        try {
            empresaDAO = new EmpresaDAO();
            empresaDAO.findByCNPJ(cnpj);
            JOptionPane.showMessageDialog(this, "Existe uma empresa já cadastrada com este CNPJ");
            txt_cpf.requestFocus();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public void validaCamposEndereco(String cep, String rua, String numero, String bairro, String cidade) {
        if (cep.replaceAll("-", "").trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CEP Inválido");
            txt_cep.requestFocus();
        } else {
            if (rua.isEmpty()) {
                JOptionPane.showMessageDialog(null, "LOGRADOURO Inválido");
                txt_rua.requestFocus();
            } else {
                if (numero.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "NÚMERO Inválido");
                    txt_numero.requestFocus();
                } else {
                    if (bairro.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "BAIRRO Inválido");
                        txt_bairro.requestFocus();
                    } else {
                        if (cidade.isEmpty()) {
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

    public void validaCamposEmail(String email, String usuario, String porta, String smtp, String senha) {
        if (ValidaEmail.validarEmail(email) == false) {
            JOptionPane.showMessageDialog(null, "Email Inválido!");
            txt_email.requestFocus();
        } else {
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Usuário Inválido!");
                txt_usuario.requestFocus();
            } else {
                if (senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Senha Inválida!");
                    txt_senha.requestFocus();
                } else {
                    if (smtp.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "SMTP Inválido!");
                        txt_smtp.requestFocus();
                    } else {
                        if (porta.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Porta Inválida!");
                            txt_porta.requestFocus();
                        } else {
                            salvar();
                        }
                    }
                }
            }
        }
    }

    public void validaCamposTelefone(String grupo, String telefone, String contato) {
        if (grupo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Grupo Inválido!");
            cbx_grupo.requestFocus();
        } else {
            if (telefone.equals("(  )     -    ") == true) {
                JOptionPane.showMessageDialog(null, "Telefone Inválido");
                txt_telefone.requestFocus();
            } else {
                if (contato.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Contato Inválido");
                    txt_contato.requestFocus();
                } else {
                    if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                        try {
                            telefoneDAO = new TelefoneDAO();
                            telefoneDAO.busca(telefone);
                            JOptionPane.showMessageDialog(null, "Telefone já cadastrado!");
                        } catch (NoResultException e) {
                            proximo();
                        }
                    } else {
                        proximo();
                    }
                }
            }
        }
    }

    public void getDadosTelefone(Empresa empresa) {
        try {
            txt_telefone.setText(empresa.getTelefoneList().get(0).getTelefone());
            txt_contato.setText(empresa.getTelefoneList().get(0).getDescricao());
            cbx_grupo.setSelectedItem(empresa.getTelefoneList().get(0).getCodgrupo().getDescricao());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar dados do Telefone!");
        }
    }

    public void getDadosEmpresa(Empresa empresa) {
        try {
            txt_nomeFantasia.setText(empresa.getNomeFantasia());
            txt_cpf.setText(empresa.getCnpjCpf());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar dados da Empresa");
        }
    }

    public void getDadosEndereco(Empresa empresa) {
        try {
            txt_cep.setText(empresa.getEnderecoList().get(0).getCep());
            txt_rua.setText(empresa.getEnderecoList().get(0).getRua());
            txt_numero.setText(empresa.getEnderecoList().get(0).getNumero() + "");
            txt_bairro.setText(empresa.getEnderecoList().get(0).getBairro());
            if (empresa.getEnderecoList().get(0).getComplemento() != null) {
                txt_complemento.setText(empresa.getEnderecoList().get(0).getComplemento());
            }
            cbx_cidades.setSelectedItem(empresa.getEnderecoList().get(0).getCodcidade().getDescricao());
            cbx_estados.setSelectedItem(empresa.getEnderecoList().get(0).getCodcidade().getCoduf().getSigla());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar dados do Endereco");
            System.out.println(empresa.getEnderecoList().get(0).getCep());
        }
    }

    public void getDadosEmail(Empresa empresa) {
        txt_email.setText(empresa.getCodemail().getEmail());
        txt_usuario.setText(empresa.getCodemail().getNome());
        txt_senha.setText(empresa.getCodemail().getSenha());
        txt_smtp.setText(empresa.getCodemail().getSmtp());
        txt_porta.setText(empresa.getCodemail().getPorta() + "");
        if (empresa.getCodemail().getSsl().equals("S") == true) {
            chx_ssl.setSelected(true);
        } else {
            chx_ssl.setSelected(false);
        }
    }

    public void getDados(Empresa empresa) {
        getDadosEmail(empresa);
        getDadosEndereco(empresa);
        getDadosTelefone(empresa);
        getDadosEmpresa(empresa);
    }

    public void setTelefone(Empresa empresa) {
        try {
            if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                telefone = new Telefone();
                grupoDAO = new GrupoDAO();
                telefone.setCodempresa(empresa);
                telefone.setDescricao(txt_nomeFantasia.getText());
                telefone.setTelefone(txt_telefone.getText());
                telefone.setCodgrupo(grupoDAO.consulta(cbx_grupo.getSelectedItem().toString()));
                empresa.getTelefoneList().add(telefone);
            } else {
                empresa.getTelefoneList().get(0).setCodgrupo(grupoDAO.consulta(cbx_grupo.getSelectedItem().toString()));
                empresa.getTelefoneList().get(0).setDescricao(txt_contato.getText());
                empresa.getTelefoneList().get(0).setTelefone(txt_telefone.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar dados do Telefone");
        }
    }

    public void setEndereco(Empresa empresa) {
        try {
            if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                endereco = new Endereco();
                cidadesDAO = new CidadesDAO();
                endereco.setCep(txt_cep.getText());
                endereco.setRua(txt_rua.getText());
                endereco.setNumero(Integer.parseInt(txt_numero.getText()));
                endereco.setBairro(txt_bairro.getText());
                if (!txt_complemento.getText().isEmpty()) {
                    endereco.setComplemento(txt_complemento.getText());
                }
                endereco.setCodcidade(cidadesDAO.consulta(cbx_cidades.getSelectedItem().toString()));
                endereco.setCodempresa(empresa);
                empresa.getEnderecoList().add(endereco);
            } else {
                empresa.getEnderecoList().get(0).setBairro(txt_bairro.getText());
                empresa.getEnderecoList().get(0).setCep(txt_cep.getText());
                empresa.getEnderecoList().get(0).setCodcidade(cidadesDAO.consulta(cbx_cidades.getSelectedItem().toString()));
                empresa.getEnderecoList().get(0).setComplemento(txt_complemento.getText());
                empresa.getEnderecoList().get(0).setNumero(Integer.parseInt(txt_numero.getText()));
                empresa.getEnderecoList().get(0).setRua(txt_rua.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar dados do Endereco");
        }
    }

    public void setEmail(Empresa empresa) {
        try {
            if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                email = new Email();
                email.setEmail(txt_email.getText());
                email.setNome(txt_usuario.getText());
                email.setSenha(txt_senha.getText());
                email.setSmtp(txt_smtp.getText());
                email.setPorta(Integer.parseInt(txt_porta.getText()));
                if (chx_ssl.getSelectedObjects() != null) {
                    email.setSsl("S");
                } else {
                    email.setSsl("N");
                }
                empresa.setCodemail(email);
            } else {
                empresa.getCodemail().setEmail(txt_email.getText());
                empresa.getCodemail().setNome(txt_usuario.getText());
                empresa.getCodemail().setPorta(Integer.parseInt(txt_porta.getText()));
                empresa.getCodemail().setSenha(txt_senha.getText());
                empresa.getCodemail().setSmtp(txt_smtp.getText());
                if (chx_ssl.getSelectedObjects() != null) {
                    empresa.getCodemail().setSsl("S");
                } else {
                    empresa.getCodemail().setSsl("N");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar dados do Email");
            System.out.println(e);
        }
    }

    public void setEmpresa(Empresa empresa) {
        try {
            empresa.setNomeFantasia(txt_nomeFantasia.getText());
            empresa.setCnpjCpf(txt_cpf.getText());
            setEndereco(empresa);
            setTelefone(empresa);
            setEmail(empresa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Capturar dados da Empresa!");
            System.out.println(e);
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

    public void novo() {
        tipoPessoaDAO = new TipoPessoaDAO();
        empresaDAO = new EmpresaDAO();
        enderecoDAO = new EnderecoDAO();
        telefoneDAO = new TelefoneDAO();
        cidadesDAO = new CidadesDAO();
        grupoDAO = new GrupoDAO();
        estadosDAO = new EstadosDAO();
        email = new Email();
        empresa = new Empresa();
        endereco = new Endereco();
        telefone = new Telefone();
    }

    public void salvar() {
        setEmpresa(empresa);
        try {
            empresaDAO = new EmpresaDAO();
            empresaDAO.salvar(empresa);
            if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                JOptionPane.showMessageDialog(null, "Empresa " + empresa.getNomeFantasia() + " salva com sucesso!");
            }else{
                JOptionPane.showMessageDialog(null, "Empresa " + empresa.getNomeFantasia() + " alterada com sucesso!");
            }
            btn_cancelar.doClick();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Empresa!");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_cancelar = new javax.swing.JButton();
        abas = new javax.swing.JTabbedPane();
        pnl_dadosEmpresa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_nomeFantasia = new javax.swing.JTextField();
        cbx_tipo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_cpf = new javax.swing.JFormattedTextField();
        btn_proximoEmpresa = new javax.swing.JButton();
        pnl_dadosTelefone = new javax.swing.JPanel();
        pnl_fundo = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbx_grupo = new javax.swing.JComboBox();
        pnl_dados = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txt_contato = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_telefone = new javax.swing.JFormattedTextField();
        btn_cadGrupo = new javax.swing.JButton();
        btn_proximoTelefone = new javax.swing.JButton();
        btn_anteriorTelefone = new javax.swing.JButton();
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
        btn_anteriorEndereco = new javax.swing.JButton();
        btn_proximoEndereco = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        pnl_dadosEmail = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_smtp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_porta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_senha = new javax.swing.JPasswordField();
        chx_ssl = new javax.swing.JCheckBox();
        btn_salvar = new javax.swing.JButton();
        btn_anteriorEmail = new javax.swing.JButton();
        btn_inclusao = new javax.swing.JButton();
        btn_alteracao = new javax.swing.JButton();
        txt_operacao = new javax.swing.JTextField();
        btn_consulta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Empresa");

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome Fantasia *:");

        cbx_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_tipoActionPerformed(evt);
            }
        });

        jLabel2.setText("Tipo *:");

        jLabel3.setText("CPF/CNPJ *:");

        txt_cpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_proximoEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximoEmpresa.setText("Proximo");
        btn_proximoEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosEmpresaLayout = new javax.swing.GroupLayout(pnl_dadosEmpresa);
        pnl_dadosEmpresa.setLayout(pnl_dadosEmpresaLayout);
        pnl_dadosEmpresaLayout.setHorizontalGroup(
            pnl_dadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEmpresaLayout.createSequentialGroup()
                .addGroup(pnl_dadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_dadosEmpresaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_dadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nomeFantasia, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                            .addGroup(pnl_dadosEmpresaLayout.createSequentialGroup()
                                .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel3)
                                .addGap(10, 10, 10)
                                .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnl_dadosEmpresaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_proximoEmpresa)))
                .addContainerGap())
        );
        pnl_dadosEmpresaLayout.setVerticalGroup(
            pnl_dadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btn_proximoEmpresa)
                .addContainerGap())
        );

        abas.addTab("Dados da Empresa", pnl_dadosEmpresa);

        jLabel10.setText("Grupo:");

        cbx_grupo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_grupoFocusGained(evt);
            }
        });

        pnl_dados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setText("Telefone:");

        jLabel12.setText("Contato:");

        try {
            txt_telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_telefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout pnl_dadosLayout = new javax.swing.GroupLayout(pnl_dados);
        pnl_dados.setLayout(pnl_dadosLayout);
        pnl_dadosLayout.setHorizontalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_contato)
                    .addComponent(txt_telefone, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_dadosLayout.setVerticalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_cadGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cadastro.png"))); // NOI18N
        btn_cadGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadGrupoActionPerformed(evt);
            }
        });

        btn_proximoTelefone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximoTelefone.setText("Proximo");
        btn_proximoTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoTelefoneActionPerformed(evt);
            }
        });

        btn_anteriorTelefone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_anteriorTelefone.setText("Anterior");
        btn_anteriorTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorTelefoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_fundoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_grupo, 0, 320, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cadGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnl_dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_anteriorTelefone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_proximoTelefone)))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cbx_grupo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cadGrupo))
                    .addComponent(pnl_dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_anteriorTelefone)
                    .addComponent(btn_proximoTelefone))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_dadosTelefoneLayout = new javax.swing.GroupLayout(pnl_dadosTelefone);
        pnl_dadosTelefone.setLayout(pnl_dadosTelefoneLayout);
        pnl_dadosTelefoneLayout.setHorizontalGroup(
            pnl_dadosTelefoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_dadosTelefoneLayout.setVerticalGroup(
            pnl_dadosTelefoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosTelefoneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnl_fundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        abas.addTab("Telefone", pnl_dadosTelefone);

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

        btn_anteriorEndereco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_anteriorEndereco.setText("Anterior");
        btn_anteriorEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorEnderecoActionPerformed(evt);
            }
        });

        btn_proximoEndereco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximoEndereco.setText("Proximo");
        btn_proximoEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoEnderecoActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 0, 0));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Com o CEP preenchido, clique em ENTER para buscar o endereço pelo CEP");

        javax.swing.GroupLayout pnl_dadosEnderecoLayout = new javax.swing.GroupLayout(pnl_dadosEndereco);
        pnl_dadosEndereco.setLayout(pnl_dadosEnderecoLayout);
        pnl_dadosEnderecoLayout.setHorizontalGroup(
            pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_anteriorEndereco)
                            .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel19)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(btn_proximoEndereco))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_cadCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbx_estados, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_bairro)
                                    .addComponent(cbx_cidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20))
                            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel22)
                                .addGap(10, 10, 10)
                                .addComponent(txt_rua)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_numero)))))
                .addContainerGap())
        );
        pnl_dadosEnderecoLayout.setVerticalGroup(
            pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel22)
                    .addComponent(txt_rua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(txt_bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)))
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(cbx_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cadCidade)
                    .addComponent(cbx_cidades, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 33, Short.MAX_VALUE)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_anteriorEndereco)
                    .addComponent(btn_proximoEndereco)
                    .addComponent(jLabel23))
                .addContainerGap())
        );

        abas.addTab("Endereço", pnl_dadosEndereco);

        jLabel4.setText("Email *:");

        jLabel5.setText("Usuario *:");

        jLabel6.setText("SMTP *:");

        jLabel7.setText("Porta *:");

        txt_porta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_portaActionPerformed(evt);
            }
        });

        jLabel8.setText("Senha *:");

        chx_ssl.setText("SSL");

        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/salvar.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_anteriorEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_anteriorEmail.setText("Anterior");
        btn_anteriorEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosEmailLayout = new javax.swing.GroupLayout(pnl_dadosEmail);
        pnl_dadosEmail.setLayout(pnl_dadosEmailLayout);
        pnl_dadosEmailLayout.setHorizontalGroup(
            pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_dadosEmailLayout.createSequentialGroup()
                        .addComponent(btn_anteriorEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar))
                    .addGroup(pnl_dadosEmailLayout.createSequentialGroup()
                        .addGroup(pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_email)
                            .addGroup(pnl_dadosEmailLayout.createSequentialGroup()
                                .addComponent(txt_smtp, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_porta, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chx_ssl))
                            .addGroup(pnl_dadosEmailLayout.createSequentialGroup()
                                .addComponent(txt_usuario)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnl_dadosEmailLayout.setVerticalGroup(
            pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_smtp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txt_porta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chx_ssl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(pnl_dadosEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_anteriorEmail))
                .addContainerGap())
        );

        abas.addTab("Email", pnl_dadosEmail);

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

        txt_operacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_operacao.setEnabled(false);

        btn_consulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/buscar.png"))); // NOI18N
        btn_consulta.setText("Consulta");
        btn_consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(abas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_inclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_alteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_operacao)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_inclusao)
                    .addComponent(btn_alteracao)
                    .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_consulta))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbx_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_tipoActionPerformed
        try {
            trocaMascara();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Selecione Um Tipo de Cliente");
        }
    }//GEN-LAST:event_cbx_tipoActionPerformed

    private void txt_portaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_portaActionPerformed
    }//GEN-LAST:event_txt_portaActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        validaCamposEmail(txt_email.getText(), txt_usuario.getText(), txt_porta.getText(), txt_smtp.getText(), txt_senha.getText());
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        while (abas.getSelectedIndex() != 0) {
            abas.setSelectedIndex(abas.getSelectedIndex() - 1);
        }
        limpaCampos();
        setEnabledFields(false);
        setEnabledButtons(true);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void txt_cepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cepKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            if (txt_cep.getText().replaceAll("-", "").trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "CEP não Cadastrado!");
            } else {
                buscaCEP(txt_cep.getText());
            }
        }
    }//GEN-LAST:event_txt_cepKeyPressed

    private void txt_cepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cepKeyReleased

    }//GEN-LAST:event_txt_cepKeyReleased

    private void cbx_estadosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_estadosFocusGained

    }//GEN-LAST:event_cbx_estadosFocusGained

    private void cbx_estadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_estadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_estadosActionPerformed

    private void txt_numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numeroActionPerformed

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

    private void cbx_cidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_cidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_cidadesActionPerformed

    private void btn_cadCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadCidadeActionPerformed
        Frm_CadCidade f = new Frm_CadCidade();
    }//GEN-LAST:event_btn_cadCidadeActionPerformed

    private void btn_proximoEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoEnderecoActionPerformed
        try {
            if (cbx_cidades.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Selecione uma Cidade!");
            } else {
                validaCamposEndereco(txt_cep.getText(), txt_rua.getText(), txt_numero.getText(), txt_bairro.getText(), cbx_cidades.getSelectedItem().toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_proximoEnderecoActionPerformed

    private void cbx_grupoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_grupoFocusGained
        carregaGrupos();
    }//GEN-LAST:event_cbx_grupoFocusGained

    private void btn_cadGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadGrupoActionPerformed
        Frm_CadGrupo f = new Frm_CadGrupo();
    }//GEN-LAST:event_btn_cadGrupoActionPerformed

    private void btn_proximoTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoTelefoneActionPerformed
        validaCamposTelefone(cbx_grupo.getSelectedItem().toString(),
                txt_telefone.getText(),
                txt_contato.getText());
    }//GEN-LAST:event_btn_proximoTelefoneActionPerformed

    private void btn_proximoEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoEmpresaActionPerformed
        validaCamposEmpresa(txt_nomeFantasia.getText(), txt_cpf.getText(), txt_telefone.getText());
    }//GEN-LAST:event_btn_proximoEmpresaActionPerformed

    private void btn_anteriorTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorTelefoneActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorTelefoneActionPerformed

    private void btn_anteriorEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorEnderecoActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorEnderecoActionPerformed

    private void btn_anteriorEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorEmailActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorEmailActionPerformed

    private void btn_inclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inclusaoActionPerformed
        txt_operacao.setText("INCLUSÃO");
        empresa = new Empresa();
        txt_nomeFantasia.requestFocus();
        setEnabledButtons(false);
        setEnabledFields(true);
    }//GEN-LAST:event_btn_inclusaoActionPerformed

    private void btn_alteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alteracaoActionPerformed
        txt_operacao.setText("ALTERAÇÃO");
        setEnabledFields(true);
        setEnabledButtons(false);
        txt_nomeFantasia.requestFocus();
    }//GEN-LAST:event_btn_alteracaoActionPerformed

    private void btn_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultaActionPerformed
        Frm_ConEmpresa f = new Frm_ConEmpresa();
        dispose();
    }//GEN-LAST:event_btn_consultaActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_CadEmpresa.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CadEmpresa.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CadEmpresa.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CadEmpresa.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_CadEmpresa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btn_alteracao;
    private javax.swing.JButton btn_anteriorEmail;
    private javax.swing.JButton btn_anteriorEndereco;
    private javax.swing.JButton btn_anteriorTelefone;
    private javax.swing.JButton btn_cadCidade;
    private javax.swing.JButton btn_cadGrupo;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_consulta;
    private javax.swing.JButton btn_inclusao;
    private javax.swing.JButton btn_proximoEmpresa;
    private javax.swing.JButton btn_proximoEndereco;
    private javax.swing.JButton btn_proximoTelefone;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_cidades;
    private javax.swing.JComboBox cbx_estados;
    private javax.swing.JComboBox cbx_grupo;
    private javax.swing.JComboBox cbx_tipo;
    private javax.swing.JCheckBox chx_ssl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnl_dados;
    private javax.swing.JPanel pnl_dadosEmail;
    private javax.swing.JPanel pnl_dadosEmpresa;
    private javax.swing.JPanel pnl_dadosEndereco;
    private javax.swing.JPanel pnl_dadosTelefone;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JTextField txt_bairro;
    private javax.swing.JFormattedTextField txt_cep;
    private javax.swing.JTextField txt_complemento;
    private javax.swing.JTextField txt_contato;
    private javax.swing.JFormattedTextField txt_cpf;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nomeFantasia;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_operacao;
    private javax.swing.JTextField txt_porta;
    private javax.swing.JTextField txt_rua;
    private javax.swing.JPasswordField txt_senha;
    private javax.swing.JTextField txt_smtp;
    private javax.swing.JFormattedTextField txt_telefone;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
