import java.util.Random;

public class Metodos {

    public static Random r = new Random();

    public static double[] gradienteNewton(FuncionMultiDimDiff2 f, double[] x) {
        double[] grad = f.gradiente(x);
        double h = f.determinanteDelHessiano(x);

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            y[i] += (-grad[i]) / h;
        }

        return y;
    }

    public static double[] gradienteDescendente(FuncionMultiDimDiff f, double[] x) {
        double[] grad = f.gradiente(x);
        double h = 0.4; //Tamaño del paso

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            y[i] += (-grad[i]) * h;
        }

        return y;
    }

    public static double randGauss(double sigma) {
        return Metodos.r.nextGaussian() * sigma;
    }

    public static double randPowerLaw(double alpha) {
        double n = Metodos.r.nextDouble()*2;
        return n>=1?(1-(n-1))*Math.exp(1/(1-alpha)):-(1-n)*Math.exp(1/(1-alpha));
    }

    public static double[] gradienteDescendenteConMomentum(FuncionMultiDimDiff f, double[] delta, double[] x) {
        double[] grad = f.gradiente(x);
        double alpha = 0.1; //Tamaño del paso (antes del momentum)
        double m = 0.8; //Momentum

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            delta[i] = m * delta[i] - grad[i] * alpha;
            y[i] += delta[i];
        }

        return y;
    }

    public static double[] ascensoALaColina(FuncionMultiDim f, double[] x) {
        double sigma = .2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randGauss(sigma);
            y[i] += ran;
        }

        if (f.f(x) < f.f(y)) {
            return x;
        }

        return y;
    }

    public static double[] ascensoALaColinaConPowerLaw(FuncionMultiDim f, double[] x) {
        double alpha = 2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randPowerLaw(alpha);
            y[i] += ran;
        }

        if (f.f(x) < f.f(y)) {
            return x;
        }

        return y;
    }

    public static void main(String[] args) {

    }
}
