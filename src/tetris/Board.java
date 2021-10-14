package tetris;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import shapes.*;

public class Board extends JPanel implements KeyListener {

    public Board() {
        setLayout(new BorderLayout());
        jPanel1 = new JPanel(new GridLayout(X, Y));
        Label label;
        for (int i = 0; i < X * Y; i++) {
            label = new Label(size, numberOfLines, factor);
            label.setVisible(true);
            jPanel1.add(label);
        }
        shape = newShape();
        nextShape = newShape();
        currentShape = nextShape;
        print(shape, jPanel1);
        bricks = new ArrayList<>();
        temp = -1;
        add(jPanel1);

        timer = new Timer(speed, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                erase(shape, jPanel1, Color.GRAY);
                shape.down(bricks);
                print(shape, jPanel1);
                finalPosition();
            }
        });
        timer.setRepeats(true);
        timer.start();
        gametimer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int sec = 0, min = 0;
                time++;
                sec = time % 60;
                min = time / 60;
                if (min > 0 && sec < 10) {
                    timeLabel.setText("time:  " + min + ":0" + sec + " m");
                } else if (min > 0) {
                    timeLabel.setText("time:  " + min + ":" + sec + " m");
                } else {
                    timeLabel.setText("time:  " + sec + " s");
                }

                if (score > level * level * 100) {
                    level++;
                    timer.setDelay(speed -= 50);
                    levelLabel.setText("level:  " + level);
                }
            }
        });
        gametimer.setRepeats(true);
        gametimer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
    }

    private void finalPosition() {
        if (shape.getI() == temp) {      //  if  the brick is in the same position as before
            int[] b = shape.getBricks();
            for (int i = 0; i < b.length; i++) {    // add the bricks to the brick list of the board 
                bricks.add(b[i]);
            }
            shape = nextShape;
            nextShape = newShape();
            setNextShapePanel();
            print(shape, jPanel1);
            findCompletedRows();
            repaint();
            revalidate();
        } else {
            temp = shape.getI();
        }

    }

    private void findCompletedRows() {
        bricks = bubbleSort(bricks);
        //  Removes out of range bricks 
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i) < 0 || bricks.get(i) > X * Y) {
                bricks.remove(i);
            } else {
                break;
            }
        }
        /* 
         10 bricks in a row is a completed row, variable  Y = 10 equals to the width of the board.
         Finds out if there are completed rows.        
         For each completed row removes all the bricks one by one and then moves all the bricks above it down by 1 row.
         */
        int i = 0;
        ArrayList<Integer> rows = new ArrayList<>();
        while (i < bricks.size() - Y + 1) {

            if (bricks.get(i + Y - 1) - bricks.get(i) == Y - 1 && bricks.get(i) % Y == 0) {
                bricks = bubbleSort(bricks);
                // removes the completed row brick by brick
                int temp = i + Y - 1;
                int size = bricks.get(temp);
                int r = bricks.get(i);
                for (int x = size; x >= r; x--) {
                    bricks.remove(temp--);
                    ((Label) jPanel1.getComponent(x)).setColor(Color.GRAY, false);
                    i--;
                }
                if (!rows.contains(r / Y)) {
                    rows.add(r / Y);
                }
                i += Y - 1;

                //  update score
                score += 10;
                scoreLabel.setText("score:  " + score);
                //  make sound
                if (isSoundEnable) {
                    Thread soundThread = new Thread(new Sound(7));
                    soundThread.start();
                }
            }
            i++;

        }
        // rows list contains the completed rows that removed
        rows = bubbleSort(rows);
        for (Integer row : rows) {
            dropBricks(row);
        }
        bricks = bubbleSort(bricks);
    }

    public void dropBricks(int size) {
        /*
         Every brick less than size moves to the next row
         */
        size = size * Y;
        for (int b = bricks.size() - 1; b >= 0; b--) {
            int brick = bricks.get(b);
            if (brick < size) {
                Color c = ((Label) jPanel1.getComponent(brick)).getColor();
                ((Label) jPanel1.getComponent(brick + Y)).setColor(c, true);
                ((Label) jPanel1.getComponent(brick)).setColor(Color.GRAY, false);
                bricks.set(b, brick + Y);
            }
        }
    }

    public ArrayList<Integer> bubbleSort(ArrayList<Integer> list) {
        for (int pass = 1; pass < list.size(); pass++) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) > list.get(i + 1)) {
                    int temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
        }
        return list;
    }

    public void setNextShapePanel() {
        Shape t1 = currentShape;
        Shape t2 = nextShape;
        currentShape = nextShape;
        t1.setI(6);
        t2.setI(6);
        t1.setX(4);
        t2.setX(4);
        t1.setY(4);
        t2.setY(4);
        for (int i = 0; i < 4 * 4; i++) {
            ((Label) jPanel2.getComponent(i)).setColor(jLabelColor, false);
        }
        print(t2, jPanel2);
        t1.setX(X);
        t2.setX(X);
        t1.setY(Y);
        t2.setY(Y);
    }

    public void pause() {
        
        
        isPaused = !isPaused;
        
        if (isPaused) {
            JLabel label = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setComposite(AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER, 0.3f));
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(0, 0, 400, 600);
                    g2d.setFont(new Font(TOOL_TIP_TEXT_KEY, 1, 100));
                    g2d.drawString("pause", 50, 300);

                }

            };
            removeAll();
           // label.setOpaque(true);
            add(label);

        board.requestFocus();
            timer.stop();
            gametimer.stop(); 
            jButton1.setText("resume");
        } else {

            removeAll();
            add(jPanel1);
            timer.restart();
            gametimer.restart();
            jButton1.setText("pause");
        }
        
        repaint();
        revalidate();
    }

    public Shape newShape() {
        Random rand = new Random();
        int r = rand.nextInt(7) + 1;
        switch (r) {
            case 1:
                return new Shape1();
            case 2:
                return new Shape2();
            case 3:
                return new Shape3();
            case 4:
                return new Shape4();
            case 5:
                return new Shape5();
            case 6:
                return new Shape6();
            case 7:
                return new Shape7();
            default:
                return new Shape1();
        }
    }

    private void erase(Shape shape, JPanel panel, Color c) {
        int[] list = shape.getBricks();
        for (Integer brick : list) {
            if (brick >= 0) {
                ((Label) panel.getComponent(brick)).setColor(Color.GRAY, false);
            }
        }
    }

    private void print(Shape shape, JPanel panel) {
        int[] list = shape.getBricks();
        for (Integer brick : list) {
            if (brick >= 0) {
                ((Label) panel.getComponent(brick)).setColor(shape.getColor(), true);
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    public JPanel getPanel() {
        return jPanel1;
    }

    public void setJButton1(JButton b) {
        jButton1 = b;
    }

    public void setJPanel2(JPanel panel) {
        jPanel2 = panel;
    }

    public void setTimeLabel(JLabel jLabel) {
        timeLabel = jLabel;
    }

    public void setScoreLabel(JLabel jLabel) {
        scoreLabel = jLabel;
    }

    public void setLevelLabel(JLabel jLabel) {
        levelLabel = jLabel;
    }

    public void setLabelVariables(int size, int numberOfLines, int factor) {
        this.size = size;
        this.numberOfLines = numberOfLines;
        this.factor = factor;
        for (Component label : jPanel2.getComponents()) {
            ((Label) label).setVariables(size, numberOfLines, factor);
        }

    }

    public void setSound(boolean b) {
        isSoundEnable = b;
    }

    public Boolean getSound() {
        return isSoundEnable;
    }

    private JPanel jPanel1, jPanel2;
    private final static int X = 15, Y = 10;
    private static Shape shape, nextShape, currentShape;
    private ArrayList<Integer> bricks;
    private int temp, speed = 700, score = 0, time = 0, level = 0;
    private Timer timer, gametimer;
    private JButton jButton1;
    private JLabel scoreLabel, timeLabel, levelLabel;
    private Boolean isSoundEnable = true;
    public Color jLabelColor = new Color(238, 238, 238);
    private int size = 1, factor = 10, numberOfLines = 12; // variables for Label

    boolean isPaused = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!isPaused) {
            if (keyCode == KeyEvent.VK_UP) {

                erase(shape, jPanel1, Color.GRAY);
                shape.turn(bricks);
                print(shape, jPanel1);

                if (shape.isPossibleMove(bricks, 1)) {
                    if (isSoundEnable) {
                        Thread soundThread = new Thread(new Sound(8));
                        soundThread.start();
                    }
                }

            } else if (keyCode == KeyEvent.VK_LEFT) {

                erase(shape, jPanel1, Color.GRAY);
                shape.left(bricks);
                print(shape, jPanel1);

            } else if (keyCode == KeyEvent.VK_RIGHT) {

                erase(shape, jPanel1, Color.GRAY);
                shape.right(bricks);
                print(shape, jPanel1);

            } else if (keyCode == KeyEvent.VK_DOWN) {

                erase(shape, jPanel1, Color.GRAY);
                shape.down(bricks);
                print(shape, jPanel1);

                if (!shape.isPossibleMove(bricks, 1)) {
                    if (isSoundEnable) {
                        Thread soundThread = new Thread(new Sound(6));
                        soundThread.start();
                    }
                }
                finalPosition();
                scoreLabel.setText("score:  " + ++score);

            } else if (keyCode == KeyEvent.VK_SPACE) {

                erase(shape, jPanel1, Color.GRAY);
                shape.space(bricks);
                print(shape, jPanel1);

                if (!shape.isPossibleMove(bricks, 1)) {
                    if (isSoundEnable) {
                        Thread soundThread = new Thread(new Sound(6));
                        soundThread.start();
                    }
                }
                finalPosition();

            }
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            pause();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        board = new Board();
        JFrame frame = new JFrame();
        board.requestFocus();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Tetris");
        frame.getContentPane().add(board, BorderLayout.CENTER);
        frame.setSize(400, 600);
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private static Board board;
}
