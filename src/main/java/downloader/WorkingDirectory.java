package downloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static downloader.utils.Utils.resolveFilePath;

public interface WorkingDirectory {

    String FilePath = System.getProperty("user.home") + "/HTMLDownloader/";

    static File getFile(final String fileName) {
        return  new File(FilePath + fileName);
    }

    static void createDirectories(final String path) throws IOException {
        Files.createDirectories(Paths.get(WorkingDirectory.FilePath + resolveFilePath(path)));
    }
}
