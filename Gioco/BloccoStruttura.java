import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.lang.reflect.GenericDeclaration;

public class BloccoStruttura extends Blocco{
    public BloccoStruttura(TextGraphics schermo, int col, int rig) {
        super(schermo, col, rig, TextColor.ANSI.GREEN);
        setStato();
    }

    public void setStato() {
        stato=2;
    }
}
