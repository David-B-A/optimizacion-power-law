public class Schuefel implements FuncionMultiDimDiff {
    public double pen1 = 5;
    public double pen2 = 8;
    public double pen3 = 500;



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

    public boolean factible(double[] x) {
        boolean factible = true;
        for (int i = 0; i<x.length; i++){
            if(x[i] > 500 || x[i] < -500)
                factible = false;
        }
        return factible;
    }

    public double[] gradiente (double[] x){
        double gradiente[] = new double[x.length];
        for(int i=0; i< gradiente.length;i++){
            gradiente[i] = - Math.sin(Math.sqrt(Math.abs(x[i]))) - (x[1]*Math.cos(Math.sqrt(Math.abs(x[i])))*sign(x[i]))/(2*Math.sqrt(Math.abs(x[i])));
        }
        return gradiente;
    }

    private int sign(double x){
        return (x < 0) ? -1 : ((x == 0) ? 0 : 1);
    }
    
    //Dado que se evidencia que la función no es convexa en todos sus puntos, no se determina el Hessiano, puesto que no va a usarse.
}
