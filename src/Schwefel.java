import java.util.Random;

public class Schwefel implements FuncionMultiDimDiff2 {
    public double pen[] = {80,80,80,80,80,80,80,80,80,80};

    public double xmin = -500;
    public double xmax = 500;

    public double f(double[] x) {
        // x debe tener n dimensiones
        double A = 418.9829;
        double xmin = -5.12, xmax = 5.12;
        double sumatoria = 0;
        for (int i = 0; i<x.length; i++){
            sumatoria += x[i]*Math.sin(Math.sqrt(Math.abs(x[i])));
        }
        return A*x.length - sumatoria;
    }

    public double fConPenalizacion(double[] x) {
        // x debe tener n dimensiones
        int A = 10;
        double xmin = -5.12, xmax = 5.12;
        double sumatoria = 0;
        for (int i = 0; i<x.length; i++){
            sumatoria += x[i]*Math.sin(Math.sqrt(Math.abs(x[i]))) + pen[i]*((x[i]<xmin) ? (xmin - x[i]) : ((xmax < x[i]) ? (x[i]-xmax) : 0));
        }
        return A*x.length - sumatoria;
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
            gradiente[i] = - Math.sin(Math.sqrt(Math.abs(x[i]))) - (x[1]*Math.cos(Math.sqrt(Math.abs(x[i])))*sign(x[i]))/(2*Math.sqrt(Math.abs(x[i]))) + pen[i]*((x[i]<xmin) ? -1 : ((xmax < x[i]) ? 1 : 0));
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
            hessiano[i][i] = x[i]!=0 ? (x[i]*Math.sin(Math.pow(Math.abs(x[i]),(1/2)))*Math.pow(sign(x[i]),2))/(4*Math.abs(x[i])) - (Math.cos(Math.pow(Math.abs(x[i]),(1/2)))*sign(x[i]))/Math.pow(Math.abs(x[i]),(1/2)) + (x[i]*Math.cos(Math.pow(Math.abs(x[i]),(1/2)))*Math.pow(sign(x[i]),2))/(4*Math.pow(Math.abs(x[i]),(3/2))) : 0;
        }
        return hessiano;
    }

    public double determinanteDelHessiano(double[] x){
        return ManejadorDeMatrices.determinante(hessiano(x));
    }

    private int sign(double x){
        return (x < 0) ? -1 : ((x == 0) ? 0 : 1);
    }

    //Dado que se evidencia que la función no es convexa en todos sus puntos, no se determina el Hessiano, puesto que no va a usarse.
}
