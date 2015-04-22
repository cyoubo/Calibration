package com.calibration.beans;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="CalibrationResult")
public class CalibrationResultBeans implements Serializable
{
	private static final long serialVersionUID = 4527690292695444079L;

	@DatabaseField(columnName="cx")
	private double cx;

	@DatabaseField(columnName="cy")
	private double cy;

	@DatabaseField(columnName="fx")
	private double fx;

	@DatabaseField(columnName="fy")
	private double fy;

	@DatabaseField(generatedId=true,columnName="index")
	private Long index;

	@DatabaseField(columnName="k1")
	private double k1;

	@DatabaseField(columnName="k2")
	private double k2;

	@DatabaseField(columnName="p1")
	private double p1;

	@DatabaseField(columnName="p2")
	private double p2;
	

	@DatabaseField(columnName="resolution")
	private String resolution;

	@DatabaseField(columnName="resultdate")
	private String resultdate;

	@DatabaseField(columnName="rms")
	private double rms;

	public double getCx()
	{
		return cx;
	}

	public double getCy()
	{
		return cy;
	}


	public double getFx()
	{
		return fx;
	}

	public double getFy()
	{
		return fy;
	}

	public Long getIndex()
	{
		return index;
	}

	public double getK1()
	{
		return k1;
	}

	public double getK2()
	{
		return k2;
	}
	public double getP1()
	{
		return p1;
	}
	public double getP2()
	{
		return p2;
	}
	public String getResolution()
	{
		return resolution;
	}
	public String getResultdate()
	{
		return resultdate;
	}
	public double getRms()
	{
		return rms;
	}
	public void setCx(double cx)
	{
		this.cx = cx;
	}
	public void setCy(double cy)
	{
		this.cy = cy;
	}
	public void setFx(double fx)
	{
		this.fx = fx;
	}
	public void setFy(double fy)
	{
		this.fy = fy;
	}
	
	public void setIndex(Long index)
	{
		this.index = index;
	}

	public void setK1(double k1)
	{
		this.k1 = k1;
	}

	public void setK2(double k2)
	{
		this.k2 = k2;
	}

	public void setP1(double p1)
	{
		this.p1 = p1;
	}


	public void setP2(double p2)
	{
		this.p2 = p2;
	}
	
	public void setResolution(String resolution)
	{
		this.resolution = resolution;
	}

	public void setResultdate(String resultdate)
	{
		this.resultdate = resultdate;
	}

	public void setRms(double rms)
	{
		this.rms = rms;
	}
	
	@Override
	public String toString()
	{
		StringBuffer buffer=new StringBuffer();
		buffer.append("index  "+this.index);
		buffer.append("\tResolution  "+this.resolution);
		buffer.append("\tfx  "+this.fx);
		return buffer.toString();
	}
}
