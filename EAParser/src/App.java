import com.epam.java.eaparser.parser.EAParser;
import com.epam.java.eaparser.parser.Model;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: nikolay
 * Date: 02.11.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class App {
    public static void main(String[]args){
        connectToAPI();
    }

private static void connectToAPI(){
    File  f = new File("C:\\test.EAP");
    Model model = new EAParser().parse(f.getPath());
}



}
