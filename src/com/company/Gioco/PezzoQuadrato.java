package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PezzoQuadrato extends Pezzo{

    TextColor colore = TextColor.ANSI.CYAN_BRIGHT;

    public PezzoQuadrato(TextGraphics schermo, Griglia campo) {
        super(campo,new BloccoPieno(schermo, 3, 1, TextColor.ANSI.CYAN_BRIGHT),
                new BloccoPieno(schermo, 4, 1, TextColor.ANSI.CYAN_BRIGHT),
                new BloccoPieno(schermo, 3, 0, TextColor.ANSI.CYAN_BRIGHT),
                new BloccoPieno(schermo, 4, 0, TextColor.ANSI.CYAN_BRIGHT));
        tipoPezzo = 6;
        rotazione = 1;
    }

}
