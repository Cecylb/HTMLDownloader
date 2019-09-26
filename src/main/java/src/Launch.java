package src;

import static src.Downloader.DownloadWebPage;

public class Launch {

    public static void main(String[] args) {
        if(args.length <1) {
            System.err.println
                    ("Error: value is missing. Please type in url and filename");
            System.exit(1);
        }

            String url = args[0];
            String name = args[1];

        DownloadWebPage(url, name);

    }
}
