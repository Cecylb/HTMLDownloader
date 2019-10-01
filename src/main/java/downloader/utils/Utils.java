package downloader.utils;

import downloader.ParsablePatterns;
import downloader.WorkingDirectory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {

    public static String resolveFilePath(final String urlLink) {
        String editedUrlLink = urlLink.replaceFirst(ParsablePatterns.HTTP.pattern.toString(),"");
        return editedUrlLink.substring(0, editedUrlLink.lastIndexOf("/"));
    }

    public static String getNameOfFile(final String urlLink) {
        return urlLink.substring(urlLink.lastIndexOf("/"));
    }

    public static String resolveUrl(final String path, final URL url) {
        return (path.matches("^(http[s]?://www\\.|http[s]?://|www\\.).*")? path : "https://" + url.getHost() + path);
    }

    public static void writeInFile(final String path, final URL url) {
        try (
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(new URL(resolveUrl(path, url)).openStream()));
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new FileWriter(WorkingDirectory.getFile(path)));
        ) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(currentLine);
                bufferedWriter.write("\n");
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
