package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoLungo extends Pezzo{
    public PezzoLungo(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 3, 0),
                new BloccoPieno(schermo, 4, 0),
                new BloccoPieno(schermo, 5, 0),
                new BloccoPieno(schermo, 6, 0));
        tipoPezzo = 0;
        rotazione = 1;
    }

}
