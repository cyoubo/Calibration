package com.calibration.beans;


public class CalibrationResultBeans
{
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

	public double getK3()
	{
		return k3;
	}

	public void setK3(double k3)
	{
		this.k3 = k3;
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
	private double k1,k2,k3;
	private double rms;
	
	private String resultdate;
}
