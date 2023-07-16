package model;

import java.util.ArrayList;
import java.util.List;


public class CarrelloBean 
{
	private List<ProductBean> listaCarrello;

	public CarrelloBean() 
	{
		this.listaCarrello = new ArrayList<>();
	}
	
	public void aggiungiProdotto(ProductBean product, String piattaforma)
	{
			product.setPiattaforma(piattaforma);
			for(ProductBean prod : listaCarrello) 
			{
				if(prod.getCodSeriale().contentEquals(product.getCodSeriale()) 
						&& prod.getPiattaforma().contentEquals(product.getPiattaforma())) 
				{
					if(prod.quantita<verificaQuantita(prod))
					{
						System.out.println(" piat " + prod.getPiattaforma());
						prod.incrementQuantita();
						return;
					}
					else
					{
						 return;
					}	
				}		
			}
			this.listaCarrello.add(product);
			product.incrementQuantita();	
	}
	public int verificaQuantita(ProductBean product) 
	{
		if (product.getPiattaforma().contentEquals("PS5 Digitale"))
   		{
			return	product.getDispPs5Digitale();
   		}
   		if (product.getPiattaforma().contentEquals("PS5 Fisico"))
   		{
   			return	product.getDispPs5Fisico();
   		}
   		if (product.getPiattaforma().contentEquals("PS4 Digitale"))
   		{
   			return	product.getDispPs4Digitale();
   		}
   		if (product.getPiattaforma().contentEquals("PS4 Fisico"))
   		{
   			return	product.getDispPs4Fisico();
   		}
   		if (product.getPiattaforma().contentEquals("XboxX Digitale"))
   		{
   			return	product.getDispXboxXDigitale();
   		}
   		if (product.getPiattaforma().contentEquals("XboxX Fisico"))
   		{
   			return	product.getDispXboxXFisico();
   		}
   		if (product.getPiattaforma().contentEquals("XboxS Digitale"))
   		{
   			return	product.getDispXboxSDigitale();
   		}
   		if (product.getPiattaforma().contentEquals("Pc Digitale"))
   		{
   			return	product.getDispPcDigitale();
   		}
   		if (product.getPiattaforma().contentEquals("Pc Fisico"))
   		{
   			return	product.getDispPcFisico();
   		}
		return 100000;
	}
	
	public void rimuoviProdotto(ProductBean product) 
	{
		for(ProductBean prod : listaCarrello) 
		{
			if(prod.getCodSeriale().contentEquals(product.getCodSeriale()) ) 
			{
				if(prod.getQuantita()==1)
				{
					this.listaCarrello.remove(prod);
				}
				else
				{
					prod.decrementQuantita();
				}
				break;
			}
		}
 	}

	public List<ProductBean> getListaCarrello() 
	{
		return listaCarrello;
	}

	public void setListaCarrello(List<ProductBean> listaCarrello) 
	{
		this.listaCarrello = listaCarrello;
	}

	@Override
	public String toString() 
	{
		return "CarrelloBean [listaCarrello=" + listaCarrello + "]";
	}
}
