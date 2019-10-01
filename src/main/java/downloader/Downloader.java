package downloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;

class Downloader {

     static void DownloadWebPage(final String webPageLink, final String webPageName) {

        try {
            URL url = new URL(webPageLink);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            System.out.println(toString(urlConnection.getInputStream(), url, webPageName));
            System.out.println("Website was successfully downloaded.");
        } catch (MalformedURLException mue) {
            System.out.println("Error: Bad URL");
        } catch (IOException ie) {
            System.out.println("Error: IOException");
        }
    }

    private static String toString(final InputStream inputStream, final URL url, final String webPageName) throws IOException
    {
        try (
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/HTMLDownloader/" + "/index.html"));)
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                inputLine = scanLine(url, inputLine);
                bufferedWriter.write(inputLine);
                bufferedWriter.write("\n");
                stringBuilder.append(inputLine);
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        }
    }

    private static String scanLine(final URL url, String currentHtmlLine) throws IOException {
        for (ParsablePatterns parsablePattern : ParsablePatterns.values()) {
            Matcher matcher = parsablePattern.pattern.matcher(currentHtmlLine);
            while(matcher.find()) {
                switch (parsablePattern.patternType) {
                    case "PNG":
                    case "SVG":
                    case "JPG":
                        currentHtmlLine = cacheImage(matcher.group(), currentHtmlLine);
                        break;
                    case "CSS":
                        currentHtmlLine = cacheCSS(url, matcher.group(), currentHtmlLine);
                        break;
                    case "JS":
                        currentHtmlLine = cacheJS(url, matcher.group(), currentHtmlLine);
                        break;
                }
            }
        }
        return currentHtmlLine;
    }

    private static String cacheImage(final String imageFile, final String currentHtmlLine) throws IOException {
        //System.out.println("FOUND IMAGE");
        System.out.println(imageFile); // Regex bug
        //System.out.println("----");
        String editedCurrentHtmlLine = currentHtmlLine.replaceFirst(imageFile, imageFile.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)",""));
        System.out.println(editedCurrentHtmlLine);
        try {
            BufferedImage image = ImageIO.read(new URL(imageFile));
            Files.createDirectories(Paths.get(System.getProperty("user.home") + "/HTMLDownloader/" + getFilePath(imageFile)));
            ImageIO.write(image, "png", new File(System.getProperty("user.home") + "/HTMLDownloader/" + imageFile.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","")));
        } catch (MalformedURLException mue) {
            System.out.println("Error: Bad image URL");
        } catch (IOException ie) {
            System.out.println("Error: IOException");
        }
        return editedCurrentHtmlLine;
    }

    private static String cacheCSS(final URL url, final String cssFile, final String currentHtmlLine) throws IOException {
        //System.out.println("FOUND CSS");
        //System.out.println(cssFile);
        //System.out.println("----");

        String path = trimCss(cssFile);
        Files.createDirectories(Paths.get(System.getProperty("user.home") + "/HTMLDownloader/" + getFilePath(path).replaceFirst("/", "")));
        try (
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new URL( "https://" + url.getHost() + trimCss(cssFile)).openStream()));
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/HTMLDownloader/" + path));
        ) {
            String currentCssLine;
            while ((currentCssLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(currentCssLine);
                bufferedWriter.write("\n");
            }
        }
        return currentHtmlLine;
    }

    private static String cacheJS(final URL url, final String jsFile, final String currentHtmlLine) throws IOException {
        //System.out.println("FOUND JS");
        //System.out.println(url.getHost() + trimJs(jsFile));
        //System.out.println("----");
        String path = trimJs(jsFile);
        Files.createDirectories(Paths.get(System.getProperty("user.home") + "/HTMLDownloader/" + getFilePath(path).replaceFirst("/", "")));
        try (
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new URL( "https://" + url.getHost() + trimJs(jsFile)).openStream()));
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/HTMLDownloader/" + path));
        ) {
            String currentJsLine;
            while ((currentJsLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(currentJsLine);
                bufferedWriter.write("\n");
            }
        }
        return currentHtmlLine;
    }

    private static String getFilePath(final String urlLink) {
         String editedUrlLink = urlLink.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","");
         return editedUrlLink.substring(0, editedUrlLink.lastIndexOf(File.separator));
    }

    private static String getNameOfFile(final String urlLink) {
         return urlLink.substring(urlLink.lastIndexOf(File.separator));
    }

    private static String trimJs(final String jsLink) {
         String jsLinkNoHead = jsLink.replaceFirst("<script.+?src=\"", "");
          return jsLinkNoHead.replaceFirst("\" .+?></script>", "");
    }

    private static String trimCss(final String cssLink) {
         String cssLinkNoHead = cssLink.replaceFirst("<link rel=\".+?\" href=\"","");
         return cssLinkNoHead.replaceFirst("\".+?>", "");
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
