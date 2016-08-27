/*
 * Essa Classe Herda os atributos da de JFrame
 * e tem como funcao recuperar os dados da camada de persistencia 
 * e inseri-los em um JTable
 */
package com.Java2D.Frame;

import com.Java2D.Entity.Jogador;
import com.Java2D.Persistencia.Persistence;
import com.Java2D.TabelModel.TabelaModelo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Diovani Bernardi da Motta
 */
public class Windows extends JFrame implements ActionListener {

    /*COLEÇÃO DE OBJETOS DO TIPO JOGADOR*/
    private ArrayList<Jogador> lstJogador = new ArrayList<>();
    /*OBJETO RESPONSAVEL POR SE CONECTAR NA CAMADA DE PERSISTENCIA*/
    private Persistence persiste = new Persistence();
    /*OBJETO DA CLASSE JOGADOR QUE REPRESENTA,O OBJETO A SER INSERIDO NA CAMADA DE PERSISTENCIA*/
    private Jogador player;
    /*COMPONENTES SWING E AWT USADOS NA CONSTRUCAO DA JANELA*/
    private JTextField txtName = new JTextField(20);
    private JLabel namePlayer = new JLabel("Jogador:");
    private JButton botaoPlayer = new JButton("CONFIRMAR:");
    private JTable tabela = new JTable();
    private Container c = new Container();
    private JScrollPane scroll;

    /**
     * ESSE É O CONSTRUTOR DA CLASSE, ELE É CHAMADO QUANDO O OBJETO É
     * INICIALIZADO
     *
     * @param player é um objeto do tipo Jogador,ele será o objeto inserido na
     * camada de persistencia
     */
    public Windows(Jogador player) {
        super("SPACE INVADERS - CONEXÃO COM A PERSISTENCIA");
        //ASSOCIO AO ATRIBUTO LOCAL O PARAMETRO DO CONSTRUTOR
        this.player = player;

        // CRIANDO UM OBJETO DO TIPO CONTAINER RECUPERADO ATRAVES DO CONTAINER DO JFRAME
        Container conteiner = this.getContentPane();
        //SETADO LAYOUT PADRAO
        conteiner.setLayout(new BorderLayout());
        // SETADO COR DE FUNDO DO COMPONENTE DE CAMPO DE TEXTO
        txtName.setBackground(Color.WHITE);
        // SETADO COR PARA AS BORDAS DO CAMPO DE TEXTO
        txtName.setForeground(Color.BLACK);
        // SETADO A COR PARA A FONTE DO CAMPO DE TEXTO
        txtName.setCaretColor(Color.GREEN);

        // SETADO COR DE FUNDO PARA O OBJETO DO TIPO ROTULO
        namePlayer.setBackground(Color.WHITE);
        // SETADO A COR PARA A FONTE DO OBJETO DO TIPO ROTULO
        namePlayer.setForeground(Color.BLACK);

        // SETADO A COR DE FUNDO PARA O COMPONENTE DO TIPO BOTAO
        botaoPlayer.setBackground(Color.WHITE);
        // SETADO A COR PARA A FONTE DO DO COMPONENTE DO TIPO BOTAO
        botaoPlayer.setForeground(Color.BLACK);

        // ADICIONADO AO COMPONENTE BOTAO UM EVENTO DO TIPO ACTIONLISTENER E REPASSADO QUE A CLASSE TRATADORA SERÁ ESTA
        botaoPlayer.addActionListener(this);

        // SETADO LAYOUT PARA O CONTAINER
        c.setLayout(new FlowLayout());

        c.setBackground(Color.BLACK);
        /*ADICIONADO COMPONENTES SWING PARA O CONTAINER*/
        c.add(namePlayer);
        c.add(txtName);
        c.add(botaoPlayer);

        //ADICIONADO CONTAINER QUE POSSUE OS COMPONENTES SWING AO CONTAINER ATUAL 
        this.add(c, BorderLayout.NORTH);
        // EXECUTADO METODO QUE CARREGA O ARRAYLIST E ADICIONA A TABELA
        this.carregarArrayList();
        // ADICIONADO TABELA A UMA BARRA DE ROLAGEM        
        scroll = new JScrollPane(tabela);
        // ADICIONADO BARRA DE ROLAGEM QUE CONTEM EM SEU INTERIOR A TABELA A PARTE CENTRAL DO JFRAME 
        this.add(scroll, BorderLayout.CENTER);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(10);


        /*SETO A OPERAÇÃO DE FECHAMENTO DA JANELA*/
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /*SETO QUE ESSA JANELA  DEVE SER MAXIMIZADA ATE COBRIR TODA JANELA*/
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        /*SETO A VISIBILIDADE DA JANELA COM VERDADEIRA*/
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*SETO PARA O NOME DO OBJETO JOGADOR O CONTEUDO NO CAMPO DE TEXTP*/
        player.setNomePlayer(txtName.getText());
        // VERIFICO SE O EVENTO DE AÇÃO É IGUAL AO OBJETO BOTAOPLAYER
        if (e.getSource() == botaoPlayer) {
            // SE O NOME DO JOGADOR TIVER COMPRIMENTO MAIOR QUE ZERO, OU SEJA O CAMPO NAO ESTEJA VAZIO
            if (player.getNomePlayer().length() > 0) {
                // MANDO O OBJETO RESPONSAVEL POR SE COMUNICAR COM A PERSISTENCIA, INSERIR O OBJETO JOGADOR
                boolean retorno = persiste.insereJogador(player);
                // SE O RETORNO FOR VEDADEIRO
                if (retorno) {
                    JOptionPane.showMessageDialog(this, "O REGISTRO NÃO FOI INSERIDO!");
                } else {
                    JOptionPane.showMessageDialog(this, "O REGISTRO FOI INSERIDO COM SUCESSO!");
                    // LIMPO A COLEÇÃO DE OBJETOS JOGADORES
                    lstJogador.clear();
                    // EFETUO O RECARREGAMENTO DA COLEÇÃO DE JOGADORES
                    this.carregarArrayList();
                    // REPASSO O MODELO DE TABELA CONTENDO UMA INSTANCIA ,COM A LISTA NO PARAMETRO
                    tabela.setModel(new TabelaModelo(lstJogador));
                    // REPINTO O JFRAME
                    this.repaint();
                    // SETO O CAMPO DE TEXTO COMO NULO
                    txtName.setText(null);
                }
                // SE O CAMPO DE TEXTO FOR VAZIO, ELE MOSTRA A MENSAGEM PARA O USUARIO,QUE O CAMPO É OBRIGATORIO
            } else {
                JOptionPane.showMessageDialog(this, "O CAMPO JOGADOR DEVE SER PREENCHIDO!");
                // REPASSO O FOCO PARA O CAMPO DE TEXTO
                txtName.requestFocus();
            }
        }
    }

    /**
     * MÉTODO QUE CARREGA O ARRAYLIST DE JOGADORES COM OS RESULTADOS ARMAZENADOS
     * NA CAMADA DE PERSISTENCIA
     *
     */
    public void carregarArrayList() {
        // SE O ARRAY LIST FOR VAZIO
        if (lstJogador.isEmpty()) {
            // A COLEÇÃO DE OBJETOS JOGADORES É INICIALIZADA COM O RETORNO DO METODO DO OBJETO PERSISTE
            lstJogador = persiste.retornarJogadores();
            // SETA MODELO PARA A TABELA COMO SENDO UMA INSTÂNCIA DA CLASSE TABELA MODELO,CONTENDO NO PARAMETRO UMA LISTA DE JOGADORES
            tabela.setModel(new TabelaModelo(lstJogador));
        }
    }
}
