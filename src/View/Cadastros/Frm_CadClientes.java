/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import Controller.AplicativoDAO;
import Controller.ClienteDAO;
import Controller.SegmentoDAO;
import Controller.UsuarioDAO;
import Enums.EstadosENUM;
import Enums.TipoPessoaENUM;
import Model.Cliente;
import Model.Contato;
import Model.Endereco;
import Model.Link;
import Model.LinkCliente;
import Util.CompletaData;
import Util.IntegerDocument;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Tadeu
 */
public class Frm_CadClientes extends javax.swing.JFrame {

    ClienteDAO clienteDAO;
    private SegmentoDAO segmentoDAO;
    private AplicativoDAO aplicativoDAO;
    private TipoPessoaENUM tipoPessoaENUM;
    private EstadosENUM estadosENUM;
    private CompletaData c;
    Cliente cliente;
    Endereco endereco;
    Contato contato;
    Link link;
    LinkCliente linksClientes;
    DefaultTableModel model;

    public Frm_CadClientes() {
        initComponents();
        setVisible(true);
        abas.setEnabled(false);
        c = new CompletaData();
        txt_codigo.setDocument(new IntegerDocument(4));
        txt_referencia.setDocument(new IntegerDocument(8));
        txt_numero.setDocument(new IntegerDocument(5));
        txt_quantidade.setDocument(new IntegerDocument(2));
        cbx_tipo.setModel(new DefaultComboBoxModel<>(tipoPessoaENUM.values()));
        cbx_estados.setModel(new DefaultComboBoxModel<>(estadosENUM.values()));
        cbx_estados.setSelectedItem("MG");
        try {
            carregaSegmentos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        try {
            trocaMascara();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Selecione Um Tipo de Cliente");
        }
        camposOFF();
    }

    public void camposOFF(){
        
    }
    
    public void proximo() {
        abas.setSelectedIndex(abas.getSelectedIndex() + 1);
    }

    public void anterior() {
        abas.setSelectedIndex(abas.getSelectedIndex() - 1);
    }

    public int getTipoEnum() {
        return cbx_tipo.getSelectedIndex();
    }

    public void setTipoEnum(int tipoEnum) {
        cbx_tipo.setSelectedIndex(tipoEnum);
    }

    public void trocaMascara() throws ParseException {
        if (cbx_tipo.getSelectedIndex() == 0) {
            txt_cpf.setValue(null);
            MaskFormatter cpf = new MaskFormatter("###.###.###-##");
            txt_cpf.setFormatterFactory(
                    new DefaultFormatterFactory(cpf));
        } else {
            txt_cpf.setValue(null);
            MaskFormatter cnpj = new MaskFormatter("##.###.###/####-##");
            txt_cpf.setFormatterFactory(
                    new DefaultFormatterFactory(cnpj));
        }
        txt_cpf.requestFocus();
    }

    public void carregaSegmentos() throws Exception {
        segmentoDAO = new SegmentoDAO();
        int i = 0;
        try {
            while (i < segmentoDAO.lista().size()) {
                String linha = segmentoDAO.lista().get(i).getDescricao();
                cbx_segmento.addItem(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Segmentos");
        }

    }

    public void getCliente(Cliente cliente) {
        //dados da aba dados pessoais
        txt_codigo.setText(cliente.getCodcliente() + "");
        cbx_tipo.setSelectedItem(cliente.getTipoPessoa());
        txt_cpf.setText(cliente.getCnpjCpf());
        txt_inscEstadual.setText(cliente.getInscricaoEstadual());
        txt_razaoSocial.setText(cliente.getRazaoSocial());
        txt_nomeFantasia.setText(cliente.getNomeFantasia());
        txt_referencia.setText(cliente.getReferencia() + "");
        txt_responsavel.setText(cliente.getResponsavel());
        txt_email.setText(cliente.getEmail());
        txt_data.setText(cliente.getDataAtualizacao() + "");
        cbx_segmento.setSelectedItem(cliente.getCodsegmento().getDescricao());
        if (cliente.getBloqueado().equals("S") == true) {
            chx_bloqueado.setSelected(true);
        } else {
            chx_bloqueado.setSelected(false);
        }
        //dados da aba endereco
        txt_cep.setText(cliente.getEnderecoList().get(0).getCep());
        txt_rua.setText(cliente.getEnderecoList().get(0).getRua());
        txt_cidade.setText(cliente.getEnderecoList().get(0).getCidade());
        cbx_estados.setSelectedItem(cliente.getEnderecoList().get(0).getEstado());
        txt_bairro.setText(cliente.getEnderecoList().get(0).getBairro());
        txt_numero.setText(cliente.getEnderecoList().get(0).getNumero() + "");
        txt_complemento.setText(cliente.getEnderecoList().get(0).getComplemento());

        //dados da aba telefones
        listaTelefones(cliente);
        txt_contato.setText(null);
        txt_telefone.setText(null);

        //dados da aba links
        listaLinks(cliente);
        txt_quantidade.setText(null);
    }

    public void setCliente() {
        //dados da aba dados pessoais
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        sdf.format(txt_data.getText());
        cliente = new Cliente();
        cliente.setCodcliente(Integer.parseInt(txt_codigo.getText()));
        cliente.setTipoPessoa(cbx_tipo.getSelectedItem().toString());
        cliente.setCnpjCpf(txt_cpf.getText());
        cliente.setInscricaoEstadual(txt_inscEstadual.getText());
        cliente.setRazaoSocial(txt_razaoSocial.getText());
        cliente.setNomeFantasia(txt_nomeFantasia.getText());
        cliente.setResponsavel(txt_responsavel.getText());
        cliente.setReferencia(Integer.parseInt(txt_referencia.getText()));
        cliente.setEmail(txt_email.getText());
        cliente.setDataAtualizacao(data);
        cliente.setCodsegmento(segmentoDAO.buscaSegmento(cbx_segmento.getSelectedItem().toString()));
        if (chx_bloqueado.getSelectedObjects() != null) {
            cliente.setBloqueado('S');
        } else {
            cliente.setBloqueado('N');
        }
        //dados da aba endereco
        cliente.getEnderecoList().add(setEndereco(cliente));

    }

    public void setContatos(Cliente cliente) {
        try {
            contato = new Contato();
            model = (DefaultTableModel) tb_telefones.getModel();
            contato.setNome(txt_contato.getText());
            contato.setTelefone(txt_contato.getText());
            contato.setCodcliente(cliente);
            String[] linha = new String[]{contato.getNome(), contato.getTelefone()};
            model.addRow(linha);
            cliente.getContatoList().add(contato);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o Contato na lista!");
        }
    }
    
    public void setLinks(Cliente cliente) {
        try {
            linksClientes = new LinkCliente();
            link=new Link();
            model = (DefaultTableModel) tb_telefones.getModel();
            link.setCodaplicativo(aplicativoDAO.buscaAplicativo(cbx_aplicativo.getSelectedItem().toString()));
            link.setDescricao(cbx_aplicativo.getSelectedItem().toString());
            linksClientes.setCodcliente(cliente);
            linksClientes.setCodlink(link);
            linksClientes.setQuantidade(Integer.parseInt(txt_quantidade.getText()));
            String[] linha = new String[]{link.getDescricao(), txt_quantidade.getText()};
            model.addRow(linha);
            cliente.getLinkClienteList().add(linksClientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o Links na lista!");
        }
    }

    public Endereco setEndereco(Cliente cliente) {
        endereco = new Endereco();
        endereco.setBairro(txt_bairro.getText());
        endereco.setCep(txt_cep.getText());
        endereco.setCidade(txt_cidade.getText());
        endereco.setComplemento(txt_complemento.getText());
        endereco.setEstado(cbx_estados.getSelectedItem().toString());
        endereco.setNumero(Integer.parseInt(txt_numero.getText()));
        endereco.setRua(txt_rua.getText());
        endereco.setCodcliente(cliente);
        return endereco;
    }

    public void listaTelefones(Cliente cliente) {
        clienteDAO = new ClienteDAO();
        model = (DefaultTableModel) tb_telefones.getModel();
        try {
            int i = 0;
            tb_telefones.removeAll();
            while (i < clienteDAO.listaContatosByCliente(cliente).size()) {
                String[] linha = new String[]{
                    clienteDAO.listaContatosByCliente(cliente).get(i).getNome(),
                    clienteDAO.listaContatosByCliente(cliente).get(i).getTelefone(),};
                model.addRow(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void listaLinks(Cliente cliente) {
        clienteDAO = new ClienteDAO();
        model = (DefaultTableModel) tb_telefones.getModel();
        try {
            int i = 0;
            tb_telefones.removeAll();
            while (i < clienteDAO.listalinksByCliente(cliente).size()) {
                String[] linha = new String[]{
                    clienteDAO.listalinksByCliente(cliente).get(i).getCodlink().getDescricao(),
                    clienteDAO.listalinksByCliente(cliente).get(i).getQuantidade() + ""};
                model.addRow(linha);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abas = new javax.swing.JTabbedPane();
        pnl_dadosCliente = new javax.swing.JPanel();
        btn_proximoDados = new javax.swing.JButton();
        pnl_dadosPessoais = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbx_tipo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txt_cpf = new javax.swing.JFormattedTextField();
        txt_inscEstadual = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_razaoSocial = new javax.swing.JTextField();
        txt_nomeFantasia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        txt_referencia = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txt_responsavel = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        cbx_segmento = new javax.swing.JComboBox();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        chx_bloqueado = new javax.swing.JCheckBox();
        txt_data = new javax.swing.JFormattedTextField();
        pnl_endereco = new javax.swing.JPanel();
        btn_proximoEndereco = new javax.swing.JButton();
        btn_anteriorEndereco = new javax.swing.JButton();
        pnl_dadosEndereco = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txt_cep = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_cidade = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cbx_estados = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        txt_bairro = new javax.swing.JTextField();
        txt_complemento = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_numero = new javax.swing.JTextField();
        txt_rua = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        pnl_telefones = new javax.swing.JPanel();
        btn_proximoTelefones = new javax.swing.JButton();
        btn_anteriorTelefones = new javax.swing.JButton();
        pnl_dados_telefones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_telefones = new javax.swing.JTable();
        pnl_cadTelefones = new javax.swing.JPanel();
        txt_telefone = new javax.swing.JFormattedTextField();
        jLabel45 = new javax.swing.JLabel();
        txt_contato = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btn_inserirTelefone = new javax.swing.JButton();
        btn_removerTelefone = new javax.swing.JButton();
        pnl_links = new javax.swing.JPanel();
        btn_salvar = new javax.swing.JButton();
        btn_anterior = new javax.swing.JButton();
        pnl_dadosLinks = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_links = new javax.swing.JTable();
        pnl_CadLinks = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbx_aplicativo = new javax.swing.JComboBox();
        cbx_links = new javax.swing.JComboBox();
        jLabel48 = new javax.swing.JLabel();
        txt_quantidade = new javax.swing.JTextField();
        btn_inserirLinks = new javax.swing.JButton();
        btn_removerLinks = new javax.swing.JButton();
        pnl_botoes = new javax.swing.JPanel();
        btn_inclusao = new javax.swing.JButton();
        btn_alteracao = new javax.swing.JButton();
        btn_exclusao = new javax.swing.JButton();
        btn_consulta = new javax.swing.JButton();
        txt_operacao = new javax.swing.JTextField();
        btn_cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Clientes");
        setResizable(false);

        btn_proximoDados.setText("Proximo");
        btn_proximoDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoDadosActionPerformed(evt);
            }
        });

        pnl_dadosPessoais.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código *:");

        jLabel2.setText("Tipo *:");

        cbx_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_tipoActionPerformed(evt);
            }
        });

        jLabel3.setText("CPF/CNPJ *:");

        txt_cpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setText("Insc Estadual *:");

        jLabel5.setText("Razão Social/Nome *:");

        jLabel6.setText("Nome Fantasia *:");

        jLabel46.setText("Email *:");

        jLabel49.setText("Referência *:");

        jLabel50.setText("Responsavel *:");

        jLabel51.setText("Segmento *:");

        cbx_segmento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_segmentoActionPerformed(evt);
            }
        });

        jLabel52.setText("Atualização *:");

        jLabel53.setText("Bloqueado *:");

        try {
            txt_data.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_data.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_data.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosPessoaisLayout = new javax.swing.GroupLayout(pnl_dadosPessoais);
        pnl_dadosPessoais.setLayout(pnl_dadosPessoaisLayout);
        pnl_dadosPessoaisLayout.setHorizontalGroup(
            pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel6)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel53)
                            .addComponent(jLabel49))
                        .addGap(18, 18, 18)))
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nomeFantasia)
                    .addComponent(txt_razaoSocial)
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_inscEstadual, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(chx_bloqueado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel52))
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(txt_referencia, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel50)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(txt_data, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel51))
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(txt_responsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel46)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_email)
                            .addComponent(cbx_segmento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnl_dadosPessoaisLayout.setVerticalGroup(
            pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txt_inscEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_razaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_responsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_referencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel50)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel52)
                        .addComponent(chx_bloqueado)
                        .addComponent(txt_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbx_segmento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel51))
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_dadosClienteLayout = new javax.swing.GroupLayout(pnl_dadosCliente);
        pnl_dadosCliente.setLayout(pnl_dadosClienteLayout);
        pnl_dadosClienteLayout.setHorizontalGroup(
            pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dadosPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosClienteLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_proximoDados)))
                .addContainerGap())
        );
        pnl_dadosClienteLayout.setVerticalGroup(
            pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_proximoDados)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        abas.addTab("Dados Pessoais", pnl_dadosCliente);

        btn_proximoEndereco.setText("Proximo");
        btn_proximoEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoEnderecoActionPerformed(evt);
            }
        });

        btn_anteriorEndereco.setText("Anterior");
        btn_anteriorEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorEnderecoActionPerformed(evt);
            }
        });

        pnl_dadosEndereco.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("CEP *:");

        jLabel17.setText("Cidade *:");

        jLabel18.setText("Estado:");

        cbx_estados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_estadosActionPerformed(evt);
            }
        });

        jLabel19.setText("Bairro *:");

        jLabel20.setText("Complemento *:");

        jLabel21.setText("Número *:");

        txt_numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numeroActionPerformed(evt);
            }
        });

        jLabel22.setText("Rua *:");

        javax.swing.GroupLayout pnl_dadosEnderecoLayout = new javax.swing.GroupLayout(pnl_dadosEndereco);
        pnl_dadosEndereco.setLayout(pnl_dadosEnderecoLayout);
        pnl_dadosEnderecoLayout.setHorizontalGroup(
            pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_rua, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addComponent(txt_bairro)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                                .addComponent(txt_cidade)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbx_estados, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnl_dadosEnderecoLayout.setVerticalGroup(
            pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosEnderecoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txt_rua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addGap(18, 18, 18)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txt_bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(cbx_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txt_cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_enderecoLayout = new javax.swing.GroupLayout(pnl_endereco);
        pnl_endereco.setLayout(pnl_enderecoLayout);
        pnl_enderecoLayout.setHorizontalGroup(
            pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_enderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_enderecoLayout.createSequentialGroup()
                        .addComponent(btn_anteriorEndereco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_proximoEndereco))
                    .addComponent(pnl_dadosEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_enderecoLayout.setVerticalGroup(
            pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_enderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(pnl_enderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proximoEndereco)
                    .addComponent(btn_anteriorEndereco))
                .addGap(11, 11, 11))
        );

        abas.addTab("Endereço", pnl_endereco);

        btn_proximoTelefones.setText("Proximo");
        btn_proximoTelefones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximoTelefonesActionPerformed(evt);
            }
        });

        btn_anteriorTelefones.setText("Anterior");
        btn_anteriorTelefones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorTelefonesActionPerformed(evt);
            }
        });

        pnl_dados_telefones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_telefones.setModel(new javax.swing.table.DefaultTableModel(
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
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_telefones);

        pnl_cadTelefones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        try {
            txt_telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_telefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel45.setText("Telefone *:");

        jLabel7.setText("Contato *:");

        javax.swing.GroupLayout pnl_cadTelefonesLayout = new javax.swing.GroupLayout(pnl_cadTelefones);
        pnl_cadTelefones.setLayout(pnl_cadTelefonesLayout);
        pnl_cadTelefonesLayout.setHorizontalGroup(
            pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cadTelefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_cadTelefonesLayout.createSequentialGroup()
                        .addGap(0, 100, Short.MAX_VALUE)
                        .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_contato))
                .addContainerGap())
        );
        pnl_cadTelefonesLayout.setVerticalGroup(
            pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_cadTelefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_cadTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addContainerGap())
        );

        btn_inserirTelefone.setText("Inserir");
        btn_inserirTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inserirTelefoneActionPerformed(evt);
            }
        });

        btn_removerTelefone.setText("Remover");
        btn_removerTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerTelefoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dados_telefonesLayout = new javax.swing.GroupLayout(pnl_dados_telefones);
        pnl_dados_telefones.setLayout(pnl_dados_telefonesLayout);
        pnl_dados_telefonesLayout.setHorizontalGroup(
            pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_cadTelefones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                        .addComponent(btn_removerTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_inserirTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_dados_telefonesLayout.setVerticalGroup(
            pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnl_dados_telefonesLayout.createSequentialGroup()
                        .addComponent(pnl_cadTelefones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addGroup(pnl_dados_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_inserirTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_removerTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_telefonesLayout = new javax.swing.GroupLayout(pnl_telefones);
        pnl_telefones.setLayout(pnl_telefonesLayout);
        pnl_telefonesLayout.setHorizontalGroup(
            pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dados_telefones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_telefonesLayout.createSequentialGroup()
                        .addComponent(btn_anteriorTelefones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, Short.MAX_VALUE)
                        .addComponent(btn_proximoTelefones)))
                .addContainerGap())
        );
        pnl_telefonesLayout.setVerticalGroup(
            pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_telefonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dados_telefones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(pnl_telefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proximoTelefones)
                    .addComponent(btn_anteriorTelefones))
                .addGap(11, 11, 11))
        );

        abas.addTab("Telefones", pnl_telefones);

        btn_salvar.setText("Salvar");

        btn_anterior.setText("Anterior");
        btn_anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anteriorActionPerformed(evt);
            }
        });

        pnl_dadosLinks.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_links.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Link", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_links);

        pnl_CadLinks.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel47.setText("Link *:");

        jLabel8.setText("Aplicativo *:");

        cbx_aplicativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_aplicativoActionPerformed(evt);
            }
        });

        cbx_links.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_linksActionPerformed(evt);
            }
        });

        jLabel48.setText("Quantidade *:");

        javax.swing.GroupLayout pnl_CadLinksLayout = new javax.swing.GroupLayout(pnl_CadLinks);
        pnl_CadLinks.setLayout(pnl_CadLinksLayout);
        pnl_CadLinksLayout.setHorizontalGroup(
            pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_CadLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47)
                    .addComponent(jLabel8)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_aplicativo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbx_links, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_CadLinksLayout.createSequentialGroup()
                        .addComponent(txt_quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_CadLinksLayout.setVerticalGroup(
            pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_CadLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbx_aplicativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(cbx_links, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnl_CadLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txt_quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_inserirLinks.setText("Inserir");
        btn_inserirLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inserirLinksActionPerformed(evt);
            }
        });

        btn_removerLinks.setText("Remover");
        btn_removerLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerLinksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosLinksLayout = new javax.swing.GroupLayout(pnl_dadosLinks);
        pnl_dadosLinks.setLayout(pnl_dadosLinksLayout);
        pnl_dadosLinksLayout.setHorizontalGroup(
            pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_CadLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dadosLinksLayout.createSequentialGroup()
                        .addComponent(btn_removerLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(btn_inserirLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_dadosLinksLayout.setVerticalGroup(
            pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLinksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnl_dadosLinksLayout.createSequentialGroup()
                        .addComponent(pnl_CadLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(pnl_dadosLinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_inserirLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_removerLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_linksLayout = new javax.swing.GroupLayout(pnl_links);
        pnl_links.setLayout(pnl_linksLayout);
        pnl_linksLayout.setHorizontalGroup(
            pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_linksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_dadosLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_linksLayout.createSequentialGroup()
                        .addComponent(btn_anterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar)))
                .addContainerGap())
        );
        pnl_linksLayout.setVerticalGroup(
            pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_linksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_anterior))
                .addContainerGap())
        );

        abas.addTab("Links", pnl_links);

        pnl_botoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_inclusao.setText("Inclusão");

        btn_alteracao.setText("Alteração");

        btn_exclusao.setText("Exclusão");

        btn_consulta.setText("Consulta");

        btn_cancelar.setText("Cancelar");

        javax.swing.GroupLayout pnl_botoesLayout = new javax.swing.GroupLayout(pnl_botoes);
        pnl_botoes.setLayout(pnl_botoesLayout);
        pnl_botoesLayout.setHorizontalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_inclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_alteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_exclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_botoesLayout.setVerticalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_inclusao)
                    .addComponent(btn_alteracao)
                    .addComponent(btn_exclusao)
                    .addComponent(btn_consulta)
                    .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pnl_botoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_proximoDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoDadosActionPerformed
        proximo();
    }//GEN-LAST:event_btn_proximoDadosActionPerformed

    private void btn_proximoEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoEnderecoActionPerformed
        proximo();
    }//GEN-LAST:event_btn_proximoEnderecoActionPerformed

    private void btn_proximoTelefonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximoTelefonesActionPerformed
        proximo();
    }//GEN-LAST:event_btn_proximoTelefonesActionPerformed

    private void btn_anteriorTelefonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorTelefonesActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorTelefonesActionPerformed

    private void btn_anteriorEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorEnderecoActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorEnderecoActionPerformed

    private void btn_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorActionPerformed
        anterior();
    }//GEN-LAST:event_btn_anteriorActionPerformed

    private void cbx_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_tipoActionPerformed
        try {
            trocaMascara();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Selecione Um Tipo de Cliente");
        }
    }//GEN-LAST:event_cbx_tipoActionPerformed

    private void cbx_estadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_estadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_estadosActionPerformed

    private void txt_numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numeroActionPerformed

    private void btn_inserirTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inserirTelefoneActionPerformed
        setContatos(cliente);
    }//GEN-LAST:event_btn_inserirTelefoneActionPerformed

    private void btn_removerTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_removerTelefoneActionPerformed

    private void btn_inserirLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inserirLinksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_inserirLinksActionPerformed

    private void btn_removerLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerLinksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_removerLinksActionPerformed

    private void cbx_aplicativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_aplicativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_aplicativoActionPerformed

    private void cbx_linksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_linksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_linksActionPerformed

    private void cbx_segmentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_segmentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_segmentoActionPerformed

    private void txt_dataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_data.setText(c.getData(txt_data.getText(), "dd/MM/yyyy"));
        }
    }//GEN-LAST:event_txt_dataKeyPressed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_CadClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CadClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CadClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CadClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_CadClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btn_alteracao;
    private javax.swing.JButton btn_anterior;
    private javax.swing.JButton btn_anteriorEndereco;
    private javax.swing.JButton btn_anteriorTelefones;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_consulta;
    private javax.swing.JButton btn_exclusao;
    private javax.swing.JButton btn_inclusao;
    private javax.swing.JButton btn_inserirLinks;
    private javax.swing.JButton btn_inserirTelefone;
    private javax.swing.JButton btn_proximoDados;
    private javax.swing.JButton btn_proximoEndereco;
    private javax.swing.JButton btn_proximoTelefones;
    private javax.swing.JButton btn_removerLinks;
    private javax.swing.JButton btn_removerTelefone;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_aplicativo;
    private javax.swing.JComboBox cbx_estados;
    private javax.swing.JComboBox cbx_links;
    private javax.swing.JComboBox cbx_segmento;
    private javax.swing.JComboBox cbx_tipo;
    private javax.swing.JCheckBox chx_bloqueado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnl_CadLinks;
    private javax.swing.JPanel pnl_botoes;
    private javax.swing.JPanel pnl_cadTelefones;
    private javax.swing.JPanel pnl_dadosCliente;
    private javax.swing.JPanel pnl_dadosEndereco;
    private javax.swing.JPanel pnl_dadosLinks;
    private javax.swing.JPanel pnl_dadosPessoais;
    private javax.swing.JPanel pnl_dados_telefones;
    private javax.swing.JPanel pnl_endereco;
    private javax.swing.JPanel pnl_links;
    private javax.swing.JPanel pnl_telefones;
    private javax.swing.JTable tb_links;
    private javax.swing.JTable tb_telefones;
    private javax.swing.JTextField txt_bairro;
    private javax.swing.JFormattedTextField txt_cep;
    private javax.swing.JTextField txt_cidade;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_complemento;
    private javax.swing.JTextField txt_contato;
    private javax.swing.JFormattedTextField txt_cpf;
    private javax.swing.JFormattedTextField txt_data;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_inscEstadual;
    private javax.swing.JTextField txt_nomeFantasia;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_operacao;
    private javax.swing.JTextField txt_quantidade;
    private javax.swing.JTextField txt_razaoSocial;
    private javax.swing.JTextField txt_referencia;
    private javax.swing.JTextField txt_responsavel;
    private javax.swing.JTextField txt_rua;
    private javax.swing.JFormattedTextField txt_telefone;
    // End of variables declaration//GEN-END:variables
}
