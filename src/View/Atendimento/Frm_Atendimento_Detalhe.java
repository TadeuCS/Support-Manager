/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Atendimento;

import Model.Atendimento;
import Util.Classes.Data;
import View.Consultas.Frm_ConAtendimento;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu
 */
public class Frm_Atendimento_Detalhe extends javax.swing.JFrame {

    public Frm_Atendimento_Detalhe(Atendimento atendimento) {
        initComponents();
        setVisible(true);
        getAtendimento(atendimento);
    }

    public void getAtendimento(Atendimento atendimento) {
        try {
            txt_codigo.setText(atendimento.getCodatendimento() + "");
            txt_cliente.setText(atendimento.getCodcliente().getNomeFantasia());
            if (atendimento.getDataAbertura() != null) {
                txt_dataAbertura.setText(Data.getDataByDate(atendimento.getDataAbertura(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getDataAgendamento() != null) {
                txt_dataAgendamento.setText(Data.getDataByDate(atendimento.getDataAgendamento(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getDataFechamento() != null) {
                txt_dataFechamento.setText(Data.getDataByDate(atendimento.getDataFechamento(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getDataFim() != null) {
                txt_dataFim.setText(Data.getDataByDate(atendimento.getDataFim(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getDataInicio() != null) {
                txt_dataInicio.setText(Data.getDataByDate(atendimento.getDataInicio(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getDataSolucao() != null) {
                txt_dataSolucao.setText(Data.getDataByDate(atendimento.getDataSolucao(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getCodprioridade() != null) {
                txt_prioridade.setText(atendimento.getCodprioridade().getDescricao());
            }
            if (atendimento.getCodorigem() != null) {
                txt_origem.setText(atendimento.getCodorigem().getDescricao());
            }
            if (atendimento.getCodtipoatendimento() != null) {
                txt_tipo.setText(atendimento.getCodtipoatendimento().getDescricao());
            }
            if (atendimento.getCodusuario() != null) {
                txt_usuario.setText(atendimento.getCodusuario().getUsuario());
            }
            if (atendimento.getProblemaInformado() != null) {
                txt_probInformado.setText(atendimento.getProblemaInformado());
            }
            if (atendimento.getProblemaDetectado() != null) {
                txt_probDetectado.setText(atendimento.getProblemaDetectado());
            }
            if (atendimento.getProblemaSolucao() != null) {
                txt_probSolucao.setText(atendimento.getProblemaSolucao());
            }
            if (atendimento.getProblemaPendencia()!= null) {
                txt_probPendencia.setText(atendimento.getProblemaPendencia());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar Atendimento: " + atendimento.getCodatendimento());
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        txt_cliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_dataAbertura = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_dataFechamento = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_dataInicio = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_dataFim = new javax.swing.JFormattedTextField();
        txt_dataAgendamento = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_dataSolucao = new javax.swing.JFormattedTextField();
        txt_origem = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_prioridade = new javax.swing.JTextField();
        txt_tipo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_probDetectado = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_probSolucao = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_probPendencia = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_probInformado = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        btn_fechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Atendimento detalhado");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código:");

        txt_codigo.setEnabled(false);

        txt_cliente.setEnabled(false);

        jLabel2.setText("Cliente:");

        try {
            txt_dataAbertura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataAbertura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataAbertura.setEnabled(false);
        txt_dataAbertura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataAberturaKeyPressed(evt);
            }
        });

        jLabel3.setText("Data Abertura:");

        try {
            txt_dataFechamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataFechamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataFechamento.setEnabled(false);
        txt_dataFechamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataFechamentoKeyPressed(evt);
            }
        });

        jLabel4.setText("Data Fechamento:");

        try {
            txt_dataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataInicio.setEnabled(false);
        txt_dataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataInicioKeyPressed(evt);
            }
        });

        jLabel5.setText("Data Início:");

        jLabel6.setText("Data Fim *:");

        try {
            txt_dataFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataFim.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataFim.setEnabled(false);
        txt_dataFim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataFimKeyPressed(evt);
            }
        });

        try {
            txt_dataAgendamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataAgendamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataAgendamento.setEnabled(false);
        txt_dataAgendamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataAgendamentoKeyPressed(evt);
            }
        });

        jLabel7.setText("Data Agendamento:");

        jLabel8.setText("Data Solução *:");

        try {
            txt_dataSolucao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataSolucao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataSolucao.setEnabled(false);
        txt_dataSolucao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataSolucaoKeyPressed(evt);
            }
        });

        txt_origem.setEnabled(false);

        jLabel9.setText("Origem:");

        jLabel10.setText("Prioridade:");

        txt_prioridade.setEnabled(false);

        txt_tipo.setEnabled(false);

        jLabel11.setText("Tipo:");

        txt_usuario.setEnabled(false);

        jLabel12.setText("Usuário:");

        txt_probDetectado.setColumns(5);
        txt_probDetectado.setLineWrap(true);
        txt_probDetectado.setRows(5);
        txt_probDetectado.setWrapStyleWord(true);
        txt_probDetectado.setEnabled(false);
        txt_probDetectado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_probDetectadoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txt_probDetectado);

        jLabel13.setText("Problema Detectado *:");

        txt_probSolucao.setColumns(5);
        txt_probSolucao.setLineWrap(true);
        txt_probSolucao.setRows(5);
        txt_probSolucao.setWrapStyleWord(true);
        txt_probSolucao.setEnabled(false);
        txt_probSolucao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_probSolucaoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txt_probSolucao);

        jLabel14.setText("Solução *:");

        txt_probPendencia.setColumns(5);
        txt_probPendencia.setLineWrap(true);
        txt_probPendencia.setRows(5);
        txt_probPendencia.setWrapStyleWord(true);
        txt_probPendencia.setEnabled(false);
        txt_probPendencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_probPendenciaKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(txt_probPendencia);

        jLabel15.setText("Pendencia *:");

        txt_probInformado.setColumns(5);
        txt_probInformado.setLineWrap(true);
        txt_probInformado.setRows(5);
        txt_probInformado.setWrapStyleWord(true);
        txt_probInformado.setEnabled(false);
        txt_probInformado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_probInformadoKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(txt_probInformado);

        jLabel16.setText("Problema Informado *:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_cliente))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_dataAbertura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txt_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txt_prioridade, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_dataFechamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel8))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_usuario)
                                        .addComponent(txt_origem, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_dataAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_dataFim)
                                        .addComponent(txt_dataSolucao, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txt_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_dataAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txt_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dataFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_dataSolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_prioridade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt_origem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_fechar.setText("Fechar");
        btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_fechar)
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

    private void txt_dataAberturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataAberturaKeyPressed
    }//GEN-LAST:event_txt_dataAberturaKeyPressed

    private void txt_dataFechamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataFechamentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dataFechamentoKeyPressed

    private void txt_dataInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataInicioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dataInicioKeyPressed

    private void txt_dataFimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataFimKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dataFimKeyPressed

    private void txt_dataAgendamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataAgendamentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dataAgendamentoKeyPressed

    private void txt_dataSolucaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataSolucaoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dataSolucaoKeyPressed

    private void txt_probDetectadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_probDetectadoKeyReleased
    }//GEN-LAST:event_txt_probDetectadoKeyReleased

    private void txt_probSolucaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_probSolucaoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_probSolucaoKeyReleased

    private void txt_probPendenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_probPendenciaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_probPendenciaKeyReleased

    private void txt_probInformadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_probInformadoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_probInformadoKeyReleased

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
            java.util.logging.Logger.getLogger(Frm_Atendimento_Detalhe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Detalhe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Detalhe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Detalhe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Frm_Atendimento_Detalhe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fechar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField txt_cliente;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JFormattedTextField txt_dataAbertura;
    private javax.swing.JFormattedTextField txt_dataAgendamento;
    private javax.swing.JFormattedTextField txt_dataFechamento;
    private javax.swing.JFormattedTextField txt_dataFim;
    private javax.swing.JFormattedTextField txt_dataInicio;
    private javax.swing.JFormattedTextField txt_dataSolucao;
    private javax.swing.JTextField txt_origem;
    private javax.swing.JTextField txt_prioridade;
    private javax.swing.JTextArea txt_probDetectado;
    private javax.swing.JTextArea txt_probInformado;
    private javax.swing.JTextArea txt_probPendencia;
    private javax.swing.JTextArea txt_probSolucao;
    private javax.swing.JTextField txt_tipo;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
