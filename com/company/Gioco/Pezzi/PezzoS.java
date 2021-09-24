package com.Gioco.Pezzi;

import com.Gioco.BloccoPieno;
import com.Gioco.Griglia;
import com.Gioco.Pezzo;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoS extends Pezzo {

    public TextColor colore = TextColor.ANSI.BLUE_BRIGHT;

    public PezzoS(TextGraphics schermo, Griglia campo) {
        super(campo, new BloccoPieno(schermo, 4, 0, TextColor.ANSI.BLUE_BRIGHT),
                new BloccoPieno(schermo, 5, 0, TextColor.ANSI.BLUE_BRIGHT),
                new BloccoPieno(schermo, 3, 1, TextColor.ANSI.BLUE_BRIGHT),
                new BloccoPieno(schermo, 4, 1, TextColor.ANSI.BLUE_BRIGHT));
        tipoPezzo = 4;
        rotazione = 1;

        maxRotazioni = 1;

        spostamentoVerticale= new int[][]{{0, 1, 0, 1}, {0, -1, 0, -1}};
        spostamentoOrizzontale = new int[][]{{0, 1, -2, -1}, {0, -1, 2, 1}};

    }

    @Override
    public void ruota(Griglia campo, int rotazione, int verso){

       for(int i=3; i>=0; i--){
            pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga());
       }
        for(int i=3;i>=0;i--){
           pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), spostamentoOrizzontale[rotazione][i]*verso, spostamentoVerticale[rotazione][i]*verso,  colore);
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
