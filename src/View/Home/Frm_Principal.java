/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Home;

import Controller.PermissoesDAO;
import Controller.StatusAtendimentoDAO;
import Controller.UsuarioDAO;
import Model.Permissoes;
import Model.Usuario;
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
import View.Consultas.Frm_ConCliente;
import View.Consultas.Frm_ConContatos;
import View.Relatorios.Frm_RelAtendimento;
import View.Relatorios.Frm_RelCliente;
import View.Relatorios.Frm_RelReciboCliente;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Frm_Principal extends javax.swing.JFrame {

    private String usuarioLogado;
    UsuarioDAO usuarioDAO;
    Usuario usuario;
    Permissoes permissoes;
    PermissoesDAO permissoesDAO;
    private StatusAtendimentoDAO statusAtendimentoDAO;
    public static PopMenu an = null;
    public static Frm_Principal j;

    public Frm_Principal() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUsuarioLogado(Frm_Login.getUsuario().getUsuario());
        criaPopMenu();
        statusAtendimentoDAO = new StatusAtendimentoDAO();
    }

    public void setPermissoes() {
        try {
            permissoesDAO = new PermissoesDAO();
            usuarioDAO = new UsuarioDAO();
            permissoes = new Permissoes();
            permissoes = permissoesDAO.findByTipoUsuario(usuarioDAO.consultaByUsuario(getUsuarioLogado()).getCodtipousuario());

            item_aplicativo.setVisible(permissoes.getCadAplicativo());
            item_links.setVisible(permissoes.getCadLinks());
            item_Atendimento.setVisible(permissoes.getCadAtendimento());
            item_prioridade.setVisible(permissoes.getCadPrioridade());
            item_origem.setVisible(permissoes.getCadOrigem());
            item_status.setVisible(permissoes.getCadStatusAtendimento());
            item_tipoAtendimento.setVisible(permissoes.getCadTipoAtendimento());
            item_cliente.setVisible(permissoes.getCadCliente());
            item_segmento.setVisible(permissoes.getCadSegmento());
            item_parcela.setVisible(permissoes.getCadParcela());
            item_salario.setVisible(permissoes.getCadSalario());
            item_contato.setVisible(permissoes.getCadContato());
            item_grupo.setVisible(permissoes.getCadGrupo());
            item_usuario.setVisible(permissoes.getCadUsuario());
            item_tipoUsuario.setVisible(permissoes.getCadTipoUsuario());
            item_Empresa.setVisible(permissoes.getCadEmpresa());

            item_abertos.setVisible(permissoes.getConsAtendimentoAbertos());
            item_executando.setVisible(permissoes.getConsAtendimentoExecutando());
            item_concluidos.setVisible(permissoes.getConsAtendimentoConcluidos());
            item_pendentes.setVisible(permissoes.getConsAtendimentoPendentes());
            item_cliente.setVisible(permissoes.getConsClientes());
            item_contato.setVisible(permissoes.getConsContatos());
            item_usuario.setVisible(permissoes.getConsUsuarios());

            item_relAtendimento.setVisible(permissoes.getRelAtendimento());
            item_relCliente.setVisible(permissoes.getRelClientes());

            item_enviaEmail.setVisible(permissoes.getUtiEnviarEmail());
            item_emiteRecibo.setVisible(permissoes.getUtiEmitirRecibo());
            item_informacao.setVisible(permissoes.getUtiInformacao());
            item_tipoInformacao.setVisible(permissoes.getUtiInformacao());
            item_trocaUsuario.setVisible(permissoes.getUtiTrocaUsuario());
            item_Permissoes.setVisible(permissoes.getUtiPermissoes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Permissões do usuario: " + getUsuarioLogado());
            Frm_Login f = new Frm_Login();
            dispose();
        }
    }

    private void criaPopMenu() {
        try {
            addWindowListener(
                    new WindowAdapter() {
                        public void windowIconified(WindowEvent evnt) {
                            //Deixa a janela atual (Janelinha) invisevel!
                            setVisible(false);
                            //Instancia a classe responsavel pelo �cone na �rea de notifica��o.
                            an = new PopMenu();
                            dispose();
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
            setPermissoes();
            if (usuarioDAO.consultaByUsuario(usuarioLogado).getSexo().equals('F') == true) {
                lb_boasVindas.setText("Bem Vinda");
            }
            try {
                usuarioDAO = new UsuarioDAO();
                if (usuarioDAO.consultaByUsuario(usuarioLogado).getCodtipousuario().getDescricao().equals("SUPORTE") == true) {
                    Menu_Relatorios.setVisible(false);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao Buscar Tipo de usuario");
            }
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
        lb_fundo = new javax.swing.JLabel();
        Menu_barra = new javax.swing.JMenuBar();
        Menu_Cadastro = new javax.swing.JMenu();
        item_aplicativo = new javax.swing.JMenuItem();
        item_Atendimento = new javax.swing.JMenuItem();
        item_cliente = new javax.swing.JMenuItem();
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
        Menu_Consulta = new javax.swing.JMenu();
        menuI_Atendimentos = new javax.swing.JMenu();
        item_abertos = new javax.swing.JMenuItem();
        item_executando = new javax.swing.JMenuItem();
        item_concluidos = new javax.swing.JMenuItem();
        item_pendentes = new javax.swing.JMenuItem();
        menuI_clientes = new javax.swing.JMenuItem();
        menuI_contatos = new javax.swing.JMenuItem();
        menuI_Usuarios = new javax.swing.JMenuItem();
        Menu_Relatorios = new javax.swing.JMenu();
        item_relAtendimento = new javax.swing.JMenuItem();
        item_relCliente = new javax.swing.JMenuItem();
        Menu_Utilitários = new javax.swing.JMenu();
        item_enviaEmail = new javax.swing.JMenuItem();
        item_emiteRecibo = new javax.swing.JMenuItem();
        menuI_Informacao = new javax.swing.JMenu();
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
        txt_usuarioLogado.setEnabled(false);

        lb_qtdeConcluidos.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
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

        lb_qtdePendentes.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
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

        lb_qtdeExecutando.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
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

        javax.swing.GroupLayout pnl_RodapeLayout = new javax.swing.GroupLayout(pnl_Rodape);
        pnl_Rodape.setLayout(pnl_RodapeLayout);
        pnl_RodapeLayout.setHorizontalGroup(
            pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_RodapeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_boasVindas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_usuarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_RodapeLayout.setVerticalGroup(
            pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_RodapeLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_qtdeConcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_qtdePendentes, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_qtdeExecutando, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_RodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_boasVindas)
                        .addComponent(txt_usuarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        lb_fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/logo.png"))); // NOI18N

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

        item_cliente.setText("Cliente");
        item_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_clienteActionPerformed(evt);
            }
        });
        Menu_Cadastro.add(item_cliente);

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

        Menu_Consulta.setText("Consultas");

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

        Menu_Consulta.add(menuI_Atendimentos);

        menuI_clientes.setText("Clientes");
        menuI_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuI_clientesActionPerformed(evt);
            }
        });
        Menu_Consulta.add(menuI_clientes);

        menuI_contatos.setText("Contatos");
        menuI_contatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuI_contatosActionPerformed(evt);
            }
        });
        Menu_Consulta.add(menuI_contatos);

        menuI_Usuarios.setText("Usuários");
        menuI_Usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuI_UsuariosActionPerformed(evt);
            }
        });
        Menu_Consulta.add(menuI_Usuarios);

        Menu_barra.add(Menu_Consulta);

        Menu_Relatorios.setText("Relatorios");

        item_relAtendimento.setText("Atendimento");
        item_relAtendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relAtendimentoActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relAtendimento);

        item_relCliente.setText("Cliente");
        item_relCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_relClienteActionPerformed(evt);
            }
        });
        Menu_Relatorios.add(item_relCliente);

        Menu_barra.add(Menu_Relatorios);

        Menu_Utilitários.setText("Utilitarios");

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

        menuI_Informacao.setText("Informação");

        item_informacao.setText("Informação");
        item_informacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_informacaoActionPerformed(evt);
            }
        });
        menuI_Informacao.add(item_informacao);

        item_tipoInformacao.setText("Tipo Informação");
        item_tipoInformacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_tipoInformacaoActionPerformed(evt);
            }
        });
        menuI_Informacao.add(item_tipoInformacao);

        Menu_Utilitários.add(menuI_Informacao);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lb_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(107, 107, 107))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lb_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void menuI_UsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuI_UsuariosActionPerformed
        Frm_ConUsuarios f = new Frm_ConUsuarios();
    }//GEN-LAST:event_menuI_UsuariosActionPerformed

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

    private void item_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_clienteActionPerformed
        Frm_CadCliente f = new Frm_CadCliente();
    }//GEN-LAST:event_item_clienteActionPerformed

    private void item_informacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_informacaoActionPerformed
        Frm_CadInformacao f = new Frm_CadInformacao();
    }//GEN-LAST:event_item_informacaoActionPerformed

    private void item_tipoInformacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_tipoInformacaoActionPerformed
        Frm_CadTipoInformacao f = new Frm_CadTipoInformacao();
    }//GEN-LAST:event_item_tipoInformacaoActionPerformed

    private void item_contatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_contatoActionPerformed
        Frm_CadTelefone f = new Frm_CadTelefone();
    }//GEN-LAST:event_item_contatoActionPerformed

    private void item_grupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_grupoActionPerformed
        Frm_CadGrupo f = new Frm_CadGrupo();
    }//GEN-LAST:event_item_grupoActionPerformed

    private void menuI_contatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuI_contatosActionPerformed
        Frm_ConContatos f = new Frm_ConContatos();
    }//GEN-LAST:event_menuI_contatosActionPerformed

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

    private void menuI_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuI_clientesActionPerformed
        Frm_ConCliente f = new Frm_ConCliente();
    }//GEN-LAST:event_menuI_clientesActionPerformed

    private void item_relAtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relAtendimentoActionPerformed
        Frm_RelAtendimento f = new Frm_RelAtendimento();
    }//GEN-LAST:event_item_relAtendimentoActionPerformed

    private void item_relClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_relClienteActionPerformed
        Frm_RelCliente f = new Frm_RelCliente();
    }//GEN-LAST:event_item_relClienteActionPerformed

    private void item_emiteReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_emiteReciboActionPerformed
        Frm_RelReciboCliente f = new Frm_RelReciboCliente();
    }//GEN-LAST:event_item_emiteReciboActionPerformed

    private void item_enviaEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_enviaEmailActionPerformed
        Frm_TestaEmail f = new Frm_TestaEmail();
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
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JMenu Menu_Consulta;
    private javax.swing.JMenu Menu_Relatorios;
    private javax.swing.JMenu Menu_Utilitários;
    private javax.swing.JMenuBar Menu_barra;
    private javax.swing.JMenuItem item_Atendimento;
    private javax.swing.JMenuItem item_Empresa;
    private javax.swing.JMenuItem item_Permissoes;
    private javax.swing.JMenuItem item_abertos;
    private javax.swing.JMenuItem item_aplicativo;
    private javax.swing.JMenuItem item_cliente;
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
    private javax.swing.JMenuItem item_relAtendimento;
    private javax.swing.JMenuItem item_relCliente;
    private javax.swing.JMenuItem item_salario;
    private javax.swing.JMenuItem item_segmento;
    private javax.swing.JMenuItem item_status;
    private javax.swing.JMenuItem item_tipoAtendimento;
    private javax.swing.JMenuItem item_tipoInformacao;
    private javax.swing.JMenuItem item_tipoUsuario;
    private javax.swing.JMenuItem item_trocaUsuario;
    private javax.swing.JMenuItem item_usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lb_boasVindas;
    private javax.swing.JLabel lb_fundo;
    private javax.swing.JLabel lb_qtdeConcluidos;
    private javax.swing.JLabel lb_qtdeExecutando;
    private javax.swing.JLabel lb_qtdePendentes;
    private javax.swing.JMenu menuI_Atendimentos;
    private javax.swing.JMenu menuI_Informacao;
    private javax.swing.JMenuItem menuI_Usuarios;
    private javax.swing.JMenuItem menuI_clientes;
    private javax.swing.JMenuItem menuI_contatos;
    private javax.swing.JPanel pnl_Rodape;
    private javax.swing.JTextField txt_usuarioLogado;
    // End of variables declaration//GEN-END:variables
}
