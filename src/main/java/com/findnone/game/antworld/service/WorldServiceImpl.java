package com.findnone.game.antworld.service;

import com.findnone.game.antworld.api.WorldService;
import com.findnone.game.antworld.domain.Ant;
import com.findnone.game.antworld.domain.Square;
import com.findnone.game.antworld.domain.World;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.PathParam;
import java.util.Random;

/**
 * Created by Duyong on 2016/2/25.
 */

@Service("worldService")
public class WorldServiceImpl implements WorldService {

    World world;
    Ant[] ants = new Ant[1];
    @Override
    public String sayHello(@PathParam("a") String a) {
        return "Hello " + a + ", Welcome to CXF RS Spring Boot World!!!";
    }

    @PostConstruct
    public void init() {
        world = new World();
        world.init();

        //随机扔Ant
        Random random = new Random();
        for (int i = 0; i < ants.length; i++) {
            int x = random.nextInt(world.getWidth());
            int y = random.nextInt(world.getHeight());
            ants[i] = new Ant(x, y);
            world.getSquare(x, y).ants++;
        }
    }

    @Override
    public Square[][] worldStatus() {
        for (Ant ant : ants) {
//            Square tosquare = ant.look(world);
//                System.out.println(tosquare);
            ant.move(world);
        }
        //世界激素衰减
        world.decayPheromone();
        return world.getSquares();
    }

    @PreDestroy
    public void destory() {

    }

}
