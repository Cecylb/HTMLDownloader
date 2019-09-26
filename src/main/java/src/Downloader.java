package src;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

class Downloader {

    static void DownloadWebPage(String webPageLink, String webPageName) {
        
        try {

            URL url = new URL(webPageLink);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(webPageName));

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(currentLine);
                bufferedWriter.write("\n");
            }

            bufferedReader.close();
            bufferedWriter.close();
            System.out.println("Website was successfully downloaded.");
        }

        catch (MalformedURLException mue) {
            System.out.println("Error: Bad URL");
        } catch (IOException ie) {
            System.out.println("Error: IOException");
        }
    }
}
