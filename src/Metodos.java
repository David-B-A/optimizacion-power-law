import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class Metodos {

    public static Random r = new Random();
    public static Random rTemplado = new Random();

    public static double[] gradienteNewton(FuncionMultiDimDiff2 f, double[] x) {
        double[] grad = f.gradiente(x);
        double h = f.determinanteDelHessiano(x);

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            y[i] += (-grad[i]) / h;
        }

        return y;
    }

    public static double[] gradienteDescendente(FuncionMultiDimDiff f, double[] x, double h) {
        double[] grad = f.gradiente(x);

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            y[i] += (-grad[i]) * h;
        }

        return y;
    }

    public static double randGauss(double sigma) {
        return Metodos.r.nextGaussian() * sigma;
    }

    public static double randPowerLaw(double alpha) {
        double n = Metodos.r.nextDouble()*2;
        return n>=1?Math.pow((2-n),(1/(1-alpha))):-Math.pow((n),(1/(1-alpha)));
    }

    public static double[] gradienteDescendenteConMomentum(FuncionMultiDimDiff f, double[] delta, double[] x, double h, double m) {
        double[] grad = f.gradiente(x);

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            delta[i] = m * delta[i] - grad[i] * h;
            y[i] += delta[i];
        }

        return y;
    }

    public static double[] ascensoALaColina(FuncionMultiDim f, double[] x, double sigma) {

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randGauss(sigma);
            y[i] += ran;
        }

        if (f.f(x) < f.f(y) || !f.factible(y)) {
            return x;
        }

        return y;
    }

    public static double[] ascensoALaColinaConPowerLaw(FuncionMultiDim f, double[] x, double alpha) {

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randPowerLaw(alpha);
            y[i] += ran;
        }

        if (f.f(x) < f.f(y)  || !f.factible(y)) {
            return x;
        }

        return y;
    }

    public static double[] templadoSimulado(FuncionMultiDim f, double[] x, double T, double sigma){
        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randGauss(sigma);
            y[i] += ran;
        }

        double fx = f.f(x);
        double fy = f.f(y);


        if (fy <= fx  && f.factible(y)) {
            return y;
        } else if(Metodos.rTemplado.nextDouble() < Math.exp(-((fy-fx))/T) &&  f.factible(y)){
            return y;
        }

        return x;
    }

    public static double[] templadoSimuladoConPowerLaw(FuncionMultiDim f, double[] x, double T, double alpha){

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randPowerLaw(alpha);
            y[i] += ran;
        }

        double fx = f.f(x);
        double fy = f.f(y);

        if (fy <= fx  && f.factible(y)) {
            return y;
        } else if(Metodos.rTemplado.nextDouble() < Math.exp(-((fy-fx))/T) &&  f.factible(y)){
            return y;
        }

        return x;
    }

    public static double enfriamientoSigmoide(double t, double N, double r){
        // r debe ser mayor que 0 es cuan "recta" es la función, siendo 1 muy recta, y cercana a cero muy curva.
        return (1/(1+Math.exp((t-N/2)/(N*r)))  - 1/(1+Math.exp((N/2)/(N*r)))  )*(( ( 1 - 1/N ) /(1/(1+Math.exp((-N/2)/(N*r))) - 1/(1+Math.exp((N/2)/(N*r))) ))) + 1/N;
    }

    public static double enfriamientoLineal(double t, double N){
        return -1/N * t + 1;
    }

    public static double enfriamientoNewton(double t, double N, double r){
        // r debe sec cercano al log de N para que al final la probabilidad en t=N sea de 1/N
        return Math.exp((-t /(N*r*2))) * (1-1/N)/(1-Math.exp((-1/(r*2)))) + 1/N;
    }



    public static void experimentoGradienteNewton(FuncionMultiDimDiff2 f, int experimentos, int iteraciones, int dimensiones, String archivo){
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                for (int j = 0; j < iteraciones; j++) {
                    x = gradienteNewton(f, x);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoGradienteDescendente(FuncionMultiDimDiff f, int experimentos, int iteraciones, int dimensiones, String archivo, double h){
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                for (int j = 0; j < iteraciones; j++) {
                    x = gradienteDescendente(f, x, h);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoGradienteDescendenteConMomentum(FuncionMultiDimDiff f, int experimentos, int iteraciones, int dimensiones, String archivo, double h, double m){
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                double[] delta = new double[dimensiones];
                for(int d = 0;d<dimensiones;d++){
                    delta[d] = 0;
                }
                for (int j = 0; j < iteraciones; j++) {
                    x = gradienteDescendenteConMomentum(f, delta, x, h, m);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoAscensoALaColina(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo, double sigma){
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                for (int j = 0; j < iteraciones; j++) {
                    x = ascensoALaColina(f, x,sigma);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoAscensoALaColinaConPowerLaw(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo, double alpha){
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                for (int j = 0; j < iteraciones; j++) {
                    x = ascensoALaColinaConPowerLaw(f, x, alpha);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoTempladoSimuladoSigmoide(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo, double sigma){
        //sugerido: 0 < coeficienteDeTemperatura <= 1, siendo 1 casi una linea recta, y entre más próximo a 0, más curva.
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                double T = 1;
                for (int j = 0; j < iteraciones; j++) {
                    T = enfriamientoSigmoide(j,iteraciones,coeficienteDeTemperatura);
                    x = templadoSimulado(f, x, T, sigma);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoTempladoSimuladoLineal(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo, double sigma){
        //sugerido: 0 < coeficienteDeTemperatura <= 1, siendo 1 casi una linea recta, y entre más próximo a 0, más curva.
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                double T = 1;
                for (int j = 0; j < iteraciones; j++) {
                    T = enfriamientoLineal(j,iteraciones);
                    x = templadoSimulado(f, x, T, sigma);
                    resultados[i][j] = f.f(x);
                }

            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoTempladoSimuladoNewton(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo, double sigma){
        //sugerido: r = log(N) para que la temperatura de la última iteración sea 1/N.
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                double T = 1;
                for (int j = 0; j < iteraciones; j++) {
                    T = enfriamientoNewton(j,iteraciones,coeficienteDeTemperatura);
                    x = templadoSimulado(f, x, T, sigma);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }


    public static void experimentoTempladoSimuladoPLSigmoide(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo, double alpha){
        //sugerido: 0 < coeficienteDeTemperatura <= 1, siendo 1 casi una linea recta, y entre más próximo a 0, más curva.
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                double T = 1;
                for (int j = 0; j < iteraciones; j++) {
                    T = enfriamientoSigmoide(j,iteraciones,coeficienteDeTemperatura);
                    x = templadoSimuladoConPowerLaw(f, x, T, alpha);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoTempladoSimuladoPLLineal(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo, double alpha){
        //sugerido: 0 < coeficienteDeTemperatura <= 1, siendo 1 casi una linea recta, y entre más próximo a 0, más curva.
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                double T = 1;
                for (int j = 0; j < iteraciones; j++) {
                    T = enfriamientoLineal(j,iteraciones);
                    x = templadoSimuladoConPowerLaw(f, x, T, alpha);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }

    public static void experimentoTempladoSimuladoPLNewton(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo, double alpha){
        //sugerido: r = log(N) para que la temperatura de la última iteración sea 1/N.
        try {
            double[][] resultados = new double[experimentos][iteraciones];
            File archivoresultados = new File(archivo);
            if (archivoresultados.exists()) {
                archivoresultados.delete();
                archivoresultados = new File(archivo);
            }
            FileWriter writerresultados = new FileWriter(archivoresultados, true);


            for (int i = 0; i < experimentos; i++) {
                Metodos.r = new Random();
                double[] x = new double[dimensiones];
                f.inicializar(x);
                double T = 1;
                for (int j = 0; j < iteraciones; j++) {
                    T = enfriamientoNewton(j,iteraciones,coeficienteDeTemperatura);
                    x = templadoSimuladoConPowerLaw(f, x, T,alpha);
                    resultados[i][j] = f.f(x);
                }


            }
            for (int i = 0; i < experimentos; i++) {
                writerresultados.write("Experimento_" + (i+1) + ";");
            }
            writerresultados.write("\n");
            for (int j = 0; j < iteraciones; j++) {
                for (int i = 0; i < experimentos; i++) {
                    writerresultados.write(resultados[i][j] + ";");
                }
                writerresultados.write("\n");
            }
        } catch (Exception e){
            System.out.println("Imposible realizar operación para " +archivo+" - Exception: "+ e.getMessage());
        }
    }


    public static void conjuntoDeExperimentos(FuncionMultiDimDiff funcion, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperaturaSigmoide, double coeficienteDeTemperaturaNewton, String nombreFuncion, double h, double m, double sigma, double alpha){

        System.out.println("Ejecutando: " + nombreFuncion);

        //experimentoGradienteDescendente(funcion,experimentos,iteraciones,dimensiones,nombreFuncion+"resultadosGradienteDesc"+".txt",h);
        //experimentoGradienteDescendenteConMomentum(funcion,experimentos,iteraciones,dimensiones,nombreFuncion+"resultadosGradienteDescMomentum"+".txt",h,m);
        experimentoAscensoALaColina(funcion,experimentos,iteraciones,dimensiones,nombreFuncion+"resultadosAscensoColina"+".txt",sigma);
        experimentoAscensoALaColinaConPowerLaw(funcion,experimentos,iteraciones,dimensiones,nombreFuncion+"AscensoColinaPL"+".txt",alpha);

        experimentoTempladoSimuladoSigmoide(funcion,experimentos,iteraciones,dimensiones,0.1,nombreFuncion+"resultadosTSSigmoide"+".txt",sigma);
        experimentoTempladoSimuladoLineal(funcion,experimentos,iteraciones,dimensiones,nombreFuncion+"resultadosTSLineal"+".txt",sigma);
        experimentoTempladoSimuladoNewton(funcion,experimentos,iteraciones,dimensiones,0.1,nombreFuncion+"resultadosTSNewton"+".txt",sigma);
        experimentoTempladoSimuladoPLSigmoide(funcion,experimentos,iteraciones,dimensiones,0.1,nombreFuncion+"resultadosTSPLSigmoide"+".txt",alpha);
        experimentoTempladoSimuladoPLLineal(funcion,experimentos,iteraciones,dimensiones,nombreFuncion+"resultadosTSPLLineal"+".txt",alpha);
        experimentoTempladoSimuladoPLNewton(funcion,experimentos,iteraciones,dimensiones,0.1,nombreFuncion+"resultadosTSPLNewton"+".txt",alpha);
    }

    public static void main(String[] args) {

        int experimentos = 100;
        int iteraciones = 1000;
        int dimensiones = 2;

        Cuadratica cuadratica = new Cuadratica(); //prueba
        Rastrigin rastrigin = new Rastrigin(); // -5.12, 5.12
        Grienwank grienwank = new Grienwank(); // -600,600
        Schwefel schwefel = new Schwefel(); // -500,500
        Rosenbrock rosenbrock = new Rosenbrock(); //-5,10

        double h,m,sigma,alpha; // h -> paso del gradiente, m -> momentum gradiente, sigma -> st. deviation gaussiana, alpha -> power law

        //a menor alpha mas grandes los numeros que se generan con power law
/*
        h = 0.6;m = 0.7;sigma = 0.2;alpha = 7;
        //conjuntoDeExperimentos(cuadratica,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Cuadratica" + dimensiones + "d",h,m,sigma,alpha);
        h = 0.6;m = 0.7;sigma = 0.2;alpha = 7;
        conjuntoDeExperimentos(rastrigin,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rastrigin" + dimensiones + "d",h,m,sigma,alpha);
        h = 1;m = 1;sigma = 4;alpha = 1.8;
        conjuntoDeExperimentos(grienwank,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Grienwank" + dimensiones + "d",h,m,sigma,alpha);
        h = 1;m = 1;sigma = 3;alpha = 1.9;
        conjuntoDeExperimentos(schwefel,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Schwefel" + dimensiones + "d",h,m,sigma,alpha);
        h = 0.00005;m = 0.09;sigma = 0.2;alpha = 7;
        conjuntoDeExperimentos(rosenbrock,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rosenbrock" + dimensiones + "d",h,m,sigma,alpha);

*/
        //experimentoGradienteNewton(cuadratica, experimentos, iteraciones, dimensiones, "Cuadratica" + dimensiones + "d" + "resultadosGradienteNewton" + ".txt");
        experimentoGradienteNewton(rastrigin, experimentos, iteraciones, dimensiones, "Rastrigin" + dimensiones + "d" +  "resultadosGradienteNewton" + ".txt");
        //experimentoGradienteNewton(schwefel, experimentos, iteraciones, dimensiones, "Schwefel" + dimensiones + "d" +"resultadosGradienteNewton" +  ".txt");
        //experimentoGradienteNewton(rosenbrock, experimentos, iteraciones, dimensiones, "Rosenbrock" + dimensiones + "d" + "resultadosGradienteNewton" + ".txt");


/*
        experimentos = 100;
        iteraciones = 10000;
        dimensiones = 10;

        h = 0.6;m = 0.7;sigma = 0.2;alpha = 7;
        //conjuntoDeExperimentos(cuadratica,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Cuadratica" + dimensiones + "d",h,m,sigma,alpha);
        h = 0.6;m = 0.7;sigma = 0.2;alpha = 7;
        conjuntoDeExperimentos(rastrigin,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rastrigin" + dimensiones + "d",h,m,sigma,alpha);
        h = 1;m = 1;sigma = 4;alpha = 1.8;
        conjuntoDeExperimentos(grienwank,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Grienwank" + dimensiones + "d",h,m,sigma,alpha);
        h = 1;m = 1;sigma = 3;alpha = 1.9;
        conjuntoDeExperimentos(schwefel,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Schwefel" + dimensiones + "d",h,m,sigma,alpha);
        h = 0.00005;m = 0.09;sigma = 0.2;alpha = 7;
        conjuntoDeExperimentos(rosenbrock,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rosenbrock" + dimensiones + "d",h,m,sigma,alpha);
*/

        //experimentoGradienteNewton(cuadratica, experimentos, iteraciones, dimensiones, "Cuadratica" + dimensiones + "d" +"resultadosGradienteNewton" +  ".txt");

        /*
        experimentoGradienteNewton(rastrigin, experimentos, iteraciones, dimensiones, "Rastrigin" + dimensiones + "d" + "resultadosGradienteNewton" + ".txt");
        experimentoGradienteNewton(schuefel, experimentos, iteraciones, dimensiones, "Schwefel" + dimensiones + "d" + "resultadosGradienteNewton" +  ".txt");
        experimentoGradienteNewton(rosenbrock, experimentos, iteraciones, dimensiones, "Rosenbrock" + dimensiones + "d" + "resultadosGradienteNewton" + ".txt");
*/

    }
}
