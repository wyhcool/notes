import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 程序在一个目录及它的所有子目录下搜索所有文件，
 * 计算匹配的文件数目
 *
 * @version 1.0.0 2020-04-02 19:22
 * @author bruce
 */
public class FutureTest {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src): ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String keyword = in.nextLine();

            MatchCounter counter = new MatchCounter(new File(directory), keyword);
            FutureTask<Integer> task = new FutureTask<Integer>(counter);
            Thread t = new Thread(task);
            t.start();
            try {
                System.out.println(task.get() + " matching files.");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        }

    }

}

/**
 * This task counts the files in a dirctory
 */
class MatchCounter implements Callable<Integer> {

    private File directory;
    private String keyword;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        File[] files = directory.listFiles();
        List<Future<Integer>> results = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                MatchCounter counter = new MatchCounter(file, keyword);
                FutureTask<Integer> task = new FutureTask<Integer>(counter);
                Thread t = new Thread(task);
                t.start();
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
                while (!found && in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.contains(keyword)) {
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
