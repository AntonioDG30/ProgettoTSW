package model;

import java.io.Serializable;


public class ProductBean implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	String codSeriale;
	String nome;
	float prezzo;
	String dataUscita;
	String descrizioneRidotta;
	String descrizioneCompleta;
	String immagine;
	boolean tipologia;
	int quantita;
	int pegi;
	String genere;
	String piattaforma;
	int dispPs5Fisico;
	int dispPs5Digitale;
	int dispPs4Fisico;
	int dispPs4Digitale;
	int dispXboxXFisico;
	int dispXboxXDigitale;
	int dispXboxSFisico;
	int dispXboxSDigitale;
	int dispPcFisico;
	int dispPcDigitale;

	

	public ProductBean() 
	{
		this.codSeriale="";
		this.nome="";
		this.prezzo=0;
		this.dataUscita="";
		this.descrizioneRidotta="";
		this.descrizioneCompleta="";
		this.immagine="";
		this.tipologia=false;
		this.quantita=0;
		this.pegi=0;
		this.genere="";
		this.piattaforma="";
		this.dispPs5Fisico=0;
		this.dispPs5Digitale=0;
		this.dispPs4Fisico=0;
		this.dispPs4Digitale=0;
		this.dispXboxXFisico=0;
		this.dispXboxXDigitale=0;
		this.dispXboxSFisico=0;
		this.dispXboxSDigitale=0;
		this.dispPcFisico=0;
		this.dispPcDigitale=0;
	}

	public String getCodSeriale() 
	{
		return codSeriale;
	}

	public void setCodSeriale(String codSeriale) 
	{
		this.codSeriale = codSeriale;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	public float getPrezzo() 
	{
		return prezzo;
	}

	public void setPrezzo(float prezzo) 
	{
		this.prezzo = prezzo;
	}

	public String getDataUscita() 
	{
		return dataUscita;
	}

	public void setDataUscita(String dataUscita) 
	{
		this.dataUscita = dataUscita;
	}

	public String getDescrizioneRidotta() 
	{
		return descrizioneRidotta;
	}

	public void setDescrizioneRidotta(String descrizioneRidotta) 
	{
		this.descrizioneRidotta = descrizioneRidotta;
	}

	public String getDescrizioneCompleta() 
	{
		return descrizioneCompleta;
	}

	public void setDescrizioneCompleta(String descrizioneCompleta) 
	{
		this.descrizioneCompleta = descrizioneCompleta;
	}	
	
	public String getImmagine() 
	{
		return immagine;
	}

	public void setImmagine(String immagine) 
	{
		this.immagine = immagine;
	}

	public boolean getTipologia() 
	{
		return tipologia;
	}

	public void setTipologia(boolean tipologia) 
	{
		this.tipologia = tipologia;
	}

	public int getQuantita() 
	{
		return quantita;
	}

	public void setQuantita(int quantita) 
	{
		this.quantita = quantita;
	}
	
	public void incrementQuantita()
	{
		this.quantita = this.quantita + 1;
	}
	
	public void decrementQuantita()
	{
		this.quantita = this.quantita - 1;
	}

	public int getPegi() 
	{
		return pegi;
	}

	public void setPegi(int pegi) 
	{
		this.pegi = pegi;
	}

	public String getGenere() 
	{
		return genere;
	}

	public void setGenere(String genere) 
	{
		this.genere = genere;
	}
	
	

	public String getPiattaforma()
	{
		return piattaforma;
	}

	public void setPiattaforma(String piattaforma) 
	{
		this.piattaforma = piattaforma;
	}

	public int getDispPs5Fisico() 
	{
		return dispPs5Fisico;
	}

	public void setDispPs5Fisico(int dispPs5Fisico) 
	{
		this.dispPs5Fisico = dispPs5Fisico;
	}

	public int getDispPs5Digitale() 
	{
		return dispPs5Digitale;
	}

	public void setDispPs5Digitale(int dispPs5Digitale) 
	{
		this.dispPs5Digitale = dispPs5Digitale;
	}

	public int getDispPs4Fisico() 
	{
		return dispPs4Fisico;
	}

	public void setDispPs4Fisico(int dispPs4Fisico) 
	{
		this.dispPs4Fisico = dispPs4Fisico;
	}

	public int getDispPs4Digitale() 
	{
		return dispPs4Digitale;
	}

	public void setDispPs4Digitale(int dispPs4Digitale) 
	{
		this.dispPs4Digitale = dispPs4Digitale;
	}

	public int getDispXboxXFisico() 
	{
		return dispXboxXFisico;
	}

	public void setDispXboxXFisico(int dispXboxXFisico) 
	{
		this.dispXboxXFisico = dispXboxXFisico;
	}

	public int getDispXboxXDigitale() 
	{
		return dispXboxXDigitale;
	}

	public void setDispXboxXDigitale(int dispXboxXDigitale) 
	{
		this.dispXboxXDigitale = dispXboxXDigitale;
	}

	public int getDispXboxSFisico() 
	{
		return dispXboxSFisico;
	}

	public void setDispXboxSFisico(int dispXboxSFisico) 
	{
		this.dispXboxSFisico = dispXboxSFisico;
	}

	public int getDispXboxSDigitale() 
	{
		return dispXboxSDigitale;
	}

	public void setDispXboxSDigitale(int dispXboxSDigitale) 
	{
		this.dispXboxSDigitale = dispXboxSDigitale;
	}

	public int getDispPcFisico() 
	{
		return dispPcFisico;
	}

	public void setDispPcFisico(int dispPcFisico) 
	{
		this.dispPcFisico = dispPcFisico;
	}

	public int getDispPcDigitale()
	{
		return dispPcDigitale;
	}

	public void setDispPcDigitale(int dispPcDigitale) 
	{
		this.dispPcDigitale = dispPcDigitale;
	}

	@Override
	public String toString() 
	{
		return "ProductBean [codSeriale=" + codSeriale + ", nome=" + nome + ", prezzo=" + prezzo + ", dataUscita="
				+ dataUscita + ", descrizioneRidotta=" + descrizioneRidotta + ", descrizioneCompleta="
				+ descrizioneCompleta + ", immagine=" + immagine + ", tipologia=" + tipologia + ", quantita=" + quantita
				+ ", pegi=" + pegi + ", genere=" + genere + ", piattaforma=" + piattaforma + ", dispPs5Fisico="
				+ dispPs5Fisico + ", dispPs5Digitale=" + dispPs5Digitale + ", dispPs4Fisico=" + dispPs4Fisico
				+ ", dispPs4Digitale=" + dispPs4Digitale + ", dispXboxXFisico=" + dispXboxXFisico
				+ ", dispXboxXDigitale=" + dispXboxXDigitale + ", dispXboxSFisico=" + dispXboxSFisico
				+ ", dispXboxSDigitale=" + dispXboxSDigitale + ", dispPcFisico=" + dispPcFisico
				+ ", dispPcDigitale=" + dispPcDigitale + "]";
	}

}
