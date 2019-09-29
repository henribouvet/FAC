package achour;

/**
 * The element that will transmit a non-treated number to the next {@link Filter}.
 */
public class Buffer {

    /**
     * The number going through the {@link Buffer}.
     */
    private int bufferedNumber;
    /**
     * The Buffer received the instruction to stop.
     */
    private boolean terminated = false;
    /**
     * The buffered number is consumable.
     */
    private boolean canConsume = false;

    /**
     * Offers a number to the {@link Buffer} (and the chain).
     * @param i The number to deal with.
     * @throws InterruptedException If wait/notify leads to an issue.
     * @throws FilterEndException If the Buffer has been terminated.
     */
    public synchronized void offer(int i) throws InterruptedException, FilterEndException {
        if (terminated)
            throw new FilterEndException();
        if (canConsume) {
            wait();
        }
        bufferedNumber = i;
        canConsume = true;
        notify();
    }

    /**
     * Asks for the number stored in the {@link Buffer}.
     * @return The buffered number.
     * @throws InterruptedException If wait/notify leads to an issue.
     * @throws FilterEndException If the Buffer has been terminated.
     */
    public synchronized int retrieve() throws InterruptedException, FilterEndException {
        if (terminated)
            throw new FilterEndException();
        if (!canConsume)
            wait();
        notify();
        return bufferedNumber;
    }

    /**
     * Waits for the buffered number to be consumed by a {@link Filter} and indicates that the {@link Buffer} cannot accept any number anymore.
     * @throws InterruptedException If wait leads to an issue.
     */
    public synchronized void terminate() throws InterruptedException {
        if (canConsume) {
            wait();
        }
        terminated = true;
    }
}
