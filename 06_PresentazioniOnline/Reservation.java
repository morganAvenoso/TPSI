package network;

public class Reservation {
   
    private String[] reservations;

    //Costruttore
    Reservation(int Size ) {
    	reservations=new String[Size];
    	for (int i=0 ; i<Size ; i++ ) {
    		reservations[i]="Posto libero";
    	}
    }
         
    public synchronized String getReservations() {
    	String r="";
    	for (int i=0 ; i<reservations.length ; i++ ) {
    		r= r + "\n" +  i + " " + reservations[i];
    	}
        return r;
    }
    
    public synchronized String setReservation( int posto, String nome ) {
    	String result = "Prenotazione per " + nome + " rifiutata!";
    	if ( reservations[posto].equals("Posto libero")) {
    		 reservations[posto]=nome;
    		 result="Prenotazione per " + nome + " accettata!";
    	} 
        return result;
    }
}
