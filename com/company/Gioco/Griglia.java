package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;
import static com.company.Gioco.Schermo.*;

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

    /**
     * @param riga
     * fa cadere la struttura dalla riga passata come parametro
     */
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
     * Come prima cosa alza il pezzo, non posso alzare semplicemente i blocchi perché al prossimo timer il pezzo cadrà
     * da dov'era prima, quindi bisogna alzare proprio il pezzo.
     * Dopo di chè controllo alzo ogni pezzo delle righe, a meno ché il blocco dove finirà c'è già un bloccoPieno
     * Per ogni pezzo faccio un controllo che andando su non causi un errore andando fuori dalla struttura, ma per i
     * blocchi della struttura e spazzatura nel caso "sforino" la griglia si ha perso, quindi richiamo il metodo HaiPerso.
     * Alla fine di tutti aggiungo le righe spazzatura in basso.
     */
    public void aggiungiSpazzatura(int righeSpazzatura){
        //Alza il pezzo, non posso trattarlo come un blocco normale perché altrimenti, al brickDropTimer scenderebbe da
        //dov'era prima
        pezzoScelto.alzaPezzo(righeSpazzatura);

        for(int i = 0; i < 24; i++){
            for(int j = 0; j < 12; j++) {
                    switch (griglia[j][i].stato) {
                        //Devo alzare un blocco vuoto
                        case 0: {
                            if (!(i - righeSpazzatura < 0) && griglia[j][i - righeSpazzatura].getStato()!=1) //Controllo che non vada oltre la griglia per evitare un errore
                                griglia[j][i - righeSpazzatura] = new BloccoVuoto(screen,
                                        griglia[j][i - righeSpazzatura].colonnaGriglia,
                                        griglia[j][i - righeSpazzatura].rigaGriglia);
                            break;
                        }

                        //Devo alzare un blocco della struttura
                        case 2: {
                            if (i - righeSpazzatura < 0) { //Controllo che se va oltre la griglia, nel caso ho perso
                                haiPerso();
                                break;
                            } else if(griglia[j][i - righeSpazzatura].getStato()!=1) {
                                        griglia[j][i - righeSpazzatura] = new BloccoStruttura(screen,
                                        griglia[j][i - righeSpazzatura].colonnaGriglia,
                                        griglia[j][i - righeSpazzatura].rigaGriglia);
                            }
                            break;
                        }

                        //Devo alzare della spazzatura
                        case 3: {
                            if (i - righeSpazzatura < 0) { //Controllo che se va oltre la griglia, nel caso ho perso
                                haiPerso();
                                break;
                            } else if(griglia[j][i - righeSpazzatura].getStato()!=1){
                                griglia[j][i - righeSpazzatura] = new BloccoSpazzatura(screen,
                                        griglia[j][i - righeSpazzatura].colonnaGriglia,
                                        griglia[j][i - righeSpazzatura].rigaGriglia);
                            }
                            break;
                        }
                    }
                    //Disegno le nuove righe spazzatura in fondo
                    if (i >= (23 - righeSpazzatura)) {
                        griglia[j][i] = new BloccoSpazzatura(screen, griglia[j][i].colonnaGriglia, griglia[j][i].rigaGriglia);
                    }
            }
        }
    }
}
