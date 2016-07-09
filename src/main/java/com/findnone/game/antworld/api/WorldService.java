package com.findnone.game.antworld.api;

import com.findnone.game.antworld.domain.World;

/**
 * Created by Duyong on 2016/2/26.
 */

public interface WorldService {

    /**
     * 世界诞生
     */
    public World init(World world);

    /**
     * 世界毁灭
     */
    public void destory(World world);

    /**
     * 四季变化
     */
    //public World changeReason();

    /**
     * 昼夜变化
     */
    //public World changeDay();

    /**
     * 温度变化
     */
    public World changeTemperature(World world);

    /**
     * 光线变化
     */

    public World changeLight(World world);

    /**
     * 世界时间变化
     */
    public World stepOver(World world);

    /**
     * 世界状态
     */
//    public World getStatus();
}
