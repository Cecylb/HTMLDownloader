package downloader;

import downloader.cache.CSS;
import downloader.cache.Image;
import downloader.cache.JS;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public interface LineScanner {

    static String scanLine(final URL url, final String currentHtmlLine) throws IOException {
        String editedCurrentHtmlLine = currentHtmlLine;
        for (ParsablePatterns parsablePattern : ParsablePatterns.values()) {
            Matcher matcher = parsablePattern.pattern.matcher(currentHtmlLine);
            while(matcher.find()) {
                switch (parsablePattern) {
                    case PNG:
                    //case ICO:
                    //case SVG:
                    case JPG:
                        editedCurrentHtmlLine = new Image(matcher.group(), editedCurrentHtmlLine).getEditedCurrentHtmlLine();
                        break;
                    case CSS:
                        editedCurrentHtmlLine = new CSS(url, matcher.group(), editedCurrentHtmlLine).getEditedCurrentHtmlLine();
                        break;
                    case JS:
                        editedCurrentHtmlLine = new JS(url, matcher.group(), editedCurrentHtmlLine).getEditedCurrentHtmlLine();
                        break;
                }
            }
        }
        return editedCurrentHtmlLine;
    }
}
