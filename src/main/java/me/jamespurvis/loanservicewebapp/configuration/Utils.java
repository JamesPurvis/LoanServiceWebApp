package me.jamespurvis.loanservicewebapp.configuration;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {
    public static String formatNumber(double number) {
        NumberFormat nf = new DecimalFormat("##.###");
        return nf.format(number);
    }
}
