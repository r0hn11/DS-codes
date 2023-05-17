import java.util.Scanner;
import java.rmi.*;

public class Client{
	public static void main(String[] args){
		try{
			Scanner sc = new Scanner(System.in);
			ServerInterface srvr = (ServerInterface) Naming.lookup("rmi://localhost/Server");


			
			int clientId = (Integer) srvr.getNumClients();
			srvr.addClient();
			System.out.println("Client '"+clientId+"' added.");
			
			while(true){
				System.out.print("Type a message: ");
				String msg = sc.nextLine();
				String response = srvr.processMessage(msg);
				System.out.println("/server/: "+response);
			}
			
		}catch(Exception e){e.printStackTrace();}
	}
}