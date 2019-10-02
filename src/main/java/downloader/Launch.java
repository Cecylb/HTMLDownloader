package downloader;

import java.io.IOException;

public class Launch {

    public static void main(String[] args) throws IOException {
       /* if(args.length <1) {
            System.err.println
                    ("Error: value is missing. Please type in url and filename");
            System.exit(1);
        } */

        new Downloader().download("https://www.ozon.ru/");

        //"https://www.bbc.com/news/world-asia-china-49891403"
        //"https://geekboards.ru/collection/keyboards"
        //"https://www.youtube.com"
        //"https://vk.com/album-106901869_234935849"

        //new Downloader().download(args[0]);

    }
}
