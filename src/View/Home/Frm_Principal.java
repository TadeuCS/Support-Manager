package View.Home;

import Controller.AtendimentoDAO;
import Controller.PermissoesDAO;
import Controller.StatusAtendimentoDAO;
import Controller.UsuarioDAO;
import Model.Permissoes;
import Model.Telefone;
import Model.Usuario;
import Util.Classes.Criptografia;
import Util.Classes.GeraRelatorios;
import Util.Classes.PopMenu;
import View.Atendimento.Frm_Atendimento_Abertura;
import View.Cadastros.Frm_CadAplicativo;
import View.Cadastros.Frm_CadCliente;
import View.Cadastros.Frm_CadEmpresa;
import View.Cadastros.Frm_CadGrupo;
import View.Cadastros.Frm_CadInformacao;
import View.Cadastros.Frm_CadLinks;
import View.Cadastros.Frm_CadOrigem;
import View.Cadastros.Frm_CadParcela;
import View.Cadastros.Frm_CadPrioridade;
import View.Cadastros.Frm_CadSalario;
import View.Cadastros.Frm_CadSegmento;
import View.Cadastros.Frm_CadStatusAtendimento;
import View.Cadastros.Frm_CadTelefone;
import View.Cadastros.Frm_CadTipoAtendimento;
import View.Cadastros.Frm_CadTipoInformacao;
import View.Cadastros.Frm_CadUsuario;
import View.Consultas.Frm_ConUsuarios;
import View.Cadastros.Frm_CadTipoUsuario;
import View.Consultas.Frm_ConAtendimento;
import View.Consultas.Frm_ConClientes;
import View.Consultas.Frm_ConContatos;
import View.Consultas.Frm_ConRankUsuarios;
import View.Relatorios.Frm_RelAtendimento;
import View.Relatorios.Frm_RelClienteByLink;
import View.Relatorios.Frm_RelClienteBySegmento;
import View.Relatorios.Frm_RelInformacao;
import View.Relatorios.Frm_RelReciboCliente;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Frm_Principal extends javax.swing.JFrame {

    private String usuarioLogado;
    UsuarioDAO usuarioDAO;
    Usuario usuario;
    Permissoes permissoes;
    PermissoesDAO permissoesDAO;
    AtendimentoDAO atendimentoDAO;
    private StatusAtendimentoDAO statusAtendimentoDAO;
    public static PopMenu an;
    public static Frm_Principal j = null;
    int tentativas;

    public Frm_Principal() {
        initComponents();
        tentativas = 0;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        pnl_alteraSenha.setVisible(false);
        setUsuarioLogado(Frm_Login.getUsuario().getUsuario());
        criaPopMenu();
        statusAtendimentoDAO = new StatusAtendimentoDAO();
        getTotalAtendimentosByStatus();
    }

    public void solucoesTemporarias() {
        pnl_atalhos.setVisible(false);
        Menu_Relatorios.setVisible(false);
        item_tipoInformacao.setVisible(false);
    }

    public String setUsuarioDaLista() {
        String usuarioDaLista = null;
        usuarioDAO = new UsuarioDAO();
        if (getUsuarioLogado().equals("ADMINISTRADOR") == true) {
            usuarioDaLista = "%%";
        } else {
            if (usuarioDAO.consultaByUsuario(getUsuarioLogado()).getCodtipousuario().getDescricao().equals("SUPORTE") == false) {
                usuarioDaLista = "%%";
            } else {
                usuarioDaLista = getUsuarioLogado();
            }
        }
        return usuarioDaLista;
    }

    public void getTotalAtendimentosByStatus() {
        Thread acao;
        acao = new Thread(new Runnable() {
            @Override
            public void run() {
                while (getTitle() != "") {
                    lb_qtdeAbertos.setText("0");
                    lb_qtdeExecutando.setText("0");
                    lb_qtdeConcluidos.setText("0");
                    lb_qtdePendentes.setText("0");
                    try {
                        atendimentoDAO = new AtendimentoDAO();
                        for (int i = 0; i < atendimentoDAO.getCountAtendimentos(setUsuarioDaLista()).size(); i++) {
                            insereQuantidades((Object[]) atendimentoDAO.getCountAtendimentos(setUsuarioDaLista()).get(i));
                        }
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao Contar a quantidade de atendimentos");
                    }
                }
            }

            private void insereQuantidades(Object[] lista) {
                if (lista[0].equals("ABERTO") == true) {
                    lb_qtdeAbertos.setText(lista[1].toString());
                } else {
                    if (lista[0].equals("EXECUCAO") == true) {
                        lb_qtdeExecutando.setText(lista[1].toString());
                    } else {
                        if (lista[0].equals("CONCLUIDO") == true) {
                            lb_qtdeConcluidos.setText(lista[1].toString());
                        } else {
                            if (lista[0].equals("PENDENTE") == true) {
                                lb_qtdePendentes.setText(lista[1].toString());
                            }
                        }
                    }
                }
            }
        });
        acao.start();
    }

    public void trataPermissoes() {
        try {
            permissoesDAO = new PermissoesDAO();
            usuarioDAO = new UsuarioDAO();
            permissoes = new Permissoes();
            permissoes = permissoesDAO.findByTipoUsuario(usuarioDAO.consultaByUsuario(getUsuarioLogado()).getCodtipousuario());
            getPermissoesCadastro();
            getPermissoesConsulta();
            getPermissoesRelatorios();
            getPermissoesUtilitarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Permissões do usuario: " + getUsuarioLogado());
            Frm_Login f = new Frm_Login();
            this.dispose();
        }
    }

    public void getPermissoesCadastro() {
        item_aplicativo.setVisible(permissoes.getCadAplicativo());
        item_links.setVisible(permissoes.getCadLinks());
        item_Atendimento.setVisible(permissoes.getCadAtendimento());
        pnl_aberturaAtendimento.setVisible(permissoes.getCadAtendimento());
        item_prioridade.setVisible(permissoes.getCadPrioridade());
        item_origem.setVisible(permissoes.getCadOrigem());
        item_status.setVisible(permissoes.getCadStatusAtendimento());
        item_tipoAtendimento.setVisible(permissoes.getCadTipoAtendimento());
        item_cadCliente.setVisible(permissoes.getCadCliente());
        item_segmento.setVisible(permissoes.getCadSegmento());
        item_parcela.setVisible(permissoes.getCadParcela());
        item_salario.setVisible(permissoes.getCadSalario());
        item_contato.setVisible(permissoes.getCadContato());
        item_grupo.setVisible(permissoes.getCadGrupo());
        item_usuario.setVisible(permissoes.getCadUsuario());
        item_tipoUsuario.setVisible(permissoes.getCadTipoUsuario());
        item_Empresa.setVisible(permissoes.getCadEmpresa());
    }

    public void getPermissoesConsulta() {
        item_abertos.setVisible(permissoes.getConsAtendimentoAbertos());
        item_executando.setVisible(permissoes.getConsAtendimentoExecutando());
        item_concluidos.setVisible(permissoes.getConsAtendimentoConcluidos());
        item_pendentes.setVisible(permissoes.getConsAtendimentoPendentes());
        item_ConsCliente.setVisible(permissoes.getConsClientes());
        pnl_consClientes.setVisible(permissoes.getConsClientes());
        item_ConsContatos.setVisible(permissoes.getConsContatos());
        pnl_consContatos.setVisible(permissoes.getConsContatos());
        item_ConUsuarios.setVisible(permissoes.getConsUsuarios());
        item_ConsRankUsuarios.setVisible(permissoes.getConsRankUsuarios());
        pnl_rankUsuarios.setVisible(permissoes.getConsRankUsuarios());
    }

    public void getPermissoesUtilitarios() {
        item_enviaEmail.setVisible(permissoes.getUtiEnviarEmail());
        item_emiteRecibo.setVisible(permissoes.getUtiEmitirRecibo());
        item_informacao.setVisible(permissoes.getUtiInformacao());
        pnl_lancaInformacao.setVisible(permissoes.getUtiInformacao());
        item_tipoInformacao.setVisible(permissoes.getUtiTipoInformacao());
        item_trocaUsuario.setVisible(permissoes.getUtiTrocaUsuario());
        item_Permissoes.setVisible(permissoes.getUtiPermissoes());
    }

    public void getPermissoesRelatorios() {
        item_relAtendimentoAnalitico.setVisible(permissoes.getRelAtendimentoAnalitico());
        item_relAtendimentoSintetico.setVisible(permissoes.getRelAtendimentoSintetico());
        item_relClienteByLink.setVisible(permissoes.getRelClientesByLink());
        item_relClienteBySegmento.setVisible(permissoes.getRelClientesBySegmento());
        item_relInformacoes.setVisible(permissoes.getRelInformacoes());
        item_relUsuario.setVisible(permissoes.getRelUsuarios());
        if ((permissoes.getRelAtendimentoAnalitico() == false) && (permissoes.getRelAtendimentoSintetico() == false)
                && (permissoes.getRelClientesByLink() == false) && (permissoes.getRelClientesBySegmento() == false)
                && (permissoes.getRelInformacoes() == false) && (permissoes.getRelUsuarios() == false)) {
            Menu_Relatorios.setVisible(false);
        }
    }

    private void criaPopMenu() {
        try {
            addWindowListener(
                    new WindowAdapter() {
                        public void windowIconified(WindowEvent evnt) {
                            setVisible(false);
                            an = new PopMenu();
                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsuarioLogado() {
        return txt_usuarioLogado.getText();
    }

    public void setUsuarioLogado(String usuarioLogado) {
        txt_usuarioLogado.setText(usuarioLogado);
        if (usuarioLogado.equals("ADMIN") == true) {
            txt_usuarioLogado.setText("ADMINISTRADOR");
        } else {
            trataPermissoes();
            setTipoUsuarioLogado(usuarioLogado);
            if (usuarioDAO.consultaByUsuario(usuarioLogado).getSexo().equals('F') == true) {
                lb_boasVindas.setText("Bem Vinda");
            }
        }
    }

    public void setTipoUsuarioLogado(String nomeUsuario) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuario = new Usuario();
            usuario = usuarioDAO.consultaByUsuario(nomeUsuario);
            txt_Tipo.setText(usuario.getCodtipousuario().getDescricao());
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(null, "Erro ao localizar tipo do usuário: " + nomeUsuario);
        }
    }

    public void setFocusONLabel(JLabel label) {
        label.setForeground(Color.blue);
    }

    public void setFocusOFFLabel(JLabel label) {
        label.setForeground(Color.gray);
    }

    public String getTipoUsuarioLogado() {
        try {
            usuarioDAO = new UsuarioDAO();
            return usuarioDAO.consultaByUsuario(getUsuarioLogado()).getCodtipousuario().getDescricao();
        } catch (Exception e) {
            return "";
        }
    }

    public void listaAtendimentos(String status) {
        try {
            statusAtendimentoDAO = new StatusAtendimentoDAO();
            Frm_ConAtendimento f = new Frm_ConAtendimento(statusAtendimentoDAO.buscaStatusAtendimento(status), txt_usuarioLogado.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao Listar atendimentos, Status inválido!");
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Rodape = new javax.swing.JPanel();
        lb_boasVindas = new javax.swing.JLabel();
        txt_usuarioLogado = new javax.swing.JTextField();
        lb_qtdeConcluidos = new javax.swing.JLabel();
        lb_qtdePendentes = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_qtdeExecutando = new javax.swing.JLabel();
        lb_abertos = new javax.swing.JLabel();
        lb_qtdeAbertos = new javax.swing.JLabel();
        txt_Tipo = new javax.swing.JTextField();
        lb_boasVindas1 = new javax.swing.JLabel();
        pnl_alteraSenha = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_antigaSenha = new javax.swing.JPasswordField();
        txt_novaSenha = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_confirmaSenha = new javax.swing.JPasswordField();
        btn_salvar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();
        pnl_atalhos = new javax.swing.JPanel();
        pnl_cadCliente = new javax.swing.JPanel();
        atalhoCadastroCliente = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pnl_aberturaAtendimento = new javax.swing.JPanel();
        atalhoAbrirAtendimento = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pnl_consClientes = new javax.swing.JPanel();
        atalhoConsultarClientes = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pnl_consContatos = new javax.swing.JPanel();
        atalhoConsultarContatos = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        pnl_rankUsuarios = new javax.swing.JPanel();
        atalhoRankUsuários = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pnl_lancaInformacao = new javax.swing.JPanel();
        atalhoLancarInformacao = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Menu_barra = new javax.swing.JMenuBar();
        Menu_Cadastro = new javax.swing.JMenu();
        item_aplicativo = new javax.swing.JMenuItem();
        item_Atendimento = new javax.swing.JMenuItem();
        item_cadCliente = new javax.swing.JMenuItem();
        item_contato = new javax.swing.JMenuItem();
        item_Empresa = new javax.swing.JMenuItem();
        item_grupo = new javax.swing.JMenuItem();
        item_links = new javax.swing.JMenuItem();
        item_origem = new javax.swing.JMenuItem();
        item_parcela = new javax.swing.JMenuItem();
        item_prioridade = new javax.swing.JMenuItem();
        item_salario = new javax.swing.JMenuItem();
        item_segmento = new javax.swing.JMenuItem();
        item_status = new javax.swing.JMenuItem();
        item_tipoAtendimento = new javax.swing.JMenuItem();
        item_tipoUsuario = new javax.swing.JMenuItem();
        item_usuario = new javax.swing.JMenuItem();
        item_rankUsuarios = new javax.swing.JMenu();
        menuI_Atendimentos = new javax.swing.JMenu();
        item_abertos = new javax.swing.JMenuItem();
        item_executando = new javax.swing.JMenuItem();
        item_concluidos = new javax.swing.JMenuItem();
        item_pendentes = new javax.swing.JMenuItem();
        item_ConsCliente = new javax.swing.JMenuItem();
        item_ConsContatos = new javax.swing.JMenuItem();
        item_ConUsuarios = new javax.swing.JMenuItem();
        item_ConsRankUsuarios = new javax.swing.JMenuItem();
        Menu_Relatorios = new javax.swing.JMenu();
        item_relAtendimentoAnalitico = new javax.swing.JMenuItem();
        item_relAtendimentoSintetico = new javax.swing.JMenuItem();
        item_relClienteByLink = new javax.swing.JMenuItem();
        item_relClienteBySegmento = new javax.swing.JMenuItem();
        item_relUsuario = new javax.swing.JMenuItem();
        item_relInformacoes = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        Menu_Utilitários = new javax.swing.JMenu();
        item_AlterarSenha = new javax.swing.JMenuItem();
        item_enviaEmail = new javax.swing.JMenuItem();
        item_emiteRecibo = new javax.swing.JMenuItem();
        item_informacao = new javax.swing.JMenuItem();
        item_tipoInformacao = new javax.swing.JMenuItem();
        item_trocaUsuario = new javax.swing.JMenuItem();
        item_Permissoes = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bem Vindo");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        pnl_Rodape.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lb_boasVindas.setText("Bem Vindo ");

        txt_usuarioLogado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_usuarioLogado.setToolTipText("Clique aqui para alterar a senha deste Usuário");
        txt_usuarioLogado.setEnabled(false);
        txt_usuarioLogado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_usuarioLogadoMousePressed(evt);
            }
        });
        txt_usuarioLogado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usuarioLogadoActionPerformed(evt);
            }
        });

        lb_qtdeConcluidos.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        lb_qtdeConcluidos.setForeground(new java.awt.Color(102, 102, 102));
        lb_qtdeConcluidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_qtdeConcluidos.setText("0");
        lb_qtdeConcluidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_qtdeConcluidosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_qtdeConcluidosMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lb_qtdeConcluidosMousePressed(evt);
            }
        });

        lb_qtdePendentes.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        lb_qtdePendentes.setForeground(new java.awt.Color(102, 102, 102));
        lb_qtdePendentes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_qtdePendentes.setText("0");
        lb_qtdePendentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_qtdePendentesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_qtdePendentesMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lb_qtdePendentesMousePressed(evt);
            }
        });

        jLabel1.setText("Concluídos:");

        jLabel6.setText("Pendentes:");

        jLabel4.setText("Executando:");

        lb_qtdeExecutando.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        lb_qtdeExecutando.setForeground(new java.awt.Color(102, 102, 102));
        lb_qtdeExecutando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_qtdeExecutando.setText("0");
        lb_qtdeExecutando.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_qtdeExecutandoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_qtdeExecutandoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lb_qtdeExecutandoMousePressed(evt);
            }
        });

        lb_abertos.setText("Abertos:");

        lb_qtdeAbertos.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        lb_qtdeAbertos.setForeground(new java.awt.Color(102, 102, 102));
        lb_qtdeAbertos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_qtdeAbertos.setText("0");
        lb_qtdeAbertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_qtdeAbertosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_qtdeAbertosMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lb_qtdeAbertosMousePressed(evt);
            }
        });

        txt_Tipo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_Tipo.setToolTipText("Clique aqui para alterar a senha deste Usuário");
        txt_Tipo.setEnabled(false);
        txt_Tipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_TipoMousePressed(evt);
            }
        });
        txt_Tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TipoActionPerformed(evt);
            }
        });

        lb_boasVindas1.setText("Tipo:");

        javax.swing.GroupLayout pnl_RodapeLayout = new javax.swing.GroupLayout(pnl_Rodape);
        pnl_Rodape.setLayout(pnl_RodapeLayout);
        pnl_RodapeLayout.setHorizontalGroup(
            pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_RodapeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_boasVindas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_usuarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_abertos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_qtdeAbertos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_qtdeExecutando, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_qtdeConcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_qtdePendentes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_boasVindas1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        pnl_RodapeLayout.setVerticalGroup(
            pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_RodapeLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_qtdeConcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_boasVindas1)
                        .addComponent(txt_Tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_qtdePendentes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_boasVindas)
                        .addComponent(txt_usuarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_abertos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_qtdeAbertos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_qtdeExecutando, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        pnl_alteraSenha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Antiga Senha*:");

        txt_antigaSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_antigaSenhaKeyPressed(evt);
            }
        });

        txt_novaSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_novaSenhaKeyPressed(evt);
            }
        });

        jLabel3.setText("Nova Senha*:");

        jLabel5.setText("Confirma Senha*:");

        txt_confirmaSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confirmaSenhaKeyPressed(evt);
            }
        });

        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/salvar.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_fechar.setText("Fechar");
        btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_alteraSenhaLayout = new javax.swing.GroupLayout(pnl_alteraSenha);
        pnl_alteraSenha.setLayout(pnl_alteraSenhaLayout);
        pnl_alteraSenhaLayout.setHorizontalGroup(
            pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_alteraSenhaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_alteraSenhaLayout.createSequentialGroup()
                        .addGroup(pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_novaSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(txt_confirmaSenha)
                            .addComponent(txt_antigaSenha))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_alteraSenhaLayout.createSequentialGroup()
                        .addComponent(btn_fechar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar)))
                .addContainerGap())
        );
        pnl_alteraSenhaLayout.setVerticalGroup(
            pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_alteraSenhaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_antigaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_novaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_confirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_alteraSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_fechar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_atalhos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        atalhoCadastroCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atalhoCadastroCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cadCliente.png"))); // NOI18N
        atalhoCadastroCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalhoCadastroCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                atalhoCadastroClienteMousePressed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Cadastro de Cliente");

        javax.swing.GroupLayout pnl_cadClienteLayout = new javax.swing.GroupLayout(pnl_cadCliente);
        pnl_cadCliente.setLayout(pnl_cadClienteLayout);
        pnl_cadClienteLayout.setHorizontalGroup(
            pnl_cadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cadClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_cadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_cadClienteLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addComponent(atalhoCadastroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_cadClienteLayout.setVerticalGroup(
            pnl_cadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cadClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atalhoCadastroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        atalhoAbrirAtendimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atalhoAbrirAtendimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/abrirAtendimento.png"))); // NOI18N
        atalhoAbrirAtendimento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalhoAbrirAtendimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                atalhoAbrirAtendimentoMousePressed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Abrir Atendimento");

        javax.swing.GroupLayout pnl_aberturaAtendimentoLayout = new javax.swing.GroupLayout(pnl_aberturaAtendimento);
        pnl_aberturaAtendimento.setLayout(pnl_aberturaAtendimentoLayout);
        pnl_aberturaAtendimentoLayout.setHorizontalGroup(
            pnl_aberturaAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_aberturaAtendimentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_aberturaAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_aberturaAtendimentoLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addComponent(atalhoAbrirAtendimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_aberturaAtendimentoLayout.setVerticalGroup(
            pnl_aberturaAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_aberturaAtendimentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atalhoAbrirAtendimento, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap())
        );

        atalhoConsultarClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atalhoConsultarClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/consClientes.png"))); // NOI18N
        atalhoConsultarClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalhoConsultarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                atalhoConsultarClientesMousePressed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Consultar Clientes");

        javax.swing.GroupLayout pnl_consClientesLayout = new javax.swing.GroupLayout(pnl_consClientes);
        pnl_consClientes.setLayout(pnl_consClientesLayout);
        pnl_consClientesLayout.setHorizontalGroup(
            pnl_consClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_consClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_consClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_consClientesLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addComponent(atalhoConsultarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_consClientesLayout.setVerticalGroup(
            pnl_consClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_consClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atalhoConsultarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        atalhoConsultarContatos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atalhoConsultarContatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/consContatos.png"))); // NOI18N
        atalhoConsultarContatos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalhoConsultarContatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                atalhoConsultarContatosMousePressed(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Consultar Contatos");

        javax.swing.GroupLayout pnl_consContatosLayout = new javax.swing.GroupLayout(pnl_consContatos);
        pnl_consContatos.setLayout(pnl_consContatosLayout);
        pnl_consContatosLayout.setHorizontalGroup(
            pnl_consContatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_consContatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_consContatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_consContatosLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addComponent(atalhoConsultarContatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_consContatosLayout.setVerticalGroup(
            pnl_consContatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_consContatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atalhoConsultarContatos, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        atalhoRankUsuários.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atalhoRankUsuários.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/rankUsuarios.png"))); // NOI18N
        atalhoRankUsuários.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalhoRankUsuários.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                atalhoRankUsuáriosMousePressed(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Rank de Usuários");

        javax.swing.GroupLayout pnl_rankUsuariosLayout = new javax.swing.GroupLayout(pnl_rankUsuarios);
        pnl_rankUsuarios.setLayout(pnl_rankUsuariosLayout);
        pnl_rankUsuariosLayout.setHorizontalGroup(
            pnl_rankUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_rankUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_rankUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_rankUsuariosLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel16))
                    .addComponent(atalhoRankUsuários, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_rankUsuariosLayout.setVerticalGroup(
            pnl_rankUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_rankUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atalhoRankUsuários, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addContainerGap())
        );

        atalhoLancarInformacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atalhoLancarInformacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/lancarInformacao.png"))); // NOI18N
        atalhoLancarInformacao.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalhoLancarInformacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                atalhoLancarInformacaoMousePressed(evt);
            }
        });

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Lançar Informação");

        javax.swing.GroupLayout pnl_lancaInformacaoLayout = new javax.swing.GroupLayout(pnl_lancaInformacao);
        pnl_lancaInformacao.setLayout(pnl_lancaInformacaoLayout);
        pnl_lancaInformacaoLayout.setHorizontalGroup(
            pnl_lancaInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_lancaInformacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_lancaInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_lancaInformacaoLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel17))
                    .addComponent(atalhoLancarInformacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_lancaInformacaoLayout.setVerticalGroup(
            pnl_lancaInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_lancaInformacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atalhoLancarInformacao, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_atalhosLayout = new javax.swing.GroupLayout(pnl_atalhos);
        pnl_atalhos.setLayout(pnl_atalhosLayout);
        pnl_atalhosLayout.setHorizontalGroup(
            pnl_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_atalhosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_cadCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_aberturaAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_consClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_consContatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_rankUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_lancaInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_atalhosLayout.setVerticalGroup(
            pnl_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_atalhosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnl_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_cadCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_aberturaAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_consClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_consContatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_rankUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_lancaInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        Menu_Cadastro.setText("Cadastros");

        item_aplicativo.setText("Aplicativo");
        item_aplicativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_aplicativoActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_aplicativo);

        item_Atendimento.setText("Atendimento");
        item_Atendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_AtendimentoActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_Atendimento);

        item_cadCliente.setText("Cliente");
        item_cadCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_cadClienteActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_cadCliente);

        item_contato.setText("Contato");
        item_contato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_contatoActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_contato);

        item_Empresa.setText("Empresa");
        item_Empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_EmpresaActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_Empresa);

        item_grupo.setText("Grupo");
        item_grupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_grupoActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_grupo);

        item_links.setText("Links");
        item_links.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_linksActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_links);

        item_origem.setText("Origem");
        item_origem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_origemActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_origem);

        item_parcela.setText("Parcela");
        item_parcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_parcelaActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_parcela);

        item_prioridade.setText("Prioridade");
        item_prioridade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_prioridadeActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_prioridade);

        item_salario.setText("Salário");
        item_salario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_salarioActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_salario);

        item_segmento.setText("Segmento");
        item_segmento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_segmentoActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_segmento);

        item_status.setText("Status Atendimento");
        item_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_statusActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_status);

        item_tipoAtendimento.setText("Tipo Atendimento");
        item_tipoAtendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_tipoAtendimentoActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_tipoAtendimento);

        item_tipoUsuario.setText("Tipo Usuário");
        item_tipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_tipoUsuarioActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_tipoUsuario);

        item_usuario.setText("Usuário");
        item_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_usuarioActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_usuario);

        Menu_barra.add(Menu_Cadastro);

        item_rankUsuarios.setText("Consultas");

        menuI_Atendimentos.setText("Atendimentos");

        item_abertos.setText("Abertos");
        item_abertos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_abertosActionPerformed(evt);
            }
        });
        menuI_Atendimentos.add(item_abertos);

        item_executando.setText("Executando");
        item_executando.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_executandoActionPerformed(evt);
            }
        });
        menuI_Atendimentos.add(item_executando);

        item_concluidos.setText("Concluídos");
        item_concluidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_concluidosActionPerformed(evt);
            }
        });
        menuI_Atendimentos.add(item_concluidos);

        item_pendentes.setText("Pendêntes");
        item_pendentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_pendentesActionPerformed(evt);
            }
        });
        menuI_Atendimentos.add(item_pendentes);

        item_rankUsuarios.add(menuI_Atendimentos);

        item_ConsCliente.setText("Clientes");
        item_ConsCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ConsClienteActionPerformed(evt);
            }
        });
        item_rankUsuarios.add(item_ConsCliente);

        item_ConsContatos.setText("Contatos");
        item_ConsContatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ConsContatosActionPerformed(evt);
            }
        });
        item_rankUsuarios.add(item_ConsContatos);

        item_ConUsuarios.setText("Usuários");
        item_ConUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ConUsuariosActionPerformed(evt);
            }
        });
        item_rankUsuarios.add(item_ConUsuarios);

        item_ConsRankUsuarios.setText("Rank de Usuarios");
        item_ConsRankUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ConsRankUsuariosActionPerformed(evt);
            }
        });
        item_rankUsuarios.add(item_ConsRankUsuarios);

        Menu_barra.add(item_rankUsuarios);

        Menu_Relatorios.setText("Relatorios");

        item_relAtendimentoAnalitico.setText("Atendimento Analítico");
        item_relAtendimentoAnalitico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relAtendimentoAnaliticoActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relAtendimentoAnalitico);

        item_relAtendimentoSintetico.setText("Atendimento Sintético");
        item_relAtendimentoSintetico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relAtendimentoSinteticoActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relAtendimentoSintetico);

        item_relClienteByLink.setText("Cliente por Link");
        item_relClienteByLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relClienteByLinkActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relClienteByLink);

        item_relClienteBySegmento.setText("Cliente por Segmento");
        item_relClienteBySegmento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relClienteBySegmentoActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relClienteBySegmento);

        item_relUsuario.setText("Usuários");
        item_relUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relUsuarioActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relUsuario);

        item_relInformacoes.setText("Informações");
        item_relInformacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relInformacoesActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relInformacoes);
        Menu_Relatorios.add(jSeparator17);

        Menu_barra.add(Menu_Relatorios);

        Menu_Utilitários.setText("Utilitarios");

        item_AlterarSenha.setText("Alterar Senha");
        item_AlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_AlterarSenhaActionPerformed(evt);
            }
        });
        Menu_Utilitários.add(item_AlterarSenha);

        item_enviaEmail.setText("Enviar Email");
        item_enviaEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_enviaEmailActionPerformed(evt);
            }
        });
        Menu_Utilitários.add(item_enviaEmail);

        item_emiteRecibo.setText("Emitir Recibo");
        item_emiteRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_emiteReciboActionPerformed(evt);
            }
        });
        Menu_Utilitários.add(item_emiteRecibo);

        item_informacao.setText("Lançar Informação");
        item_informacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_informacaoActionPerformed(evt);
            }
        });
        Menu_Utilitários.add(item_informacao);

        item_tipoInformacao.setText("Tipo Informação");
        item_tipoInformacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_tipoInformacaoActionPerformed(evt);
            }
        });
        Menu_Utilitários.add(item_tipoInformacao);

        item_trocaUsuario.setText("Trocar de Usuário");
        item_trocaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_trocaUsuarioActionPerformed(evt);
            }
        });
        Menu_Utilitários.add(item_trocaUsuario);

        item_Permissoes.setText("Permissões");
        item_Permissoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_PermissoesActionPerformed(evt);
            }
        });
        Menu_Utilitários.add(item_Permissoes);

        Menu_barra.add(Menu_Utilitários);

        setJMenuBar(Menu_barra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Rodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(pnl_alteraSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(pnl_atalhos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnl_atalhos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                .addComponent(pnl_alteraSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Rodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void item_trocaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_trocaUsuarioActionPerformed
        Frm_Login f = new Frm_Login();
        f.setVisible(true);
        dispose();
    }//GEN-LAST:event_item_trocaUsuarioActionPerformed

    private void item_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_usuarioActionPerformed
        try {
            Frm_CadUsuario c = new Frm_CadUsuario();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_item_usuarioActionPerformed

    private void item_tipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_tipoUsuarioActionPerformed
        Frm_CadTipoUsuario c = new Frm_CadTipoUsuario();
    }//GEN-LAST:event_item_tipoUsuarioActionPerformed

    private void item_ConUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ConUsuariosActionPerformed
        Frm_ConUsuarios f = new Frm_ConUsuarios();
    }//GEN-LAST:event_item_ConUsuariosActionPerformed

    private void item_aplicativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_aplicativoActionPerformed
        Frm_CadAplicativo f = new Frm_CadAplicativo();
    }//GEN-LAST:event_item_aplicativoActionPerformed

    private void item_tipoAtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_tipoAtendimentoActionPerformed
        Frm_CadTipoAtendimento f = new Frm_CadTipoAtendimento();
    }//GEN-LAST:event_item_tipoAtendimentoActionPerformed

    private void item_origemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_origemActionPerformed
        Frm_CadOrigem f = new Frm_CadOrigem();
    }//GEN-LAST:event_item_origemActionPerformed

    private void item_prioridadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_prioridadeActionPerformed
        Frm_CadPrioridade f = new Frm_CadPrioridade();
    }//GEN-LAST:event_item_prioridadeActionPerformed

    private void item_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_statusActionPerformed
        Frm_CadStatusAtendimento s = new Frm_CadStatusAtendimento();
    }//GEN-LAST:event_item_statusActionPerformed

    private void item_linksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_linksActionPerformed
        Frm_CadLinks f = new Frm_CadLinks();
    }//GEN-LAST:event_item_linksActionPerformed

    private void item_segmentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_segmentoActionPerformed
        Frm_CadSegmento f = new Frm_CadSegmento();
    }//GEN-LAST:event_item_segmentoActionPerformed

    private void item_cadClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_cadClienteActionPerformed
        Frm_CadCliente f = new Frm_CadCliente();
    }//GEN-LAST:event_item_cadClienteActionPerformed

    private void item_informacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_informacaoActionPerformed
        Frm_CadInformacao f = new Frm_CadInformacao();
    }//GEN-LAST:event_item_informacaoActionPerformed

    private void item_tipoInformacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_tipoInformacaoActionPerformed
        Frm_CadTipoInformacao f = new Frm_CadTipoInformacao();
    }//GEN-LAST:event_item_tipoInformacaoActionPerformed

    private void item_contatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_contatoActionPerformed
        Telefone tel = new Telefone();
        Frm_CadTelefone f = new Frm_CadTelefone(tel);
    }//GEN-LAST:event_item_contatoActionPerformed

    private void item_grupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_grupoActionPerformed
        Frm_CadGrupo f = new Frm_CadGrupo();
    }//GEN-LAST:event_item_grupoActionPerformed

    private void item_ConsContatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ConsContatosActionPerformed
        Frm_ConContatos f = new Frm_ConContatos();
    }//GEN-LAST:event_item_ConsContatosActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == Event.ESCAPE) {
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente Sair?", "Saindo", 0) == 0) {
                Frm_Login f = new Frm_Login();
                f.setVisible(true);
                dispose();
            }
        }
    }//GEN-LAST:event_formKeyPressed

    private void item_parcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_parcelaActionPerformed
        Frm_CadParcela f = new Frm_CadParcela();
    }//GEN-LAST:event_item_parcelaActionPerformed

    private void item_salarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_salarioActionPerformed
        Frm_CadSalario f = new Frm_CadSalario();
    }//GEN-LAST:event_item_salarioActionPerformed

    private void item_ConsClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ConsClienteActionPerformed
        Frm_ConClientes f = new Frm_ConClientes();
    }//GEN-LAST:event_item_ConsClienteActionPerformed

    private void item_relAtendimentoAnaliticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relAtendimentoAnaliticoActionPerformed
        Frm_RelAtendimento f = new Frm_RelAtendimento("ANALITICO");
    }//GEN-LAST:event_item_relAtendimentoAnaliticoActionPerformed

    private void item_emiteReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_emiteReciboActionPerformed
        Frm_RelReciboCliente f = new Frm_RelReciboCliente();
    }//GEN-LAST:event_item_emiteReciboActionPerformed

    private void item_enviaEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_enviaEmailActionPerformed
        Frm_EnviarEmail f = new Frm_EnviarEmail();
    }//GEN-LAST:event_item_enviaEmailActionPerformed

    private void item_abertosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_abertosActionPerformed
        listaAtendimentos("ABERTO");
    }//GEN-LAST:event_item_abertosActionPerformed

    private void item_executandoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_executandoActionPerformed
        listaAtendimentos("EXECUCAO");
    }//GEN-LAST:event_item_executandoActionPerformed

    private void item_concluidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_concluidosActionPerformed
        listaAtendimentos("CONCLUIDO");
    }//GEN-LAST:event_item_concluidosActionPerformed

    private void item_pendentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_pendentesActionPerformed
        listaAtendimentos("PENDENTE");
    }//GEN-LAST:event_item_pendentesActionPerformed

    private void lb_qtdeConcluidosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeConcluidosMouseEntered
        setFocusONLabel(lb_qtdeConcluidos);
    }//GEN-LAST:event_lb_qtdeConcluidosMouseEntered

    private void lb_qtdeConcluidosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeConcluidosMouseExited
        setFocusOFFLabel(lb_qtdeConcluidos);
    }//GEN-LAST:event_lb_qtdeConcluidosMouseExited

    private void lb_qtdeExecutandoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeExecutandoMouseEntered
        setFocusONLabel(lb_qtdeExecutando);
    }//GEN-LAST:event_lb_qtdeExecutandoMouseEntered

    private void lb_qtdeExecutandoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeExecutandoMouseExited
        setFocusOFFLabel(lb_qtdeExecutando);
    }//GEN-LAST:event_lb_qtdeExecutandoMouseExited

    private void lb_qtdePendentesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdePendentesMouseEntered
        setFocusONLabel(lb_qtdePendentes);
    }//GEN-LAST:event_lb_qtdePendentesMouseEntered

    private void lb_qtdePendentesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdePendentesMouseExited
        setFocusOFFLabel(lb_qtdePendentes);
    }//GEN-LAST:event_lb_qtdePendentesMouseExited

    private void item_EmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_EmpresaActionPerformed
        Frm_CadEmpresa f = new Frm_CadEmpresa();
    }//GEN-LAST:event_item_EmpresaActionPerformed

    private void lb_qtdeExecutandoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeExecutandoMousePressed
        listaAtendimentos("EXECUCAO");
    }//GEN-LAST:event_lb_qtdeExecutandoMousePressed

    private void lb_qtdeConcluidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeConcluidosMousePressed
        listaAtendimentos("CONCLUIDO");
    }//GEN-LAST:event_lb_qtdeConcluidosMousePressed

    private void lb_qtdePendentesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdePendentesMousePressed
        listaAtendimentos("PENDENTE");
    }//GEN-LAST:event_lb_qtdePendentesMousePressed

    private void item_AtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_AtendimentoActionPerformed
        Frm_Atendimento_Abertura f = new Frm_Atendimento_Abertura();
    }//GEN-LAST:event_item_AtendimentoActionPerformed

    private void item_PermissoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_PermissoesActionPerformed
        Frm_Permissoes f = new Frm_Permissoes();
    }//GEN-LAST:event_item_PermissoesActionPerformed

    private void txt_usuarioLogadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usuarioLogadoActionPerformed

    }//GEN-LAST:event_txt_usuarioLogadoActionPerformed

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        pnl_alteraSenha.setVisible(false);
    }//GEN-LAST:event_btn_fecharActionPerformed

    private void txt_usuarioLogadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_usuarioLogadoMousePressed
        pnl_alteraSenha.setVisible(true);
        txt_antigaSenha.requestFocus();
    }//GEN-LAST:event_txt_usuarioLogadoMousePressed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        validaCampos(txt_antigaSenha.getText(), txt_novaSenha.getText(), txt_confirmaSenha.getText());
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void txt_antigaSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_antigaSenhaKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            txt_novaSenha.requestFocus();
        }
    }//GEN-LAST:event_txt_antigaSenhaKeyPressed

    private void txt_novaSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_novaSenhaKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            txt_confirmaSenha.requestFocus();
        }
    }//GEN-LAST:event_txt_novaSenhaKeyPressed

    private void txt_confirmaSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confirmaSenhaKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            btn_salvar.requestFocus();
        }
    }//GEN-LAST:event_txt_confirmaSenhaKeyPressed

    private void lb_qtdeAbertosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeAbertosMouseEntered
        setFocusONLabel(lb_qtdeAbertos);
    }//GEN-LAST:event_lb_qtdeAbertosMouseEntered

    private void lb_qtdeAbertosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeAbertosMouseExited
        setFocusOFFLabel(lb_qtdeAbertos);
    }//GEN-LAST:event_lb_qtdeAbertosMouseExited

    private void lb_qtdeAbertosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_qtdeAbertosMousePressed
        listaAtendimentos("ABERTO");
    }//GEN-LAST:event_lb_qtdeAbertosMousePressed

    private void item_relAtendimentoSinteticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relAtendimentoSinteticoActionPerformed
        Frm_RelAtendimento f = new Frm_RelAtendimento("SINTETICO");
    }//GEN-LAST:event_item_relAtendimentoSinteticoActionPerformed

    private void item_relUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relUsuarioActionPerformed
        gerarRelatorioUsuarios("Usuários", "src/Relatorios/Rel_Usuarios.jasper");
    }//GEN-LAST:event_item_relUsuarioActionPerformed

    private void item_relClienteByLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relClienteByLinkActionPerformed
        Frm_RelClienteByLink f = new Frm_RelClienteByLink();
    }//GEN-LAST:event_item_relClienteByLinkActionPerformed

    private void item_relInformacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relInformacoesActionPerformed
        Frm_RelInformacao f = new Frm_RelInformacao();
    }//GEN-LAST:event_item_relInformacoesActionPerformed

    private void item_AlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_AlterarSenhaActionPerformed
        pnl_alteraSenha.setVisible(true);
    }//GEN-LAST:event_item_AlterarSenhaActionPerformed

    private void item_relClienteBySegmentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relClienteBySegmentoActionPerformed
        Frm_RelClienteBySegmento f = new Frm_RelClienteBySegmento();
    }//GEN-LAST:event_item_relClienteBySegmentoActionPerformed

    private void atalhoCadastroClienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalhoCadastroClienteMousePressed
        Frm_CadCliente f = new Frm_CadCliente();
    }//GEN-LAST:event_atalhoCadastroClienteMousePressed

    private void atalhoAbrirAtendimentoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalhoAbrirAtendimentoMousePressed
        Frm_Atendimento_Abertura f = new Frm_Atendimento_Abertura();
    }//GEN-LAST:event_atalhoAbrirAtendimentoMousePressed

    private void atalhoConsultarClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalhoConsultarClientesMousePressed
        Frm_ConClientes f = new Frm_ConClientes();
    }//GEN-LAST:event_atalhoConsultarClientesMousePressed

    private void atalhoConsultarContatosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalhoConsultarContatosMousePressed
        Frm_ConContatos f = new Frm_ConContatos();
    }//GEN-LAST:event_atalhoConsultarContatosMousePressed

    private void atalhoRankUsuáriosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalhoRankUsuáriosMousePressed
        Frm_ConRankUsuarios f = new Frm_ConRankUsuarios();
    }//GEN-LAST:event_atalhoRankUsuáriosMousePressed

    private void atalhoLancarInformacaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalhoLancarInformacaoMousePressed
        Frm_CadInformacao f = new Frm_CadInformacao();
    }//GEN-LAST:event_atalhoLancarInformacaoMousePressed

    private void txt_TipoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_TipoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TipoMousePressed

    private void txt_TipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TipoActionPerformed

    private void item_ConsRankUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ConsRankUsuariosActionPerformed
        Frm_ConRankUsuarios f = new Frm_ConRankUsuarios();
    }//GEN-LAST:event_item_ConsRankUsuariosActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Principal();
            }
        });
        //Listener que capitura o evento "minimizar"

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Menu_Cadastro;
    private javax.swing.JMenu Menu_Relatorios;
    private javax.swing.JMenu Menu_Utilitários;
    private javax.swing.JMenuBar Menu_barra;
    private javax.swing.JLabel atalhoAbrirAtendimento;
    private javax.swing.JLabel atalhoCadastroCliente;
    private javax.swing.JLabel atalhoConsultarClientes;
    private javax.swing.JLabel atalhoConsultarContatos;
    private javax.swing.JLabel atalhoLancarInformacao;
    private javax.swing.JLabel atalhoRankUsuários;
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JMenuItem item_AlterarSenha;
    private javax.swing.JMenuItem item_Atendimento;
    private javax.swing.JMenuItem item_ConUsuarios;
    private javax.swing.JMenuItem item_ConsCliente;
    private javax.swing.JMenuItem item_ConsContatos;
    private javax.swing.JMenuItem item_ConsRankUsuarios;
    private javax.swing.JMenuItem item_Empresa;
    private javax.swing.JMenuItem item_Permissoes;
    private javax.swing.JMenuItem item_abertos;
    private javax.swing.JMenuItem item_aplicativo;
    private javax.swing.JMenuItem item_cadCliente;
    private javax.swing.JMenuItem item_concluidos;
    private javax.swing.JMenuItem item_contato;
    private javax.swing.JMenuItem item_emiteRecibo;
    private javax.swing.JMenuItem item_enviaEmail;
    private javax.swing.JMenuItem item_executando;
    private javax.swing.JMenuItem item_grupo;
    private javax.swing.JMenuItem item_informacao;
    private javax.swing.JMenuItem item_links;
    private javax.swing.JMenuItem item_origem;
    private javax.swing.JMenuItem item_parcela;
    private javax.swing.JMenuItem item_pendentes;
    private javax.swing.JMenuItem item_prioridade;
    private javax.swing.JMenu item_rankUsuarios;
    private javax.swing.JMenuItem item_relAtendimentoAnalitico;
    private javax.swing.JMenuItem item_relAtendimentoSintetico;
    private javax.swing.JMenuItem item_relClienteByLink;
    private javax.swing.JMenuItem item_relClienteBySegmento;
    private javax.swing.JMenuItem item_relInformacoes;
    private javax.swing.JMenuItem item_relUsuario;
    private javax.swing.JMenuItem item_salario;
    private javax.swing.JMenuItem item_segmento;
    private javax.swing.JMenuItem item_status;
    private javax.swing.JMenuItem item_tipoAtendimento;
    private javax.swing.JMenuItem item_tipoInformacao;
    private javax.swing.JMenuItem item_tipoUsuario;
    private javax.swing.JMenuItem item_trocaUsuario;
    private javax.swing.JMenuItem item_usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JLabel lb_abertos;
    private javax.swing.JLabel lb_boasVindas;
    private javax.swing.JLabel lb_boasVindas1;
    private javax.swing.JLabel lb_qtdeAbertos;
    private javax.swing.JLabel lb_qtdeConcluidos;
    private javax.swing.JLabel lb_qtdeExecutando;
    private javax.swing.JLabel lb_qtdePendentes;
    private javax.swing.JMenu menuI_Atendimentos;
    private javax.swing.JPanel pnl_Rodape;
    private javax.swing.JPanel pnl_aberturaAtendimento;
    private javax.swing.JPanel pnl_alteraSenha;
    private javax.swing.JPanel pnl_atalhos;
    private javax.swing.JPanel pnl_cadCliente;
    private javax.swing.JPanel pnl_consClientes;
    private javax.swing.JPanel pnl_consContatos;
    private javax.swing.JPanel pnl_lancaInformacao;
    private javax.swing.JPanel pnl_rankUsuarios;
    private javax.swing.JTextField txt_Tipo;
    private javax.swing.JPasswordField txt_antigaSenha;
    private javax.swing.JPasswordField txt_confirmaSenha;
    private javax.swing.JPasswordField txt_novaSenha;
    private javax.swing.JTextField txt_usuarioLogado;
    // End of variables declaration//GEN-END:variables

    private void validaCampos(String antigaSenha, String novaSenha, String confirmaSenha) {
        if (antigaSenha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Antiga Senha inválida!");
            txt_antigaSenha.requestFocus();
        } else {
            if (novaSenha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nova Senha inválida!");
                txt_novaSenha.requestFocus();
            } else {
                if (confirmaSenha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Confirmação da Nova Senha inválida!");
                    txt_confirmaSenha.requestFocus();
                } else {
                    if (novaSenha.equals(confirmaSenha) == false) {
                        JOptionPane.showMessageDialog(null, "Senhas Diferentes");
                        txt_novaSenha.setText(null);
                        txt_confirmaSenha.setText(null);
                        txt_novaSenha.requestFocus();
                    } else {
                        validaAntigaSenha(antigaSenha, novaSenha);
                    }
                }
            }
        }
    }

    private void validaAntigaSenha(String antigaSenha, String novaSenha) {
        tentativas++;
        try {
            usuarioDAO = new UsuarioDAO();
            usuario = new Usuario();
            usuario = usuarioDAO.consultaByUsuario(getUsuarioLogado());
            if (usuario.getSenha().equals(Criptografia.criptografar(antigaSenha)) == true) {
                salvar(novaSenha);
            } else {
                JOptionPane.showMessageDialog(null, "Senha Inválida para este Usuário!");
                limpaCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar Usuário: " + getUsuarioLogado());
        }
        System.out.println(tentativas);
        if (tentativas >= 3) {
            item_trocaUsuario.doClick();
        }
    }

    public void limpaCampos() {
        txt_novaSenha.setText(null);
        txt_antigaSenha.setText(null);
        txt_confirmaSenha.setText(null);
        txt_antigaSenha.requestFocus();
    }

    private void salvar(String novaSenha) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuario.setSenha(Criptografia.criptografar(novaSenha));
            usuarioDAO.salvar(usuario);
            JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
            limpaCampos();
            item_trocaUsuario.doClick();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar a senha do Usuário: " + getUsuarioLogado());
        }
    }

    private void gerarRelatorioUsuarios(String tipo, String diretorio) {
        try {
            Map parameters = new HashMap();
            GeraRelatorios geraRelatorios = new GeraRelatorios();
            geraRelatorios.imprimirRelatorioSQLNoRelatorio(parameters, diretorio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, tipo + " não encontrado!");
        }
    }

}
