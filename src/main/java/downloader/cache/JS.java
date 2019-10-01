package downloader.cache;

import downloader.WorkingDirectory;
import java.io.*;
import java.net.URL;

import static downloader.utils.Utils.*;

public class JS implements Cache{

    private String editedCurrentHtmlLine;

    public JS(final URL url, final String jsUrl, final String currentHtmlLine) throws IOException {
        //System.out.println("FOUND JS");
        //System.out.println(trimJs(jsUrl));
        String path = trimJs(jsUrl);
        editedCurrentHtmlLine = currentHtmlLine.replaceFirst(path, path.replaceFirst("/",""));
        WorkingDirectory.createDirectories(path);
        //System.out.println("PATH: " + path);
        //System.out.println("URL HOST: " + url.getHost());
        //System.out.println("GET FILE PATH: " + resolveFilePath(path));
        writeInFile(path, url);
    }

    private static String trimJs(final String jsLink) {
        return jsLink.replaceFirst("<script.+?src=\"", "").replaceFirst("\".*?></script>", "");
    }

    @Override
    public String getEditedCurrentHtmlLine() {
        return editedCurrentHtmlLine;
    }
}
