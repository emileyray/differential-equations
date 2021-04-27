package sample;

import java.util.ArrayList;
import java.util.function.Function;

public class ImprovedEuler implements NumericalSolution{
    private double X_from;
    private double X_to;
    private double Y_0;
    private int N;
    private double greatestLTE = 0;
    private double greatestGTE = 0;

    private double c1;
    private Function<Double, Double> f;

    public ImprovedEuler(double X_from, double X_to, double Y_0, int N) {
        c1 = 1/(Math.exp(X_from) - Y_0) - X_from;
        f = x -> -1/(c1 + x) + Math.exp(x);

        this.X_from = X_from;
        this.X_to = X_to;
        this.Y_0 = Y_0;
        this.N = N;
        setValues();
    }

    private ArrayList<Double> X = new ArrayList<>();
    private ArrayList<Double> Y = new ArrayList<>();
    private ArrayList<Double> LTE_Y = new ArrayList<>();
    private ArrayList<Double> GTE_Y = new ArrayList<>();

    public void setValues(){
        X.clear();
        X.add(X_from);
        Y.clear();
        Y.add(Y_0);
        LTE_Y.clear();
        GTE_Y.clear();
        LTE_Y.add(0.0);
        GTE_Y.add(0.0);

        double step = (X_to - X_from)/N;

        for (int i = 1; i <= N; i++){
            X.add(X.get(i-1) + step);

            double a1 = Math.exp(2*X.get(i-1));
            double b1 = Math.exp(X.get(i-1));
            double c1 = Y.get(i-1)*Y.get(i-1);
            double d1 = -2*Y.get(i-1)*Math.exp(X.get(i-1));

            double k1 = a1 + b1 + c1 + d1;

            double a2 = Math.exp(2*(X.get(i-1) + step));
            double b2 = Math.exp(X.get(i-1) + step);
            double c2 = (Y.get(i-1) + step*k1)*(Y.get(i-1) + step*k1);
            double d2 = -2*(Y.get(i-1) + step*k1)*Math.exp(X.get(i-1) + step);

            double k2 = a2 + b2 + c2 + d2;

            Y.add(Y.get(i-1) + step*(k1 + k2)/2);

            double Y_ex = f.apply(X.get(i-1));

            double a1_err = Math.exp(2*X.get(i-1));
            double b1_err = Math.exp(X.get(i-1));
            double c1_err = Y_ex*Y_ex;
            double d1_err = -2*Y_ex*Math.exp(X.get(i-1));

            double k1_err = a1_err + b1_err + c1_err + d1_err;

            double a2_err = Math.exp(2*(X.get(i-1) + step));
            double b2_err = Math.exp(X.get(i-1) + step);
            double c2_err = (Y_ex + step*k1_err)*(Y_ex + step*k1_err);
            double d2_err = -2*(Y_ex + step*k1_err)*Math.exp(X.get(i-1) + step);

            double k2_err = a2_err + b2_err + c2_err + d2_err;

            LTE_Y.add(Math.abs(Y.get(i) - (Y_ex + step*(k1_err + k2_err)/2)));
            GTE_Y.add(Math.abs(Y.get(i) - f.apply(X.get(i))));

            if (LTE_Y.get(i) > greatestLTE){
                greatestLTE = LTE_Y.get(i);
            }

            if (GTE_Y.get(i) > greatestGTE){
                greatestGTE = GTE_Y.get(i);
            }
        }
    }

    public ArrayList<Double> getX(){
        return X;
    }

    public ArrayList<Double> getY(){
        return Y;
    }

    public ArrayList<Double> getLTE_Y() {return LTE_Y;}

    public ArrayList<Double> getGTE_Y() { return GTE_Y; }

    public double getGreatestLTE() {
        return greatestLTE;
    }

    public double getGreatestGTE() {
        return greatestGTE;
    }
}