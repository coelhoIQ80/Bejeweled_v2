package jogo;

import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class GUI extends Application {

    int px1, py1, px2, py2;

    int selOneOrTwo = 1;
    
    //## modificado para comunicar com lógica de jogo 
    //## ("LogicaJogo" é a classe com a logica do jogo
    //##  deve ser colocada no mesmo package deste classe)
    LogicaJogo logicaJogo=null;

    private Button[] btns = new Button[64];

    @Override
    public void start(Stage primaryStage) {

        //## modificado para comunicar com lógica de jogo:
        logicaJogo=new LogicaJogo();

        initBtnsArray();
        
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 320, 320);

        primaryStage.setTitle("Bejeweled!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    private Pane getGrid() {
        int i = 0;
        GridPane gridPane = new GridPane();

        for (int n = 1; n <= 8; n++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(40)); // column 1 is 40 wide
            gridPane.getRowConstraints().add(new RowConstraints(40)); // column 1 is 40 wide
        }

        for (Button b : btns) {
        // for (int j=0; j<btns.length; j++) {
            // Button b=btns[j];
            // do something with your button
            // maybe add an EventListener or something
            //gridPane.add(b, i*(i+(int)b.getWidth()), (int) (i/3));
            b.setOnAction(this::handleButtonAction);
            gridPane.add(b, i / 8, (int) (i % 8));
            i++;
        }
        return gridPane;
    }
    
    private void initBtnsArray() {
        // Random myRandomNumber = new Random();
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new Button();
            btns[i].setPadding(Insets.EMPTY);
            btns[i].setMinSize(40, 40);
            btns[i].setMaxSize(40, 40);
            //## modificado para comunicar com lógica de jogo:
            btns[i].setStyle("-fx-background-image: url('/images/gem" + (logicaJogo.tabuleiro[i%8][i/8]) + ".png')");
        }
    }

    private void handleButtonAction(ActionEvent event) {

        for (int i = 0; i < btns.length; i++) {
            if (event.getSource() == btns[i]) {
                if (selOneOrTwo == 1) {
                    System.out.print("First button clicked: ");
                    px1 = i / 8;
                    py1 = i % 8;
                    System.out.println("px1=" + px1 + " py1=" + py1);
                    selOneOrTwo = 2;
                } else if (selOneOrTwo == 2) {
                    System.out.print("Second button clicked: ");
                    px2 = i % 8;
                    py2 = i / 8;
                    System.out.println("px2=" + px2 + " py2=" + py2);
                    selOneOrTwo = 1;
                }
            }
        }
    }
}