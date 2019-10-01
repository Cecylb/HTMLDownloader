package downloader.cache;

import downloader.ParsablePatterns;
import downloader.WorkingDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

import static downloader.utils.Utils.resolveFilePath;

public class Image implements Cache {

    private final String editedCurrentHtmlLine;
    private final String html = ParsablePatterns.HTTP.pattern.toString();

    public Image(final String imageUrl, final String currentHtmlLine) {
        //System.out.println(imageUrl); // Regex bug
        //System.out.println("----");
        editedCurrentHtmlLine = currentHtmlLine.replaceFirst(imageUrl, removeHost(imageUrl));
        writeImage(imageUrl);
    }

    private void writeImage(final String imageUrl) {
        try {
            BufferedImage image = ImageIO.read(new URL(imageUrl));
            WorkingDirectory.createDirectories(imageUrl);
            ImageIO.write(image, "png", WorkingDirectory.getFile(removeHost(imageUrl)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String removeHost(final String imageUrl) {
        return imageUrl.replaceFirst(html, "");
    }

    @Override
    public String getEditedCurrentHtmlLine() {
        return editedCurrentHtmlLine;
    }
}
