package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WorkedThread extends Thread{
	
	Socket sock;
	int clientNo;
	Reservation reservations;

	WorkedThread( String threadName , Socket inSocket, int ClientNo ,  Reservation r) {
		super(threadName);
		sock = inSocket;
		clientNo = ClientNo;
		reservations=r;
	}

	public boolean isNumeric( String stringa_numerica) {
	  boolean result=true;
	  
	  try {
	        int num = Integer.parseInt(stringa_numerica);
	      } catch (NumberFormatException e) {
	        result = false;
	      }
	  return result;
	}
	
	public String joinArray( String a[] , int from , int to ) {
		  String s="";
		  for (int i=from ; i<=to ; i++) {
			  s=s+ " " + a[i];
		  }
		  return s;
		}
	
	public void run() {
		try {

			InetSocketAddress clientAddr = (InetSocketAddress) sock.getRemoteSocketAddress();
	        System.out.println("ServerTread: " + this.getName() + " New connection from port=" + clientAddr.getPort() + " host=" + clientAddr.getHostName());
	        
			// Streams to read and write the data to socket streams
			DataInputStream inStream   = new DataInputStream(sock.getInputStream());
			DataOutputStream outStream = new DataOutputStream(sock.getOutputStream());

			String cMsg = "";
			String sMsg = "";

			while (!cMsg.equals("End")) {

				cMsg = inStream.readUTF();  // bloccante
				
				// elaboro il comando del client
				cMsg.trim();
				String cMsgArr[]=cMsg.split("\\s+");
				System.out.println("ServerTread: " + this.getName() + " Ricevuto messaggio " + cMsg );
				int n;
				switch (cMsgArr[0]) {
					                         
				  case "Info" :  
					  sMsg=reservations.getReservations();
                      break;
                            
				  case "Prenotazione":
					  System.out.println("ServerTread: " + this.getName() +" (prenotazione) numero="+  cMsgArr[1] + " nome=" +  cMsgArr[2] );
					  if (isNumeric(cMsgArr[1]) == true) {
						  int num=Integer.parseInt(cMsgArr[1]);
						  String name=joinArray(cMsgArr , 2 , cMsgArr.length-1 );
						  sMsg=reservations.setReservation(num,name);
					  } else {
					 	 sMsg="Syntax error";
					  }
					  break;
		            
				  case "Help" : 
					  sMsg="Comandi disponibili\n Help - mostra i comandi\n Info - mostra i posti e le prenotazioni\n Prenotazione <numero> <nome>\n End - uscire";
				      break;
				                 
				  case "End" :  
					  sMsg="Exit";
	                  break;
		                		
				  default: 
					  sMsg="Command " + cMsg + " not found";
		              break;
				}
				
				System.out.println("ServerTread: " + this.getName() + " invio risposta " + sMsg );
				outStream.writeUTF(sMsg);
				outStream.flush();
			}
			
			sMsg="Bye";
			System.out.println("Server.Thread " + clientNo + " Invio messaggio " + cMsg );
			outStream.writeUTF(sMsg);
			outStream.flush();
			inStream.close();
			outStream.close();
			sock.close();

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			System.out.println("Client -" + clientNo + " exit!! ");
		}
	}
}
