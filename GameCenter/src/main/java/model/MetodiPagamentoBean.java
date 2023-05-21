package model;

import java.io.Serializable;

public class MetodiPagamentoBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String numeroCarta;
    String titolareCarta;
    String scadenza;
    String email; 
    
    public MetodiPagamentoBean() 
	{
    	this.numeroCarta="";
		this.titolareCarta="";
		this.scadenza="";
		this.email="";
	}

	public String getNumeroCarta() 
	{
		return numeroCarta;
	}

	public void setNumeroCarta(String numeroCarta) 
	{
		this.numeroCarta = numeroCarta;
	}

	public String getTitolareCarta() 
	{
		return titolareCarta;
	}

	public void setTitolareCarta(String titolareCarta) 
	{
		this.titolareCarta = titolareCarta;
	}

	public String getScadenza() 
	{
		return scadenza;
	}

	public void setScadenza(String scadenza)
	{
		this.scadenza = scadenza;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	@Override
	public String toString() 
	{
		return "MetodiPagamentoBean [numeroCarta=" + numeroCarta + ", titolareCarta=" + titolareCarta + ", scadenza="
				+ scadenza + ", email=" + email + "]";
	}
}
