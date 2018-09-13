public class Grienwank implements FuncionMultiDimDiff {
    public double pen1 = 5;
    public double pen2 = 8;
    public double pen3 = 500;



    public double f(double[] x) {
        // x debe tener n dimensiones
        double sumatoria = 0;
        double productoria = 1;
        for (int i = 0; i<x.length; i++){
            sumatoria += Math.pow(x[i],2)/4000;
        }
        for (int i = 0; i<x.length; i++){
            productoria *= Math.cos(x[i]/Math.sqrt(i+1));
        }
        return sumatoria - productoria +1;
    }

    public boolean factible(double[] x) {
        boolean factible = true;
        for (int i = 0; i<x.length; i++){
            if(x[i] > 600 || x[i] < -600)
                factible = false;
        }
        return factible;
    }

    public double[] gradiente (double[] x){
        double gradiente[] = new double[x.length];
        double coefProductoria;
        for(int i=0; i< gradiente.length;i++){
            coefProductoria = 1;
            for (int j = 0; j<gradiente.length; j++){
                if(j != i){
                    coefProductoria *= Math.cos(x[j]/Math.sqrt(j+1));
                }
            }
            gradiente[i] = x[i]/2000 + coefProductoria*Math.sin(x[i]/Math.sqrt(i+1))/Math.sqrt(i+1);
        }
        return gradiente;
    }

    //Dado que se evidencia que la función no es convexa en todos sus puntos, no se determina el Hessiano, puesto que no va a usarse.
}
