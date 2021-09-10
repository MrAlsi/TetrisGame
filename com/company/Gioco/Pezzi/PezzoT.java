package com.company.Gioco.Pezzi;

import com.company.Gioco.BloccoPieno;
import com.company.Gioco.Griglia;
import com.company.Gioco.Pezzo;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoT extends Pezzo{
    public TextColor colore = TextColor.ANSI.YELLOW_BRIGHT;

    public PezzoT(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 4, 1, TextColor.ANSI.YELLOW_BRIGHT),
                new BloccoPieno(schermo, 3, 0, TextColor.ANSI.YELLOW_BRIGHT),
                new BloccoPieno(schermo, 4, 0, TextColor.ANSI.YELLOW_BRIGHT),
                new BloccoPieno(schermo, 5, 0, TextColor.ANSI.YELLOW_BRIGHT));
        tipoPezzo = 1;
        rotazione = 1;

         maxRotazioni = 3;

        spostamentoVerticale= new int[][]{​{​1,-1,0, 1}​, {​-1,-1,0,1}​, {​-1,1,0,-1}​, {​1,1,0,-1}​}​;
        spostamentoOrizzontale = new int[][]{​{​-1,-1,0,1}​, {​-1,1,0,-1}​, {1,1,0,-1}​, {1,-1,0,1}​}​;

    }

    @Override
    public void ruota(Griglia campo, int rotazione, int verso){

       for(int i=3; i>=0; i--){
       pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga());
         }
        for(int i=3;i>=0;i--){
       pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), spostamentoOrizzontale[rotazione][i], spostamentoVerticale[rotazione][i],  colore);
       pezzo[i].colonnaGriglia = pezzo[i].getColonna() + spostamentoOrizzontale[rotazione][i];
       pezzo[i].rigaGriglia = pezzo[i].getRiga() + spostamentoVerticale[rotazione][i];
       }
       if(rotazione!=maxRotazioni)
         this.rotazione++;
       else
         this.rotazione=0;
   
 
    }
/*
    @Override
    public void ruota(Griglia campo) {
        if (pezzo[0].rigaGriglia > 1) {
            switch (rotazione) {
                case 0:
                    if(pezzo[0].colonnaGriglia > 1) {
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1, colore);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0, colore);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1, colore);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1, colore);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                        rotazione = 1;
                    }
                    break;

                case 1:
                    pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                    pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                    pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                    pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());

                    pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1, colore);
                    pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                    pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                    pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0, colore);
                    pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                    pezzo[2].rigaGriglia = pezzo[2].getRiga();

                    pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1, colore);
                    pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                    pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                    pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1, colore);
                    pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                    pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                    rotazione = 2;

                    break;

                case 2:
                    if(pezzo[0].colonnaGriglia < 10) {
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());


                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1, colore);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0, colore);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1, colore);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1, colore);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                        rotazione = 3;
                    }
                    break;

                case 3:
                    pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                    pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                    pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                    pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());

                    pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1, colore);
                    pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                    pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                    pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0, colore);
                    pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                    pezzo[2].rigaGriglia = pezzo[2].getRiga();

                    pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1, colore);
                    pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                    pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                    pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1, colore);
                    pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                    pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                    rotazione = 0;

                    break;
            }
        }
    } */
}
