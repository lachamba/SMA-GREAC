package holajade;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jade.*;
import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class repositorio extends Agent{
	//Agente Repositorio recibe EA y busca en la base de datos los recursos y actividades
	//pertenecientes al estilo VARK obtenido.
	Object [][]a= new Object[10][10];
	String Visual,Auditivo,LeerEscribir,Kinestesico,id_estudiante,id_reac,nombre_reac,estilo=null;
	String cadena="";
	//lista: se la utilizara para obtener los datos enviados desde el agente usuario
	ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
	//reacmod: recursos y actividades de moodle
	ArrayList<ArrayList<String>> reacmod = new ArrayList<ArrayList<String>>();
	//estudiante, recursos y actividades clasificados segun estilo de aprendizaje
	ArrayList<ArrayList<String>> esreac = new ArrayList<ArrayList<String>>();
	
	
	protected void setup() {
		
		// Se inicializa ls listas, creando los espacios que se van a utilizar, en este caso 5, 
		//debido a que se guardara el id del estudiante, el estilo, y un contador.
		for(int i =0; i<= 5; i++){
	        lista.add(new ArrayList<String>());
			reacmod.add(new ArrayList<String>());
			esreac.add(new ArrayList<String>());
	        }
		//comportamiento creado, para recibir mensajes
		addBehaviour(new CyclicBehaviour() {
			
			
			@Override
			public void action() {
				System.out.println("Hola, soy el agente Repositorio, y  entre en accion");
				//Se realiza la conección a la base de datos
				Connection con= conectar.conecta();
				//Se recibe el mensaje enviado desde el agente usuario.
				ACLMessage msg=receive();
				//Comprueba si existen datos dentro del mensaje
				if(msg!=null){				
					try {
						//Se realiza la consulta a la base de datos de moodle
						//para obtener los recursos y actividades y proceder a compararlos
						Statement s = con.createStatement();	  
						//Se alamacena la información recibida, aquella que fue enviada por el agente usuario 
						ResultSet rs = s.executeQuery ("select *from mdl_modules ");
						 
						 lista=(ArrayList<ArrayList<String>>) msg.getContentObject();
						 //Se imprime la información obtenida
						 
						 System.out.println("Esta información me ha enviado el agente Usuario: ");
						for (int i = 0; i <= lista.get(0).size() - 1; i++) {
            						System.out.println("id " + lista.get(0).get(i) + " tiene "+ lista.get(1).get(i));
        					}
						//Se Vacia la lista, para que no exista redundancia de informacion, al momento que se 
						//agreguen de manera interactiva los usuarios.
						 reacmod.get(0).clear();
						 reacmod.get(1).clear();
						 reacmod.get(2).clear();
						 // en el siguiente ciclo se va almacenando en la lista reacmod los datos de moodle el id el nombre y el estilo VARK
						 //al que pertenecen
						 while (rs.next())
						 {				
						 		id_reac=rs.getString(1);
						 		nombre_reac=rs.getString(2);
						 		estilo=rs.getString(7);
						 		reacmod.get(0).add(id_reac);						 						 		
								reacmod.get(1).add(nombre_reac);
								reacmod.get(2).add(estilo);
						 }
						//Se Vacia la lista, para que no exista redundancia de informacion, al momento que se 
							//agreguen de manera interactiva los usuarios.
						 esreac.get(0).clear();
						 esreac.get(1).clear();
						 esreac.get(2).clear();
						 
						//En este ciclo se asigna el envia la informacion del recurso o actividad que debe usar cada estudiante
						 // tomando en cuenta el estilo VARK
						 for (int i = 0; i <= reacmod.get(0).size() - 1; i++) {
								
	     							System.out.println("id " + reacmod.get(0).get(i) +  " name "+ reacmod.get(1).get(i)+ " tiene "+ reacmod.get(2).get(i));
								cadena=reacmod.get(2).get(i);
								if (cadena!=null)
								for (int num = 0; num < cadena.length(); num++) {
	                						if(cadena.charAt(num)=='V'){
										for (int j = 0; j <= lista.get(0).size() - 1; j++){
											if(lista.get(1).get(j).equals("Visual")){
												esreac.get(0).add(lista.get(0).get(j));						 						 		
												esreac.get(1).add(reacmod.get(1).get(i));
											}
										}
									}else if(cadena.charAt(num)=='A'){
										for (int j = 0; j <= lista.get(0).size() - 1; j++){
											if(lista.get(1).get(j).equals("Auditivo")){
												esreac.get(0).add(lista.get(0).get(j));						 						 		
												esreac.get(1).add(reacmod.get(1).get(i));
											}
										}
									}else if(cadena.charAt(num)=='R'){
										for (int j = 0; j <= lista.get(0).size() - 1; j++){
											if(lista.get(1).get(j).equals("LeerEscribir")){
												esreac.get(0).add(lista.get(0).get(j));						 						 		
												esreac.get(1).add(reacmod.get(1).get(i));
											}
										}
									}else if(cadena.charAt(num)=='K'){
										for (int j = 0; j <= lista.get(0).size() - 1; j++){
											if(lista.get(1).get(j).equals("Kinestesico")){
												esreac.get(0).add(lista.get(0).get(j));						 						 		
												esreac.get(1).add(reacmod.get(1).get(i));
											}
										}
									}
	                
	            						}
							}
						 //en esta posicion se recibe el contador enviado desde Usuario
						 //Para realizar la comparacion cuando se agreguen nuevos estilos
					esreac.get(4).clear();					   
 					esreac.get(4).add(lista.get(4).get(0));
 					
 					//Se crea un nuevo mensaje para comunicarse con el agente interfaz
 				 ACLMessage msg1= new ACLMessage(ACLMessage.INFORM);
					//Se agrega la lista	 
 					
 					msg1.setContentObject(esreac);
					msg1.addReceiver(new AID("agentinterfaz",AID.ISLOCALNAME));
					send(msg1);
				
					 con.close();} catch (HeadlessException | UnreadableException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			}else block();
				
			}
		});
		
	}
	 
}
