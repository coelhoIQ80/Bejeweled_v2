package jogo;

import javax.swing.JFrame;

public class JanelaJogo extends JFrame {
    
    public JanelaJogo() { }
    
    public static void main(String[] args) { 
    
        JanelaJogo janelaJogo = new JanelaJogo();
        
        // GraficosJogoSwing é equivalente, em Swing, à classe GraficosJogoFX
        // Mas em Swing temos de ter esta classe extra (JanelaJogo) para meter lá os gráficos
        GraficosJogoSwing graficosJogoSwing = new GraficosJogoSwing();
        
        janelaJogo.add(graficosJogoSwing);
        janelaJogo.setSize(640, 640);
        janelaJogo.setResizable(false);
        janelaJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaJogo.show();
    }
}
