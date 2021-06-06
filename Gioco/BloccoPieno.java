import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class BloccoPieno extends Blocco{
    int velocita=600;

    public BloccoPieno(TextGraphics screen, int col, int rig) {
        super(screen, col, rig, TextColor.ANSI.BLUE);

    }

    public void gravita(Griglia campo, int colonna, int riga){

        try {
            Thread.sleep(velocita);
            if(campo.griglia[colonna][riga+1].getStato()==2){
                System.out.println("OoooOoOOOooook");
                campo.griglia[colonna][riga]=new BloccoStruttura(schermo, campo.griglia[colonna][riga].getPosizioneColonna(), campo.griglia[colonna][riga].getPosizioneRiga());

            } else {
                //Metto la casella dove sono stato come blocco vuoto
                campo.griglia[colonna][riga] = new BloccoVuoto(schermo, campo.griglia[colonna][riga].getPosizioneColonna(), campo.griglia[colonna][riga].getPosizioneRiga());

                //Riempo la successiva con un bloccoPieno
                campo.griglia[colonna][riga + 1] = new BloccoPieno(schermo, campo.griglia[colonna][riga+1].getPosizioneColonna(), campo.griglia[colonna][riga+1].getPosizioneRiga());
                System.out.println(riga);
            }

            //System.out.println("ma");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cambiaColore(){

    }

    public void aumentaVelocita(){
        velocita=velocita-100;
    }




}
