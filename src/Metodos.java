import java.util.Random;

public class Metodos {

    public static Random r = new Random();
    public static Random rTemplado = new Random();

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

    public static double[] templadoSimulado(FuncionMultiDim f, double[] x, double T){
        double sigma = .2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randGauss(sigma);
            y[i] += ran;
        }

        double fx = f.f(x);
        double fy = f.f(y);

        if (fx < fy) {
            return x;
        } else if(Metodos.rTemplado.nextDouble() < Math.exp((fy-fx)/T)){
            return x;
        }

        return y;
    }

    public static double[] templadoSimuladoConPowerLaw(FuncionMultiDim f, double[] x, double T){
        double alpha = 2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randPowerLaw(alpha);
            y[i] += ran;
        }

        double fx = f.f(x);
        double fy = f.f(y);

        if (fx < fy) {
            return x;
        } else if(Metodos.rTemplado.nextDouble() < Math.exp((fy-fx)/T)){
            return x;
        }

        return y;
    }

    public static double enfriamientoSigmoide(double t, double N, double r){
        // r debe ser mayor que 0 es cuan "recta" es la función, siendo 1 muy recta, y cercana a cero muy curva.
        return (1/(1+Math.exp((t-N/2)/(N*r)))  - 1/(1+Math.exp((N/2)/(N*r)))  )*(( ( 1 - 1/N ) /(1/(1+Math.exp((-1000)/(N*r))) - 1/(1+Math.exp((N/2)/(N*r))) ))) + 1/N;
    }

    public static double enfriamientoLineal(double t, double N){
        return -1/N * t + 1;
    }

    public static double enfriamientoNewton(double t, double N, double r){
        // r debe sec cercano al log de N para que al final la probabilidad en t=N sea de 1/N
        return Math.exp(-t * r / N);
    }

    public static void main(String[] args) {
    }
}
