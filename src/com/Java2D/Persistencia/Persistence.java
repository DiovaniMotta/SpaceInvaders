/*
 * Essa Classe é a responsável por Efetuar a comunicação
 * entre a camada de Aplicação e a Camada de Persistência
 */
package com.Java2D.Persistencia;

import com.Java2D.Entity.Jogador;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diovani Bernardi da Motta
 */
public class Persistence {

    // OBJETO RESPONSÁVEL POR CRIAR A CONEXAO ENTRE A CAMADA FISICA E DE APLICACAO
    private Connection conexao;
    // OBJETO RESPONSAVEL POR  EXECUTAR UM COMANDO SQL
    private PreparedStatement con;
    //OBJETO RESPONSÁVEL POR ARMAZENAR O RESULTADO DO COMANDO SQL
    private ResultSet result;
    // VARIAVEL QUE GUARDA O USUARIO DO BANCO DE DADOS
    private String usuario = "root";
    //VARIAVEL QUE GUARDA A SENHA DO BANCO DE DAODS
    private String password = "19120822";
    // VARIAVEL QUE GUARDA O SERVIDOR DE BANCO DE DADOS
    private String servidorDataBase = "localhost";
    // VARIAVEL QUE GUARDA O NOME DO BANCO DE DADOS
    private String bancoDados = "spaceInvaders";
    // VARIVEL QUE  ARMAZENA O ENDERECO DE CONEXAO COM  A CAMADA FISICA;
    private String url = "jdbc:mysql://" + servidorDataBase + "/" + bancoDados;
    // VARIVEL QUE EXECUTA UM COMANDO SQL
    private String querySql = "USE spaceInvaders";
    // OBJETO RESPONSÁVEL POR ARMAZENAR O ARRAYLIST DE JOGADOR RETORNADOS DE UMA CONSULTA; 
    private ArrayList<Jogador> lstJogadors = new ArrayList<>();

    /* CONSTRUTORES DA CLASSE */
    public Persistence() {
        try {
            // CAPTURA A CONEXAO COM O BANCO DE DADOS
            conexao = (Connection) DriverManager.getConnection(url, usuario, password);
            con = conexao.prepareStatement(querySql);
        } catch (SQLException ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Persistence(String usuario, String password, String Servidor) {
        /*INICIALIZO AS VARIAVEIS DE INSTANCIA*/
        this.usuario = usuario;
        this.password = password;
        this.servidorDataBase = Servidor;

        try {
            // CAPTURA A CONEXAO COM O BANCO DE DADOS
            conexao = (Connection) DriverManager.getConnection(url, usuario, password);
            con = conexao.prepareStatement(querySql);
        } catch (SQLException ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*GETTERS AND SETTERS */
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServidorDataBase() {
        return servidorDataBase;
    }

    public void setServidorDataBase(String servidorDataBase) {
        this.servidorDataBase = servidorDataBase;
    }

    // METODO RESPONSAVEL POR INSERIR O OBJETO JOGADOR NA CAMADA DE PERSISTENCIA
    public boolean insereJogador(Jogador jogador) {
        //STRING SQL QUE REPRESENTA UM COMANDO DML PARA O BANCO DE DADOS
        String insertJogador = "INSERT INTO pontuacao(nomeJogador,score,live)VALUES(?,?,?);";
        // VARIAVEL QUE CONTROLA SE A OPERAÇÃO DE INSERÇÃO NA CAMADA DE PERSISTENCIA OCOREU COM SUCESSO OU NAO
        boolean retorno = false;
        try {
            // PREPARO A EXECUÇÃO DO COMANDO SQL DE INSERÇÃO
            con = conexao.prepareStatement(insertJogador);
            /*SETO O VALOR RECEBIDO PARA CADA ATRIBUTO DO OBJETO JOGADOR SEGUINDO A SEQUENCIA REPASSADA NA STRING INSERTJOGAODOR*/
            con.setString(1, jogador.getNomePlayer());
            con.setInt(2, jogador.getScore());
            con.setInt(3, jogador.getLives());
            // RECUPERO O RETORNO DO METODO QUE EXECUTA A OPERAÇÃO NO BANCO DE DADOS
            retorno = con.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    // METODO RESPONSAVEL POR RETORNAR UMA LISTA DE JOGADORES DA CONSULTA REALIZADA NO BANCO DE DADOS
    public ArrayList<Jogador> retornarJogadores() {
        try {
            // DETERMINO CONSULTA NO BANCO DE DADOS
            con = conexao.prepareStatement("SELECT nomeJogador,score FROM pontuacao ORDER BY score DESC LIMIT 5;");
            // EXECUTO A CONSULTA
            result = con.executeQuery();
            // ENQUANTO HOUVER RESULTADOS DA CONSULTA REALIZADA
            while (result.next()) {
                // INSTACIO OBJETO JOGADOR
                Jogador controle = new Jogador();
                // CAPTURO VALOR  ARMAZENADO NA PRIMEIRA COLUNA DA MINHA CONSULTA E ATRIBUO AO OBJETO JOGADOR
                controle.setNomePlayer(result.getNString("nomeJogador"));
                // CAPTURO VALOR  ARMAZENADO NA SEGUNDA COLUNA DA MINHA CONSULTA E ATRIBUO AO OBJETO JOGADOR
                controle.setScore(result.getInt("score"));

                // ADICIONO OBJETO AO MEU ARRAY
                lstJogadors.add(controle);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }

        // RETORNO ARRAY CARREGADO COM TODOS OS OBJETOS SALVOS NA CONSULTA
        return lstJogadors;
    }

    /* MÉTODO RESPONSÁVEL POR FINALIZAR A CONEXAO COM A PERSISTENCIA */
    public void desconectar() {
        try {
            /*ENCERRO A CONEXÃO DE TODOS OS OBJETOS QUE TEM VINCULO COM O BANCO DE DADOS*/
            result.close();
            con.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
