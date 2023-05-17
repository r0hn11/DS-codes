import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.Scanner;

public class Server extends UnicastRemoteObject implements ServerInterface{
	
	public static final long serialVersionUID = 1L;
	private static int numClients = 0;
	
	public Server() throws RemoteException{super();}
	public String sayHello() throws RemoteException{return "Hello";}
	public synchronized int getNumClients() throws RemoteException{return numClients;}
	public synchronized void addClient() throws RemoteException{numClients++;}
	public synchronized void removeClient() throws RemoteException{numClients--;}
	
	public String processMessage(String msg) throws RemoteException{
		Scanner sc = new Scanner(System.in);
		System.out.println("/client/:"+msg);
		System.out.print("Type a message: ");
		String reply = sc.nextLine();
		return reply;
	}
	
	public static void main(String args[]){
		try{
			Registry registry = LocateRegistry.createRegistry(1099);
			Server srvr = new Server();
			registry.rebind("Server", srvr);
			System.out.println("Server started.");
		}catch(Exception e){}
	}
}