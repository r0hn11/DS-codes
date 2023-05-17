import java.util.*;

public class RingAlgorithm{
    private static final int NUM_NODES = 5;

    private static class Node extends Thread{
        private int id;
        private int nextId;
        private boolean coordinator;

        public Node(int id, int nextId){
            this.id = id;
            this.nextId = nextId;
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
            while(true){
                if(coordinator){
                    System.out.println("Node " + id + " is coordinator");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Node " + id + " received coordinator message");
                }
				else{
                    // send election message to next node
                    System.out.println("Node " + id + " sending election message to node " + nextId);
                    if (sendElectionMessage(nextId)) {
                        // next node responded, so wait for coordinator message
                        System.out.println("Node " + id + " waiting for coordinator message");
                        try{
                            wait();
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
					else{
                        // next node did not respond, so this node becomes coordinator
                        setCoordinator();
                        // send coordinator message to previous node
                        int prevId = id == 0 ? NUM_NODES - 1 : id - 1;
                        System.out.println("Node " + id + " sending coordinator message to node " + prevId);
                        sendCoordinatorMessage(prevId);
                    }
                }
            }
        }

        private boolean sendCoordinatorMessage(int node){
            // send coordinator message to specified node
            return Math.random() < 0.5;
        }
    }

    public static void main(String[] args){
        List<Node> nodes = new ArrayList<>();
        for(int i = 0; i < NUM_NODES; i++){
            int nextId = i == NUM_NODES - 1 ? 0 : i + 1;
            nodes.add(new Node(i, nextId));
        }
        for(Node node : nodes){
            node.start();
        }
    }
}
