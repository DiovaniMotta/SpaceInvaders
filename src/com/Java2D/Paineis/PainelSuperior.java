/*
 * Essa classe extende de JPanel
 * e é responsável por implementar o component Swing na parte superior da tela
 */
package com.Java2D.Paineis;

import com.Java2D.Entity.Ebe;
import com.Java2D.Entity.Jogador;
import com.Java2D.Main.Janela;
import com.Java2D.Movimento.Projetil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Diovani Bernardi da Motta
 */
public class PainelSuperior extends JPanel implements ActionListener {
    
    /*OBJETOS  DE REPRESENTAÇÃO SWING E AWT*/
    private JTextField txtScore = new JTextField(6);
    private JTextField txtLive = new JTextField(6);
    private JButton reset = new JButton(" REINICIAR ");
    private JLabel score = new JLabel("SCORE:");
    private JLabel live = new JLabel("LIVE:");
    private Container content = new Container();
    private Container contantBotao = new Container();
    private Container contentLive = new Container();
    /* ATRIBUTOS QUE REPRESENTAM OBJETOS CRIADOS NO PROJETO E SAO RECEBIDOS POR PARAMETRO*/
    private PainelGame game;
    private Janela janela;
    private Jogador player;
    private Timer timer = new Timer();

    public PainelSuperior(PainelGame game, Janela janela, Jogador jogador) {
        this.game = game;
        this.janela = janela;
        this.player = jogador;

        // INICIALIZO MECANISMO DE REPINTURA AUTOMATICA DO PAINEL
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //REPINTO PAINEL AUTOMATICAMENTE A CADA 250 MS
                repaint();
            }
        }, 0, 250);
    }

    @Override
    public void paintComponent(Graphics g) {
        //CHAMO O CONSTRUTOR DA SUPER CLASSE QUE É O JPANEL
        super.paintComponent(g);
        // SETO A COR DE PINTURA DO FUNDO DO CONTAINER
        super.setBackground(Color.BLACK);

        // SETO O LAYOUT PARA O CONTAINER PAINELSUPERIOR COM UM LAYOUT DE BORDAS
        this.setLayout(new BorderLayout());

        // SETO A COR PARA A PINTURA DO TEXTO COMO BRANCA
        score.setForeground(Color.WHITE);

        //SETO A COR DE FUNDO DO OBJETO COM PRETO
        txtScore.setBackground(Color.BLACK);
        // SETO A COR PARA AS BORDAS COM COMPONENT COMO BRANCO
        txtScore.setCaretColor(Color.WHITE);
        // SETO PARA O COMPONENTE QUE O SEU CONTEUDO/TEXTO NÃO PODE SER ALTERADO PELO USUARIO
        txtScore.setEditable(false);
        // SETO A COR PARA A FONTE DENTRO DO JTEXTFIELD
        txtScore.setForeground(Color.GREEN);
        //SETO O ALINHAMENTO DENTRO DO  JTEXTFILED
        txtScore.setHorizontalAlignment(JTextField.CENTER);
        // SETO TEXTO DENTRO DO TXTSCORE
        txtScore.setText(String.valueOf(player.getScore()));

        //SETO UM LAYOUT PARA ESSE CONTAINER 
        content.setLayout(new FlowLayout());
        //SETO A COR DE FUNDO PARA O CONTAINER INICIALIZADO
        content.setBackground(Color.BLACK);

        // ADICIONO AO CONTAINER OS DOIS OBJETOS JA INSTACIADOS,SENDO UM DO TIPO JLABEL E OUTRO DO TIPO JTEXTFIELD
        content.add(score);
        content.add(txtScore);

        // ADICIONO AO JPANEL O CONTAINER CONTENDO OS OBJETOS JLABEL E JTEXTFIELD NA LINHA INICIAL DO CONTAINER
        this.add(content, BorderLayout.LINE_START);

        //SETO UMA COR DE FUNDO PARA O OBJETO
        reset.setBackground(Color.WHITE);
        // SETO COR PARA A FONTE DO OBJETO
        reset.setForeground(Color.BLACK);


        // REPASSO LAYOUT PARA O CONTAINER CRIADO
        contantBotao.setLayout(new FlowLayout());
        //SETO COR DE FUNDO PARA O CONTAINER
        contantBotao.setBackground(Color.BLACK);
        //ADICIONO A COR PARA O BOTAO
        contantBotao.setForeground(Color.BLACK);
        // ADICIONO AO CONTAINER O OBJETO  JBUTTON CRIADO.
        contantBotao.add(reset);

        this.add(contantBotao, BorderLayout.CENTER);

        //SETO UM LAYOUT PARA ESSE CONTAINER SENDO ELE DE FORMA LIVRE
        contentLive.setLayout(new FlowLayout());

        // SETO A COR PARA A PINTURA DO TEXTO COMO BRANCA
        live.setForeground(Color.WHITE);

        //SETO A COR DE FUNDO DO OBJETO COM PRETO
        txtLive.setBackground(Color.BLACK);
        // SETO A COR PARA AS BORDAS COM COMPONENT COMO BRANCO
        txtLive.setCaretColor(Color.WHITE);
        // SETO PARA O COMPONENTE QUE O SEU CONTEUDO/TEXTO NÃO PODE SER ALTERADO PELO USUARIO
        txtLive.setEditable(false);
        // SETO A COR PARA O TEXTO DENTRO DO JTEXTFILD
        txtLive.setForeground(Color.GREEN);
        //SETAR ALINHAMENTO DENTRO DO JTEXTFIELD 
        txtLive.setHorizontalAlignment(JTextField.CENTER);
        // SETO O A QUANTIDADE DE VIDAS GUARDA NO OBJETO PLAYER DA CLASSE JOGADOR
        txtLive.setText(String.valueOf(player.getLives()));

        //ADICIONO OS DOIS OBJETOS CRIADOS AO CONTAINER 
        contentLive.add(live);
        contentLive.add(txtLive);


        // ADICIONO O CONTAINER QUE CONTÉM OS OBJETOS DO TIPO JBUTTON E JTEXTFIELD AO CONTAINER PAINELSUPERIOR
        this.add(contentLive, BorderLayout.LINE_END);

        //INFORMO QUE A CLASSE QUE DEVE TRATAR OS EVENTOS DE CLICK NO BOTAO RESET DA CLASSE PAINEL SERAO FEITOS PELA CLASSE JANELA
        reset.addActionListener(this);
    }

    // TRATAMENTO DO EVENTO DE CLICK NO BOTAO RESET
    @Override
    public void actionPerformed(ActionEvent e) {
        // SETA VALOR DA FLAG COMO 0, OU SEJA REINICIA O JOGO
        this.game.setaFlag(0);
        // ALTERO O VALOR DA VARIAVEL RESPONSAVEL POR CONTROLAR A PINTURA DO PROJETIL
        this.game.setaValorProjetil(0);
        // RETORNA O VALOR INICIAL DAS VARIAVEIS RESPONSAVEIS POR DETERMINAR EM QUAL ALTURA SERÁ INICIALIZADAA PINTURA DOS EBE
        this.game.setLinhaUm(50);
        this.game.setLinhaDois(100);
        this.game.setLinhaTres(150);
        this.game.setLinhaQuatro(200);
        //REINICIO QUANTIDADE DE VIDAS E PONTUACAO;
        this.player.setLives(3);
        this.player.setScore(0);
        // CRIO UM NOVO OBJETO DO TIPO ARRAY DE EBE VAZIO E REPASSO PARA O MEU OBJETO GAME PARA QUE POSSA RECARREGAR TODOS OS EBES
        ArrayList<Ebe> limpaLista = new ArrayList<>();
        // SETO LISTA DE EBE VAZIA PARA O OBJETO
        this.game.setLstEbe(limpaLista);
        // INSTANCIO UM OBJETO DO TIPO PROJETIL QUE SERÁ RESPONSÁVEL POR REINICIAR O OBJETO DA CLASSE PAINELGAME
        Projetil p = new Projetil();
        // REPASSA OBJETO VAZIO CRIADO PARA O PAINEL GAME
        this.game.setProjetil(p);
        //REINICIO A POSICAO DA NAVE
        this.game.setNaveEixoX(600);
        this.game.setNaveEixoY(600);
        // REINICIO A VARIAVEL 
        this.game.setUserGanhou(0);
        //REINICIAR VARIAVEIS LIVES E SCORE DO OBJETO JOGADOR NA CLASSE PAINELGAME
        this.game.getPlayer().zerarLive(3);
        this.game.getPlayer().zerarScore(0);
        // REPASSO O FOCO PARA ESSE A JANELA (JFRAME)
        janela.setFocusable(true);
        //REQUESITO O FOCO  DA JANELA PARA A JANELA(JFRAME)
        janela.requestFocusInWindow();
    }
}
