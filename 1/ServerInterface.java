import java.rmi.*;

public interface ServerInterface extends Remote{
	public String sayHello() throws RemoteException;
	public int getNumClients() throws RemoteException;
	public void addClient() throws RemoteException;
	public void removeClient() throws RemoteException;
	public String processMessage(String msg) throws RemoteException;
}