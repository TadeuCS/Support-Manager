/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Home;

import Controller.EmpresaDAO;
import Model.Empresa;
import Util.Classes.ValidaEmail;
import Util.Email.ConfigEmail;
import Util.Email.EnviaEmail;
import View.Cadastros.Frm_CadEmpresa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void limpaCampos() {
        txt_email.setText(null);
        txt_assunto.setText(null);
        txt_menssagem.setText(null);
    }

    public void validaCampos(String nomeEmpresa, String emailDestinatario, String assunto, String messagem) {
        if (nomeEmpresa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma Empresa Remetente");
            cbx_empresas.requestFocus();
        } else {
            if (ValidaEmail.validarEmail(emailDestinatario) == false) {
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
                        try {
                            empresaDAO = new EmpresaDAO();
                            empresa = new Empresa();
                            empresa = empresaDAO.findByNomeFantasia(nomeEmpresa);
                            List<String> destinatarios = new ArrayList<>();
                            destinatarios.add(emailDestinatario);
                            enviaEmail(empresa.getCodemail().getSmtp(),
                                    empresa.getCodemail().getPorta(),
                                    empresa.getCodemail().getNome(),
                                    empresa.getCodemail().getSenha(),
                                    destinatarios, assunto, messagem, null);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao Testar envio de Email");
                        }

                    }
                }
            }
        }
    }

    public void enviaEmail(String smtp, int porta, String usuario, String senha, List<String> destinatarios, String assunto, String mensagem, List<String> anexos) {
        ConfigEmail mj = new ConfigEmail();
        //configuracoes de envio
        mj.setSmtpHostMail(smtp);
        mj.setSmtpPortMail(porta + "");
        mj.setSmtpAuth("true");
        mj.setSmtpStarttls("true");
        mj.setUserMail(usuario);
        mj.setFromNameMail("Olivet Sistemas");
        mj.setPassMail(senha);
        mj.setCharsetMail("ISO-8859-1");
        mj.setSubjectMail(assunto);
        mj.setBodyMail(mensagem);//aqui vc passa o texto do email, ou o texto em html usando o metodo "htmlMessage()".
        mj.setTypeTextMail(ConfigEmail.TYPE_TEXT_HTML);

        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < destinatarios.size(); i++) {
            map.put(destinatarios.get(i), "");
        }
        mj.setToMailsUsers(map);
        
        //seta quantos anexos for nescessário
        if (anexos != null) {
            mj.setFileMails(anexos);
        }
        Thread acao = new Thread(new Runnable() {
            @Override
            public void run() {
                lb_status.setText("Enviando Email...");
                try {
                    EnviaEmail sender = new EnviaEmail();
                    sender.senderMail(mj);
                    lb_status.setText("Email enviado com sucesso!");
                    limpaCampos();
                    Thread.sleep(1000);
                    lb_status.setText(null);
                } catch (Exception e) {
                    lb_status.setText("Erro ao enviar Email.");
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    lb_status.setText(null);
                }
            }
        });
        acao.start();
        
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
        btn_cadEmpresa = new javax.swing.JButton();
        btn_testar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();
        lb_status = new javax.swing.JLabel();

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
                        .addGap(0, 218, Short.MAX_VALUE)))
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

        btn_cadEmpresa.setText("...");
        btn_cadEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadEmpresaActionPerformed(evt);
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
                .addComponent(cbx_empresas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_cadEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_remetenteLayout.setVerticalGroup(
            pnl_remetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_remetenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_remetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbx_empresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cadEmpresa))
                .addContainerGap(13, Short.MAX_VALUE))
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

        lb_status.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lb_status.setForeground(new java.awt.Color(153, 0, 0));
        lb_status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_fundoLayout.createSequentialGroup()
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(lb_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_fundoLayout.createSequentialGroup()
                        .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_testar)
                            .addComponent(btn_fechar))
                        .addGap(0, 0, Short.MAX_VALUE)))
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

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        dispose();
    }//GEN-LAST:event_btn_fecharActionPerformed

    private void cbx_empresasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_empresasFocusGained
        carregaEmpresas();
    }//GEN-LAST:event_cbx_empresasFocusGained

    private void btn_testarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_testarActionPerformed
        validaCampos(cbx_empresas.getSelectedItem().toString(), txt_email.getText(), txt_assunto.getText(), txt_menssagem.getText());
    }//GEN-LAST:event_btn_testarActionPerformed

    private void btn_cadEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadEmpresaActionPerformed
        Frm_CadEmpresa f = new Frm_CadEmpresa();
    }//GEN-LAST:event_btn_cadEmpresaActionPerformed

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
    private javax.swing.JButton btn_cadEmpresa;
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_testar;
    private javax.swing.JComboBox cbx_empresas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_status;
    private javax.swing.JPanel pnl_destinatario;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JPanel pnl_remetente;
    private javax.swing.JTextField txt_assunto;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextArea txt_menssagem;
    // End of variables declaration//GEN-END:variables
}
