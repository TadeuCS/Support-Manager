package View.Cadastros;

import Controller.SegmentoDAO;
import Model.Segmento;
import Util.Classes.FixedLengthDocument;
import Util.Classes.TableConfig;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public final class Frm_CadSegmento extends javax.swing.JFrame {

    SegmentoDAO segmentoDAO;
    Segmento segmento;

    public Frm_CadSegmento() {
        initComponents();
        setVisible(true);
        txt_descricao.setDocument(new FixedLengthDocument(20));
        listar();
    }

    public void novo() {
        segmentoDAO = new SegmentoDAO();
        segmento = new Segmento();
    }

    public void salvar(String descricao) {
        novo();
        segmento.setDescricao(descricao);
        try {
            segmentoDAO.buscaSegmento(descricao);
            JOptionPane.showMessageDialog(null, "Segmento já existe\n", "Alerta", JOptionPane.ERROR_MESSAGE);
            txt_descricao.requestFocus();
        } catch (NoResultException ex) {
            try {
                segmentoDAO.salvar(segmento);
                JOptionPane.showMessageDialog(null, "Segmento salvo com sucesso!");
                txt_descricao.setText(null);
                txt_descricao.requestFocus();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao Salvar Segmento\n", "Alerta", JOptionPane.ERROR_MESSAGE);
                txt_descricao.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar Segmento " + descricao);
            txt_descricao.setText(null);
            txt_descricao.requestFocus();
        } finally {
            listar();
        }
    }

    public void listar() {
        try {
            TableConfig.limpaTabela(tb_segmentos);
            segmentoDAO = new SegmentoDAO();
            for (int i = 0; i < segmentoDAO.lista().size(); i++) {
                String[] linha = new String[]{
                    segmentoDAO.lista().get(i).getCodsegmento().toString(),
                    segmentoDAO.lista().get(i).getDescricao()};
                TableConfig.getModel(tb_segmentos).addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar Segmentos!");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        pnl_dados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_descricao = new javax.swing.JTextField();
        btn_salvar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        pnl_tabela = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_segmentos = new javax.swing.JTable();
        txt_filtro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btn_alterar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Segmento");
        setResizable(false);

        pnl_dados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Descrição *:");

        javax.swing.GroupLayout pnl_dadosLayout = new javax.swing.GroupLayout(pnl_dados);
        pnl_dados.setLayout(pnl_dadosLayout);
        pnl_dadosLayout.setHorizontalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_descricao)
                .addContainerGap())
        );
        pnl_dadosLayout.setVerticalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_descricao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/salvar.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        tb_segmentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Segmento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_segmentos);
        if (tb_segmentos.getColumnModel().getColumnCount() > 0) {
            tb_segmentos.getColumnModel().getColumn(0).setMinWidth(80);
            tb_segmentos.getColumnModel().getColumn(0).setPreferredWidth(80);
            tb_segmentos.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        txt_filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtroKeyReleased(evt);
            }
        });

        jLabel2.setText("Filtro:");

        javax.swing.GroupLayout pnl_tabelaLayout = new javax.swing.GroupLayout(pnl_tabela);
        pnl_tabela.setLayout(pnl_tabelaLayout);
        pnl_tabelaLayout.setHorizontalGroup(
            pnl_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnl_tabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_filtro)
                .addContainerGap())
        );
        pnl_tabelaLayout.setVerticalGroup(
            pnl_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_tabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btn_alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/alterar.png"))); // NOI18N
        btn_alterar.setText("Alterar");
        btn_alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alterarActionPerformed(evt);
            }
        });

        btn_fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_fechar.setText("Fechar");
        btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_tabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_cancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_tabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_alterar)
                    .addComponent(btn_fechar))
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

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        if (txt_descricao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Parcela inválida!");
            txt_descricao.requestFocus();
        } else {
            if (btn_alterar.isEnabled() == true) {
                salvar(txt_descricao.getText());
            } else {
                try {
                    segmentoDAO.buscaSegmento(txt_descricao.getText());
                    JOptionPane.showMessageDialog(null, "Segmento ja cadastrado!");
                    txt_descricao.requestFocus();
                } catch (NoResultException e) {
                    alterar();
                }
            }
        }
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        btn_alterar.setEnabled(true);
        txt_descricao.setText(null);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alterarActionPerformed
        if (tb_segmentos.getSelectedRowCount() != 1) {
            JOptionPane.showMessageDialog(null, "Selecione apenas uma linha para alterar");
        } else {
            getSegmento(tb_segmentos.getValueAt(tb_segmentos.getSelectedRow(), 1).toString());
            btn_alterar.setEnabled(false);
        }
    }//GEN-LAST:event_btn_alterarActionPerformed

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        dispose();
    }//GEN-LAST:event_btn_fecharActionPerformed

    private void txt_filtroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyReleased
        TableConfig.filtrar(evt, tb_segmentos, txt_filtro);
    }//GEN-LAST:event_txt_filtroKeyReleased

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
            java.util.logging.Logger.getLogger(Frm_CadSegmento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CadSegmento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CadSegmento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CadSegmento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_CadSegmento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_alterar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnl_dados;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JPanel pnl_tabela;
    private javax.swing.JTable tb_segmentos;
    private javax.swing.JTextField txt_descricao;
    private javax.swing.JTextField txt_filtro;
    // End of variables declaration//GEN-END:variables

    private void getSegmento(String descricao) {
        try {
            segmento = segmentoDAO.buscaSegmento(descricao);
            txt_descricao.setText(segmento.getDescricao());
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o Segmento: " + descricao);
        }
    }

    private void alterar() {
        try {
            setSegmento(segmento);
            segmentoDAO.salvar(segmento);
            JOptionPane.showMessageDialog(null, "Segmento alterado com sucesso!");
            txt_descricao.setText(null);
            btn_alterar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar Segmento: " + segmento.getDescricao());
            txt_descricao.requestFocus();
        } finally {
            listar();
        }
    }

    private void setSegmento(Segmento segmento) {
        try {
            segmento.setDescricao(txt_descricao.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao setar o Segmento: " + txt_descricao.getText());
        }

    }
}
