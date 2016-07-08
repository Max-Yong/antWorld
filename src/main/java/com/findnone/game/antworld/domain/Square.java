package com.findnone.game.antworld.domain;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Duyong on 2016/2/16.
 */
@XmlRootElement(name = "S")
public class Square implements Serializable {
    public int ants;        //本方格中包含的蚂蚁
    public Food food;            //本方格中包含的食物数
    public Nest nest;                //方格为蚁穴
    public Pheromone pheromone;            //本方格中的外激素含量
    public int x;            //本方格的坐标
    public int y;
    public boolean wall;            //是否有障碍物

    //初始化
    public Square(int x, int y) {
        food = null;
        nest = null;
        pheromone = new Pheromone();
        this.x = x;
        this.y = y;
        wall = false;
        ants = 0;
    }
    public Square(int x, int y,boolean wall) {
        food = null;
        nest = null;
        pheromone = new Pheromone();
        this.x = x;
        this.y = y;
        this.wall = wall;
        ants = 0;
    }

    public double calculateSquareValue(PheromoneType type) {
        if (this.wall) // 方格有障碍物
            return -100000;
        // 计算方格中各项参数的值
        if (type == PheromoneType.FOOD) {
            if (getFood() != null && getFood().count > 0)
                return 9999999999.0;
        } else if (type == PheromoneType.NEST) {
            if (getNest() != null)
                return 9999999999.0;
        }
        int value = 0;
        if (getPheromone() != null)
            value = getPheromone().getPheromone(type) * Settings.PHEROMONE_THRESHOLDS;
        return value; // 外激素
    }


    public Pheromone getPheromone() {
        return pheromone;
    }

    public void setPheromone(Pheromone pheromone) {
        this.pheromone = pheromone;
    }

    public Nest getNest() {
        return nest;
    }

    public void setNest(Nest nest) {
        this.nest = nest;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
