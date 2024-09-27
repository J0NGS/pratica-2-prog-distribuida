import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import Drone.DroneDataServerImpl;

public class DroneDataServerMain {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            DroneDataServerImpl server = new DroneDataServerImpl();
            Naming.rebind("rmi://localhost/DroneDataServer", server);
            System.out.println("Drone Data Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}