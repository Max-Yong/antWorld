package com.findnone.game.antworld.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Duyong on 2016/2/15.
 */
@XmlRootElement(name = "W")
public class World {
    private Square[][] squares;        //定义环境二维数组
    private int width;
    private int height;

    public World() {
        width = 10;
        height = 10;
        squares = new Square[width + 2][height + 2];

    }

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        squares = new Square[width + 2][height + 2];

    }

    //init world
    public void init() {
        Random random = new Random();
//        周围墙的设置
        for (int i = 0; i < width + 2; i++) {
            squares[i][0] = new Square(i, 0, true);
            squares[i][width + 1] = new Square(i, width + 1, true);
        }
        for (int j = 0; j < height + 2; j++) {
            squares[0][j] = new Square(0, j, true);
            squares[height + 1][j] = new Square(height + 1, j, true);
        }
        //        squares init
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                //        rount wall
                if (random.nextDouble() < Settings.DISTURBANCE_THRESHOLDS)
                    squares[i][j] = new Square(i, j,true);
                else
                    squares[i][j] = new Square(i, j);
            }
        }
        //nest and food init
        squares[random.nextInt(width)][random.nextInt(height)].setNest(new Nest());
        squares[random.nextInt(width)][random.nextInt(height)].setFood(new Food(100));
    }


    //show world
    public void showworld() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {


                if (squares[i][j].getNest() != null)
                    System.out.print("[N]");
                else if (squares[i][j].getFood() != null)
                    System.out.print("[F]");
                else if (squares[i][j].ants != 0)
                    System.out.print("[" + squares[i][j].ants + "]");
                else
                    System.out.print("[ ]");
            }
            System.out.println();
        }
    }

    //获取x,y坐标周围的方块
    public List<Square> getNearbySquare(int x, int y) {
        //左上为（0，0）起点
        List<Square> squarelist = new ArrayList<Square>();
        //north
        if (y - 1 >= 0)
            squarelist.add(squares[x][y - 1]);
        //south
        if (y + 1 < height)
            squarelist.add(squares[x][y + 1]);
        //west
        if (x - 1 >= 0)
            squarelist.add(squares[x - 1][y]);
        //east
        if (x + 1 < width)
            squarelist.add(squares[x + 1][y]);
//        //northwest
//        if (y - 1 >= 0 && x - 1 >= 0)
//            squarelist.add(squares[x - 1][y - 1]);
//        //northeast
//        if (x - 1 >= 0 && y + 1 < height)
//            squarelist.add(squares[x - 1][y + 1]);
//        //southwest
//        if (x + 1 < width && y - 1 >= 0)
//            squarelist.add(squares[x + 1][y - 1]);
//        //southeast
//        if (x + 1 < width && y + 1 < height)
//            squarelist.add(squares[x + 1][y + 1]);

        return squarelist;
    }

    //查找指定的方块
    public Square getSquare(int x, int y) {
        return squares[x][y];
    }

    public Square[][] getSquares() {
        return squares;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void decayPheromone() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                squares[i][j].getPheromone().decay();
            }
        }
    }
}
