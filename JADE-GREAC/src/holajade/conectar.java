package holajade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conectar {

	 static Connection con=null;
	   
	    static String url="jdbc:mysql://localhost/moodle?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	    static String pass="";
	    static String user="root";
	    //Clase para realizar la conección a la base de datos
	    public static Connection conecta(){
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            
	            try {
	            	//Realiza la conección a la base de datos, tomando los parámetros establecidos.
	                con= DriverManager.getConnection(url,user,pass);
	                //Mensaje para visualizar la conección de base de datos de moodle.
	                System.out.println("Conectado a Base de datos Moodle"); 
	               
	                
	            } catch (SQLException ex) {
	                Logger.getLogger(conectar.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        } catch (ClassNotFoundException ex) {
	            Logger.getLogger(conectar.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    return con;
	    }
}
