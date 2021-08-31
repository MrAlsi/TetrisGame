package com.Gioco;

import com.Gioco.Mini.Mini_BloccoPieno;
import com.Gioco.Mini.Mini_BloccoStruttura;
import com.Gioco.Mini.Mini_BloccoVuoto;
import com.googlecode.lanterna.TextColor;

import static com.Gioco.Schermo.miniCampo;
import static com.Gioco.Schermo.schermo;


public class Traduttore implements Runnable{
    public String stringa;

    public Traduttore(String stringa){
        this.stringa=stringa;
    }

    @Override
    public void run() {
        traduciStringToInt(stringa);
    }

    public static void traduciStringToInt (String stringa){
            String[] s = stringa.split(":");
            String[] blocchi = s[1].split("");
            int index = 0;
            int[][] campoAvv = new int[12][24];

            for (int i = 0; i < 12; i++) {
                for (int e = 0; e < 24; e++) {
                    campoAvv[i][e] = Integer.parseInt(blocchi[index]);
                    index++;
                }
            }

            traduciIntToMiniGriglia(campoAvv, s[0]);

    }

    public static void traduciIntToMiniGriglia(int[][] campo, String nome) {
        int id = 0;
        try {
            for (int i = 0; i < miniCampo.length; i++) {
                if (nome.equals(miniCampo[i].nome)) {
                    id = i;
                }
            }
        } catch (Exception e) {

        }

        for (int i = 0; i < campo.length; i++) {
            for (int e = 0; e < campo[i].length; e++) {
                switch (campo[i][e]) {
                    case 0 -> {
                        miniCampo[id].griglia[i][e] = new Mini_BloccoVuoto(schermo, i, e, id);
                        break;
                    }
                    case 1 -> {
                        miniCampo[id].griglia[i][e] = new Mini_BloccoPieno(schermo, i, e, TextColor.ANSI.WHITE_BRIGHT, id);
                        break;
                    }
                    case 2 -> {
                        miniCampo[id].griglia[i][e] = new Mini_BloccoStruttura(schermo, i, e, id);
                        break;
                    }
                }
            }
        }
    }
}