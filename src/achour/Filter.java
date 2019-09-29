package achour;

/**
 * The element that computes to know if a number is prime or not, and delegates the work if it's an unknown number.
 */
public class Filter implements Runnable {

    /**
     * The number that this {@link Filter} will deal with.
     */
    private final int myNumber;
    /**
     * The {@link Buffer} before and after the Filter.
     */
    private Buffer consume, produce;
    /**
     * The next {@link Filter}.
     */
    private Filter next;


    /**
     * Creates a {@link Filter} with its "in" {@link Buffer}, indicates its prime number and starts the {@link Thread}.
     * @param i The prime number of this {@link Filter}.
     * @param buffer The "in" {@link Buffer}.
     */
    public Filter(int i, Buffer buffer) {
        myNumber = i;
        consume = buffer;
        produce = new Buffer();
        System.out.println(myNumber + " est premier.");
        new Thread(this).start();
    }

    @Override
    public void run() {
        int current;
        try {
            while(true) {
                current = consume.retrieve();

                if (current % myNumber != 0) {
                    if (next == null)
                        next = new Filter(current, produce);
                    else
                        produce.offer(current);
                }
            }
        } catch (FilterEndException e) {
            try {
                produce.terminate();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("Filtre " + myNumber + " terminé (FilterEndException).");
        } catch (Exception e) {
            System.out.println("Error in " + this.toString());
        }
    }
}
