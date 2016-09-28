package com.aura.bluetoothphone.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;

import android.text.TextUtils;
import android.util.Log;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.bean.ResponseBean;
import com.aura.bluetoothphone.configs.ServerConfig;
import com.aura.bluetoothphone.configs.TApplication;
import com.aura.bluetoothphone.utils.LogUtil;
import com.aura.bluetoothphone.utils.NetUtil;
import com.aura.bluetoothphone.utils.OperationJson;


/**
 * 系统业务逻辑
 * 
 * @Description TODO
 * @author LiGang
 * @version 1.0
 * @date 2013-10-22
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 * 
 */
public class BaseBiz {

	/**
	 * 添加post请求的前三个固定参数
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午5:00:05
	 * @updateTime 2014年12月31日,上午5:00:05
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param method
	 *            请求的方法名
	 * @return
	 */
	protected static HashMap<String, String> getPostHeadMap(String method) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(ServerConfig.SERVER_METHOD_KEY, method);
		map.put(ServerConfig.SERVER_TYPE_KEY, ServerConfig.SERVER_TYPE_VALUE);
		map.put(ServerConfig.SERVER_VESRTION_KEY, ServerConfig.SERVER_VESRTION_VAULE);
		map.put(ServerConfig.SERVER_UPDATE_KEY, ServerConfig.SERVER_UPDATE_VAULE);
		return map;

	}
	/**
	 * 发送post请求
	 *
	 * @version 1.0
	 * @createTime 2014年12月31日,上午5:00:39
	 * @updateTime 2014年12月31日,上午5:00:39
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param timeout
	 *            接口请求超时时间
	 * @param attribute
	 *            接口参数
	 * @return 请求后解析完毕的数据对象
	 */
	protected static ResponseBean sendPost(int timeout, HashMap<String, String> attribute,String url) {
		LogUtil.out(attribute.get("======================>"));
		ResponseBean responseBean = new ResponseBean();
		HashMap<String, String> map = new HashMap<String, String>();
		String result = "";
		try {
			result = new NetUtil().sendPost(url, timeout, attribute);
			Log.i("JSONDATA:", result.toString());
			LogUtil.out(attribute.get(ServerConfig.SERVER_METHOD_KEY), result);
			// 返回的字符串不能进行json解析，故添加这样的处理
			int index = result.indexOf("{");
			if (index >= 0) {
				result = result.substring(result.indexOf("{"), result.length());
			}
			map.putAll(OperationJson.resolvingJsonObject(result));
		} catch (IOException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_io_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_io_message));
			e.printStackTrace();
		} catch (TimeoutException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_time_out_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_time_out_message));
			e.printStackTrace();
		} catch (JSONException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_json_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_json_message));
			e.printStackTrace();
		}
		responseBean.setStatus(map.get("status"));
		responseBean.setInfo(map.get("info"));
		responseBean.setObject(map.get("data"));
		return responseBean;
	}

	/**
	 * 发送post请求
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午5:01:35
	 * @updateTime 2014年12月31日,上午5:01:35
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param url
	 *            接口地址
	 * @param timeout
	 *            超时时间
	 * @param attribute
	 *            接口参数
	 * @return 请求后返回的解析数据
	 */
	protected static ResponseBean sendPost(String url, int timeout, HashMap<String, String> attribute) {
		ResponseBean responseBean = new ResponseBean();
		HashMap<String, String> map = new HashMap<String, String>();
		String result = "";
		try {
			result = new NetUtil().sendPost(url, timeout, attribute);
			LogUtil.out(url, result);
			// 返回的字符串不能进行json解析，故添加这样的处理
			int index = result.indexOf("{");
			if (index >= 0) {
				result = result.substring(result.indexOf("{"), result.length());
			}
			map.putAll(OperationJson.resolvingJsonObject(result));
		} catch (IOException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_io_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_io_message));
			e.printStackTrace();
		} catch (TimeoutException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_time_out_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_time_out_message));
			e.printStackTrace();
		} catch (JSONException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_json_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_json_message));
			e.printStackTrace();
		}
		responseBean.setStatus(map.get("status"));
		responseBean.setInfo(map.get("info"));
		responseBean.setObject(map.get("data"));
		return responseBean;
	}

	/**
	 * 发送get请求
	 * 
	 * @version 1.0
	 * @createTime 2014年5月20日,下午4:57:16
	 * @updateTime 2014年5月20日,下午4:57:16
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param timeout
	 *            超时时间
	 * @param url
	 *            服务器地址
	 * @param method
	 *            方法名
	 * @return 请求后返回的接口数据
	 */
	protected static ResponseBean sendGet(String url, String method, int timeout) {
		ResponseBean responseBean = new ResponseBean();
		HashMap<String, String> map = new HashMap<String, String>();
		String result = "";
		try {
			result = new NetUtil().sendGet(url + method, timeout);
			LogUtil.out("url=>", result);
			map.putAll(OperationJson.resolvingJsonObject(result));
		} catch (IOException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_io_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_io_message));
			e.printStackTrace();
		} catch (TimeoutException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_time_out_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_time_out_message));
			e.printStackTrace();
		} catch (JSONException e) {
			map.put("status", TApplication.context.getString(R.string.exception_net_work_json_code));
			map.put("info", TApplication.context.getString(R.string.exception_net_work_json_message));
			e.printStackTrace();
		}
		responseBean.setStatus(map.get("status"));
		responseBean.setInfo(map.get("info"));
		responseBean.setObject(map.get("data"));
		return responseBean;
	}

	/**
	 * 为map添加数据，如果value值为空则不传递次参数
	 * 
	 * @version 1.0
	 * @createTime 2013-11-8,下午2:10:30
	 * @updateTime 2013-11-8,下午2:10:30
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param map
	 *            键值对对象
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	protected static void MapPutValue(HashMap<String, String> map, String key, String value) {
		if (!TextUtils.isEmpty(value)) {
			map.put(key, value);
		}
	}
}
