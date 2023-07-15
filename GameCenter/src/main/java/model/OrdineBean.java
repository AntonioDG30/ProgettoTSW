package model;

import java.io.Serializable;

public class OrdineBean implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	int codOrdine;
	float sconto;
	String dataAcquisto;
	String fattura;
	float prezzoTotale;
	String statoOrdine;
	String email;

	public OrdineBean() 
	{
		this.codOrdine=0;
		this.sconto=0;
		this.dataAcquisto="";
		this.fattura="";
		this.prezzoTotale=0;
		this.statoOrdine="";
		this.email="";
	}

	public int getCodOrdine() 
	{
		return codOrdine;
	}

	public void setCodOrdine(int codOrdine) 
	{
		this.codOrdine = codOrdine;
	}

	public float getSconto() 
	{
		return sconto;
	}

	public void setSconto(float sconto) 
	{
		this.sconto = sconto;
	}

	public String getDataAcquisto() 
	{
		return dataAcquisto;
	}

	public void setDataAcquisto(String dataAcquisto) 
	{
		this.dataAcquisto = dataAcquisto;
	}

	public String getFattura() 
	{
		return fattura;
	}

	public void setFattura(String fattura) 
	{
		this.fattura = fattura;
	}

	public float getPrezzoTotale() 
	{
		return prezzoTotale;
	}

	public void setPrezzoTotale(float prezzoTotale) 
	{
		this.prezzoTotale = prezzoTotale;
	}

	public String getStatoOrdine() 
	{
		return statoOrdine;
	}

	public void setStatoOrdine(String statoOrdine) 
	{
		this.statoOrdine = statoOrdine;
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
		return "OrdineBean [codOrdine=" + codOrdine + ", sconto=" + sconto + ", dataAcquisto=" + dataAcquisto
				+ ", fattura=" + fattura + ", prezzoTotale=" + prezzoTotale + ", statoOrdine=" + statoOrdine
				+ ", email=" + email + "]";
	}
	
	

}
