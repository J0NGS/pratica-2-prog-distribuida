import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DroneDataServerImpl extends UnicastRemoteObject implements DroneDataServer {
    private List<String> dataStore;

    protected DroneDataServerImpl() throws RemoteException {
        dataStore = new ArrayList<>();
    }

    @Override
    public synchronized void sendData(String data) throws RemoteException {
        dataStore.add(data);
        System.out.println("Data received: " + data);
    }
}