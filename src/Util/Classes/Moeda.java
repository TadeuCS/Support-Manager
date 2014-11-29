package Util.Classes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class Moeda {

    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(Locale.getDefault()); 

    public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00", REAL);

    public static String mascaraDinheiro(double valor) {
        return DINHEIRO_REAL.format(valor);
    }

}