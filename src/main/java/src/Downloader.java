package src;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

class Downloader {

    static void DownloadWebPage(String webPageLink) {
        
        try {

            URL url = new URL(webPageLink);
            BufferedReader readBuffer =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter writeBuffer =
                    new BufferedWriter(new FileWriter("Website.html"));

            String currentLine;
            while ((currentLine = readBuffer.readLine()) != null) {
                writeBuffer.write(currentLine);
                writeBuffer.write("\n");
            }

            readBuffer.close();
            writeBuffer.close();
            System.out.println("Website was successfully downloaded.");
        }

        catch (MalformedURLException mue) {
            System.out.println("Error: Bad URL");
        } catch (IOException ie) {
            System.out.println("Error: IOException");
        }
    }
}
