import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DroneDataServer extends Remote {
    void sendData(String data) throws RemoteException;
}