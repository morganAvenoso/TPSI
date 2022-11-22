package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedTcpServer {
	
	Reservation reservations;
	
	public static void main(String[] args) throws Exception {
	   
		int count = 0;       // conta il numero di client
		
		int severPort=8698;
		int Size=10;  // numero di posti
		Reservation reservations= new Reservation(Size);
		
		// Listen to port
		ServerSocket socket = new ServerSocket(8698);
		System.out.println("Server: in ascolto sula porta " + severPort);
			
		while (true) {	//loop
			
			// Start accepting requests and wait until client connects
			Socket serverClientSocket = socket.accept();  // bloccante
			
			count++;
			
			System.out.println("Server: Serving Client " + count);
			// Handle the client communication
			WorkedThread wt = new WorkedThread( "Thread " + count, serverClientSocket, count , reservations);
			//sa.setName("Thread-Numero-" +count);  
			wt.start();  // non Ã¨ bloccante
		}
	}
}
