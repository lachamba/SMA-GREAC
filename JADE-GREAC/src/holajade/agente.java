package holajade;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.*;

import java.util.ArrayList;
public class agente extends Agent  {
	// Agente inicia todo haciendo la consuta de la bd
	int x=0; String contador=null;
	//Array de listas donde se alamacenaran los datos obtenidos de la base de datos de moodle;
	ArrayList<ArrayList<String>> lista2 = new ArrayList<ArrayList<String>>(); 
	ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
	String estilo=null;
	String id_estudiante=null;
	Connection con= conectar.conecta();
	Object [][]a= new Object[10][10];
	 int i=0;
	 int count=0;

	@Override
	protected void setup() {
		
	// Se inicializa ls listas, creando los espacios que se van a utilizar, en este caso 5, 
	//debido a que se guardara el id del estudiante, el estilo, y un contador.
		for(int i =0; i<= 5; i++){
        			lista.add(new ArrayList<String>());
   			 }
		//lista.get(4).add("0");
		addBehaviour (new OneShotBehaviour() {
		
			@Override
			public void action() {
				// TODO Auto-generated method stub
				System.out.println("Hola, soy el agente Usuario, y  entre en accion");
				
				try {
					//Se llama a la conección de la base de datos, para realizar las consultas.
					 Statement s = con.createStatement();
					 //De la tabla en donde guarda el estilo de aprendizaje se realiza la obtencion del estilo VARK de los estudiantes.
					 ResultSet rs = s.executeQuery ("select *from mdl_historial_vark ");
					 while (rs.next())
					 {
					              	
					 	if(rs.getString(3).contentEquals("Visual")||rs.getString(3).contentEquals("Auditivo")|| rs.getString(3).contentEquals("LeerEscribir")||rs.getString(3).contentEquals("Kinestesico")) {
					 		
					 		estilo=rs.getString(3);
					 		id_estudiante=rs.getString(2);
					 		//se almacena en la lista los datos del estudiante, obteniendo de esta manera el estilo correspondiente
					 		//a cada estudiante.
					 		lista.get(0).add(id_estudiante);			
					 		lista.get(1).add(estilo);

							 		
					 		 System.out.println("Usuario "+id_estudiante + " Tiene " +estilo );
					 				
					 	}i++; count++;//el count es aquel que cuenta el numero de registros
					 }
					 
					 try {
				         //Ponemos a "Dormir" el programa durante los ms que queremos, para proceder a iniciar el sniffer
						 // y visualizar su comunicación.
				            Thread.sleep(30*1000);
				         } catch (InterruptedException e) {
				            System.out.println(e);
				         }
					 //Este contador, nos sirve para realizar la comparacion, entre usuarios en la 
					 //base de datos, y saber cuando ingresa un nuevo usuario, para que los agentes entren en acción.
					 contador=Integer.toString(count);
					// Se agrega el contador en la lista para ser enviado por mensaje.		 
					 
						lista.get(4).add(contador);
						//Se crea el mensaje para enviar al agente repositorio y activarlo, en la lista se envia la informacion
						//del estilo de aprendizaje del estudiante.
						 ACLMessage msg= new ACLMessage(ACLMessage.INFORM);
							
							msg.setContentObject(lista);
							msg.addReceiver(new AID("agentrepositorio",AID.ISLOCALNAME));
							send(msg);
					//Se cierra la conección de la base de datos.
					 con.close();  
		            
		        } catch (SQLException | IOException ex) {
		            Logger.getLogger(conectar.class.getName()).log(Level.SEVERE, null, ex);
				 
		        }
				
			}
		});
	}
}


