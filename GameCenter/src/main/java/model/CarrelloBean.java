package model;

import java.util.ArrayList;
import java.util.List;


public class CarrelloBean 
{
	private List<ProductBean> ListaCarrello;

	public CarrelloBean() 
	{
		this.ListaCarrello = new ArrayList<ProductBean>();
	}
	
	public void AggiungiProdotto(ProductBean product, String Piattaforma)
	{
			product.setPiattaforma(Piattaforma);
			for(ProductBean prod : ListaCarrello) 
			{
				if(prod.getCodSeriale().contentEquals(product.getCodSeriale()) && prod.getPiattaforma().contentEquals(product.getPiattaforma())) 
				{
					prod.incrementQuantita();
					return;
				}
			}
			this.ListaCarrello.add(product);
			product.incrementQuantita();

		
		
	}
	
	public void RimuoviProdotto(ProductBean product) 
	{
		for(ProductBean prod : ListaCarrello) 
		{
			if(prod.getCodSeriale().contentEquals(product.getCodSeriale()) ) 
			{
				if(prod.getQuantita()==1)
				{
					this.ListaCarrello.remove(prod);
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
		return ListaCarrello;
	}

	public void setListaCarrello(List<ProductBean> listaCarrello) 
	{
		this.ListaCarrello = listaCarrello;
	}
	
	
	


}
