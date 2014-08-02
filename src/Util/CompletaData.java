/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tadeu
 */
public class CompletaData {
    public String getData(String texto,String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        Date data = new Date();
        sdf.format(data);
        if (texto.contains("  /  /    ")) {
            texto = sdf.format(data);
        } else {
            if (texto.endsWith("/  /    ")) {
                String dataAtual = sdf.format(data);
                texto = dataAtual.replace(dataAtual.substring(0, 2), texto.substring(0, 2));
            } else {
                if (texto.endsWith("/    ")) {
                    String dataAtual = sdf.format(data);
                    texto = texto.replaceAll("    ", dataAtual.substring(6, 10));
                } else {
                    if ("  ".equals(texto.substring(8, 10))) {
                        String dataAtual = sdf.format(data);
                        texto = texto.replaceAll(texto.substring(6,8)+"  ", dataAtual.substring(6,8)+texto.substring(6,8));
                    }
                }
            }
        }
        return texto;
    }
}
