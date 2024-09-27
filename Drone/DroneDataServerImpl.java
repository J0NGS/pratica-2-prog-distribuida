package Drone;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class DroneDataServerImpl extends UnicastRemoteObject implements DroneDataServer {
    private ConcurrentMap<String, Deque<DroneData>> dataStore;
    private ConcurrentMap<String, Deque<DroneData>> consumerQueues;

    public DroneDataServerImpl() throws RemoteException {
        dataStore = new ConcurrentHashMap<>();
        consumerQueues = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void sendData(DroneData data) throws RemoteException {
        String droneName = data.getDroneName();
        dataStore.computeIfAbsent(droneName, k -> new ConcurrentLinkedDeque<>()).push(data);
        System.out.println("Data received from " + droneName + ": \n" + data);

        // Adiciona a mensagem na fila de cada consumidor
        for (Deque<DroneData> queue : consumerQueues.values()) {
            queue.addLast(data);
        }
    }

    @Override
    public synchronized void registerDrone(String droneName) throws RemoteException {
        dataStore.putIfAbsent(droneName, new ConcurrentLinkedDeque<>());
        System.out.println("Drone registered: " + droneName);
    }

    @Override
    public synchronized void registerConsumer(String consumerName) throws RemoteException {
        consumerQueues.putIfAbsent(consumerName, new ConcurrentLinkedDeque<>());
    }

    @Override
    public synchronized List<DroneData> getNewData(String consumerName) throws RemoteException {
        Deque<DroneData> queue = consumerQueues.get(consumerName);
        if (queue == null) {
            throw new RemoteException("Consumer not registered: " + consumerName);
        }
        List<DroneData> newData = queue.stream().collect(Collectors.toList());
        queue.clear();
        return newData;
    }
}