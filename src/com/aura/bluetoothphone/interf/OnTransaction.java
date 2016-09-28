package com.aura.bluetoothphone.interf;

import android.database.sqlite.SQLiteDatabase;

/**
 * 事务操作接口.
 * 
 * @Description 提供数据库事务操作接口.
 * @author LiGang
 * @date 2014-5-31
 */
public interface OnTransaction {

	/**
	 * 执信事务.
	 * 
	 * @param db
	 *            数据库操作对象.
	 * @return 若事务执行完成并成功返回true,若失败则返回false.
	 * 
	 * @version 1.0
	 * @createTime 2014-3-1,下午4:46:47
	 * @updateTime 2014-3-1,下午4:46:47
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo
	 */
	public boolean executeTransaction(SQLiteDatabase db);

}
