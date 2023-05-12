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
    



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OrdineModel Omodel = new OrdineModel();
		
		z++;
		String zs = Float.toString(z);
		String servletPath = "C:/Users/anton/git/ProgettoTSW/GameCenter/src/main/webapp";
	    File file = new File(servletPath + "/TemplateFattura.pdf");
	    PDDocument fattura = PDDocument.load(file);
	    
	    
	    
	    //ProductModel Pmodel = new ProductModel();
	    
	    
	    
	    
	    
	    PDPage page = (PDPage)fattura.getDocumentCatalog().getPages().get(0);											
	    PDPageContentStream contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
	    PDType1Font font = PDType1Font.TIMES_ROMAN;
	    
	    x = (float) 425;
	    y = (float) 768.55;
 	    contentStream.beginText();
	    contentStream.setFont(font, 11);
	    contentStream.newLineAtOffset(x, y); 
	    contentStream.showText(zs); 
	    contentStream.endText();

	    
	    y = (float) 754;
	    contentStream.beginText();
	    contentStream.setFont(font, 11);
	    contentStream.newLineAtOffset(x, y); 
	    contentStream.showText(zs); 
	    contentStream.endText();
	    
	    
	    x = (float) 395;
	    y = (float) 710;
	    contentStream.beginText();
	    contentStream.setFont(font, 11);
	    contentStream.newLineAtOffset(x, y); 
	    contentStream.showText(zs); 
	    contentStream.endText();
	    
	    
	    y = y - 10;
	    contentStream.beginText();
	    contentStream.setFont(font, 11);
	    contentStream.newLineAtOffset(x, y); 
	    contentStream.showText(zs); 
	    contentStream.endText();
	    
	    y = y - 10;
	    contentStream.beginText();
	    contentStream.setFont(font, 11);
	    contentStream.newLineAtOffset(x, y); 
	    contentStream.showText(zs); 
	    contentStream.endText();
	    
	    y = y - 10;
	    contentStream.beginText();
	    contentStream.setFont(font, 11);
	    contentStream.newLineAtOffset(x, y); 
	    contentStream.showText(zs); 
	    contentStream.endText();
	    
	    y = y - 10;
	    contentStream.beginText();
	    contentStream.setFont(font, 11);
	    contentStream.newLineAtOffset(x, y); 
	    contentStream.showText(zs); 
	    contentStream.endText();
	    
	    
	    
	    y = (float) 590;
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
	    	    x = x + 124;
		    }
	    	y = y - 17;
	    }
	    //System.out.println("x: " + x + ", y: " + y);
	    
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}