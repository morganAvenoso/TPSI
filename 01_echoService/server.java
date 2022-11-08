package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	public static void main(String[] args) throws Exception {
		try {
			 
			int severPort=8698;
			String clientMessage = "";
			
			// Listen to port
			ServerSocket server = new ServerSocket(severPort);
			System.out.println("Server: in ascolto sula porta " + severPort );

			Socket serverClientSocket = server.accept();
			
			DataInputStream inStream = new DataInputStream(serverClientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(serverClientSocket.getOutputStream());
			
			
			
			while(!clientMessage.equals("end")) {
		      clientMessage = inStream.readUTF();
		      System.out.println("Server: ricevuto messaggio " + clientMessage );
		      System.out.println("Server: invio messaggio "    + clientMessage );
			  outStream.writeUTF("Echo from server : "         + clientMessage);
		      outStream.flush();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
