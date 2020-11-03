import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Quora quora = new Quora();
        quora.crawler();
    }
}
