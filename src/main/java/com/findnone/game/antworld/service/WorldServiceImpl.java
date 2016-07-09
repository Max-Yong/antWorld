package com.findnone.game.antworld.service;

import com.findnone.game.antworld.api.WorldService;
import com.findnone.game.antworld.domain.World;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Duyong on 2016/2/25.
 */

@Service("worldService")
public class WorldServiceImpl implements WorldService {

    @Override
    public World init(World world) {
        return null;
    }

    @Override
    public void destory(World world) {

    }

    @Override
    public World changeTemperature(World world) {
        return null;
    }

    @Override
    public World changeLight(World world) {
        return null;
    }

    @Override
    @Scheduled(cron = "1 0 0 0 0 0")
    public World changeTime(World world) {
        return null;
    }
}
