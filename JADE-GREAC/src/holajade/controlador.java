package holajade;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class controlador extends Agent{
	Object [][]a= new Object[10][10];
	String Visual,Auditivo,LeerEscribir,Kinestesico,id_estudiante,id_reac,nombre_reac,estilo=null;
	String cadena="";
	ArrayList<ArrayList<String>> lista2 = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
	
	Connection con= null;
	int i=0,count=0;

	protected void setup() {
		

		for(int i =0; i<= 5; i++){
	            	lista.add(new ArrayList<String>());
	            	lista2.add(new ArrayList<String>());
			
		
	        }
		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				 con= conectar.conecta();
				String contador=null;
				// TODO Auto-generated method stub
				ACLMessage msg2=receive();
				
				if(msg2!=null) {
					
						
						try {
							lista2= (ArrayList<ArrayList<String>>) msg2.getContentObject();
						} catch (UnreadableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						contador=lista2.get(4).get(0);
						while(lista2.get(4).get(0).equals(contador)) {
							System.out.println("estoy en el while lista 2s y este es el cont que recibo  " +lista2.get(4).get(0));
							System.out.println("INICIO____ " +contador);
							lista.get(0).clear();
							lista.get(1).clear();
							lista.get(2).clear();
							lista.get(3).clear();
							lista.get(4).clear();
							 try {
						            //Ponemos a "Dormir" el programa durante los ms que queremos
						            Thread.sleep(15*1000);
						         } catch (InterruptedException e) {
						            System.out.println(e);
						         }
							 Statement s;
							try {
								s = con.createStatement();
								 ResultSet rsa = s.executeQuery ("select *from mdl_historial_vark ");
								 count=0;
								 while (rsa.next())
								 {
								      	
								 	if(rsa.getString(3).contentEquals("Visual")||rsa.getString(3).contentEquals("Auditivo")|| rsa.getString(3).contentEquals("LeerEscribir")||rsa.getString(3).contentEquals("Kinestesico")) {
								 		estilo=rsa.getString(3);
								 		id_estudiante=rsa.getString(2);
								 		lista.get(0).add(id_estudiante);			 		
								 		
										lista.get(1).add(estilo);

										 		
								 		 System.out.println("Usuario "+id_estudiante + " Tiene " +estilo);
								 			
								 		
								 	}i++; count++;//el count es aquel que cuenta el numero de registros
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							 contador=Integer.toString(count);
							 System.out.println("FIN____ " +contador);
							 System.out.println("estoy en el while");
							 
							
							 
						}System.out.println("SALI while");
						lista.get(4).add(contador);
						
						ACLMessage msg1= new ACLMessage(ACLMessage.INFORM);
	 					
	 					try {
							msg1.setContentObject(lista);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						msg1.addReceiver(new AID("agentrepositorio",AID.ISLOCALNAME));
						send(msg1);
					
						 try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}else block();
				
			}});

}
}