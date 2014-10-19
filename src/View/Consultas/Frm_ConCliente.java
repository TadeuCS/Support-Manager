/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Consultas;

import Controller.AplicativoDAO;
import Controller.CidadesDAO;
import Controller.ClienteDAO;
import Controller.EstadosDAO;
import Controller.GrupoDAO;
import Controller.LinksDAO;
import Controller.ParcelaDAO;
import Controller.SalarioDAO;
import Controller.SegmentoDAO;
import Controller.StatusPessoaDAO;
import Controller.TelefoneDAO;
import Controller.TipoPessoaDAO;
import Model.Telefone;
import View.Cadastros.Frm_CadCliente;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Tadeu
 */
public class Frm_ConCliente extends javax.swing.JFrame {

    DefaultTableModel model;
    ClienteDAO clienteDAO;
    EstadosDAO estadosDAO;
    TelefoneDAO telefoneDAO;
    SegmentoDAO segmentoDAO;
    StatusPessoaDAO statusPessoaDAO;
    CidadesDAO cidadesDAO;
    ParcelaDAO parcelaDAO;
    LinksDAO linksDAO;
    GrupoDAO grupoDAO;
    SalarioDAO salarioDAO;
    TipoPessoaDAO tipoPessoaDAO;
    AplicativoDAO aplicativoDAO;

    public Frm_ConCliente() {
        initComponents();
        model = (DefaultTableModel) tb_clientes.getModel();
        setVisible(true);
        listaClientes();
    }

    public void carregaContatos(List<Telefone> telefones) {
        DefaultTableModel model = (DefaultTableModel) tb_contatos.getModel();
        limpaTabela(model);
        for (int i = 0; i < telefones.size(); i++) {
            String[] linha = new String[]{telefones.get(i).getDescricao(), telefones.get(i).getTelefone()};
            model.addRow(linha);
        }
    }

