package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoS extends Pezzo{
    public PezzoS(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 3, 1),
                new BloccoPieno(schermo, 4, 1),
                new BloccoPieno(schermo, 4, 0),
                new BloccoPieno(schermo, 5, 0));
        tipoPezzo = 4;
        rotazione = 1;
    }

}
