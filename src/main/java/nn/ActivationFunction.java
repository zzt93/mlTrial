package nn;

/**
 * Created by zzt on 12/15/17.
 *
 * <h3></h3>
 */
public enum ActivationFunction {

  AS_IS {
    @Override
    double activate(double x) {
      return x;
    }
  },
  SGN {
    @Override
    double activate(double x) {
      return x >= 0 ? 1 : 0;
    }
  }, SIGMOID {
    @Override
    double activate(double x) {
      return 1 / (1 + Math.pow(Math.E, -x));
    }
  };

  /**
   * @return 0 or 1
   * @param x
   */
  abstract double activate(double x);
}
