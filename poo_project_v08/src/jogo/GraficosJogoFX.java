package jogo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class GraficosJogoFX extends Application {

    private static final int SCALE_FACTOR = 1;
    private static final int GEM_SIZE = SCALE_FACTOR*40;
    private static final int GRID_SIZE = SCALE_FACTOR*320;
    
    private int px1, py1, px2, py2;

    private int selOneOrTwo = 1;

    private Button[] btns = new Button[64];

    private LogicaJogo logicaJogo = new LogicaJogo();

    @Override
    public void start(Stage palcoJogo) {

        logicaJogo.preencheTabuleiroDeTeste();
        logicaJogo.mostraTabuleiro();
        logicaJogo.detetarRepeticoes();

        initBtnsArray();

        // System.out.println("ex posicao 2 2 = "+logicaJogo.tabuleiroJogo[2][2]);
        Group agrupamentoPrincipalDeComponentes = new Group();

        agrupamentoPrincipalDeComponentes.getChildren().add(getGrid());

        Scene scene = new Scene(agrupamentoPrincipalDeComponentes, GRID_SIZE, GRID_SIZE);

        palcoJogo.setTitle("Bejeweled!");
        palcoJogo.setScene(scene);
        palcoJogo.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Pane getGrid() {

        
        GridPane gridPane = new GridPane();

        for (int n = 0; n < 7; n++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(GEM_SIZE)); // column 1 is 40 wide
            gridPane.getRowConstraints().add(new RowConstraints(GEM_SIZE)); // column 1 is 40 wide
        }

        int i = 0;
        for (Button b : btns) {
            gridPane.add(b, i % 8, (int) (i / 8));
            i++;
        }
        
        return gridPane;
        
    }

    private void initBtnsArray() {

        int linha;
        int coluna;
        int pecaNessaPosicao;

        for (int i = 0; i < btns.length; i++) {
            linha = (int) (i / 8.0);
            coluna = i % 8;
            //pecaNessaPosicao = logicaJogo.tabuleiroJogo[linha][coluna];
            pecaNessaPosicao = logicaJogo.getTabuleiroJogo()[linha][coluna];
            // System.out.println("linha="+linha+" coluna="+coluna+" peca="+pecaNessaPosicao);
            btns[i] = new Button();
            btns[i].setMinSize(GEM_SIZE, GEM_SIZE);
            btns[i].setMaxSize(GEM_SIZE, GEM_SIZE);
            // btns[i].setStyle("-fx-background-image: url('/imagens/gem" + (myRandomNumber.nextInt(7) + 1) + ".png')");
            btns[i].setStyle("-fx-background-image: url('/imagens/gem" + pecaNessaPosicao + ".png')");
            btns[i].setOnAction(this::handleButtonAction);
        }
    }

    private void displayBtnsArray() {

        int linha;
        int coluna;
        int pecaNessaPosicao;

        for (int i = 0; i < btns.length; i++) {
            linha = (int) (i / 8.0);
            coluna = i % 8;
            pecaNessaPosicao = logicaJogo.getTabuleiroJogo()[linha][coluna];
            btns[i].setStyle("-fx-background-image: url('/imagens/gem" + pecaNessaPosicao + ".png')");
        }
    }
    
    private void movePiece(int px1, int py1, int px2, int py2) {
        
            int auxPieceValue1 = logicaJogo.getTabuleiroJogo()[py1][px1];
            int auxPieceValue2 = logicaJogo.getTabuleiroJogo()[py2][px2];
            
            System.out.println("1st has value: "+auxPieceValue1);
            System.out.println("2nd has value: "+auxPieceValue2);
            

            logicaJogo.setTabuleiroJogo ( py1, px1, auxPieceValue2 );
            logicaJogo.setTabuleiroJogo ( py2, px2, auxPieceValue1 );
            
            logicaJogo.mostraTabuleiro();
            
            displayBtnsArray();
        
        
    }
    
    
    private void handleButtonAction(ActionEvent event) {
       
        for (int i = 0; i < btns.length; i++) {
            if (event.getSource() == btns[i]) {
                if (selOneOrTwo == 1) {
                    System.out.print("First button clicked: ");
                    px1 = i % 8;
                    py1 = i / 8;
                    System.out.println("px1=" + px1 + " py1=" + py1);
                    selOneOrTwo = 2;
                } else if (selOneOrTwo == 2) {
                    System.out.print("Second button clicked: ");
                    px2 = i % 8;
                    py2 = i / 8;
                    System.out.println("px2=" + px2 + " py2=" + py2);
                    movePiece(px1,py1,px2,py2);
                    selOneOrTwo = 1;
                }
            }
        }
    }
}
