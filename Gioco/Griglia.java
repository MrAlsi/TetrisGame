import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Griglia {
    Blocco[][] griglia;
    TextGraphics screen;

    public Griglia(TextGraphics screen){
        this.screen=screen;
        griglia=new Blocco[12][24];
    }

    public void CreazioneCampo(){
        for(int i=0; i<12; i++){
            for(int e=0; e<24; e++){
                griglia[i][e]=new BloccoVuoto(screen, i*5, e*3);
            }
        }
    }

}
