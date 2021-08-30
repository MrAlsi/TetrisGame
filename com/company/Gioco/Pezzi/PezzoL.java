package com.company.Gioco.Pezzi;

import com.company.Gioco.BloccoPieno;
import com.company.Gioco.Griglia;
import com.company.Gioco.Pezzo;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoL extends Pezzo {

    public TextColor colore = TextColor.ANSI.YELLOW_BRIGHT;

    public PezzoL(TextGraphics schermo, Griglia campo) {
        super(campo, new BloccoPieno(schermo, 3, 1, TextColor.ANSI.YELLOW_BRIGHT),
                new BloccoPieno(schermo, 4, 1, TextColor.ANSI.YELLOW_BRIGHT),
                new BloccoPieno(schermo, 5, 1, TextColor.ANSI.YELLOW_BRIGHT),
                new BloccoPieno(schermo, 5, 0, TextColor.ANSI.YELLOW_BRIGHT));
        tipoPezzo = 2;
        rotazione = 1;
    }

    @Override
    public void ruota(Griglia campo) {
        switch (rotazione) {
            case 0:
                if(pezzo[0].colonnaGriglia < 11) {
                    pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                    pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                    pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                    pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());

                    pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 2, 0, colore);
                    pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 2;
                    pezzo[3].rigaGriglia = pezzo[3].getRiga();

                    pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, 1, colore);
                    pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;
                    pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;

                    pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0, colore);
                    pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                    pezzo[1].rigaGriglia = pezzo[1].getRiga();

                    pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1, colore);
                    pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                    pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                    rotazione = 1;
                }
                break;

            case 1:
                pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());

                pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 2, colore);
                pezzo[3].colonnaGriglia = pezzo[3].getColonna();
                pezzo[3].rigaGriglia = pezzo[3].getRiga() + 2;

                pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1, colore);
                pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;
                pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;

                pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0, colore);
                pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                pezzo[1].rigaGriglia = pezzo[1].getRiga();

                pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1, colore);
                pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                rotazione = 2;

                break;

            case 2:
                if(pezzo[0].colonnaGriglia > 0) {
                    pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                    pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                    pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                    pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());

                    pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -2, 0, colore);
                    pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 2;
                    pezzo[3].rigaGriglia = pezzo[3].getRiga();

                    pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, -1, colore);
                    pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;
                    pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;

                    pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0, colore);
                    pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                    pezzo[1].rigaGriglia = pezzo[1].getRiga();

                    pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1, colore);
                    pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                    pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                    rotazione = 3;
                }
                break;

            case 3:
                pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga());
                pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga());
                pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga());
                pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga());

                pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, -2, colore);
                pezzo[3].colonnaGriglia = pezzo[3].getColonna();
                pezzo[3].rigaGriglia = pezzo[3].getRiga() - 2;

                pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1, colore);
                pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;
                pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;

                pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0, colore);
                pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                pezzo[1].rigaGriglia = pezzo[1].getRiga();

                pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1, colore);
                pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                rotazione = 0;

                break;
        }
    }
}
