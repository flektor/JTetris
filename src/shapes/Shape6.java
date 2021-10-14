package shapes;

import java.awt.Color;
import java.util.ArrayList;
 
public class Shape6 extends Shape {

    public Shape6() {
        choice = 4;
        color = Color.CYAN;
    }


    @Override
    public int[] getBricks() {
        int[] bricks = new int[4];

        switch (choice) {
            case 1:
                bricks[0] = i;
                bricks[1] = i - 1;
                bricks[2] = i + 1;
                bricks[3] = i - Y - 1;
                break;
            case 2:
                bricks[0] = i;
                bricks[1] = i + Y;
                bricks[2] = i - Y;
                bricks[3] = i - Y + 1;
                break;
            case 3:
                bricks[0] = i;
                bricks[1] = i + 1;
                bricks[2] = i - 1;
                bricks[3] = i + Y + 1;
                break;
            case 4:
                bricks[0] = i;
                bricks[1] = i - Y;
                bricks[2] = i + Y - 1;
                bricks[3] = i + Y;
                break;
        }
        return bricks;
    }

    @Override
    public int[] turn(ArrayList<Integer> level) {
        int[] temp = getBricks();
        int tchoice = choice;
        if (choice == 1 ) {
            choice = 2;
        } else if (choice == 2 && i % Y > 0   ) {
            choice = 3;
        } else if (choice == 3 ) {
            choice = 4;
        } else if (choice == 4 && i % Y < Y-1  ) {
            choice = 1;
        }
        if (isPossibleMove(level, 0)){
            return getBricks();
        }
        choice = tchoice;
        return temp;
        
    }

    @Override
    public int[] left(ArrayList<Integer> level) {
        int num = 1;
        if (choice == 2) {
            num = 0;
        }
        if (i % Y > num) 
            if (isPossibleMove(level, -1)) 
                i--;
        return getBricks();
    }

    @Override
    public int[] right(ArrayList<Integer> level) {
        int num = 2;
        if (choice == 4) {
            num = 1;
        }
        if (i % Y < Y - num) {
            if (isPossibleMove(level, 1)) {
                i++;
            }
        }
        return getBricks();
    }
    @Override
    public int[] down(ArrayList<Integer> level) {
        int num = 2;
        if (choice == 1) {
            num = 1;
        }
        if (i / Y < X - num) {
            if (isPossibleMove(level, Y)) {
                i += Y;
            }
        }
        return getBricks();
    }
}
