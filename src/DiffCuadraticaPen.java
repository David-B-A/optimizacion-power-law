 public class DiffCuadraticaPen implements Diff2Funcion {
    public double pen1 = 5;
    public double pen2 = 8;
    public double pen3 = 500;

    public double f(double[] x) {
        return (x[0] * x[0] + x[1] * x[1]) + this.pen1 * ((x[0]<1) ? (1-x[0]) : 0) + this.pen2 * (x[1] < 2 ? 2 - x[1] : 0) + this.pen3 * (x[0] + 2 * x[1] > 6 ? x[0] + 2 * x[1] - 6 : 0);
    }

    public double[] gradient(double[] x) {
        return new double[]{ 2 * x[0] + this.pen1 * (x[0] < 1 ? -1 : 0) + this.pen3 * (x[0] + 2 * x[1] > 6 ? 1 : 0), 2 * x[1] + this.pen2 * (x[1] < 2 ? -1 : 0) + this.pen3 * (x[0] + 2 * x[1] > 6 ? 2 : 0)};
    }

    public double detHesian(double[] x) {
        return 4.0;
    }

    public boolean factible(double[] x) {
        return x[0] >= 1 && x[1] >= 2 && x[0] + 2 * x[1] <= 6;
    }
}
