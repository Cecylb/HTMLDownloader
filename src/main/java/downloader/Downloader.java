package downloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.regex.Matcher;

class Downloader {

    private String hostName;
    static Vector<String> URLs;

     static void DownloadWebPage(final String webPageLink, final String webPageName) {

        //hostName = webPageLink;

        //Vector<String> urlToDownload = downloadAllUrl(webPageLink);
        try {

            URL url = new URL(webPageLink);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(webPageName));

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                scanLine(currentLine);
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
/*
        for(int i = 0; i < urlToDownload.size(); i++) {
            DownloadWebPage(urlToDownload.elementAt(i));
        }
*/
    }

    private static void scanLine(final String currentLine) throws IOException {
        for (ParsablePatterns parsablePattern : ParsablePatterns.values()) {
            Matcher matcher = parsablePattern.pattern.matcher(currentLine);
            System.out.println(currentLine);
            while(matcher.find()) {
                switch (parsablePattern.patternType) {
                    case "PNG":
                    case "SVG":
                    case "JPG":
                        loadImage(matcher.group());
                        break;
                    case "CSS":
                        loadCSS(matcher.group());
                        break;
                    case "JS":
                        loadJS(matcher.group());
                        break;
                }
            }
        }
    }

    private static void loadImage(final String imageFile) {
        //System.out.println("FOUND IMAGE");
        //System.out.println("HERES THE LINK");
        //System.out.println(imageFile);
        //System.out.println("----");
        try {
            BufferedImage image = ImageIO.read(new URL(imageFile));
            File picture = new File(new URL(imageFile).getFile());
            //System.out.println(new URL(imageFile).getPath());
            //System.out.println(new URL(imageFile).getFile());
            Files.createDirectories(Paths.get(getFilePath(imageFile)));
                    ImageIO.write(image, "png", new File(imageFile.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","")));
        } catch (MalformedURLException mue) {
            System.out.println("Error: Bad image URL");
        } catch (IOException ie) {
            System.out.println("Error: IOException");
        }
    }

    private static void loadCSS(final String cssFile) throws IOException {
        //System.out.println("FOUND CSS");
        //System.out.println("HERES THE LINK");
        //System.out.println(cssFile);
        //System.out.println("----");
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(new URL(cssFile).openStream()));
        BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter("cssFile"));
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            bufferedWriter.write(currentLine);
            bufferedWriter.write("\n");
        }
    }

    private static void loadJS(final String jsFile) throws IOException {
        System.out.println("FOUND JS");
        System.out.println("HERES THE LINK");
        System.out.println(jsFile);
        System.out.println("----");
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(new URL(jsFile).openStream()));
        BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter("jsFile"));
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            bufferedWriter.write(currentLine);
            bufferedWriter.write("\n");
        }
    }

    private static String getFilePath(final String urlLink) {
         String urlLinkNoHTTP = urlLink.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","");
         return  urlLinkNoHTTP.substring(0, urlLinkNoHTTP.lastIndexOf(File.separator));
    }

    private static String getNameOfFile(final String urlLink) {
         return urlLink.substring(urlLink.lastIndexOf(File.separator));
    }

    /*

    private static Vector<String> downloadAllUrl(final String webPageLink) {

        return urlToDownload;
    }

    private boolean isUrlAlreadyInTheVector(final Vector<String> urls, final String url) {
         for(String currentUrl : urls) {
             if(currentUrl.equals(url)) return true;
         }
         return false;
    }

    private void modifyLink() {

    }

    private void processLink() {

    }


     */
}
