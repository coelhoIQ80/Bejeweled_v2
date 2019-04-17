package jogo;

import java.util.Random;

/**
 *
 * @author lcunha
 */
public class GameLogic {     // notar que o nome das classes é sempre em ** Maiusculas **, e o dos objetos dessa classe é sempre em "camel case";

    // fields do objeto de tipo GameLogic: notar como se opta por fazê-los privados ("private")
    // se quisermos saber ou alterar os valores destes campos, usamos métodos públicos: getters / setters
    // conceito de POO: ** ENCAPSULAMENTO DE DADOS **
    
    // declaração de uma constante em Java (notar uso de maíusculas: convenção para constantes)
    // "static final" tornam "NUM_PIECES" numa constante
    private static final int NUM_PIECES = 7;
    
    // quando se declara um array ou um objeto em Java, ou inicializamos logo (com "new", ou colocamos a "null");
    // notar outra convenção de nomes em Java: "camel case": exemplos "nomeVariavel", "numPontos", "meuObjeto","garagemCarrosMotasBicicletas";
    // os tipos primitivos (int, float, double,...) e os objetos devem ter nomes em "camel case";
    // em alguns casos, como nos índices de ciclos ("i","j","n", etc), podemos usar só uma letra, por comodidade, porque o contexto dá informação sobre variável;
    
    private int[][] myArray = null;
    private int[][] myArrayAux = null;
    private int numPontos;
    private boolean repetitionFound;

    // construtor: sempre que um objeto do tipo "GameLogic" é criado, este método é chamado
    
