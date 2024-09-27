// DroneLauncher.java

import java.rmi.Naming;

public class DroneLauncher {
    public static void main(String[] args) {
        try {
            DroneDataServer server = (DroneDataServer) Naming.lookup("rmi://localhost/DroneDataServer");
            for (int i = 0; i < 5; i++) { // Inicia 5 drones
                Drone drone = new Drone(server);
                Thread droneThread = new Thread(drone);
                droneThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
