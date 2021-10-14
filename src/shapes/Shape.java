package shapes;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Shape {

    public Shape() {
        i = 5;
        choice = 1;
    }

    public boolean isPossibleMove(ArrayList<Integer> list, int num) {
        /*  Finds out if the next move is possible!
         The list contains the bricks in the board.
         Every brick is a cell of the GridLayout.           
         */
        int[] bricks = getBricks();
        for (Integer brick : bricks) {
            for (Integer l : list) {
                if (l == brick + num ) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] space(ArrayList<Integer> level) {
        int[] b = down(level);
        while (down(level)[0] != b[0]) {
            b = down(level);
        }
        return getBricks();
    }

    public void setI(int x) {
        i = x;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public Color getColor() {
        return color;
    }

    public int getI() {
        return i;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    protected int i, choice;
    protected int Y = 10, X = 15;
    protected Color color;

    public abstract int[] getBricks();

    public abstract int[] turn(ArrayList<Integer> level);

    public abstract int[] left(ArrayList<Integer> level);

    public abstract int[] right(ArrayList<Integer> level);

    public abstract int[] down(ArrayList<Integer> level);
    

}
