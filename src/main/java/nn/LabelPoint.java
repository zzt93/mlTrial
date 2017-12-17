package nn;

/**
 * Created by zzt on 12/15/17.
 *
 * <h3></h3>
 */
public class LabelPoint {

  private final double[] data;
  private final double[] label;

  public double[] getData() {
    return data;
  }

  public double[] getLabel() {
    return label;
  }

  public LabelPoint(double[] data, double[] label) {
    this.data = data;
    this.label = label;
  }
}
