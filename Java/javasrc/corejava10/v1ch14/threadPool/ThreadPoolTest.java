import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @version 1.0.0 2020-04-02 22:10
 * @author bruce
 */
public class ThreadPoolTest {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {

            System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src): ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String keyword = in.nextLine();

            ExecutorService pool = Executors.newCachedThreadPool();

            MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(counter);

            try {
                System.out.println(result.get() + " matching files.");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ExecutionException ex) {

            }
            pool.shutdown();

            int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
            System.out.println("largest pool size=" + largestPoolSize);

        }

    }
}


/**
 * This task counts the files in a dirctory
 */
class MatchCounter implements Callable<Integer> {

    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    public MatchCounter(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        count = 0;
        File[] files = directory.listFiles();
        List<Future<Integer>> results = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                MatchCounter counter = new MatchCounter(file, keyword, pool);
                Future<Integer> result = pool.submit(counter);
                results.add(result);
            } else {
                if (search(file)) {
                    count++;
                }
            }
        }

        for (Future<Integer> result : results) {
            try {
                count += result.get();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        }
        return count;
    }

    /**
     * search a file for a given keyword and
     * prints all matching lines
     * @param file the file to search
     */
    public boolean search(File file) {
        try {
            try (Scanner in = new Scanner(file, "UTF-8")) {
                boolean found = false;
                int lineNumber = 0;
                while (in.hasNextLine()) {
                    lineNumber++;
                    String line = in.nextLine();
                    if (line.contains(keyword)) {
                        System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
                        found = true;
                    }
                }
                return found;
            }
        } catch (FileNotFoundException ex) {
            return false;
        }
    }
}

