package View.Home;

import Controller.PermissoesDAO;
import Controller.TipoUsuarioDAO;
import Model.Permissoes;
import Model.TipoUsuario;
import Model.Usuario;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;

public class Frm_Permissoes extends javax.swing.JFrame {

    Permissoes permissoes;
    PermissoesDAO permissoesDAO;
    TipoUsuarioDAO tipoUsuarioDAO;

    public Frm_Permissoes() {
        initComponents();
        setVisible(true);
        carregaTipoUsuarios();
    }

    public void carregaTipoUsuarios() {
        tipoUsuarioDAO = new TipoUsuarioDAO();
        try {
            for (int i = 0; i < tipoUsuarioDAO.lista().size(); i++) {
                cbx_tipoUsuario.addItem(tipoUsuarioDAO.lista().get(i).getDescricao());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Tipos de Usuários!");
        }
    }

    public void salvar() {
        tipoUsuarioDAO = new TipoUsuarioDAO();
        permissoesDAO = new PermissoesDAO();
        try {
            permissoes = new Permissoes();
            this.permissoes = permissoesDAO.findByTipoUsuario(tipoUsuarioDAO.buscaTipoUsuario(cbx_tipoUsuario.getSelectedItem().toString()));
        } catch (NoResultException e) {
            permissoes = new Permissoes();
        } finally {
            setPermissoesCadastro(permissoes);
            setPermissoesConsulta(permissoes);
            setPermissoesRelatorios(permissoes);
            setPermissoesUtilitarios(permissoes);
        }
        try {
            permissoesDAO = new PermissoesDAO();
            permissoes.setCodTipoUsuario(tipoUsuarioDAO.buscaTipoUsuario(cbx_tipoUsuario.getSelectedItem().toString()));
            permissoesDAO.salvar(permissoes);
            JOptionPane.showMessageDialog(null, "Permissões salvas com sucesso para usuários do Tipo: " + cbx_tipoUsuario.getSelectedItem().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Permisões para usuários do Tipo: " + cbx_tipoUsuario.getSelectedItem().toString());
        } finally {
            dispose();
        }
    }

    public void carregarPermissoesByTipoUsuario(String tipo) {
        tipoUsuarioDAO = new TipoUsuarioDAO();
        permissoesDAO = new PermissoesDAO();
        permissoes = new Permissoes();
        try {
            permissoes = permissoesDAO.findByTipoUsuario(tipoUsuarioDAO.buscaTipoUsuario(tipo));
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(null, "Não existem permissões para o tipo de usuário " + tipo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar permissões por Tipo de Usuário");
        }
        getPermissoesCadastro(permissoes);
        getPermissoesConsulta(permissoes);
        getPermissoesRelatorios(permissoes);
        getPermissoesUtilitarios(permissoes);
    }

    public void getPermissoesRelatorios(Permissoes permissoes) {
        try {
            chx_relAtendimentoAnalitico.setSelected(permissoes.getRelAtendimentoAnalitico());
            chx_relAtendimentoSintetico.setSelected(permissoes.getRelAtendimentoSintetico());
            chx_relClienteByLink.setSelected(permissoes.getRelClientesByLink());
            chx_relClienteBySegmento.setSelected(permissoes.getRelClientesBySegmento());
            chx_relInformacoes.setSelected(permissoes.getRelInformacoes());
            chx_relUsuarios.setSelected(permissoes.getRelUsuarios());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Informações Permissões de Relatórios");
        }
    }

    public void getPermissoesUtilitarios(Permissoes permissoes) {
        try {
            chx_utiEmitirRecibo.setSelected(permissoes.getUtiEmitirRecibo());
            chx_utiEnviarEmail.setSelected(permissoes.getUtiEnviarEmail());
            chx_utiInformacao.setSelected(permissoes.getUtiInformacao());
            chx_utiTipoInformacao.setSelected(permissoes.getUtiTipoInformacao());
            chx_utiTrocaUsuario.setSelected(permissoes.getUtiTrocaUsuario());
            chx_utiPermissoes.setSelected(permissoes.getUtiPermissoes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Informações Permissões de Utilitários");
        }
    }

    public void getPermissoesConsulta(Permissoes permissoes) {
        try {
            chx_consAtendimentosAbertos.setSelected(permissoes.getConsAtendimentoAbertos());
            chx_consAtendimentosExecutando.setSelected(permissoes.getConsAtendimentoExecutando());
            chx_consAtendimentosConcluidos.setSelected(permissoes.getConsAtendimentoConcluidos());
            chx_consAtendimentosPendentes.setSelected(permissoes.getConsAtendimentoPendentes());
            chx_consCliente.setSelected(permissoes.getConsClientes());
            chx_consContato.setSelected(permissoes.getConsContatos());
            chx_consUsuarios.setSelected(permissoes.getConsUsuarios());
            chx_consRankUsuarios.setSelected(permissoes.getConsRankUsuarios());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Informações Permissões de Consulta");
        }
    }

    public void getPermissoesCadastro(Permissoes permissoes) {
        try {
            chx_cadAplicativo.setSelected(permissoes.getCadAplicativo());
            chx_cadLinks.setSelected(permissoes.getCadLinks());
            chx_cadAtendimento.setSelected(permissoes.getCadAtendimento());
            chx_cadPrioridade.setSelected(permissoes.getCadPrioridade());
            chx_cadOrigem.setSelected(permissoes.getCadOrigem());
            chx_cadStatusAtendimento.setSelected(permissoes.getCadStatusAtendimento());
            chx_cadTipoAtendimento.setSelected(permissoes.getCadTipoAtendimento());
            chx_cadCliente.setSelected(permissoes.getCadCliente());
            chx_cadSegmento.setSelected(permissoes.getCadSegmento());
            chx_cadParcela.setSelected(permissoes.getCadParcela());
            chx_cadSalario.setSelected(permissoes.getCadSalario());
            chx_cadContato.setSelected(permissoes.getCadContato());
            chx_cadGrupo.setSelected(permissoes.getCadGrupo());
            chx_cadUsuario.setSelected(permissoes.getCadUsuario());
            chx_cadTipoUsuario.setSelected(permissoes.getCadTipoUsuario());
            chx_cadEmpresa.setSelected(permissoes.getCadEmpresa());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Informações Permissões de Cadastro");
        }
    }

    public void setPermissoesCadastro(Permissoes permissoes) {
        try {
            permissoes.setCadAplicativo(chx_cadAplicativo.isSelected());
            permissoes.setCadLinks(chx_cadLinks.isSelected());
            permissoes.setCadAtendimento(chx_cadAtendimento.isSelected());
            permissoes.setCadPrioridade(chx_cadPrioridade.isSelected());
            permissoes.setCadOrigem(chx_cadOrigem.isSelected());
            permissoes.setCadStatusAtendimento(chx_cadStatusAtendimento.isSelected());
            permissoes.setCadTipoAtendimento(chx_cadTipoAtendimento.isSelected());
            permissoes.setCadCliente(chx_cadCliente.isSelected());
            permissoes.setCadSegmento(chx_cadSegmento.isSelected());
            permissoes.setCadParcela(chx_cadParcela.isSelected());
            permissoes.setCadSalario(chx_cadSalario.isSelected());
            permissoes.setCadContato(chx_cadContato.isSelected());
            permissoes.setCadGrupo(chx_cadGrupo.isSelected());
            permissoes.setCadUsuario(chx_cadUsuario.isSelected());
            permissoes.setCadTipoUsuario(chx_cadTipoUsuario.isSelected());
            permissoes.setCadEmpresa(chx_cadEmpresa.isSelected());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar Permissões de cadastro");
        }
    }

    public void setPermissoesConsulta(Permissoes permissoes) {
        try {
            permissoes.setConsAtendimentoAbertos(chx_consAtendimentosAbertos.isSelected());
            permissoes.setConsAtendimentoExecutando(chx_consAtendimentosExecutando.isSelected());
            permissoes.setConsAtendimentoConcluidos(chx_consAtendimentosConcluidos.isSelected());
            permissoes.setConsAtendimentoPendentes(chx_consAtendimentosPendentes.isSelected());
            permissoes.setConsClientes(chx_consCliente.isSelected());
            permissoes.setConsContatos(chx_consContato.isSelected());
            permissoes.setConsUsuarios(chx_consUsuarios.isSelected());
            permissoes.setConsRankUsuarios(chx_consRankUsuarios.isSelected());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar Permissões de Consulta");
        }
    }

    public void setPermissoesRelatorios(Permissoes permissoes) {
        try {
            permissoes.setRelAtendimentoAnalitico(chx_relAtendimentoAnalitico.isSelected());
            permissoes.setRelAtendimentoSintetico(chx_relAtendimentoSintetico.isSelected());
            permissoes.setRelClientesByLink(chx_relClienteByLink.isSelected());
            permissoes.setRelClientesBySegmento(chx_relClienteBySegmento.isSelected());
            permissoes.setRelInformacoes(chx_relInformacoes.isSelected());
            permissoes.setRelUsuarios(chx_relUsuarios.isSelected());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar Permissões de Relatórios");
        }
    }

    public void setPermissoesUtilitarios(Permissoes permissoes) {
        try {
            permissoes.setUtiEmitirRecibo(chx_utiEmitirRecibo.isSelected());
            permissoes.setUtiEnviarEmail(chx_utiEnviarEmail.isSelected());
            permissoes.setUtiInformacao(chx_utiInformacao.isSelected());
            permissoes.setUtiTipoInformacao(chx_utiTipoInformacao.isSelected());
            permissoes.setUtiTrocaUsuario(chx_utiTrocaUsuario.isSelected());
            permissoes.setUtiPermissoes(chx_utiPermissoes.isSelected());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar Permissões de Utilitários");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        abas = new javax.swing.JTabbedPane();
        aba_cadastro = new javax.swing.JPanel();
        chx_cadAplicativo = new javax.swing.JCheckBox();
        chx_cadLinks = new javax.swing.JCheckBox();
        chx_cadAtendimento = new javax.swing.JCheckBox();
        chx_cadPrioridade = new javax.swing.JCheckBox();
        chx_cadOrigem = new javax.swing.JCheckBox();
        chx_cadStatusAtendimento = new javax.swing.JCheckBox();
        chx_cadTipoAtendimento = new javax.swing.JCheckBox();
        chx_cadCliente = new javax.swing.JCheckBox();
        chx_cadSegmento = new javax.swing.JCheckBox();
        chx_cadParcela = new javax.swing.JCheckBox();
        chx_cadSalario = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        chx_cadContato = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();
        chx_cadGrupo = new javax.swing.JCheckBox();
        chx_cadTipoUsuario = new javax.swing.JCheckBox();
        chx_cadUsuario = new javax.swing.JCheckBox();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        chx_cadEmpresa = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        chx_consAtendimentosAbertos = new javax.swing.JCheckBox();
        chx_consAtendimentosExecutando = new javax.swing.JCheckBox();
        chx_consAtendimentosConcluidos = new javax.swing.JCheckBox();
        chx_consAtendimentosPendentes = new javax.swing.JCheckBox();
        jSeparator6 = new javax.swing.JSeparator();
        chx_consCliente = new javax.swing.JCheckBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        chx_consContato = new javax.swing.JCheckBox();
        jSeparator9 = new javax.swing.JSeparator();
        chx_consUsuarios = new javax.swing.JCheckBox();
        jSeparator17 = new javax.swing.JSeparator();
        chx_consRankUsuarios = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        chx_relAtendimentoAnalitico = new javax.swing.JCheckBox();
        jSeparator10 = new javax.swing.JSeparator();
        chx_relAtendimentoSintetico = new javax.swing.JCheckBox();
        jSeparator11 = new javax.swing.JSeparator();
        chx_relClienteByLink = new javax.swing.JCheckBox();
        jSeparator18 = new javax.swing.JSeparator();
        chx_relClienteBySegmento = new javax.swing.JCheckBox();
        jSeparator19 = new javax.swing.JSeparator();
        chx_relUsuarios = new javax.swing.JCheckBox();
        jSeparator20 = new javax.swing.JSeparator();
        chx_relInformacoes = new javax.swing.JCheckBox();
        jSeparator21 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        chx_utiEnviarEmail = new javax.swing.JCheckBox();
        jSeparator12 = new javax.swing.JSeparator();
        chx_utiEmitirRecibo = new javax.swing.JCheckBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        chx_utiPermissoes = new javax.swing.JCheckBox();
        jSeparator15 = new javax.swing.JSeparator();
        chx_utiTrocaUsuario = new javax.swing.JCheckBox();
        chx_utiInformacao = new javax.swing.JCheckBox();
        jSeparator16 = new javax.swing.JSeparator();
        chx_utiTipoInformacao = new javax.swing.JCheckBox();
        jSeparator22 = new javax.swing.JSeparator();
        btn_salvar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbx_tipoUsuario = new javax.swing.JComboBox();
        btn_carregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Parametrização por Usuário");
        setResizable(false);

        chx_cadAplicativo.setText("CADASTRO/APLICATIVO");

        chx_cadLinks.setText("CADASTRO/LINKS");
        chx_cadLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadLinksActionPerformed(evt);
            }
        });

        chx_cadAtendimento.setText("CADASTRO/ATENDIMENTO");

        chx_cadPrioridade.setText("CADASTRO/PRIORIDADE");

        chx_cadOrigem.setText("CADASTRO/ORIGEM");

        chx_cadStatusAtendimento.setText("CADASTRO/STATUS ATENDIMENTO");

        chx_cadTipoAtendimento.setText("CADASTRO/TIPO ATENDIMENTO");
        chx_cadTipoAtendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadTipoAtendimentoActionPerformed(evt);
            }
        });

