package homework.thread;

public class ThreadStart {
    private static ThreadWorker worker = new ThreadWorker();

    public static void main (String[] args) throws Exception {
        String[] values = {"A", "B", "C"};
        worker.thread(values);
    }
}
