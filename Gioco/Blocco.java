import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.lang.reflect.GenericDeclaration;

public class Blocco {
    TextGraphics quadrato;
    int larghezza=3;
    int altezza=2;
    TextGraphics schermo;
    TextColor colore;
    int col;
    int rig;
    int stato=3; //0=Vuoto - 1=Pezzo che sta scendendo - 2=Struttura

    public Blocco(TextGraphics schermo, int col, int rig, TextColor colore){
        this.schermo=schermo;
        schermo.setForegroundColor(colore);
        this.col=col;
        this.rig=rig;
        quadrato=schermo.fillRectangle(new TerminalPosition(col, rig), new TerminalSize(larghezza, altezza), Symbols.BLOCK_SOLID);

    }

    public void cambiaColore(TextColor colore){
        schermo.setBackgroundColor(colore);
    }

    public int getPosizioneRiga(){
        return rig;
    }

    public int getPosizioneColonna(){
        return col;
    }

    public int getStato(){
        return stato;
    }

    public void setStato(){

    }

}
