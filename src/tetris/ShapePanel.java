package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import shapes.Shape;
import shapes.Shape1;
 
public class ShapePanel extends JPanel {

    public ShapePanel() {
        super(new BorderLayout());
        shape = new Shape1();
        color = shape.getColor();
        preview();
        setSize(X, Y);
        setPreferredSize(new Dimension(X, Y));
    }

    public ShapePanel(int x, int y, Shape s){
        super(new BorderLayout());
        X = x;
        Y = y;
        shape = s;
        color = shape.getColor();
        preview();
        setSize(X, Y);
        setPreferredSize(new Dimension(X, Y));
    }

    private void preview() {
        jPanel1 = new JPanel(new GridLayout(4, 4));
        for (int i = 0; i < 4 * 4; i++) {
            label = new Label(size, numberOfLines, factor);
            label.setSize(X / 4, Y / 4);
            label.setColor(bgcolor, false);

            jPanel1.add(label);
        }

        
        shape.setI(6);
        shape.setX(4);
        shape.setY(4);
        print();
        add(jPanel1);
    }

    private void erase() {
        int[] list = shape.getBricks();
        for (Integer brick : list) {
            if (brick >= 0) {
                ((Label) jPanel1.getComponent(brick)).setColor(bgcolor, false);
            }
        }
    }

    private void print() {
        int[] list = shape.getBricks();
        for (Integer brick : list) {
            if (brick >= 0) {
                ((Label) jPanel1.getComponent(brick)).setColor(color, true);
            }
        }
    }

    public void setLabelVariables(int size, int numberOfLines, int factor) {
        this.size = size;
        this.numberOfLines = numberOfLines;
        this.factor = factor;
        int[] list = shape.getBricks();
        for (Integer brick : list) {
            if (brick >= 0) {
                ((Label) jPanel1.getComponent(brick)).setVariables(size, numberOfLines, factor);
            }
        }

    }

    public void setColor(Color color) {
        this.color = color;
        print();
    }
    
    public Shape getShape(){
        return shape;
    }
    
    public void setShape(Shape shape){
       erase();
       this.shape = shape;
       print();
    }

    public int getSizee(){
        return size;
    }
    
    public int getNumberOfLines(){
        return numberOfLines;
    
    }
    public int getFactor(){
        return factor;
    }
    public Color getColor(){
        return color;
    }

    private Label label;
    private Shape shape;
    private JPanel jPanel1;
    private int X = 160, Y = 160, size = 1, factor = 1, numberOfLines = 1; // variables for Label
    private Color bgcolor = new Color(238, 238, 238), color;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        ShapePanel s = new ShapePanel();
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(s);
        frame.repaint();
        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
