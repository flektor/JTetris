package tetris;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
 
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame jFrame1 = new JFrame();
        Panel panel = new Panel();
        panel.getBoard().requestFocus();
        jFrame1.setTitle("Tetris"); 
        jFrame1.setIconImage(Toolkit.getDefaultToolkit().createImage("C:\\Users\\ge\\Documents\\NetBeansProjects\\Tetris\\src\\material\\icons\\tetris.gif"));
        //jFrame1.setLayout(new BorderLayout());
        MenuBar menubar = new MenuBar(panel);
        jFrame1.setJMenuBar(menubar);
        jFrame1.getContentPane().add(panel, BorderLayout.CENTER);
        jFrame1.setResizable(false);
        jFrame1.repaint();
        jFrame1.revalidate();
        jFrame1.pack();
        jFrame1.setVisible(true);
        jFrame1.setLocationRelativeTo(null);
        jFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
