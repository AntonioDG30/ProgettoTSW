package model;

import java.io.Serializable;

public class IndirizziSpedizioneBean implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	int codIndirizzo;
	String email;
	String nome;
	String cognome;
	int cap;
	String via;
	int civico;
	String citta;
	String provincia;
	String numeroTelefono;
	

	public IndirizziSpedizioneBean() 
	{
		this.codIndirizzo=0;
		this.email="";
		this.nome="";
		this.cognome="";
		this.cap=0;
		this.via="";
		this.civico=0;
		this.citta="";
		this.provincia="";
		this.numeroTelefono="";
	}
	
	

	public int getCodIndirizzo() 
	{
		return codIndirizzo;
	}

	public void setCodIndirizzo(int codIndirizzo) 
	{
		this.codIndirizzo = codIndirizzo;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getCognome() 
	{
		return cognome;
	}

	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}

	public int getCAP() 
	{
		return cap;
	}

	public void setCAP(int cap) 
	{
		this.cap = cap;
	}

	public String getVia() 
	{
		return via;
	}

	public void setVia(String via) 
	{
		this.via = via;
	}

	public int getCivico() 
	{
		return civico;
	}

	public void setCivico(int civico) 
	{
		this.civico = civico;
	}

	public String getCitta() 
	{
		return citta;
	}

	public void setCitta(String citta) 
	{
		this.citta = citta;
	}

	public String getProvincia() 
	{
		return provincia;
	}

	public void setProvincia(String provincia) 
	{
		this.provincia = provincia;
	}

	public String getNumeroTelefono() 
	{
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) 
	{
		this.numeroTelefono = numeroTelefono;
	}

	@Override
	public String toString() 
	{
		return "IndirizziSpedizioneBean [codIndirizzo=" + codIndirizzo + ", email=" + email + ", nome=" + nome
				+ ", cognome=" + cognome + ", CAP=" + cap + ", via=" + via + ", civico=" + civico + ", citta=" + citta
				+ ", provincia=" + provincia + ", numeroTelefono=" + numeroTelefono + "]";
	}
	
	

}
