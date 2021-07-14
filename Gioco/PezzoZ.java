package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoZ extends Pezzo{
    public PezzoZ(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 4, 1),
                new BloccoPieno(schermo, 5, 1),
                new BloccoPieno(schermo, 3, 0),
                new BloccoPieno(schermo, 4, 0));
        tipoPezzo = 5;
        rotazione = 1;
    }

}
