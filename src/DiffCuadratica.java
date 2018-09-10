public class DiffCuadratica implements Diff2Funcion {
    public double f(double[] x) {
        return x[0] * x[0] + x[1] * x[1];
    }

    public double[] gradient(double[] x) {
        return new double[]{2 * x[0], 2 * x[1]};
    }

    public double detHesian(double[] x) {
        return 4.0;
    }

    public boolean factible(double[] x) {
        return false;
    }
}
