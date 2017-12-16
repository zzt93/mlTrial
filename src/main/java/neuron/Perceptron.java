package neuron;

import static neuron.ActivationFunction.SGN;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zzt on 12/8/17.
 *
 * <h3>Implement neuron networks only has a input layer and output layer</h3>
 * <ul>
 *   <li>small dataset multiple times training vs large dataset one time training</li>
 *   <li>update formula: learningRate * (hHat - y) * xi</li>
 * </ul>
 */
public class Perceptron {

  private static final int LAYERS = 2;
  private static Logger logger = LoggerFactory.getLogger(Perceptron.class);
  private final int inputLayerNum;
  private final int outputLayerNum = 1;
  private List<Neuron>[] layers = new ArrayList[LAYERS];
  private double learningRate;

  private Perceptron(int inputLayerNum, double learningRate) {
    this.inputLayerNum = inputLayerNum;
//    layers[0] = new ArrayList<>(this.inputLayerNum);
//    for (int i = 0; i < inputLayerNum; i++) {
//      layers[0].add(new Neuron(AS_IS, 1));
//    }
    layers[1] = new ArrayList<>(this.outputLayerNum);
    for (int i = 0; i < outputLayerNum; i++) {
      layers[1].add(new Neuron(SGN, inputLayerNum));
    }
    this.learningRate = learningRate;
  }

  public static Perceptron train(LabelPoint[] input, double learningRate, int max) {
    Preconditions.checkArgument(input.length != 0);
    Preconditions.checkArgument(learningRate > 0 && learningRate < 1);
    LabelPoint labelPoint = input[0];
    Perceptron perceptron = new Perceptron(labelPoint.getData().length, learningRate);
    logger.info("Start training");
    for (int i = 0; i < max; i++) {
      System.out.println(perceptron);
      perceptron.feedForwardAndUpdateWeight(input);
    }
    logger.info("Training finished");
    return perceptron;
  }

  private void feedForwardAndUpdateWeight(LabelPoint[] labelPoints) {
    for (LabelPoint labelPoint : labelPoints) {
      double[] input = labelPoint.getData();
      double y = feedForward(input);
      double yHat = labelPoint.getLabel()[0];
      List<Neuron> out = layers[1];
      for (Neuron neuron : out) {
        double[] delta = new double[inputLayerNum];
        for (int i = 0; i < inputLayerNum; i++) {
          delta[i] = learningRate * (yHat - y) * input[i];
        }
        neuron.update(delta, learningRate * (yHat - y) * -1);
      }
    }
  }

  private double feedForward(double[] input) {
    double[] out = new double[outputLayerNum];
    List<Neuron> layer = layers[1];
    for (int i = 0; i < layer.size(); i++) {
      Neuron neuron = layer.get(i);
      out[i] = neuron.output(input);
    }
    return out[0];
  }

  public double test(double[] input) {
    Preconditions.checkArgument(input.length == inputLayerNum);
    return feedForward(input);
  }

  @Override
  public String toString() {
    return "Perceptron{" +
        "outputLayer=" + Arrays.toString(layers) +
        '}';
  }

  public static void main(String[] args) {
    Random random = new Random(12);
    int n = 20;
    LabelPoint[] labelPoints = new LabelPoint[n];
    for (int i = 0; i < n; i++) {
      boolean b1 = random.nextBoolean();
      boolean b2 = random.nextBoolean();
      boolean b3 = b1 & b2;
      labelPoints[i] = new LabelPoint(new double[]{b1 ? 1 : 0, b2 ? 1 : 0},
          new double[]{b3 ? 1 : 0});
    }
    Perceptron train = Perceptron.train(labelPoints, 0.1, 20);
    boolean b1 = random.nextBoolean();
    boolean b2 = random.nextBoolean();
    System.out.format("%b & %b = %f", b1, b2, train.test(new double[]{b1 ? 1 : 0, b2 ? 1 : 0}));
  }

}
