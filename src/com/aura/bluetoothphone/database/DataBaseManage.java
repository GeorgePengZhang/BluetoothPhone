package com.aura.bluetoothphone.database;

import android.database.sqlite.SQLiteDatabase;

import com.aura.bluetoothphone.configs.TApplication;
import com.aura.bluetoothphone.interf.OnOperationDataBase;
import com.aura.bluetoothphone.utils.LogUtil;
import com.aura.bluetoothphone.utils.OperationDataBaseUtil;



/**
 * 数据库管理工具
 * 
 * @Description
 * @author LiGang
 * @version 1.0
 * @date 2014年5月18日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 * 
 */
public class DataBaseManage {

	/** 数据库名称 */
	public static final String DATA_BASE_NAME = "DataBase_Name";
	/** 数据库版本 */
	public static final int DATA_BASE_VERSION = 1;

	/** 数据库操作对象 */
	private static OperationDataBaseUtil dataBaseHelper;

	/**
	 * 创建指定名称的数据库
	 * 
	 * @version 1.0
	 * @createTime 2013-10-22,下午3:51:28
	 * @updateTime 2013-10-22,下午3:51:28
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param dataBaseName
	 */
	public static void createDataBase(String dataBaseName) {
		dataBaseHelper = new OperationDataBaseUtil(TApplication.context, dataBaseName, null, DATA_BASE_VERSION, new OnOperationDataBase() {

			@Override
			public void updateDataBase(SQLiteDatabase db, int oldVersion, int newVersion) {
				if (newVersion > oldVersion) {// 如果是数据库升级
					updateTables(db);
				}
			}

			@Override
			public void createTable(SQLiteDatabase db) {
				createTables(db);
			}
		});
		dataBaseHelper.onCreate(dataBaseHelper.getWritableDatabase());
		dataBaseHelper.close();
	}

	/**
	 * 获取数据库操作对象
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午10:03:52
	 * @updateTime 2013-10-21,下午10:03:52
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public static OperationDataBaseUtil getOperationDataBaseUtil(String dataBaseName) {

		if (null != dataBaseHelper) {
			dataBaseHelper.close();
			dataBaseHelper = null;
		}

		dataBaseHelper = new OperationDataBaseUtil(TApplication.context, dataBaseName, null, DATA_BASE_VERSION);
		return dataBaseHelper;

	}

	/**
	 * 更新数据库表
	 * 
	 * @version 1.0
	 * @createTime 2013-11-22,下午2:47:17
	 * @updateTime 2013-11-22,下午2:47:17
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param db
	 *            数据库操作对象
	 * @return
	 */
	private static void updateTables(SQLiteDatabase db) {
		try {
			switch (db.getVersion()) {
			case 1:// 数据库版本1-2
			case 2:// 数据库版本2-3
			case 3:// 数据库版本3-4
			case 4:// 数据库版本4-5
			case 5:// 数据库版本5-6
			case 6:// 数据库版本6-7
			case 7:// 数据库版本7-8
			case 8:// 数据库版本8-9
			case 9:// 数据库版本9-10
			case 10:// 数据库版本10-11
			}
		} catch (RuntimeException e) {
			// 数据库更新失败
			LogUtil.err("DataBase Update Error ============>\n" + e.getMessage());
		}

	}

	/**
	 * 创建数据表
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午4:20:03
	 * @updateTime 2013-10-21,下午4:20:03
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param db
	 *            数据库操作对象
	 */
	private static void createTables(SQLiteDatabase db) {

		/**
		 * 创建表
		 * 
		 * @version 1.0
		 * @createTime 2013-10-21,下午4:20:03
		 * @updateTime 2013-10-21,下午4:20:03
		 * @createAuthor LiGang
		 * @updateAuthor LiGang
		 */
		// String sql = "create table if not exists " + 表名 + "(" // 创推送消息表
		// + DataBaseFields.ID + " integer PRIMARY KEY autoincrement, " //
		// 自增长id.
		// + DataBaseFields.MESSAGE_ID + " varchar, " // 更推送消息id
		// + DataBaseFields.TITLE + " varchar, " // 推送消息标题
		// + DataBaseFields.MESSAGE_TYPE + " varchar, " // 推送消息类型
		// + DataBaseFields.MESSAGE + " varchar, " // 推送消息
		// + DataBaseFields.PATH + " varchar, " // 推送消息链接网页
		// + DataBaseFields.IMAGES + " varchar, " // 推送消息图片地址
		// + DataBaseFields.IS_READ + " varchar, " // 消息是否已读（1未读，2已读）
		// + DataBaseFields.SEND_TIME + " varchar" // 发送时间
		// + ");";
		// db.execSQL(sql);

	}

}
