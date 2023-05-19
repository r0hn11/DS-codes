import mpi.*;

public class MPISum {

    public static void main(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int N = 100; // size of array
        int[] arr = new int[N];

        // initialize array with random values
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * 100);
        }

        int chunkSize = N / size;
        int[] chunk = new int[chunkSize];

        // distribute chunks to processors
        MPI.COMM_WORLD.Scatter(arr, 0, chunkSize, MPI.INT, chunk, 0, chunkSize, MPI.INT, 0);

        // calculate sum of chunk
        int sum = 0;
        for (int i = 0; i < chunkSize; i++) {
            sum += chunk[i];
        }

        // send sum back to main processor
        int[] intermediateSums = new int[size];
        MPI.COMM_WORLD.Gather(new int[] {sum}, 0, 1, MPI.INT, intermediateSums, 0, 1, MPI.INT, 0);

        // display intermediate sums calculated at different processors
        if (rank == 0) {
            System.out.println("Intermediate sums:");
            for (int i = 0; i < size; i++) {
                System.out.println("Processor " + i + ": " + intermediateSums[i]);
            }
        }

        MPI.Finalize();
    }
}
