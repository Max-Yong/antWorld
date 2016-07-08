package com.findnone.game.antworld.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Duyong on 2016/2/16.
 */
@XmlRootElement(name = "N")
public class Nest {

    private  int foods;

    private  int position_x;
    private  int position_y;
    private  float homePheromone=10000.0f;
}
