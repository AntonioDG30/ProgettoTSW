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
    
    



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String servletPath = "C:/Users/anton/git/ProgettoTSW/GameCenter/src/main/webapp";
	    File file = new File(servletPath + "/TemplateFattura.pdf");
	    PDDocument fattura = PDDocument.load(file);
	    
	    
	    //OrdineModel Omodel = new OrdineModel();
	    //ProductModel Pmodel = new ProductModel();
	    
	    
	    
	    
	    
	    PDPage page = (PDPage)fattura.getDocumentCatalog().getPages().get(0);											
	    PDPageContentStream contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
	    PDType1Font font = PDType1Font.TIMES_ROMAN;
	    
	    contentStream.beginText();
	    contentStream.setFont(font, 9);
	    
	    contentStream.newLineAtOffset(265, 195); 
	    contentStream.showText("Antonio"); 
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