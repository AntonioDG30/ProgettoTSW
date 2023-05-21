package model;

import java.io.Serializable;

public class PegiBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	int codPegi;

	public PegiBean() 
	{
		this.codPegi=3;
	}

	public int getCodPEGI() 
	{
		return codPegi;
	}

	public void setCodPEGI(int codPegi) 
	{
		this.codPegi = codPegi;
	}

	@Override
	public String toString() 
	{
		return "PegiBean [codPEGI=" + codPegi + "]";
	}

	

}