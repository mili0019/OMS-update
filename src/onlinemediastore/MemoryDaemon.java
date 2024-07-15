package onlinemediastore;

public class MemoryDaemon implements Runnable{
	private volatile boolean running = true;
    
    @Override
    public void run() {
        while (running) {
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
    
            System.out.println("Memory Usage: " + usedMemory + " bytes");
    
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void stop() {
        running = false;
    }
    
}
