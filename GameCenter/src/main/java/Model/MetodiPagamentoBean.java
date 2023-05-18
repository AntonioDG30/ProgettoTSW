package Model;

import java.io.Serializable;

public class MetodiPagamentoBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String NumeroCarta;
    String TitolareCarta;
    String Scadenza;
    String Email; 
    
    public MetodiPagamentoBean() 
	{
    	this.NumeroCarta="";
		this.TitolareCarta="";
		this.Scadenza="";
		this.Email="";
	}

	public String getNumeroCarta() 
	{
		return NumeroCarta;
	}

	public void setNumeroCarta(String numeroCarta) 
	{
		this.NumeroCarta = numeroCarta;
	}

	public String getTitolareCarta() {
		return TitolareCarta;
	}

	public void setTitolareCarta(String titolareCarta) 
	{
		this.TitolareCarta = titolareCarta;
	}

	public String getScadenza() 
	{
		return Scadenza;
	}

	public void setScadenza(String scadenza)
	{
		this.Scadenza = scadenza;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) 
	{
		this.Email = email;
	}

	@Override
	public String toString() 
	{
		return "MetodiPagamentoBean [NumeroCarta=" + NumeroCarta + ", TitolareCarta=" + TitolareCarta + ", Scadenza="
				+ Scadenza + ", Email=" + Email + "]";
	}
    
	
	
	
    

}
