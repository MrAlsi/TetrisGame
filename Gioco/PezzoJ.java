package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoJ extends Pezzo{
    public PezzoJ(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 5, 1),
                new BloccoPieno(schermo, 3, 0),
                new BloccoPieno(schermo, 4, 0),
                new BloccoPieno(schermo, 5, 0));
        tipoPezzo = 3;
        rotazione = 1;
    }

}
