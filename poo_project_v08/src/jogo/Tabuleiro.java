/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import java.util.Random;

/**
 *
 * @author lcunha
 */
public class Tabuleiro {

    private static int numPieces=7;
    
    private int[][] myArray = null;
    private int[][] myArrayAux = null;
    
    private int numPontos;

    Tabuleiro() {

        myArray = new int[8][8];
        myArrayAux = new int[8][8];
        
        numPontos=0;

        Random myRandom = new Random();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int auxVar = myRandom.nextInt(numPieces) + 1;
                myArray[i][j] = auxVar;
                myArrayAux[i][j] = auxVar;
            }
        }
    }

    boolean verifyRepetionInColumns() {

        boolean repetitionFound = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                int valueAtIJ = myArray[i][j];
                int contaReps = 1;
                int jj = j + 1;
                while (jj < 8 && myArray[i][jj] == valueAtIJ) {
                    contaReps++;
                    jj++;
                }
                if (contaReps >= 3) {
                    repetitionFound = true;
                    int acrescimoPontos=(int) Math.pow((contaReps-2),2)*10;
                    numPontos+=acrescimoPontos;
                    jj += contaReps;
                    System.out.println("COLUMN REPETITION: Value " + valueAtIJ + " repeated " + contaReps + " times at i=" + i + " j= " + j + " Pontos="+acrescimoPontos);
                    zeroColumn(i, j, contaReps);
                    j=jj;
                    // return repetitionFound;
                }
            }
        }
        return repetitionFound;
    }

    boolean verifyRepetionInLines() {

        boolean repetitionFound = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                int valueAtIJ = myArray[i][j];
                int contaReps = 1;
                int ii = i + 1;
                while (ii < 8 && myArray[ii][j] == valueAtIJ) {
                    contaReps++;
                    ii++;
                }
                if (contaReps >= 3) {
                    repetitionFound = true;
                    int acrescimoPontos=(int) Math.pow((contaReps-2),2)*10;
                    numPontos+=acrescimoPontos;
                    ii += contaReps;
                    System.out.println("LINE REPETITION: Value " + valueAtIJ + " repeated " + contaReps + " times at i=" + i + " j= " + j + " Pontos="+acrescimoPontos);
                    zeroLine(i, j, contaReps);
                    i=ii;
                    return repetitionFound;
                }
            }
        }
        return repetitionFound;
    }

    void zeroColumn(int i, int j, int contaReps) {
        for (int jj = j; jj < (j + contaReps); jj++) {
            myArrayAux[i][jj] = 0;
        }
    }

    void zeroLine(int i, int j, int contaReps) {
        for (int ii = i; ii < (i + contaReps); ii++) {
            myArrayAux[ii][j] = 0;
        }
    }

    void printTabuleiro() {
        System.out.println("\n\n** Array Original: **");
        System.out.print("ij ");
        for (int j = 0; j < 8; j++) {
            System.out.print(j + " ");
        }
        for (int i = 0; i < 8; i++) {
            System.out.println("");
            System.out.print("" + i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + myArray[i][j]);
            }
        }
        System.out.println("\n\n** Auxiliary Array: **");
        System.out.print("ij ");
        for (int j = 0; j < 8; j++) {
            System.out.print(j + " ");
        }
        for (int i = 0; i < 8; i++) {
            System.out.println("");
            System.out.print("" + i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + myArrayAux[i][j]);
            }
        }
        System.out.println("\nSCORE="+numPontos+"\n\n");

    }

    public void verifyRepetitions() {
        printTabuleiro();
        while (this.verifyRepetionInColumns() || this.verifyRepetionInLines()) {
            this.makeFall();
        }
    }

    public void fillTopSpace() {

        Random myRandom = new Random();

        for (int j = 0; j < 8; j++) {
            int i = 0;
            while (i < 8 && myArrayAux[i][j] == 0) {
                myArrayAux[i][j] = myRandom.nextInt(numPieces) + 1;
                i++;
            }
        }

    }

    public void makeFall() {

        System.out.println("\nmakeFall chamado");

        for (int j = 0; j < 8; j++) {
            for (int i = 7; i >= 0; i--) {
                if (myArrayAux[i][j] == 0) {
                    int ii = i - 1;
                    while (ii >= 0 && myArrayAux[ii][j] == 0) {
                        ii--;
                    }
                    if (ii >= 0) {
                        // int auxVar=myArrayAux[i][j];
                        myArrayAux[i][j] = myArrayAux[ii][j];
                        myArrayAux[ii][j] = 0;
                    }
                }
            }
        }

        System.out.println("Before filling top space");
        this.printTabuleiro();

        System.out.println("After filling top space");
        fillTopSpace();
        this.printTabuleiro();
        
        this.copyAuxArrayToOriginalArray();

    }

    public void copyAuxArrayToOriginalArray() {
        
        for (int i = 0; i < 8; i++) {
            System.arraycopy(myArrayAux[i], 0, myArray[i], 0, 8);
        }

    }
    
    String moveHint() {
        
        String nextMoveHint="none";
        
        int aux;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                // check exchange piece to top position, if possible:
                if (i>0) {
                    aux=myArray[i-1][j];
                    myArray[i-1][j]=myArray[i][j];
                    myArray[i][j]=aux;
                    boolean movimentoEncontrado=(this.verifyRepetionInColumns() || this.verifyRepetionInLines());
                    aux=myArray[i-1][j];
                    myArray[i-1][j]=myArray[i][j];
                    myArray[i][j]=aux;                   
                    if (movimentoEncontrado) {
                        nextMoveHint="Slide Up (i,j): ("+i+","+j+") with ("+(i-1)+","+j+")";
                        return nextMoveHint;
                    }
                }
                // check exchange piece to bottom position, if possible:
                if (i<7) {
                    aux=myArray[i+1][j];
                    myArray[i+1][j]=myArray[i][j];
                    myArray[i][j]=aux;
                    boolean movimentoEncontrado=(this.verifyRepetionInColumns() || this.verifyRepetionInLines());
                    aux=myArray[i+1][j];
                    myArray[i+1][j]=myArray[i][j];
                    myArray[i][j]=aux;                   
                    if (movimentoEncontrado) {
                        nextMoveHint="Slide Down (i,j): ("+i+","+j+") with ("+(i+1)+","+j+")";
                        return nextMoveHint;
                    }
                }
                // check exchange piece to left position, if possible:
                if (j>0) {
                    aux=myArray[i][j-1];
                    myArray[i][j-1]=myArray[i][j];
                    myArray[i][j]=aux;
                    boolean movimentoEncontrado=(this.verifyRepetionInColumns() || this.verifyRepetionInLines());
                    aux=myArray[i][j-1];
                    myArray[i][j-1]=myArray[i][j];
                    myArray[i][j]=aux;                   
                    if (movimentoEncontrado) {                        
                        nextMoveHint="Slide Left (i,j): ("+i+","+j+") with ("+i+","+(j-1)+")";
                        return nextMoveHint;
                    }
                }
                // check exchange piece to right position, if possible:
                if (j<7) {
                    aux=myArray[i][j+1];
                    myArray[i][j+1]=myArray[i][j];
                    myArray[i][j]=aux;
                    boolean movimentoEncontrado=(this.verifyRepetionInColumns() || this.verifyRepetionInLines());
                    aux=myArray[i][j+1];
                    myArray[i][j+1]=myArray[i][j];
                    myArray[i][j]=aux;                   
                    if (movimentoEncontrado) {
                        nextMoveHint="Slide Right (i,j): ("+i+","+j+") with ("+i+","+(j+1)+")";
                        return nextMoveHint;
                    }
                }
                
            }
        }
        return nextMoveHint;
    }

    public static void main(String[] args) {

        Tabuleiro myTabuleiro = new Tabuleiro();

        myTabuleiro.verifyRepetitions();

        myTabuleiro.printTabuleiro();
        
        System.out.println(myTabuleiro.moveHint());

    }

}
