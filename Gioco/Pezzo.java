import com.googlecode.lanterna.graphics.TextGraphics;

public class Pezzo {
    public Blocco[] pezzo;
    Griglia campo;

    public Pezzo(Griglia campo, BloccoPieno b0, BloccoPieno b1, BloccoPieno b2, BloccoPieno b3){
        pezzo=new Blocco[4];
        pezzo[0]= b0;
        pezzo[1]= b1;
        pezzo[2]= b2;
        pezzo[3]= b3;
        this.campo=campo;
    }

    public void ruota(){

    }

    public boolean scendi(Griglia campo, int orizzontale, int verticale){
        //System.out.println(pezzo[0].getPosizioneGrigliaColonna()+"  --  "+ pezzo[0].getPosizioneGrigliaRiga());
        if(pezzo[0].collisioneSotto(campo, pezzo[0].getPosizioneGrigliaColonna(), pezzo[0].getPosizioneGrigliaRiga()) &&
            pezzo[1].collisioneSotto(campo, pezzo[1].getPosizioneGrigliaColonna(), pezzo[1].getPosizioneGrigliaRiga()) &&
            pezzo[2].collisioneSotto(campo, pezzo[2].getPosizioneGrigliaColonna(), pezzo[2].getPosizioneGrigliaRiga()) &&
            pezzo[3].collisioneSotto(campo, pezzo[3].getPosizioneGrigliaColonna(), pezzo[3].getPosizioneGrigliaRiga())){

            for(int i=0; i<4; i++){
                System.out.println(pezzo[i].getPosizioneGrigliaColonna() + " --- "+ pezzo[i].getPosizioneGrigliaRiga());
                pezzo[i].muovi(campo, pezzo[i].getPosizioneGrigliaColonna(), pezzo[i].getPosizioneGrigliaRiga(), 0, 1);
            }

            return true;
        } else {
            for(int i=0; i<4; i++){
                pezzo[i] = new BloccoStruttura(campo.screen, campo.griglia[orizzontale][verticale].getPosizioneColonna(), campo.griglia[orizzontale+i][verticale].getPosizioneRiga());
            }
            return false;
        }
    }

}
