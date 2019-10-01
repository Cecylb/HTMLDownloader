package downloader;

import static downloader.Downloader.DownloadWebPage;

public class Launch {

    public static void main(String[] args) {
       /* if(args.length <1) {
            System.err.println
                    ("Error: value is missing. Please type in url and filename");
            System.exit(1);
        } */

        DownloadWebPage("https://vk.com/album-106901869_234935849");

        //DownloadWebPage(args[0]);

    }
}
