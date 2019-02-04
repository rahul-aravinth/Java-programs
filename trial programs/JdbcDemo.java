import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

class JdbcDemo{
public static void main(String args[]){
try{
//step1 load the driver class
Class.forName("com.mysql.jdbc.Driver");

//step2 create  the connection object
Connection con=DriverManager.getConnection("jdbc:mysql://localhost/test","root","");

CallableStatement cs = con.prepareCall("{call run()}");
//step4 execute query
cs.execute();
System.out.println(cs.getString());
//step5 close the connection object
con.close();

}catch(Exception e){ System.out.println(e);}

}
}
