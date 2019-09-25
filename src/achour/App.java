package achour;

public class App {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Filter filter = new Filter(2, buffer);
        final int limit = 100;


        try {
            for (int i = 2; i < limit; ++i) {
                buffer.offer(i);
            }
            buffer.terminate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
