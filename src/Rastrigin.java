public class Rastrigin implements FuncionMultiDimDiff2 {
    public double pen[] = {5,5,5,5,5,5,5,5,5,5};

    public double xmin = -5.12;
    public double xmax = 5.12;

    public double f(double[] x) {
        // x debe tener n dimensiones
        int A = 10;
        double xmin = -5.12, xmax = 5.12;
        double sumatoria = 0;
        for (int i = 0; i<x.length; i++){
            sumatoria += (Math.pow(x[i],2) - A*Math.cos(2*Math.PI*x[i]));
        }
        return A*x.length - sumatoria;
    }

    public double fConPenalizacion(double[] x) {
        // x debe tener n dimensiones
        int A = 10;
        double xmin = -5.12, xmax = 5.12;
        double sumatoria = 0;
        for (int i = 0; i<x.length; i++){
            sumatoria += (Math.pow(x[i],2) - A*Math.cos(2*Math.PI*x[i])) + pen[i]*((x[i]<xmin) ? (xmin - x[i]) : ((xmax < x[i]) ? (x[i]-xmax) : 0));
        }
        return A*x.length - sumatoria;
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
            gradiente[i] = 2*x[i] + 20*Math.PI*Math.sin(2*Math.PI*x[1]) + pen[i]*((x[i]<xmin) ? -1 : ((xmax < x[i]) ? 1 : 0));
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
            hessiano[i][i] = 40*Math.pow(Math.PI,2)*Math.cos(2*Math.PI*x[i]) + 2;
        }
        return hessiano;
    }

    public double determinanteDelHessiano(double[] x){
        return ManejadorDeMatrices.determinante(hessiano(x));
    }

    //Dado que se evidencia que la función no es convexa en todos sus puntos, no se determina el Hessiano, puesto que no va a usarse.
}
