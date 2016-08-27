/*
 * Essa é a classe responsável por registrar o movimento do projetil lançado pela nave 
 * ate o Grupo de EBE's
 * 
 */
package com.Java2D.Movimento;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Diovani Bernardi da Motta
 */
public class Projetil {

    // VARIAVEIS QUE REPRESENTAM A POSICAO DO OBJETO NA TELA
    private int eixoX = 0;
    private int eixoY = 0;
    // VARIAVEL QUE CONTROLA SE O OBJETO DESSA CLASSE DEVE SER EXIBIDO NA JANELA OU NAO
    private boolean colidiu = false;

    public void draw(Graphics2D g2, int eixoX, int eixoY, Color c) {
        // SE O VALOR DA VARIAVEL FOR IGUAL A ZERO

        if (this.eixoX == 0 && colidiu == false) {
            // ATRIBUI O VALOR A VARIAVEL DE INSTANCIA  
            this.eixoX = eixoX;
        }
        // SE A VARIAVEL FOR ZERO, ELE ATRIBUI O VALOR
        if (this.eixoY == 0 && colidiu == false) {
            // ATRIBUI O VALOR A VARIAVEL DE INSTANCIA
            this.eixoY = eixoY - 20;
        }
        // SETA A COR UTILIZADA PARA A PINTURA DO COMPONENTE;
        g2.setColor(c);

        if (this.eixoY >= 0 && colidiu == false) {
            // DESENHA A FÓRMULA GEOMETRICA NA TELA DE UM RETANGULO
            g2.fillRect(this.eixoX, this.eixoY, 15, 15);
        }
        // DECREMENTO A VARIAVEL PARA MOVIMENTAR O OBJETO 
        this.eixoY -= 20;
        
        // SE HOUVER COLISAO ENTRE EBE E PROJETIL É REINICIADA AS POSIÇOES DO PROJETIL
        if(colidiu){
            this.eixoX = 0;
            this.eixoY = 0;
        }
    }

    /* GETTERS AND SETTERS DAS VARIAVEIS DE INSTANCIA */
    public int getEixoX() {
        return eixoX;
    }

    public void setEixoX(int eixoX) {
        this.eixoX = eixoX;
    }

    public int getEixoY() {
        return eixoY;
    }

    public void setEixoY(int eixoY) {
        this.eixoY = eixoY;
    }

    public boolean isColidiu() {
        return colidiu;
    }

    public void setColidiu(boolean colidiu) {
        this.colidiu = colidiu;
    }
}
