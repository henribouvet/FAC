package achour;

public class Filter implements Runnable {

    private final int myNumber;
    private Buffer consume, produce;
    private Filter next;


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
            while (!consume.isTerminated()) {
                current = consume.retrieve();

                if (current % myNumber != 0) {
                    if (next == null)
                        next = new Filter(current, produce);
                    else
                        produce.offer(current);
                }
            }
            produce.terminate();
        } catch (Exception e) {
            System.out.println("Error in " + this.toString());
        }
    }
}
