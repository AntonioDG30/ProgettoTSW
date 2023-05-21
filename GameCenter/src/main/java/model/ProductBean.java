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
	int PEGI;
	String genere;
	String piattaforma;
	int disp_Ps5_Fisico;
	int disp_Ps5_Digitale;
	int disp_Ps4_Fisico;
	int disp_Ps4_Digitale;
	int disp_XboxX_Fisico;
	int disp_XboxX_Digitale;
	int disp_XboxS_Fisico;
	int disp_XboxS_Digitale;
	int disp_Pc_Fisico;
	int disp_Pc_Digitale;

	

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
		this.PEGI=0;
		this.genere="";
		this.piattaforma="";
		this.disp_Ps5_Fisico=0;
		this.disp_Ps5_Digitale=0;
		this.disp_Ps4_Fisico=0;
		this.disp_Ps4_Digitale=0;
		this.disp_XboxX_Fisico=0;
		this.disp_XboxX_Digitale=0;
		this.disp_XboxS_Fisico=0;
		this.disp_XboxS_Digitale=0;
		this.disp_Pc_Fisico=0;
		this.disp_Pc_Digitale=0;
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

	public int getPEGI() 
	{
		return PEGI;
	}

	public void setPEGI(int PEGI) 
	{
		this.PEGI = PEGI;
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

	public int getDisp_Ps5_Fisico() 
	{
		return disp_Ps5_Fisico;
	}

	public void setDisp_Ps5_Fisico(int disp_Ps5_Fisico) 
	{
		this.disp_Ps5_Fisico = disp_Ps5_Fisico;
	}

	public int getDisp_Ps5_Digitale() 
	{
		return disp_Ps5_Digitale;
	}

	public void setDisp_Ps5_Digitale(int disp_Ps5_Digitale) 
	{
		this.disp_Ps5_Digitale = disp_Ps5_Digitale;
	}

	public int getDisp_Ps4_Fisico() 
	{
		return disp_Ps4_Fisico;
	}

	public void setDisp_Ps4_Fisico(int disp_Ps4_Fisico) 
	{
		this.disp_Ps4_Fisico = disp_Ps4_Fisico;
	}

	public int getDisp_Ps4_Digitale() 
	{
		return disp_Ps4_Digitale;
	}

	public void setDisp_Ps4_Digitale(int disp_Ps4_Digitale) 
	{
		this.disp_Ps4_Digitale = disp_Ps4_Digitale;
	}

	public int getDisp_XboxX_Fisico() 
	{
		return disp_XboxX_Fisico;
	}

	public void setDisp_XboxX_Fisico(int disp_XboxX_Fisico) 
	{
		this.disp_XboxX_Fisico = disp_XboxX_Fisico;
	}

	public int getDisp_XboxX_Digitale() 
	{
		return disp_XboxX_Digitale;
	}

	public void setDisp_XboxX_Digitale(int disp_XboxX_Digitale) 
	{
		this.disp_XboxX_Digitale = disp_XboxX_Digitale;
	}

	public int getDisp_XboxS_Fisico() 
	{
		return disp_XboxS_Fisico;
	}

	public void setDisp_XboxS_Fisico(int disp_XboxS_Fisico) 
	{
		this.disp_XboxS_Fisico = disp_XboxS_Fisico;
	}

	public int getDisp_XboxS_Digitale() 
	{
		return disp_XboxS_Digitale;
	}

	public void setDisp_XboxS_Digitale(int disp_XboxS_Digitale) 
	{
		this.disp_XboxS_Digitale = disp_XboxS_Digitale;
	}

	public int getDisp_Pc_Fisico() 
	{
		return disp_Pc_Fisico;
	}

	public void setDisp_Pc_Fisico(int disp_Pc_Fisico) 
	{
		this.disp_Pc_Fisico = disp_Pc_Fisico;
	}

	public int getDisp_Pc_Digitale()
	{
		return disp_Pc_Digitale;
	}

	public void setDisp_Pc_Digitale(int disp_Pc_Digitale) 
	{
		this.disp_Pc_Digitale = disp_Pc_Digitale;
	}

	@Override
	public String toString() 
	{
		return "ProductBean [codSeriale=" + codSeriale + ", nome=" + nome + ", prezzo=" + prezzo + ", dataUscita="
				+ dataUscita + ", descrizioneRidotta=" + descrizioneRidotta + ", descrizioneCompleta="
				+ descrizioneCompleta + ", immagine=" + immagine + ", tipologia=" + tipologia + ", quantita=" + quantita
				+ ", PEGI=" + PEGI + ", genere=" + genere + ", piattaforma=" + piattaforma + ", disp_Ps5_Fisico="
				+ disp_Ps5_Fisico + ", disp_Ps5_Digitale=" + disp_Ps5_Digitale + ", disp_Ps4_Fisico=" + disp_Ps4_Fisico
				+ ", disp_Ps4_Digitale=" + disp_Ps4_Digitale + ", disp_XboxX_Fisico=" + disp_XboxX_Fisico
				+ ", disp_XboxX_Digitale=" + disp_XboxX_Digitale + ", disp_XboxS_Fisico=" + disp_XboxS_Fisico
				+ ", disp_XboxS_Digitale=" + disp_XboxS_Digitale + ", disp_Pc_Fisico=" + disp_Pc_Fisico
				+ ", disp_Pc_Digitale=" + disp_Pc_Digitale + "]";
	}

}
