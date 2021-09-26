package com.company.Gioco.Pezzi;

import com.company.Gioco.BloccoPieno;
import com.company.Gioco.Griglia;
import com.company.Gioco.Pezzo;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoLungo extends Pezzo {

    public TextColor colore = TextColor.ANSI.RED_BRIGHT;

    public PezzoLungo(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 3, 0, TextColor.ANSI.RED_BRIGHT),
                new BloccoPieno(schermo, 4, 0, TextColor.ANSI.RED_BRIGHT),
                new BloccoPieno(schermo, 5, 0, TextColor.ANSI.RED_BRIGHT),
                new BloccoPieno(schermo, 6, 0, TextColor.ANSI.RED_BRIGHT));

        rotazione = 0;

        maxRotazioni = 1;

        spostamentoVerticale = new int[][]{{-2,-1,0,1}, {2,1,0,-1}};
        spostamentoOrizzontale = new int[][]{{2,1,0,-1}, {-2,-1,0,1}};

    }

    @Override
    public void ruota(Griglia campo, int rotazione, int verso){
        for(int i=3; i>=0; i--) {
            pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga());
            pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), spostamentoOrizzontale[rotazione][i]*verso, spostamentoVerticale[rotazione][i]*verso, colore);
            pezzo[i].colonnaGriglia = pezzo[i].getColonna() + spostamentoOrizzontale[rotazione][i]*verso;
            pezzo[i].rigaGriglia = pezzo[i].getRiga() + spostamentoVerticale[rotazione][i]*verso;

        }

        if(verso!=-1) {
            if (rotazione != maxRotazioni) {
                this.rotazione++;
            } else {
                this.rotazione = 0;
            }
        } else
            this.rotazione=rotazione;
    }
}

