package com.company.Gioco;

public class Pezzo {

    public Blocco[] pezzo;
    Griglia campo;
    int tipoPezzo = 0; // 0 pezzoLungo, 1 pezzoT, 2 pezzoL , 3 pezzoJ,  4 pezzoS, 5 PezzoZ, 6 pezzoQuadrato
    int rotazione = 0; // 0 posizione iniziale, successivamente in senso orario

    public Pezzo(Griglia campo, BloccoPieno b0, BloccoPieno b1, BloccoPieno b2, BloccoPieno b3) {
        pezzo = new Blocco[4];
        pezzo[0] = b0;
        pezzo[1] = b1;
        pezzo[2] = b2;
        pezzo[3] = b3;
        this.campo = campo;
    }

    public void ruota(Griglia campo) {
        switch (tipoPezzo) {
            case 0: // PezzoLungo
                switch (rotazione) {

                    case 0:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 0);
                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 0);
                        pezzo[3].rigaGriglia = pezzo[3].getRiga();
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna();

                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1);
                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1);
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;

                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -2, 2);
                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -2, 2);
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 2;
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 2;

                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -3, 3);
                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -3, 3);
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 3;
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 3;

                        rotazione = 1;

                        break;

                    case 1:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 0);
                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 0);
                        pezzo[3].rigaGriglia = pezzo[3].getRiga();
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna();

                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1);
                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1);
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;

                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 2, -2);
                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 2, -2);
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 2;
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 2;

                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 3, -3);
                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 3, -3);
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 3;
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 3;

                        rotazione = 0;

                        break;
                }
                break;

            case 1:  // PezzoT
                switch (rotazione) {

                    case 0:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                        rotazione = 1;

                        break;

                    case 1:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                        rotazione = 2;

                        break;

                    case 2:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);


                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                        rotazione = 3;

                        break;

                    case 3:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                        rotazione = 0;

                        break;
                }
                break;

            case 2: // PezzoL
                switch (rotazione) {

                    case 0:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 2, 0);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, 1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 2, 0);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 2;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga();

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, 1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                        rotazione = 1;

                        break;

                    case 1:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 2);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 2);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna();
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 2;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                        rotazione = 2;

                        break;

                    case 2:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -2, 0);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, -1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -2, 0);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 2;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga();

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, -1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                        rotazione = 3;

                        break;

                    case 3:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, -2);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, -2);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna();
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 2;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                        rotazione = 0;

                        break;
                }
                break;

            case 3: // PezzoJ
                switch (rotazione) {

                    case 0:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 2);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 2);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna();
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 2;

                        rotazione = 1;

                        break;

                    case 1:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -2, 0);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -2, 0);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 2;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga();

                        rotazione = 2;

                        break;

                    case 2:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, -2);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, -2);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna();
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 2;

                        rotazione = 3;

                        break;

                    case 3:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 2, 0);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 2, 0);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 2;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga();

                        rotazione = 0;

                        break;
                }
                break;

            case 4: // PezzoS
                switch (rotazione) {

                    case 0:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 2, 0);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, -1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 2, 0);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 2;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga();

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, -1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), - 1, - 1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                        rotazione = 1;

                        break;

                    case 1:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 2);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, 1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, 2);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna();
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 2;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 1, 1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, -1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() - 1;

                        rotazione = 2;

                        break;

                    case 2:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -2, 0);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -2, 0);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 2;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga();

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, 1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() + 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 1, 1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() + 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                        rotazione = 3;

                        break;

                    case 3:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, -2);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, -1);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 0, -2);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna();
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 2;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -1, -1);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 1;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() - 1;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 0, 0);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna();
                        pezzo[1].rigaGriglia = pezzo[1].getRiga();

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), -1, 1);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna() - 1;
                        pezzo[0].rigaGriglia = pezzo[0].getRiga() + 1;

                        rotazione = 0;

                        break;
                }
                break;

            case 5: // PezzoZ
                switch (rotazione) {

                    case 0:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, -2);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, -1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, -2);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() - 2;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, 1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna();
                        pezzo[0].rigaGriglia = pezzo[0].getRiga();

                        rotazione = 1;

                        break;

                    case 1:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 2, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), 1, 1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() + 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 2, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() + 2;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, 1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() + 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna();
                        pezzo[0].rigaGriglia = pezzo[0].getRiga();

                        rotazione = 2;

                        break;

                    case 2:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 2);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), -1, -1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, 1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() + 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), 0, 2);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna();
                        pezzo[2].rigaGriglia = pezzo[2].getRiga() + 2;

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), - 1, - 1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() - 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna();
                        pezzo[0].rigaGriglia = pezzo[0].getRiga();

                        rotazione = 3;

                        break;

                    case 3:
                        pezzo[3].rimuovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
                        pezzo[2].rimuovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -2, 0);
                        pezzo[1].rimuovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
                        pezzo[0].rimuovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);

                        pezzo[3].muovi(campo, pezzo[3].getColonna(), pezzo[3].getRiga(), -1, -1);
                        pezzo[3].colonnaGriglia = pezzo[3].getColonna() - 1;
                        pezzo[3].rigaGriglia = pezzo[3].getRiga() - 1;

                        pezzo[2].muovi(campo, pezzo[2].getColonna(), pezzo[2].getRiga(), -2, 0);
                        pezzo[2].colonnaGriglia = pezzo[2].getColonna() - 2;
                        pezzo[2].rigaGriglia = pezzo[2].getRiga();

                        pezzo[1].muovi(campo, pezzo[1].getColonna(), pezzo[1].getRiga(), 1, -1);
                        pezzo[1].colonnaGriglia = pezzo[1].getColonna() + 1;
                        pezzo[1].rigaGriglia = pezzo[1].getRiga() - 1;

                        pezzo[0].muovi(campo, pezzo[0].getColonna(), pezzo[0].getRiga(), 0, 0);
                        pezzo[0].colonnaGriglia = pezzo[0].getColonna();
                        pezzo[0].rigaGriglia = pezzo[0].getRiga();

                        rotazione = 0;

                        break;
                }
                break;
        }
    }

    public boolean scendi(Griglia campo) {
        //System.out.println(pezzo[0].getPosizioneGrigliaColonna()+"  --  "+ pezzo[0].getPosizioneGrigliaRiga());
        if (collisioneSotto()) {
            for (int i = 0; i < 4; i++) {
                int y = pezzo[i].getRiga();
                int x = pezzo[i].getColonna();
                campo.griglia[x][y] = new BloccoStruttura(campo.screen, x, y);
                //pezzo[i] = new BloccoStruttura(campo.screen, x, y);
                //pezzo[i].setStato();
            }
            return true;
        } else {
            for (int i = 0; i < 4; i++) {
                pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), 0, 1);
                // System.out.println(pezzo[i].getStato());
            }
            for (int i = 0; i < 4; i++) {
                pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), 0, 1);
                pezzo[i].rigaGriglia = pezzo[i].getRiga() + 1;
               // System.out.println(pezzo[i].getStato());
            }

            return false;
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

    public void muovi(Griglia campo, int orizzontale){
        if(!collisioneLaterale(orizzontale)){ //deve ritornare false per entrare
            if(orizzontale==1){
                for (int i = 3; i >= 0; i--) {
                    pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), orizzontale, 0);

                }
                for (int i = 3; i >= 0; i--) {
                    pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), orizzontale, 0);
                    pezzo[i].colonnaGriglia = pezzo[i].getColonna() + 1;
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    pezzo[i].rimuovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), orizzontale, 0);

                }
                for (int i = 0; i < 4; i++) {
                    pezzo[i].muovi(campo, pezzo[i].getColonna(), pezzo[i].getRiga(), orizzontale, 0);
                    pezzo[i].colonnaGriglia = pezzo[i].getColonna() - 1;
                }
            }
        }
    }

    public boolean collisioneLaterale(int spostamento){ //Spostamento 1: Destra  -1: Sinistra
        boolean collide = pezzo[0].collisioneLaterale(campo, spostamento) ||
                pezzo[1].collisioneLaterale(campo, spostamento) ||
                pezzo[2].collisioneLaterale(campo, spostamento) ||
                pezzo[3].collisioneLaterale(campo, spostamento);
        return collide;
    }

    public boolean collisioneSotto() {
        boolean collide = pezzo[0].collisioneSotto(campo) == true ||
                pezzo[1].collisioneSotto(campo) == true ||
                pezzo[2].collisioneSotto(campo) == true ||
                pezzo[3].collisioneSotto(campo) == true;
        return collide;
    }
}
