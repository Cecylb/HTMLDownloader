package downloader;

import static downloader.Downloader.DownloadWebPage;

public class Launch {

    public static void main(String[] args) {
       /* if(args.length <1) {
            System.err.println
                    ("Error: value is missing. Please type in url and filename");
            System.exit(1);
        } */

        DownloadWebPage("https://www.youtube.com/", "youtube.html");

        //DownloadWebPage(args[0], args[1]);

    }
}
