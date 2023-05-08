package Model;

import java.io.Serializable;


public class ProductBean implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	String CodSeriale;
	String Nome;
	float Prezzo;
	String DataUscita;
	String DescrizioneRidotta;
	String DescrizioneCompleta;
	String Immagine;
	boolean Tipologia;
	int quantitaCarr;
	int PEGI;
	String Genere;
	int Disp_Ps5_Fisico;
	int Disp_Ps5_Digitale;
	int Disp_Ps4_Fisico;
	int Disp_Ps4_Digitale;
	int Disp_XboxX_Fisico;
	int Disp_XboxX_Digitale;
	int Disp_XboxS_Fisico;
	int Disp_XboxS_Digitale;
	int Disp_Pc_Fisico;
	int Disp_Pc_Digitale;
	
	

	public ProductBean() 
	{
		this.CodSeriale="";
		this.Nome="";
		this.Prezzo=0;
		this.DataUscita="";
		this.DescrizioneRidotta="";
		this.DescrizioneCompleta="";
		this.Immagine="";
		this.Tipologia=false;
		this.quantitaCarr=0;
		this.PEGI=0;
		this.Genere="";
		this.Disp_Ps5_Fisico=0;
		this.Disp_Ps5_Digitale=0;
		this.Disp_Ps4_Fisico=0;
		this.Disp_Ps4_Digitale=0;
		this.Disp_XboxX_Fisico=0;
		this.Disp_XboxX_Digitale=0;
		this.Disp_XboxS_Fisico=0;
		this.Disp_XboxS_Digitale=0;
		this.Disp_Pc_Fisico=0;
		this.Disp_Pc_Digitale=0;
	}

	public String getCodSeriale() 
	{
		return CodSeriale;
	}

	public void setCodSeriale(String codSeriale) 
	{
		this.CodSeriale = codSeriale;
	}

	public String getNome() 
	{
		return Nome;
	}

	public void setNome(String nome) 
	{
		this.Nome = nome;
	}
	
	public float getPrezzo() 
	{
		return Prezzo;
	}

	public void setPrezzo(float prezzo) 
	{
		this.Prezzo = prezzo;
	}

	public String getDataUscita() 
	{
		return DataUscita;
	}

	public void setDataUscita(String dataUscita) 
	{
		this.DataUscita = dataUscita;
	}

	public String getDescrizioneRidotta() 
	{
		return DescrizioneRidotta;
	}

	public void setDescrizioneRidotta(String descrizioneRidotta) 
	{
		this.DescrizioneRidotta = descrizioneRidotta;
	}

	public String getDescrizioneCompleta() 
	{
		return DescrizioneCompleta;
	}

	public void setDescrizioneCompleta(String descrizioneCompleta) 
	{
		this.DescrizioneCompleta = descrizioneCompleta;
	}	
	
	public String getImmagine() 
	{
		return Immagine;
	}

	public void setImmagine(String immagine) 
	{
		this.Immagine = immagine;
	}

	public boolean getTipologia() 
	{
		return Tipologia;
	}

	public void setTipologia(boolean tipologia) 
	{
		this.Tipologia = tipologia;
	}

	public int getQuantitaCarr() 
	{
		return quantitaCarr;
	}

	public void setQuantitaCarr(int quantitaCarr) 
	{
		this.quantitaCarr = quantitaCarr;
	}
	
	public void incrementQuantitaCarr()
	{
		this.quantitaCarr = this.quantitaCarr + 1;
	}
	
	public void decrementQuantitaCarr()
	{
		this.quantitaCarr = this.quantitaCarr - 1;
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
		return Genere;
	}

	public void setGenere(String genere) 
	{
		this.Genere = genere;
	}

	public int getDisp_Ps5_Fisico() 
	{
		return Disp_Ps5_Fisico;
	}

	public void setDisp_Ps5_Fisico(int disp_Ps5_Fisico) 
	{
		this.Disp_Ps5_Fisico = disp_Ps5_Fisico;
	}

	public int getDisp_Ps5_Digitale() 
	{
		return Disp_Ps5_Digitale;
	}

	public void setDisp_Ps5_Digitale(int disp_Ps5_Digitale) 
	{
		this.Disp_Ps5_Digitale = disp_Ps5_Digitale;
	}

	public int getDisp_Ps4_Fisico() 
	{
		return Disp_Ps4_Fisico;
	}

	public void setDisp_Ps4_Fisico(int disp_Ps4_Fisico) 
	{
		this.Disp_Ps4_Fisico = disp_Ps4_Fisico;
	}

	public int getDisp_Ps4_Digitale() 
	{
		return Disp_Ps4_Digitale;
	}

	public void setDisp_Ps4_Digitale(int disp_Ps4_Digitale) 
	{
		this.Disp_Ps4_Digitale = disp_Ps4_Digitale;
	}

	public int getDisp_XboxX_Fisico() 
	{
		return Disp_XboxX_Fisico;
	}

	public void setDisp_XboxX_Fisico(int disp_XboxX_Fisico) 
	{
		this.Disp_XboxX_Fisico = disp_XboxX_Fisico;
	}

	public int getDisp_XboxX_Digitale() 
	{
		return Disp_XboxX_Digitale;
	}

	public void setDisp_XboxX_Digitale(int disp_XboxX_Digitale) 
	{
		this.Disp_XboxX_Digitale = disp_XboxX_Digitale;
	}

	public int getDisp_XboxS_Fisico() 
	{
		return Disp_XboxS_Fisico;
	}

	public void setDisp_XboxS_Fisico(int disp_XboxS_Fisico) 
	{
		this.Disp_XboxS_Fisico = disp_XboxS_Fisico;
	}

	public int getDisp_XboxS_Digitale() 
	{
		return Disp_XboxS_Digitale;
	}

	public void setDisp_XboxS_Digitale(int disp_XboxS_Digitale) 
	{
		this.Disp_XboxS_Digitale = disp_XboxS_Digitale;
	}

	public int getDisp_Pc_Fisico() 
	{
		return Disp_Pc_Fisico;
	}

	public void setDisp_Pc_Fisico(int disp_Pc_Fisico) 
	{
		this.Disp_Pc_Fisico = disp_Pc_Fisico;
	}

	public int getDisp_Pc_Digitale()
	{
		return Disp_Pc_Digitale;
	}

	public void setDisp_Pc_Digitale(int disp_Pc_Digitale) 
	{
		this.Disp_Pc_Digitale = disp_Pc_Digitale;
	}

	

}
