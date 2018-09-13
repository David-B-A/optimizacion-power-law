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

    public double determinanteDelHessiano(double[] x){
        return ManejadorDeMatrices.determinante(ManejadorDeMatrices.hessiano(x));
    }



    public static void main(String[] args){
        double numeros[][] = new double [4][4];

        Rosenbrock rosenbrock = new Rosenbrock();
        numeros[0][0] = 10;
        numeros[0][1] = 2;
        numeros[0][2] = 33;
        numeros[0][3] = 4;

        numeros[1][0] = 5;
        numeros[1][1] = 6;
        numeros[1][2] = 7;
        numeros[1][3] = 8;

        numeros[2][0] = 9;
        numeros[2][1] = 10;
        numeros[2][2] = 11;
        numeros[2][3] = 12;

        numeros[3][0] = 13;
        numeros[3][1] = 14;
        numeros[3][2] = 15;
        numeros[3][3] = 18;

        for (int f = 0; f < numeros.length; f++) {

            for (int c = 0; c < numeros[f].length; c++) {

                System.out.print(numeros[f][c] + " ");

            }

            System.out.println();
        }

        System.out.println("numeros.length: " + numeros.length);
        System.out.println("numeros[0].length: " + numeros[0].length);
        System.out.println("det: " + ManejadorDeMatrices.determinante(numeros));
    }

    //Dado que se evidencia que la función no es convexa en todos sus puntos, no se determina el Hessiano, puesto que no va a usarse.
}
