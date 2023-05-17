import java.util.*;

public class BullyAlgo{
    private static final int NUM_NODES = 5;

    private static class Node extends Thread {
        private int id;
        private boolean coordinator;

        public Node(int id){
            this.id = id;
            this.coordinator = false;
        }

        private boolean sendElectionMessage(int node){
            // send election message to specified node
            return Math.random() < 0.5;
        }

        public synchronized void setCoordinator(){
            coordinator = true;
            System.out.println("Node " + id + " is now coordinator");
            notifyAll();
        }

        public synchronized void unsetCoordinator(){
            coordinator = false;
        }

        @Override
        public synchronized void run(){
            System.out.println("Node " + id + " started");
            while (true) {
                if (coordinator) {
                    System.out.println("Node " + id + " is coordinator");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Node " + id + " received coordinator message");
                } else {
                    // send election message to higher numbered nodes
                    for (int i = id + 1; i < NUM_NODES; i++) {
                        System.out.println("Node " + id + " sending election message to node " + i);
                        if (sendElectionMessage(i)) {
                            // higher node responded, so wait for coordinator message
                            System.out.println("Node " + id + " waiting for coordinator message");
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    // no higher node responded, so this node becomes coordinator
                    if (!coordinator) {
                        for (int i = id - 1; i >= 0; i--) {
                            System.out.println("Node " + id + " sending coordinator message to node " + i);
                            if (sendCoordinatorMessage(i)) {
                                break;
                            }
                        }
                        setCoordinator();
                    }
                }
            }
        }

        private boolean sendCoordinatorMessage(int node){
            // send coordinator message to specified node
            return Math.random() < 0.5;
        }
    }

    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < NUM_NODES; i++) {
            nodes.add(new Node(i));
        }
        for (Node node : nodes) {
            node.start();
        }
    }
}
