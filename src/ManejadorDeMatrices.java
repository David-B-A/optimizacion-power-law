public class ManejadorDeMatrices {


    public static double determinante(double[][] x){
        double determinante = 0;
        if(x.length > 2){
            double[][] matTemp = new double[x.length-1][x.length-1];
            for(int i = 0;i<x.length; i++){
                if(i%2 == 0){
                    determinante += x[0][i] * determinante(adjunto(x,i));
                }
                else{
                    determinante -= x[0][i] * determinante(adjunto(x,i));
                }
            }
        } else{
            determinante = x[0][0]*x[1][1] - x[0][1]*x[1][0];
        }
        return determinante;
    }
    public static double[][] adjunto(double[][] x, int i){
        double[][] adj = new double[x.length-1][x.length-1];
        int cont = 0;
        for(int a = 0;a<x.length;a++){
            if(a != i){
                for(int k = 1;k< x.length; k++){
                    adj[cont][k-1] = x[k][a];
                }
                cont++;
            }
        }
        return adj;
    }
}
