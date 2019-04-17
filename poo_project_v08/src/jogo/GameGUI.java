package jogo;  // os ficheiros de código do projeto ficam neste pacote (os gráficos ficam noutro, chamado "resources"

// imports: como estamos na GUI, são relacionados com JavaFX. 
// quando colocamos alguma classe do JavaFX (Pane, Button, Scene, etc), basta CTRL+SHIFT+I, ou ir ao Menu "Source->Fix Imports", para adicionar
// (os atalhos de teclado / menus referem-se ao IDE Netbeans)

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// além dos imports, uma assinatura clara de uma aplicação JavaFX é o "extends Application":

public class GameGUI extends Application {

    // fields do objeto de tipo GameGUI: notar como se opta por fazê-los privados ("private")
    // se quisermos saber ou alterar os valores destes campos, usamos métodos públicos: getters / setters
    // conceito de POO: ** ENCAPSULAMENTO DE DADOS **
    
    private int px1, py1, px2, py2;
    private int selOneOrTwo = 1;
    private GameLogic myGameEngine = new GameLogic();
    private Button[] btns = new Button[64];
    private Label numPointsLabel = new Label("0");

    @Override
    public void start(Stage primaryStage) {
        myGameEngine.verifyRepetitions();
        initBtnsArray();
        updateScore();
        Group root = new Group();
        HBox gameArena = new HBox();
        gameArena.getChildren().add(getGrid());
        gameArena.getChildren().add(getGamePanel());
        root.getChildren().add(gameArena);
        Scene scene = new Scene(root, 500, 320);
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
            gridPane.getRowConstraints().add(new RowConstraints(40)); // row 1 is 40 wide
        }
        for (Button b : btns) {
            b.setOnAction(this::handlePieceMovementAction);
            gridPane.add(b, i % 8, (int) (i / 8));
            i++;
        }
        return gridPane;
    }

    private void initBtnsArray() {
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new Button();
            btns[i].setPadding(Insets.EMPTY);
            btns[i].setMinSize(40, 40);
            btns[i].setMaxSize(40, 40);
            btns[i].setStyle("-fx-background-image: url('/resources/gem"
                    + (myGameEngine.getMyArrayColLin(i % 8, (int) (i / 8))) + ".png')");
        }
    }

    private VBox getGamePanel() {
        VBox panelVBox = new VBox();
        panelVBox.setPrefWidth(180);
        panelVBox.setMinWidth(180);
        panelVBox.setMaxWidth(180);
        panelVBox.setAlignment(Pos.CENTER);
        panelVBox.setSpacing(10);
        panelVBox.setStyle("-fx-background-color: #EEE; ");
        Label scoreLabel = new Label("Score");
// numPointsLabel=new Label("00000");
        Button hintButton = new Button("Hint");
        hintButton.setOnAction(this::handleHintButtonAction);
        Button exitButton = new Button("Quit");
        exitButton.setOnAction(e -> {
            System.exit(0);
        });
        panelVBox.getChildren().add(scoreLabel);
        panelVBox.getChildren().add(numPointsLabel);
        panelVBox.getChildren().add(hintButton);
        panelVBox.getChildren().add(exitButton);
        return panelVBox;
    }

    private void updateBtnsArray() {
        for (int i = 0; i < btns.length; i++) {
            btns[i].setStyle("-fx-background-image: url('/resources/gem"
                    + (myGameEngine.getMyArrayColLin(i % 8, (int) (i / 8))) + ".png')");
        }
    }

    private void updateScore() {
        int currentScore = myGameEngine.getNumPontos();
        numPointsLabel.setText(String.valueOf(currentScore));
    }

    private void executeMovement() {
        int auxVar = myGameEngine.getMyArrayColLin(px1, py1);
        myGameEngine.setMyArrayColLin(px1, py1, myGameEngine.getMyArrayColLin(px2, py2));
        myGameEngine.setMyArrayColLin(px2, py2, auxVar);
        myGameEngine.verifyRepetitions();
        if (!myGameEngine.getRepetitionFound()) {
            System.out.println("Not forming 3+ series: movement not allowed");
            auxVar = myGameEngine.getMyArrayColLin(px1, py1);
            myGameEngine.setMyArrayColLin(px1, py1, myGameEngine.getMyArrayColLin(px2, py2));
            myGameEngine.setMyArrayColLin(px2, py2, auxVar);
            return;
        }
        updateBtnsArray();
        updateScore();
    }

    private void handlePieceMovementAction(ActionEvent event) {
        System.out.println("Button Clicked!");
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
                    selOneOrTwo = 1;
                }
            }
        }
        if ((selOneOrTwo == 1) && (Math.abs(px1 - px2) + Math.abs(py1 - py2) == 1)) {
            System.out.println("OK: Valid Movement!!");
            executeMovement();
        } else if (selOneOrTwo == 1) {
            System.out.println("ERROR! Invalid Movemnet");
        }
    }

    private void handleHintButtonAction(ActionEvent event) {
        String moveHint = myGameEngine.moveHint();
        System.out.println(moveHint);
        String px1Str = moveHint.substring(14, 15);
        String py1Str = moveHint.substring(16, 17);
        String px2Str = moveHint.substring(25, 26);
        String py2Str = moveHint.substring(27, 28);
        System.out.print("i1=" + px1Str);
        System.out.println(" j1=" + py1Str);
        System.out.print("i2=" + px2Str);
        System.out.println(" j2=" + py2Str);
        int px1 = Integer.parseInt(px1Str);
        int py1 = Integer.parseInt(py1Str);
        int px2 = Integer.parseInt(px2Str);
        int py2 = Integer.parseInt(py2Str);
        int i1 = px1 * 8 + py1;
        int i2 = px2 * 8 + py2;
        btns[i1].setStyle("-fx-border-color: red;"
                + "-fx-border-width: 3;"
                + "-fx-background-image: url('/resources/gem"
                + (myGameEngine.getMyArrayColLin(i1 % 8, (int) (i1 / 8))) + ".png')");
        btns[i2].setStyle("-fx-border-color: red;"
                + "-fx-border-width: 3;"
                + "-fx-background-image: url('/resources/gem"
                + (myGameEngine.getMyArrayColLin(i2 % 8, (int) (i2 / 8))) + ".png')");
    }
}
