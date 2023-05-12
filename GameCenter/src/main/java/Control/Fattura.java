package Control;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import Model.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.fdf.FDFPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;



@WebServlet("/Fattura")
public class Fattura extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Fattura() 
    {
        super();
    }
    
    float x=0, y=0, z=0;
    
    OrdineModel Omodel = new OrdineModel();
	ProductModel Pmodel = new ProductModel();
	UserModel Umodel = new UserModel();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String CodOrdineS = request.getParameter("CodOrdine");
		int CodOrdine = Integer.parseInt(CodOrdineS);
		String PDF = "";
		String servletPath = "";
		try 
		{
			PDF = Omodel.RicercaFattura(CodOrdine);
		
		
		
			if(!(PDF != null))
			{
				
				z++;
				String zs = Float.toString(z);
				servletPath = "C:/Users/anton/git/ProgettoTSW/GameCenter/src/main/webapp";
			    File file = new File(servletPath + "/TemplateFattura.pdf");
			    PDDocument fattura = PDDocument.load(file);    
			    PDPage page = (PDPage)fattura.getDocumentCatalog().getPages().get(0);											
			    PDPageContentStream contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
			    PDType1Font font = PDType1Font.TIMES_ROMAN;
			    
			    
			    
			    //Inserimento numero fattura
			    x = (float) 450;
			    y = (float) 768.55;
		 	    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(CodOrdineS); 
			    contentStream.endText();
	
			    
			    //inserimento data fattura
			    y = (float) 754;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(zs); 
			    contentStream.endText();
			    
			    
			    //inserimento Dati Utente
			    String Email=(String) request.getSession().getAttribute("Email");
			    UserBean Utente = Umodel.RicercaDatiSensibili(Email); 
			    
			    x = (float) 395;
			    y = (float) 710;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Utente.getNome() + " " + Utente.getCognome()); 
			    contentStream.endText();
			    
			    
			    y = y - (float) 14.5;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Utente.getVia() + ", " + Utente.getCivico()); 
			    contentStream.endText();
			    
			    y = y - (float) 14.5;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Utente.getCitta() + ", " + Utente.getProvincia() + ", " + Utente.getCAP()); 
			    contentStream.endText();
			    
			    
			    y = y - (float) 14.5;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(Email); 
			    contentStream.endText();
			    
			    //inserimento prodotti
			    y = (float) 604;
			    Collection<?> Ordini = (Collection<?>) Omodel.DettagliOrdine(CodOrdine);
			    if (Ordini != null && Ordini.size() != 0) 
				{
					Iterator<?> it = Ordini.iterator();
					while (it.hasNext()) 
					{
						x = (float) 83;
						ProductBean bean = (ProductBean) it.next();
						contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(Integer.toString(bean.getQuantita())); 
			    	    contentStream.endText();
			    	    
			    	    
			    	    x = 124;
			    	    contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(bean.getNome()); 
			    	    contentStream.endText();
			    	    
			    	    
			    	    x = 390;
			    	    contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(Float.toString(bean.getPrezzo())); 
			    	    contentStream.endText();
			    	    
			    	    
			    	    Float PrezzoTotRiga = (float) bean.getPrezzo() * bean.getQuantita();
			    	    x = 485;
			    	    contentStream.beginText();
						contentStream.setFont(font, 11);
						contentStream.newLineAtOffset(x, y);
						contentStream.showText(Float.toString(PrezzoTotRiga)); 
			    	    contentStream.endText();
			    	    y = y - (float) 16.42;
					}
					
				}
			    
			    /*y = (float) 603;
			    for(int j=0; j<20; j++)
			    {
			    	x = (float) 53;
			    	for(int i=0; i<4; i++)
				    {
			    		contentStream.beginText();
			    	    contentStream.setFont(font, 11);
			    	    contentStream.newLineAtOffset(x, y); 
			    	    contentStream.showText(zs); 
			    	    contentStream.endText();
			    	    x = x + 121;
				    }
			    	y = y - (float) 16.42;
			    }*/
			    
			    x = (float) 488;
			    y = (float) 62;
			    contentStream.beginText();
			    contentStream.setFont(font, 11);
			    contentStream.newLineAtOffset(x, y); 
			    contentStream.showText(zs); 
			    contentStream.endText();
			    
			    
			    
			    contentStream.close();
			    fattura.save(servletPath + "/Fatture/Fattura.pdf");
			    fattura.close();
			}
			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		/*String PDF_Path = servletPath + "/Fatture/" + PDF;
		response.sendRedirect(PDF_Path);
		System.out.println("qui2");*/
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}