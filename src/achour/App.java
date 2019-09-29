package achour;

/**
 * The main class, starts the process of computing prime numbers to a fixed limit.
 */
public class App {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        new Filter(2, buffer);
        final int limit = 1000;

        try {
            for (int i = 2; i < limit; ++i) {
                buffer.offer(i);
            }
            buffer.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
