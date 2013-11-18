import com.epam.java.ea.parser.EAParser;
import com.epam.java.ea.parser.Model;
import org.sparx.Diagram;
import org.sparx.Repository;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
