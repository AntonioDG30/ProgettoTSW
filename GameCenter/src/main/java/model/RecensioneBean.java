package model;

import java.io.Serializable;

public class RecensioneBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	int valutazione;
	String descrizione;
	
	public RecensioneBean() 
	{
		this.valutazione=0;
		this.descrizione="";
	}

	public int getValutazione() 
	{
		return valutazione;
	}

	public void setValutazione(int valutazione) 
	{
		this.valutazione = valutazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) 
	{
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "RecensioneBean [valutazione=" + valutazione + ", descrizione=" + descrizione + "]";
	}
}
