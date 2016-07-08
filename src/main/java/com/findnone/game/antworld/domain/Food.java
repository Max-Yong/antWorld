package com.findnone.game.antworld.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Duyong on 2016/2/16.
 */
@XmlRootElement(name = "F")
public class Food {

    //大小
    int size;
    //含有食物数量
    int count;

    //毒物数量
    int poison;

    public Food(int count){
        this.count=count;
    }

}
