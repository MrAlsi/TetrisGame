import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class BloccoPieno extends Blocco{
    int velocita=600;

    public BloccoPieno(TextGraphics screen, int col, int rig) {
        super(screen, col, rig, TextColor.ANSI.BLUE);
        stato=1;
    }

    //Metodo per far scendere i vari blocchi
    public void gravita(Griglia campo, int colonna, int riga){
        try {
            Thread.sleep(velocita);
            if(campo.griglia[colonna][riga+1].getStato()==2){
                campo.griglia[colonna][riga]=new BloccoStruttura(schermo, campo.griglia[colonna][riga].getPosizioneColonna(), campo.griglia[colonna][riga].getPosizioneRiga());
            } else {
                //Metto la casella dove sono stato come blocco vuoto
                campo.griglia[colonna][riga] = new BloccoVuoto(schermo, campo.griglia[colonna][riga].getPosizioneColonna(), campo.griglia[colonna][riga].getPosizioneRiga());

                //Riempo la successiva con un bloccoPieno
                campo.griglia[colonna][riga + 1] = new BloccoPieno(schermo, campo.griglia[colonna][riga+1].getPosizioneColonna(), campo.griglia[colonna][riga+1].getPosizioneRiga());
                System.out.println(riga);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cambiaColore(){ }

    //La velocità aumenta quando
    public void aumentaVelocita(){
        velocita=velocita-100;
    }


    //Metodo per spostare un blocco a destra
    public int destra(Griglia campo, int colonna, int riga){
        //Metto la casella dove sono stato come blocco vuoto
        campo.griglia[colonna][riga] = new BloccoVuoto(schermo, campo.griglia[colonna][riga].getPosizioneColonna(), campo.griglia[colonna][riga].getPosizioneRiga());

        //Riempo la successiva con un bloccoPieno
        campo.griglia[colonna+1][riga] = new BloccoPieno(schermo, campo.griglia[colonna+1][riga].getPosizioneColonna(), campo.griglia[colonna+1][riga].getPosizioneRiga());
        System.out.println(colonna+" - " +riga);

        //ritorno la nuova colonna
        return colonna+1;
    }

    //Metodo per spostare un blocco a sinistra
    public int sinistra(Griglia campo, int colonna, int riga){
        //Metto la casella dove sono stato come blocco vuoto
        campo.griglia[colonna][riga] = new BloccoVuoto(schermo, campo.griglia[colonna][riga].getPosizioneColonna(), campo.griglia[colonna][riga].getPosizioneRiga());

        //Riempo la successiva con un bloccoPieno
        campo.griglia[colonna-1][riga] = new BloccoPieno(schermo, campo.griglia[colonna-1][riga].getPosizioneColonna(), campo.griglia[colonna-1][riga].getPosizioneRiga());
        System.out.println(colonna+" - " +riga);

        //ritorno la nuova colonna
        return colonna-1;
    }


}
