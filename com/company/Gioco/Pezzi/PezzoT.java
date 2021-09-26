package com.company.Gioco.Pezzi;

import com.company.Gioco.BloccoPieno;
import com.company.Gioco.Griglia;
import com.company.Gioco.Pezzo;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoT extends Pezzo{
    public TextColor colore = TextColor.ANSI.GREEN;

    public PezzoT(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 4, 0, TextColor.ANSI.GREEN),
                new BloccoPieno(schermo, 5, 0, TextColor.ANSI.GREEN),
                new BloccoPieno(schermo, 6, 0, TextColor.ANSI.GREEN),
                new BloccoPieno(schermo, 5, 1, TextColor.ANSI.GREEN));
        
        rotazione = 1;

        maxRotazioni = 3;

        spostamentoVerticale= new int[][]{{-1, 0, 1, 1}, {-1, 0, 1, -1}, {1, 0, -1, -1}, {1, 0, -1, 1}};
        spostamentoOrizzontale = new int[][]{{-1, 0, 1, -1}, {1, 0, -1, -1}, {1, 0, -1, 1}, {-1, 0, 1, 1}};
    }

    @Override
    public void ruota(Griglia campo, int rotazione, int verso){

        for(int i=3; i>=0; i--) {
            pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga());
         }
        for(int i=3;i>=0;i--){
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
