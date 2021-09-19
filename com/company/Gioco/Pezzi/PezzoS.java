package com.company.Gioco.Pezzi;

import com.company.Gioco.BloccoPieno;
import com.company.Gioco.Griglia;
import com.company.Gioco.Pezzo;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoS extends Pezzo {

    public TextColor colore = TextColor.ANSI.BLUE_BRIGHT;

    public PezzoS(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 4, 0, TextColor.ANSI.BLUE_BRIGHT),
                new BloccoPieno(schermo, 5, 0, TextColor.ANSI.BLUE_BRIGHT),
                new BloccoPieno(schermo, 3, 1, TextColor.ANSI.BLUE_BRIGHT),
                new BloccoPieno(schermo, 4, 1, TextColor.ANSI.BLUE_BRIGHT));
        tipoPezzo = 4;
        rotazione = 0;

        maxRotazioni = 1;

        spostamentoVerticale= new int[][]{{0, 2, 0, 0}, {0, -2, 0, 0}};
        spostamentoOrizzontale = new int[][]{{-1, -1, 0, 0}, {1, 1, 0, 0}};

    }

    @Override
    public void ruota(Griglia campo, int rotazione, int verso){

       for(int i=3; i>=0; i--){
       pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga());
        /* }
        for(int i=3;i>=0;i--){*/
       pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), spostamentoOrizzontale[rotazione][i], spostamentoVerticale[rotazione][i],  colore);
       pezzo[i].colonnaGriglia = pezzo[i].getColonna() + spostamentoOrizzontale[rotazione][i];
       pezzo[i].rigaGriglia = pezzo[i].getRiga() + spostamentoVerticale[rotazione][i];
       }
       if(rotazione!=maxRotazioni)
         this.rotazione++;
       else
         this.rotazione=0;
    }
}
