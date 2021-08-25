package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_PezzoLungo extends Mini_Pezzo{
    public Mini_PezzoLungo(TextGraphics schermo, Mini_Griglia campo) {
        super(campo,new Mini_BloccoPieno(schermo, 3, 0, TextColor.ANSI.RED_BRIGHT),
                new Mini_BloccoPieno(schermo, 4, 0, TextColor.ANSI.RED_BRIGHT),
                new Mini_BloccoPieno(schermo, 5, 0, TextColor.ANSI.RED_BRIGHT),
                new Mini_BloccoPieno(schermo, 6, 0, TextColor.ANSI.RED_BRIGHT));
       // tipoPezzo = 0;
       // rotazione = 1;

    }

}
