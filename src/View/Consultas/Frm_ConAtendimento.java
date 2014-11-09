/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Consultas;

import Controller.AtendimentoDAO;
import Controller.StatusAtendimentoDAO;
import Controller.UsuarioDAO;
import Model.Atendimento;
import Model.StatusAtendimento;
import Model.Usuario;
import Util.Classes.Data;
import View.Atendimento.Frm_Atendimento_Detalhe;
import View.Atendimento.Frm_Atendimento_Encerramento;
import View.Home.Frm_Principal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Tadeu
 */
public final class Frm_ConAtendimento extends javax.swing.JFrame {

    DefaultTableModel model;
    AtendimentoDAO atendimentoDAO;
    Frm_Principal principal = new Frm_Principal();
    UsuarioDAO usuarioDAO;
    StatusAtendimentoDAO statusAtendimentoDAO;
    Atendimento atendimento;

    public Frm_ConAtendimento(StatusAtendimento status, String usuarioLogado) {
        initComponents();
        model = (DefaultTableModel) tb_atendimentos.getModel();
        setVisible(true);
        actionByStatus(status);
        usuarioDAO = new UsuarioDAO();
        listaAtendimentoByStatusAndUsuario(status, usuarioLogado);
    }

    private void actionByStatus(StatusAtendimento status) {
        if (status.getDescricao().equals("ABERTO") == true) {
            setTitle("Consulta de Atendimentos Abertos");
            btn_alterar.setEnabled(true);
            btn_consultar.setEnabled(true);
            btn_apagar.setEnabled(true);
            btn_sair.setEnabled(true);
            btn_finalizar.setEnabled(false);
        }
        if (status.getDescricao().equals("CONCLUIDO") == true) {
            setTitle("Consulta de Atendimentos Concluídos");
            btn_alterar.setEnabled(false);
            btn_consultar.setEnabled(true);
            btn_apagar.setEnabled(false);
            btn_sair.setEnabled(true);
            btn_finalizar.setEnabled(false);
        }
        if (status.getDescricao().equals("EXECUCAO") == true) {
            setTitle("Consulta de Atendimentos em Execução");
            btn_alterar.setEnabled(true);
            btn_consultar.setEnabled(true);
            btn_apagar.setEnabled(true);
            btn_sair.setEnabled(true);
            btn_finalizar.setEnabled(true);
        }
        if (status.getDescricao().equals("PENDENTE") == true) {
            setTitle("Consulta de Atendimentos Pendentes");
            btn_alterar.setEnabled(true);
            btn_consultar.setEnabled(true);
            btn_apagar.setEnabled(false);
            btn_sair.setEnabled(true);
            btn_finalizar.setEnabled(true);
        }
    }

