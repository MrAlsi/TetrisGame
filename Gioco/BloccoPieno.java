package com.company.Gioco;

import com.googlecode.lanterna.TextColor;


import com.googlecode.lanterna.graphics.TextGraphics;

public class BloccoPieno extends Blocco{
    public static int velocita = 1000;

    public BloccoPieno(TextGraphics screen, int colTerminale, int rigTerminale, TextColor colore) {
        super(screen, colTerminale, rigTerminale, colore);
        stato=1;
    }

    public void cambiaColore(){ }

    //La velocità aumenta quando
    public void aumentaVelocita(){
        velocita=velocita-100;
    }

        //orizzontale: +1: sposta a destra  -1:sposta a sinistra
        //Verticale: +1 scende      -1:sale

    @Override
    public void muovi(Griglia campo, int colGriglia, int rigGriglia, int orizzontale, int verticale){

        //Riempo la successiva con un bloccoPieno
        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale] =
                new BloccoPieno(schermo,
                        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale].getColonna(),
                        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale].getRiga());

    }

    public void rimuovi(Griglia campo, int colGriglia, int rigGriglia){
        //Metto la casella dove sono stato come blocco vuoto
        campo.griglia[colGriglia][rigGriglia] = new BloccoVuoto(schermo, colGriglia, rigGriglia);
    }

    //Gestione delle collisioni
    public boolean collisioneLaterale(Griglia campo, int x, int y, int spostamento){
        if(x==0 || x==11 || campo.griglia[x+spostamento][y].getStato() == 2){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean collisioneSotto(Griglia campo){
        return super.collisioneSotto(campo);
    }

}
