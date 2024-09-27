package open.drone;
import java.rmi.Naming;
import java.util.Scanner;

import open.broker.DroneDataServer;

public class DroneLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DroneDataServer server = null;

        while (server == null) {
            try {
                System.out.print("Digite o endereço do servidor RMI (ex: rmi://localhost/DroneDataServer): ");
                String serverAddress = scanner.nextLine();
                server = (DroneDataServer) Naming.lookup(serverAddress);
            } catch (Exception e) {
                System.err.println("Erro ao conectar ao servidor: " + e.getMessage());
            }
        }

        System.out.print("Digite o nome do drone: ");
        String droneName = scanner.nextLine();

        try {
            server.registerDrone(droneName);
            Drone drone = new Drone(server, droneName);
            Thread droneThread = new Thread(drone);
            droneThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}