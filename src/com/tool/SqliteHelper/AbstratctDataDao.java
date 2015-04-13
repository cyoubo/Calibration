package com.tool.SqliteHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.ContentValues;
import android.database.Cursor;

import com.tool.SqliteHelper.ISQLiteDataInterface.IDataBaseInfo;
import com.tool.SqliteHelper.ISQLiteDataInterface.ISQLiteDataDAO;
/**
 * 基础的Dao类，抽象类，仅能用于被继承<br>
 * 已经通过反射实现的功能包括
 * <ul>
 * <li>获取属性名即列名</li>
 * <li>根据获取的属性名完成ContentValue的组装</li>
 * <li>只包含主键的bean对象的其他数据补充</li>
 * </ul>
 * */
public abstract class AbstratctDataDao<T> implements ISQLiteDataDAO
{
	/**
	 * 获得对应bean对象的实例<br>
	 * 一般写作 return beans;
	 * */
	abstract public T getBeansObj();
	
	/**
	 * 设置对应bean对象的实例<br>
	 * 一般写作 this.t=t;
	 * */
	abstract public void setBeansObj(T t);

	/**
	 * 获得对应bean对象的类型<br>
	 * 一般写作 return T.class;
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
		//准备数据集合
		ContentValues values = new ContentValues();
		//获取列名
		String[] ColumnNames = getColumnNames();
		//反射获取实体bean的类型
		Class<T> ct = getBeansClass();
		//循环赋值
		for (int i = 0; i < ColumnNames.length; i++)
		{
			try
			{
				//以反射调用getter方法的形式获取需要入库的数据（个人爱好，也可直接访问属性）
				Method method = ct.getDeclaredMethod("get" + UpCassHeader(ColumnNames[i]));
				//以getBeansObj()对象调用getter方法，并获取值
				Object value = method.invoke(getBeansObj());
				//获取获取值的类型
				Class<?> type = method.getReturnType();
				//获取值类型判断，并调用对应put方法
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
		//数据库查询当前实体类的记录
		SQLiteHelper helper = new SQLiteHelper(info.getDataBaseFullPath());
		helper.OpenDataBase(false);
		Cursor cursor = helper.Query(this);
		//若查询成功则更新实体类
		if (cursor != null && cursor.moveToFirst())
		{
			//获取实体类对象
			T data = getBeansObj();
			//获取实体类类型
			Class<T> ct = getBeansClass();
			//获取列名
			String[] colnames = getColumnNames();

			for (int index = 0; index < colnames.length; index++)
			{
				try
				{
					//以访问属性的形式修改属性值
					//获取指定列名对应的属性名称
					Field field = ct.getDeclaredField(colnames[index]);
					Class<?> type = field.getType();
					//根据属性类型，对应赋值
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
					//更新对象
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
	 * 首字母大写
	 * 
	 * @param string
	 *            待转换的字符串
	 * */
	private String UpCassHeader(String string)
	{
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

}
