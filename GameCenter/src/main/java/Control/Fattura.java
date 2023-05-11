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
import org.apache.commons.logging.LogFactory;



@WebServlet("/Fattura")
public class Fattura extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Fattura() 
    {
        super();
    }
    
    
    public void csprintln(PDPageContentStream cs, String string) throws IOException
    {
    	cs.showText(string);
    	cs.newLine();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setHeader("Content-disposition", "attachment; filename=Fattura.pdf");
		String servletPath = getServletContext().getRealPath("/");
	    System.out.println("Servlet path: " + servletPath + "TemplateFattura.pdf");
	    File file = new File(servletPath + "TemplateFattura.pdf");
	    PDDocument fattura = PDDocument.load(file);
	    System.out.println("Aperto il documento");
	    
	    
	    OrdineModel Omodel = new OrdineModel();
	    ProductModel Pmodel = new ProductModel();
	    
	    
	    
	    
	    
	    PDPage page = fattura.getPage(0); //carico la prima pagina del documento TemplateFattura12
											//aggiungi e non sostituire, non comprimere
	    PDPageContentStream contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, false);
	    
	    
	    
	    contentStream.beginText(); //inizio a scrivere
	    
	    contentStream.newLineAtOffset(220, 10); //prende le coordinate (x,y) del documento in cui iniziare a scrivere
	    csprintln(contentStream,"Antonio1 Di Giorgio");//NOME DESTINATARIO
	    contentStream.endText();
	    contentStream.close();
	    System.out.println("ooooo");  
	    fattura.save("Fatture1.pdf"); //salva il documeto in una fonte di output in questo caso response
	    fattura.close();
	    System.out.println("Chiuso il documento");   
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
