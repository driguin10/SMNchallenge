package com.desafio.rodrigo.smnchallenge.auxiliares;

import java.text.Normalizer;

public class RemoveAcentos {
    public String removeAcentos(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }
}
