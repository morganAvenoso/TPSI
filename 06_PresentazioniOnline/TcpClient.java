package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
	
	static Scanner scanner;
	
	public static void main(String[] args) throws Exception {
		try {
			String severAddress="127.0.0.1";  // localhost
			int severPort=8698;
						
			String clientMessage = "";
			String serverMessage = "";	
			
			// Create connection to server socket
			System.out.print("Client: Tentativo di connessione server=" + severAddress + ":" + severPort + " ... ");
			Socket socket = new Socket(severAddress, severPort); //
			System.out.println("Connected");
			
			printMenu();
			
			// Create streams to read/write data
			DataInputStream inStream   = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

			scanner = new Scanner(System.in);
			
			while (!clientMessage.equals("end")) {

				// Prompt user to enter some number or 'end'
				clientMessage = prompt();
			    System.out.println("Client: invio il messaggio: " + clientMessage);
				outStream.writeUTF(clientMessage);    // bloccanre
				outStream.flush();
				// Read data from socket input stream
				serverMessage = inStream.readUTF();      // bloccante  
				System.out.println("Client: ricevuto il messaggio: " + serverMessage);
		        
			}
			System.out.println("Client: disconnecting");
			// Close resources
			outStream.close();
			outStream.close();
			socket.close();
			scanner.close();
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Exit");
		}
	}
	
	public static void printMenu() {
		System.out.println("Comandi disponibili");
		System.out.println("  Help - mostra i comandi");
		System.out.println("  Info - mostra i posti e le prenotazioni");
		System.out.println("  Prenotazione <numero> <nome>");
		System.out.println("  End - uscire");

	}
	
	public static String prompt() {
		scanner = new Scanner(System.in);
		System.out.print("inserisci comando> ");
		String cmd = scanner.nextLine();
		return cmd;
	}
}
