/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Relatorios;

import Controller.ClienteDAO;
import Controller.StatusAtendimentoDAO;
import Controller.StatusPessoaDAO;
import Controller.TipoPessoaDAO;
import Controller.TipoUsuarioDAO;
import Controller.UsuarioDAO;
import Model.StatusAtendimento;
import Model.StatusPessoa;
import Model.TipoPessoa;
import Model.TipoUsuario;
import Util.Classes.AutoComplete;
import Util.Classes.Data;
import Util.Classes.GeraRelatorios;
import java.awt.Event;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu
 */
public class Frm_RelAtendimento extends javax.swing.JFrame {

    Data data;
    ClienteDAO clienteDAO;
    UsuarioDAO usuarioDAO;
    StatusPessoaDAO statusPessoaDAO;
    StatusPessoa statusPessoa;
    StatusAtendimentoDAO statusAtendimentoDAO;
    TipoUsuarioDAO tipoUsuarioDAO;
    TipoUsuario tipoUsuario;

    public Frm_RelAtendimento(String tipo) {
        initComponents();
        setVisible(true);
        if (tipo.equals("ANALITICO") == false) {
            pnl_dadosRelAnalitico.setVisible(false);
            setSize(508, 150);
            setTitle("Relatorio de Atendimento Sintético");
        } else {
            setTitle("Relatorio de Atendimento Analítico");
        }
        AutoComplete.decorate(cbx_cliente);
        carregaClientes();
        carregaStatus();
        carregaUsuarios();
    }

