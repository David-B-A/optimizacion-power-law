import java.util.Random;

public class Rosenbrock implements FuncionMultiDimDiff2 {
    public double pen[] = {3,3,3,3,3,3,3,3,3,3};

    public double xmin = -5;
    public double xmax = 10;

    public double f(double[] x) {
        // x debe tener n dimensiones
        double sumatoria = 0;
        for (int i = 0; i<x.length-1; i++){
            sumatoria += 100*Math.pow((x[i+1] - Math.pow(x[i],2)),2) + Math.pow((x[i]-1),2);
        }
        return sumatoria;
    }

    public double fConPenalizacion(double[] x) {
        // x debe tener n dimensiones
        double sumatoria = 0;
        for (int i = 0; i<x.length-1; i++){
            sumatoria += 100*Math.pow((x[i+1] - Math.pow(x[i],2)),2) + Math.pow((x[i]-1),2) + pen[i]*((x[i]<xmin) ? (xmin - x[i]) : ((xmax < x[i]) ? (x[i]-xmax) : 0));
        }
        sumatoria += pen[x.length-1]*((x[x.length-1]<xmin) ? (xmin - x[x.length-1]) : ((xmax < x[x.length-1]) ? (x[x.length-1]-xmax) : 0));
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
        for(int i=0; i< gradiente.length - 1;i++){
            gradiente[i] = 2*x[i] - 400*x[i]*(- Math.pow(x[i],2) + x[i+1]) - 2 + pen[i]*((x[i]<xmin) ? -1 : ((xmax < x[i]) ? 1 : 0));
        }
        gradiente[gradiente.length - 1] = - 200*Math.pow(x[x.length-2],2) + 200*x[x.length - 1] + pen[gradiente.length - 1]*((x[gradiente.length - 1]<xmin) ? -1 : ((xmax < x[gradiente.length - 1]) ? 1 : 0));
        return gradiente;
    }

    public double[][] hessiano (double[] x){
        double[][] hessiano = new double[x.length][x.length];
        for(int i = 0;i<x.length;i++){
            for(int j = 0;j<x.length;j++) {
                hessiano[i][j] = 0;
            }
        }
        for(int i = 0;i<x.length - 1;i++){
            hessiano[i][i] = 1200*Math.pow(x[i],2) - 400*x[i+1] + 2;
            hessiano[i][i+1] = -400*x[i];
            hessiano[i+1][i] = -400*x[i];
        }
        hessiano[x.length-1][x.length-1] = 200;
        return hessiano;
    }

    public double determinanteDelHessiano(double[] x){
        return ManejadorDeMatrices.determinante(hessiano(x));
    }


}
