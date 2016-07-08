/****************************************************************************************
 * @File name   :      CacheServiceImpl.java
 * @Author :      LEIKZHU
 * @Date :      Sep 10, 2014
 * @Copyright Notice:
 * Copyright (c) 2014 SGM, Inc. All  Rights Reserved.
 * This software is published under the terms of the SGM Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * <p/>
 * <p/>
 * --------------------------------------------------------------------------------------
 * Date								Who					Version				Comments
 * Sep 10, 2014 2:23:43 PM			LEIKZHU				1.0				Initial Version
 ****************************************************************************************/
package com.findnone.game.antworld.utils;

import com.findnone.game.antworld.api.ICacheService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Service("CacheService")
public class CacheServiceImpl implements ICacheService {

    private final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    // memcached服务器地址
    @Value("#{propertyConfigurer['com.findnone.common.cache.memcached.address']}")
    private String memcachedAddress;

    // memcached客户端连接池大小
    @Value("#{propertyConfigurer['com.findnone.common.cache.memcached.poolsize']}")
    private int poolSize;

    MemcachedClient memcachedClient = null;

    @PostConstruct
    public boolean init() {
        boolean succ = false;

        if (memcachedClient == null)
            try {
                MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                        AddrUtil.getAddresses(this.memcachedAddress));
                builder.setCommandFactory(new BinaryCommandFactory());
                builder.setConnectionPoolSize(this.poolSize);
                this.memcachedClient = builder.build();
                succ = true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        return succ;
    }

    @PreDestroy
    public boolean destroy() {
        boolean succ = false;
        try {
            memcachedClient.shutdown();
            succ = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return succ;
    }

    @Override
    public boolean setCacheValue(String namespace, String key, int expiredTime, Object value) {
        boolean result = false;
        MemcachedClient memcachedClient ;
        try {
            memcachedClient = getMemcachedClient();
            if (memcachedClient != null) {
                result = memcachedClient.set(namespace + "/" + key, expiredTime, value);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object getCacheValue(String namespace, String key) {
        Object value = null;
        MemcachedClient memcachedClient ;
        try {
            memcachedClient = getMemcachedClient();
            if (memcachedClient != null) {
                value = memcachedClient.get(namespace + "/" + key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public boolean deleteCacheValue(String namespace, String key) {
        boolean result = false;
        MemcachedClient memcachedClient;
        try {
            memcachedClient = getMemcachedClient();
            if (memcachedClient != null) {
                result = memcachedClient.delete(namespace + "/" + key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    private synchronized MemcachedClient getMemcachedClient() {
        if (memcachedClient == null)
            init();
        return this.memcachedClient;
    }
}
