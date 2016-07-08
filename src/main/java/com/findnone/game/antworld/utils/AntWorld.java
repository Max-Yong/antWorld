package com.findnone.game.antworld.utils;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.findnone.game.antworld.domain.Ant;
import com.findnone.game.antworld.domain.Square;
import com.findnone.game.antworld.domain.World;

import java.util.Random;

/**
 * Created by Duyong on 2016/2/15.
 */
public class AntWorld {
    public static void main(String[] args) {
        System.out.println("hello ant world!");


//        ApplicationContext ctx = new ClassPathXmlApplicationContext(
//                "spring-config.xml");
//        FooService foo = (FooService) ctx.getBean("FooService");

        World world = new World();
        world.init();
//        world.showworld();

        Ant[] ants = new Ant[100];
        //随机扔Ant
        Random random = new Random();
        for (int i = 0; i < ants.length; i++) {
            int x = random.nextInt(world.getWidth());
            int y = random.nextInt(world.getHeight());
            ants[i] = new Ant(x, y);
            world.getSquare(x, y).ants++;
        }

//循环一年
        for (int i = 0; i < 3650; i++) {


            // ant born

            // ant look and touch
            for (Ant ant : ants) {
//                Square tosquare = ant.look(world);
//                System.out.println(tosquare);
                ant.move(world);
            }
            // ant move and active
//            for(ant.Ant ant:world.getAnts()){
//                ant.move(world);
//            }
            // ant dead
//            for(ant.Ant ant:world.getAnts()){
//            }
            //世界激素衰减
            world.decayPheromone();
        }
        System.out.println("result");
        world.showworld();
    }
}
