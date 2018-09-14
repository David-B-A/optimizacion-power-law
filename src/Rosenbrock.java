public class Rosenbrock implements FuncionMultiDimDiff2 {
    public double pen1 = 5;
    public double pen2 = 8;
    public double pen3 = 500;



    public double f(double[] x) {
        // x debe tener n dimensiones
        double sumatoria = 0;
        for (int i = 0; i<x.length-1; i++){
            sumatoria += 100*Math.pow((x[i+1] - Math.pow(x[i],2)),2) + Math.pow((x[i]-1),2);
        }
        return sumatoria;
    }

    public boolean factible(double[] x) {
        boolean factible = true;
        for (int i = 0; i<x.length; i++){
            if(x[i] > 10 || x[i] < -5)
                factible = false;
        }
        return factible;
    }

    public double[] gradiente (double[] x){
        double gradiente[] = new double[x.length];
        for(int i=0; i< gradiente.length - 1;i++){
            gradiente[i] = 2*x[i] - 400*x[i]*(- Math.pow(x[i],2) + x[i+1]) - 2;
        }
        gradiente[gradiente.length - 1] = - 200*Math.pow(x[x.length-2],2) + 200*x[x.length - 1];
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
