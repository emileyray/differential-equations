package sample;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.function.Function;

public class MyGraph {

    final private LineChart<Double, Double> graph;
    private double from;
    private double to;
    private int steps;
    private XYChart.Series<Double, Double> exact;
    private XYChart.Series<Double, Double> euler;
    private XYChart.Series<Double, Double> improvedEuler;
    private XYChart.Series<Double, Double> rungeKutta;

    private XYChart.Series<Double, Double> euler_err;
    private XYChart.Series<Double, Double> improvedEuler_err;
    private XYChart.Series<Double, Double> rungeKutta_err;

    public MyGraph(final LineChart<Double, Double> graph, final double from, final double to, final int steps) {
        this.graph = graph;
        this.from = from;
        this.to = to;
        this.steps = steps;
    }

    public void plotLine(final Function<Double, Double> function) {
        exact = new XYChart.Series<Double, Double>();
        for (double x = from; x <= to; x = x + 0.01) {
            plotPoint(x, function.apply(x), exact);
        }
        exact.setName("Exact Solution");
        graph.getData().add(exact);
        Node line = exact.getNode().lookup(".chart-series-line");
        setColor(line, 87, 87,87);
    }

    public void plotLine(ArrayList<Double> X, ArrayList<Double> Y, int code){
        final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        for (int i = 0; i <= steps; i++){
            plotPoint(X.get(i), Y.get(i), series);
        }
        if (code == 0) {
            euler = series;
            euler.setName("Euler");
            graph.getData().add(euler);
            Node line = euler.getNode().lookup(".chart-series-line");
            setColor(line, 249, 72,34);
        }
        if (code == 1) {
            improvedEuler = series;
            improvedEuler.setName("Improved Euler");
            graph.getData().add(improvedEuler);
            Node line = improvedEuler.getNode().lookup(".chart-series-line");
            setColor(line, 35, 224,42);
        }
        if (code == 2) {
            rungeKutta = series;
            rungeKutta.setName("Runge-Kutta");
            graph.getData().add(rungeKutta);
            Node line = rungeKutta.getNode().lookup(".chart-series-line");
            setColor(line, 35, 145,224);
        }
        if (code == 4){
            euler_err = series;
            euler_err.setName("Euler LE");
            graph.getData().add(euler_err);
            Node line = euler_err.getNode().lookup(".chart-series-line");
            setColor(line, 249, 72,34);
        }

        if (code == 5){
            improvedEuler_err = series;
            improvedEuler_err.setName("Improved Euler LE");
            graph.getData().add(improvedEuler_err);
            Node line = improvedEuler_err.getNode().lookup(".chart-series-line");
            setColor(line, 35, 224,42);
        }
        if (code == 6) {
            rungeKutta_err = series;
            rungeKutta_err.setName("Runge-Kutta LE");
            graph.getData().add(rungeKutta_err);
            Node line = rungeKutta_err.getNode().lookup(".chart-series-line");
            setColor(line, 35, 145,224);
        }
    }

    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<Double, Double>(x, y));
    }

    public void remove(int code){
        if (code == 0) graph.getData().remove(euler);
        if (code == 1) graph.getData().remove(improvedEuler);
        if (code == 2) graph.getData().remove(rungeKutta);
        if (code == 3) graph.getData().remove(exact);
        if (code == 4) graph.getData().remove(euler_err);
        if (code == 5) graph.getData().remove(improvedEuler_err);
        if (code == 6) graph.getData().remove(rungeKutta_err);
    }

    public void setParameters(double from, double to, int steps){
        this.from = from;
        this.to = to;
        this.steps = steps;
    }

    public void setParameters(double from, double to){
        this.from = from;
        this.to = to;
    }

    private void setColor(Node line, int red, int green, int blue){
        line.setStyle("-fx-stroke: rgba(" + red + ", " + green + ", " +blue+ ", 1.0);");
    }
}