/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import Controller.GrupoDAO;
import Controller.StatusPessoaDAO;
import Controller.TelefoneDAO;
import Controller.TipoUsuarioDAO;
import Controller.UsuarioDAO;
import Model.StatusPessoa;
import Model.Telefone;
import Model.TipoUsuario;
import Model.Usuario;
import Util.Classes.Criptografia;
import Util.Classes.FixedLengthDocument;
import Util.Classes.IntegerDocument;
import Util.Classes.ValidaEmail;
import Util.Classes.ValidarCGCCPF;
import View.Consultas.Frm_ConUsuarios;
import View.Home.Frm_Principal;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Tadeu
 */
public class Frm_CadUsuario extends javax.swing.JFrame {

    UsuarioDAO usuarioDAO;
    TelefoneDAO telefoneDAO;
    GrupoDAO grupoDAO;
    TipoUsuarioDAO tipoUsuarioDAO;
    StatusPessoaDAO statusPessoaDAO;
    Usuario usuario;
    Telefone telefone;
    DefaultTableModel model;
    private static int codigoUsuario;
    Frm_Principal principal = new Frm_Principal();

    public Frm_CadUsuario() {
        initComponents();
        setVisible(true);
        abas.setEnabled(false);
        btn_bloqueado.setToolTipText("Clique aqui para mudar para SIM");
        txt_codigo.setDocument(new IntegerDocument(3));
        txt_nome.setDocument(new FixedLengthDocument(100));
        txt_usuario.setDocument(new FixedLengthDocument(20));
        txt_senha.setDocument(new FixedLengthDocument(18));
        txt_confirmaSenha.setDocument(new FixedLengthDocument(18));
        txt_contato.setDocument(new FixedLengthDocument(100));
        codigoUsuario = 0;
        carregaTipoUsuarios();
        carregaGrupos();
        camposOFF();
        novo();
    }

    public void novo() {
        usuarioDAO = new UsuarioDAO();
        tipoUsuarioDAO = new TipoUsuarioDAO();
        grupoDAO = new GrupoDAO();
        telefoneDAO = new TelefoneDAO();
        statusPessoaDAO = new StatusPessoaDAO();
        usuario = new Usuario();
        telefone = new Telefone();
        model = (DefaultTableModel) tb_telefones.getModel();
    }

    //inicio das validações da interface
    public static int getCodigoUsuario() {
        return codigoUsuario;
    }

    public static void setCodigoUsuario(int codigoUsuario) {
        Frm_CadUsuario.codigoUsuario = codigoUsuario;
    }

    public void proximo() {
        abas.setSelectedIndex(abas.getSelectedIndex() + 1);
    }

    public void anterior() {
        abas.setSelectedIndex(abas.getSelectedIndex() - 1);
    }

    public void camposOFF() {
        btn_consulta.setEnabled(true);
        btn_alteracao.setEnabled(true);
        btn_inclusao.setEnabled(true);
        btn_cancelar.setEnabled(false);
        btn_salvar.setEnabled(false);
        txt_codigo.setEnabled(false);
        txt_confirmaSenha.setEnabled(false);
        txt_cpf.setEnabled(false);
        txt_email.setEnabled(false);
        txt_nome.setEnabled(false);
        txt_senha.setEnabled(false);
        txt_usuario.setEnabled(false);
        cbx_tipoUsuario.setEnabled(false);
        rbt_masculino.setEnabled(false);
        rbt_feminino.setEnabled(false);
        btn_bloqueado.setEnabled(false);
        txt_contato.setEnabled(false);
        txt_telefone.setEnabled(false);
        btn_proximo.setEnabled(false);
        btn_anterior.setEnabled(false);
    }

