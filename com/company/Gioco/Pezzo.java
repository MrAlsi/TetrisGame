package com.company.Gioco;

/**
 * Un pezzo è il pezzo che noi vediamo cadere, pezzo è l'insieme dei 4 blocchi pieni, riuniti con un'array[4],
 * un pezzo può essere di diverso tipo (vedi package pezzi)
 *
 */
public class Pezzo {
    public Blocco[] pezzo;
    Griglia campo;
    public int tipoPezzo = 0; // 0 pezzoLungo, 1 pezzoT, 2 pezzoL , 3 pezzoJ,  4 pezzoS, 5 PezzoZ, 6 pezzoQuadrato
    public int rotazione = 0; // 0 posizione iniziale, successivamente in senso orario

    public int maxRotazioni; //numero massimo di rotazioni possibili

    //Le griglie si basano sui numeri i e j, [i][] che fà capire in quel momento
    //il secondo numero [][j] dice dove si sposterà il blocco del pezzo
    public int[][] spostamentoVerticale;
    public int[][] spostamentoOrizzontale;

    public Pezzo(Griglia campo, BloccoPieno b0, BloccoPieno b1, BloccoPieno b2, BloccoPieno b3) {
        pezzo = new Blocco[4];
        pezzo[0] = b0;
        pezzo[1] = b1;
        pezzo[2] = b2;
        pezzo[3] = b3;
        this.campo = campo;
    }

    public void ruota(Griglia campo, int rotazione, int verso){
        
    }

    public void scendi(Griglia campo) {
        for (int i = 0; i < 4; i++) {
            pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga());
            // System.out.println(pezzo[i].getStato());
        }
        for (int i = 0; i < 4; i++) {
            pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), 0, 1, pezzo[i].colore);
            pezzo[i].rigaGriglia = pezzo[i].getRiga() + 1;
            // System.out.println(pezzo[i].getStato());
        }

    }

    public void setStruttura(){
        for (int i = 0; i < 4; i++) {
            int y = pezzo[i].rigaGriglia;
            int x = pezzo[i].colonnaGriglia;
            //pezzo[i] = new BloccoStruttura(campo.screen, x, y);
            //pezzo[i].setStato();
            campo.griglia[x][y] = new BloccoStruttura(campo.screen, x, y);
            //System.out.println(pezzo[i].getStato());
        }
    }

    public void muovi(Griglia campo, int orizzontale, int verticale){
        if(!collisioneMovimento(orizzontale)){ //deve ritornare false per entrare
            for (int i = 3; i >= 0; i--) {
                pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga());
            }
            for (int i = 3; i >= 0; i--) {
                pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), orizzontale, verticale, pezzo[i].colore);
                pezzo[i].colonnaGriglia = pezzo[i].getColonna() + orizzontale;
                pezzo[i].rigaGriglia = pezzo[i].getRiga() + verticale;
            }
        }
    }

    //Controllo laterale e con struttura per spostamento
    public boolean collisioneMovimento(int spostamento){ //Spostamento 1: Destra  -1: Sinistra
        return pezzo[0].collisioneLaterale(campo, spostamento, 0) ||
                pezzo[1].collisioneLaterale(campo, spostamento, 0) ||
                pezzo[2].collisioneLaterale(campo, spostamento, 0) ||
                pezzo[3].collisioneLaterale(campo, spostamento, 0);
    }

    //Controllo laterale e con struttura per rotazione
    public boolean collisioneRotazione(){
        return  pezzo[0].collisioneLaterale(campo, spostamentoOrizzontale[rotazione][0], spostamentoVerticale[rotazione][0]) ||
                pezzo[1].collisioneLaterale(campo, spostamentoOrizzontale[rotazione][1], spostamentoVerticale[rotazione][1]) ||
                pezzo[2].collisioneLaterale(campo, spostamentoOrizzontale[rotazione][2], spostamentoVerticale[rotazione][2]) ||
                pezzo[3].collisioneLaterale(campo, spostamentoOrizzontale[rotazione][3], spostamentoVerticale[rotazione][3]);
}

    public boolean collisioneSotto() {
        return pezzo[0].collisioneSotto(campo)||
                pezzo[1].collisioneSotto(campo) ||
                pezzo[2].collisioneSotto(campo) ||
                pezzo[3].collisioneSotto(campo);
    }

    public void alzaPezzo(int altezza){
        if (pezzo[0].collisioneLaterale(campo, 0, -altezza) ||
                pezzo[1].collisioneLaterale(campo, 0, -altezza) ||
                pezzo[2].collisioneLaterale(campo, 0, -altezza) ||
                pezzo[3].collisioneLaterale(campo, 0, -altezza)) {
            System.out.println("ricorsione");
            alzaPezzo(altezza-1);
        } else {
            muovi(campo, 0, -altezza);
        }
    }
}
