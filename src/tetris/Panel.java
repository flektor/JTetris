package tetris;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class Panel extends JPanel {

    public Panel() {
        newGame();
    }
    
    public void newGame(){
        board = new Board();
        timeLabel = new JLabel("time:  " + board.getTime());
        scoreLabel = new JLabel("score:  " + board.getScore());
        levelLabel = new JLabel("level:  " + board.getLevel());
        board.setTimeLabel(timeLabel);
        board.setScoreLabel(scoreLabel);
        board.setLevelLabel(levelLabel);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        JPanel right = new JPanel(new GridLayout(1, 6));
        JLabel btm = new JLabel("bottom");
        jPanel2 = new JPanel(new BorderLayout());
        jPanel3 = new JPanel(new FlowLayout());
        jPanel1 = new JPanel(new GridLayout(4, 4));
        board.setJPanel2(jPanel1);
        Dimension d = new Dimension(150, 150);
        jPanel1.setPreferredSize(d);
        d = new Dimension(400, 600);
        board.setPreferredSize(d);
        jPanel2.setPreferredSize(d);
        jPanel2.add(board);
        d = new Dimension(100, 100);
        jPanel3.setPreferredSize(d);
        for (int i = 0; i < 4 * 4; i++) {
            Label label = new Label(size ,numberOfLines ,factor);
            label.setColor(board.jLabelColor, false);
            jPanel1.add(label);
        }

        jButton1 = new JButton("pause");
        board.setJButton1(jButton1);
        jButton1.setToolTipText("Esc");
        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                board.pause();
                requestFocus();
            }
        });

        jLabel1 = new JLabel();
        jLabel1.setToolTipText("F8");
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/material/icons/musicOn.png").getPath()));
        jLabel1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (music.isAlive()) {
                    jLabel1.setIcon(new ImageIcon(getClass().getResource("/material/icons/musicOff.png").getPath()));

                    music.stop();
                } else {
                    jLabel1.setIcon(new ImageIcon(getClass().getResource("/material/icons/musicOn.png").getPath()));
                    music = new Thread(new Sound(1));
                    music.start();
                }
                board.requestFocus();
            }
        }
        );
//        music.start();
        
        jLabel2 = new JLabel();
        jLabel2.setToolTipText("F8");
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/material/icons/soundOn.png").getPath()));
        jLabel2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (board.getSound()) {
                    jLabel2.setIcon(new ImageIcon(getClass().getResource("/material/icons/soundOff.png").getPath()));
                    board.setSound(false);
                } else {
                    jLabel2.setIcon(new ImageIcon(getClass().getResource("/material/icons/soundOn.png").getPath()));
                    board.setSound(true);
                }
                board.requestFocus();
            }
        }
        );

        board.setNextShapePanel();
        jPanel3.add(new JLabel("                           "));
        jPanel3.add(jPanel1);
        jPanel3.add(jButton1);

        jPanel3.add(new JLabel("                           "));
        jPanel3.add(jLabel1);
        jPanel3.add(jLabel2);

        jPanel3.add(new JLabel("                           "));
        jPanel3.add(timeLabel);

        jPanel3.add(new JLabel("                           "));
        jPanel3.add(new JLabel("_________"));
        jPanel3.add(new JLabel("                           "));
        jPanel3.add(scoreLabel);

        jPanel3.add(new JLabel("                           "));
        jPanel3.add(new JLabel("_________"));
        jPanel3.add(new JLabel("                           "));
        jPanel3.add(levelLabel);

        jPanel3.add(new JLabel("                           "));

        right.add(jPanel3);

        bottom.add(btm);

        panel.add(top, BorderLayout.NORTH);

        panel.add(jPanel2);
        panel.add(right, BorderLayout.EAST);

        panel.add(
                new Label(), BorderLayout.SOUTH);
        removeAll();
        repaint();
        revalidate();
        add(panel);
    }

    public Board getBoard() {
        return board;
    }
    
    public void setLabelVariables(int size, int numberOfLines, int factor) {
        this.size = size;
        this.numberOfLines = numberOfLines;
        this.factor = factor;
         for (Component label : jPanel2.getComponents() ){
            ((Label) label).setVariables(size, numberOfLines, factor);
        }
    }

    private final static int X = 15, Y = 10;
    private JLabel scoreLabel, timeLabel, levelLabel;
    private JLabel jLabel2, jLabel1;
    private JPanel jPanel1, jPanel2, jPanel3;
    private JButton jButton1;
    private Thread music = new Thread(new Sound(1));
    private Board board;
     private int size = 1, factor = 10, numberOfLines = 12; // variables for Label
}
