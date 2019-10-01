package downloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import static downloader.LineScanner.scanLine;

class Downloader {

    private final String ENCODING = "UTF-8";

    Downloader() throws IOException {
        Files.createDirectories(Paths.get(WorkingDirectory.FilePath));
    }

     public void download(final String webPageLink) {
        try {
            URL url = new URL(webPageLink);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            expandSite(urlConnection.getInputStream(), url);
            System.out.println("Website was successfully downloaded.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void expandSite(final InputStream inputStream, final URL url) throws IOException {
        try (
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream, ENCODING));
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new FileWriter(WorkingDirectory.getFile("/index.html")))
        ) {
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                editAndWriteLine(url, bufferedWriter, inputLine);
            }
        }
    }

    private void editAndWriteLine(final URL url, final BufferedWriter bufferedWriter, final String inputLine) throws IOException {
        bufferedWriter.write(scanLine(url, inputLine));
        bufferedWriter.write("\n");
    }
}
