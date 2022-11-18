package algo3;

import java.util.ArrayList;

public class Colision {
    Bola bola;

    public Colision(Bola bola) {
        this.bola = bola;
    }

    public void colisionBolaPaleta(Paleta paleta) {
        double[] posBola = this.bola.posicion();
        int[] posPaleta = paleta.posicion(); // 0 = posX, 1 = posY
        int[] dimPaleta = paleta.altoAncho(); // 0 = alto, 1 = ancho
   
        int posIzqPal = posPaleta[0] - (dimPaleta[1] / 2);
        int posDerPal = posPaleta[0] + (dimPaleta[1] / 2);
        int posSupPal = posPaleta[1] + (dimPaleta[0] / 2);
        int posInfPal = posPaleta[1] - (dimPaleta[0] / 2);

        if (this.bola.posInf() == posSupPal && posBola[0] >= posIzqPal && posBola[0] <= posDerPal) {
            // Choque en la parte superior
            // TODO: implementar mejor rebote
            //double distanciaAlCentro = posBola[0] - posPaleta[0];
            //double nuevaDirX = distanciaAlCentro / posDerPal;
            int parteIzqMitad = posPaleta[0] - (dimPaleta[1] / 5);
            int parteDerMitad = posPaleta[0] + (dimPaleta[1] / 5);
            double dirY = this.bola.verDireccion()[1];

            if (posBola[0] >= parteIzqMitad && posBola[0] <= parteDerMitad) {
                // Impacta cerca de la mitad de la paleta
                this.bola.modificarDireccion(0.0, -dirY); 
            } else if (posBola[0] < parteIzqMitad) {
                // Impacta en la parte izq
                this.bola.modificarDireccion(-1.0, -dirY); 
            } else if (posBola[0] > parteDerMitad) {
                // Impacta en la parte der
                this.bola.modificarDireccion(1.0, -dirY); 
            }       
        } else if ((this.bola.posIzq() == posDerPal || this.bola.posDer() == posIzqPal)
            && posBola[1] >= posInfPal && posBola[1] <= posSupPal) {
            // Choque lateral
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
        }
    }

    public boolean contactoBolaBloque(int[] posicion, int[] altoAncho) {
        double[] posBola = this.bola.posicion(); // 0 = posX, 1 = posY

        int posIzqBloque = posicion[0] - altoAncho[1];
        int posDerBloque = posicion[0] + altoAncho[1];
        int posSupBloque = posicion[1] + altoAncho[0];
        int posInfBloque = posicion[1] - altoAncho[0];

        // Todas las posibles combinaciones de colisión bola-bloque
        if ((this.bola.posSup() == posInfBloque || this.bola.posInf() == posSupBloque)
            && posBola[0] > posIzqBloque && posBola[0] < posDerBloque) {
            // Choque en la parte superior / inferior
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
            return true;
        } else if ((this.bola.posIzq() == posDerBloque || this.bola.posDer() == posIzqBloque)
            && posBola[1] > posInfBloque && posBola[1] < posSupBloque) {
            // Choque lateral
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
            return true;
        }
        return false;
    }

    public void colisionBolaBloque(FabricaDeBloques fabrica) {
        ArrayList<Object> listaBloques = fabrica.listaBloques();
        
        if (listaBloques == null) {
            // No se creó el nivel o no hay bloques
            return;
        }
        for (int i = 0; i < listaBloques.size(); i++) {
            Object objBloque = listaBloques.get(i);

            if (objBloque.getClass() == Bloque.class) {
                // El objeto golpeado es un bloque común
                Bloque bloque = (Bloque)objBloque;

                if (!contactoBolaBloque(bloque.posicion(), bloque.altoAncho())) {
                    continue;
                }    
                bloque.golpear();
                //breakout.subirPuntuacion(bloque.puntuacion());

                if (!bloque.estado()) {
                    // El bloque fue destruido
                    listaBloques.remove(i);
                }
            } else if (objBloque.getClass() == BloqueInvisible.class) {
                // El objeto golpeado es un bloque invisible
                BloqueInvisible bloqueInv = (BloqueInvisible)objBloque;

                if (!contactoBolaBloque(bloqueInv.posicion(), bloqueInv.altoAncho())){
                    continue;
                }
                bloqueInv.golpear();
                listaBloques.set(i, bloqueInv.reemplazarBloque());
            }
        }
    }

    public void colisionBolaPared(int paredSup, int paredDer) {
        /* int[] paredSupDer = breakout.altoAncho(); // 0 = paredSup, 1 = paredDer
      
        if (this.posInfBola == 0) {
            breakout.reducirVida();
            breakout.reiniciar();
        } */
        if (this.bola.posIzq() == 0.0 || this.bola.posDer() == paredDer) {
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
        } else if (this.bola.posSup() == paredSup) {
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
        }
    }
}