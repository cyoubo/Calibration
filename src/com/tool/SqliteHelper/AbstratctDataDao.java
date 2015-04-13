package com.tool.SqliteHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.ContentValues;
import android.database.Cursor;

import com.tool.SqliteHelper.ISQLiteDataInterface.IDataBaseInfo;
import com.tool.SqliteHelper.ISQLiteDataInterface.ISQLiteDataDAO;
/**
 * ������Dao�࣬�����࣬�������ڱ��̳�<br>
 * �Ѿ�ͨ������ʵ�ֵĹ��ܰ���
 * <ul>
 * <li>��ȡ������������</li>
 * <li>���ݻ�ȡ�����������ContentValue����װ</li>
 * <li>ֻ����������bean������������ݲ���</li>
 * </ul>
 * */
public abstract class AbstratctDataDao<T> implements ISQLiteDataDAO
{
	/**
	 * ��ö�Ӧbean�����ʵ��<br>
	 * һ��д�� return beans;
	 * */
	abstract public T getBeansObj();
	
	/**
	 * ���ö�Ӧbean�����ʵ��<br>
	 * һ��д�� this.t=t;
	 * */
	abstract public void setBeansObj(T t);

	/**
	 * ��ö�Ӧbean���������<br>
	 * һ��д�� return T.class;
	 * */
	abstract protected Class<T> getBeansClass();

	@Override
	public String[] getColumnNames()
	{
		Class<T> ct = getBeansClass();
		Field[] fields = ct.getDeclaredFields();
		String[] ColumnNames = new String[fields.length];
		for (int i = 0; i < ColumnNames.length; i++)
			ColumnNames[i] = fields[i].getName();
		return ColumnNames;
	}

	@Override
	public ContentValues CombineContentValues()
	{
		//׼�����ݼ���
		ContentValues values = new ContentValues();
		//��ȡ����
		String[] ColumnNames = getColumnNames();
		//�����ȡʵ��bean������
		Class<T> ct = getBeansClass();
		//ѭ����ֵ
		for (int i = 0; i < ColumnNames.length; i++)
		{
			try
			{
				//�Է������getter��������ʽ��ȡ��Ҫ�������ݣ����˰��ã�Ҳ��ֱ�ӷ������ԣ�
				Method method = ct.getDeclaredMethod("get" + UpCassHeader(ColumnNames[i]));
				//��getBeansObj()�������getter����������ȡֵ
				Object value = method.invoke(getBeansObj());
				//��ȡ��ȡֵ������
				Class<?> type = method.getReturnType();
				//��ȡֵ�����жϣ������ö�Ӧput����
				if (type == Double.class)
					values.put(ColumnNames[i], Double.parseDouble(value.toString()));
				else if (type == Integer.class)
					values.put(ColumnNames[i], Integer.parseInt(value.toString()));
				else if (type == Short.class)
					values.put(ColumnNames[i], Short.parseShort(value.toString()));
				else if (type == Long.class)
					values.put(ColumnNames[i], Long.parseLong(value.toString()));
				else if (type == Boolean.class)
					values.put(ColumnNames[i], Boolean.parseBoolean(value.toString()));
				else if (type == Float.class)
					values.put(ColumnNames[i], Float.parseFloat(value.toString()));
				else
					values.put(ColumnNames[i], value.toString());
			}
			catch (NoSuchMethodException e)
			{
				System.err.println("NoSuchMethodException");
			}
			catch (IllegalAccessException e)
			{
				System.err.println("IllegalAccessException");
			}
			catch (IllegalArgumentException e)
			{
				System.err.println("IllegalArgumentException");
			}
			catch (InvocationTargetException e)
			{
				System.err.println("InvocationTargetException");
			}
		}
		return values;
	}

	@Override
	public boolean UpdataBeans(IDataBaseInfo info)
	{
		boolean reuslt = false;
		//���ݿ��ѯ��ǰʵ����ļ�¼
		SQLiteHelper helper = new SQLiteHelper(info.getDataBaseFullPath());
		helper.OpenDataBase(false);
		Cursor cursor = helper.Query(this);
		//����ѯ�ɹ������ʵ����
		if (cursor != null && cursor.moveToFirst())
		{
			//��ȡʵ�������
			T data = getBeansObj();
			//��ȡʵ��������
			Class<T> ct = getBeansClass();
			//��ȡ����
			String[] colnames = getColumnNames();

			for (int index = 0; index < colnames.length; index++)
			{
				try
				{
					//�Է������Ե���ʽ�޸�����ֵ
					//��ȡָ��������Ӧ����������
					Field field = ct.getDeclaredField(colnames[index]);
					Class<?> type = field.getType();
					//�����������ͣ���Ӧ��ֵ
					if (type == Boolean.class)
						field.setBoolean(data,Boolean.parseBoolean(cursor.getString(index)));
					else if (type == Double.class)
						field.setDouble(data, cursor.getDouble(index));
					else if (type == Integer.class)
						field.setInt(data, cursor.getInt(index));
					else if (type == Long.class)
						field.setLong(data, cursor.getLong(index));
					else if (field.getType() == Short.class)
						field.setShort(data, cursor.getShort(index));
					else
						field.set(data, cursor.getString(index));
					//���¶���
					this.setBeansObj(data);
					reuslt=true;
				}
				catch (NoSuchFieldException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return reuslt;
	}

	/**
	 * ����ĸ��д
	 * 
	 * @param string
	 *            ��ת�����ַ���
	 * */
	private String UpCassHeader(String string)
	{
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

}
