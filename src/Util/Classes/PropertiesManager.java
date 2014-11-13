package Util.Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class PropertiesManager {
    
    //altere de acordo com o nome do arquivo properties criado
    public static String file;
    FileOutputStream fos;
    FileInputStream fis;
    Properties properties = new Properties();

    public PropertiesManager() {
        cria();
    }
    public Properties getProperties(){
        return properties;
    }
    private String getCaminho() {
        File file = new File(this.file);
        //altere de acordo com o diretorio do seu arquivo properties
        System.out.println(file.getAbsolutePath().replaceAll(this.file, "").concat("src\\Util\\Email\\"));
        return file.getAbsolutePath().replaceAll(this.file, "").concat("src\\Util\\Email\\");
//        return file.getAbsolutePath().replaceAll(this.file, "").concat("src\\Util\\Classes\\");
    }

    private void cria() {
        try {
            //Criamos um objeto FileOutputStream
            //Setamos o arquivo que será lido
            fis = new FileInputStream(getCaminho()+file);
            properties.load(fis);
        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void altera(String campo, String valor) {
        try {
            fos = new FileOutputStream(getCaminho() + file);
            //altera do campo
            properties.setProperty(campo, valor);
            //grava os dados no arquivo
            properties.store(fos, campo);
            fos.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String ler(String campo) {
        try {
            //método load faz a leitura através do objeto fis
            properties.load(fis);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        //Captura o valor da propriedade, através do nome da propriedade(Key)
        return properties.getProperty(campo);
    }
}
