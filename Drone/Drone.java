package Drone;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Drone implements Runnable {
    private DroneDataServer server;
    private String droneName;

    public Drone(DroneDataServer server, String droneName) {
        this.server = server;
        this.droneName = droneName;
    }

    @Override
    public void run() {
        Random random = new Random();
        Thread.currentThread().setName(droneName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
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

                String currentDateTime = dateFormat.format(new Date());
                DroneData data = new DroneData(
                    this.droneName,
                    currentDateTime,
                    String.valueOf(950 + random.nextInt(100)),
                    String.valueOf(random.nextInt(1000)),
                    String.valueOf(15 + random.nextInt(20)),
                    String.valueOf(30 + random.nextInt(70))
                );
                server.sendData(data);
                Thread.sleep(3000); // Simula a coleta de dados a cada 3 segundos
            } catch (RemoteException e) {
                System.err.println("Erro ao enviar dados: " + e.getMessage());
                server = null; // Reseta a conexão para tentar novamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}