        chx_cadCliente.setText("CADASTRO/CLIENTE");
        chx_cadCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadClienteActionPerformed(evt);
            }
        });

        chx_cadSegmento.setText("CADASTRO/SEGMENTO");
        chx_cadSegmento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadSegmentoActionPerformed(evt);
            }
        });

        chx_cadParcela.setText("CADASTRO/PARCELA");
        chx_cadParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadParcelaActionPerformed(evt);
            }
        });

        chx_cadSalario.setText("CADASTRO/SALÁRIO");
        chx_cadSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadSalarioActionPerformed(evt);
            }
        });

        chx_cadContato.setText("CADASTRO/CONTATO");
        chx_cadContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadContatoActionPerformed(evt);
            }
        });

        chx_cadGrupo.setText("CADASTRO/GRUPO");
        chx_cadGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadGrupoActionPerformed(evt);
            }
        });

        chx_cadTipoUsuario.setText("CADASTRO/TIPO USUÁRIO");
        chx_cadTipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadTipoUsuarioActionPerformed(evt);
            }
        });

        chx_cadUsuario.setText("CADASTRO/USUÁRIO");
        chx_cadUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadUsuarioActionPerformed(evt);
            }
        });

        chx_cadEmpresa.setText("CADASTRO/EMPRESA");
        chx_cadEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_cadEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout aba_cadastroLayout = new javax.swing.GroupLayout(aba_cadastro);
        aba_cadastro.setLayout(aba_cadastroLayout);
        aba_cadastroLayout.setHorizontalGroup(
            aba_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aba_cadastroLayout.createSequentialGroup()
                .addGroup(aba_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(aba_cadastroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(aba_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chx_cadLinks, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chx_cadAplicativo, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(aba_cadastroLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(aba_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chx_cadCliente)
                            .addComponent(chx_cadSegmento)
                            .addComponent(chx_cadParcela)
                            .addComponent(chx_cadSalario)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chx_cadContato)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chx_cadGrupo)
                            .addComponent(chx_cadUsuario)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chx_cadTipoUsuario)
                            .addComponent(chx_cadEmpresa)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(aba_cadastroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(aba_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chx_cadPrioridade)
                            .addComponent(chx_cadOrigem)
                            .addComponent(chx_cadStatusAtendimento)
                            .addComponent(chx_cadTipoAtendimento)
                            .addComponent(chx_cadAtendimento))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        aba_cadastroLayout.setVerticalGroup(
            aba_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aba_cadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chx_cadAplicativo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chx_cadLinks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(chx_cadAtendimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chx_cadPrioridade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadOrigem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chx_cadStatusAtendimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadTipoAtendimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadSegmento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadParcela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadSalario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadContato)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadTipoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_cadEmpresa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abas.addTab("Cadastro", aba_cadastro);

        chx_consAtendimentosAbertos.setText("CONSULTA/ATENDIMENTO/ABERTOS");

        chx_consAtendimentosExecutando.setText("CONSULTA/ATENDIMENTO/EXECUTANDO");

        chx_consAtendimentosConcluidos.setText("CONSULTA/ATENDIMENTO/CONCLUÍDOS");

        chx_consAtendimentosPendentes.setText("CONSULTA/ATENDIMENTO/PENDENTES");

        chx_consCliente.setText("CONSULTA/CLIENTE");

        chx_consContato.setText("CONSULTA/CONTATO");

        chx_consUsuarios.setText("CONSULTA/USUÁRIOS");

        chx_consRankUsuarios.setText("CONSULTA/RANK DE USUÁRIOS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(chx_consRankUsuarios)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chx_consCliente)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(5, 5, 5)
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chx_consAtendimentosExecutando)
                                .addComponent(chx_consAtendimentosConcluidos)
                                .addComponent(chx_consAtendimentosPendentes)
                                .addComponent(chx_consAtendimentosAbertos)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(5, 5, 5)
                                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chx_consContato)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(5, 5, 5)
                                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(chx_consUsuarios)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chx_consAtendimentosAbertos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chx_consAtendimentosExecutando)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_consAtendimentosConcluidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chx_consAtendimentosPendentes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_consCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_consContato)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_consUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_consRankUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(216, Short.MAX_VALUE))
        );

        abas.addTab("Consulta", jPanel4);

        chx_relAtendimentoAnalitico.setText("RELATÓRIO/ATENDIMENTO ANALÍTICO");

        chx_relAtendimentoSintetico.setText("RELATÓRIO/ATENDIMENTO SINTÉTICO");

        chx_relClienteByLink.setText("RELATÓRIO/CLIENTE POR LINK");

        chx_relClienteBySegmento.setText("RELATÓRIO/CLIENTE POR SEGMENTO");

        chx_relUsuarios.setText("RELATÓRIO/USUÁRIOS");

        chx_relInformacoes.setText("RELATÓRIO/INFORMAÇÕES");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chx_relAtendimentoAnalitico)
                    .addComponent(chx_relAtendimentoSintetico)
                    .addComponent(chx_relClienteByLink)
                    .addComponent(chx_relClienteBySegmento)
                    .addComponent(chx_relUsuarios)
                    .addComponent(chx_relInformacoes)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chx_relAtendimentoAnalitico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_relAtendimentoSintetico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_relClienteByLink)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_relClienteBySegmento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_relUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_relInformacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(249, Short.MAX_VALUE))
        );

        abas.addTab("Relatórios", jPanel5);

        chx_utiEnviarEmail.setText("UTILITÁRIOS/ENVIAR E-MAIL");

        chx_utiEmitirRecibo.setText("UTILITÁRIOS/EMITIR RECIBO");

        chx_utiPermissoes.setText("UTILIÁRIOS/PARAMETRIZAÇÃO");

        chx_utiTrocaUsuario.setText("UTILITÁRIOS/TROCA DE USUÁRIO");

        chx_utiInformacao.setText("UTILITÁRIOS/INFORMAÇÃO");
        chx_utiInformacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chx_utiInformacaoActionPerformed(evt);
            }
        });

        chx_utiTipoInformacao.setText("UTILITÁRIOS/TIPO INFORMAÇÃO");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chx_utiEnviarEmail)
                            .addComponent(chx_utiInformacao)
                            .addComponent(chx_utiTrocaUsuario)
                            .addComponent(chx_utiPermissoes)
                            .addComponent(chx_utiTipoInformacao)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chx_utiEmitirRecibo)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chx_utiEnviarEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_utiEmitirRecibo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_utiInformacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_utiTipoInformacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_utiTrocaUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chx_utiPermissoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(254, Short.MAX_VALUE))
        );

        abas.addTab("Utilitários", jPanel6);

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

        jLabel1.setText("Tipo Usuário:");

        btn_carregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/carregar.png"))); // NOI18N
        btn_carregar.setText("Carregar");
        btn_carregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_carregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(abas)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_fundoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_tipoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_carregar)))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_tipoUsuario, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_carregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_fechar))
                .addGap(11, 11, 11))
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

    private void chx_cadTipoAtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadTipoAtendimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadTipoAtendimentoActionPerformed

    private void chx_cadClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadClienteActionPerformed

    private void chx_cadSegmentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadSegmentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadSegmentoActionPerformed

    private void chx_cadParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadParcelaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadParcelaActionPerformed

    private void chx_cadSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadSalarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadSalarioActionPerformed

    private void chx_cadLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadLinksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadLinksActionPerformed

    private void chx_cadContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadContatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadContatoActionPerformed

    private void chx_cadGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadGrupoActionPerformed

    private void chx_cadTipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadTipoUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadTipoUsuarioActionPerformed

    private void chx_cadUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadUsuarioActionPerformed

    private void chx_cadEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_cadEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_cadEmpresaActionPerformed

    private void chx_utiInformacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chx_utiInformacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chx_utiInformacaoActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        salvar();
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_carregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_carregarActionPerformed
        carregarPermissoesByTipoUsuario(cbx_tipoUsuario.getSelectedItem().toString());
    }//GEN-LAST:event_btn_carregarActionPerformed

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        dispose();
    }//GEN-LAST:event_btn_fecharActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_Permissoes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Permissoes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Permissoes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Permissoes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Frm_Permissoes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aba_cadastro;
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btn_carregar;
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_tipoUsuario;
    private javax.swing.JCheckBox chx_cadAplicativo;
    private javax.swing.JCheckBox chx_cadAtendimento;
    private javax.swing.JCheckBox chx_cadCliente;
    private javax.swing.JCheckBox chx_cadContato;
    private javax.swing.JCheckBox chx_cadEmpresa;
    private javax.swing.JCheckBox chx_cadGrupo;
    private javax.swing.JCheckBox chx_cadLinks;
    private javax.swing.JCheckBox chx_cadOrigem;
    private javax.swing.JCheckBox chx_cadParcela;
    private javax.swing.JCheckBox chx_cadPrioridade;
    private javax.swing.JCheckBox chx_cadSalario;
    private javax.swing.JCheckBox chx_cadSegmento;
    private javax.swing.JCheckBox chx_cadStatusAtendimento;
    private javax.swing.JCheckBox chx_cadTipoAtendimento;
    private javax.swing.JCheckBox chx_cadTipoUsuario;
    private javax.swing.JCheckBox chx_cadUsuario;
    private javax.swing.JCheckBox chx_consAtendimentosAbertos;
    private javax.swing.JCheckBox chx_consAtendimentosConcluidos;
    private javax.swing.JCheckBox chx_consAtendimentosExecutando;
    private javax.swing.JCheckBox chx_consAtendimentosPendentes;
    private javax.swing.JCheckBox chx_consCliente;
    private javax.swing.JCheckBox chx_consContato;
    private javax.swing.JCheckBox chx_consRankUsuarios;
    private javax.swing.JCheckBox chx_consUsuarios;
    private javax.swing.JCheckBox chx_relAtendimentoAnalitico;
    private javax.swing.JCheckBox chx_relAtendimentoSintetico;
    private javax.swing.JCheckBox chx_relClienteByLink;
    private javax.swing.JCheckBox chx_relClienteBySegmento;
    private javax.swing.JCheckBox chx_relInformacoes;
    private javax.swing.JCheckBox chx_relUsuarios;
    private javax.swing.JCheckBox chx_utiEmitirRecibo;
    private javax.swing.JCheckBox chx_utiEnviarEmail;
    private javax.swing.JCheckBox chx_utiInformacao;
    private javax.swing.JCheckBox chx_utiPermissoes;
    private javax.swing.JCheckBox chx_utiTipoInformacao;
    private javax.swing.JCheckBox chx_utiTrocaUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel pnl_fundo;
    // End of variables declaration//GEN-END:variables
}
