package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static com.googlecode.lanterna.Symbols.BLOCK_SOLID;

public class Mini_BloccoPieno extends Mini_Blocco {
    public Mini_BloccoPieno(TextGraphics screen, int colGriglia, int rigGriglia, TextColor colore, int scostamento){
        super(screen,
                colGriglia,         //Colonna nella griglia
                rigGriglia,         //Riga nella griglia
                colore,             //Colore del pezzo che sta scendendo
                BLOCK_SOLID,        //Feature del blocco
                scostamento);       //Lo scostamento è lo scostamento dal margine sinistro dello schermo, a seconda
        //dello scostamento sai in che campo devi cambiare
        stato=1;
    }
}
