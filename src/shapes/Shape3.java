package shapes;

import java.awt.Color;
import java.util.ArrayList;

public class Shape3 extends Shape {

    public Shape3() {
        choice = 2;
        color = Color.GREEN;
    }

    @Override
    public int[] getBricks() {
        int[] bricks = new int[4];
        bricks[0] = i;
        bricks[1] = i - 1;
        bricks[2] = i + Y;
        bricks[3] = i + Y - 1;
        return bricks;
    }

    @Override
    public int[] turn(ArrayList<Integer> level) {
        return getBricks();
    }

    @Override
    public int[] left(ArrayList<Integer> level) {
        if (i % Y > 1) {
            if (isPossibleMove(level, -1)) {
                i--;
            }
        }
        return getBricks();
    }

    @Override
    public int[] right(ArrayList<Integer> level) {
        if (i % Y < Y - 1) {
            if (isPossibleMove(level, 1)) {
                i++;
            }
        }
        return getBricks();
    }

    @Override
    public int[] down(ArrayList<Integer> level) {
        if (i / Y < X - 2) {
            if (isPossibleMove(level, Y)) {
                i += Y;
            }
        }
        return getBricks();
    }

}
