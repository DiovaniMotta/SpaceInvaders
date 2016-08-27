/*
 * Essa classe representa O Jogador no mundo dos objetos.
 * Terá como objetivo,armazenar suas caracteristicas, como nome,pontuação,e quantidade de vidas
 */
package com.Java2D.Entity;

/**
 *
 * @author Diovani Bernardi da Motta
 */
public class Jogador {
    // VARIAVEIS DE INSTÂNCIA DA CLASSE

    // NOME DO JOGADOR
    private String nomePlayer;
    // QUANTIDADE DE VIDAS
    private int lives = 3;
    // PRONTUACAO DO JOGADOR
    private int score = 0;

    // CONSTRUTORES
    public Jogador() {
    }

    public Jogador(String nomePlayer, int lives, int score) {
        this.nomePlayer = nomePlayer;
        this.lives = lives;
        this.score = score;
    }

    // METODOS RESPONSÁVEIS POR ENCAPSULAR AS VARIAVEIS DE INSTANCIA
    public String getNomePlayer() {
        return nomePlayer;
    }

    public void setNomePlayer(String nomePlayer) {
        this.nomePlayer = nomePlayer;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives += lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void diminuirLive(int live) {
        this.lives -= live;
    }
    
   /**
    * 
    * @param zero é responsável por zerar o valor das variaveis 
    * 
    */ 
    public void zerarScore(int zero){
        this.score = zero;
    }  
    /**
     * 
     * @param zero é responsavel por zerar as vidas  do Jogador.
     */
    public void zerarLive(int zero){
        this.lives = zero;
    }
}
