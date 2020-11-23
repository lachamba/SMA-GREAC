package holajade;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.sql.Statement;
public class interfaz extends Agent{
	//Agente interfaz  llena la base de datos  para que sea leida por moodle
	ArrayList<ArrayList<String>> Adry = new ArrayList<ArrayList<String>>();
	protected void setup() {
		for(int i =0; i<= 5; i++){
        	
	Adry.add(new ArrayList<String>());
    }
		addBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				// TODO Auto-generated method stub 
				int cuentas=0;
				ACLMessage msg=receive();
				
				if(msg!=null){
					 try {
						
						Adry=(ArrayList<ArrayList<String>>) msg.getContentObject();
						for (int i = 0; i <= Adry.get(0).size() - 1; i++) {
							
 							System.out.println("id " + Adry.get(0).get(i) +  " debe usar un recurso: "+ Adry.get(1).get(i));
					}
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 Connection con= conectar.conecta();
					 try {
						
						 
						 Statement s= con.createStatement();
					
						 String rsa="DELETE FROM mdl_reac";
						// String rse="SELECT *FROM  mdl reac";
						 PreparedStatement stma=con.prepareStatement(rsa);
						 //PreparedStatement stmae=con.prepareStatement(rsa);
						 
						 stma.execute();
						 
						 
						 /* ResultSet ac=s.executeQuery("select *from mdl_reac");
						 
						 int numero=0;
						 if(ac.next()) {
							 		
					 		 while (ac.next())
							 {
					 			 numero++;
					 			 System.out.println("While base de datos__ " + numero);
							   }	
							 
							 
						 }*/
						 
						 String rs="INSERT INTO mdl_reac (id_reac,id_user,id_module) values (?, ?, ?)";
						   PreparedStatement stm=con.prepareStatement(rs);
						   
						   for (int i = 0; i <= Adry.get(0).size() - 1; i++) {
							   stm.setLong(1, i);
							    //rs.setFetchSize(1, i);   
						           // rs.setINT    (2, Integer.parseInt(Adry.get(0).get(i)));
						            //rs.setINT    (3, Integer.parseInt(Adry.get(1).get(i)));	
							   stm.setLong(2, Integer.parseInt(Adry.get(0).get(i)));
							   //stm.setLong(3, Integer.parseInt(Adry.get(1).get(i)));
							   stm.setString(3, Adry.get(1).get(i));
						     	
							   stm.executeUpdate();
						   }
							 
						 
						 ACLMessage msg2= new ACLMessage(ACLMessage.INFORM);
						  System.out.println("ADRY REpositorio______ conteiene NUMERO "+ Adry.get(4).get(0));
		 					msg2.setContentObject(Adry);
							msg2.addReceiver(new AID("agentcontrol",AID.ISLOCALNAME));
							send(msg2);
						
						 
							System.out.println("Llamada agregada con éxito a la base de datos.");	 
						 
						   s.close();
						   con.close();
						 
						} catch (SQLException | IOException  e) {
						            System.out.println("Error!, la llamada no pudo ser agregada a la base de datos.");
						}
				
				
						
						 
				}	
			}
			
		});
	}

}
