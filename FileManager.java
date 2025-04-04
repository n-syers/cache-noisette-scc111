import java.awt.image.BufferedImage;
import java.io.RandomAccessFile;

/**
 * This class represents a file manager. Methods can be called directly, meaning
 * an instance of this class does not need to be made to access methods.
 */
public class FileManager {
    /**
     * The function takes a directory path as a string and returns a BufferedImage using RandomAccessFile data streams. Byte data is read row-by-row and converted to RGB values and set on an empty bufferedImage.
     * @param levelFilePath String of the file path.
     * @return The bufferedImage after the image is created.
     */
    public static BufferedImage readBitmapAsBufferedImage(String levelFilePath){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(levelFilePath, "r");
            randomAccessFile.skipBytes(54);
            BufferedImage bufferedImage = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
            int rowLength = 15 * 3;
            int paddedRowLength = (rowLength + 3) & ~3;
            byte[] pixelData = new byte[paddedRowLength]; // 15x15 pixel image with 3 bytes per pixel rounded to the next multiple of 4
            for (int y = 15 - 1; y >= 0; y--) {
                randomAccessFile.readFully(pixelData); // Read one row of pixel data
                for (int x = 0; x < 15; x++) {
                    int blue = pixelData[x * 3] & 0xFF;
                    int green = pixelData[x * 3 + 1] & 0xFF;
                    int red = pixelData[x * 3 + 2] & 0xFF;
                    int rgb = (red << 16) | (green << 8) | blue;
                    bufferedImage.setRGB(x, y, rgb);
                }
            }
            randomAccessFile.close();
            return bufferedImage;
        } catch (Exception e) {
            System.err.println("(FILEMANAGER::readBitmapAsBufferedImage) An error occurred: " + e.getMessage());
            return null;
        }
    }
}
