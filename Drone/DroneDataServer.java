package Drone;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DroneDataServer extends Remote {
    void sendData(DroneData data) throws RemoteException;
    void registerDrone(String droneName) throws RemoteException;
    void registerConsumer(String consumerName) throws RemoteException;
    List<DroneData> getNewData(String consumerName) throws RemoteException;
}