package com.tool.SqliteHelper;

import com.tool.SqliteHelper.ISQLiteDataInterface.ISQLiteDataDAO;
import com.tool.SqliteHelper.ISQLiteDataInterface.ISQLiteIndexDataDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * ���ݿ����������<br>
 * <ul>
 * <li>��������ݿ�CURD����</li>
 * <li>��������ݿ��ı���</li>
 * <li>��������ݿ��¼�Ĵ����ж�</li>
 * </ul>
 * <b>�ð�����ĺ���ΪSQliteDataBase</b>
 * */
public class SQLiteHelper
{
	/**���ݿ����������ɻ�������*/
	private SQLiteDatabase db;
	/**���ݿ�·��*/
	private String databasefullpath;
	/**
	 * ���캯��<br>
	 * �������ݲ������������
	 * @param databasefullpath ���ݿ��ȫ·��<br>
	 * <br>
	 * <b>�÷������ᴴ�����ݿ�����ڽ������ݲ���ǰ��Ҫ����OpenDataBase����</b>
	 * */
	public SQLiteHelper(String databasefullpath)
	{
		this.databasefullpath=databasefullpath;
	}
	/**
	 * �����ݿ�����
	 * @param iscreate �����ݿ�ʱ���Ƿ���Ҫ����<br>
	 * @return ���򿪳ɹ��򷵻�ture ���򷵻�false
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
	 * �ر����ݿ�����
	 * */
	public void CloseDataBase()
	{
		if(db!=null)
			db.close();
	}
	/**
	 * ִ���޷��ص�SQL���
	 * @param statement ����create,drop��DDL���<br>
	 * */
	public void Exec_No_QueryStatement(String statement)
	{
		db.execSQL(statement);
	}
	/**
	 * ִ���޷��ص�SQL���
	 * @param statement ����create,drop��DDL���<br>
	 * @param args statement����Ҫ�滻�ģ�����<br>
	 * */
	public void Exec_No_QueryStatement(String statement,Object[] args)
	{
		db.execSQL(statement, args);
	}
	/**
	 * ִ�в������
	 * @param table  ���ݱ���
	 * @param values ���ݼ���
	 * @return ���µ�����ID(���ݿ�Ĭ��ֵ)
	 * */
	public long Insert(String table,ContentValues values)
	{
		return db.insert(table, null, values);
	}
	/**
	 * ִ�в������
	 * @param dao  ʵ��ISQLiteDataDAO�ӿڵ�ʵ����
	 * @return ���µ�����ID(���ݿ�Ĭ��ֵ)
	 * */
	public long Insert(ISQLiteDataDAO dao)
	{
		return db.insert(dao.getTableName(), null, dao.CombineContentValues());
	}
	/**
	 * ִ��ɾ�����
	 * @param table  ���ݱ���
	 * @param wherestatement ɾ�����������
	 * @param args ����������
	 * @return ��Ӱ�������
	 * */
	public int Delete(String table,String wherestatement, String[] args)
	{
		return db.delete(table, wherestatement, args);
	}
	/**
	 * ִ��ɾ�����
	 * @param dao  ʵ��ISQLiteDataDAO�ӿڵ�ʵ����
	 * @return ��Ӱ�������
	 * */
	public int Delete(ISQLiteDataDAO dao)
	{
		String where= CombineWhereClause(dao.getPrimaryKeys());
		return db.delete(dao.getTableName(), where, dao.getPrimaryValues());
	}
	/**
	 * ִ�и������
	 * @param table  ���ݱ���
	 * @param values ���ݼ���
	 * @param wherestatement ���µ��������
	 * @param args ����������
	 * @return ��Ӱ�������
	 * */
	public int Update(String table,ContentValues values,String wherestatement, String[] args)
	{
		return db.update(table, values, wherestatement, args);
	}
	/**
	 * ִ�и������
	 * @param dao  ʵ��ISQLiteDataDAO�ӿڵ�ʵ����
	 * @return ��Ӱ�������
	 * */
	public int Update(ISQLiteDataDAO dao)
	{
		String where= CombineWhereClause(dao.getPrimaryKeys());
		return db.update(dao.getTableName(), dao.CombineContentValues(), where, dao.getPrimaryValues());
	}
	/**
	 * ִ�в�ѯ���
	 * @param table  ���ݱ���
	 * @param where ��ѯ���������
	 * @param args ����������
	 * @return ����ѯ�ɹ��򷵻��α���󣬷��򷵻�NULL
	 * <p>
	 * <b>ʹ�ø÷����Ľ��ǰ��Ҫ���ǿ��ж�</b>
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
	 * ִ�и������
	 * @param dao  ʵ��ISQLiteDataDAO�ӿڵ�ʵ����
	 * @return ����ѯ�ɹ��򷵻��α���󣬷��򷵻�NULL
	 * <p>
	 * <b>ʹ�ø÷����Ľ��ǰ��Ҫ���ǿ��ж�</b>
	 * */
	public Cursor Query(ISQLiteDataDAO dao)
	{
		String where= CombineWhereClause(dao.getPrimaryKeys());
		return Query(dao.getTableName(), where, dao.getPrimaryValues());
	}
	/**
	 * ִ�����ݱ�ȫ�����������
	 * @param table  ���ݱ���
	 * @return ����ѯ�ɹ��򷵻��α���󣬷��򷵻�NULL
	 * <p>
	 * <b>ʹ�ø÷����Ľ��ǰ��Ҫ���ǿ��ж�</b>
	 * */
	public Cursor Query_TravelTable(String table)
	{
		return Query(table, null, null);
	}
	/**
	 * ִ��ָ���������������
	 * @param table  ���ݱ���
	 * @param indexcolumn �����ֶ���
	 * @return ����ѯ�ɹ��򷵻�������������򷵻�-1
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
	 * ִ��ָ���������������
	 * @param dao  ʵ��ISQLiteIndexDataDAO�ӿڵ�ʵ����
	 * @return ����ѯ�ɹ��򷵻�������������򷵻�-1
	 * */
	public long Query_MaxIndex(ISQLiteIndexDataDAO dao)
	{
		return Query_MaxIndex(dao.getTableName(), dao.getIndexColumnName());
	}
	/**
	 * �ж����ݱ��е�ǰʵ���Ӧ��¼�Ƿ����
	 * @param dao  ʵ��ISQLiteDataDAO�ӿڵ�ʵ����
	 * @return ����ѯ��¼�����򷵻�true�����򷵻�false
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
	 * ˽�к���<br>
	 * ������ɽ���������ƴ�ӳ�where�������
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
