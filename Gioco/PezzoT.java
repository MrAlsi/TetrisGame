package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoT extends Pezzo{
    public PezzoT(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 4, 1),
                new BloccoPieno(schermo, 3, 0),
                new BloccoPieno(schermo, 4, 0),
                new BloccoPieno(schermo, 5, 0));
        tipoPezzo = 1;
        rotazione = 1;
    }
@Override
    public void ruota(Griglia campo) {
        switch (rotazione) {
        case 0:
            pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
            pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1);
            pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);

            pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
            pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
            pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

            pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[2].colonnaGriglia = pezzo[2].getColonna();
            pezzo[2].rigaGriglia = pezzo[2].getRiga();

            pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1);
            pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
            pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

            pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);
            pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
            pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

            rotazione = 1;

            break;

        case 1:
            pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
            pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
            pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1);

            pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
            pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
            pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

            pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[2].colonnaGriglia = pezzo[2].getColonna();
            pezzo[2].rigaGriglia = pezzo[2].getRiga();

            pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
            pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
            pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

            pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1);
            pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
            pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

            rotazione = 2;

            break;

        case 2:
            pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
            pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
            pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);


            pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
            pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
            pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

            pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[2].colonnaGriglia = pezzo[2].getColonna();
            pezzo[2].rigaGriglia = pezzo[2].getRiga();

            pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
            pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
            pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

            pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);
            pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
            pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

            rotazione = 3;

            break;

        case 3:
            pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
            pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
            pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);

            pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
            pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
            pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

            pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
            pezzo[2].colonnaGriglia = pezzo[2].getColonna();
            pezzo[2].rigaGriglia = pezzo[2].getRiga();

            pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
            pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
            pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

            pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);
            pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
            pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

            rotazione = 0;

            break;
        }
    }

}
