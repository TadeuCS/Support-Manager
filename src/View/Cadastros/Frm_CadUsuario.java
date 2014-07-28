/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import Controller.TipoUsuarioDAO;
import Controller.UsuarioDAO;
import Model.TipoUsuario;
import Model.Usuario;
import Util.Criptografia;
import Util.FixedLengthDocument;
import Util.IntegerDocument;
import Util.ValidarCpf;
import View.Consultas.Frm_ConUsuarios;
import View.Consultas.Frm_ConCliente;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tadeu
 */
public class Frm_CadUsuario extends javax.swing.JFrame {

    UsuarioDAO usuarioDAO;
    Usuario usuario;
    TipoUsuarioDAO tipoUsuarioDAO;
    DefaultTableModel model;
    private static int codigoUsuario;

    public Frm_CadUsuario() {
        initComponents();
        setVisible(true);
        txt_codigo.setDocument(new IntegerDocument(3));
        txt_nome.setDocument(new FixedLengthDocument(100));
        txt_usuario.setDocument(new FixedLengthDocument(20));
        txt_senha.setDocument(new FixedLengthDocument(18));
        txt_confirmaSenha.setDocument(new FixedLengthDocument(18));
        codigoUsuario = 0;
        carregaTipoUsuarios();
        camposOFF();

    }

    public static int getCodigoUsuario() {
        return codigoUsuario;
    }

    public static void setCodigoUsuario(int codigoUsuario) {
        Frm_CadUsuario.codigoUsuario = codigoUsuario;
    }

    public void camposOFF() {
        btn_consulta.setEnabled(true);
        btn_alteracao.setEnabled(true);
        btn_inclusao.setEnabled(true);
        btn_exclusao.setEnabled(true);
        btn_cancelar.setEnabled(false);
        btn_salvar.setEnabled(false);
        txt_codigo.setEnabled(false);
        txt_confirmaSenha.setEnabled(false);
        txt_cpf.setEnabled(false);
        txt_email.setEnabled(false);
        txt_nome.setEnabled(false);
        txt_senha.setEnabled(false);
        txt_usuario.setEnabled(false);
        cbx_tipo.setEnabled(false);
        rbt_masculino.setEnabled(false);
        rbt_feminino.setEnabled(false);
        btn_bloqueado.setEnabled(false);
    }

    public void camposON() {
        btn_consulta.setEnabled(false);
        btn_alteracao.setEnabled(false);
        btn_inclusao.setEnabled(false);
        btn_exclusao.setEnabled(false);
        btn_cancelar.setEnabled(true);
        btn_salvar.setEnabled(true);
        txt_codigo.setEnabled(true);
        txt_confirmaSenha.setEnabled(true);
        txt_cpf.setEnabled(true);
        txt_email.setEnabled(true);
        txt_nome.setEnabled(true);
        txt_senha.setEnabled(true);
        txt_usuario.setEnabled(true);
        cbx_tipo.setEnabled(true);
        rbt_masculino.setEnabled(true);
        rbt_feminino.setEnabled(true);
        btn_bloqueado.setEnabled(true);
    }

