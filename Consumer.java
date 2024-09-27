import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

import Drone.DroneData;
import Drone.DroneDataServer;

public class Consumer implements Runnable {
    private DroneDataServer server;
    private String consumerName;

    public Consumer(DroneDataServer server, String consumerName) {
        this.server = server;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(consumerName);
        try {
            server.registerConsumer(consumerName);
            while (true) {
                List<DroneData> newData = server.getNewData(consumerName);
                for (DroneData data : newData) {
                    System.out.println("Data received by " + consumerName + ": \n" + data);
                }
                Thread.sleep(3000); // Aguarda 3 segundos antes de buscar novos dados
            }
        } catch (RemoteException | InterruptedException e) {
            System.err.println("Erro ao consumir dados: " + e.getMessage());
        }
    }
}