    public void listaAtendimentoByStatusAndUsuario(StatusAtendimento statusAtendimento, String usuario) {
        atendimentoDAO = new AtendimentoDAO();
        try {
            limpaTabela();
            Frm_Principal f = new Frm_Principal();
            List<Atendimento> lista = atendimentoDAO.listaByStatusAndUsuario(statusAtendimento, f.setUsuarioDaLista());
            for (int i = 0; i < lista.size(); i++) {
                String[] linha = new String[]{
                    lista.get(i).getCodatendimento().toString(),
                    Data.getDataByDate(lista.get(i).getDataAgendamento(), "dd/MM/yyyy HH:mm"),
                    lista.get(i).getCodcliente().getNomeFantasia(),
                    lista.get(i).getCodtipoatendimento().getDescricao(),
                    lista.get(i).getCodprioridade().getDescricao()};
                model.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void finalizar() {
        if (tb_atendimentos.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        } else {
            if (tb_atendimentos.getSelectedRowCount() > 1) {
                JOptionPane.showMessageDialog(null, "Selecione Apenas uma linha!");
            } else {
                try {
                    atendimentoDAO = new AtendimentoDAO();
                    Frm_Atendimento_Encerramento f = new Frm_Atendimento_Encerramento(btn_finalizar, atendimentoDAO.getByCodigo(
                            Integer.parseInt(tb_atendimentos.getValueAt(tb_atendimentos.getSelectedRow(), 0).toString())
                    ));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao Selecionar Atendimento");
                }
            }
        }
    }

    public void alterar() {
        if (tb_atendimentos.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        } else {
            if (tb_atendimentos.getSelectedRowCount() > 1) {
                JOptionPane.showMessageDialog(null, "Selecione Apenas uma linha!");
            } else {
                try {
                    atendimentoDAO = new AtendimentoDAO();
                    Frm_Atendimento_Encerramento f = new Frm_Atendimento_Encerramento(btn_alterar, atendimentoDAO.getByCodigo(
                            Integer.parseInt(tb_atendimentos.getValueAt(tb_atendimentos.getSelectedRow(), 0).toString())
                    ));
                    dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao Selecionar Atendimento");
                }
            }
        }
    }

    public void apagar(int codigo) {
        try {
            atendimentoDAO = new AtendimentoDAO();
            atendimentoDAO.remove(atendimentoDAO.getByCodigo(codigo));
            JOptionPane.showMessageDialog(null, "Atendimento removido com sucesso!");
            model = (DefaultTableModel) tb_atendimentos.getModel();
            model.removeRow(tb_atendimentos.getSelectedRow());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar o atendimento: " + codigo);
        }
    }

    public void filtrar(java.awt.event.KeyEvent evt) {
        TableRowSorter sorter = new TableRowSorter(tb_atendimentos.getModel());
        tb_atendimentos.setRowSorter(sorter);
        String texto = txt_filtro.getText();
        try {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Valor Não Encontrado!!!", "AVISO - Erro", JOptionPane.ERROR_MESSAGE);
        }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_atendimentos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_filtro = new javax.swing.JTextField();
        btn_apagar = new javax.swing.JButton();
        btn_finalizar = new javax.swing.JButton();
        btn_alterar = new javax.swing.JButton();
        btn_consultar = new javax.swing.JButton();
        btn_sair = new javax.swing.JButton();
        btn_executar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_atendimentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data Agendamento", "Cliente", "Tipo", "Prioridade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_atendimentos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_atendimentos);
        if (tb_atendimentos.getColumnModel().getColumnCount() > 0) {
            tb_atendimentos.getColumnModel().getColumn(0).setMinWidth(70);
            tb_atendimentos.getColumnModel().getColumn(0).setPreferredWidth(70);
            tb_atendimentos.getColumnModel().getColumn(0).setMaxWidth(70);
            tb_atendimentos.getColumnModel().getColumn(1).setMinWidth(120);
            tb_atendimentos.getColumnModel().getColumn(1).setPreferredWidth(120);
            tb_atendimentos.getColumnModel().getColumn(1).setMaxWidth(120);
            tb_atendimentos.getColumnModel().getColumn(3).setMinWidth(120);
            tb_atendimentos.getColumnModel().getColumn(3).setPreferredWidth(120);
            tb_atendimentos.getColumnModel().getColumn(3).setMaxWidth(120);
            tb_atendimentos.getColumnModel().getColumn(4).setMinWidth(85);
            tb_atendimentos.getColumnModel().getColumn(4).setPreferredWidth(85);
            tb_atendimentos.getColumnModel().getColumn(4).setMaxWidth(85);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );

        jLabel1.setText("Filtro:");

        txt_filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtroKeyReleased(evt);
            }
        });

