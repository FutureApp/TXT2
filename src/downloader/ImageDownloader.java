package downloader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;

public class ImageDownloader
{
    public static void main(String[] arguments) throws IOException
    {
        downloadImage("http://lh3.google.com/_0vJpWmRjlxc/Rjmgn-23GWI/AAAAAAAABQo/b91lJY06Y-A/s800/Ahimsa_1month+_3.jpg",
                new File("").getAbsolutePath());
    }

    public static void downloadImage(String sourceUrl, String targetDirectory)
            throws MalformedURLException, IOException, FileNotFoundException
    {
        URL imageUrl = new URL(sourceUrl);
        try (InputStream imageReader = new BufferedInputStream(
                imageUrl.openStream());
                OutputStream imageWriter = new BufferedOutputStream(
                        new FileOutputStream(targetDirectory + File.separator
                                + FilenameUtils.getName(sourceUrl)));)
        {
            int readByte;

            while ((readByte = imageReader.read()) != -1)
            {
                imageWriter.write(readByte);
            }
        }
    }
}