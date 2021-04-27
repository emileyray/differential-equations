package sample;

import java.util.ArrayList;

public interface NumericalSolution {
    void setValues();

    ArrayList<Double> getX();
    ArrayList<Double> getY();

    ArrayList<Double> getLTE_Y();
    ArrayList<Double> getGTE_Y();

    double getGreatestLTE();
    double getGreatestGTE();
}
