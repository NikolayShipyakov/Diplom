
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.POITextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
import org.apache.poi.hwpf.usermodel.HeaderStories;

public class App {

	public static void main(String[] args) throws Exception {
		/**This is the document that you want to read using Java.**/
        String fileName = "D:\\Projects\\NPA_DIPLOM\\WordParser\\word_docs\\test2.docx";
 
        /**Method call to read the document (demonstrate some useage of POI)**/
        //readMyDocument(fileName);
        /*File inputFile = new File(fileName);
        POITextExtractor extractor = ExtractorFactory.createExtractor(inputFile); 
        System.out.println("Word Document Text: "); 
        System.out.println("===================="); 
        System.out.println(extractor.getText());     */

        XWPFDocument docx = new XWPFDocument(OPCPackage.openOrCreate(new File(fileName)));
        XWPFWordExtractor wx = new XWPFWordExtractor(docx);
        System.out.println(wx.getText());
	}
	
	public static void readMyDocument(String fileName){
        POIFSFileSystem fs = null;
        try {
            //fs = new POIFSFileSystem(new FileInputStream(fileName));
            XWPFDocument doc = new XWPFDocument(new FileInputStream(fileName));
 
            /** Read the content **/
            //readParagraphs(doc);
 
            int pageNumber=1;
 
            /** We will try reading the header for page 1**/
            //readHeader(doc, pageNumber);
 
            /** Let's try reading the footer for page 1**/
            //readFooter(doc, pageNumber);
 
            /** Read the document summary**/
            //readDocumentSummary(doc);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
 
    public static void readParagraphs(HWPFDocument doc) throws Exception{
        WordExtractor we = new WordExtractor(doc);
 
        /**Get the total number of paragraphs**/
        String[] paragraphs = we.getParagraphText();
        System.out.println("Total Paragraphs: "+paragraphs.length);
 
        for (int i = 0; i < paragraphs.length; i++) {
 
            System.out.println("Length of paragraph "+(i +1)+": "+ paragraphs[i].length());
            System.out.println(paragraphs[i].toString());
 
        }
 
    }
 
    public static void readHeader(HWPFDocument doc, int pageNumber){
        HeaderStories headerStore = new HeaderStories( doc);
        String header = headerStore.getHeader(pageNumber);
        System.out.println("Header Is: "+header);
 
    }
 
    public static void readFooter(HWPFDocument doc, int pageNumber){
        HeaderStories headerStore = new HeaderStories( doc);
        String footer = headerStore.getFooter(pageNumber);
        System.out.println("Footer Is: "+footer);
 
    }
 
    public static void readDocumentSummary(HWPFDocument doc) {
        DocumentSummaryInformation summaryInfo=doc.getDocumentSummaryInformation();
        String category = summaryInfo.getCategory();
        String company = summaryInfo.getCompany();
        int lineCount=summaryInfo.getLineCount();
        int sectionCount=summaryInfo.getSectionCount();
        int slideCount=summaryInfo.getSlideCount();
 
        System.out.println("---------------------------");
        System.out.println("Category: "+category);
        System.out.println("Company: "+company);
        System.out.println("Line Count: "+lineCount);
        System.out.println("Section Count: "+sectionCount);
        System.out.println("Slide Count: "+slideCount);
 
    }

}