    GameLogic() {
        
        // como o nome indica, um construtor serve para construír objetos de uma classe
        // daí, a inicialização das variáveis é muitas vezes feita aqui
        
        myArray = new int[8][8];
        myArrayAux = new int[8][8];
        numPontos = 0;
        Random myRandom = new Random();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int auxVar = myRandom.nextInt(NUM_PIECES) + 1;
                myArray[i][j] = auxVar;
                myArrayAux[i][j] = auxVar;
            }
        }
    }

    private boolean verifyRepetionInColumns() {
        boolean columnRepetitionFound = false;
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
                    columnRepetitionFound = true;
                    int acrescimoPontos = (int) Math.pow((contaReps - 2), 2) * 10;
                    numPontos += acrescimoPontos;
                    jj += contaReps;
                    System.out.println("COLUMN REPETITION: Value " + valueAtIJ + " repeated" + contaReps + " times at i=" + i + " j= " + j + " Pontos=" + acrescimoPontos);
                    zeroColumn(i, j, contaReps);
                    j = jj;
                    return columnRepetitionFound;
                }
            }
        }
        return columnRepetitionFound;
    }

    public int getMyArrayColLin(int arrayColumn, int arrayLine) {
        return myArray[arrayLine][arrayColumn];
    }

    public void setMyArrayColLin(int arrayColumn, int arrayLine, int intValue) {
        myArray[arrayLine][arrayColumn] = intValue;
        myArrayAux[arrayLine][arrayColumn] = intValue;
    }

    private boolean verifyRepetionInLines() {
        
        boolean lineRepetitionFound = false;
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
                    lineRepetitionFound = true;
                    int acrescimoPontos = (int) Math.pow((contaReps - 2), 2) * 10;
                    numPontos += acrescimoPontos;
                    ii += contaReps;
                    System.out.println("LINE REPETITION: Value " + valueAtIJ + " repeated "
                            + contaReps + " times at i=" + i + " j= " + j + " Pontos="
                            + acrescimoPontos);
                    zeroLine(i, j, contaReps);
                    i = ii;
                    return lineRepetitionFound;
                }
            }
        }
        return lineRepetitionFound;
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
        System.out.println("\nSCORE=" + numPontos + "\n\n");
    }

    public boolean getRepetitionFound() {
        return repetitionFound;
    }

    public void verifyRepetitions() {
        repetitionFound = false;
        printTabuleiro();
        while (this.verifyRepetionInColumns() || this.verifyRepetionInLines()) {
            repetitionFound = true;
            this.makeFall();
        }
    }

    private void fillTopSpace() {
        Random myRandom = new Random();
        for (int j = 0; j < 8; j++) {
            int i = 0;
            while (i < 8 && myArrayAux[i][j] == 0) {
                myArrayAux[i][j] = myRandom.nextInt(NUM_PIECES) + 1;
                i++;
            }
        }
    }

    private void makeFall() {
        System.out.println("\nmakeFall chamado");
        for (int j = 0; j < 8; j++) {
            for (int i = 7; i >= 0; i--) {
                if (myArrayAux[i][j] == 0) {
                    int ii = i - 1;
                    while (ii >= 0 && myArrayAux[ii][j] == 0) {
                        ii--;
                    }
                    if (ii >= 0) {
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
// this.printTabuleiro();
        this.copyAuxArrayToOriginalArray();
        this.printTabuleiro();
    }

    public void copyAuxArrayToOriginalArray() {

        for (int i = 0; i < 8; i++) {
            System.arraycopy(myArrayAux[i], 0, myArray[i], 0, 8);
        }
    }

    public String moveHint() {
        String nextMoveHint = "none";
        int aux;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
// check exchange piece to top position, if possible:
                if (i > 0) {
                    aux = myArray[i - 1][j];
                    myArray[i - 1][j] = myArray[i][j];
                    myArray[i][j] = aux;
                    boolean movimentoEncontrado = (this.verifyRepetionInColumns()
                            || this.verifyRepetionInLines());
                    aux = myArray[i - 1][j];
                    myArray[i - 1][j] = myArray[i][j];
                    myArray[i][j] = aux;
                    if (movimentoEncontrado) {
// nextMoveHint = "Slide Up (i,j): (" + i + "," + j + ") with (" + (i - 1) + "," + j + ")";
// na interface grafica, vamos usar esta mensagem para encontrar (i,j) da peca a mover: por isso, queremos mensagens em que esses valores estejam sempre na mesma posicao, na string:
                        nextMoveHint = "Slide (i,j): (" + i + "," + j + ") with (" + (i - 1)
                                + "," + j + ")";
                        return nextMoveHint;
                    }
                }
// check exchange piece to bottom position, if possible:
                if (i < 7) {
                    aux = myArray[i + 1][j];
                    myArray[i + 1][j] = myArray[i][j];
                    myArray[i][j] = aux;
                    boolean movimentoEncontrado = (this.verifyRepetionInColumns()
                            || this.verifyRepetionInLines());
                    aux = myArray[i + 1][j];
                    myArray[i + 1][j] = myArray[i][j];
                    myArray[i][j] = aux;
                    if (movimentoEncontrado) {
// nextMoveHint = "Slide Down (i,j): (" + i + "," + j + ") with (" +(i + 1) + "," + j + ")";
// na interface grafica, vamos usar esta mensagem para encontrar (i,j) da peca a mover: por isso, queremos mensagens em que esses valores estejam sempre na mesma posicao, na string:
                        nextMoveHint = "Slide (i,j): (" + i + "," + j + ") with (" + (i + 1)
                                + "," + j + ")";
                        return nextMoveHint;
                    }
                }
// check exchange piece to left position, if possible:
                if (j > 0) {
                    aux = myArray[i][j - 1];
                    myArray[i][j - 1] = myArray[i][j];
                    myArray[i][j] = aux;
                    boolean movimentoEncontrado = (this.verifyRepetionInColumns()
                            || this.verifyRepetionInLines());
                    aux = myArray[i][j - 1];
                    myArray[i][j - 1] = myArray[i][j];
                    myArray[i][j] = aux;
                    if (movimentoEncontrado) {
// nextMoveHint = "Slide Left (i,j): (" + i + "," + j + ") with (" +i + "," + (j - 1) + ")";
// na interface grafica, vamos usar esta mensagem para encontrar (i,j) da peca a mover: por isso, queremos mensagens em que esses valores estejam sempre na mesma posicao, na string:
                        nextMoveHint = "Slide (i,j): (" + i + "," + j + ") with (" + i + ","
                                + (j - 1) + ")";
                        return nextMoveHint;
                    }
                }
// check exchange piece to right position, if possible:
                if (j < 7) {
                    aux = myArray[i][j + 1];
                    myArray[i][j + 1] = myArray[i][j];
                    myArray[i][j] = aux;
                    boolean movimentoEncontrado = (this.verifyRepetionInColumns()
                            || this.verifyRepetionInLines());
                    aux = myArray[i][j + 1];
                    myArray[i][j + 1] = myArray[i][j];
                    myArray[i][j] = aux;
                    if (movimentoEncontrado) {
// nextMoveHint = "Slide Right (i,j): (" + i + "," + j + ") with ("+ i + "," + (j + 1) + ")";
// na interface grafica, vamos usar esta mensagem para encontrar (i,j) da peca a mover: por isso, queremos mensagens em que esses valores estejam sempre na mesma posicao, na string:
                        nextMoveHint = "Slide (i,j): (" + i + "," + j + ") with (" + i + ","
                                + (j + 1) + ")";
                        return nextMoveHint;
                    }
                }
            }
        }
        return nextMoveHint;
    }

    /*
    private void moveAction() {  

        // este método não é usado na versão com interface gráfica
        // obtem input do utilizador para mover peça
        // foi apenas usado na fase em que ainda não havia GUI

        Scanner myScanner = new Scanner(System.in);
        System.out.print("Enter i (line):");
        int iPosition = myScanner.nextInt();
        System.out.print("Enter j (line):");
        int jPosition = myScanner.nextInt();
        System.out.print("Enter direction: 1-up, 2-down, 3-left, 4-right");
        int moveDirection = myScanner.nextInt();
        int aux = this.myArrayAux[iPosition][jPosition];
        
        //verifica se movimento para direita é válido:
        if (moveDirection == 4 && jPosition < 7) {
            this.myArray[iPosition][jPosition] = aux;
            this.myArrayAux[iPosition][jPosition] = aux;
            this.myArray[iPosition][jPosition] = this.myArray[iPosition][jPosition + 1];
            this.myArrayAux[iPosition][jPosition] = this.myArrayAux[iPosition][jPosition + 1];
            this.myArray[iPosition][jPosition + 1] = aux;
            this.myArrayAux[iPosition][jPosition + 1] = aux;
            System.out.println("Movimento para direita efetuado!");
        }
        this.makeFall();
    }
    */
    
    // getter do numPontos: ver bem convenção de nomes: get minusculo, numPontos passa a NumPontos
    // é sempre assim...
    public int getNumPontos() {
        return numPontos;
    }
}