    public void camposON() {
        btn_consulta.setEnabled(false);
        btn_alteracao.setEnabled(false);
        btn_inclusao.setEnabled(false);
        btn_cancelar.setEnabled(true);
        btn_salvar.setEnabled(true);
        txt_codigo.setEnabled(true);
        txt_confirmaSenha.setEnabled(true);
        txt_cpf.setEnabled(true);
        txt_email.setEnabled(true);
        txt_nome.setEnabled(true);
        txt_senha.setEnabled(true);
        txt_usuario.setEnabled(true);
        cbx_tipoUsuario.setEnabled(true);
        rbt_masculino.setEnabled(true);
        rbt_feminino.setEnabled(true);
        btn_bloqueado.setEnabled(true);
        txt_contato.setEnabled(true);
        txt_telefone.setEnabled(true);
        btn_proximo.setEnabled(true);
        btn_anterior.setEnabled(true);
    }

    public void booleanButton() {
        if (btn_bloqueado.getText().equals("SIM") == true) {
            btn_bloqueado.setText("NÃO");
            btn_bloqueado.setToolTipText("Clique aqui para mudar para SIM");
        } else {
            btn_bloqueado.setText("SIM");
            btn_bloqueado.setToolTipText("Clique aqui para mudar para NÃO");
        }
    }

