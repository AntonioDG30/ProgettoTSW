package model;

import java.io.Serializable;

public class GenereBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String nomeGenere;

	public GenereBean() 
	{
		this.nomeGenere="";
	}

	public String getNomeGenere() 
	{
		return nomeGenere;
	}

	public void setNomeGenere(String nomeGenere) 
	{
		this.nomeGenere = nomeGenere;
	}

	@Override
	public String toString() 
	{
		return "GenereBean [NomeGenere=" + nomeGenere + "]";
	}


}