    public void listaClientes() {
        clienteDAO = new ClienteDAO();
        try {
            limpaTabela((DefaultTableModel) tb_clientes.getModel());
            
            for (int i = 0; i < clienteDAO.lista().size(); i++) {
                String[] linha = new String[]{
                    clienteDAO.lista().get(i).getCodcliente().toString(),
                    clienteDAO.lista().get(i).getReferencia().toString(),
                    clienteDAO.lista().get(i).getNomeFantasia(),
                    clienteDAO.lista().get(i).getCnpjCpf()};
                model.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar Clientes");
            System.out.println(e);
        }
    }

    public void selecionarCliente() {
        if (tb_clientes.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        } else {
            if (tb_clientes.getSelectedRowCount() > 1) {
                JOptionPane.showMessageDialog(null, "Selecione Apenas uma linha!");
            } else {
                Frm_CadCliente f = new Frm_CadCliente();
                f.setCodigoCliente(Integer.parseInt(tb_clientes.getValueAt(tb_clientes.getSelectedRow(), 0).toString()));
                f.buscar();
                dispose();
            }
        }
    }

    public void filtrar(java.awt.event.KeyEvent evt) {
        TableRowSorter sorter = new TableRowSorter(tb_clientes.getModel());
        tb_clientes.setRowSorter(sorter);
        String texto = txt_filtro.getText();
        try {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Valor Não Encontrado!!!", "AVISO - Erro", JOptionPane.ERROR_MESSAGE);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_clientes = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_contatos = new javax.swing.JTable();
        btn_voltar = new javax.swing.JButton();
        btn_selecionar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_filtro = new javax.swing.JTextField();
        btn_fechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta Clientes");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Referência", "Nome Fantasia", "CPF/CNPJ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_clientes.getTableHeader().setReorderingAllowed(false);
        tb_clientes.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                tb_clientesHierarchyChanged(evt);
            }
        });
        tb_clientes.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                tb_clientesAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tb_clientes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tb_clientesFocusGained(evt);
            }
        });
        tb_clientes.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
                tb_clientesAncestorMoved1(evt);
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
            }
        });
        tb_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_clientesMouseClicked(evt);
            }
        });
        tb_clientes.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                tb_clientesCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        tb_clientes.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tb_clientesPropertyChange(evt);
            }
        });
        tb_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_clientesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tb_clientesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tb_clientes);
        if (tb_clientes.getColumnModel().getColumnCount() > 0) {
            tb_clientes.getColumnModel().getColumn(0).setMinWidth(70);
            tb_clientes.getColumnModel().getColumn(0).setPreferredWidth(70);
            tb_clientes.getColumnModel().getColumn(0).setMaxWidth(70);
            tb_clientes.getColumnModel().getColumn(1).setMinWidth(70);
            tb_clientes.getColumnModel().getColumn(1).setPreferredWidth(70);
            tb_clientes.getColumnModel().getColumn(1).setMaxWidth(70);
            tb_clientes.getColumnModel().getColumn(3).setMinWidth(200);
            tb_clientes.getColumnModel().getColumn(3).setPreferredWidth(200);
            tb_clientes.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        tb_contatos.setBackground(new java.awt.Color(204, 204, 204));
        tb_contatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Contato", "Telefone"
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
        jScrollPane2.setViewportView(tb_contatos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btn_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/voltar.png"))); // NOI18N
        btn_voltar.setText("Voltar");
        btn_voltar.setToolTipText("Volta para o Cadastro de Cliente");
        btn_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_voltarActionPerformed(evt);
            }
        });

        btn_selecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/selecionar.png"))); // NOI18N
        btn_selecionar.setText("Selecionar");
        btn_selecionar.setToolTipText("Carrega os dados do Cliente selecionado, no cadastro de Cliente");
        btn_selecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_selecionarActionPerformed(evt);
            }
        });

        jLabel1.setText("Filtro:");

        txt_filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtroKeyReleased(evt);
            }
        });

        btn_fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_fechar.setText("Fechar");
        btn_fechar.setToolTipText("Fecha a tela de Consulta de Clientes");
        btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_selecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_selecionar)
                    .addComponent(btn_voltar)
                    .addComponent(btn_fechar))
                .addContainerGap())
        );

        btn_voltar.getAccessibleContext().setAccessibleName("");
        btn_selecionar.getAccessibleContext().setAccessibleName("");
        btn_fechar.getAccessibleContext().setAccessibleName("");

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

    private void btn_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_voltarActionPerformed
        Frm_CadCliente F = new Frm_CadCliente();
        dispose();
    }//GEN-LAST:event_btn_voltarActionPerformed

    private void tb_clientesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tb_clientesFocusGained
    }//GEN-LAST:event_tb_clientesFocusGained

    private void tb_clientesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tb_clientesPropertyChange
    }//GEN-LAST:event_tb_clientesPropertyChange

    private void tb_clientesCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tb_clientesCaretPositionChanged
    }//GEN-LAST:event_tb_clientesCaretPositionChanged

    private void tb_clientesAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tb_clientesAncestorMoved
    }//GEN-LAST:event_tb_clientesAncestorMoved

    private void tb_clientesHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tb_clientesHierarchyChanged
    }//GEN-LAST:event_tb_clientesHierarchyChanged

    private void tb_clientesAncestorMoved1(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tb_clientesAncestorMoved1
    }//GEN-LAST:event_tb_clientesAncestorMoved1

    private void tb_clientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_clientesKeyPressed
    }//GEN-LAST:event_tb_clientesKeyPressed

    private void tb_clientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_clientesKeyReleased
        if (tb_clientes.getSelectedRowCount() == 1) {
            carregaContatos(clienteDAO.buscaClienteByCodigo(Integer.parseInt(tb_clientes.getValueAt(tb_clientes.getSelectedRow(), 0).toString())).getTelefoneList());
        }
    }//GEN-LAST:event_tb_clientesKeyReleased

    private void txt_filtroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyReleased
        filtrar(evt);
    }//GEN-LAST:event_txt_filtroKeyReleased

    private void tb_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_clientesMouseClicked
        if (tb_clientes.getSelectedRowCount() == 1) {
            carregaContatos(clienteDAO.buscaClienteByCodigo(Integer.parseInt(tb_clientes.getValueAt(tb_clientes.getSelectedRow(), 0).toString())).getTelefoneList());
        }
    }//GEN-LAST:event_tb_clientesMouseClicked

    private void btn_selecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_selecionarActionPerformed
        selecionarCliente();
    }//GEN-LAST:event_btn_selecionarActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_ConCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_ConCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_ConCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_ConCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_ConCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_selecionar;
    private javax.swing.JButton btn_voltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tb_clientes;
    private javax.swing.JTable tb_contatos;
    private javax.swing.JTextField txt_filtro;
    // End of variables declaration//GEN-END:variables
}
