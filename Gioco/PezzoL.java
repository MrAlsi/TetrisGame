package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoL extends Pezzo{
    public PezzoL(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 3, 1),
                new BloccoPieno(schermo, 4, 1),
                new BloccoPieno(schermo, 5, 1),
                new BloccoPieno(schermo, 5, 0));
        tipoPezzo = 2;
        rotazione = 1;
    }

}
