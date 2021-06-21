public class Pezzo {
    public Blocco[] pezzo;
    Griglia campo;

    public Pezzo(Griglia campo, BloccoPieno b0, BloccoPieno b1, BloccoPieno b2, BloccoPieno b3) {
        pezzo = new Blocco[4];
        pezzo[0] = b0;
        pezzo[1] = b1;
        pezzo[2] = b2;
        pezzo[3] = b3;
        this.campo = campo;
    }

    public void ruota() {

    }

    public boolean scendi(Griglia campo, int orizzontale, int verticale) {
        //System.out.println(pezzo[0].getPosizioneGrigliaColonna()+"  --  "+ pezzo[0].getPosizioneGrigliaRiga());
        if (collisioneSotto()) {
            for (int i = 0; i < 4; i++) {
                int y=pezzo[i].rigaGriglia;
                int x=pezzo[i].colonnaGriglia;
                pezzo[i] = new BloccoStruttura(campo.screen, x, y);
            }
            return true;
        } else {
            for (int i = 0; i < 4; i++) {
                pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), 0, 1);
                pezzo[i].sceso();
            }

            return false;
        }
    }

    public boolean collisioneSotto() {

        boolean collide = pezzo[0].collisioneSotto(campo) &&
                pezzo[1].collisioneSotto(campo) &&
                pezzo[2].collisioneSotto(campo) &&
                pezzo[3].collisioneSotto(campo);

        return collide;
    }
}
