package math;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Created by zzt on 12/5/17.
 *
 * <h3></h3>
 */
public class ImageFeatureExtractor {

  private double[][] matrixEigenvalueCalculation(double[][] image) {
    Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(image);
    // cal eigenvalue & eigenvector
    EigenDecomposition eigenDecomposition = new EigenDecomposition(matrix);
    int c = 0;
    for (int i = 0; i < 512; i++) {
      double realEigenvalue = eigenDecomposition.getRealEigenvalue(i);
      if (eigenDecomposition.getImagEigenvalue(i) == 0) {
        c++;
      }
      RealVector eigenvector = eigenDecomposition.getEigenvector(i);
    }
//    RealMatrix d = eigenDecomposition.getD();
//    RealMatrix v = eigenDecomposition.getV();
    // sort eigenvalue and according eigenvector

    // re-build image
    return image;
  }

  private byte[] extractBytes(BufferedImage bufferedImage) throws IOException {
    // get DataBufferBytes from Raster
    WritableRaster raster = bufferedImage.getRaster();
    DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

    return data.getData();
  }

  private ColorModel extractColor(BufferedImage bufferedImage) throws IOException {
    return bufferedImage.getColorModel();
  }

  private void saveImg(byte[] bytes, String formatName) throws IOException {
    BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
    // TODO 12/7/17 color model?
    ImageIO.write(img, formatName, new File("res"));
  }

  public void imageCompress(InputStream img) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(img);
    int n = bufferedImage.getWidth();
    if (n != bufferedImage.getHeight()) {
      throw new IllegalArgumentException("Only accept square image");
    }
    byte[] bytes = extractBytes(bufferedImage);
    double[][] newImg = matrixEigenvalueCalculation(toMatrix(bytes, n));
    saveImg(toArray(newImg, n), "jpeg");
  }

  private byte[] toArray(double[][] matrix, int n) {
    byte[] res = new byte[n * n];
    return res;
  }

  private double[][] toMatrix(byte[] bytes, int n) {
    double[][] res = new double[n][n];
    int s = n * n;
    for (int i = 0; i < s; i++) {
      res[i / n][i % n] = bytes[i];
    }
    return res;
  }

  public static void main(String[] args) throws IOException {
    ImageFeatureExtractor extractor = new ImageFeatureExtractor();
    extractor.imageCompress(ClassLoader.getSystemResourceAsStream("512.jpg"));
  }
}
