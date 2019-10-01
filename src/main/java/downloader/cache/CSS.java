package downloader.cache;

import downloader.WorkingDirectory;
import java.io.*;
import java.net.URL;

import static downloader.utils.Utils.*;

public class CSS implements Cache {

    private String editedCurrentHtmlLine;

    public CSS(final URL url, final String cssUrl, final String currentHtmlLine) throws IOException {
        //System.out.println("FOUND CSS");
        //System.out.println(trimCss(cssUrl));
        //System.out.println("----");
        String path = trimCss(cssUrl);
        editedCurrentHtmlLine = currentHtmlLine.replaceFirst(path, path.replaceFirst("/",""));
        WorkingDirectory.createDirectories(path);
        //System.out.println(Paths.get(System.getProperty("user.home") + "/HTMLDownloader/" + resolveFilePath(path)));
        //System.out.println(resolveFilePath(path));
        writeInFile(path, url);
    }

    private static String trimCss(final String cssLink) {
        return cssLink.replaceFirst("<link.*?href=\"","").replaceFirst("\\.css.*?>", "") + ".css"; //idk how to make it better yet
    }

    @Override
    public String getEditedCurrentHtmlLine() {
        return editedCurrentHtmlLine;
    }
}
