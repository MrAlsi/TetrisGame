import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Pezzo {
    String nome;
    TextGraphics forma;
    TextGraphics s;
    TextColor color;
    int colonna=25;
    int riga=0;

    public Pezzo(Screen screen){
        nome="Quadrato";
        TextGraphics s = screen.newTextGraphics();
        s.setForegroundColor(TextColor.ANSI.RED);
        forma=s.drawRectangle(new TerminalPosition(colonna, riga), new TerminalSize(3, 4), Symbols.BLOCK_SOLID);
    }

    public TextGraphics getPezzo(){
        return forma;
    }

    public void gravita(){
        riga++;
        forma=s.drawRectangle(new TerminalPosition(colonna, riga), new TerminalSize(3, 4), Symbols.BLOCK_SOLID);
    }
}