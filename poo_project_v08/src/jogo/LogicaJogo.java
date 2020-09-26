package jogo;

import java.util.Random;

public class LogicaJogo {

    private int[][] tabuleiroJogo = new int[8][8];

    // Se quiser ver o método de verificação em ação, tire o comentário do println;
    private boolean verificaSeEsteTipoTipaEAutorizado() {
        // escrever aqui codigo de login, etc, etc, etc
        // System.out.println("Estás a ser verificado!! Big brother is watching you!");

        boolean resultadoDeVerificacao = true;
        return resultadoDeVerificacao;
    }

    public void setTabuleiroJogo(int linha, int coluna, int tipoPeca) {
        tabuleiroJogo[linha][coluna]=tipoPeca;
    }
    
    // usamos um "getter" como exemplo de conceito de encapsulamento;
    // objetivo: controlar acesso a dados privados (tabuleiroJogo, neste caso).
    public int[][] getTabuleiroJogo() {

        boolean esUmUtilizadorAutorizado = verificaSeEsteTipoTipaEAutorizado();

        if (esUmUtilizadorAutorizado) {
            return tabuleiroJogo;
        } else {
            System.out.println("És um candidato a Rui Pinto! Ainda tens de suar muito!");
            return null;
        }
    }

    public void preencheTabuleiroDeTeste() {

        int tabuleiroJogoAux[][] = {
            {7, 6, 3, 5, 3, 4, 1, 3},
            {1, 3, 1, 4, 3, 3, 7, 7},
            {1, 7, 4, 6, 5, 3, 3, 6},
            {6, 3, 5, 1, 3, 3, 2, 7},
            {7, 7, 4, 7, 5, 7, 1, 5},
            {3, 2, 2, 2, 2, 2, 2, 2},
            {5, 1, 6, 3, 2, 7, 6, 3},
            {1, 7, 2, 6, 1, 4, 3, 7}
        };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabuleiroJogo[i][j] = tabuleiroJogoAux[i][j];
            }
        }

    }

    
    public void preencheTabuleiro() {

        Random r = new Random();

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                tabuleiroJogo[j][i] = r.nextInt(7) + 1;
            }
        }
        System.out.println("Tabuleiro Preenchido");
    }
    
    public void mostraTabuleiro() {
        
        System.out.println("\n\n");
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                System.out.print(" " + tabuleiroJogo[j][i]);
            }
            System.out.println("");
        }
    }

    public void detetarRepeticoes() {
        detetarRepeticoesLinhas();
        detetarRepeticoesColunas();
    }
    
    private void detetarRepeticoesLinhas() {
        
        for (int j = 0; j < 8; j++) {  // linhas
            for (int i = 0; i < 6; i++) {  // colunas
                int salto = 0;
                if (tabuleiroJogo[j][i] == tabuleiroJogo[j][i + 1]
                        && tabuleiroJogo[j][i + 1] == tabuleiroJogo[j][i + 2]) {
                    System.out.print("Detetada Repetição de 3 Peças");
                    System.out.print(" na linha " + j + " e coluna " + i + "\n");
                    salto = 3;
                    if (i + 3 < 8 && tabuleiroJogo[j][i + 2] == tabuleiroJogo[j][i + 3]) {
                        System.out.println("E ainda há uma quarta repetição");
                        salto = 4;
                        if (i + 4 < 8 && tabuleiroJogo[j][i + 3] == tabuleiroJogo[j][i + 4]) {
                            System.out.println("E ainda há uma quinta repetição");
                            salto = 5;
                            if (i + 5 < 8 && tabuleiroJogo[j][i + 4] == tabuleiroJogo[j][i + 5]) {
                                System.out.println("E ainda há uma sexta repetição");
                                salto = 6;
                                if (i + 6 < 8 && tabuleiroJogo[j][i + 5] == tabuleiroJogo[j][i + 6]) {
                                    System.out.println("E ainda há uma setima repetição");
                                    salto = 7;
                                    if (i + 7 < 8 && tabuleiroJogo[j][i + 6] == tabuleiroJogo[j][i + 7]) {
                                        System.out.println("E ainda há uma oitava repetição");
                                        salto = 8;
                                    }

                                }
                            }
                        }
                    }
                }

                // colocar 3+ zeros no local indicado por i,j ( o numero de zeros é dado pela variável "salto"
                colocarZerosOndeHaviaRepeticoesLinhas(j,i,salto);
                i += salto;
            }
        }
    }
    
    private void detetarRepeticoesColunas() {

        for (int j = 0; j < 6; j++) {  // linhas
            for (int i = 0; i < 8; i++) {  // colunas
                int salto = 0;
                if (tabuleiroJogo[j + 1][i] == tabuleiroJogo[j][i]
                        && tabuleiroJogo[j + 1][i] == tabuleiroJogo[j+2][i]) {
                    System.out.print("Detetada Repetição de 3 Peças");
                    System.out.print(" na linha " + j + " e coluna " + i + "\n");
                    salto = 3;
                    if (i + 3 < 8 && tabuleiroJogo[j+2][i] == tabuleiroJogo[j+3][i]) {
                        System.out.println("E ainda há uma quarta repetição");
                        salto = 4;
                        if (i + 4 < 8 && tabuleiroJogo[j+3][i] == tabuleiroJogo[j+3][i]) {
                            System.out.println("E ainda há uma quinta repetição");
                            salto = 5;
                            if (i + 5 < 8 && tabuleiroJogo[j+4][i] == tabuleiroJogo[j+5][i]) {
                                System.out.println("E ainda há uma sexta repetição");
                                salto = 6;
                                if (i + 6 < 8 && tabuleiroJogo[j+5][i] == tabuleiroJogo[j+6][i]) {
                                    System.out.println("E ainda há uma setima repetição");
                                    salto = 7;
                                    if (i + 7 < 8 && tabuleiroJogo[j+6][i] == tabuleiroJogo[j+7][i]) {
                                        System.out.println("E ainda há uma oitava repetição");
                                        salto = 8;
                                    }

                                }
                            }
                        }
                    }
                }

                // colocar 3+ zeros no local indicado por i,j ( o numero de zeros é dado pela variável "salto"
                colocarZerosOndeHaviaRepeticoesColunas(j,i,salto);
                i += salto;
            }
        }       
    }

    void colocarZerosOndeHaviaRepeticoesLinhas(int j, int i, int salto) {
        for (int iAux = i; iAux < i + salto; iAux++) {
            tabuleiroJogo[j][iAux] = 0;
        }
    }
    
    void colocarZerosOndeHaviaRepeticoesColunas(int j, int i, int salto) {
        for (int jAux = j; jAux < j + salto; jAux++) {
            tabuleiroJogo[jAux][i] = 0;
        }
    }
    

    /*
    public static void main(String[] args) {

        LogicaJogo jogo = new LogicaJogo();

        // jogo.preencheTabuleiro();
        jogo.preencheTabuleiroDeTeste();
        jogo.mostraTabuleiro();
        jogo.detetarRepeticoesLinhas();
        
    }
     */
}
