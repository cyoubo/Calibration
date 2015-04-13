package com.tool.SqliteHelper;

import android.content.ContentValues;

/**
 * 数据库工具类所包含的接口集合
 * */
public class ISQLiteDataInterface
{
	/**DAO模型接口 */
	public static interface ISQLiteDataDAO
	{
		/** 获得数据实体类的表名*/
		String getTableName();
		/** 获得数据实体类的列名*/
		String[] getColumnNames();
		/** 获得数据实体类的主键列名*/
		String[] getPrimaryKeys();
		/** 获得数据实体类的主键值*/
		String[] getPrimaryValues();
		/** 获得数据实体类数据集合对象*/
		ContentValues CombineContentValues();
		/**当只指定Bean主键时，更新此bean的其他属性*/
		boolean UpdataBeans(IDataBaseInfo info);
		
	}
	/**包含索引列的DAO模型接口 */
	public static interface ISQLiteIndexDataDAO extends ISQLiteDataDAO
	{
		/**获得数据实体类的索引列列名 */
		String   getIndexColumnName();
		/**获得数据实体类的索引列值 */
		String   getIndexValues();
	}
	
	/**
	 * 内部接口：提供数据库的名称，路径参数等信息
	 * */
	public static interface IDataBaseInfo
	{
		/**获取数据库的完整路径*/
		String getDataBaseFullPath();
		/**获取数据库的所在路径(结尾不包含/)*/
		String getDataBasePath();
		/**获取数据库的文件名(包含.db后缀)*/
		String getDataBaseName();
	}
}
