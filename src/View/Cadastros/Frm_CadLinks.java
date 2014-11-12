/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import Controller.AplicativoDAO;
import Controller.LinksDAO;
import Model.Aplicativo;
import Model.Link;
import Util.Classes.FixedLengthDocument;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Tadeu
 */
public final class Frm_CadLinks extends javax.swing.JFrame {

    AplicativoDAO aplicativoDAO;
    Aplicativo aplicativo;
    LinksDAO linksDAO;
    Link link;
    DefaultTableModel model;

    public Frm_CadLinks() {
        initComponents();
        setVisible(true);
        model = (DefaultTableModel) tb_links.getModel();
        txt_descricao.setDocument(new FixedLengthDocument(20));
        carregaAplicativos();
        listarLinks();
    }

    public void novo() {
        aplicativoDAO = new AplicativoDAO();
        aplicativo = new Aplicativo();
        link = new Link();
        linksDAO = new LinksDAO();
    }

    public void carregaAplicativos() {
        aplicativoDAO = new AplicativoDAO();
        int i = 0;
        cbx_aplicativos.removeAllItems();
        try {
            while (i < aplicativoDAO.lista().size()) {
                String linha = aplicativoDAO.lista().get(i).getDescricao();
                cbx_aplicativos.addItem(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Aplicativos");
        }

    }

    public void salvar(String descricao) {
        try {
            linksDAO.buscaLink(descricao);
            JOptionPane.showMessageDialog(null, "Link já existe\n", "Alerta", JOptionPane.ERROR_MESSAGE);
            txt_descricao.requestFocus();
        } catch (NoResultException ex) {
            try {
                link = new Link();
                setLink(this.link);
                linksDAO.salvar(link);
                JOptionPane.showMessageDialog(null, "Link salvo com sucesso!");
                txt_descricao.setText(null);
                txt_descricao.requestFocus();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao Salvar Link\n", "Alerta", JOptionPane.ERROR_MESSAGE);
                txt_descricao.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar Link " + descricao);
            txt_descricao.setText(null);
            txt_descricao.requestFocus();
        } finally {
            listarLinks();
        }
    }

    public void limpaTabela(DefaultTableModel model) {
        try {
            while (0 < model.getRowCount()) {
                model.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void listarLinks() {
        linksDAO = new LinksDAO();
        try {
            limpaTabela(model);
            for (int i = 0; i < linksDAO.lista().size(); i++) {
                String[] linha = new String[]{linksDAO.lista().get(i).getCodlink().toString(),
                    linksDAO.lista().get(i).getDescricao(),
                    linksDAO.lista().get(i).getCodaplicativo().getDescricao()};
                model.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar os Links cadastrados!");
        }
    }

    public void filtrar(java.awt.event.KeyEvent evt) {
        TableRowSorter sorter = new TableRowSorter(tb_links.getModel());
        tb_links.setRowSorter(sorter);
        String texto = txt_filtro.getText();
        try {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Valor Não Encontrado!!!", "AVISO - Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        pnl_dados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_descricao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbx_aplicativos = new javax.swing.JComboBox();
        btn_salvar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_links = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt_filtro = new javax.swing.JTextField();
        btn_alterar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Links");
        setResizable(false);

        pnl_dados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Descrição *:");

        jLabel2.setText("Aplicativo *:");

        javax.swing.GroupLayout pnl_dadosLayout = new javax.swing.GroupLayout(pnl_dados);
        pnl_dados.setLayout(pnl_dadosLayout);
        pnl_dadosLayout.setHorizontalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_descricao))
                    .addGroup(pnl_dadosLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_aplicativos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_dadosLayout.setVerticalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbx_aplicativos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_descricao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_links.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Link", "Aplicativo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_links.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_links);
        if (tb_links.getColumnModel().getColumnCount() > 0) {
            tb_links.getColumnModel().getColumn(0).setMinWidth(80);
            tb_links.getColumnModel().getColumn(0).setPreferredWidth(80);
            tb_links.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        jLabel3.setText("Filtro:");

        txt_filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtroKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_filtro, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btn_alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/alterar.png"))); // NOI18N
        btn_alterar.setText("Alterar");
        btn_alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alterarActionPerformed(evt);
            }
        });

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        if(txt_descricao.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Link inválido!");
            txt_descricao.requestFocus();
        }else{
            if (btn_alterar.isEnabled() == true) {
                salvar(txt_descricao.getText());
            } else {
                alterar(txt_descricao.getText());
            }
        }
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        dispose();
    }//GEN-LAST:event_btn_fecharActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        btn_alterar.setEnabled(true);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void txt_filtroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyReleased
        filtrar(evt);
    }//GEN-LAST:event_txt_filtroKeyReleased

    private void btn_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alterarActionPerformed
        if (tb_links.getSelectedRowCount() != 1) {
            JOptionPane.showMessageDialog(null, "Selecione apenas uma linha para alterar");
        } else {
            getLink(tb_links.getValueAt(tb_links.getSelectedRow(), 1).toString());
            btn_alterar.setEnabled(false);
        }
    }//GEN-LAST:event_btn_alterarActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_CadLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CadLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CadLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CadLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_CadLinks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_alterar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_aplicativos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnl_dados;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JTable tb_links;
    private javax.swing.JTextField txt_descricao;
    private javax.swing.JTextField txt_filtro;
    // End of variables declaration//GEN-END:variables

    private Link setLink(Link link) {
        link.setCodaplicativo(aplicativoDAO.buscaAplicativo(cbx_aplicativos.getSelectedItem().toString()));
        link.setDescricao(txt_descricao.getText());
        return link;
    }

    private void getLink(String link) {
        try {
            this.link = linksDAO.buscaLink(link);
            cbx_aplicativos.setSelectedItem(this.link.getCodaplicativo().getDescricao());
            txt_descricao.setText(this.link.getDescricao());
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o Link: " + link);
        }
    }

    private void alterar(String text) {
        try {
            setLink(this.link);
            linksDAO.salvar(link);
            JOptionPane.showMessageDialog(null, "Link alterado com sucesso!");
            listarLinks();
            txt_descricao.setText(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar Link: "+link.getDescricao());
        }
    }
}
