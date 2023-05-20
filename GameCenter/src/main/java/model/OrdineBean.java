package model;

import java.io.Serializable;

public class OrdineBean implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	int CodOrdine;
	float Sconto;
	String DataAcquisto;
	String Fattura;
	float PrezzoTotale;
	String StatoOrdine;
	String Email;

	public OrdineBean() 
	{
		this.CodOrdine=0;
		this.Sconto=0;
		this.DataAcquisto="";
		this.Fattura="";
		this.PrezzoTotale=0;
		this.StatoOrdine="";
		this.Email="";
	}

	public int getCodOrdine() 
	{
		return CodOrdine;
	}

	public void setCodOrdine(int codOrdine) 
	{
		this.CodOrdine = codOrdine;
	}

	public float getSconto() 
	{
		return Sconto;
	}

	public void setSconto(float Sconto) 
	{
		this.Sconto = Sconto;
	}

	public String getDataAcquisto() 
	{
		return DataAcquisto;
	}

	public void setDataAcquisto(String dataAcquisto) 
	{
		this.DataAcquisto = dataAcquisto;
	}

	public String getFattura() 
	{
		return Fattura;
	}

	public void setFattura(String fattura) 
	{
		this.Fattura = fattura;
	}

	public float getPrezzoTotale() 
	{
		return PrezzoTotale;
	}

	public void setPrezzoTotale(float prezzoTotale) 
	{
		this.PrezzoTotale = prezzoTotale;
	}

	public String getStatoOrdine() 
	{
		return StatoOrdine;
	}

	public void setStatoOrdine(String statoOrdine) 
	{
		this.StatoOrdine = statoOrdine;
	}

	public String getEmail() 
	{
		return Email;
	}

	public void setEmail(String email) 
	{
		this.Email = email;
	}
	
	

}
