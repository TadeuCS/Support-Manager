package View.Relatorios;

import Controller.ClienteDAO;
import Controller.StatusAtendimentoDAO;
import Controller.StatusPessoaDAO;
import Controller.UsuarioDAO;
import Model.StatusAtendimento;
import Model.StatusPessoa;
import Util.Classes.AutoComplete;
import Util.Classes.Data;
import Util.Classes.GeraRelatorios;
import java.awt.Event;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;

public class Frm_RelInformacao extends javax.swing.JFrame {

    Data data;
    ClienteDAO clienteDAO;
    UsuarioDAO usuarioDAO;
    StatusPessoaDAO statusPessoaDAO;
    StatusPessoa statusPessoa;
    StatusAtendimentoDAO statusAtendimentoDAO;

    public Frm_RelInformacao() {
        initComponents();
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        btn_gerar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();
        pnl_dadosRelSintetico = new javax.swing.JPanel();
        txt_dataInicio = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_dataFim = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório de Informações lançadas por período");
        setResizable(false);

        btn_gerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/carregar.png"))); // NOI18N
        btn_gerar.setText("Gerar");
        btn_gerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gerarActionPerformed(evt);
            }
        });

        btn_fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Util/Img/fechar.png"))); // NOI18N
        btn_fechar.setText("Fechar");
        btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fecharActionPerformed(evt);
            }
        });

        pnl_dadosRelSintetico.setBorder(javax.swing.BorderFactory.createTitledBorder("Data de Lançamento"));

        try {
            txt_dataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataInicioKeyPressed(evt);
            }
        });

        jLabel3.setText("Data Início *:");

        try {
            txt_dataFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_dataFim.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dataFim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataFimKeyPressed(evt);
            }
        });

        jLabel4.setText("Data Fim *:");

        javax.swing.GroupLayout pnl_dadosRelSinteticoLayout = new javax.swing.GroupLayout(pnl_dadosRelSintetico);
        pnl_dadosRelSintetico.setLayout(pnl_dadosRelSinteticoLayout);
        pnl_dadosRelSinteticoLayout.setHorizontalGroup(
            pnl_dadosRelSinteticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosRelSinteticoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_dadosRelSinteticoLayout.setVerticalGroup(
            pnl_dadosRelSinteticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosRelSinteticoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosRelSinteticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosRelSinteticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txt_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosRelSinteticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txt_dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_gerar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnl_dadosRelSintetico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosRelSintetico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_gerar)
                    .addComponent(btn_fechar))
                .addGap(6, 6, 6))
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

    private void txt_dataInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataInicioKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            data = new Data();
            txt_dataInicio.setText(data.completaData(txt_dataInicio.getText(), "dd/MM/yyyy"));
        }
    }//GEN-LAST:event_txt_dataInicioKeyPressed

    private void txt_dataFimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataFimKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            data = new Data();
            txt_dataFim.setText(data.completaData(txt_dataFim.getText(), "dd/MM/yyyy"));
        }
    }//GEN-LAST:event_txt_dataFimKeyPressed

    private void btn_gerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerarActionPerformed
        validaTipo(this.getTitle());
    }//GEN-LAST:event_btn_gerarActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_RelInformacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_RelInformacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_RelInformacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_RelInformacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Frm_RelInformacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_gerar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel pnl_dadosRelSintetico;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JFormattedTextField txt_dataFim;
    private javax.swing.JFormattedTextField txt_dataInicio;
    // End of variables declaration//GEN-END:variables

    private void validaTipo(String title) {
        if (validaDatas() == true) {
            geraRelatorioByDatas(txt_dataInicio.getText(), txt_dataFim.getText());
        }
    }

    private boolean validaDatas() {
        if (txt_dataInicio.getText().equals("  /  /    ") == true) {
            JOptionPane.showMessageDialog(null, "Data Inicial inválida!");
            txt_dataInicio.requestFocus();
            return false;
        } else {
            if (txt_dataFim.getText().equals("  /  /    ") == true) {
                JOptionPane.showMessageDialog(null, "Data Final inválida!");
                txt_dataFim.requestFocus();
                return false;
            } else {
                return true;
            }
        }
    }

    private void geraRelatorioByDatas(String inicio, String fim) {
        try {
            Map parameters = new HashMap();
            GeraRelatorios geraRelatorios = new GeraRelatorios();
            parameters.put("DataInicial", Data.getDataByTexto(inicio, "dd/MM/yyyy"));
            parameters.put("DataFinal", Data.getDataByTexto(fim, "dd/MM/yyyy"));
            geraRelatorios.imprimirRelatorioSQLNoRelatorio(parameters, "src/Relatorios/Rel_Informacao.jasper");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma informação encontrada neste período!");
        }
    }
}
