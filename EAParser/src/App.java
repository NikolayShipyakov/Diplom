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
        //connectToAccess();
        connectToAPI();
    }

    public static void connectToAccess(){
        try{
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

        String accessFileName = "C:\\programming\\Diplom\\EAParser\\test\\test.accdb";

        String connURL="jdbc:odbc:Driver={Microsoft Access Driver (*.accdb)};DBQ="+accessFileName;

        Connection con = DriverManager.getConnection(connURL, "", "");

        Statement stmt = con.createStatement();

        stmt.execute("select * from student"); // execute query in table student

        ResultSet rs = stmt.getResultSet(); // get any Result that came from our query

        if (rs != null)
            while ( rs.next() ){

                System.out.println("Name: " + rs.getString("Name") + " ID: "+rs.getString("ID"));
            }

        stmt.close();
        con.close();
    }
    catch (Exception err) {
        System.out.println("ERROR: " + err);
    }
}

private static void connectToAPI(){
    System.out.println("begin");
    Repository r = new Repository();
    File  f = new File("C:\\test.EAP");
    boolean b = r.OpenFile(f.getAbsolutePath());
    System.out.println("end " + r);
}

}
