package Util.Classes;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class GeraRelatorios {

    public String getDiretorio(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        return arquivo.getAbsolutePath();
    }

    public void imprimirRelatorioEmCodigo() {
        Connection conn = null;
        try {
            // Obtém o diretório da aplicação

            // Carrega conexão via JDBC
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:1527/banco", "usuario", "senha");
            Statement sql = conn.createStatement();

            // Carrega fonte de dados
            ResultSet rs = sql.executeQuery("SELECT * from sua tabela");
            JRDataSource ds = new JRResultSetDataSource(rs);

            // Preenche o relatório com os dados
            JasperPrint print = JasperFillManager.fillReport(getDiretorio("Teste") + "/arquivo.jasper", null, ds);

            // Exibe visualização dos dados
            JasperViewer.viewReport(print, false);

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao localizar a classe responsável pela geração do relatório!\n" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar consulta no Banco de dados!\n" + ex.getMessage());
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n" + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão com o Banco de Dados.");
            }
        }
    }

    

    public void imprimirRelatorioSQLNoRelatorio(Map parametros, String diretorio) {
        Connection conn = null;
        try {
            // Carrega conexão via JDBC
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smdados", "root", "1234");

            // Preenche o relatório com os dados
            JasperPrint print = JasperFillManager.fillReport(diretorio, parametros, conn);

            // Exibe visualização dos dados
            JasperViewer.viewReport(print, false);

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao localizar a classe responsável pela geração do relatório!\n" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar consulta no Banco de dados!\n" + ex.getMessage());
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n" + ex.getMessage());
        }catch(NoResultException e){
            System.out.println("vazio");
        }finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão com o Banco de Dados.");
            }
        }
    }
}
