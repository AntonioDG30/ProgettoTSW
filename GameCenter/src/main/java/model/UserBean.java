package model;

import java.io.Serializable;

public class UserBean implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	String Email;
	String Password;
	int PuntiFedelta;
	boolean Tipo;
	String CodiceFiscale;
	String Nome;
	String Cognome;
	int CAP;
	String Via;
	int Civico;
	String Citta;
	String Provincia;
	String NumeroTelefono;
	

	public UserBean() 
	{
		this.Email="";
		this.Password="";
		this.PuntiFedelta=0;
		this.Tipo=false;
		this.CodiceFiscale="";
		this.Nome="";
		this.Cognome="";
		this.CAP=0;
		this.Via="";
		this.Civico=0;
		this.Citta="";
		this.Provincia="";
		this.NumeroTelefono="";
	}

	public String getEmail() 
	{
		return Email;
	}

	public void setEmail(String email) 
	{
		this.Email = email;
	}

	public String getPassword() 
	{
		return Password;
	}

	public void setPassword(String password) 
	{
		this.Password = password;
	}
	
	public int getPuntiFedelta() 
	{
		return PuntiFedelta;
	}

	public void setPuntiFedelta(int puntifedelta) 
	{
		this.PuntiFedelta = puntifedelta;
	}

	public boolean getTipo() 
	{
		return Tipo;
	}

	public void setTipo(boolean tipo) 
	{
		this.Tipo = tipo;
	}
	
	

	public String getCodiceFiscale() 
	{
		return CodiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) 
	{
		this.CodiceFiscale = codiceFiscale;
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
		return "UserBean [Email=" + Email + ", Password=" + Password + ", PuntiFedelta=" + PuntiFedelta + ", Tipo="
				+ Tipo + ", CodiceFiscale=" + CodiceFiscale + ", Nome=" + Nome + ", Cognome=" + Cognome + ", CAP=" + CAP
				+ ", Via=" + Via + ", Civico=" + Civico + ", Citta=" + Citta + ", Provincia=" + Provincia
				+ ", NumeroTelefono=" + NumeroTelefono + "]";
	}

	
	
}
