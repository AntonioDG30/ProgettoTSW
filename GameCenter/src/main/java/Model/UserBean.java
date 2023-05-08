package Model;

import java.io.Serializable;

public class UserBean implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	String Email;
	String Password;
	int PuntiFedelta;
	boolean Tipo;

	public UserBean() 
	{
		this.Email="";
		this.Password="";
		this.PuntiFedelta=0;
		this.Tipo=false;
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

	@Override
	public String toString() {
		return "UserBean [Email=" + Email + ", Password=" + Password + ", PuntiFedelta=" + PuntiFedelta + ", Tipo="
				+ Tipo + "]";
	}
	
}
