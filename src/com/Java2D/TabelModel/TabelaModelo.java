package com.Java2D.TabelModel;

import com.Java2D.Entity.Jogador;
import java.awt.ScrollPane;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TabelaModelo extends AbstractTableModel {
    
    /*VARIAVEIS ESTATICAS QUE REPRESNTAM CADA COLUNA DA TABELA*/
    public static final int NUM = 0;
    public static final int NUM3 = 1;
    /*COLEÇÃO DE OBJETOS DO TIPO JOGADOR*/
    private ArrayList<Jogador> lista;
    // ARRAY DE INTEIROS QUE CONTEM OS VALORES DAS COLUNAS
    private int[] numeros = {NUM,NUM3};
    // ARRAY DE STRINGS QUE CONTEM O NOME DE CADA COLUNA
    private String[] nomes = {"JOGADOR","SCORE"};
   
    /**
     *  ESSE É O CONSTRUTOR DA CLASSE, É UTILIZADO QUANDO O OBJETO É INSTANCIADO
     * @param arrayL representa uma lista de objetos do tipo jogador que serão recuperados da camada de persistencia
     */
    public TabelaModelo(ArrayList<Jogador> arrayL) {
        lista = arrayL;
    }

    @Override
    public int getRowCount() {
        // retorna a quantidade de linhas da tabela
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        //retornar a quantidade de colunas ;
        return numeros.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // retorna os tipos de objetos das colunas;
        Jogador j = lista.get(rowIndex);
        if (columnIndex == NUM) {
            return j.getNomePlayer();
        } else if (columnIndex == NUM3) {
            return j.getScore();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        // retorna o nome das colunas;
        return nomes[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // retorna o tipo de dado em cada tabela
        if (columnIndex == NUM) {
            return String.class;
        } else if (columnIndex == NUM3) {
            return Integer.class;
        }
        return null;
    }
}
