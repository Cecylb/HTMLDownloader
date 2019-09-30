package downloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;

class Downloader {

     static void DownloadWebPage(final String webPageLink, final String webPageName) {

        try {

            URL url = new URL(webPageLink);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/HTMLDownloader/" + webPageName));

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                scanLine(url, currentLine);
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

    private static void scanLine(final URL url, final String currentLine) throws IOException {
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
                        loadJS(url, matcher.group());
                        break;
                }
            }
        }
    }

    private static void loadImage(final String imageFile) {
        //System.out.println("FOUND IMAGE");
        //System.out.println(imageFile);
        //System.out.println("----");
        try {
            BufferedImage image = ImageIO.read(new URL(imageFile));
            System.out.println(getFilePath(imageFile));
            Files.createDirectories(Paths.get(System.getProperty("user.home") + "/HTMLDownloader/" + getFilePath(imageFile)));
                    ImageIO.write(image, "png", new File(System.getProperty("user.home") + "/HTMLDownloader/" + imageFile.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","")));
        } catch (MalformedURLException mue) {
            System.out.println("Error: Bad image URL");
        } catch (IOException ie) {
            System.out.println("Error: IOException");
        }
    }

    private static void loadCSS(final String cssFile) throws IOException {
        System.out.println("FOUND CSS");
        System.out.println(cssFile);
        System.out.println("----");
        try {
            String path = trimCss(cssFile);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new URL(cssFile).openStream()));
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter("cssFile"));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(currentLine);
                bufferedWriter.write("\n");
            }
        } catch (MalformedURLException mue) {
            System.out.println("Error: Bad CSS URL");
        } catch (IOException ie) {
            System.out.println("Error: IOException");
        }
    }

    private static void loadJS(final URL url, final String jsFile) throws IOException {
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
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(currentLine);
                bufferedWriter.write("\n");
            }
        } catch (MalformedURLException mue) {
            System.out.println("Error: Bad JS URL");
        } catch (IOException ie) {
            System.out.println("Error: JS IOException");
        }
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
