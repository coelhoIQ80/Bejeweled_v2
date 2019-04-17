package jogo;

import java.util.Random;

public class LogicaJogo {

    int[][] tabuleiro;
    private int[][] tabuleiroAuxiliar;

    public LogicaJogo() {
        tabuleiro = new int[8][8];
        tabuleiroAuxiliar = new int[8][8];
        preencheTabuleiroTeste();  // para efeitos de teste, apenas;
        imprimeTabuleiro("Tabuleiro inicial");
        verificaRepeticoes();
        imprimeTabuleiro("Tabuleiro inicial2");
        copiaArrayAuxParaArrayOriginal(); //tabuleiro = tabuleiroAuxiliar;
        imprimeTabuleiro("Tabuleiro após verificar repetições");
    }

    public void copiaArrayAuxParaArrayOriginal() {

        for (int i = 0; i < 8; i++) {
            System.arraycopy(tabuleiroAuxiliar[i], 0, tabuleiro[i], 0, 8);
        }
    }

    private void preencheTabuleiroTeste() {

        int[][] tstTabuleiro = {
            {1, 2, 3, 4, 5, 2, 1, 2},
            {1, 1, 1, 7, 6, 1, 1, 1},
            {1, 4, 5, 7, 7, 3, 1, 2},
            {4, 5, 6, 7, 4, 1, 2, 3},
            {5, 6, 7, 7, 7, 3, 3, 3},
            {6, 7, 3, 7, 2, 3, 4, 5},
            {6, 6, 6, 7, 3, 4, 5, 6},
            {6, 1, 2, 7, 4, 5, 6, 7}
        };

        tabuleiroAuxiliar = tstTabuleiro;
        copiaArrayAuxParaArrayOriginal();

    }

    private void preencheTabuleiro() {

        Random geradorNumeros = new Random();

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                int pecaInicial = 1 + geradorNumeros.nextInt(7);
                tabuleiro[linha][coluna] = pecaInicial;
                tabuleiroAuxiliar[linha][coluna] = pecaInicial;
            }
        }
    }

    private void imprimeTabuleiro() {
        imprimeTabuleiro("");
    }

    private void imprimeTabuleiro(String tituloImpressao) {

        System.out.println(tituloImpressao);
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                System.out.print(" " + tabuleiro[linha][coluna]);
            }
            System.out.println("");
        }

        System.out.println("Tabuleiro Auxiliar");
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                System.out.print(" " + tabuleiroAuxiliar[linha][coluna]);
            }
            System.out.println("");
        }

    }

    public boolean verificaRepeticaoLinhas() {

        boolean repeticaoEncontrada = false;
        for (int coluna = 0; coluna < 8; coluna++) {
            for (int linha = 0; linha < 6; linha++) {
                
                int contaRepeticoes = 0;
                int linhaDeBaixo = linha + 1;
                while (linhaDeBaixo <= 7 &&  tabuleiro[linha][coluna]
                        == tabuleiro[linhaDeBaixo][coluna]) {
                    linhaDeBaixo++;
                    contaRepeticoes++;
                }
                // if (contaRepeticoes >= 2 && tabuleiroAuxiliar[linha][coluna] != 0) {
                if (contaRepeticoes >= 2) {
                    System.out.println("O numero "
                            + tabuleiro[linha][coluna]
                            + " repetiu-se " + contaRepeticoes + " vezes "
                            + " na coluna " + coluna);
                    apagaRepeticaoLinhas(coluna, linha, contaRepeticoes);
                    linha += contaRepeticoes;
                    repeticaoEncontrada = true;
                }
            }
        }
        System.out.println("Repetição encontrada linhas=" + repeticaoEncontrada);
        return repeticaoEncontrada;
    }

    private void apagaRepeticaoLinhas(int coluna, int linha, int contaRepeticoes) {

        for (int borracha = 0; borracha <= contaRepeticoes; borracha++) {
            tabuleiroAuxiliar[linha + borracha][coluna] = 0;
        }
    }

    public boolean verificaRepeticaoColunas() {

        boolean repeticaoEncontrada = false;
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 6; coluna++) {
                int contaRepeticoes = 0;
                int colunaDaDireita = coluna + 1;

                while (colunaDaDireita <= 7 && 
                        tabuleiro[linha][coluna] == tabuleiro[linha][colunaDaDireita]) {
                    colunaDaDireita++;
                    contaRepeticoes++;
                }
                if (contaRepeticoes >= 2) {
                    System.out.println("O numero "
                            + tabuleiro[linha][coluna]
                            + " repetiu-se " + contaRepeticoes + " vezes "
                            + " na linha " + linha);
                    apagaRepeticaoColunas(coluna, linha, contaRepeticoes);
                    coluna += contaRepeticoes;
                    repeticaoEncontrada = true;
                }
            }
        }
        System.out.println("Repetição encontrada colunas=" + repeticaoEncontrada);
                        
        return repeticaoEncontrada;
    }

    private void apagaRepeticaoColunas(int coluna, int linha, int contaRepeticoes) {

        for (int borracha = 0; borracha <= contaRepeticoes; borracha++) {
            tabuleiroAuxiliar[linha][coluna + borracha] = 0;
        }
    }

    private void verificaRepeticoes() {

        verificaRepeticaoLinhas();
        verificaRepeticaoColunas();
        
    }
}
