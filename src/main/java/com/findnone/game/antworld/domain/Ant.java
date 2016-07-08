package com.findnone.game.antworld.domain;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Duyong on 2016/2/14.
 */
@XmlRootElement(name = "A")
public class Ant {
    //位置参数
    int position_x;
    int position_y;
    //血量
    int hp;
    //能力
    int power;
    //食物量
    int food;
    //健康程度
    int health;
    //特质
    String att;
    Direction direction;
//    历史上走过的路径
    Square[] path;
    int pheromone;

    boolean foodornest;
    public Ant(int x, int y) {
        position_x = x;
        position_y = y;
        Random r = new Random();
        switch (r.nextInt(Direction.values().length)) {
            case 0:
                direction = Direction.E;
                break;
            case 1:
                direction = Direction.S;
                break;
            case 2:
                direction = Direction.W;
                break;
            case 3:
                direction = Direction.N;
                break;
//            case 4:
//                direction = Direction.SE;
//                break;
//            case 5:
//                direction = Direction.SW;
//                break;
//            case 6:
//                direction = Direction.NE;
//                break;
//            case 7:
//                direction = Direction.NW;
//                break;
        }
        pheromone = 0;
    }

    public void move(World world) {
        //当前方块减少一只蚂蚁
        world.getSquare(position_x, position_y).ants--;
        System.out.println("before|x:" + position_x + "|y:" + position_y);
        //检查方向可以走的方向，如果靠边了。返回可以走的不同的方向，没靠边，则返回零数量
        List<Direction> directions = checkDirection(world.getHeight(), world.getWidth(), position_x, position_y);

        //确定信息素，走信息素最多的方向，同时不走回头路
        world.getSquare(position_x,position_y).getPheromone();

        if (directions.size() != 0) {
            Random r = new Random();
            int directIndex = r.nextInt(directions.size());
            direction = directions.get(directIndex);
        } else {

            //原方向行走，同时小概率随机扰动，不走回头路
            Random r = new Random();
            if (r.nextDouble() < Settings.DISTURBANCE_THRESHOLDS) {
                int directIndex = r.nextInt(8);
                Direction[] li = Direction.values();
                direction = li[directIndex];
            }
        }

        System.out.println("directs:" + directions + "|direct:" + direction);
        switch (direction) {
            case E:
                position_x++;
                break;
            case S:
                position_y++;
                break;
            case W:
                position_x--;
                break;
            case N:
                position_y--;
                break;
//            case SE:
//                position_x++;
//                position_y++;
//                break;
//            case SW:
//                position_x--;
//                position_y++;
//                break;
//            case NE:
//                position_x++;
//                position_y--;
//                break;
//            case NW:
//                position_x--;
//                position_y--;
//                break;
        }
        System.out.println("end|x:" + position_x + "|y:" + position_y);
        world.getSquare(position_x, position_y).ants++;
    }
    //检查可以移动的方向(环境检查)
    private List<Direction> checkDirection(int height, int width, int x, int y) {
        //
        List<Direction> squarelist = new ArrayList<>();
        //4边4角排除方向
        //northwest
        if (y <= 0 && x <= 0) {
            squarelist.add(Direction.E);
            squarelist.add(Direction.S);
//            squarelist.add(Direction.SE);
            return squarelist;
        }
        //northeast
        if (y <= 0 && x + 1 >= width) {
            squarelist.add(Direction.W);
            squarelist.add(Direction.S);
//            squarelist.add(Direction.SW);
            return squarelist;
        }
        //southwest
        if (x <= 0 && y + 1 >= height) {
            squarelist.add(Direction.E);
            squarelist.add(Direction.N);
//            squarelist.add(Direction.NE);
            return squarelist;
        }
        //southeast
        if (x + 1 >= width && y + 1 >= height) {
            squarelist.add(Direction.W);
            squarelist.add(Direction.N);
//            squarelist.add(Direction.NW);
            return squarelist;
        }

        //north
        if (y <= 0) {
            squarelist.add(Direction.W);
//            squarelist.add(Direction.SW);
            squarelist.add(Direction.S);
//            squarelist.add(Direction.SE);
            squarelist.add(Direction.E);
            return squarelist;
        }
        //south
        if (y + 1 >= height) {
            squarelist.add(Direction.W);
//            squarelist.add(Direction.NW);
            squarelist.add(Direction.N);
//            squarelist.add(Direction.NE);
            squarelist.add(Direction.E);
            return squarelist;
        }
        //west
        if (x <= 0) {
            squarelist.add(Direction.S);
//            squarelist.add(Direction.SE);
            squarelist.add(Direction.E);
//            squarelist.add(Direction.NE);
            squarelist.add(Direction.N);
            return squarelist;
        }
        //east
        if (x + 1 >= width) {
            squarelist.add(Direction.S);
//            squarelist.add(Direction.SW);
            squarelist.add(Direction.W);
//            squarelist.add(Direction.NW);
            squarelist.add(Direction.N);
            return squarelist;
        }

        return squarelist;
    }
    //移动前查看周围环境
    public Square look(World world) {
        double current_best_value = 0;
        double value = 0;
        Square bestsquare = null;
        //查看周围方格内的情况
        List<Square> squareList = world.getNearbySquare(position_x, position_y);


        //不受干扰的，由激素确定走向
        for (Square square : squareList) {
            if (foodornest)
                value = square.calculateSquareValue(PheromoneType.FOOD);//计算方格Food值
            else
                value = square.calculateSquareValue(PheromoneType.NEST);//计算方格Nest值
//            System.out.println(value + "|" + current_best_value);
            if (value > current_best_value) {
                current_best_value = value;
                bestsquare = square;
            }

//            if(square.ants >= Settings.MAXIMUM_NUMBER_OF_ANTS)
//            {
//                return SQUARE;
//            }
        }
        //食物或者巢穴没找到
        if (bestsquare == null) {
//            找到path激素最小的
            bestsquare = squareList.get(0);
            current_best_value = 100;
            //移除path不为零的
            Iterator<Square> sqIter = squareList.iterator();

            while (sqIter.hasNext()) {
                Square square = sqIter.next();
                value = square.calculateSquareValue(PheromoneType.PATH);
                if (value >= 0 && value < current_best_value) {
                    current_best_value = value;
                    bestsquare = square;
                } else if (value >= 0 && value > current_best_value) {
                    sqIter.remove();
                }
            }
            if (bestsquare == null) {
                bestsquare = sqIter.next();
            }
            //随机扰动决定走向
//            Random r = new Random();
//            if (r.nextDouble() < Settings.DISTURBANCE_THRESHOLDS) {
            //不走回头路
//                squareList.remove(last_sq);
//            bestsquare = squareList.get(r.nextInt());
//            }
        }
        return bestsquare;
    }
/*
    public void movep(World world, Square tosquare) {
        //先散布激素,未找到任何东西时,不散布激素
        //找到食物
        if (world.getSquare(position_x, position_y).getFood() != null) {
            pheromone.setFood_pheromone(Settings.MAX_FOOD_PHEROMONE);
            foodornest = false;
        }
        //找到巢穴
        if (world.getSquare(position_x, position_y).getNest() != null) {
            pheromone.setNest_pheromone(Settings.MAX_NEST_PHEROMONE);
            foodornest = true;
        }

        //已x,y为中心，散布激素
        List<Square> squareList = world.getNearbySquare(position_x, position_y);
//        中心散布
        world.getSquare(position_x, position_y).putPheromone(pheromone.getFood_pheromone(), pheromone.getNest_pheromone(), 100);
//        身后散布
//        for (Square square : squareList) {
//            square.putPheromone(pheromone.getFood_pheromone(), pheromone.getNest_pheromone(), 50);
//        }
        //然后移动
        world.getSquare(position_x, position_y).ants--;
        position_x = tosquare.x;
        position_y = tosquare.y;
        world.getSquare(position_x, position_y).ants++;
        //Ant激素衰减
        pheromone.decay();
    }

    public void mutation() {

    }
*/
}
