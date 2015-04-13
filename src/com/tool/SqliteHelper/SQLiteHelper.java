package com.tool.SqliteHelper;

import com.tool.SqliteHelper.ISQLiteDataInterface.ISQLiteDataDAO;
import com.tool.SqliteHelper.ISQLiteDataInterface.ISQLiteIndexDataDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 数据库操作帮助类<br>
 * <ul>
 * <li>可完成数据库CURD操作</li>
 * <li>可完成数据库表的遍历</li>
 * <li>可完成数据库记录的存在判断</li>
 * </ul>
 * <b>该帮助类的核心为SQliteDataBase</b>
 * */
public class SQLiteHelper
{
	/**数据库对象，用于完成基础操作*/
	private SQLiteDatabase db;
	/**数据库路径*/
	private String databasefullpath;
	/**
	 * 构造函数<br>
	 * 构建数据操作帮助类对象
	 * @param databasefullpath 数据库的全路径<br>
	 * <br>
	 * <b>该方法不会创建数据库对象，在进行数据操作前需要调用OpenDataBase方法</b>
	 * */
	public SQLiteHelper(String databasefullpath)
	{
		this.databasefullpath=databasefullpath;
	}
	/**
	 * 打开数据库连接
	 * @param iscreate 打开数据库时，是否需要创建<br>
	 * @return 若打开成功则返回ture 否则返回false
	 * */
	public boolean OpenDataBase(boolean iscreate)
	{
		if(iscreate)
			db=SQLiteDatabase.openOrCreateDatabase(databasefullpath,null);
		else
			db=SQLiteDatabase.openDatabase(databasefullpath, null, SQLiteDatabase.OPEN_READWRITE);
		
		return db==null;
	}
	/**
	 * 关闭数据库连接
	 * */
	public void CloseDataBase()
	{
		if(db!=null)
			db.close();
	}
	/**
	 * 执行无返回的SQL语句
	 * @param statement 诸如create,drop等DDL语句<br>
	 * */
	public void Exec_No_QueryStatement(String statement)
	{
		db.execSQL(statement);
	}
	/**
	 * 执行无返回的SQL语句
	 * @param statement 诸如create,drop等DDL语句<br>
	 * @param args statement中需要替换的？参数<br>
	 * */
	public void Exec_No_QueryStatement(String statement,Object[] args)
	{
		db.execSQL(statement, args);
	}
	/**
	 * 执行插入语句
	 * @param table  数据表名
	 * @param values 数据集合
	 * @return 最新的行数ID(数据库默认值)
	 * */
	public long Insert(String table,ContentValues values)
	{
		return db.insert(table, null, values);
	}
	/**
	 * 执行插入语句
	 * @param dao  实现ISQLiteDataDAO接口的实体类
	 * @return 最新的行数ID(数据库默认值)
	 * */
	public long Insert(ISQLiteDataDAO dao)
	{
		return db.insert(dao.getTableName(), null, dao.CombineContentValues());
	}
	/**
	 * 执行删除语句
	 * @param table  数据表名
	 * @param wherestatement 删除的条件语句
	 * @param args 条件语句参数
	 * @return 受影响的行数
	 * */
	public int Delete(String table,String wherestatement, String[] args)
	{
		return db.delete(table, wherestatement, args);
	}
	/**
	 * 执行删除语句
	 * @param dao  实现ISQLiteDataDAO接口的实体类
	 * @return 受影响的行数
	 * */
	public int Delete(ISQLiteDataDAO dao)
	{
		String where= CombineWhereClause(dao.getPrimaryKeys());
		return db.delete(dao.getTableName(), where, dao.getPrimaryValues());
	}
	/**
	 * 执行更新语句
	 * @param table  数据表名
	 * @param values 数据集合
	 * @param wherestatement 更新的条件语句
	 * @param args 条件语句参数
	 * @return 受影响的行数
	 * */
	public int Update(String table,ContentValues values,String wherestatement, String[] args)
	{
		return db.update(table, values, wherestatement, args);
	}
	/**
	 * 执行更新语句
	 * @param dao  实现ISQLiteDataDAO接口的实体类
	 * @return 受影响的行数
	 * */
	public int Update(ISQLiteDataDAO dao)
	{
		String where= CombineWhereClause(dao.getPrimaryKeys());
		return db.update(dao.getTableName(), dao.CombineContentValues(), where, dao.getPrimaryValues());
	}
	/**
	 * 执行查询语句
	 * @param table  数据表名
	 * @param where 查询的条件语句
	 * @param args 条件语句参数
	 * @return 若查询成功则返回游标对象，否则返回NULL
	 * <p>
	 * <b>使用该方法的结果前需要做非空判断</b>
	 * */
	public Cursor Query(String table,String where,String[] args)
	{
		
		Cursor cursor=null;
		cursor=db.query(false, table, null, where, args, null, null, null, null);
		if(cursor.moveToFirst())
			return cursor;
		else 
			return cursor;
	}
	/**
	 * 执行更新语句
	 * @param dao  实现ISQLiteDataDAO接口的实体类
	 * @return 若查询成功则返回游标对象，否则返回NULL
	 * <p>
	 * <b>使用该方法的结果前需要做非空判断</b>
	 * */
	public Cursor Query(ISQLiteDataDAO dao)
	{
		String where= CombineWhereClause(dao.getPrimaryKeys());
		return Query(dao.getTableName(), where, dao.getPrimaryValues());
	}
	/**
	 * 执行数据表全遍历操作语句
	 * @param table  数据表名
	 * @return 若查询成功则返回游标对象，否则返回NULL
	 * <p>
	 * <b>使用该方法的结果前需要做非空判断</b>
	 * */
	public Cursor Query_TravelTable(String table)
	{
		return Query(table, null, null);
	}
	/**
	 * 执行指定表中最大索引号
	 * @param table  数据表名
	 * @param indexcolumn 索引字段名
	 * @return 若查询成功则返回最大索引，否则返回-1
	 * */
	public long Query_MaxIndex(String table,String indexcolumn)
	{
		Cursor cursor=null;
		String sql=String.format("select max(%s) from %s", indexcolumn,table);
		cursor=db.rawQuery(sql, null);
		if(cursor.moveToFirst())
			return cursor.getLong(0);
		else 
			return -1;
	}
	/**
	 * 执行指定表中最大索引号
	 * @param dao  实现ISQLiteIndexDataDAO接口的实体类
	 * @return 若查询成功则返回最大索引，否则返回-1
	 * */
	public long Query_MaxIndex(ISQLiteIndexDataDAO dao)
	{
		return Query_MaxIndex(dao.getTableName(), dao.getIndexColumnName());
	}
	/**
	 * 判定数据表中当前实体对应记录是否存在
	 * @param dao  实现ISQLiteDataDAO接口的实体类
	 * @return 若查询记录存在则返回true，否则返回false
	 * */
	public boolean RecordIsExist(ISQLiteDataDAO dao)
	{
		boolean result=false;
		Cursor cursor=this.Query(dao);
		if(cursor!=null&&cursor.moveToFirst())
			result= cursor.getCount()!=0;
		cursor.close();
		return result;
	}
	/**
	 * 私有函数<br>
	 * 用于完成将主键列名拼接成where条件语句
	 * */
	private String CombineWhereClause(String[] keys)
	{
		StringBuffer wherebuffer=new StringBuffer();
		for (String key : keys)
		{
			wherebuffer.append(key);
			wherebuffer.append(" = ?");
			wherebuffer.append(",");
		}
		wherebuffer.delete(wherebuffer.length()-1, 1);
		return wherebuffer.toString();
	}
}
