package com.findnone.game.antworld.api;

import com.findnone.game.antworld.domain.Square;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Duyong on 2016/2/26.
 */

public interface WorldService {

    public String sayHello(@PathParam("a") String a);

    Square[][] worldStatus();
}
