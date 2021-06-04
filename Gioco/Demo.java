import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import java.security.KeyStore;

public class Demo {
    public static void main(String[] args) throws IOException, InterruptedException {
        Terminal terminal=new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);

        TextGraphics tg=screen.newTextGraphics();
        TextGraphics campo=screen.newTextGraphics();

        screen.startScreen();

        //creazione campo
        campo.setForegroundColor(TextColor.ANSI.WHITE);
        for(int i=2; i<200;i++) {
            campo.putString(15, i, String.valueOf(Symbols.BLOCK_MIDDLE));       //lato sinistro
            campo.putString(60, i, String.valueOf(Symbols.BLOCK_MIDDLE));       //lato destro
            campo.putString(i, 23, String.valueOf(Symbols.BLOCK_MIDDLE));      //pavimento

            screen.refresh();
        }

        //disegno un rettangolo
        /*tg.setForegroundColor(TextColor.ANSI.RED);
        TextGraphics a=tg.drawRectangle(new TerminalPosition(25, 0), new TerminalSize(3, 4), Symbols.BLOCK_SOLID);
        */
        Pezzo nuovoPezzo = new Pezzo(screen);
        TextGraphics a=nuovoPezzo.getPezzo();
        for(int i=0; i<23; i++) {
            /*nuovoPezzo.gravita();
            a.enableModifiers();*/

            tg.drawRectangle(new TerminalPosition(25, i), new TerminalSize(3, 4), Symbols.BLOCK_SOLID);
            Thread.sleep(100);

            screen.refresh();

        }

        screen.stopScreen();
    }
}
