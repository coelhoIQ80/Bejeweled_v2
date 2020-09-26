package jogo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GraficosJogoSwing extends JPanel implements MouseListener {

    private int px1 = 100, py1 = 100, px2, py2;
    private int selectedTile1Column, selectedTile1Line, selectedTile2Column, selectedTile2Line;
    
    private int selOneOrTwo = 0;

    // para termos acesso, no lado da Interface, à lógica do jogo:
    private final LogicaJogo logicaJogo = new LogicaJogo();

    // isto não é um método, é o construtor: atenção, por favor ;) 
    public GraficosJogoSwing() {

        // podemos chamar os métodos públicos da lógica de jogo, por exemplo:
        logicaJogo.preencheTabuleiroDeTeste();
        logicaJogo.detetarRepeticoes();
        logicaJogo.mostraTabuleiro();

        // acrescentamos um "MouseListener" ao objeto ("this") 
        // desta classe (GraficosJogoSwing), que é do tipo JPanel:
        this.addMouseListener(this);

    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D graphics2d = (Graphics2D) g;
        
        // getWidth() dá-nos a largura da nossa janela (JFrame), em pixeis
        // getHeight() dá-nos a altura da nossa janela (JFrame), em pixeis
        // sizeX calcula a largura de cada peça, para caberem 8 em cada linha;
        // sizeY calcula a altura de cada peça, para caberem 8 em cada coluna;
        int sizeX = getWidth() / 8;
        int sizeY = getHeight() / 8;
        for (int linha = 0; linha <= 7; linha++) {
            for (int coluna = 0; coluna <= 7; coluna++) {
                Color pieceColor;
                switch (logicaJogo.getTabuleiroJogo()[linha][coluna]) {
                    case 1:
                        pieceColor = Color.MAGENTA;
                        break;
                    case 2:
                        pieceColor = Color.ORANGE;
                        break;
                    case 3:
                        pieceColor = Color.GREEN;
                        break;
                    case 4:
                        pieceColor = Color.CYAN;
                        break;
                    case 5:
                        pieceColor = Color.YELLOW;
                        break;
                    case 6:
                        pieceColor = Color.GRAY;
                        break;
                    case 7:
                        pieceColor = Color.RED;
                        break;
                    default: 
                        pieceColor = Color.BLACK;
                }
                
                
                graphics2d.setColor(pieceColor);
                graphics2d.fillRect(coluna * sizeX, linha * sizeY, coluna * sizeX + sizeX, linha * sizeY + sizeY);
            }
        }
        /*
        System.out.println("\n\n\nTABULEIRO APÓS AS CORES:");
        logicaJogo.mostraTabuleiro();
        */
        
        if (selOneOrTwo == 1) {
            // selectedTile1Column permite obter a coluna da peça 1, com base no sítio onde o jogador clicou
            // selectedTile1Line permite obter a linha da peça 1, com base no sítio onde o jogador clicou
            selectedTile1Column = (px1 / sizeX) % 8;
            selectedTile1Line = (py1 / sizeY);
            System.out.println("Trocar peça na linha: " + selectedTile1Line + " coluna:" + selectedTile1Column);
            graphics2d.setColor(Color.red);
            
            float thickness = 4;
            Stroke oldStroke = graphics2d.getStroke();
            graphics2d.setStroke(new BasicStroke(thickness));
            graphics2d.drawRect(selectedTile1Column * sizeX, selectedTile1Line * sizeY, sizeX, sizeY);
            graphics2d.setStroke(oldStroke);
            
            // this.repaint();            
            selOneOrTwo = 2;
        } else if (selOneOrTwo == 2) {
            selectedTile2Column = (px2 / sizeX) % 8;
            selectedTile2Line = (py2 / sizeY);
            System.out.println("Com a peça na linha: " + selectedTile2Line + " coluna:" + selectedTile2Column);
            graphics2d.setColor(Color.yellow);

            float thickness = 4;
            Stroke oldStroke = graphics2d.getStroke();
            graphics2d.setStroke(new BasicStroke(thickness));
            graphics2d.drawRect(selectedTile2Column * sizeX, selectedTile2Line * sizeY, sizeX, sizeY);
            graphics2d.setStroke(oldStroke);

            

            selOneOrTwo = 1;
            
            System.out.println(System.currentTimeMillis());
            System.out.println( "vamos trocar "+selectedTile1Column+","+selectedTile1Line+" com "+selectedTile2Column+","+selectedTile2Line);

            int auxPieceValue1 = logicaJogo.getTabuleiroJogo()[selectedTile1Line][selectedTile1Column];
            int auxPieceValue2 = logicaJogo.getTabuleiroJogo()[selectedTile2Line][selectedTile2Column];
            
            System.out.println("1st has value: "+auxPieceValue1);
            System.out.println("2nd has value: "+auxPieceValue2);
            

            logicaJogo.setTabuleiroJogo ( selectedTile1Line, selectedTile1Column, auxPieceValue2 );
            logicaJogo.setTabuleiroJogo ( selectedTile2Line, selectedTile2Column, auxPieceValue1 );
            
            logicaJogo.mostraTabuleiro();
            
            this.repaint();
            selOneOrTwo = 0;
            
        }
        
        // Só para efeitos de debug "visual":
        // String posString="x="+(selectedTileX)+" y="+(selectedTileY);
        // graphics2d.drawString(posString, px1, py1);
        
    }

    public void mouseAction(MouseEvent e) {

        if (selOneOrTwo == 0) selOneOrTwo = 1; // just the first time, to indicate the player has clicked the mouse;
        
        if (selOneOrTwo == 1) {
            // First button clicked:
            px1 = e.getX();
            py1 = e.getY();
        } else if (selOneOrTwo == 2) {
            // Second button clicked:
            px2 = e.getX();
            py2 = e.getY();
        }
        
        this.repaint();
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseAction(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {  // https://stackoverflow.com/questions/11453240/mouselistener-in-java-swing-sometimes-not-respond
        mouseAction(e);
    }
    

        // Os seguintes métodos "mouseXXX" têm de ser implementados
    // porque isso faz parte do "contrato" que aceitamos ao dizermos
    // que a nossa classe "GraficosJogoSwing" implementa o interface
    // "MouseListener": todos os métodos declarados nesse interface
    // têm de ser implementados, nem que seja "vazios", ou da maneira
    // que fazemos aqui, lançando uma "exceção" a dizer que o método
    // ainda não foi implementado.
    // Neste exemplo, só precisamos do método "mouseClicked", em cima.
    @Override
    public void mousePressed(MouseEvent arg0) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
