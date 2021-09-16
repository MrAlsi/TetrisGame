package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static com.googlecode.lanterna.Symbols.BLOCK_SOLID;

public class BloccoVuoto extends Blocco{
    public BloccoVuoto(TextGraphics screen, int col, int rig) {
        super(screen, col, rig, TextColor.ANSI.BLACK_BRIGHT, BLOCK_SOLID);
        stato = 0;
    }
}
