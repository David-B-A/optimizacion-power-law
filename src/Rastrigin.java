public class Rastrigin implements FuncionMultiDimDiff2 {
    public double pen1 = 5;
    public double pen2 = 8;
    public double pen3 = 500;



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

    public boolean factible(double[] x) {
        boolean factible = true;
        for (int i = 0; i<x.length; i++){
            if(x[i] > 5.12 || x[i] < -5.12)
                factible = false;
        }
        return factible;
    }

    public double[] gradiente (double[] x){
        double gradiente[] = new double[x.length];
        for(int i=0; i< gradiente.length;i++){
            gradiente[i] = 2*x[i] + 20*Math.PI*Math.sin(2*Math.PI*x[1]);
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