    public void carregaTipoUsuarios() {
        tipoUsuarioDAO = new TipoUsuarioDAO();
        try {
            int i = 0;
            while (i < tipoUsuarioDAO.lista().size()) {
                String linha = tipoUsuarioDAO.lista().get(i).getDescricao();
                cbx_tipo.addItem(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void buscaUsuario() {
        try {
            usuarioDAO = new UsuarioDAO();
            usuario = new Usuario();
            usuario = usuarioDAO.findByCodigo(codigoUsuario);
            carregaUsuario(usuario);
            txt_operacao.setText("CONSULTA");
            camposOFF();
            btn_inclusao.setEnabled(false);
            btn_consulta.setEnabled(false);
            btn_cancelar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao Buscar Usuario: " + codigoUsuario);
        }
    }

    public void carregaUsuario(Usuario usuario) {
        txt_codigo.setText(usuario.getCodusuario().toString());
        txt_cpf.setText(usuario.getCpf());
        txt_email.setText(usuario.getEmail());
        txt_nome.setText(usuario.getNome());
        txt_usuario.setText(usuario.getUsuario());
        cbx_tipo.setSelectedItem(usuario.getCodtipousuario().getDescricao());
        setSexo(usuario);
        setBloqueado(usuario);
    }

    public void booleanButton() {
        if (btn_bloqueado.getText().equals("SIM") == true) {
            btn_bloqueado.setText("NAO");
        } else {
            btn_bloqueado.setText("SIM");
        }
    }

    public void groupRadions(int selecionado) {
        if (selecionado == 1) {
            rbt_feminino.setSelected(false);
            rbt_masculino.setSelected(true);
        }
        if (selecionado == 2) {
            rbt_feminino.setSelected(true);
            rbt_masculino.setSelected(false);
        }
    }

    public void setBloqueado(Usuario usuario) {
        if (usuario.getBloqueado().equals("N") == true) {
            btn_bloqueado.setText("NÃO");
        } else {
            btn_bloqueado.setText("SIM");
        }
    }

    public void setSexo(Usuario usuario) {
        if (usuario.getSexo().compareTo('F') == 0) {
            rbt_feminino.setSelected(true);
        } else {
            rbt_masculino.setSelected(true);
        }
    }

    public void getSexo(Usuario usuario) {
        String sexo = null;
        if (rbt_feminino.getSelectedObjects() != null) {
            usuario.setSexo('F');
        } else {
            if (rbt_masculino.getSelectedObjects() != null) {
                usuario.setSexo('M');
            }
        }
    }

    public void validaNullos(String codigo, String nome, String cpf, String email, String usuario, String senha, String confirmaSenha) {
        if (nome.equals("") == true) {
            JOptionPane.showMessageDialog(null, "Nome Inválido");
            txt_nome.requestFocus();
        } else {
            if (cpf.equals("   .   .   -  ") == true) {
                JOptionPane.showMessageDialog(null, "CPF Inválido");
                txt_cpf.requestFocus();
            } else {
                ValidarCpf v = new ValidarCpf();
                if (v.validarCpf(txt_cpf.getText()) == false) {
                    JOptionPane.showMessageDialog(null, "CPF Inválido");
                    txt_cpf.requestFocus();
                } else {
                    if (email.equals("") == true) {
                        JOptionPane.showMessageDialog(null, "Email Inválido");
                        txt_email.requestFocus();
                    } else {
                        if (validarEmail(email) == false) {
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
                                                    salva(codigo, nome, cpf, email, usuario, senha, btn_bloqueado.getText().substring(0, 1),
                                                            tipoUsuarioDAO.buscaTipoUsuario(cbx_tipo.getSelectedItem().toString()));
                                                } else {
                                                    if (txt_operacao.getText().equals("ALTERAÇÃO") == true) {
                                                        alterar(codigo, nome, cpf, email, usuario, senha, btn_bloqueado.getText().substring(0, 1),
                                                                tipoUsuarioDAO.buscaTipoUsuario(cbx_tipo.getSelectedItem().toString()));
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
    }

    public void salva(String codigo, String nome, String cpf, String email, String user,
            String senha, String bloqueado, TipoUsuario tipo) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuario = new Usuario();
            getSexo(usuario);
            usuario.setCodtipousuario(tipo);
            usuario.setUsuario(user);
            usuario.setSenha(Criptografia.criptografar(senha));
            usuario.setCpf(cpf);
            usuario.setEmail(email);
            usuario.setNome(nome);
            usuario.setBloqueado(bloqueado);
            usuarioDAO.salvar(usuario);
            JOptionPane.showMessageDialog(null, "Usuario Salvo com Sucesso!");
            limpaCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Usuario\n" + "Usuário já cadastrado!", "Alerta", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void alterar(String codigo, String nome, String cpf, String email, String user,
            String senha, String bloqueado, TipoUsuario tipo) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuario = new Usuario();
            usuario.setCodusuario(codigoUsuario);
            getSexo(usuario);
            usuario.setCodtipousuario(tipo);
            usuario.setUsuario(user);
            usuario.setSenha(Criptografia.criptografar(senha));
            usuario.setCpf(cpf);
            usuario.setEmail(email);
            usuario.setNome(nome);
            usuario.setBloqueado(bloqueado);
            usuarioDAO.salvar(usuario);
            JOptionPane.showMessageDialog(null, "Usuario Alterado com Sucesso!");
            limpaCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar Usuario\n", "Alerta", JOptionPane.ERROR_MESSAGE);

        }
    }

    public static boolean validarEmail(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
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
        cbx_tipo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_cpf = new javax.swing.JFormattedTextField();
        txt_email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        pnl_botoes = new javax.swing.JPanel();
        btn_alteracao = new javax.swing.JButton();
        btn_inclusao = new javax.swing.JButton();
        btn_exclusao = new javax.swing.JButton();
        txt_operacao = new javax.swing.JTextField();
        btn_cancelar = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();
        btn_consulta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuario");
        setResizable(false);

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
        btn_bloqueado.setToolTipText("Clique para Mudar a opção");
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

        javax.swing.GroupLayout pnl_dadosLayout = new javax.swing.GroupLayout(pnl_dados);
        pnl_dados.setLayout(pnl_dadosLayout);
        pnl_dadosLayout.setHorizontalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_confirmaSenha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(txt_senha, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_usuario, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbt_masculino)
                                .addGap(14, 14, 14)
                                .addComponent(rbt_feminino))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_bloqueado)))))
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_cpf)
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)))
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_nome))
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_email)))))
                .addContainerGap())
        );
        pnl_dadosLayout.setVerticalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbt_masculino)
                    .addComponent(rbt_feminino)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(btn_bloqueado))
                    .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txt_confirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_botoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_alteracao.setText("Alteração");
        btn_alteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alteracaoActionPerformed(evt);
            }
        });

        btn_inclusao.setText("Inclusão");
        btn_inclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inclusaoActionPerformed(evt);
            }
        });

        btn_exclusao.setText("Exclusão");
        btn_exclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exclusaoActionPerformed(evt);
            }
        });

        txt_operacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_operacao.setEnabled(false);

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_consulta.setText("Consulta");
        btn_consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_botoesLayout = new javax.swing.GroupLayout(pnl_botoes);
        pnl_botoes.setLayout(pnl_botoesLayout);
        pnl_botoesLayout.setHorizontalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_inclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_alteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_exclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_botoesLayout.setVerticalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_cancelar)
                        .addComponent(btn_salvar))
                    .addGroup(pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_alteracao)
                        .addComponent(btn_inclusao)
                        .addComponent(btn_exclusao)
                        .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_consulta)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_botoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnl_botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        groupRadions(2);
    }//GEN-LAST:event_rbt_femininoActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        validaNullos(txt_codigo.getText(), txt_nome.getText(), txt_cpf.getText(), txt_email.getText(), txt_usuario.getText(), txt_senha.getText(),
                txt_confirmaSenha.getText());

    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_bloqueadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bloqueadoActionPerformed
        booleanButton();
    }//GEN-LAST:event_btn_bloqueadoActionPerformed

    private void rbt_masculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_masculinoActionPerformed
        groupRadions(1);
    }//GEN-LAST:event_rbt_masculinoActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        txt_operacao.setText(null);
        limpaCampos();
        camposOFF();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_alteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alteracaoActionPerformed
        if (txt_codigo.getText().equals("") == false) {
            txt_operacao.setText("ALTERAÇÃO");
            camposON();
            txt_codigo.setEnabled(false);
            txt_nome.requestFocus();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Codigo do usuário Inválido!");
        }

    }//GEN-LAST:event_btn_alteracaoActionPerformed

    private void btn_inclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inclusaoActionPerformed
        txt_operacao.setText("INCLUSÃO");
        camposON();
        btn_bloqueado.setEnabled(false);
        txt_codigo.setEnabled(false);
        txt_nome.requestFocus();
    }//GEN-LAST:event_btn_inclusaoActionPerformed

    private void btn_exclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exclusaoActionPerformed
        if (txt_codigo.getText().equals("") == false) {
            if (JOptionPane.showConfirmDialog(null, "Deseja relamente excluir o Usuaário: " + usuario.getUsuario(),
                    "Atenção", 0, JOptionPane.INFORMATION_MESSAGE) == 0) {
                try {
                    usuarioDAO = new UsuarioDAO();
                    usuario = usuarioDAO.findByCodigo(codigoUsuario);
                    usuarioDAO.excluir(usuario);
                    JOptionPane.showMessageDialog(null, "Usuário excluido com Sucesso!");
                    txt_codigo.requestFocus();
                    limpaCampos();
                    camposOFF();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "erro ao excluir o Usuário: " + usuario.getUsuario());
                }

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Codigo do usuário Inválido!");
        }
    }//GEN-LAST:event_btn_exclusaoActionPerformed

    private void txt_codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoKeyPressed
        if (txt_codigo.getText().equals("") == false) {
            codigoUsuario = Integer.parseInt(txt_codigo.getText());
        }
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER)) {
            if (codigoUsuario == 0) {
                Frm_ConUsuarios f = new Frm_ConUsuarios();
            } else {
                txt_codigo.setText(codigoUsuario + "");
                try {
                    usuarioDAO = new UsuarioDAO();
                    usuario = new Usuario();
                    usuario = usuarioDAO.findByCodigo(codigoUsuario);
                    carregaUsuario(usuario);
                    btn_alteracao.setEnabled(true);
                    btn_exclusao.setEnabled(true);
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
                if ("Nimbus".equals(info.getName())) {
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
                new Frm_CadUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_alteracao;
    private javax.swing.JButton btn_bloqueado;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_consulta;
    private javax.swing.JButton btn_exclusao;
    private javax.swing.JButton btn_inclusao;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_tipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pnl_botoes;
    private javax.swing.JPanel pnl_dados;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JRadioButton rbt_feminino;
    private javax.swing.JRadioButton rbt_masculino;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JPasswordField txt_confirmaSenha;
    private javax.swing.JFormattedTextField txt_cpf;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_operacao;
    private javax.swing.JPasswordField txt_senha;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
