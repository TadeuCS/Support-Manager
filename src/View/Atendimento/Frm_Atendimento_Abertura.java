package View.Atendimento;

import Controller.AtendimentoDAO;
import Controller.ClienteDAO;
import Controller.EmpresaDAO;
import Controller.OrigemDAO;
import Controller.PrioridadeDAO;
import Controller.StatusAtendimentoDAO;
import Controller.StatusPessoaDAO;
import Controller.TipoAtendimentoDAO;
import Controller.TipoPessoaDAO;
import Controller.TipoUsuarioDAO;
import Controller.UsuarioDAO;
import Model.Atendimento;
import Model.Empresa;
import Model.StatusPessoa;
import Model.TipoPessoa;
import Model.TipoUsuario;
import Model.Usuario;
import Util.Classes.AutoComplete;
import Util.Classes.Data;
import Util.Classes.FixedLengthDocument;
import Util.Email.EnviaEmail;
import View.Home.Frm_Principal;
import java.awt.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Frm_Atendimento_Abertura extends javax.swing.JFrame {

    Atendimento atendimento;
    Data data;
    Usuario usuario;
    AtendimentoDAO atendimentoDAO;
    ClienteDAO clienteDAO;
    UsuarioDAO usuarioDAO;
    TipoAtendimentoDAO tipoAtendimentoDAO;
    OrigemDAO origemDAO;
    PrioridadeDAO prioridadeDAO;
    StatusAtendimentoDAO statusAtendimentoDAO;
    Frm_Principal principal;
    StatusPessoaDAO statusPessoaDAO;
    StatusPessoa statusPessoa;
    TipoUsuarioDAO tipoUsuarioDAO;
    TipoUsuario tipoUsuario;

    public Frm_Atendimento_Abertura() {
        initComponents();
        setVisible(true);
        carregaUsuarios();
        carregaClientes();
        carregaTipos();
        carregaOrigem();
        carregaPrioridade();
        selecioneUsuarioLogado();
        txt_problemaInformado.setDocument(new FixedLengthDocument(255));
        txt_solicitante.setDocument(new FixedLengthDocument(100));
        AutoComplete.decorate(cbx_cliente);
        qtde.setText(txt_problemaInformado.getText().length() + "");
        selecionaOsPadroes(cbx_tipoAtendimento, cbx_origem, cbx_prioridade);
    }

    public void selecionaOsPadroes(JComboBox tipo, JComboBox origem, JComboBox prioridade) {
        tipo.setSelectedItem("SUPORTE");
        origem.setSelectedItem("TELEFONE");
        prioridade.setSelectedItem("NORMAL");
    }

    public void selecioneUsuarioLogado() {
        try {
            usuario = new Usuario();
            principal = new Frm_Principal();
            usuarioDAO = new UsuarioDAO();
            usuario = usuarioDAO.consultaByUsuario(principal.getUsuarioLogado());
            if (usuario.getCodtipousuario().getDescricao().equals("SUPORTE") == true) {
                cbx_usuario.setSelectedItem(usuario.getUsuario());
                cbx_usuario.setEnabled(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void carregaUsuarios() {
        try {
            usuarioDAO = new UsuarioDAO();
            tipoUsuarioDAO = new TipoUsuarioDAO();
            statusPessoaDAO = new StatusPessoaDAO();
            tipoUsuario = new TipoUsuario();
            statusPessoa = new StatusPessoa();
            cbx_usuario.removeAllItems();
            statusPessoa = statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO");
            tipoUsuario = tipoUsuarioDAO.buscaTipoUsuario("SUPORTE");
            for (int i = 0; i < usuarioDAO.listaUsuariosDesbloqueados(statusPessoa, tipoUsuario).size(); i++) {
                cbx_usuario.addItem(usuarioDAO.listaUsuariosDesbloqueados(statusPessoa, tipoUsuario).get(i).getUsuario());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Usuários");
        }
    }

    private void carregaClientes() {
        clienteDAO = new ClienteDAO();
        try {
            cbx_cliente.removeAllItems();
            statusPessoaDAO = new StatusPessoaDAO();
            for (int i = 0; i < clienteDAO.listaByStatus(statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO")).size(); i++) {
                cbx_cliente.addItem(clienteDAO.listaByStatus(statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO")).get(i).getNomeFantasia());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Clientes");
        }
    }

    private void carregaTipos() {
        tipoAtendimentoDAO = new TipoAtendimentoDAO();
        try {
            cbx_tipoAtendimento.removeAllItems();
            for (int i = 0; i < tipoAtendimentoDAO.lista().size(); i++) {
                cbx_tipoAtendimento.addItem(tipoAtendimentoDAO.lista().get(i).getDescricao());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Tipos de Usuários");
        }
    }

    private void carregaOrigem() {
        origemDAO = new OrigemDAO();
        try {
            cbx_origem.removeAllItems();
            for (int i = 0; i < origemDAO.lista().size(); i++) {
                cbx_origem.addItem(origemDAO.lista().get(i).getDescricao());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Origens");
        }
    }

    private void carregaPrioridade() {
        prioridadeDAO = new PrioridadeDAO();
        try {
            cbx_prioridade.removeAllItems();
            for (int i = 0; i < prioridadeDAO.lista().size(); i++) {
                cbx_prioridade.addItem(prioridadeDAO.lista().get(i).getDescricao());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Prioridades");
        }
    }

    public void validaCampos(JComboBox usuario, String dataAgendamento, JComboBox cliente, String solicitante, JComboBox tipoAtendimento,
            JComboBox origem, JComboBox prioridade, String problemaInformado) {
        if (usuario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Usuário inválido!");
            cbx_usuario.requestFocus();
        } else {
            if (dataAgendamento.replace("/", "").replace(":", "").trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Data de Agendamento inválida!");
                txt_data.requestFocus();
            } else {
                if (cliente.getSelectedItem()==null) {
                    JOptionPane.showMessageDialog(null, "Cliente inválido!");
                    cbx_cliente.requestFocus();
                } else {
                    if (solicitante.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Solicitante inválido!");
                        txt_solicitante.requestFocus();
                    } else {
                        if (tipoAtendimento.getSelectedItem() == null) {
                            JOptionPane.showMessageDialog(null, "Tipo de Atendimento inválido!");
                            cbx_tipoAtendimento.requestFocus();
                        } else {
                            if (origem.getSelectedItem() == null) {
                                JOptionPane.showMessageDialog(null, "Origem inválida!");
                                cbx_origem.requestFocus();
                            } else {
                                if (prioridade.getSelectedItem() == null) {
                                    JOptionPane.showMessageDialog(null, "Prioridade inválida!");
                                    cbx_prioridade.requestFocus();
                                } else {
                                    if (problemaInformado.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Problema Informado é inválido!");
                                        txt_problemaInformado.requestFocus();
                                    } else {
                                        salvar();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Date validaDataAgendamento() {
        if (txt_data.getText().equals("  /  /       :  ") == true) {
            return null;
        } else {
            return Data.getDataByTexto(txt_data.getText(), "dd/MM/yyyy HH:mm");
        }
    }

    public void instanciaControladores() {
        atendimento = new Atendimento();
        clienteDAO = new ClienteDAO();
        tipoAtendimentoDAO = new TipoAtendimentoDAO();
        origemDAO = new OrigemDAO();
        prioridadeDAO = new PrioridadeDAO();
    }

    public Date capturaDataAtual() throws ParseException {
        Date data = new Date();
        String formato = "dd/MM/yyyy HH:mm";
        Date date = new SimpleDateFormat(formato).parse(Data.getDataByDate(data, formato));
        return date;
    }

    public void setAtendimento() {
        try {
            instanciaControladores();
            statusAtendimentoDAO = new StatusAtendimentoDAO();
            atendimento.setDataAgendamento(validaDataAgendamento());
            atendimento.setCodusuario(usuarioDAO.consultaByUsuario(cbx_usuario.getSelectedItem().toString()));
            atendimento.setProblemaInformado(txt_problemaInformado.getText());
            atendimento.setCodcliente(clienteDAO.buscaClienteByNomeFantasia(cbx_cliente.getSelectedItem().toString()));
            atendimento.setCodtipoatendimento(tipoAtendimentoDAO.buscaTipoAtendimento(cbx_tipoAtendimento.getSelectedItem().toString()));
            atendimento.setCodorigem(origemDAO.buscaOrigem(cbx_origem.getSelectedItem().toString()));
            atendimento.setCodprioridade(prioridadeDAO.buscaPrioridade(cbx_prioridade.getSelectedItem().toString()));
            atendimento.setSolicitante(txt_solicitante.getText());
            atendimento.setProblemaInformado(txt_problemaInformado.getText());
            atendimento.setCodstatusatendimento(statusAtendimentoDAO.buscaStatusAtendimento("ABERTO"));
            atendimento.setPendencia('N');
            atendimento.setDataAbertura(capturaDataAtual());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao capturar informações do Atendimento!");
        }
    }

    public void salvar() {
        setAtendimento();
        try {
            atendimentoDAO = new AtendimentoDAO();
            atendimentoDAO.salvar(atendimento);
            JOptionPane.showMessageDialog(null, "Atendimento Aberto com Sucesso!");
            enviarAtendimentoByEmail();
            limpaCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao Salvar Atendimento!");
        }
    }

    public String criaMsgHTML(int numero, String cliente, String analista, String dataAbertura, String dataAgendamento,
            String solicitante, String origem, String tipo, String prioridade, String problemaInformado,
            String empresa, String telefone) {
        return "<html>\n"
                + "    <head>\n"
                + "        <title>TODO supply a title</title>\n"
                + "        <meta charset=\"\"iso-8859-1\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <p>Foi registrado em nosso sistema o atendimento Nº: " + numero + " com os respectivos dados:</p>\n"
                + "        <div align=\"center\">\n"
                + "            <table width=\"644\" height=\"357\" border=\"15\" bordercolor=\"#0000FF\">\n"
                + "                <tr>\n"
                + "                    <td width=\"311\"><pre>Cliente: " + cliente + "</pre>\n"
                + "                        <pre>Data Abertura: " + dataAbertura + "</pre>\n"
                + "                        <pre>Data Agendamento: " + dataAgendamento + "</pre>\n"
                + "                        <pre>Solicitante: " + solicitante + "</pre>\n"
                + "                        <pre>Origem: " + origem + "</pre>\n"
                + "                        <pre>Tipo Atendimento: " + tipo + "</pre>\n"
                + "                        <pre>Prioridade: " + prioridade + "</pre>\n"
                + "                        <pre>Analista: " + analista + "</pre>\n"
                + "                        <pre>Problema Informado: " + problemaInformado + "</pre></td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "        </div>\n"
                + "        <p>Suporte " + empresa + " " + telefone + ". E-mail automático, por favor não responda essa mensagem.</p>\n"
                + "        <p>&nbsp;</p>\n"
                + "    </body>\n"
                + "\n"
                + "\n"
                + "</html>";
    }

    public void enviarAtendimentoByEmail() {
        Thread acao = new Thread(new Runnable() {
            @Override
            public void run() {
                EnviaEmail send = new EnviaEmail();
                EmpresaDAO empresaDAO = new EmpresaDAO();
                Empresa empresa = new Empresa();
                List<String> destinatario = new ArrayList<String>();
                lb_status.setText("Enviando E-mail...");
                btn_salvar.setEnabled(false);
                try {
                    destinatario.add(atendimento.getCodcliente().getEmail());
                    empresa = empresaDAO.findByNomeFantasia("Olivet Sistemas");
                    send.enviaEmail(empresa.getCodemail().getSmtp(),
                            empresa.getCodemail().getPorta(),
                            empresa.getCodemail().getEmail(),
                            empresa.getCodemail().getSenha(),
                            destinatario,
                            "Abertura de Atendimento", criaMsgHTML(atendimentoDAO.getMaxAtendimento(),
                                    atendimento.getCodcliente().getNomeFantasia(),
                                    atendimento.getCodusuario().getUsuario(),
                                    Data.getDataByDate(atendimento.getDataAbertura(), "dd/MM/yyyy HH:mm"),
                                    Data.getDataByDate(atendimento.getDataAgendamento(), "dd/MM/yyyy HH:mm"),
                                    atendimento.getSolicitante(),
                                    atendimento.getCodorigem().getDescricao(),
                                    atendimento.getCodtipoatendimento().getDescricao(),
                                    atendimento.getCodprioridade().getDescricao(),
                                    atendimento.getProblemaInformado(),
                                    empresa.getNomeFantasia(),
                                    empresa.getTelefoneList().get(0).getTelefone()),
                            null);
                    lb_status.setText("E-mail enviado com sucesso!");
                    Thread.sleep(1000);
                    lb_status.setText(null);
                } catch (Exception e) {
                    lb_status.setText("Erro ao enviar E-mail.");
                    JOptionPane.showMessageDialog(null, "Emitente não cadastrado!");
                    lb_status.setText(null);
                }finally{
                    btn_salvar.setEnabled(true);
                }
            }
        });
        acao.start();
    }

    public void limpaCampos() {
        txt_solicitante.setText(null);
        txt_problemaInformado.setText(null);
        txt_data.setText(null);
        txt_data.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_data = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_solicitante = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbx_tipoAtendimento = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cbx_origem = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cbx_prioridade = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_problemaInformado = new javax.swing.JTextArea();
        cbx_cliente = new javax.swing.JComboBox();
        qtde = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbx_usuario = new javax.swing.JComboBox();
        btn_salvar = new javax.swing.JButton();
        btn_sair = new javax.swing.JButton();
        lb_status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela de Abertura de Atendimento");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Data Agendamento *:");

        try {
            txt_data.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_data.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_data.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataKeyPressed(evt);
            }
        });

        jLabel3.setText("Cliente*:");

        jLabel4.setText("Solicitante*:");

        jLabel5.setText("Tipo Atendimento *:");

        jLabel6.setText("Origem*:");

        jLabel7.setText("Prioridade*:");

        jLabel8.setText("Problema Informado *:");

        txt_problemaInformado.setColumns(5);
        txt_problemaInformado.setLineWrap(true);
        txt_problemaInformado.setRows(5);
        txt_problemaInformado.setWrapStyleWord(true);
        txt_problemaInformado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_problemaInformadoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txt_problemaInformado);

        cbx_cliente.setBackground(new java.awt.Color(204, 204, 204));
        cbx_cliente.setEditable(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_solicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_tipoAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_origem, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_prioridade, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_data, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(qtde, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbx_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(qtde, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbx_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_solicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbx_tipoAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbx_origem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbx_prioridade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel1.setText("Usuário* :");

        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/salvar.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_sair.setText("Fechar");
        btn_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sairActionPerformed(evt);
            }
        });

        lb_status.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lb_status.setForeground(new java.awt.Color(153, 0, 0));
        lb_status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbx_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lb_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbx_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_salvar)
                        .addComponent(btn_sair))
                    .addComponent(lb_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
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

    private void btn_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sairActionPerformed
        dispose();
    }//GEN-LAST:event_btn_sairActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        validaCampos(cbx_usuario,
                txt_data.getText(), 
                cbx_cliente,
                txt_solicitante.getText(),
                cbx_tipoAtendimento,
                cbx_origem,
                cbx_prioridade,
                txt_problemaInformado.getText());
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void txt_dataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataKeyPressed
        if ((evt.getKeyCode() == Event.ENTER) && (txt_data.getText().equals("  /  /       :  ") == true)) {
            data = new Data();
            Date dataAtual = new Date();
            txt_data.setText(data.getDataByDate(dataAtual, "dd/MM/yyyy HH:mm"));
        }
    }//GEN-LAST:event_txt_dataKeyPressed

    private void txt_problemaInformadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_problemaInformadoKeyReleased
        qtde.setText(txt_problemaInformado.getText().length() + "");
    }//GEN-LAST:event_txt_problemaInformadoKeyReleased

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
            java.util.logging.Logger.getLogger(Frm_Atendimento_Abertura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Abertura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Abertura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Abertura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Atendimento_Abertura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sair;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_cliente;
    private javax.swing.JComboBox cbx_origem;
    private javax.swing.JComboBox cbx_prioridade;
    private javax.swing.JComboBox cbx_tipoAtendimento;
    private javax.swing.JComboBox cbx_usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_status;
    private javax.swing.JLabel qtde;
    private javax.swing.JFormattedTextField txt_data;
    private javax.swing.JTextArea txt_problemaInformado;
    private javax.swing.JTextField txt_solicitante;
    // End of variables declaration//GEN-END:variables
}
