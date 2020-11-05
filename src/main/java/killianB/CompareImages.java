package killianB;

import com.github.kilianB.hash.Hash;
import com.github.kilianB.hashAlgorithms.AverageHash;
import com.github.kilianB.hashAlgorithms.HashingAlgorithm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * An example demonstrating how two images can be compared at a time using a single algorithm
 *
 * @author Kilian
 *
 */
public class CompareImages {

    // Key bit resolution
    private int keyLength = 64;

    // Pick an algorithm
    private HashingAlgorithm hasher = new AverageHash(keyLength);

    // Images used for testing
    private HashMap<String, BufferedImage> images = new HashMap<>();


    public CompareImages() {

        loadImages();

        // Compare each picture to each other
        images.forEach((imageName, image) -> {
            images.forEach((imageName2, image2) -> {
                if (!imageName.equals(imageName2))
                    formatOutput(imageName, imageName2, compareTwoImages(image, image2));
            });
        });
    }

    /**
     * Compares the similarity of two images.
     * @param image1	First image to be matched against 2nd image
     * @param image2	The second image
     * @return	true if the algorithm defines the images to be similar.
     */
    public boolean compareTwoImages(BufferedImage image1, BufferedImage image2) {

        //Generate the hash for each image
        Hash hash1 = hasher.hash(image1);
        Hash hash2 = hasher.hash(image2);

        //Compute a similarity score
        // Ranges between 0 - 1. The lower the more similar the images are.
        double similarityScore = hash1.normalizedHammingDistance(hash2);
        System.out.println("similarityScore " + similarityScore);
        return similarityScore < 0.4d;
    }

    /**
     * Compares the similarity of two images.
     * @param image1	First image to be matched against 2nd image
     * @param image2	The second image
     * @return	true if the algorithm defines the images to be similar.
     * @throws IOException IOerror occurred during image loading
     */
    public boolean compareTwoImages2(File image1, File image2) throws IOException {

        //Generate the hash for each image
        Hash hash1 = hasher.hash(image1);
        Hash hash2 = hasher.hash(image2);

        // Ranges between [0 - keyLength]. The lower the more similar the images are.
        int similarityScore = hash1.hammingDistance(hash2);

        return similarityScore < 41;
    }


    //Utility function
    private void formatOutput(String image1, String image2, boolean similar) {
        String format = "| %-11s | %-11s | %-8b |%n";
        System.out.printf(format, image1, image2, similar);
    }

    private void loadImages() {
        // Load images
        try {
            images.put("comp1", ImageIO.read(getClass().getResourceAsStream("../photos/comp1.jpg")));
            images.put("comp2", ImageIO.read(getClass().getResourceAsStream("../photos/comp2.jpg")));
            images.put("comp3", ImageIO.read(getClass().getResourceAsStream("../photos/comp3.jpg")));
            images.put("comp4", ImageIO.read(getClass().getResourceAsStream("../photos/comp4.jpg")));
            images.put("photo2", ImageIO.read(getClass().getResourceAsStream("../photos/photo2.jpg")));
            images.put("photo1", ImageIO.read(getClass().getResourceAsStream("../photos/photo1.jpg")));
//            images.put("lowQuality", ImageIO.read(getClass().getResourceAsStream("5/photo2-1.jpg")));
//            images.put("thumbnail", ImageIO.read(getClass().getResourceAsStream("images/thumbnail.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Print header
        System.out.println("|   Image 1   |   Image 2   | Similar  |");
    }

    public static void main(String[] args) {
        new CompareImages();
    }

}