    private void carregaUsuarios() {
        try {
            usuarioDAO = new UsuarioDAO();
            tipoUsuarioDAO = new TipoUsuarioDAO();
            statusPessoaDAO = new StatusPessoaDAO();
            tipoUsuario = new TipoUsuario();
            statusPessoa = new StatusPessoa();
            cbx_usuario.removeAllItems();
            statusPessoa = statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO");
            tipoUsuario = tipoUsuarioDAO.buscaTipoUsuario("SUPORTE");
            for (int i = 0; i < usuarioDAO.listaUsuariosDesbloqueados(statusPessoa, tipoUsuario).size(); i++) {
                cbx_usuario.addItem(usuarioDAO.listaUsuariosDesbloqueados(statusPessoa, tipoUsuario).get(i).getUsuario());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao carregar usuários");
        }
    }

    private void carregaClientes() {
        clienteDAO = new ClienteDAO();
        try {
            cbx_cliente.removeAllItems();
            statusPessoaDAO = new StatusPessoaDAO();
            for (int i = 0; i < clienteDAO.listaByStatus(statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO")).size(); i++) {
                cbx_cliente.addItem(clienteDAO.listaByStatus(statusPessoaDAO.buscaStatusPessoa("DESBLOQUEADO")).get(i).getNomeFantasia());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao carregar Clientes");
        }
    }

    private void carregaStatus() {
        statusAtendimentoDAO = new StatusAtendimentoDAO();
        try {
            cbx_status.removeAllItems();
            for (int i = 0; i < statusAtendimentoDAO.lista().size(); i++) {
                cbx_status.addItem(statusAtendimentoDAO.lista().get(i).getDescricao());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro ao carregar Status");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        pnl_dadosRelAnalitico = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbx_usuario = new javax.swing.JComboBox();
        cbx_status = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbx_cliente = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        btn_gerar = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();
        pnl_dadosRelSintetico = new javax.swing.JPanel();
        txt_dataInicio = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_dataFim = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatorio de Atendimentos");
        setResizable(false);

        pnl_dadosRelAnalitico.setBorder(javax.swing.BorderFactory.createTitledBorder("Outros Filtros"));

        jLabel1.setText("Usuário *:");

        jLabel5.setText("Status *:");

        cbx_cliente.setBackground(new java.awt.Color(204, 204, 204));
        cbx_cliente.setEditable(true);

        jLabel6.setText("Cliente*:");

        javax.swing.GroupLayout pnl_dadosRelAnaliticoLayout = new javax.swing.GroupLayout(pnl_dadosRelAnalitico);
        pnl_dadosRelAnalitico.setLayout(pnl_dadosRelAnaliticoLayout);
        pnl_dadosRelAnaliticoLayout.setHorizontalGroup(
            pnl_dadosRelAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosRelAnaliticoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosRelAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosRelAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_status, 0, 405, Short.MAX_VALUE)
                    .addComponent(cbx_usuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbx_cliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_dadosRelAnaliticoLayout.setVerticalGroup(
            pnl_dadosRelAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosRelAnaliticoLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosRelAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbx_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosRelAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbx_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosRelAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbx_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        pnl_dadosRelSintetico.setBorder(javax.swing.BorderFactory.createTitledBorder("Data de Agendamento"));

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

        jLabel3.setText("Data Inicio *:");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
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
                    .addComponent(pnl_dadosRelAnalitico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_dadosRelAnalitico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        if ((evt.getKeyCode() == Event.ENTER) && (txt_dataInicio.getText().equals("  /  /       :  ") == true)) {
            data = new Data();
            Date dataAtual = new Date();
            txt_dataInicio.setText(data.getDataByDate(dataAtual, "dd/MM/yyyy HH:mm"));
        }
    }//GEN-LAST:event_txt_dataInicioKeyPressed

    private void txt_dataFimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataFimKeyPressed
        if ((evt.getKeyCode() == Event.ENTER) && (txt_dataFim.getText().equals("  /  /       :  ") == true)) {
            data = new Data();
            Date dataAtual = new Date();
            txt_dataFim.setText(data.getDataByDate(dataAtual, "dd/MM/yyyy HH:mm"));
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
            java.util.logging.Logger.getLogger(Frm_RelAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_RelAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_RelAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_RelAtendimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Frm_RelAtendimento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_gerar;
    private javax.swing.JComboBox cbx_cliente;
    private javax.swing.JComboBox cbx_status;
    private javax.swing.JComboBox cbx_usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel pnl_dadosRelAnalitico;
    private javax.swing.JPanel pnl_dadosRelSintetico;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JFormattedTextField txt_dataFim;
    private javax.swing.JFormattedTextField txt_dataInicio;
    // End of variables declaration//GEN-END:variables

    private void validaTipo(String title) {
        if (title.contains("Sintético") == true) {
            if (validaDatas() == true) {
                gerarSintetico(txt_dataInicio.getText(), txt_dataFim.getText());
            }
        } else {
            if (validaDatas() == true) {
                validaOutrosFiltros();
            }
        }
    }

    private boolean validaDatas() {
        if (txt_dataInicio.getText().equals("  /  /       :  ") == true) {
            JOptionPane.showMessageDialog(null, "Data Inicial inválida!");
            txt_dataInicio.requestFocus();
            return false;
        } else {
            if (txt_dataFim.getText().equals("  /  /       :  ") == true) {
                JOptionPane.showMessageDialog(null, "Data Final inválida!");
                txt_dataFim.requestFocus();
                return false;
            } else {
                return true;
            }
        }
    }

    private void gerarSintetico(String inicio, String fim) {
        try {
            Map parameters = new HashMap();
            GeraRelatorios geraRelatorios = new GeraRelatorios();
            parameters.put("dataInicial", Data.getDataByTexto(inicio, "dd/MM/yyyy HH:mm"));
            parameters.put("dataFinal", Data.getDataByTexto(fim, "dd/MM/yyyy HH:mm"));
            geraRelatorios.imprimirRelatorioSQLNoRelatorio(parameters, "src/Relatorios/Rel_Atendimento-Sintetico.jasper");
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(null, "Nenhum atendimento encontrado neste período!");
        }
    }

    private void validaOutrosFiltros() {
        if (cbx_cliente.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um Cliente!");
            cbx_cliente.requestFocus();
        } else {
            if (cbx_usuario.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione um Usuário!");
                cbx_usuario.requestFocus();
            } else {
                if (cbx_status.getSelectedItem().toString().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione um Status!");
                    cbx_status.requestFocus();
                } else {
                    gerarAnalitico(cbx_cliente.getSelectedItem().toString(),
                            cbx_status.getSelectedItem().toString(),
                            cbx_usuario.getSelectedItem().toString());
                }
            }
        }
    }

    private void gerarAnalitico(String cliente, String status, String usuario) {
        try {
            Map parameters = new HashMap();
            usuarioDAO = new UsuarioDAO();
            statusAtendimentoDAO = new StatusAtendimentoDAO();
            clienteDAO = new ClienteDAO();
            GeraRelatorios geraRelatorios = new GeraRelatorios();
            parameters.put("codUsuario", usuarioDAO.consultaByUsuario(usuario).getCodusuario());
            parameters.put("codCliente", clienteDAO.buscaClienteByNomeFantasia(cliente).getCodcliente());
            parameters.put("codStatus", statusAtendimentoDAO.buscaStatusAtendimento(status).getCodstatusatendimento());
            geraRelatorios.imprimirRelatorioSQLNoRelatorio(parameters, "src/Relatorios/Rel_Atendimento-Analitico.jasper");
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(null, "Nenhum atendimento encontrado!");
        }
    }
}
