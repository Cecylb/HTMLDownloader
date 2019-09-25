package src;

import java.util.Scanner;
import static src.Downloader.DownloadWebPage;

public class Launch {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("URL: ");
        String url = sc.nextLine();
        DownloadWebPage(url);

    }
}
