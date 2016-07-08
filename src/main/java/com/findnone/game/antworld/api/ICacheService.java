/****************************************************************************************
 * @File name   :      ICacheService.java
 *
 * @Author      :      LEIKZHU
 *
 * @Date        :      Sep 10, 2014
 *
 * @Copyright Notice: 
 * Copyright (c) 2014 SGM, Inc. All  Rights Reserved.
 * This software is published under the terms of the SGM Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * 
 * --------------------------------------------------------------------------------------
 * Date								Who					Version				Comments
 * Sep 10, 2014 2:18:57 PM			LEIKZHU				1.0				Initial Version
 ****************************************************************************************/
package com.findnone.game.antworld.api;

public interface ICacheService {

	public boolean setCacheValue(String namespace, String key, int expiredTime, Object value);

	public Object getCacheValue(String namespace, String key);

	public boolean deleteCacheValue(String namespace, String key);

}
