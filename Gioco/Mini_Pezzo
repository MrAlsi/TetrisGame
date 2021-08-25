package com.company.Gioco;

public class Mini_Pezzo{

    Mini_Griglia campo;
    Mini_Blocco[] miniPezzo;

    public Mini_Pezzo(Mini_Griglia campo, Mini_BloccoPieno b0, Mini_BloccoPieno b1, Mini_BloccoPieno b2, Mini_BloccoPieno b3){
        miniPezzo = new Mini_Blocco[4];
        miniPezzo[0] = b0;
        miniPezzo[1] = b1;
        miniPezzo[2] = b2;
        miniPezzo[3] = b3;
        this.campo = campo;
    }

    public boolean scendi(Mini_Griglia campo, int scostamento) {
        for (int i = 0; i < 4; i++) {
            miniPezzo[i].rimuovi(campo, miniPezzo[i].getColonna(), miniPezzo[i].getRiga());
        }
        for (int i = 0; i < 4; i++) {
            miniPezzo[i].muovi(campo, miniPezzo[i].getColonna(), miniPezzo[i].getRiga(), 0, 1, miniPezzo[i].colore, scostamento);
            miniPezzo[i].rigaGriglia = miniPezzo[i].getRiga() + 1;
        }
        return false;
    }


    public void muovi(Mini_Griglia campo, int orizzontale, int scostamento){
        if(orizzontale==1){
            for (int i = 3; i >= 0; i--) {
                miniPezzo[i].rimuovi(campo, miniPezzo[i].getColonna(), miniPezzo[i].getRiga());

            }
            for (int i = 3; i >= 0; i--) {
                miniPezzo[i].muovi(campo, miniPezzo[i].getColonna(), miniPezzo[i].getRiga(), orizzontale, 0, miniPezzo[i].colore, scostamento);
                miniPezzo[i].colonnaGriglia = miniPezzo[i].getColonna() + 1;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                miniPezzo[i].rimuovi(campo, miniPezzo[i].getColonna(), miniPezzo[i].getRiga());
            }
            for (int i = 0; i < 4; i++) {
                miniPezzo[i].muovi(campo, miniPezzo[i].getColonna(), miniPezzo[i].getRiga(), orizzontale, 0, miniPezzo[i].colore, scostamento);
                miniPezzo[i].colonnaGriglia = miniPezzo[i].getColonna() - 1;
            }
        }
    }
}
