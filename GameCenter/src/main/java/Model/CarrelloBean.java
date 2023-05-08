package Model;

import java.util.ArrayList;
import java.util.List;


public class CarrelloBean 
{
	private List<ProductBean> ListaCarrello;

	public CarrelloBean() 
	{
		this.ListaCarrello = new ArrayList<ProductBean>();
	}
	
	public void AggiungiProdotto(ProductBean product)
	{

			for(ProductBean prod : ListaCarrello) 
			{
				if(prod.getCodSeriale().contentEquals(product.getCodSeriale()) ) 
				{
					prod.incrementQuantitaCarr();
					return;
				}
			}
			this.ListaCarrello.add(product);
			product.incrementQuantitaCarr();

		
		
	}
	
	public void RimuoviProdotto(ProductBean product) 
	{
		for(ProductBean prod : ListaCarrello) 
		{
			if(prod.getCodSeriale().contentEquals(product.getCodSeriale()) ) 
			{
				if(prod.getQuantitaCarr()==1)
				{
					this.ListaCarrello.remove(prod);
				}
				else
				{
					prod.decrementQuantitaCarr();
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
