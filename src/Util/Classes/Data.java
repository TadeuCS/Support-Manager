package Util.Classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Data {

    private String data;
    private int dia;
    private int mes;
    private int ano;

    /**
     * Coverta "data" para inteiro E valida Mês e Dia verificando o retorno de
     * cada metodo
     */
    public static Date getDataByTexto(String data, String formato) {
        try {
            Date date = null;
            DateFormat formatter = new SimpleDateFormat(formato);
            date = (java.util.Date) formatter.parse(data);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static  String getData(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        Date data = new Date();
        return sdf.format(data);
    }

    public static String getDataByDate(Date data, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(data);
    }

    public String completaData(String texto, String formato) {
        if (texto.contains("  /  /    ")) {
            texto = getData(formato);
        } else {
            if (texto.endsWith("/  /    ")) {
                String dataAtual = getData(formato);
                texto = dataAtual.replace(dataAtual.substring(0, 2), texto.substring(0, 2));
            } else {
                if (texto.endsWith("/    ")) {
                    String dataAtual = getData(formato);
                    texto = texto.replaceAll("    ", dataAtual.substring(6, 10));
                } else {
                    if ("  ".equals(texto.substring(8, 10))) {
                        String dataAtual = getData(formato);
                        texto = texto.replaceAll(texto.substring(6, 8) + "  ", dataAtual.substring(6, 8) + texto.substring(6, 8));
                    }
                }
            }
        }
        return validaData(texto);
    }

    public String validaData(String dataTemp) {

        int mesTemp;
        int diaTemp;
        data = dataTemp.replace("/", "");
        dia = Integer.parseInt(data.trim().substring(0, 2));
        mes = Integer.parseInt(data.trim().substring(2, 4));
        ano = Integer.parseInt(data.trim().substring(4, 8));
        mesTemp = checaMes(mes);
        if (mesTemp != -1) {
            mes = mesTemp;
            diaTemp = checaDia(dia);
            if (diaTemp != -1) {
                dia = diaTemp;
            } else {
                JOptionPane.showMessageDialog(null, "Data Inválida");
                dataTemp = null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Inválida!");
            dataTemp = null;
        }
        return dataTemp;
    }

//Checa mes verificando se é maior que zero e menor que 12
    public int checaMes(int mesTemp) {

        if (mesTemp > 0 && mesTemp <= 12) {
            return mesTemp;
        } else {
            return -1;
        }
    }

    /**
     * Checa dia tendo uma variavel inteira como arrayc "ultimoDiaMes" com os
     * valores do ultimo dia de cada mes. Verifica também se o ano é Bissexto
     */
    public int checaDia(int diaTemp) {
        int ultimoDiaMes[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (diaTemp > 0 && diaTemp <= ultimoDiaMes[mes]) {
            return diaTemp;
        } else if ((mes == 2 && diaTemp == 29) && (ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) {
            return diaTemp;
        } else {
            return -1;
        }
    }
}
