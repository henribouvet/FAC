package achour;

public class Buffer {

    private int bufferedNumber;
    private boolean terminated = false, canConsume = false;

    public synchronized void offer(int i) throws InterruptedException {
        if (canConsume) {
            wait();
        }
        bufferedNumber = i;
        canConsume = true;
        notify();
    }

    public synchronized int retrieve() throws InterruptedException, FilterEndException {
        if (terminated)
            throw new FilterEndException();
        if (!canConsume)
            wait();
        notify();
        return bufferedNumber;
    }

    public synchronized void terminate() throws InterruptedException {
        if (canConsume) {
            wait();
        }
        terminated = true;
    }

    public boolean isTerminated() {
        return terminated;
    }
}
