package model;

import java.io.Serializable;

public class UserBean implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	String email;
	String password;
	int puntiFedelta;
	boolean tipo;
	String codiceFiscale;
	String nome;
	String cognome;
	int cap;
	String via;
	int civico;
	String citta;
	String provincia;
	String numeroTelefono;
	

	public UserBean() 
	{
		this.email="";
		this.password="";
		this.puntiFedelta=0;
		this.tipo=false;
		this.codiceFiscale="";
		this.nome="";
		this.cognome="";
		this.cap=0;
		this.via="";
		this.civico=0;
		this.citta="";
		this.provincia="";
		this.numeroTelefono="";
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public int getPuntiFedelta() 
	{
		return puntiFedelta;
	}

	public void setPuntiFedelta(int puntifedelta) 
	{
		this.puntiFedelta = puntifedelta;
	}

	public boolean getTipo() 
	{
		return tipo;
	}

	public void setTipo(boolean tipo) 
	{
		this.tipo = tipo;
	}
	
	public String getCodiceFiscale() 
	{
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) 
	{
		this.codiceFiscale = codiceFiscale;
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
		return "UserBean [email=" + email + ", password=" + password + ", puntiFedelta=" + puntiFedelta + ", tipo="
				+ tipo + ", codiceFiscale=" + codiceFiscale + ", nome=" + nome + ", cognome=" + cognome + ", CAP=" + cap
				+ ", via=" + via + ", civico=" + civico + ", citta=" + citta + ", provincia=" + provincia
				+ ", numeroTelefono=" + numeroTelefono + "]";
	}
	
	
}