        btn_apagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/excluir.png"))); // NOI18N
        btn_apagar.setText("Apagar");
        btn_apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_apagarActionPerformed(evt);
            }
        });

        btn_finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/finalizar.png"))); // NOI18N
        btn_finalizar.setText("Finalizar");
        btn_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_finalizarActionPerformed(evt);
            }
        });

        btn_alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/alterar.png"))); // NOI18N
        btn_alterar.setText("Alterar");
        btn_alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alterarActionPerformed(evt);
            }
        });

        btn_consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/detalhe.png"))); // NOI18N
        btn_consultar.setText("Detalhar");
        btn_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultarActionPerformed(evt);
            }
        });

        btn_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_sair.setText("Fechar");
        btn_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sairActionPerformed(evt);
            }
        });

        btn_executar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/executar.png"))); // NOI18N
        btn_executar.setText("Executar");
        btn_executar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_executarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(btn_executar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_apagar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_finalizar)
                    .addComponent(btn_apagar)
                    .addComponent(btn_alterar)
                    .addComponent(btn_consultar)
                    .addComponent(btn_sair)
                    .addComponent(btn_executar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btn_finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_finalizarActionPerformed
        finalizar();
        dispose();
    }//GEN-LAST:event_btn_finalizarActionPerformed

    private void btn_apagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apagarActionPerformed
        if (tb_atendimentos.getSelectedRowCount() == 1) {
            if (JOptionPane.showConfirmDialog(null, "Deseja relamente excluir o atendimento: " + tb_atendimentos.getValueAt(tb_atendimentos.getSelectedRow(), 0).toString(), "", 0, JOptionPane.YES_NO_OPTION) == 0) {
                apagar(Integer.parseInt(tb_atendimentos.getValueAt(tb_atendimentos.getSelectedRow(), 0).toString()));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um Atendimento para APAGAR");
        }
    }//GEN-LAST:event_btn_apagarActionPerformed

    private void btn_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultarActionPerformed
        if (tb_atendimentos.getSelectedRowCount() == 1) {
            try {
                atendimentoDAO = new AtendimentoDAO();
                Frm_Atendimento_Detalhe f = new Frm_Atendimento_Detalhe(atendimentoDAO.getByCodigo(
                        Integer.parseInt(tb_atendimentos.getValueAt(tb_atendimentos.getSelectedRow(), 0).toString()
                        )));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao Detalhar Atendimento");
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma atendimento para DETALHAR");
        }
    }//GEN-LAST:event_btn_consultarActionPerformed

    private void btn_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alterarActionPerformed
        alterar();
    }//GEN-LAST:event_btn_alterarActionPerformed

    private void btn_executarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_executarActionPerformed
        if (tb_atendimentos.getSelectedRowCount() != 1) {
            JOptionPane.showMessageDialog(null, "Selecione apenas um atendimento de cada vêz!");
        } else {
            executar(Integer.parseInt(tb_atendimentos.getValueAt(tb_atendimentos.getSelectedRow(), 0).toString()));
        }
    }//GEN-LAST:event_btn_executarActionPerformed

    private void txt_filtroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyReleased
        filtrar(evt);
    }//GEN-LAST:event_txt_filtroKeyReleased

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
            java.util.logging.Logger.getLogger(Frm_ConAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_ConAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_ConAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_ConAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Frm_ConAtendimento().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_alterar;
    private javax.swing.JButton btn_apagar;
    private javax.swing.JButton btn_consultar;
    private javax.swing.JButton btn_executar;
    private javax.swing.JButton btn_finalizar;
    private javax.swing.JButton btn_sair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_atendimentos;
    private javax.swing.JTextField txt_filtro;
    // End of variables declaration//GEN-END:variables

    private void executar(int codigo) {
        try {
            atendimentoDAO = new AtendimentoDAO();
            statusAtendimentoDAO = new StatusAtendimentoDAO();
            atendimento = new Atendimento();
            atendimento = atendimentoDAO.getByCodigo(codigo);
            atendimento.setCodstatusatendimento(statusAtendimentoDAO.buscaStatusAtendimento("EXECUCAO"));
            atendimentoDAO.salvar(atendimento);
            model.removeRow(tb_atendimentos.getSelectedRow());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Executar atendimento!");
            System.out.println(e);
        }
    }
}
