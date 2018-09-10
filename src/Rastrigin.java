public class Rastrigin implements FuncionMultiDim {
    public double pen1 = 5;
    public double pen2 = 8;
    public double pen3 = 500;



    public double f(double[] x, int n) {
        // x debe tener n dimensiones
        int A = 10;
        double xmin = -5.12, xmax = 5.12;
        double sumatoria = 0;
        for (int i = 0; i<n; i++){
            sumatoria += (Math.pow(x[i],2) - A*Math.cos(2*Math.PI*x[i]));
        }
        return A*n - sumatoria;
    }

    public boolean factible(double[] x) {
        boolean factible = true;
        for (int i = 0; i<x.length; i++){
            if(x[i] > 5.12 || x[i] < -5.12)
                factible = false;
        }
        return factible;
    }
}
