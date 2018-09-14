public interface FuncionMultiDimDiff extends FuncionMultiDim{
    public double[] gradiente(double[] x);
    public double fConPenalizacion(double[] x);
}
