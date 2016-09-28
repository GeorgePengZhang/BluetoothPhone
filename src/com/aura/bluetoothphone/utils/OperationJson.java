package com.aura.bluetoothphone.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Json操作工具类
 * 
 * @Description TODO
 * @author LiGang
 * @version 1.0
 * @date 2014年12月31日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 * 
 */
public class OperationJson {
	
	/**
	 * 解析JsonObject.
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午2:46:19
	 * @updateTime 2014年12月31日,上午2:46:19
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param json
	 *            需要解析的json字符串
	 * @return 解析后的map集合
	 * @throws JSONException
	 *             json解析异常
	 */
	public static HashMap<String, String> resolvingJsonObject(String json) throws JSONException {
		HashMap<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(json);
		Iterator<?> iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			map.put(key, jsonObject.getString(key));
			
		}
		return map;
	}

	/**
	 * 解析JsonObject.
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午2:46:19
	 * @updateTime 2014年12月31日,上午2:46:19
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param json
	 *            需要解析的json字符串
	 * @param title
	 *            需要解析的key集合
	 * @return 解析后的map集合
	 * @throws JSONException
	 *             json解析异常
	 */
	public static HashMap<String, String> resolvingJsonObject(String json, String[] title) throws JSONException {
		HashMap<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(json);
		for (int i = 0; i < title.length; i++)
			map.put(title[i], jsonObject.getString(title[i]));
		return map;
	}
	
	/**
	 * 解析JsonArray.
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午2:47:28
	 * @updateTime 2014年12月31日,上午2:47:28
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param json
	 *            需要解析的json字符串
	 * @return 解析后的map列表集合
	 * @throws JSONException
	 *             json解析异常
	 */
	public static ArrayList<HashMap<String, String>> resolvingJsonArray(String json) throws JSONException {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		JSONArray jsonArray = new JSONArray(json);
		for (int i = 0; i < jsonArray.length(); i++){
			list.add(resolvingJsonObject(jsonArray.getString(i)));
		}
		return list;
	}

	/**
	 * 解析JsonArray.
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午2:47:28
	 * @updateTime 2014年12月31日,上午2:47:28
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param json
	 *            需要解析的json字符串
	 * @param title
	 *            需要解析的key集合
	 * @return 解析后的map列表集合
	 * @throws JSONException
	 *             json解析异常
	 */
	public static ArrayList<HashMap<String, String>> resolvingJsonArray(String json, String[] title) throws JSONException {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		JSONArray jsonArray = new JSONArray(json);
		for (int i = 0; i < jsonArray.length(); i++){
			list.add(resolvingJsonObject(jsonArray.getString(i), title));
		}
		return list;
	}

	/**
	 * 将map对象打包成json字符串
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午2:48:38
	 * @updateTime 2014年12月31日,上午2:48:38
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param map
	 *            需要封装车jsonObject的map集合
	 * @return 转换之后的json字符串
	 * @throws JSONException
	 *             json转换异常
	 */
	public static String packageJsonObject(HashMap<String, String> map) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		return jsonObject.toString();
	}


	/**
	 * 将map列表转换成jsonArray字符串
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午2:50:37
	 * @updateTime 2014年12月31日,上午2:50:37
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param list
	 *            待转换的map列表
	 * @return 转换之后的jsonArray字符串
	 * @throws JSONException
	 *             json封装转化异常
	 */
	public static String packageJsonArray(ArrayList<HashMap<String, String>> list) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++)
			jsonArray.put(packageJsonObject(list.get(i)));
		return jsonArray.toString();
	}
}
