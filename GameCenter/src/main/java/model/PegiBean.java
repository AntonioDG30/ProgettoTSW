package model;

import java.io.Serializable;

public class PegiBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	int codPEGI;

	public PegiBean() 
	{
		this.codPEGI=3;
	}

	public int getCodPEGI() 
	{
		return codPEGI;
	}

	public void setCodPEGI(int CodPEGI) 
	{
		this.codPEGI = CodPEGI;
	}

	@Override
	public String toString() 
	{
		return "PegiBean [codPEGI=" + codPEGI + "]";
	}

	

}