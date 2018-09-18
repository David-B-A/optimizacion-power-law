import java.util.Random;

public class Grienwank implements FuncionMultiDimDiff {
    public double pen[] = {90,90,90,90,90,90,90,90,90,90};

    public double xmin = -600;
    public double xmax = 600;

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

    public double fConPenalizacion(double[] x) {
        // x debe tener n dimensiones
        double sumatoria = 0;
        double productoria = 1;
        for (int i = 0; i<x.length; i++){
            sumatoria += Math.pow(x[i],2)/4000 + pen[i]*((x[i]<xmin) ? (xmin - x[i]) : ((xmax < x[i]) ? (x[i]-xmax) : 0));
        }
        for (int i = 0; i<x.length; i++){
            productoria *= Math.cos(x[i]/Math.sqrt(i+1));
        }
        return sumatoria - productoria +1;
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
        double coefProductoria;
        for(int i=0; i< gradiente.length;i++){
            coefProductoria = 1;
            for (int j = 0; j<gradiente.length; j++){
                if(j != i){
                    coefProductoria *= Math.cos(x[j]/Math.sqrt(j+1));
                }
            }
            gradiente[i] = x[i]/2000 + coefProductoria*Math.sin(x[i]/Math.sqrt(i+1))/Math.sqrt(i+1) + pen[i]*((x[i]<xmin) ? -1 : ((xmax < x[i]) ? 1 : 0));
        }
        return gradiente;
    }

    //Dado que se evidencia que la función no es convexa en todos sus puntos, no se determina el Hessiano, puesto que no va a usarse.
}
