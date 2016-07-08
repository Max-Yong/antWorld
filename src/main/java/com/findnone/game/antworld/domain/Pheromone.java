package com.findnone.game.antworld.domain;

/**
 * Created by Duyong on 2016/2/16.
 */
public class Pheromone {


    //巢穴 激素
    private int nest_pheromone;
    //食物 激素
    private int food_pheromone;
    //路径 激素
    private int path_pheromone;


    public Pheromone() {
        nest_pheromone = 0;
        food_pheromone = 0;
        path_pheromone = 0;
    }

    //激素衰减
    public void decay() {

        if (nest_pheromone > 0)
            nest_pheromone--;
        if (food_pheromone > 0)
            food_pheromone--;
        if (path_pheromone > 0)
            path_pheromone--;
    }

    public int getPheromone(PheromoneType type) {
        switch (type) {
            case NEST:
                return nest_pheromone;
            case FOOD:
                return food_pheromone;
            case PATH:
                return path_pheromone;
            default:
                return 0;
        }

    }

    public void setPheromone(PheromoneType type, int amount) {
        switch (type) {
            case NEST:
                this.nest_pheromone = amount;
                break;
            case FOOD:
                this.food_pheromone = amount;
                break;
            case PATH:
                this.path_pheromone = amount;
                break;
            default:

                break;
        }

    }

    //增加食物激素
    public void putFoodPheromone(int amount) {
        if (this.getFood_pheromone() < Settings.PHEROMONE_LIMIT)
            this.setFood_pheromone(this.getFood_pheromone() + amount);
    }

    //增加巢穴激素
    public void putNestPheromone(int amount) {
        if (this.getNest_pheromone() < Settings.PHEROMONE_LIMIT)
            this.setNest_pheromone(this.getNest_pheromone() + amount);
    }

    public void putHperomone(PheromoneType type, int amount) {
        if (this.getPheromone(type) < Settings.PHEROMONE_LIMIT)
            this.setPheromone(type, this.getPheromone(type) + amount);
    }

    public void putPheromone(int food, int nest, int path) {
        putHperomone(PheromoneType.NEST, nest);
        putHperomone(PheromoneType.FOOD, food);
        putHperomone(PheromoneType.PATH, path);
    }
    
    public int getNest_pheromone() {
        return nest_pheromone;
    }

    public void setNest_pheromone(int nest_pheromone) {
        this.nest_pheromone = nest_pheromone;
    }

    public int getFood_pheromone() {
        return food_pheromone;
    }

    public void setFood_pheromone(int food_pheromone) {
        this.food_pheromone = food_pheromone;
    }

    public int getPath_pheromone() {
        return path_pheromone;
    }

    public void setPath_pheromone(int path_pheromone) {
        this.path_pheromone = path_pheromone;
    }
}
