package id.epost.pos.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class Money {
    private static final NumberFormat IDR = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
    private Money() {}
    public static String idr(long value) { return IDR.format(value).replace(",00",""); }
}
