package downloader.cache;

import downloader.WorkingDirectory;
import java.io.*;
import java.net.URL;

import static downloader.utils.Utils.*;

public class CSS implements Cache {

    private String editedCurrentHtmlLine;

    public CSS(final URL url, final String cssUrl, final String currentHtmlLine) throws IOException {
        String path = trimCss(cssUrl);
        editedCurrentHtmlLine = currentHtmlLine.replaceFirst(path, checkPath(path));
        WorkingDirectory.createDirectories(path);
        writeInFile(path, url);
    }

    private static String trimCss(final String cssLink) {
        return cssLink.replaceFirst("<link.*?href=\"","").replaceFirst("\\.css.*?>", ""); //idk how to make it better yet
    }

    @Override
    public String getEditedCurrentHtmlLine() {
        return editedCurrentHtmlLine;
    }
}
