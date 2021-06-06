import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class BloccoVuoto extends Blocco{
    public BloccoVuoto(TextGraphics screen, int col, int rig) {
        super(screen, col, rig, TextColor.ANSI.DEFAULT);

    }
}
