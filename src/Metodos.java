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

    public static double[] gradienteDescendente(FuncionMultiDimDiff f, double[] x) {
        double[] grad = f.gradiente(x);
        double h = 0.4; //Tamaño del paso

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
        return n>=1?(1-(n-1))*Math.exp(1/(1-alpha)):-(1-n)*Math.exp(1/(1-alpha));
    }

    public static double[] gradienteDescendenteConMomentum(FuncionMultiDimDiff f, double[] delta, double[] x) {
        double[] grad = f.gradiente(x);
        double alpha = 0.1; //Tamaño del paso (antes del momentum)
        double m = 0.8; //Momentum

        double[] y = x.clone();

        for(int i = 0; i < y.length; i++) {
            delta[i] = m * delta[i] - grad[i] * alpha;
            y[i] += delta[i];
        }

        return y;
    }

    public static double[] ascensoALaColina(FuncionMultiDim f, double[] x) {
        double sigma = .2;

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

    public static double[] ascensoALaColinaConPowerLaw(FuncionMultiDim f, double[] x) {
        double alpha = 2;

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

    public static double[] templadoSimulado(FuncionMultiDim f, double[] x, double T){
        double sigma = .2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randGauss(sigma);
            y[i] += ran;
        }

        double fx = f.f(x);
        double fy = f.f(y);

        if (fy <= fx  && f.factible(y)) {
            return y;
        } else if(Metodos.rTemplado.nextDouble() < Math.exp((fy-fx)/T) &&  f.factible(y)){
            return y;
        }

        return x;
    }

    public static double[] templadoSimuladoConPowerLaw(FuncionMultiDim f, double[] x, double T){
        double alpha = 2;

        double[] y = x.clone();

        for (int i = 0; i < y.length; i++) {
            double ran = Metodos.randPowerLaw(alpha);
            y[i] += ran;
        }

        double fx = f.f(x);
        double fy = f.f(y);

        if (fy <= fx  && f.factible(y)) {
            return y;
        } else if(Metodos.rTemplado.nextDouble() < Math.exp((fy-fx)/T) &&  f.factible(y)){
            return y;
        }

        return x;
    }

    public static double enfriamientoSigmoide(double t, double N, double r){
        // r debe ser mayor que 0 es cuan "recta" es la función, siendo 1 muy recta, y cercana a cero muy curva.
        return (1/(1+Math.exp((t-N/2)/(N*r)))  - 1/(1+Math.exp((N/2)/(N*r)))  )*(( ( 1 - 1/N ) /(1/(1+Math.exp((-1000)/(N*r))) - 1/(1+Math.exp((N/2)/(N*r))) ))) + 1/N;
    }

    public static double enfriamientoLineal(double t, double N){
        return -1/N * t + 1;
    }

    public static double enfriamientoNewton(double t, double N, double r){
        // r debe sec cercano al log de N para que al final la probabilidad en t=N sea de 1/N
        return Math.exp(-t * r / N);
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

    public static void experimentoGradienteDescendente(FuncionMultiDimDiff f, int experimentos, int iteraciones, int dimensiones, String archivo){
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
                    x = gradienteDescendente(f, x);
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

    public static void experimentoGradienteDescendenteConMomentum(FuncionMultiDimDiff f, int experimentos, int iteraciones, int dimensiones, String archivo){
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
                    x = gradienteDescendenteConMomentum(f, delta, x);
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

    public static void experimentoAscensoALaColina(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo){
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
                    x = ascensoALaColina(f, x);
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

    public static void experimentoAscensoALaColinaConPowerLaw(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo){
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
                    x = ascensoALaColinaConPowerLaw(f, x);
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

    public static void experimentoTempladoSimuladoSigmoide(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo){
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
                    x = templadoSimulado(f, x, T);
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

    public static void experimentoTempladoSimuladoLineal(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo){
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
                    x = templadoSimulado(f, x, T);
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

    public static void experimentoTempladoSimuladoNewton(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo){
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
                    x = templadoSimulado(f, x, T);
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


    public static void experimentoTempladoSimuladoPLSigmoide(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo){
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
                    x = templadoSimuladoConPowerLaw(f, x, T);
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

    public static void experimentoTempladoSimuladoPLLineal(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, String archivo){
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
                    x = templadoSimuladoConPowerLaw(f, x, T);
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

    public static void experimentoTempladoSimuladoPLNewton(FuncionMultiDim f, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperatura, String archivo){
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
                    x = templadoSimuladoConPowerLaw(f, x, T);
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


    public static void conjuntoDeExperimentos(FuncionMultiDimDiff funcion, int experimentos, int iteraciones, int dimensiones, double coeficienteDeTemperaturaSigmoide, double coeficienteDeTemperaturaNewton, String nombreFuncion){

        System.out.println("Ejecutando: " + nombreFuncion);

        experimentoGradienteDescendente(funcion,experimentos,iteraciones,dimensiones,"resultadosGradienteDesc"+nombreFuncion+".txt");
        experimentoGradienteDescendenteConMomentum(funcion,experimentos,iteraciones,dimensiones,"resultadosGradienteDescMomentum"+nombreFuncion+".txt");
        experimentoAscensoALaColina(funcion,experimentos,iteraciones,dimensiones,"resultadosAscensoColina"+nombreFuncion+".txt");
        experimentoAscensoALaColinaConPowerLaw(funcion,experimentos,iteraciones,dimensiones,"AscensoColinaPL"+nombreFuncion+".txt");

        experimentoTempladoSimuladoSigmoide(funcion,experimentos,iteraciones,dimensiones,0.2,"resultadosTSSigmoide"+nombreFuncion+".txt");
        experimentoTempladoSimuladoLineal(funcion,experimentos,iteraciones,dimensiones,"resultadosTSLineal"+nombreFuncion+".txt");
        experimentoTempladoSimuladoNewton(funcion,experimentos,iteraciones,dimensiones,Math.log(iteraciones),"resultadosTSNewton"+nombreFuncion+".txt");
        experimentoTempladoSimuladoPLSigmoide(funcion,experimentos,iteraciones,dimensiones,0.2,"resultadosTSPLSigmoide"+nombreFuncion+".txt");
        experimentoTempladoSimuladoPLLineal(funcion,experimentos,iteraciones,dimensiones,"resultadosTSPLLineal"+nombreFuncion+".txt");
        experimentoTempladoSimuladoPLNewton(funcion,experimentos,iteraciones,dimensiones,Math.log(iteraciones),"resultadosTSPLNewton"+nombreFuncion+".txt");
    }

    public static void main(String[] args) {

        int experimentos = 100;
        int iteraciones = 1000;
        int dimensiones = 2;

        Cuadratica cuadratica = new Cuadratica();
        experimentoGradienteNewton(cuadratica, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Cuadratica" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(cuadratica,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Cuadratica" + dimensiones + "d");

        Rastrigin rastrigin = new Rastrigin();
        experimentoGradienteNewton(rastrigin, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Rastrigin" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(rastrigin,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rastrigin" + dimensiones + "d");

        Grienwank grienwank = new Grienwank();
        conjuntoDeExperimentos(grienwank,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Grienwank" + dimensiones + "d");

        Schuefel schuefel = new Schuefel();
        experimentoGradienteNewton(schuefel, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Schuefel" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(schuefel,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Schuefel" + dimensiones + "d");

        Rosenbrock rosenbrock = new Rosenbrock();
        experimentoGradienteNewton(rosenbrock, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Rosenbrock" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(rosenbrock,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rosenbrock" + dimensiones + "d");

        experimentos = 30;
        iteraciones = 10000;
        dimensiones = 10;

        experimentoGradienteNewton(cuadratica, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Cuadratica" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(cuadratica,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Cuadratica" + dimensiones + "d");

        experimentoGradienteNewton(rastrigin, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Rastrigin" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(rastrigin,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rastrigin" + dimensiones + "d");

        conjuntoDeExperimentos(grienwank,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Grienwank" + dimensiones + "d");

        experimentoGradienteNewton(schuefel, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Schuefel" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(schuefel,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Schuefel" + dimensiones + "d");

        experimentoGradienteNewton(rosenbrock, experimentos, iteraciones, dimensiones, "resultadosGradienteNewton" + "Rosenbrock" + dimensiones + "d" + ".txt");
        conjuntoDeExperimentos(rosenbrock,experimentos,iteraciones,dimensiones,0.2,Math.log(iteraciones),"Rosenbrock" + dimensiones + "d");
    }
}
