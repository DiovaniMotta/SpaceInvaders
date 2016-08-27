/*
 * Essa Classe tem como funcionalidade, exibir toda a trama do Jogo
 * nela serão exibidos a tela de boas vindas,do jogo,final e os resultados do banco
 * 
 */
package com.Java2D.Paineis;

import com.Java2D.Entity.Ebe;
import com.Java2D.Entity.Jogador;
import com.Java2D.Frame.Windows;
import com.Java2D.Movimento.Projetil;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Diovani Bernardi da Motta
 */
public class PainelGame extends JPanel implements KeyListener {
    // VARIAVEL DE CONTROLE DE MOVIMENTO NO EIXO X

    private int naveEixoX = 600;
    // VARIAVEL DE CONTROLE DE MOVIMENTO NO EIXO Y
    private int naveEixoY = 600;
    // VARIAVEIS QUE CONTROLA A ALTURA E A LARGURA DA TELA
    private int widht, height;
    // OBJETOS DO TIPO LINHA  QUE SÃO DESENHADAS NO INICIO E FINAL DA TELA
    private Line2D linha, linhaInicial;
    //OBJETO DO TIPO EBE QUE É CONTROLADO PELO USUARIO
    private Ebe combate = new Ebe();
    // CONJUNTO DE EBES QUE DEVEM SER COMBATIDOS PELO USUARIO
    private ArrayList<Ebe> lstEbe = new ArrayList<>();
    // TEMPORIZADOR RESPONSAVEL POR REPINTAR A TELA AUTOMATICAMENTE
    private Timer timer = new Timer();
    // OBJETO QUE SERÁ DISPARADO PELO USUARIO EM DIREÇAO AO CONJUNTO DE EBE
    private Projetil projetil = new Projetil();
    // VARIAVEL DE CONTROLE DE TRANSICAO ENTRE TELAS
    private int valor = 0;
    // VARIAVEL DE CONTROLE DE REPINTURA DO OBJETO PROJETIL 
    private int valorProjetil = 0;
    // OBJETOS QUE CARREGAM IMAGEM DO DISCO
    private BufferedImage menu, eebOne, eebTwo, space, gameOver, vencedor;
    // A VARIAVEL ESPACO_TECLADO FOI DECLARADA PORQUE EM MEU NOTEBOOK UM EVENTO DO TIPO KEYEVENT RETORNAVA O VALOR 32, E 
    // NO QUAL DENTRO DA CLASSE CORRESPONDENTE ELE POSSUI VALOR 8, SENDO ASSIM NÃO ERA POSSIVEL COMPARAR A ACAO DESTE.
    private static final int ESPACO_TECLADO = 32;
    // OBJETO QUE RECEBE A ENTIDADE JOGADOR POR PARAMETRO NO CONSTRUTOR 
    private Jogador player;
    // VARIAVEL RESPONSÁVEL POR DETERMINAR A ALTURA DO COMEÇO DA PINTURA DA PRIMEIRA LINHA DE EBE
    private int linhaUm = 50;
    // VARIAVEL RESPONSÁVEL POR DETERMINAR A ALTURA DO COMEÇO DA PINTURA DA SEGUNDA LINHA DE EBE
    private int linhaDois = 100;
    // VARIAVEL RESPONSÁVEL POR DETERMINAR A ALTURA DO COMEÇO DA PINTURA DA TERCEIRA LINHA DE EBE
    private int linhaTres = 150;
    // VARIAVEL RESPONSÁVEL POR DETERMINAR A ALTURA DO COMEÇO DA PINTURA DA QUARTA LINHA DE EBE
    private int linhaQuatro = 200;
    // EBE DO MENU DA COR AZUL
    private Ebe ebeAzul = new Ebe();
    // EBE DO MENU DA COR VERMELHA
    private Ebe ebeVermelho = new Ebe();
    // EBE DO MENU DA COR AMARELO
    private Ebe ebeAmarelo = new Ebe();
    // EBE DO MENU DA COR VERDE
    private Ebe ebeVerde = new Ebe();
    // VARIAVEL DE CONTROLE DA TROCA DO PAINEL GAME OVER PARA A TABELA COM OS REGISTROS DO BANCO DE DADOS
    private int trocaPainel = 0;
    // VARIAVEL QUE CONTROLA QUANDO O USUARIO JA ATINGUI TODOS OS ALVOS
    private int userGanhou = 0;
    // ESSA VARIAVEL  É RESPONSÁVEL POR ARMAZENAR A POSIÇÃO DENTRO DO ARRAY LIST QUE TEVE UMA COLISAO
    private int posicao = -1;
    // ESSA VARIAVEL É RESPONSAVEL POR ARMAZENAR O VALOR QUE O OBJETO EBE NO QUAL TEVE UMA COLISAO POSSUE
    private int eixoXEbe = 0;

