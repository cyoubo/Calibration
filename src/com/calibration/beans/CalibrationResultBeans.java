package com.calibration.beans;

import java.io.Serializable;


public class CalibrationResultBeans implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4527690292695444079L;

	public Long getIndex()
	{
		return index;
	}

	public void setIndex(Long index)
	{
		this.index = index;
	}

	public double getFx()
	{
		return fx;
	}

	public void setFx(double fx)
	{
		this.fx = fx;
	}

	public double getFy()
	{
		return fy;
	}

	public void setFy(double fy)
	{
		this.fy = fy;
	}

	public double getCx()
	{
		return cx;
	}

	public void setCx(double cx)
	{
		this.cx = cx;
	}

	public double getCy()
	{
		return cy;
	}

	public void setCy(double cy)
	{
		this.cy = cy;
	}

	public double getK1()
	{
		return k1;
	}

	public void setK1(double k1)
	{
		this.k1 = k1;
	}

	public double getK2()
	{
		return k2;
	}

	public void setK2(double k2)
	{
		this.k2 = k2;
	}


	public double getRms()
	{
		return rms;
	}

	public void setRms(double rms)
	{
		this.rms = rms;
	}

	public String getResultdate()
	{
		return resultdate;
	}

	public void setResultdate(String resultdate)
	{
		this.resultdate = resultdate;
	}

	private Long index;
	private double fx,fy,cx,cy;
	private double k1,k2;
	private double p1,p2;
	private double rms;
	
	public double getP1()
	{
		return p1;
	}

	public void setP1(double p1)
	{
		this.p1 = p1;
	}

	public double getP2()
	{
		return p2;
	}

	public void setP2(double p2)
	{
		this.p2 = p2;
	}


	private String resultdate;
	
	private String resolution;

	public String getResolution()
	{
		return resolution;
	}

	public void setResolution(String resolution)
	{
		this.resolution = resolution;
	}
}
