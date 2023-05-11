package Model;

import java.io.Serializable;

public class PegiBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	int CodPEGI;

	public PegiBean() 
	{
		this.CodPEGI=3;
	}

	public int getCodPEGI() 
	{
		return CodPEGI;
	}

	public void setCodPEGI(int CodPEGI) 
	{
		this.CodPEGI = CodPEGI;
	}

	

}