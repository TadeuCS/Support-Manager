/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Home;

import Controller.EmpresaDAO;
import Model.Empresa;
import View.Cadastros.Frm_CadEmpresa;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu
 */
public class Frm_TestaEmail extends javax.swing.JFrame {

    EmpresaDAO empresaDAO;
    Empresa empresa;

    public Frm_TestaEmail() {
        initComponents();
        setVisible(true);
        carregaEmpresas();
    }

    public void carregaEmpresas() {
        try {
            cbx_empresas.removeAllItems();
            empresaDAO = new EmpresaDAO();
            for (int i = 0; i < empresaDAO.lista().size(); i++) {
                cbx_empresas.addItem(empresaDAO.lista().get(i).getNomeFantasia());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Empresas");
        }

    }

    public void validaCampos(String nomeEmpresa, String emailDestinatario, String assunto, String messagem) {
        if (nomeEmpresa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma Empresa Remetente");
            cbx_empresas.requestFocus();
        } else {
            if (emailDestinatario.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Email do destinatário inválido!");
                txt_email.requestFocus();
            } else {
                if (assunto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Assunto inválido!");
                    txt_assunto.requestFocus();
                } else {
                    if (messagem.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Menssagem inválida!");
                        txt_menssagem.requestFocus();
                    } else {
                        boolean ssl = false;
//                        sendEmail = new EnviaEmail();
                        try {
                            empresaDAO = new EmpresaDAO();
                            empresa = new Empresa();
                            empresa = empresaDAO.findByNomeFantasia(nomeEmpresa);

                            if (empresa.getCodemail().getSsl().equals("S") == true) {
                                ssl = true;
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao Carregar dados da Empresa");
                        }
//                        sendEmail.enviarEmail(empresa.getCodemail().getSmtp(),
//                                empresa.getCodemail().getPorta(),
//                                empresa.getCodemail().getEmail(),
//                                empresa.getCodemail().getSenha(),
//                                ssl,
//                                emailDestinatario,
//                                empresa.getCodemail().getEmail(),
//                                assunto, messagem);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        pnl_destinatario = new javax.swing.JPanel();
        txt_email = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_assunto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_menssagem = new javax.swing.JTextArea();
        pnl_remetente = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbx_empresas = new javax.swing.JComboBox();
        btn_fechar1 = new javax.swing.JButton();
        btn_testar = new javax.swing.JButton();
        msg = new javax.swing.JLabel();
        btn_fechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Teste de envio de Email");
        setResizable(false);

        pnl_destinatario.setBorder(javax.swing.BorderFactory.createTitledBorder("Destinatário"));

        jLabel2.setText("Email *:");

        jLabel3.setText("Assunto *:");

        jLabel4.setText("Menssagem *:");

        txt_menssagem.setColumns(20);
        txt_menssagem.setRows(5);
        jScrollPane2.setViewportView(txt_menssagem);

        javax.swing.GroupLayout pnl_destinatarioLayout = new javax.swing.GroupLayout(pnl_destinatario);
        pnl_destinatario.setLayout(pnl_destinatarioLayout);
        pnl_destinatarioLayout.setHorizontalGroup(
            pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_destinatarioLayout.createSequentialGroup()
                .addGroup(pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_destinatarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(pnl_destinatarioLayout.createSequentialGroup()
                        .addGroup(pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_destinatarioLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_destinatarioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_assunto, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_destinatarioLayout.setVerticalGroup(
            pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_destinatarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_assunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_destinatarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_destinatarioLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 60, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnl_remetente.setBorder(javax.swing.BorderFactory.createTitledBorder("Remetente"));

        jLabel1.setText("Empresa *:");

        cbx_empresas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_empresasFocusGained(evt);
            }
        });

        btn_fechar1.setText("...");
        btn_fechar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fechar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_remetenteLayout = new javax.swing.GroupLayout(pnl_remetente);
        pnl_remetente.setLayout(pnl_remetenteLayout);
        pnl_remetenteLayout.setHorizontalGroup(
            pnl_remetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_remetenteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbx_empresas, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_fechar1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_remetenteLayout.setVerticalGroup(
            pnl_remetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_remetenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_remetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbx_empresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_fechar1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_testar.setText("Testar Envio");
        btn_testar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_testarActionPerformed(evt);
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
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_testar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnl_destinatario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_remetente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_remetente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_destinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_testar)
                        .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(pnl_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        dispose();
    }//GEN-LAST:event_btn_fecharActionPerformed

    private void btn_fechar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fechar1ActionPerformed
        Frm_CadEmpresa f = new Frm_CadEmpresa();
    }//GEN-LAST:event_btn_fechar1ActionPerformed

    private void cbx_empresasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_empresasFocusGained
        carregaEmpresas();
    }//GEN-LAST:event_cbx_empresasFocusGained

    private void btn_testarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_testarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_testarActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_TestaEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_TestaEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_TestaEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_TestaEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_TestaEmail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_fechar1;
    private javax.swing.JButton btn_testar;
    private javax.swing.JComboBox cbx_empresas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel msg;
    private javax.swing.JPanel pnl_destinatario;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JPanel pnl_remetente;
    private javax.swing.JTextField txt_assunto;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextArea txt_menssagem;
    // End of variables declaration//GEN-END:variables
}
