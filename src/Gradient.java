import java.util.Random;

public class Gradient {

    public static Random r = new Random();

    public static double[] newton(Diff2Funcion f, double[] x) {
        double[] grad = f.gradient(x);
        double h = f.detHesian(x);

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            y[i] += (-grad[i]) / h;
        }

        return y;
    }

    public static double[] newtonDesc(DiffFuncion f, double[] x) {
        double[] grad = f.gradient(x);
        double h = 0.4;

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            y[i] += (-grad[i]) * h;
        }

        return y;
    }

    public static double randGauss(double sigma) {
        return Gradient.r.nextGaussian() * sigma;
    }

    public static double randPowerLaw(double alpha) {
        double n = Gradient.r.nextDouble()*2;
        return n>=1?(1-(n-1))*Math.exp(1/(1-alpha)):-(1-n)*Math.exp(1/(1-alpha));
    }

    public static double[] newtonMomento(DiffFuncion f, double[] delta, double[] x) {
        double[] grad = f.gradient(x);
        double alpha = 0.1;
        double miu = 0.8;

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            delta[i] = miu * delta[i] - grad[i] * alpha;
            y[i] += delta[i];
        }

        return y;
    }

    public static double[] newtonAscenso(Funcion f, double[] x) {
        double sigma = .2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Gradient.randGauss(sigma);
            y[i] += ran;
        }

        if (f.f(x) < f.f(y)) {
            return x;
        }

        return y;
    }
    public static double[] newtonAscensoMultiDim(FuncionMultiDim f, double[] x, int n) {
        double sigma = .2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Gradient.randGauss(sigma);
            y[i] += ran;
        }

        if (f.f(x,n) < f.f(y,n)) {
            return x;
        }

        return y;
    }

    public static double[] newtonAscensoPL(Funcion f, double[] x) {
        double alpha = 2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Gradient.randPowerLaw(alpha);
            y[i] += ran;
        }

        if (f.f(x) < f.f(y)) {
            return x;
        }

        return y;
    }

    public static double[] newtonAscensoPLMultiDim(FuncionMultiDim f, double[] x, int n) {
        double alpha = 2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Gradient.randPowerLaw(alpha);
            y[i] += ran;
        }

        if (f.f(x,n) < f.f(y,n)) {
            return x;
        }

        return y;
    }

    public static double[] newtonRaphson(Diff2Funcion funcion) {
        double[] x0 = {2, 3};
        double[] delta = {0, 0};

        int cuantasIteraciones = 0;
        int muchasIteraciones = 1000;
        while (!funcion.factible(x0) && cuantasIteraciones < muchasIteraciones) {
            double[] x1 = Gradient.newtonAscenso(funcion, x0);

            System.out.println("IT: " + cuantasIteraciones + " -> " + x1[0] + " : " + x1[1]);

            x0 = x1;
            cuantasIteraciones += 1;
        }

        return x0;
    }

    public static void main(String[] args) {

        Rastrigin rastrigin = new Rastrigin();
        int n=10;
        double[] xPL = new double[n];
        double[] x = new double[n];
        for (int i =0 ; i<n; i++){
            double temp = Gradient.r.nextInt(8) + Gradient.r.nextDouble();
            xPL[i] = temp >4.5? -(temp-4.5) : temp;

            x[i] = xPL[i];
        }
        int ultimaPL =0;
        int ultima = 0;
        boolean factiblePL = false;
        boolean factible = false;
        for (int i = 0; i <= n*10000; i++){
            double[] resPL = newtonAscensoPLMultiDim(rastrigin, xPL, n);
            if(rastrigin.factible(resPL)){
                if(xPL[0] != resPL[0] && xPL[1] != resPL[1])
                    ultimaPL = i;
                xPL = resPL;
            }
            factiblePL = rastrigin.factible(xPL);
        }
        for (int i = 0; i <= n*10000; i++){
            double[] res = newtonAscensoMultiDim(rastrigin, x, n);
            if(rastrigin.factible(res)) {
                if(x[0] != res[0] && x[1] != res[1])
                    ultima = i;
                x = res;

            }
            factible = rastrigin.factible(x);
        }


        /*
        for (int i = 0; i <= n*100000; i++){
            double[] res = newtonAscensoMultiDim(rastrigin, x, n);
            if(x[0] != res[0] && x[1] != res[1])
                ultima = i;
            x = res;
        }
*/

        String resultadoPL = "Res PL: ";
        String resultado = "Res: ";
        for (int j = 0;j<n; j++){
            resultadoPL += xPL[j] + " :: ";
            resultado += x[j] + " :: ";
        }
        System.out.println("Ultima Iteración con cambio Power Law: "+ ultimaPL);
        System.out.println(resultadoPL);
        System.out.println("Factible: " + factiblePL);
        System.out.println();
        System.out.println("Ultima Iteración con cambio: "+ ultima);
        System.out.println(resultado);
        System.out.println("Factible: " + factible);

        /*DiffCuadraticaPen cuadratica = new DiffCuadraticaPen();
        double[] xPL = {2,3};
        double[] x = {2,3};
        int ultima = 0;
        int ultimaPL = 0;
        for(int i = 0; i <= 5000; i++){
            double[] resPL = newtonAscensoPL(cuadratica, x);
            if(xPL[0] != resPL[0] && xPL[1] != resPL[1])
                ultimaPL = i;
            xPL = resPL;

            double[] res = newtonAscenso(cuadratica, x);
            if(x[0] != res[0] && x[1] != res[1])
                ultima = i;
            x = res;
            //System.out.println("It:" + i + " -> " + res[0] + " :: " + res[1]);
        }
        System.out.println("Ultima Iteración con cambio Power Law: "+ ultimaPL);
        System.out.println("Resultado Power Law: "+ xPL[0] + " :: "+xPL[1]);
        System.out.println();
        System.out.println("Ultima Iteración con cambio: "+ ultima);
        System.out.println("Resultado: "+ x[0] + " :: "+x[1]);*/


//        DiffCuadratica cuadratica = new DiffCuadratica();
//
//        double[] res = newtonRaphson(cuadratica);
//        System.out.println(res[0] + " :: " + res[1]);
    }
}
