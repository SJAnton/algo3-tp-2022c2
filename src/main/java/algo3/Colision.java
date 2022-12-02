package algo3;

import java.util.ArrayList;

public class Colision {
    Bola bola;

    public Colision(Bola bola) {
        this.bola = bola;
    }

    public void colisionBolaPaleta(Paleta paleta) {
        int[] posPaleta = paleta.posicion(); // 0 = posX, 1 = posY
        int[] dimPaleta = paleta.altoAncho(); // 0 = alto, 1 = ancho
   
        int posIzqPal = posPaleta[0] - (dimPaleta[1] / 2);
        int posDerPal = posPaleta[0] + (dimPaleta[1] / 2);
        int posSupPal = posPaleta[1] + (dimPaleta[0] / 2);
        int posInfPal = posPaleta[1] - (dimPaleta[0] / 2);

        if (this.bola.posInf() == posSupPal && this.bola.posX() >= posIzqPal && this.bola.posX() <= posDerPal) {
            // Choque en la parte superior
            // TODO: implementar mejor rebote
            int parteIzqMitad = posPaleta[0] - (dimPaleta[1] / 5);
            int parteDerMitad = posPaleta[0] + (dimPaleta[1] / 5);
            double dirY = this.bola.verDireccion()[1];

            if (this.bola.posX() >= parteIzqMitad && this.bola.posX() <= parteDerMitad) {
                // Impacta cerca de la mitad de la paleta
                this.bola.modificarDireccion(0.0, -dirY); 
            } else if (this.bola.posX() < parteIzqMitad) {
                // Impacta en la parte izq
                this.bola.modificarDireccion(-1.0, -dirY); 
            } else if (this.bola.posX() > parteDerMitad) {
                // Impacta en la parte der
                this.bola.modificarDireccion(1.0, -dirY); 
            }       
        } else if ((this.bola.posIzq() == posDerPal || this.bola.posDer() == posIzqPal)
            && this.bola.posY() >= posInfPal && this.bola.posY() <= posSupPal) {
            // Choque lateral
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
        }
    }

    public boolean contactoBolaBloque(int[] posicion, int[] altoAncho) {
        int posIzqBloque = posicion[0] - altoAncho[1];
        int posDerBloque = posicion[0] + altoAncho[1];
        int posSupBloque = posicion[1] + altoAncho[0];
        int posInfBloque = posicion[1] - altoAncho[0];

        // Todas las posibles combinaciones de colisión bola-bloque
        if ((this.bola.posSup() == posInfBloque || this.bola.posInf() == posSupBloque)
            && this.bola.posX() > posIzqBloque && this.bola.posX() < posDerBloque) {
            // Choque en la parte superior / inferior
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
            return true;
        } else if ((this.bola.posIzq() == posDerBloque || this.bola.posDer() == posIzqBloque)
            && this.bola.posY() > posInfBloque && this.bola.posY() < posSupBloque) {
            // Choque lateral
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
            return true;
        }
        return false;
    }

    public void colisionBolaBloque(Breakout breakout, FabricaDeBloques fabrica) {
        ArrayList<Object> listaBloques = fabrica.listaBloques();
        
        if (listaBloques == null) {
            // No se creó el nivel o no hay bloques
            return;
        }
        for (int i = 0; i < listaBloques.size(); i++) {
            Object objBloque = listaBloques.get(i);

            if (objBloque.getClass() == BloqueComun.class) {
                // El objeto golpeado es un bloque común
                BloqueComun bloque = (BloqueComun)objBloque;

                if (!contactoBolaBloque(bloque.posicion(), bloque.altoAncho())) {
                    continue;
                }    
                bloque.golpear();

                if (!bloque.estado()) {
                    // El bloque fue destruido
                    listaBloques.remove(i);

                    if (breakout == null) {
                        // Breakout es null en las pruebas de colisiones
                        return;
                    }
                    breakout.subirPuntuacion(bloque.puntuacion());
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

    public void colisionBolaPared(Breakout breakout, int paredSup, int paredDer) {      
        if (this.bola.posInf() == 0) {
            breakout.reducirVida();
            breakout.reiniciar();
        } else if (this.bola.posIzq() == 0.0 || this.bola.posDer() == paredDer) {
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
        } else if (this.bola.posSup() == paredSup) {
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
        }
    }
}