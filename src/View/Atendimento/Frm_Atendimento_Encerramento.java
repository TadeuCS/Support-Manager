/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Atendimento;

import Controller.AtendimentoDAO;
import Controller.StatusAtendimentoDAO;
import Model.Atendimento;
import Util.Classes.Data;
import java.awt.Event;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu
 */
public class Frm_Atendimento_Encerramento extends javax.swing.JFrame {

    private static int codigoAtendimento;
    AtendimentoDAO atendimentoDAO;
    Atendimento atendimento;
    StatusAtendimentoDAO statusAtendimentoDAO;

    public Frm_Atendimento_Encerramento(JButton botao, Atendimento atendimento) {
        initComponents();
        setVisible(true);
        pendenciaGroup.add(rbt_nao);
        pendenciaGroup.add(rbt_sim);
        getPendencia();
        this.atendimento = atendimento;
        getAtendimento(atendimento);
        if (botao.getText().equals("Finalizar") == true) {
            setTitle("Encerramento de Atendimento");
            btn_salvar.setText("Finalizar");
            btn_salvar.setIcon(botao.getIcon());
        } else {
            setTitle("Alteração de Atendimento");
            btn_salvar.setText("Salvar");
        }
    }

    public void getAtendimento(Atendimento atendimento) {
        try {
            txt_cliente.setText(atendimento.getCodcliente().getNomeFantasia());
            if (atendimento.getDataFim() != null) {
                txt_dataFim.setText(Data.getDataByDate(atendimento.getDataFim(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getDataInicio() != null) {
                txt_dataInicio.setText(Data.getDataByDate(atendimento.getDataInicio(), "dd/MM/yyyy HH:mm"));
            }
            if (atendimento.getCodusuario() != null) {
                txt_usuario.setText(atendimento.getCodusuario().getUsuario());
            }
            Frm_Atendimento_Encerramento.this.getPendencia(atendimento);
            if (atendimento.getProblemaPendencia() != null) {
                txt_pendencia.setText(atendimento.getProblemaPendencia());
            }
            if (atendimento.getProblemaInformado() != null) {
                txt_problemaInformado.setText(atendimento.getProblemaInformado());
            }
            if (atendimento.getProblemaDetectado() != null) {
                txt_problemaDetectado.setText(atendimento.getProblemaDetectado());
            }
            if (atendimento.getProblemaSolucao() != null) {
                txt_problemaSolucao.setText(atendimento.getProblemaSolucao());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar informações do atendimento: " + atendimento.getCodatendimento());
        }
    }

    public void getPendencia(Atendimento atendimento) {
        if (atendimento.getPendencia().equals("S") == true) {
            rbt_sim.setSelected(true);
            rbt_nao.setSelected(false);
        } else {
            rbt_nao.setSelected(true);
            rbt_sim.setSelected(false);
        }
    }

    public void getPendencia() {
        if (rbt_sim.getSelectedObjects() != null) {
            pnl_pendencia.setVisible(true);
            txt_pendencia.requestFocus();
        } else {
            pnl_pendencia.setVisible(false);
        }
    }

    public static int getCodigoAtendimento() {
        return codigoAtendimento;
    }

    public static void setCodigoAtendimento(int codigoAtendimento) {
        Frm_Atendimento_Encerramento.codigoAtendimento = codigoAtendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        try {
            atendimento.setDataInicio(Data.getDataByTexto(txt_dataInicio.getText(), "dd/MM/yyyy HH:mm"));
            atendimento.setDataFim(Data.getDataByTexto(txt_dataFim.getText(), "dd/MM/yyyy HH:mm"));
            if (btn_salvar.getText().equals("Finalizar") == true) {
                atendimento.setDataFechamento(Data.getDataByTexto(Data.getData("dd/MM/yyyy HH:mm"), "dd/MM/yyyy HH:mm"));
                statusAtendimentoDAO = new StatusAtendimentoDAO();
                atendimento.setCodstatusatendimento(statusAtendimentoDAO.buscaStatusAtendimento("CONCLUIDO"));
                atendimento.setProblemaSolucao(txt_problemaSolucao.getText());
            }
            setPendencia(atendimento);
            if (!txt_problemaInformado.getText().isEmpty()) {
                atendimento.setProblemaInformado(txt_problemaInformado.getText());
            }
            if (!txt_problemaDetectado.getText().isEmpty()) {
                atendimento.setProblemaDetectado(txt_problemaDetectado.getText());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar informações do Atendimento");
        }
    }

    public void setPendencia(Atendimento atendimento) {
        if (rbt_sim.getSelectedObjects() != null) {
            atendimento.setPendencia('S');
            atendimento.setProblemaPendencia(txt_pendencia.getText());
        } else {
            atendimento.setPendencia('N');
        }
    }

    public void limpaCampos() {
        txt_cliente.setText(null);
        txt_dataInicio.setText(null);
        txt_dataFim.setText(null);
        txt_usuario.setText(null);
        rbt_nao.setSelected(false);
        rbt_sim.setSelected(false);
        txt_problemaInformado.setText(null);
        txt_problemaDetectado.setText(null);
        txt_problemaSolucao.setText(null);
        txt_pendencia.setText(null);
    }

    public void salvar() {
        atendimentoDAO = new AtendimentoDAO();
        setAtendimento(atendimento);
        try {
            atendimentoDAO.salvar(atendimento);
            JOptionPane.showMessageDialog(null, "Atendimento " + btn_salvar.getText() + " com sucesso!");
            limpaCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao " + btn_salvar.getText() + " Atendimento!");
        }

    }

    public void validaCampos(String operacao,String dataInicio, String dataFim, boolean pendencia, String probInformado, String probDetectado, String probSolucao, String probPendencia) {
        if (dataInicio.equals("  /  /       :  ") == true) {
            JOptionPane.showMessageDialog(null, "Data Início inválida!");
            txt_dataInicio.requestFocus();
        } else {
            if (dataFim.equals("  /  /       :  ") == true) {
                JOptionPane.showMessageDialog(null, "Data Fim inválida!");
                txt_dataFim.requestFocus();
            } else {
                if ((rbt_sim.getSelectedObjects() != null) && (txt_pendencia.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "o movito da Pendencia informada é inválida!");
                    txt_pendencia.requestFocus();
                } else {
                    if (txt_problemaDetectado.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Problema Detectado inválido!");
                        txt_problemaDetectado.requestFocus();
                    } else {
                        if (txt_problemaSolucao.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Solução do problema inválida!");
                            txt_problemaSolucao.requestFocus();
                        } else {
                            salvar();
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pendenciaGroup = new javax.swing.ButtonGroup();
        pnl_fundo = new javax.swing.JPanel();
        pnl_dados = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_dataInicio = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_problemaInformado = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txt_dataFim = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        rbt_sim = new javax.swing.JRadioButton();
        rbt_nao = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_problemaDetectado = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        pnl_pendencia = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_pendencia = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txt_problemaSolucao = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txt_cliente = new javax.swing.JTextField();
        txt_usuario = new javax.swing.JTextField();
        btn_salvar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fechamento de Atendimento");
        setResizable(false);

        pnl_dados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Data Início *:");

        try {
            txt_dataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataInicioKeyPressed(evt);
            }
        });

        jLabel3.setText("Cliente*:");

        jLabel4.setText("Pendência:");

        jLabel8.setText("Problema Informado *:");

        txt_problemaInformado.setColumns(20);
        txt_problemaInformado.setRows(5);
        txt_problemaInformado.setEnabled(false);
        jScrollPane1.setViewportView(txt_problemaInformado);

        jLabel1.setText("Usuário* :");

        try {
            txt_dataFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataFim.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataFim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataFimKeyPressed(evt);
            }
        });

        jLabel9.setText("Data Fim *:");

        rbt_sim.setText("Sim");
        rbt_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_simActionPerformed(evt);
            }
        });

        rbt_nao.setText("Não");
        rbt_nao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_naoActionPerformed(evt);
            }
        });

        txt_problemaDetectado.setColumns(20);
        txt_problemaDetectado.setRows(5);
        jScrollPane2.setViewportView(txt_problemaDetectado);

        jLabel10.setText("Problema Detectado *:");

        jLabel11.setText("Pendência *:");

        txt_pendencia.setColumns(20);
        txt_pendencia.setRows(5);
        jScrollPane3.setViewportView(txt_pendencia);

        javax.swing.GroupLayout pnl_pendenciaLayout = new javax.swing.GroupLayout(pnl_pendencia);
        pnl_pendencia.setLayout(pnl_pendenciaLayout);
        pnl_pendenciaLayout.setHorizontalGroup(
            pnl_pendenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_pendenciaLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
        );
        pnl_pendenciaLayout.setVerticalGroup(
            pnl_pendenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnl_pendenciaLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 51, Short.MAX_VALUE))
        );

        txt_problemaSolucao.setColumns(20);
        txt_problemaSolucao.setRows(5);
        jScrollPane5.setViewportView(txt_problemaSolucao);

        jLabel13.setText("Solução *:");

        txt_cliente.setEnabled(false);

        txt_usuario.setEnabled(false);

        javax.swing.GroupLayout pnl_dadosLayout = new javax.swing.GroupLayout(pnl_dados);
        pnl_dados.setLayout(pnl_dadosLayout);
        pnl_dadosLayout.setHorizontalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_dataFim, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                                .addComponent(rbt_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rbt_sim))
                            .addComponent(txt_usuario)))
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_dadosLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnl_pendencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dadosLayout.setVerticalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rbt_sim)
                    .addComponent(rbt_nao))
                .addGap(7, 7, 7)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnl_pendencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/salvar.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_cancelar.setText("Fechar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_cancelar))
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

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void rbt_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_simActionPerformed
        getPendencia();
    }//GEN-LAST:event_rbt_simActionPerformed

    private void rbt_naoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_naoActionPerformed
        getPendencia();
    }//GEN-LAST:event_rbt_naoActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        salvar();
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void txt_dataInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataInicioKeyPressed
        if ((evt.getKeyCode() == Event.ENTER) && (txt_dataInicio.getText().equals("  /  /       :  ") == true)) {
            Data data = new Data();
            Date dataAtual = new Date();
            txt_dataInicio.setText(data.getDataByDate(dataAtual, "dd/MM/yyyy HH:mm"));
        }
    }//GEN-LAST:event_txt_dataInicioKeyPressed

    private void txt_dataFimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataFimKeyPressed
        if ((evt.getKeyCode() == Event.ENTER) && (txt_dataFim.getText().equals("  /  /       :  ") == true)) {
            Data data = new Data();
            Date dataAtual = new Date();
            txt_dataFim.setText(data.getDataByDate(dataAtual, "dd/MM/yyyy HH:mm"));
        }
    }//GEN-LAST:event_txt_dataFimKeyPressed

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
            java.util.logging.Logger.getLogger(Frm_Atendimento_Encerramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Encerramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Encerramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Atendimento_Encerramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Frm_Atendimento_Encerramento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.ButtonGroup pendenciaGroup;
    private javax.swing.JPanel pnl_dados;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JPanel pnl_pendencia;
    private javax.swing.JRadioButton rbt_nao;
    private javax.swing.JRadioButton rbt_sim;
    private javax.swing.JTextField txt_cliente;
    private javax.swing.JFormattedTextField txt_dataFim;
    private javax.swing.JFormattedTextField txt_dataInicio;
    private javax.swing.JTextArea txt_pendencia;
    private javax.swing.JTextArea txt_problemaDetectado;
    private javax.swing.JTextArea txt_problemaInformado;
    private javax.swing.JTextArea txt_problemaSolucao;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
