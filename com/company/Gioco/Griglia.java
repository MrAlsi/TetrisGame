package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

/**
 * La classe griglia è la classe che crea e gestisce il campo da gioco, è costituita da una griglia 12x24 di @Blocco
 * NELLA GRIGLIA
 * Colonne  ||||
 * Righe    ====
 */
public class Griglia {
    Blocco[][] griglia;
    TextGraphics screen;

    public Griglia(TextGraphics screen) {
        this.screen = screen;
        griglia = new Blocco[12][24];
    }


    /**
     * Metodo che crea un campo costituito da BlocchiVuoti
     */
    public void creaCampo() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 24; j++) {
                griglia[i][j] = new BloccoVuoto(screen, i, j);
            }
        }
    }

    /**
     * Il metodo controlloRighe controlla tutte le righe partendo dal alto e se vede che sono tutti Blocchi della struttura
     * la segna da eliminare (elimina=true).
     * Entra nell'if dove:
     * - Viene incrementata Combo, necessaria per le righe spazzatura
     * - Elimina la riga
     * - Fa cadere tutta la struttura sopra
     *
     * @return combo, ovvero il totale di righe eliminate
     */

    public int controlloRighe(){
        int combo = 0;                  //Combo per le riga spazzatura
        boolean elimina = false;        //Se non entra mai nel if viene segnata come riga da eliminare

        for(int i = 0; i < 24; i++){
            for(int j = 0; j<12; j++){
                if(griglia[j][i].stato!=2){     //Se trova una sola cassella vuota (!=2) quella riga non si deve eliminare
                    elimina=false;
                    break;
                }
                elimina=true;
            }
            if(elimina) {
                combo++;                        //Incrementa combo
                eliminaRiga(i);                 //Elimina le righe che gli passano
                cadutaStruttura(i);             //Fa cadere la struttura dalla riga che ha eliminato
            }
        }
        return combo;
    }

    /**
     * @param riga da eliminare
     * Rende la riga tutta di blocchi vuoti
     */
    public void eliminaRiga(int riga){
        for(int i=0; i<12; i++){
            griglia[i][riga] = new BloccoVuoto(screen, griglia[i][riga].colonnaGriglia, griglia[i][riga].rigaGriglia);
        }
    }

    public void cadutaStruttura(int riga){
        for(int i = riga - 1; i >= 0; i--){
            for(int j = 0; j < 12; j++){
                if(griglia[j][i].stato==2) {
                    griglia[j][i] = new BloccoVuoto(screen,
                            griglia[j][i].colonnaGriglia,
                            griglia[j][i].rigaGriglia);

                    griglia[j][i + 1] = new BloccoStruttura(screen,
                            griglia[j][i + 1].colonnaGriglia,
                            griglia[j][i + 1].rigaGriglia);
                }
            }
        }
    }

    /**
     * @param righeSpazzatura
     */
    public void aggiungiSpazzatura(int righeSpazzatura){
        for(int i = 4; i < 24; i++){
            for(int j = 0; j < 12; j++){
                if(griglia[j][i].stato==2 && i >= righeSpazzatura - 1) {
                    griglia[j][i - righeSpazzatura] = new BloccoStruttura(screen,
                            griglia[j][i - righeSpazzatura].colonnaGriglia,
                            griglia[j][i - righeSpazzatura].rigaGriglia);
                }
                if(griglia[j][i].stato==0 && i >= righeSpazzatura - 1) {
                    griglia[j][i - righeSpazzatura] = new BloccoVuoto(screen,
                            griglia[j][i - righeSpazzatura].colonnaGriglia,
                            griglia[j][i - righeSpazzatura].rigaGriglia);
                }
                if(griglia[j][i].stato==3 && i >= righeSpazzatura - 1) {
                    griglia[j][i - righeSpazzatura] = new BloccoSpazzatura(screen,
                            griglia[j][i - righeSpazzatura].colonnaGriglia,
                            griglia[j][i - righeSpazzatura].rigaGriglia);
                }
                if(i >= (23 - righeSpazzatura)){
                    griglia[j][i] = new BloccoSpazzatura(screen,
                            griglia[j][i].colonnaGriglia,
                            griglia[j][i].rigaGriglia);
                }
            }
        }
    }

    //Un po' una maialata, però cosi si perde
    public boolean sconfitta(){
        if(griglia[4][0].stato>1||griglia[5][0].stato>1||griglia[6][0].stato>1||griglia[3][0].stato>1){
            return true;
        }
        return false;
    }
}
