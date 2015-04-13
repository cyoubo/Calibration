package com.tool.SqliteHelper;

import android.content.ContentValues;

/**
 * ���ݿ⹤�����������Ľӿڼ���
 * */
public class ISQLiteDataInterface
{
	/**DAOģ�ͽӿ� */
	public static interface ISQLiteDataDAO
	{
		/** �������ʵ����ı���*/
		String getTableName();
		/** �������ʵ���������*/
		String[] getColumnNames();
		/** �������ʵ�������������*/
		String[] getPrimaryKeys();
		/** �������ʵ���������ֵ*/
		String[] getPrimaryValues();
		/** �������ʵ�������ݼ��϶���*/
		ContentValues CombineContentValues();
		/**��ָֻ��Bean����ʱ�����´�bean����������*/
		boolean UpdataBeans(IDataBaseInfo info);
		
	}
	/**���������е�DAOģ�ͽӿ� */
	public static interface ISQLiteIndexDataDAO extends ISQLiteDataDAO
	{
		/**�������ʵ��������������� */
		String   getIndexColumnName();
		/**�������ʵ�����������ֵ */
		String   getIndexValues();
	}
	
	/**
	 * �ڲ��ӿڣ��ṩ���ݿ�����ƣ�·����������Ϣ
	 * */
	public static interface IDataBaseInfo
	{
		/**��ȡ���ݿ������·��*/
		String getDataBaseFullPath();
		/**��ȡ���ݿ������·��(��β������/)*/
		String getDataBasePath();
		/**��ȡ���ݿ���ļ���(����.db��׺)*/
		String getDataBaseName();
	}
}