    public void trocaMascara() throws ParseException {
        if (cbx_tipoUsuario.getSelectedIndex() == 0) {
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

    public void trocaSexo(int selecionado) {
        if (selecionado == 1) {
            rbt_feminino.setSelected(false);
            rbt_masculino.setSelected(true);
        }
        if (selecionado == 2) {
            rbt_feminino.setSelected(true);
            rbt_masculino.setSelected(false);
        }
    }
    //Fim das validações da interface

    public void getUsuario(Usuario usuario) {
        if (usuario.getCodusuario().equals("") == false) {
            txt_codigo.setText(usuario.getCodusuario().toString());
        }
        txt_cpf.setText(usuario.getCpf());
        txt_email.setText(usuario.getEmail());
        txt_nome.setText(usuario.getNome());
        txt_usuario.setText(usuario.getUsuario());
        cbx_tipoUsuario.setSelectedItem(usuario.getCodtipousuario().getDescricao());
        setSexo(usuario.getSexo().toString());
        setBloqueado(usuario.getCodstatuspessoa().getDescricao());
        listaTelefonesByUsuario(usuario);
        //contato
    }

    public void listaTelefonesByUsuario(Usuario usuario) {
        telefoneDAO = new TelefoneDAO();
        try {
            int i = 0;
            limpaTabela();
            while (i < telefoneDAO.listaByUsuario(usuario).size()) {
                String[] linha = new String[]{
                    telefoneDAO.listaByUsuario(usuario).get(i).getDescricao(),
                    telefoneDAO.listaByUsuario(usuario).get(i).getTelefone()};
                model.addRow(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setUsuario() {
        if (txt_codigo.getText().equals("") == false) {
            usuario.setCodusuario(Integer.parseInt(txt_codigo.getText()));
        }
        usuario.setCpf(txt_cpf.getText());
        usuario.setEmail(txt_email.getText());
        usuario.setNome(txt_nome.getText());
        usuario.setUsuario(txt_usuario.getText());

        usuario.setSenha(Criptografia.criptografar(txt_senha.getText()));
        usuario.setSexo(getSexo());
        usuario.setCodtipousuario(getTipoUsuario());
        usuario.setCodstatuspessoa(getBloqueado());
    }

    public void setBloqueado(String status) {
        if (status.equals("DESBLOQUEADO") == true) {
            btn_bloqueado.setText("NÃO");
        } else {
            btn_bloqueado.setText("SIM");
        }
    }

    public StatusPessoa getBloqueado() {
        statusPessoaDAO = new StatusPessoaDAO();
        if (btn_bloqueado.getText().equals("SIM") == false) {
            return statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO");
        } else {
            return statusPessoaDAO.buscaStatusPessoa("BLOQUEADO");
        }
    }

    public void setSexo(String sexo) {
        if (sexo.toUpperCase().equals("F") == true) {
            rbt_feminino.setSelected(true);
        } else {
            rbt_masculino.setSelected(true);
        }
    }

    public Character getSexo() {
        if (rbt_feminino.getSelectedObjects() != null) {
            return 'F';
        } else {
            return 'M';
        }
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuarioDAO.buscaTipoUsuario(cbx_tipoUsuario.getSelectedItem().toString());
    }

    public void carregaTipoUsuarios() {
        tipoUsuarioDAO = new TipoUsuarioDAO();
        try {
            if (principal.getUsuarioLogado().equals("ADMINISTRADOR") == true) {
                for (int i = 0; i < tipoUsuarioDAO.lista().size(); i++) {
                    cbx_tipoUsuario.addItem(tipoUsuarioDAO.lista().get(i).getDescricao());
                }
            } else {
                usuarioDAO = new UsuarioDAO();
                if (usuarioDAO.consultaByUsuario(principal.getUsuarioLogado()).getCodtipousuario().getDescricao().equals("ATENDENTE") == true) {
                    cbx_tipoUsuario.addItem("SUPORTE");
                } else {
                    for (int i = 0; i < tipoUsuarioDAO.lista().size(); i++) {
                        cbx_tipoUsuario.addItem(tipoUsuarioDAO.lista().get(i).getDescricao());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Tipos de Usuários!");
        }
    }

    public void validaProximo(String codigo, String nome, String cpf, String email, String usuario, String senha, String confirmaSenha, StatusPessoa bloqueado, TipoUsuario tipo) {
        if (nome.equals("") == true) {
            JOptionPane.showMessageDialog(null, "Nome Inválido");
            txt_nome.requestFocus();
        } else {
            if (cpf.equals("   .   .   -  ") == true) {
                JOptionPane.showMessageDialog(null, "CPF Inválido");
                txt_cpf.requestFocus();
            } else {
                if (ValidarCGCCPF.validaCPF(cpf) == false) {
                    JOptionPane.showMessageDialog(null, "CPF Inválido");
                    txt_cpf.requestFocus();
                } else {
                    if (email.equals("") == true) {
                        JOptionPane.showMessageDialog(null, "Email Inválido");
                        txt_email.requestFocus();
                    } else {
                        if (ValidaEmail.validarEmail(email) == false) {
                            JOptionPane.showMessageDialog(null, "Email Inválido");
                            txt_email.requestFocus();
                        } else {
                            if (usuario.equals("") == true) {
                                JOptionPane.showMessageDialog(null, "Usuário Inválido");
                                txt_usuario.requestFocus();
                            } else {
                                if (senha.equals("") == true) {
                                    JOptionPane.showMessageDialog(null, "Senha Inválida");
                                    txt_senha.requestFocus();
                                } else {
                                    if (confirmaSenha.equals("") == true) {
                                        JOptionPane.showMessageDialog(null, "Confirma Senha Inválida");
                                        txt_confirmaSenha.requestFocus();
                                    } else {
                                        if (senha.equals(confirmaSenha) == false) {
                                            JOptionPane.showMessageDialog(null, "Senhas Diferentes");
                                            txt_confirmaSenha.setText(null);
                                            txt_senha.setText(null);
                                            txt_senha.requestFocus();
                                        } else {
                                            if ((rbt_feminino.getSelectedObjects() == null) && (rbt_masculino.getSelectedObjects() == null)) {
                                                JOptionPane.showMessageDialog(null, "Sexo Inválido");
                                                rbt_masculino.requestFocus();
                                            } else {
                                                if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                                                    if ((existeCPF(cpf) == false) && (existeEmail(email) == false) && (existeUsuario(usuario) == false)) {
                                                        proximo();
                                                    }
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
            }
        }
    }

    public boolean existeCPF(String cpf) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuarioDAO.consultaByCPF(cpf);
            JOptionPane.showMessageDialog(this, "Já existe um usuário cadastrado com este CPF!");
            txt_cpf.requestFocus();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existeEmail(String email) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuarioDAO.consultaByEmail(email);
            JOptionPane.showMessageDialog(this, "Já existe um usuário cadastrado com este email!");
            txt_email.requestFocus();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existeUsuario(String usuario) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuarioDAO.consultaByUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Já existe um usuário cadastrado com este Usuario!");
            txt_usuario.requestFocus();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public void validaNullos(String codigo, String nome, String cpf, String email, String usuario, String senha, String confirmaSenha, StatusPessoa bloqueado, TipoUsuario tipo) {
        if (nome.equals("") == true) {
            JOptionPane.showMessageDialog(null, "Nome Inválido");
            txt_nome.requestFocus();
        } else {
                if (ValidarCGCCPF.validaCPF(cpf)== false) {
                    JOptionPane.showMessageDialog(null, "CPF Inválido");
                    txt_cpf.requestFocus();
                } else {
                    if (email.equals("") == true) {
                        JOptionPane.showMessageDialog(null, "Email Inválido");
                        txt_email.requestFocus();
                    } else {
                        if (ValidaEmail.validarEmail(email) == false) {
                            JOptionPane.showMessageDialog(null, "Email Inválido");
                            txt_email.requestFocus();
                        } else {
                            if (usuario.equals("") == true) {
                                JOptionPane.showMessageDialog(null, "Usuário Inválido");
                                txt_usuario.requestFocus();
                            } else {
                                if (senha.equals("") == true) {
                                    JOptionPane.showMessageDialog(null, "Senha Inválida");
                                    txt_senha.requestFocus();
                                } else {
                                    if (confirmaSenha.equals("") == true) {
                                        JOptionPane.showMessageDialog(null, "Confirma Senha Inválida");
                                        txt_confirmaSenha.requestFocus();
                                    } else {
                                        if (senha.equals(confirmaSenha) == false) {
                                            JOptionPane.showMessageDialog(null, "Senhas Diferentes");
                                            txt_confirmaSenha.setText(null);
                                            txt_senha.setText(null);
                                            txt_senha.requestFocus();
                                        } else {
                                            if ((rbt_feminino.getSelectedObjects() == null) && (rbt_masculino.getSelectedObjects() == null)) {
                                                JOptionPane.showMessageDialog(null, "Sexo Inválido");
                                                rbt_masculino.requestFocus();
                                            } else {
                                                tipoUsuarioDAO = new TipoUsuarioDAO();
                                                if (txt_operacao.getText().equals("INCLUSÃO") == true) {
                                                    salva();
                                                } else {
                                                    if (txt_operacao.getText().equals("ALTERAÇÃO") == true) {
                                                        alterar();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    public void limpaCampos() {
        txt_operacao.setText(null);
        codigoUsuario = 0;
        txt_codigo.setText(null);
        txt_confirmaSenha.setText(null);
        txt_cpf.setText(null);
        txt_email.setText(null);
        txt_nome.setText(null);
        txt_senha.setText(null);
        txt_usuario.setText(null);
        btn_bloqueado.setText("NÃO");
        rbt_feminino.setSelected(false);
        rbt_masculino.setSelected(false);
        txt_contato.setText(null);
        txt_telefone.setText(null);
        limpaTabela();

    }

    public void limpaTabela() {
        try {
            while (0 < model.getRowCount()) {
                model.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void validaTelefoneExistente(String grupo, String contato, String numTelefone) {
        try {
            telefoneDAO = new TelefoneDAO();
            telefoneDAO.busca(numTelefone);
            JOptionPane.showMessageDialog(null, "Telefone já cadastrado!");
            txt_telefone.requestFocus();
        } catch (NoResultException e) {
            boolean existe = false;
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
            telefone.setCodusuario(usuario);
            usuario.getTelefoneList().add(telefone);
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

    //Metodos do CRUD
    public void salva() {
        try {
            setUsuario();
            usuarioDAO.salvar(usuario);
            JOptionPane.showMessageDialog(null, "Usuario Salvo com Sucesso!");
            cancelar();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Usuario\n" + "Usuário já cadastrado!", "Alerta", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void alterar() {
        try {
            setUsuario();
            usuarioDAO.salvar(usuario);
            JOptionPane.showMessageDialog(null, "Usuario Alterado com Sucesso!");
            cancelar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar Usuario\n", "Alerta", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void buscar() {
        try {
            usuarioDAO = new UsuarioDAO();
            usuario = new Usuario();
            usuario = usuarioDAO.findByCodigo(codigoUsuario);
            getUsuario(usuario);
            txt_operacao.setText("CONSULTA");
            camposOFF();
            btn_inclusao.setEnabled(false);
            btn_consulta.setEnabled(false);
            btn_cancelar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao Buscar Usuario: " + codigoUsuario);
        }
    }

    public void removeTelefone(String numeroTelefone) {
        try {
            model = (DefaultTableModel) tb_telefones.getModel();
            for (int i = 0; i < usuario.getTelefoneList().size(); i++) {
                if (usuario.getTelefoneList().get(i).getTelefone().equals(numeroTelefone) == true) {
                    usuario.getTelefoneList().remove(i);
                    model.removeRow(tb_telefones.getSelectedRow());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Remover Telefone: " + numeroTelefone);
            System.out.println(e);
        }
    }

    public void remove(String numeroTelefone) {
        try {
            telefoneDAO = new TelefoneDAO();
            telefoneDAO.busca(numeroTelefone);
            if (telefoneDAO.busca(numeroTelefone).getCodtelefone() != null) {
                try {
                    telefoneDAO.apagar(telefoneDAO.busca(numeroTelefone));
                    removeTelefone(numeroTelefone);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao remover telefone " + numeroTelefone);
                }

            }
        } catch (NoResultException e) {
            removeTelefone(numeroTelefone);

        }

    }

    public void cancelar() {
        while (abas.getSelectedIndex() != 0) {
            abas.setSelectedIndex(abas.getSelectedIndex() - 1);
        }
        limpaCampos();
        camposOFF();
        codigoUsuario = 0;
        txt_operacao.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        pnl_botoes = new javax.swing.JPanel();
        btn_alteracao = new javax.swing.JButton();
        btn_inclusao = new javax.swing.JButton();
        txt_operacao = new javax.swing.JTextField();
        btn_consulta = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        abas = new javax.swing.JTabbedPane();
        pnl_dados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_senha = new javax.swing.JPasswordField();
        txt_confirmaSenha = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        rbt_masculino = new javax.swing.JRadioButton();
        rbt_feminino = new javax.swing.JRadioButton();
        btn_bloqueado = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbx_tipoUsuario = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_cpf = new javax.swing.JFormattedTextField();
        txt_email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btn_proximo = new javax.swing.JButton();
        pnl_dados_telefones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_telefones = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cbx_grupo = new javax.swing.JComboBox();
        pnl_cadTelefones = new javax.swing.JPanel();
        txt_telefone = new javax.swing.JFormattedTextField();
        jLabel45 = new javax.swing.JLabel();
        txt_contato = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btn_cadGrupo = new javax.swing.JButton();
        btn_anterior = new javax.swing.JButton();
        btn_adicionarContato = new javax.swing.JButton();
        btn_removerContato = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuario");
        setResizable(false);

        pnl_botoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_alteracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/alterar.png"))); // NOI18N
        btn_alteracao.setText("Alteração");
        btn_alteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alteracaoActionPerformed(evt);
            }
        });

        btn_inclusao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/adicionar.png"))); // NOI18N
        btn_inclusao.setText("Inclusão");
        btn_inclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inclusaoActionPerformed(evt);
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
                    .addComponent(btn_alteracao)
                    .addComponent(btn_inclusao)
                    .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_consulta)
                    .addComponent(btn_cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_dados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Nome *:");

        jLabel2.setText("Usuário *:");

        jLabel3.setText("Senha *:");

        jLabel4.setText("Confirma Senha *:");

        rbt_masculino.setText("Masculino");
        rbt_masculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_masculinoActionPerformed(evt);
            }
        });

        rbt_feminino.setText("Feminino");
        rbt_feminino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_femininoActionPerformed(evt);
            }
        });

        btn_bloqueado.setText("NÃO");
        btn_bloqueado.setToolTipText("");
        btn_bloqueado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bloqueadoActionPerformed(evt);
            }
        });

        jLabel6.setText("Sexo *:");

        jLabel7.setText("Bloqueado *:");

        jLabel8.setText("Tipo *:");

        jLabel9.setText("CPF *:");

        try {
            txt_cpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_cpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setText("Email *:");

        txt_codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codigoKeyPressed(evt);
            }
        });

        jLabel10.setText("Código *:");

        btn_proximo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/proximo.png"))); // NOI18N
        btn_proximo.setText("Proximo");
        btn_proximo.setToolTipText("");
        btn_proximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosLayout = new javax.swing.GroupLayout(pnl_dados);
        pnl_dados.setLayout(pnl_dadosLayout);
        pnl_dadosLayout.setHorizontalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txt_senha)
                            .addComponent(txt_confirmaSenha))
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbx_tipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                                        .addComponent(rbt_masculino)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rbt_feminino))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_proximo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_bloqueado)))
                .addGap(89, 89, 89))
        );
        pnl_dadosLayout.setVerticalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_bloqueado)
                    .addComponent(jLabel7))
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(cbx_tipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(rbt_masculino)
                            .addComponent(rbt_feminino))
                        .addGap(10, 10, 10)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_confirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_proximo)
                        .addContainerGap())))
        );

        abas.addTab("Dados Pessoais", pnl_dados);

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
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_telefones);

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
                        .addGap(0, 88, Short.MAX_VALUE)))
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
                    .addComponent(btn_cadGrupo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_cadTelefones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        btn_anterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/anterior.png"))); // NOI18N
        btn_anterior.setText("Anterior");
        btn_anterior.setToolTipText("");
        btn_anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorActionPerformed(evt);
            }
        });

        btn_adicionarContato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/inserir.png"))); // NOI18N
        btn_adicionarContato.setToolTipText("Adicionar Contato");
        btn_adicionarContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adicionarContatoActionPerformed(evt);
            }
        });

        btn_removerContato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/remover.png"))); // NOI18N
        btn_removerContato.setToolTipText("Remover Contato");
        btn_removerContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerContatoActionPerformed(evt);
            }
        });

        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/salvar.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dados_telefonesLayout = new javax.swing.GroupLayout(pnl_dados_telefones);
        pnl_dados_telefones.setLayout(pnl_dados_telefonesLayout);
        pnl_dados_telefonesLayout.setHorizontalGroup(
            pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_adicionarContato)
                            .addComponent(btn_removerContato))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                        .addComponent(btn_anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_dados_telefonesLayout.setVerticalGroup(
            pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dados_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_anterior)
                    .addComponent(btn_salvar))
                .addContainerGap())
            .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btn_adicionarContato, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btn_removerContato, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        abas.addTab("Contatos", pnl_dados_telefones);

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_botoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 788, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rbt_femininoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_femininoActionPerformed
        trocaSexo(2);
    }//GEN-LAST:event_rbt_femininoActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        validaNullos(txt_codigo.getText(), txt_nome.getText(), txt_cpf.getText(), txt_email.getText(), txt_usuario.getText(), txt_senha.getText(),
                txt_confirmaSenha.getText(), getBloqueado(), tipoUsuarioDAO.buscaTipoUsuario(cbx_tipoUsuario.getSelectedItem().toString()));
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_bloqueadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bloqueadoActionPerformed
        booleanButton();
    }//GEN-LAST:event_btn_bloqueadoActionPerformed

    private void rbt_masculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_masculinoActionPerformed
        trocaSexo(1);
    }//GEN-LAST:event_rbt_masculinoActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        cancelar();

    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_alteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alteracaoActionPerformed
        if (txt_codigo.getText().equals("") == false) {
            txt_operacao.setText("ALTERAÇÃO");
            camposON();
            txt_codigo.setEnabled(false);
            txt_nome.requestFocus();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Codigo do usuário Inválido!\n"
                    + "Consulte primeiro o usuario para poder altera-lo!");
        }

    }//GEN-LAST:event_btn_alteracaoActionPerformed

    private void btn_inclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inclusaoActionPerformed
        txt_operacao.setText("INCLUSÃO");
        camposON();
        btn_bloqueado.setEnabled(false);
        txt_codigo.setEnabled(false);
        txt_nome.requestFocus();
        usuario = new Usuario();
    }//GEN-LAST:event_btn_inclusaoActionPerformed

    private void txt_codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoKeyPressed
        if (txt_codigo.getText().equals("") == false) {
            codigoUsuario = Integer.parseInt(txt_codigo.getText());
        }
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER)) {
            if (codigoUsuario == 0) {
                Frm_ConUsuarios f = new Frm_ConUsuarios();
            } else {
                try {
                    usuarioDAO = new UsuarioDAO();
                    usuario = new Usuario();
                    usuario = usuarioDAO.findByCodigo(codigoUsuario);
                    getUsuario(usuario);
                    btn_alteracao.setEnabled(true);
                    btn_consulta.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "erro ao Buscar Usuario: " + codigoUsuario);
                }

            }
        }
    }//GEN-LAST:event_txt_codigoKeyPressed

    private void btn_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultaActionPerformed
        Frm_ConUsuarios f = new Frm_ConUsuarios();
        dispose();
    }//GEN-LAST:event_btn_consultaActionPerformed

    private void btn_proximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoActionPerformed
        validaProximo(txt_codigo.getText(), txt_nome.getText(), txt_cpf.getText(), txt_email.getText(), txt_usuario.getText(), txt_senha.getText(),
                txt_confirmaSenha.getText(), getBloqueado(), tipoUsuarioDAO.buscaTipoUsuario(cbx_tipoUsuario.getSelectedItem().toString()));
    }//GEN-LAST:event_btn_proximoActionPerformed

    private void btn_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorActionPerformed

    private void btn_adicionarContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adicionarContatoActionPerformed
        if (txt_contato.getText().equals("") == true) {
            JOptionPane.showMessageDialog(null, "Contato Inválido");
            txt_contato.requestFocus();
        } else {
            if (txt_telefone.getText().equals("") == true) {
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
    }//GEN-LAST:event_btn_adicionarContatoActionPerformed

    private void cbx_grupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_grupoActionPerformed
    }//GEN-LAST:event_cbx_grupoActionPerformed

    private void btn_removerContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerContatoActionPerformed
        if (tb_telefones.getSelectedRowCount() == 1) {
            remove(tb_telefones.getValueAt(tb_telefones.getSelectedRow(), 1).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um Telefone na lista para remover!");
        }
    }//GEN-LAST:event_btn_removerContatoActionPerformed

    private void btn_cadGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadGrupoActionPerformed
        Frm_CadGrupo f = new Frm_CadGrupo();
    }//GEN-LAST:event_btn_cadGrupoActionPerformed

    private void cbx_grupoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_grupoFocusGained
        carregaGrupos();
    }//GEN-LAST:event_cbx_grupoFocusGained

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
            java.util.logging.Logger.getLogger(Frm_CadUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CadUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CadUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CadUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Frm_CadUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btn_adicionarContato;
    private javax.swing.JButton btn_alteracao;
    private javax.swing.JButton btn_anterior;
    private javax.swing.JButton btn_bloqueado;
    private javax.swing.JButton btn_cadGrupo;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_consulta;
    private javax.swing.JButton btn_inclusao;
    private javax.swing.JButton btn_proximo;
    private javax.swing.JButton btn_removerContato;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_grupo;
    private javax.swing.JComboBox cbx_tipoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnl_botoes;
    private javax.swing.JPanel pnl_cadTelefones;
    private javax.swing.JPanel pnl_dados;
    private javax.swing.JPanel pnl_dados_telefones;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JRadioButton rbt_feminino;
    private javax.swing.JRadioButton rbt_masculino;
    private javax.swing.JTable tb_telefones;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JPasswordField txt_confirmaSenha;
    private javax.swing.JTextField txt_contato;
    private javax.swing.JFormattedTextField txt_cpf;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_operacao;
    private javax.swing.JPasswordField txt_senha;
    private javax.swing.JFormattedTextField txt_telefone;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
