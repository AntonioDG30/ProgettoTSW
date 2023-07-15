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
				if(prod.getCodSeriale().contentEquals(product.getCodSeriale()) && prod.getPiattaforma().contentEquals(product.getPiattaforma())) 
				{
					prod.incrementQuantita();
					return;
				}
			}
			this.listaCarrello.add(product);
			product.incrementQuantita();

		
		
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
