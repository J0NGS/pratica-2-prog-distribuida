import java.rmi.Naming;
import java.util.Scanner;

import Drone.DroneDataServer;

public class ConsumerLauncher {
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

        System.out.print("Digite o nome do consumidor: ");
        String consumerName = scanner.nextLine();

        try {
            Consumer consumer = new Consumer(server, consumerName);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}