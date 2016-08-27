/*
 * Essa é a classe que executa o Aplicativo
 * Essa classe extende de JFrame.
 */
package com.Java2D.Main;

import com.Java2D.Entity.Jogador;
import com.Java2D.Paineis.PainelGame;
import com.Java2D.Paineis.PainelSuperior;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author Diovani Bernardi da Motta
 */
public class Janela extends JFrame implements KeyListener {

    private PainelSuperior superior;
    private PainelGame game;
    private Jogador player;
    // A VARIAVEL ESPACO_TECLADO FOI DECLARADA PORQUE EM MEU NOTEBOOK UM EVENTO DO TIPO KEYEVENT RETORNAVA O VALOR 32, E 
    // NO QUAL DENTRO DA CLASSE CORRESPONDENTE ELE POSSUI VALOR 8, SENDO ASSIM NÃO ERA POSSIVEL COMPARAR A ACAO DESTE.
    private static final int ESPACO_TECLADO = 32;

    public Janela() {
        //CHAMA O CONSTRUTOR DA SUPER CLASSE JFRAME
        super("Space Invaders - Programação de Aplicativos I - Engenharia da Computação - Diovani Bernardi da Motta");
        // CAPTURO O CONTAINER DO JFRAME
        Container c = this.getContentPane();
        //SETO UM LAYOUT PARA O CONTAINER, COMO LAYOUT DE BORDA
        c.setLayout(new BorderLayout());

        //INICIALIZO O OBJETO PLAYER DA CLASSE JOGADOR
        player = new Jogador();

        //INICIALIZO O OBJETO DA CLASSE PAINELGAME
        game = new PainelGame(player);
        // ADICIONO AO CONTAINER NA POSICAO CENTRAL
        c.add(game, BorderLayout.CENTER);

        // INICIALIZO O OBJETO DO TIPO PAINELSUPERIOR
        superior = new PainelSuperior(game, this, player);
        //ADICIONO  AO CONTAINER NA POSICAO NORTE
        c.add(superior, BorderLayout.NORTH);

        // SETO OPERACAO DE FECHAMENTO DA JANELA
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // SETO A JANELA COMO VISIVEL AO USUARIO
        this.setVisible(true);
        // INFORMO QUE OS EVENTOS DE TECLADO SERAO TRATADOS POR ESTA CLASSE
        this.addKeyListener(this);
        // INFORMO QUE A JANELA DEVE SER EXPANDIDA PARA A RESOLUCAO MAXIMA DO MONITOR
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Método Main: Metodo responsável por iniciar a Thread principal do
     * programa
     *
     */
    public static void main(String[] args) {
        new Janela();
    }

    /**
     * Os métodos a seguir são responsáveis por tratar os eventos de teclado que
     * ocorram. Sua implementação é simple,sendo que as rotinas que são
     * empregadas são, faz-se uma verificação para ver se o evento recebido no
     * parametro dos métodos
     * <code>KeyEvent</code> é igual a uma determinada tecla do teclado, se for
     * se emprega uma determinada rotina, se não nada acontece;
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // SE UM EVENTO DE TECLADO DO TIPO BARRA DE ESPACO ACONTECER
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            // ALTERO O VALOR DA VARIAVEL VALOR NA CLASSE GAME
            game.setaFlag(1);
            // ALTERO O VALOR DA VARIAVEL RESPONSAVEL POR CONTROLAR A PINTURA DO PROJETIL
            this.game.setaValorProjetil(0);
            // REPASSO O FOCO PARA ESSE PAINEL (PAINELGAME)
            game.setFocusable(true);
            //REQUESITO O FOCO  DA JANELA PARA ESSE PANEL (PAINELGAME)
            game.requestFocusInWindow();

        } else if (e.getKeyCode() == ESPACO_TECLADO) {

            // ALTERO O VALOR DA VARIAVEL VALOR NA CLASSE GAME
            game.setaFlag(1);
            // ALTERO O VALOR DA VARIAVEL RESPONSAVEL POR CONTROLAR A PINTURA DO PROJETIL
            this.game.setaValorProjetil(0);
            // REPASSO O FOCO PARA ESSE PAINEL (PAINELGAME)
            game.setFocusable(true);
            //REQUESITO O FOCO  DA JANELA PARA ESSE PANEL (PAINELGAME)
            game.requestFocusInWindow();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
