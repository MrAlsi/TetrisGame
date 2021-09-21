package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import static com.googlecode.lanterna.Symbols.BLOCK_SPARSE;

public class Mini_BloccoSpazzatura extends Mini_Blocco {
    public Mini_BloccoSpazzatura(TextGraphics screen, int colGriglia, int rigGriglia,  int scostamento){
        super(screen,
                colGriglia,             //Colonna nella griglia
                rigGriglia,             //Riga nella griglia
                TextColor.ANSI.RED,     //Colore del pezzo che sta scendendo
                BLOCK_SPARSE,           //Feature del blocco
                scostamento);           //Lo scostamento è lo scostamento dal margine sinistro dello schermo, a seconda
                                        //dello scostamento sai in che campo devi cambiare
        stato=3;
    }
}
