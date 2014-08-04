/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import Controller.ContatoDAO;
import Controller.GrupoDAO;
import Model.Contato;
import Model.Grupo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tadeu
 */
public class Frm_CadContato extends javax.swing.JFrame {

    private Grupo grupo;
    private Contato contato;
    ContatoDAO contatoDAO;
    GrupoDAO grupoDAO;
    DefaultTableModel model;

    public Frm_CadContato() {
        initComponents();
    }

    public void carregaTipos() {
        int i = 0;
        grupoDAO = new GrupoDAO();
        cbx_grupo.removeAllItems();
        try {
            while (i < contatoDAO.lista().size()) {
                String linha = grupoDAO.lista().get(i).getDescricao();
                cbx_grupo.addItem(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Grupos");
        }

    }

    public void validaCampos(String contato, String telefone, String grupo) {
        if (contato.equals("") == true) {
            JOptionPane.showMessageDialog(null, "Contato em branco");
            txt_contato.requestFocus();
        } else {
            if (telefone.equals("(  )     -    ") == true) {
                JOptionPane.showMessageDialog(null, "Telefone em branco");
                txt_telefone.requestFocus();
            } else {
                salvar(grupo, contato, telefone);
            }
        }
    }

    public void novo() {
        contato = new Contato();
        grupo = new Grupo();
        contatoDAO = new ContatoDAO();
        grupoDAO = new GrupoDAO();
    }

    public void salvar(String grupo, String nome, String telefone) {
        try {
            novo();
            contato.setNome(txt_contato.getText());
            contato.setTelefone(txt_telefone.getText());
            contato.setCodgrupo(grupoDAO.consulta(grupo));
            contatoDAO.salvar(contato);
            JOptionPane.showMessageDialog(null, "Contato Inserido com Sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o Contato" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityManager1 = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("Support_ManagerPU").createEntityManager();
        pnl_fundo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbx_grupo = new javax.swing.JComboBox();
        txt_dados = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_contato = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_telefone = new javax.swing.JFormattedTextField();
        btn_salvar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Grupo");
        setResizable(false);

        jLabel1.setText("Grupo:");

        txt_dados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("Telefone:");

        jLabel2.setText("Contato:");

        try {
            txt_telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_telefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout txt_dadosLayout = new javax.swing.GroupLayout(txt_dados);
        txt_dados.setLayout(txt_dadosLayout);
        txt_dadosLayout.setHorizontalGroup(
            txt_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txt_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(txt_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_contato)
                    .addComponent(txt_telefone))
                .addContainerGap())
        );
        txt_dadosLayout.setVerticalGroup(
            txt_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_dadosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(txt_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txt_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

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
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_fundoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_grupo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txt_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbx_grupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_fechar))
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
            .addComponent(pnl_fundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        validaCampos(txt_contato.getText().toUpperCase(), txt_telefone.getText(), cbx_grupo.getSelectedItem().toString());
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        dispose();
    }//GEN-LAST:event_btn_fecharActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_CadContato.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CadContato.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CadContato.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CadContato.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_CadContato().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_grupo;
    private javax.persistence.EntityManager entityManager1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JTextField txt_contato;
    private javax.swing.JPanel txt_dados;
    private javax.swing.JFormattedTextField txt_telefone;
    // End of variables declaration//GEN-END:variables
}