    public PainelGame(Jogador jogador) {

        //INFORMA QUE A CLASSE QUE DEVE TRATAR OS EVENTOS DE TECLADO SERÁ ESTA     
        this.addKeyListener(this);

        // RESPONSÁVEL POR EXECUTAR REPINTURA DA TELA A CADA 250MS
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // REPINTO ESTE PAINEL
                repaint();
            }
        }, 0, 250);
        //INICIALIZA O OBJETO PLAYER DA CLASSE JOGADOR NESTA CLASSE        
        this.player = jogador;
    }

    @Override
    public void paintComponent(Graphics g) {
        //CHAMO O METODO DE PINTURA DE COMPONENTES DA SUPER CLASSE 
        super.paintComponent(g);
        // SETO A COR DE FUNDO PARA O PAINEL
        super.setBackground(Color.BLACK);

        // REALIZO UM CASTING DE CONVERSÃO DE DADOS 
        Graphics2D g2 = (Graphics2D) g;


        //INICIALIZO OBJETO DO TIPO LINE2D REPASSANDO AS COORDENADAS
        linha = new Line2D.Float(10, this.getHeight() - 20, this.getWidth() - 10, this.getHeight() - 20);
        //SETO A ESPESSURA DA LINHA
        g2.setStroke(new BasicStroke(5));
        // REPASSO A COR QUE O OBJETO DEVE SER PINTADO
        g2.setColor(Color.GREEN);
        //MANDO PINTAR O OBJETO DO TIPO LINE2D
        g2.draw(linha);

        //INICIALIZO OBJETO DO TIPO LINE2D REPASSANDO AS COORDENADAS
        linhaInicial = new Line2D.Float(10, 20, this.getWidth() - 10, 20);
        //SETO A ESPESSURA DA LINHA
        g2.setStroke(new BasicStroke(5));
        // REPASSO A COR QUE O OBJETO DEVE SER PINTADO
        g2.setColor(Color.YELLOW);
        //MANDO PINTAR O OBJETO DO TIPO LINE2D
        g2.draw(linhaInicial);

        // APRESENTA O PAINEL INICIAL DO JOGO
        if (valor == 0) {
            // VERIFICO SE OS OBJETOS NÃO FORAM INICIALIZADOS
            if (menu == null && eebOne == null && eebTwo == null && space == null) {
                try {
                    // CARREGO AS IMAGENS DO DISCO;    
                    menu = ImageIO.read(new File("src/com/Java2D/Imagens/MENU_SPACE_INVADERS.jpg"));
                    eebOne = ImageIO.read(new File("src/com/Java2D/Imagens/EEB_MENU_ONE.jpg"));
                    eebTwo = ImageIO.read(new File("src/com/Java2D/Imagens/EEB_MENU_TWO.jpg"));
                    space = ImageIO.read(new File("src/com/Java2D/Imagens/START_SPACE.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(PainelGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // SOLICITO AO OBJETO GRAPHICS2D QUE DESENHE AS IMAGENS NA TELA
            g2.drawImage(menu, ((this.getWidth() / 2) - 150), 50, this);
            g2.drawImage(eebOne, 10, 150, this);
            g2.drawImage(eebTwo, (this.getWidth() - 210), 150, this);
            g2.drawImage(space, ((this.getWidth() / 2) - 150), (this.getHeight() - 120), this);
            // DESENHO OS EBES DO MENU 
            ebeAzul.draw(g2, (this.getWidth() / 3) + 150, 270, Color.BLUE);
            ebeVermelho.draw(g2, (this.getWidth() / 3) + 150, 320, Color.RED);
            ebeAmarelo.draw(g2, (this.getWidth() / 3) + 150, 370, Color.YELLOW);
            ebeVerde.draw(g2, (this.getWidth() / 3) + 150, 420, Color.GREEN);
            g2.setStroke(new BasicStroke(8));
            g2.setColor(Color.WHITE);
            /*ESCREVO NA TELA A PONTUACAO PARA CADA COR DE EBE*/
            g2.drawString("100 PONTOS", (this.getWidth() / 3) + 250, 285);
            g2.drawString("50 PONTOS", (this.getWidth() / 3) + 250, 335);
            g2.drawString("25 PONTOS", (this.getWidth() / 3) + 250, 385);
            g2.drawString("10 PONTOS", (this.getWidth() / 3) + 250, 435);
        }

        //INICIALIZO AS VARIAVEIS QUE CONTROLAM O A ALTURA E LARGURA MAXIMA QUE OS EBES PODEM ALCANÇAR
        this.widht = this.getWidth() - 30;
        this.height = this.getHeight();


        // SE O VALOR DA FLAG FOR 1,ELE CARREGA OS ELEMENTOS DO JOGO
        if (valor == 1) {
            // SE A LISTA FOR VAZIA
            if (lstEbe.isEmpty() && userGanhou == 0) {
                // CARREGA A LISTA DE EBES E DESENHA-AS NA TELA     
                this.CarregaEbe(g2);
                // INCLEMENTO A VARIAVEL PARA NAO ENTRAR MAIS NO IF, A NÃO SER QUE O PROGRAMA SEJA REINICIADO
                userGanhou++;
            }
            projetil.setColidiu(false);
            // CARREGO CONJUNTO DE EBES
            this.MostrarEbes(g2);

            // DESENHA O OBJETO DO TIPO EBE QUE SERÁ CONTROLADO PELO USUARIO
            combate.draw(g2, naveEixoX, naveEixoY, Color.WHITE);

            // SE O VALOR DA VARIAVEL FOR 1,O MESMO IRÁ PINTAR O PROJETIL NA TELA
            if (valorProjetil == 1) {
                //PINTA O OBJETO NA TELA
                projetil.draw(g2, naveEixoX, naveEixoY, Color.ORANGE);
                // SE O VALOR DO PROJETIL NO EIXO Y FOR IGUAL A ZERO, OU SEJA CHEGAR AO FINAL DO PAINEL
                if (projetil.getEixoY() == 0) {
                    //DESABILITA A EXIBICAO DO PROJETIL NO PAINEL
                    valorProjetil = 0;
                    //SETO O VALOR DA VARIAVEL DE INSTANCIA NA CLASSE PROJETIL COMO VALOR ZERO;
                    projetil.setEixoX(0);
                }
            }
        }

        // SE O VALOR DA FLAG FOR IGUAL A DOIS, MOSTRA NO PAINEL A TELA DE GAME OVER
        if (valor == 2) {
            // SE O OBJETO NÃO FOI INICIALIZADO
            if (gameOver == null) {
                try {
                    // CARREGO A IMAGEM DO DISCO
                    gameOver = ImageIO.read(new File("src/com/Java2D/Imagens/GAME_OVER.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(PainelGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (trocaPainel == 20) {
                //ALTERO O VALOR DA VARIAVEL DE CONTROLE, INDICANDO QUE NA PROXIMA REPINTADA DE TELA ELA MOSTRARÁ A TELA COM OS REGISTROS DO BANCO DE DADOS
                valor = 2;
                //REINICIALIZO A VARIAVEL DE CONTROLE
                trocaPainel = 0;
            }
            // INCLEMENTO A VARIAVEL QUE CONTROLA A TRANSICAO DO PAINEL GAME OVER PARA O PAINEL COM OS REGISTROS DO BANCO DE DADOS
            trocaPainel++;
            // DESENHO A IMAGEM CARREGADA DO DISCO NO PAINEL
            g2.drawImage(gameOver, (this.getWidth() / 4), (this.getHeight() / 6), this);
        }

        // MOSTRA O PAINEL QUANDO O USUARIO GANHOU     
        if (valor == 3) {
            // SE O OBJETO NÃO FOI INSTANCIADO
            if (vencedor == null) {
                try {
                    // CARREGO A IMAGEM DO DISCO 
                    vencedor = ImageIO.read(new File("src/com/Java2D/Imagens/VENCEDOR_GAME.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(PainelGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (trocaPainel == 20) {
                //ALTERO O VALOR DA VARIAVEL DE CONTROLE, INDICANDO QUE NA PROXIMA REPINTADA DE TELA ELA MOSTRARÁ A TELA COM OS REGISTROS DO BANCO DE DADOS
                valor = 3;
                //REINICIALIZO A VARIAVEL DE CONTROLE
                trocaPainel = 0;
            }
            // INCLEMENTO A VARIAVEL QUE CONTROLA A TRANSICAO DO PAINEL GAME OVER PARA O PAINEL COM OS REGISTROS DO BANCO DE DADOS
            trocaPainel++;

            // DESENHO A IMAGEM NO PAINEL
            g2.drawImage(vencedor, ((this.getWidth() / 4) + 100), (this.getHeight() / 4), this);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // VERIFICO SE O O EVENTO DE TECLADO É IGUAL A SETA APONTADA PARA A ESQUERDA
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // SE O VALOR DA FLAG FOR MAIOR IGUAL A ZERO E MENOR QUE O COMPRIMENTO DA JANELA
            if (naveEixoX >= 0 || naveEixoX <= widht) {
                // MOVO A NAVE PARA A ESQUERDA
                this.naveEixoX -= 10;
                // VERIFICO SE O VALOR ATUALIZADO DA VARIAVEL É MENOR QUE ZERO OU MAIOR QUE QUE O COMPRIMENTO
                if (naveEixoX < 0 || naveEixoX > widht) {
                    // DEFINO UM NOVO VALOR PARA O EIXO X
                    this.naveEixoX = 10;
                    // REPINTO O COMPONENTE
                }
            }
            // VERIFICO SE O EVENTO DO TECLADO É IGUAL A SETA APONTADA PARA A DIREITA  
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // SE O VALOR DA FLAG FOR MAIOR IGUAL A ZERO E MENOR QUE O COMPRIMENTO DA JANELA
            if (naveEixoX >= 0 || naveEixoX <= widht) {
                this.naveEixoX += 10;
                // VERIFICO SE O VALOR ATUALIZADO DA VARIAVEL É MENOR QUE ZERO OU MAIOR QUE QUE O COMPRIMENTO
                if (naveEixoX < 0 || naveEixoX > widht) {
                    // DEFINO UM NOVO VALOR PARA O EIXO  X
                    this.naveEixoX = (widht - 50);
                    // REPINTO O COMPONENTE
                }
            }
            // QUANDO A TECLA PRESSIONADA FOR IGUAL A BARRA DE ESPACO
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            //ALTERO O VALOR DA VARIAVEL QUE CONTROLA A PINTURA DO PROJETIL NA TELA
            valorProjetil = 1;
            // QUANDO A TECLA PRESSIONADA FOR IGUAL A BARRA DE ESPACO
        } else if (e.getKeyCode() == ESPACO_TECLADO) {
            //ALTERO O VALOR DA VARIAVEL QUE CONTROLA A PINTURA DO PROJETIL NA TELA
            valorProjetil = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setaFlag(int valorP) {
        //ALTERA O VALOR DA FLAG QUE ALTERNA OS PAINEL  
        this.valor = valorP;
    }

    public void CarregaEbe(Graphics2D g2) {
        int i = 0;

        int colunaVermelho = 150;
        int colunaAzul = 150;
        int colunaAmarelo = 150;
        int colunaVerde = 150;

        // VERIFICO O SCORE DO JOGADOR E DEPENDENDO DE SUA PONTUACAO AUMENTO A VELOCIDADE DO GRUPO DE EBE
        if (player.getScore() >= 300 && player.getScore() < 1000) {

            this.linhaUm += 1;
            this.linhaDois += 1;
            this.linhaTres += 1;
            this.linhaQuatro += 1;

        } else if (player.getScore() >= 1000 && player.getScore() < 1500) {

            this.linhaUm += 5;
            this.linhaDois += 5;
            this.linhaTres += 5;
            this.linhaQuatro += 5;

        } else if (player.getScore() >= 1500) {

            this.linhaUm += 7;
            this.linhaDois += 7;
            this.linhaTres += 7;
            this.linhaQuatro += 7;

        }

        while (i < 32) {
            //INICIALIZA UM OBJETO DO TIPO EBE 
            Ebe ebe = new Ebe();
            // DESENHO DA PRIMEIRA LINHA DE EBE 
            if (i < 8) {
                // DESENHO  O EBE NA LINHA
                ebe.draw(g2, colunaVermelho, linhaUm, Color.RED);
                // INCLEMENTO A VARIAVEL, NA QUAL TERA A RESPONSABILIDADE DE  MUDA  O PONTO DE INICIO DE DESENHO DO PROXIMO EBE NA LINHA 
                colunaVermelho += 150;

                // DESENHO DA SEGUNDA LLINHA DE EBE    
            } else if (i >= 8 && i < 16) {
                // DESENHO  O EBE NA LINHA
                ebe.draw(g2, colunaAzul, linhaDois, Color.BLUE);
                // INCLEMENTO A VARIAVEL, NA QUAL TERA A RESPONSABILIDADE DE  MUDA  O PONTO DE INICIO DE DESENHO DO PROXIMO EBE NA LINHA
                colunaAzul += 150;

                // DESENHO A TERCEIRA LINHA DE EBE    
            } else if (i >= 16 && i < 24) {
                // DESENHO  O EBE NA LINHA
                ebe.draw(g2, colunaAmarelo, linhaTres, Color.YELLOW);
                // INCLEMENTO A VARIAVEL, NA QUAL TERA A RESPONSABILIDADE DE  MUDA  O PONTO DE INICIO DE DESENHO DO PROXIMO EBE  NA LINHA
                colunaAmarelo += 150;

                // DESENHO A QUARTA LINHA DE EBE    
            } else if (i >= 24 && i < 32) {
                // DESENHO  O EBE NA LINHA
                ebe.draw(g2, colunaVerde, linhaQuatro, Color.GREEN);
                // INCLEMENTO A VARIAVEL, NA QUAL TERA A RESPONSABILIDADE DE  MUDA  O PONTO DE INICIO DE DESENHO DO PROXIMO EBE  NA LINHA
                colunaVerde += 150;
            }
            // ADICIONO OBJETO EBE AO ARRAYLIST  
            lstEbe.add(ebe);
            // INCLEMENTO A VARIAVEL DE CONTROLE DO LOOP
            i++;
        }
    }

    // REINICIA O VALOR DO PROJETIL
    public void setaValorProjetil(int proj) {
        // ALTERO O VALOR DA VARIVAVEL DE INSTANCIA QUE CONTROLA A PINTURA DO OBJETO NA TELA
        this.valorProjetil = proj;
    }

    /* GETTERS AND SETTERS */
    public int getLinhaUm() {
        return linhaUm;
    }

    public int getLinhaDois() {
        return linhaDois;
    }

    public int getLinhaTres() {
        return linhaTres;
    }

    public int getLinhaQuatro() {
        return linhaQuatro;
    }

    public void setLinhaUm(int linhaUm) {
        this.linhaUm = linhaUm;
    }

    public void setLinhaDois(int linhaDois) {
        this.linhaDois = linhaDois;
    }

    public void setLinhaTres(int linhaTres) {
        this.linhaTres = linhaTres;
    }

    public void setLinhaQuatro(int linhaQuatro) {
        this.linhaQuatro = linhaQuatro;
    }

    public ArrayList<Ebe> getLstEbe() {
        return lstEbe;
    }

    public void setLstEbe(ArrayList<Ebe> lstEbe) {
        this.lstEbe = lstEbe;
    }

    public int getNaveEixoX() {
        return naveEixoX;
    }

    public void setNaveEixoX(int naveEixoX) {
        this.naveEixoX = naveEixoX;
    }

    public int getNaveEixoY() {
        return naveEixoY;
    }

    public void setNaveEixoY(int naveEixoY) {
        this.naveEixoY = naveEixoY;
    }

    public Jogador getPlayer() {
        return player;
    }

    public void setPlayer(Jogador player) {
        this.player = player;
    }

    public Projetil getProjetil() {
        return projetil;
    }

    public void setProjetil(Projetil projetil) {
        this.projetil = projetil;
    }

    public int getUserGanhou() {
        return userGanhou;
    }

    public void setUserGanhou(int userGanhou) {
        this.userGanhou = userGanhou;
    }

    /**
     * Esse método é responsável por repintar todos os ebes em suas novas
     * coordenadas.
     */
    public void MostrarEbes(Graphics2D g2) {
        // VARIAVEL DE CONTROLE 
        int k = 0;
        try {
            // VERIFICO O SCORE DO JOGADOR E DEPENDENDO DE SUA PONTUACAO AUMENTO A VELOCIDADE DO GRUPO DE EBE
            if (player.getScore() >= 300 && player.getScore() < 1000) {

                this.linhaUm += 1;
                this.linhaDois += 1;
                this.linhaTres += 1;
                this.linhaQuatro += 1;

            } else if (player.getScore() >= 1000 && player.getScore() < 1500) {

                this.linhaUm += 5;
                this.linhaDois += 5;
                this.linhaTres += 5;
                this.linhaQuatro += 5;

            } else if (player.getScore() >= 1500) {

                this.linhaUm += 7;
                this.linhaDois += 7;
                this.linhaTres += 7;
                this.linhaQuatro += 7;

            }

            // SE O ARRAY AINDA TIVER EBES  
            if (lstEbe.size() > 0) {
                //LOOP QUE INTERA SOBRE O ARRAY DE EBES(ENQUANTO A VARIAVEL DE CONTROLE FOR MENOR QUE O TAMANHO DO ARRAY)
                while (k < lstEbe.size()) {
                    // MÉTODO QUE DETECTA A COLISAO ENTRE EBE/PROJETIL E EBE/LINHA FINAL
                    this.controleColisao(lstEbe.get(k), k);
                    // SE A COR DO EBE FOR AZUL 
                    if (lstEbe.get(k).getColor() == Color.RED) {
                        // SE O VALOR  ATRIBUIDO NA VARIAVEL EIXOXEBE FOR IGUAL AO VALOR DA COLUNA AZUL E O VALOR NA VARIAVEL POSICAO FOR DIFERENTE DE -1
                        if (this.eixoXEbe == lstEbe.get(k).x() && this.posicao != -1) {
                            // REMOVO DA LISTA DE EBES O OBJETO NAQUELA POSICAO
                            this.lstEbe.remove(posicao);
                            // REINICIO AS FLAGS DE CONTROLE
                            this.posicao = -1;
                        } else {
                            // DESENHO O EBE
                            lstEbe.get(k).draw(g2, lstEbe.get(k).x(), linhaUm, Color.RED);
                        }
                        // SE A COR DO EBE FOR AZUL
                    } else if (lstEbe.get(k).getColor() == Color.BLUE) {
                        // SE O VALOR  ATRIBUIDO NA VARIAVEL EIXOXEBE FOR IGUAL AO VALOR DA COLUNA AZUL E O VALOR NA VARIAVEL POSICAO FOR DIFERENTE DE -1
                        if (this.eixoXEbe == lstEbe.get(k).x() && this.posicao != -1) {
                            // REMOVO DA LISTA DE EBES O OBJETO NAQUELA POSICAO
                            this.lstEbe.remove(posicao);
                            // REINICIO AS FLAGS DE CONTROLE                               
                            this.posicao = -1;
                        } else {
                            // DESENHO O EBE
                            lstEbe.get(k).draw(g2, lstEbe.get(k).x(), linhaDois, Color.BLUE);
                        }
                        // SE A COR DO EBE FOR AMARELA
                    } else if (lstEbe.get(k).getColor() == Color.YELLOW) {
                        // SE O VALOR  ATRIBUIDO NA VARIAVEL EIXOXEBE FOR IGUAL AO VALOR DA COLUNA AMARELO E O VALOR NA VARIAVEL POSICAO FOR DIFERENTE DE -1
                        if (this.eixoXEbe == lstEbe.get(k).x() && this.posicao != -1) {
                            this.lstEbe.remove(posicao);
                            // REINICIALIZO AS FLAGS DE CONTROLE
                            this.posicao = -1;
                        } else {
                            // DESENHO O EBE
                            lstEbe.get(k).draw(g2, lstEbe.get(k).x(), linhaTres, Color.YELLOW);
                        }
                        // SE A COR DO EBE FOR VERDE
                    } else if (lstEbe.get(k).getColor() == Color.GREEN) {
                        // SE O VALOR  ATRIBUIDO NA VARIAVEL EIXOXEBE FOR IGUAL AO VALOR DA COLUNA VERDE E O VALOR NA VARIAVEL POSICAO FOR DIFERENTE DE -1
                        if (this.eixoXEbe == lstEbe.get(k).x() && this.posicao != -1) {
                            // REMOVO DA LISTA DE EBES O OBJETO NAQUELA POSICAO
                            this.lstEbe.remove(posicao);
                            // REINICIALIZO AS FLAGS
                            this.posicao = -1;
                        }
                        // DESENHO O EBE
                        lstEbe.get(k).draw(g2, lstEbe.get(k).x(), linhaQuatro, Color.GREEN);

                    } else if (lstEbe.get(k).y() == linha.getY1()) {
                        valor = 2;
                        /*REINICIO AS FLAGS DE CONTROLE*/
                        posicao = -1;
                        return;
                    }
                    // INCLEMENTO A VARIAVEL DE CONTROLE DO LOOP  
                    k++;
                }
            } else {
                // ALTERO O VALOR DA FLAG QUE TROCA OS PAINEIS DO JOGO
                valor = 3;
                // REINICIO AS VARIAVEIS
                posicao = -1;
                eixoXEbe = 0;
                userGanhou = 0;
            }
        } catch (Exception e) {
        }
    }

    /**
     * Esse Método tem como função controlar a posicao em que está o objeto está
     * em um determinado momento do Jogo E Verifica se a posicao atual é a mesmo
     * posicao de outro componente;
     */
    public boolean controleColisao(Ebe ebe, int k) {
        // INTERA TODAS AS POSIÇOES DO ARRAY
        boolean colidiu;
        colidiu = this.colidiuEbe(ebe, k);
        // SE O RETORNO DO METODO FOR VERDADEIRO, QUER DIZER QUE HOUVE COLISAO DO PROJETIL COM ALGUM EBE    
        if (colidiu) {
            // VERIFICO A COR DESSE EBE
            if (ebe.getColor() == Color.BLUE) {
                // SE FOR DA COR AZUL,SOMO 100 PONTOS NO SCORE DO JOGADOR
                player.setScore(100);
                // ATRIBUO A VARIAVEL DE CONTROLE A POSICAO ITERADA
                this.posicao = k;
                // ATRIBUO A FLAG O VALOR DA POSICAO NO EIXO X DO OBJETO EBE ITERADO
                this.eixoXEbe = ebe.x();
                //ALTERO O VALOR DA VARIAVEL QUE CONTROLA O DISPARO ENTRO O OBJETO PROJETIL E O EBE ITERADO
                projetil.setColidiu(true);
                return colidiu;
            } else if (ebe.getColor() == Color.RED) {
                // SE FOR DA COR VERMELHA,SOMO 50 PONTOS NO SCORE DO JOGADOR
                player.setScore(50);
                // ATRIBUO A VARIAVEL DE CONTROLE A POSICAO ITERADA
                this.posicao = k;
                // ATRIBUO A FLAG O VALOR DA POSICAO NO EIXO X DO OBJETO EBE ITERADO
                this.eixoXEbe = ebe.x();
                //ALTERO O VALOR DA VARIAVEL QUE CONTROLA O DISPARO ENTRO O OBJETO PROJETIL E O EBE ITERADO
                projetil.setColidiu(true);
                return colidiu;
            } else if (ebe.getColor() == Color.YELLOW) {
                // SE FOR DA COR AZUL,SOMO 25 PONTOS NO SCORE DO JOGADOR
                player.setScore(25);
                // ATRIBUO A VARIAVEL DE CONTROLE A POSICAO ITERADA
                this.posicao = k;
                // ATRIBUO A FLAG O VALOR DA POSICAO NO EIXO X DO OBJETO EBE ITERADO
                this.eixoXEbe = ebe.x();
                //ALTERO O VALOR DA VARIAVEL QUE CONTROLA O DISPARO ENTRO O OBJETO PROJETIL E O EBE ITERADO
                projetil.setColidiu(true);
                return colidiu;
            } else if (ebe.getColor() == Color.GREEN) {
                // SE FOR DA COR VERDE,SOMO 10 PONTOS NO SCORE DO JOGADOR
                player.setScore(10);
                // ATRIBUO A VARIAVEL DE CONTROLE A POSICAO ITERADA
                this.posicao = k;
                // ATRIBUO A FLAG O VALOR DA POSICAO NO EIXO X DO OBJETO EBE ITERADO
                this.eixoXEbe = ebe.x();
                //ALTERO O VALOR DA VARIAVEL QUE CONTROLA O DISPARO ENTRO O OBJETO PROJETIL E O EBE ITERADO
                projetil.setColidiu(true);
                return colidiu;
            }
            // SE ALGUM EBE CRUZAR A LINHA VERDE   
        } else if ((ebe.y() + 30) >= linha.getY1()) {
            // REINICIALIZO FLAGS DE CONTROLE
            posicao = -1;
            eixoXEbe = 0;
            // SE A CONDIÇAO FOR VERDADEIRA,ENTAO QUER DIZER QUE O GRUPO DE EBE CHEGOU AO FIM DA TELA,ENTÃO INSERA O JOGO E MOSTRA A TELA DE GAMEOVER                
            if (userGanhou != 0) {
                if ((player.getLives() - 1) > 0) {
                    // DIMINUO UMA VIDA DO OBJETO DA CLASSE JOGADOR CASO ALGUM EBE TOQUE A LINHA VERDE
                    player.diminuirLive(1);
                    // REINICIO AS POSICOES
                    this.linhaUm = 50;
                    this.linhaDois = 100;
                    this.linhaTres = 150;
                    this.linhaQuatro = 200;
                    //LIMPO A COLEÇÃO DE OBJETOS EBE
                    lstEbe.clear();
                    userGanhou = 0;
                    //ALTERO O VALOR DA VARIAVEL QUE CONTROLA O DISPARO ENTRO O OBJETO PROJETIL E O EBE ITERADO    
                    projetil.setColidiu(true);
                } else {
                    // ALTERO VALOR DA VARIAVEL QUE CONTROLA A TROCA DE PAINEIS
                    valor = 2;
                    // ZERO AS VIDAS DO OBJETO DA CLASSE JOGADOR
                    player.zerarLive(0);
                    //ALTERO O VALOR DA VARIAVEL QUE CONTROLA O DISPARO ENTRO O OBJETO PROJETIL E O EBE ITERADO    
                    projetil.setColidiu(true);
                    //CHAMADA A JANELA QUE EXIBE E INSERE NOVOS RESULTADOS NA CAMADA DE PERSISTENCIA
                    new Windows(player);
                    // REINICIALIZO A VARIAVEL DE CONTROLE
                    userGanhou = 0;
                }
            }
        }
        return colidiu;
    }

    /*CONTROLE DE COLISAO ENTRE EBE E PROJETIL*/
    public boolean colidiuEbe(Ebe e, int k) {
        if ((projetil.getEixoX() >= e.x() && projetil.getEixoX() <= (e.x() + 30))
                && (projetil.getEixoY() >= e.y() && projetil.getEixoY() <= (e.y() + 30))) {
            return true;
        } else if (((projetil.getEixoX() + 15) >= e.x() && (projetil.getEixoX() + 15) <= (e.x() + 30))
                && (projetil.getEixoY() >= e.y() && projetil.getEixoY() <= (e.y() + 30))) {
            return true;
        } else if ((projetil.getEixoX() >= e.x() && projetil.getEixoX() <= (e.x() + 30))
                && (projetil.getEixoY() >= (e.y() + 15) && (projetil.getEixoY() + 15) <= (e.y() + 30))) {
            return true;
        } else if (((projetil.getEixoX() + 15) >= e.x() && (projetil.getEixoX() + 15) <= (e.x() + 30))
                && ((projetil.getEixoY() + 15) >= e.y() && (projetil.getEixoY() + 15) <= (e.y() + 30))) {
            return true;
        }
        return false;
    }
}
