package neuron;

import com.google.common.base.Preconditions;
import java.util.Arrays;

/**
 * Created by zzt on 12/15/17.
 *
 * <h3></h3>
 */
public class Neuron {

  private ActivationFunction activationFunction;
  private double[] w;
  private double theta;

  public Neuron(ActivationFunction activationFunction, int lastLayerNum) {
    this.activationFunction = activationFunction;
    w = new double[lastLayerNum];
  }

  double output(double[] input) {
    assert w.length == input.length;
    double s = 0;
    for (int i = 0; i < w.length; i++) {
      s += w[i] * input[i];
    }
    s += theta * -1;
    return activationFunction.activate(s);
  }

  public void update(double[] delta, double v) {
    Preconditions.checkArgument(delta.length == w.length);
    for (int i = 0; i < delta.length; i++) {
      w[i] += delta[i];
    }
    theta += v;
  }

  @Override
  public String toString() {
    return "Neuron{" +
        "w=" + Arrays.toString(w) +
        ", theta=" + theta +
        '}';
  }
}
