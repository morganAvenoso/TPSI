
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		
		int severPort=8765;
		String clientMsg = "";
		int len=0, voc=0, cons=0;
		
		try {			 
			// Creazione del socket sul server e ascolto sulla porta
			ServerSocket serverSocket = new ServerSocket(severPort);
			System.out.println("Server: in ascolto sulla porta " + severPort);

			// Attesa della connessione con il client
			Socket clientSocket = serverSocket.accept();
			
			// Create input and output streams to read/write data
			DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	
			cons=0;
			voc=0;
			// Scambio di dati tra client e server
			do{   
				//Lettura dato da stream di rete
				clientMsg = inStream.readUTF();
				System.out.println("Server: ricevuto messaggio " + clientMsg );
				
				//VEDI QUANTE LUNGO LI MESSAGGIO .lenght
				len = clientMsg.length();
				clientMsg.toLowerCase();
					for(int i=0; i<len; i++) {
						if(clientMsg.charAt(i)=='a' || clientMsg.charAt(i)=='e' || clientMsg.charAt(i)=='i' || clientMsg.charAt(i)=='o' || clientMsg.charAt(i)=='u')
							voc++;
						
						else
							cons++;
						
						
					}
			     	//Invio dati su stream di rete
				    //outStream.writeUTF("Echo from server : "         + clientMsg);
					
				    outStream.writeUTF("Il messaggio è lungo " + len + " e le vocali sono " + voc);
				    System.out.println("Server: invio messaggio "    + clientMsg );
				    System.out.println("Il messaggio è lungo " + len + " e le vocali sono " + voc);
				    clientMsg.equals("quit");
				    outStream.flush();
			}while(cons!=voc/2);
			clientMsg="quit";
				
				
			// Close resources
			serverSocket.close();
			clientSocket.close();
			inStream.close();
			outStream.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
