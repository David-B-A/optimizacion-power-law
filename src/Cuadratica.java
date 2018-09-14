import java.util.Random;

public class Cuadratica implements FuncionMultiDimDiff2 {
    public double pen[] = {5,5,5,5,5,5,5,5,5,5};

    public double xmin = -5;
    public double xmax = 10;

    public double f(double[] x) {
        // x debe tener n dimensiones
        double sumatoria = 0;
        for (int i = 0; i<x.length; i++){
            sumatoria += Math.pow(x[i],2);
        }
        return sumatoria;
    }

    public double fConPenalizacion(double[] x) {
        // x debe tener n dimensiones
        double sumatoria = 0;
        for (int i = 0; i<x.length; i++){
            sumatoria += Math.pow(x[i],2) + pen[i]*((x[i]<xmin) ? (xmin - x[i]) : ((xmax < x[i]) ? (x[i]-xmax) : 0));
        }
        return sumatoria;
    }

    public void inicializar(double[] x){
        Random r = new Random();
        for(int i= 0;i<x.length;i++){
            x[i] = r.nextDouble() * (xmax-xmin) + xmin;
        }
    }

    public boolean factible(double[] x) {
        for (int i = 0; i<x.length; i++){
            if(x[i] > xmax || x[i] < xmin)
                return false;
        }
        return true;
    }

    public double[] gradiente (double[] x){
        double gradiente[] = new double[x.length];
        for(int i=0; i< gradiente.length;i++){
            gradiente[i] = 2*x[i] + pen[i]*((x[i]<xmin) ? -1 : ((xmax < x[i]) ? 1 : 0));
        }
        return gradiente;
    }

    public double[][] hessiano (double[] x){
        double[][] hessiano = new double[x.length][x.length];
        for(int i = 0;i<x.length;i++){
            for(int j = 0;j<x.length;j++) {
                hessiano[i][j] = 0;
            }
        }
        for(int i = 0;i<x.length;i++){
            hessiano[i][i] = 2;
        }
        return hessiano;
    }

    public double determinanteDelHessiano(double[] x){
        return ManejadorDeMatrices.determinante(hessiano(x));
    }
}
