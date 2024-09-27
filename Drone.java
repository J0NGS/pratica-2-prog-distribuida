// Drone.java
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;

public class Drone implements Runnable {
    private DroneDataServer server;

    public Drone(DroneDataServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                if (server == null) {
                    try {
                        server = (DroneDataServer) Naming.lookup("rmi://localhost/DroneDataServer");
                    } catch (Exception e) {
                        System.err.println("Erro ao conectar ao servidor: " + e.getMessage());
                        Thread.sleep(5000); // Espera antes de tentar novamente
                        continue;
                    }
                }

                String data = "Pressure: " + (950 + random.nextInt(100)) + " hPa, " +
                              "Solar Radiation: " + (random.nextInt(1000)) + " W/m², " +
                              "Temperature: " + (15 + random.nextInt(20)) + " °C, " +
                              "Humidity: " + (30 + random.nextInt(70)) + " %";
                server.sendData(data);
                Thread.sleep(5000); // Simula a coleta de dados a cada 5 segundos
            } catch (RemoteException e) {
                System.err.println("Erro ao enviar dados: " + e.getMessage());
                server = null; // Reseta a conexão para tentar novamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}