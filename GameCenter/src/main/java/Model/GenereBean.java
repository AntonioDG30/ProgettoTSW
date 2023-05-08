package Model;

import java.io.Serializable;

public class GenereBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String NomeGenere;

	public GenereBean() 
	{
		this.NomeGenere="";
	}

	public String getNomeGenere() 
	{
		return NomeGenere;
	}

	public void setNomeGenere(String nomeGenere) 
	{
		this.NomeGenere = nomeGenere;
	}

	@Override
	public String toString() 
	{
		return "GenereBean [NomeGenere=" + NomeGenere + "]";
	}


}
