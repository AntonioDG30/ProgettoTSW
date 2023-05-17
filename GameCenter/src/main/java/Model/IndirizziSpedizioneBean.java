package Model;

import java.io.Serializable;

public class IndirizziSpedizioneBean implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	int CodIndirizzo;
	String Email;
	String Nome;
	String Cognome;
	int CAP;
	String Via;
	int Civico;
	String Citta;
	String Provincia;
	String NumeroTelefono;
	

	public IndirizziSpedizioneBean() 
	{
		this.CodIndirizzo=0;
		this.Email="";
		this.Nome="";
		this.Cognome="";
		this.CAP=0;
		this.Via="";
		this.Civico=0;
		this.Citta="";
		this.Provincia="";
		this.NumeroTelefono="";
	}
	
	

	public int getCodIndirizzo() 
	{
		return CodIndirizzo;
	}

	public void setCodIndirizzo(int codIndirizzo) 
	{
		this.CodIndirizzo = codIndirizzo;
	}

	public String getEmail() 
	{
		return Email;
	}

	public void setEmail(String email) 
	{
		this.Email = email;
	}

	public String getNome() 
	{
		return Nome;
	}

	public void setNome(String nome) 
	{
		this.Nome = nome;
	}

	public String getCognome() 
	{
		return Cognome;
	}

	public void setCognome(String cognome) 
	{
		this.Cognome = cognome;
	}

	public int getCAP() 
	{
		return CAP;
	}

	public void setCAP(int CAP) 
	{
		this.CAP = CAP;
	}

	public String getVia() 
	{
		return Via;
	}

	public void setVia(String via) 
	{
		this.Via = via;
	}

	public int getCivico() 
	{
		return Civico;
	}

	public void setCivico(int civico) 
	{
		this.Civico = civico;
	}

	public String getCitta() 
	{
		return Citta;
	}

	public void setCitta(String citta) 
	{
		this.Citta = citta;
	}

	public String getProvincia() 
	{
		return Provincia;
	}

	public void setProvincia(String provincia) 
	{
		this.Provincia = provincia;
	}

	public String getNumeroTelefono() 
	{
		return NumeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) 
	{
		this.NumeroTelefono = numeroTelefono;
	}



	@Override
	public String toString() {
		return "IndirizziSpedizioneBean [CodIndirizzi=" + CodIndirizzo + ", Email=" + Email + ", Nome=" + Nome
				+ ", Cognome=" + Cognome + ", CAP=" + CAP + ", Via=" + Via + ", Civico=" + Civico + ", Citta=" + Citta
				+ ", Provincia=" + Provincia + ", NumeroTelefono=" + NumeroTelefono + "]";
	}
	
	
		
	
}
