package downloader;

import java.io.IOException;

public class Launch {

    public static void main(String[] args) throws IOException {
       /* if(args.length <1) {
            System.err.println
                    ("Error: value is missing. Please type in url and filename");
            System.exit(1);
        } */

        new Downloader().download("https://www.bbc.com/news/world-asia-china-49891403");

        //new Downloader().download(args[0]);

    }
}
