
import java.io.*;

import org.apache.poi.POITextExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

public class App {

	public static void main(String[] args) throws Exception {
		/**This is the document that you want to read using Java.**/
        //String fileName = "D:\\Projects\\NPA_DIPLOM\\WordParser\\word_docs\\test2.docx";
        String fileName = "C:\\programming\\MyNPA\\WordParser\\word_docs\\test2.docx";
        /**Method call to read the document (demonstrate some useage of POI)**/
        //readMyDocument(fileName);
        /*File inputFile = new File(fileName);
        POITextExtractor extractor = ExtractorFactory.createExtractor(inputFile); 
        System.out.println("Word Document Text: "); 
        System.out.println("===================="); 
        System.out.println(extractor.getText());     */

        /*XWPFDocument docx = new XWPFDocument(OPCPackage.openOrCreate(new File(fileName)));
        XWPFWordExtractor wx = new XWPFWordExtractor(docx);
        System.out.println(wx.getText()); */
        /*String inputFilename = fileName;

        String outputFilename = "C:\\b.docx";
        POIFSFileSystem fs = null;
        FileInputStream fis = new FileInputStream(inputFilename);
        fs = new POIFSFileSystem(fis);

        HWPFDocument doc = new HWPFDocument(fs);

        Range range = doc.getRange();
        range.replaceText("{name}", "piiiie");


        FileOutputStream fos = new FileOutputStream(outputFilename);
        doc.write(fos);

        fis.close();
        fos.close();               */
        open();
	}

    private static void open() {

        InputStream fs = null;
        try {
            fs = new FileInputStream("C:\\programming\\MyNPA\\WordParser\\word_docs\\test2.docx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < doc.getParagraphs().size() ; i++) {
            XWPFParagraph paragraph = doc.getParagraphs().get(i);
            //paragraph.getCTP().getRArray().
            System.out.println(paragraph.getParagraphText());
            for (int j = 0; j < paragraph.getCTP().getRArray().length; j++) {
                CTR run = paragraph.getCTP().getRArray()[j];

                for (int k = 0; k < run.getTArray().length; k++) {
                    CTText text = run.getTArray()[k];

                    // This will output the text contents
                    System.out.println(text.getStringValue());

                    // And this will set its contents
                    text.setStringValue("Success!");
                }
            }
        }

        try {
            doc.write(new FileOutputStream("C:\\output.docx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
