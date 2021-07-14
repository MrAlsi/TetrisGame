package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoQuadrato extends Pezzo{
    public PezzoQuadrato(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 3, 1),
                new BloccoPieno(schermo, 4, 1),
                new BloccoPieno(schermo, 3, 0),
                new BloccoPieno(schermo, 4, 0));
        tipoPezzo = 6;
        rotazione = 1;
    }

}
