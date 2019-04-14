package com.dao.source;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 动态获取数据源（读写分离）
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:17:16
 */
public class ChooseDataSource extends AbstractRoutingDataSource {
	public static Map<String, List<String>> METHODTYPE = new HashMap<String, List<String>>();

	// 获取数据源名称
	@Override
	protected Object determineCurrentLookupKey() {
		return HandleDataSource.getDataSource();
	}
	// 设置方法名前缀对应的数据源
	public void setMethodType(Map<String, String> map) {
		METHODTYPE.putAll(Maps.transformValues(map, entity-> Arrays.stream(entity.split(",")).
				filter(s-> StringUtils.isNotBlank(s)).collect(Collectors.toList())));
	}
}
