import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TrackingNumberGenerator {
    private AtomicLong atomicLongCounter;

    public TrackingNumberGenerator() {
        atomicLongCounter = new AtomicLong(1000);
    }

    public synchronized String generateRandomNumber(String prefixToken) {
        long timeStamp = System.currentTimeMillis();
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        long seqNumber = atomicLongCounter.getAndIncrement();

        return String.format("%s%s%08d%d",prefixToken, uniqueId, seqNumber, timeStamp);
    }

    public static void main(String[] args) {
        TrackingNumberGenerator generator = new TrackingNumberGenerator();

        // Simulate high concurrency with multiple threads
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    System.out.println(generator.generateRandomNumber("UID-"));
                }
            }).start();
        }
    }
}
