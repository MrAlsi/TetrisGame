package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoLungo extends Pezzo{

    public TextColor colore = TextColor.ANSI.RED_BRIGHT;

    public PezzoLungo(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 3, 0, TextColor.ANSI.RED_BRIGHT),
                new BloccoPieno(schermo, 4, 0, TextColor.ANSI.RED_BRIGHT),
                new BloccoPieno(schermo, 5, 0, TextColor.ANSI.RED_BRIGHT),
                new BloccoPieno(schermo, 6, 0, TextColor.ANSI.RED_BRIGHT));
        tipoPezzo = 0;
        rotazione = 1;
    }

    @Override
    public void ruota(Griglia campo) {
        if (pezzo[0].rigaGriglia > 2) {
            switch (rotazione) {
            case 0:
                pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 0, colore);
                pezzo[3].rigaGriglia = pezzo[3].getRiga();
                pezzo[3].colonnaGriglia = pezzo[3].getColonna();

                pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1, colore);
                pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;
                pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;

                pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -2, 2, colore);
                pezzo[1].rigaGriglia = pezzo[1].getRiga() + 2;
                pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 2;

                pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());
                pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -3, 3, colore);
                pezzo[0].rigaGriglia = pezzo[0].getRiga() + 3;
                pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 3;

                rotazione = 1;

                break;

            case 1:
                pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 0, colore);
                pezzo[3].rigaGriglia = pezzo[3].getRiga();
                pezzo[3].colonnaGriglia = pezzo[3].getColonna();

                pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1, colore);
                pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;
                pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;

                pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 2, -2, colore);
                pezzo[1].rigaGriglia = pezzo[1].getRiga() - 2;
                pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 2;

                pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());
                pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 3, -3, colore);
                pezzo[0].rigaGriglia = pezzo[0].getRiga() - 3;
                pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 3;

                rotazione = 0;

                break;
            }
        }
    }

}
