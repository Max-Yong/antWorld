package com.findnone.game.antworld.resource;

import com.findnone.game.antworld.api.WorldService;
import com.findnone.game.antworld.domain.Square;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Duyong on 2016/2/26.
 */

public class WorldResource {
    @Resource
    WorldService worldService;

    @GET
    @Path("/world")
    @Produces(MediaType.APPLICATION_JSON)
    public Square[][] worldStatus() {
        return worldService.worldStatus();
    }
}
