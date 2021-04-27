package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class Controller implements Initializable{

    public LineChart<Double, Double> GTEGraph1;
    public NumberAxis GTEY1;
    public NumberAxis GTEX1;

    public LineChart<Double, Double> LTEGraph1;
    public NumberAxis LTEY1;
    public NumberAxis LTEX1;

    public LineChart<Double, Double> lineGraph1;
    public NumberAxis Y1;
    public NumberAxis X1;

    public Circle grey1;
    public Circle blue1;
    public Circle green1;
    public Circle red1;

    public CheckBox ExactSolutionCheckBox1;
    public CheckBox RungeKuttaCheckBox1;
    public CheckBox ImprovedEulerCheckBox1;
    public CheckBox EulerCheckBox1;

    public Slider X_to1;
    public Slider X_from1;
    public Slider Y_01;

    public Slider N_to1;
    public Slider N_from1;

    private int N_from_value;
    private int N_to_value;

    public TextField X_to_textField1;
    public TextField X_from_textField1;
    public TextField Y_0_textField1;

    public TextField N_toTextField1;
    public TextField N_fromTextField1;

    private double X_from_value = -10;
    private double X_to_value = 10;
    private double Y_0_value = 0;
    private int N_steps_value = 10;

    private double X_from_value1 = -10;
    private double X_to_value1 = 10;
    private double Y_0_value1 = 0;

    private Euler euler;
    private ImprovedEuler improvedEuler;
    private RungeKutta rungeKutta;

    @FXML private LineChart<Double, Double> lineGraph;
    @FXML private LineChart<Double, Double> LTEGraph;
    @FXML private LineChart<Double, Double> GTEGraph;

    @FXML private Slider X_from;
    @FXML private Slider X_to;
    @FXML private Slider Y_0;
    @FXML private Slider N_steps;
    @FXML private Spinner<Integer> N_stepsSpinner;
    @FXML public SpinnerValueFactory.IntegerSpinnerValueFactory factory;

    @FXML private CheckBox EulerCheckBox;
    @FXML private CheckBox ImprovedEulerCheckBox;
    @FXML private CheckBox RungeKuttaCheckBox;
    @FXML private CheckBox ExactSolutionCheckBox;

    @FXML private TextField X_from_textField;
    @FXML private TextField X_to_textField;
    @FXML private TextField Y_0_textField;

    @FXML private NumberAxis X;
    @FXML private NumberAxis Y;
    @FXML public NumberAxis LTEX;
    @FXML public NumberAxis LTEY;
    @FXML public NumberAxis GTEX;
    @FXML public NumberAxis GTEY;

    private MyGraph mathsGraph;
    private MyGraph MyLTEGraph;
    private MyGraph MyGTEGraph;

    private MyGraph mathsGraph1;
    private MyGraph MyLTEGraph1;
    private MyGraph MyGTEGraph1;

    @FXML private Circle red;
    @FXML private Circle green;
    @FXML private Circle blue;
    @FXML private Circle grey;

    private ArrayList<Double> greatestLTE_Euler;
    private ArrayList<Double> greatestLTE_ImprovedEuler;
    private ArrayList<Double> greatestLTE_RungeKutta;

    private ArrayList<Double> greatestGTE_Euler;
    private ArrayList<Double> greatestGTE_ImprovedEuler;
    private ArrayList<Double> greatestGTE_RungeKutta;

    private ArrayList<Double> N_values;

    @Override
    public void initialize(URL location, ResourceBundle resources) { //initializing
        mathsGraph = new MyGraph(lineGraph,X_from_value, X_to_value, N_steps_value);
        MyLTEGraph = new MyGraph(LTEGraph, X_from_value, X_to_value, N_steps_value);
        MyGTEGraph = new MyGraph(GTEGraph, X_from_value, X_to_value, N_steps_value);

        MyLTEGraph1 = new MyGraph(LTEGraph1, X_from_value1, X_to_value1, N_steps_value);
        MyGTEGraph1 = new MyGraph(GTEGraph1, X_from_value1, X_to_value1, N_steps_value);

        mathsGraph1 = new MyGraph(lineGraph1,X_from_value1, X_to_value1, N_steps_value);

        lineGraph.setTitle("e^(2x) + e^x + y^2 - 2ye^x");
        lineGraph1.setTitle("e^(2x) + e^x + y^2 - 2ye^x");

        LTEGraph.setTitle("LTE");
        GTEGraph.setTitle("GTE");

        LTEGraph1.setTitle("Max LTE for each N");
        GTEGraph1.setTitle("Max GTE for each N");

        red.setFill(javafx.scene.paint.Paint.valueOf("#F94822"));
        green.setFill(javafx.scene.paint.Paint.valueOf("#23E02A"));
        blue.setFill(javafx.scene.paint.Paint.valueOf("#2391E0"));
        grey.setFill(javafx.scene.paint.Paint.valueOf("#575757"));

        red1.setFill(javafx.scene.paint.Paint.valueOf("#F94822"));
        green1.setFill(javafx.scene.paint.Paint.valueOf("#23E02A"));
        blue1.setFill(javafx.scene.paint.Paint.valueOf("#2391E0"));
        grey1.setFill(javafx.scene.paint.Paint.valueOf("#575757"));

        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        factory.valueProperty().addListener((arg0, arg1, arg2) -> updateN_steps(arg2));
        N_stepsSpinner.setValueFactory(factory);
    }

    private void plotLine(Function<Double, Double> function) { //plotting the graphs
        if (lineGraph.isVisible()) {
            mathsGraph.plotLine(function);
        }
    }

    private void plotLine1(Function<Double, Double> function) { //plotting the graphs
        if (lineGraph1.isVisible()) {
            mathsGraph1.plotLine(function);
        }
    }

    private void plotLine(LineChart<Double, Double> chart, MyGraph graph, ArrayList<Double> X, ArrayList<Double> Y, int code){ //plotting the graphs
        if (chart.isVisible()) {
            graph.plotLine(X, Y, code);
        }
    }

    public void handleEulerCheckBox(javafx.event.ActionEvent actionEvent) { //if euler method is selected
        if (EulerCheckBox.isSelected()){
            plotLine(lineGraph, mathsGraph, euler.getX(), euler.getY(), 0);
            plotLine(LTEGraph, MyLTEGraph, euler.getX(), euler.getLTE_Y(), 4);
            plotLine(GTEGraph, MyGTEGraph, euler.getX(), euler.getGTE_Y(), 4);
        } else {
            mathsGraph.remove(0);
            MyLTEGraph.remove(4);
            MyGTEGraph.remove(4);
        }
        update();
    }

    public void handleEulerCheckBox1(javafx.event.ActionEvent actionEvent) { //if euler method is selected
        if (EulerCheckBox.isSelected()){
            plotLine(LTEGraph1, MyLTEGraph1, N_values, greatestLTE_Euler, 6);
            plotLine(GTEGraph1, MyGTEGraph1, N_values, greatestGTE_Euler, 6);
        } else {
            MyLTEGraph1.remove(4);
            MyGTEGraph1.remove(4);
        }
        update1();
    }

    public void handleImprovedEulerCheckBox(javafx.event.ActionEvent actionEvent) { //if improved euler method is selected
        if (ImprovedEulerCheckBox.isSelected()){
            plotLine(lineGraph, mathsGraph, improvedEuler.getX(), improvedEuler.getY(), 1);
            plotLine(LTEGraph, MyLTEGraph, improvedEuler.getX(), improvedEuler.getLTE_Y(), 5);
            plotLine(GTEGraph, MyGTEGraph, improvedEuler.getX(), improvedEuler.getGTE_Y(), 5);
        } else {
            mathsGraph.remove(1);
            MyLTEGraph.remove(5);
            MyGTEGraph.remove(5);
        }
        update();
    }

    public void handleImprovedEulerCheckBox1(javafx.event.ActionEvent actionEvent) { //if improved euler method is selected
        if (ImprovedEulerCheckBox1.isSelected()){
            plotLine(LTEGraph1, MyLTEGraph1, N_values, greatestLTE_ImprovedEuler, 5);
            plotLine(GTEGraph1, MyGTEGraph1, N_values, greatestGTE_ImprovedEuler, 5);
        } else {
            MyLTEGraph1.remove(5);
            MyGTEGraph1.remove(5);
        }
        update1();
    }

    public void handleRungeKuttaCheckBox(javafx.event.ActionEvent actionEvent) { //if runge kutta method is selected
        if (RungeKuttaCheckBox.isSelected()){
            plotLine(lineGraph, mathsGraph, rungeKutta.getX(), rungeKutta.getY(), 2);
            plotLine(LTEGraph, MyLTEGraph, rungeKutta.getX(), rungeKutta.getLTE_Y(), 6);
            plotLine(GTEGraph, MyGTEGraph, rungeKutta.getX(), rungeKutta.getGTE_Y(), 6);
        } else {
            mathsGraph.remove(2);
            MyLTEGraph.remove(6);
            MyGTEGraph.remove(6);
        }
        update();
    }

    public void handleRungeKuttaCheckBox1(javafx.event.ActionEvent actionEvent) { //if runge kutta method is selected
        if (RungeKuttaCheckBox1.isSelected()){
            plotLine(LTEGraph1, MyLTEGraph1, N_values, greatestLTE_RungeKutta, 6);
            plotLine(GTEGraph1, MyGTEGraph1, N_values, greatestGTE_RungeKutta, 6);
        } else {
            MyLTEGraph1.remove(6);
            MyGTEGraph1.remove(6);
        }
        update1();
    }

    public void handleExactSolutionCheckBox(javafx.event.ActionEvent actionEvent) { //if exact solution is selected
        if (ExactSolutionCheckBox.isSelected()){
            double c = 1/(Math.exp(X_from_value) - Y_0_value) - X_from_value;
            plotLine(x -> -1/(c + x) + Math.exp(x));
        } else {
            mathsGraph.remove(3);
        }
        update();
    }

    public void handleExactSolutionCheckBox1(javafx.event.ActionEvent actionEvent) { //if exact solution is selected
        if (ExactSolutionCheckBox1.isSelected()){
            double c = 1/(Math.exp(X_from_value1) - Y_0_value1) - X_from_value1;
            plotLine1(x -> -1/(c + x) + Math.exp(x));
        } else {
            mathsGraph1.remove(3);
        }
        update1();
    }

    public void handleX_from(MouseEvent mouseEvent) { //setting up the x_from value from slider
        if (X_from.getValue() >= X_to.getValue()){
            if (X_from.getValue() == 0.0){
                X_from.setMax(0.001);
                X_from.setValue(0.001);
                X_to.setMin(0.001);
                X_to.setValue(0.001);
            }
            X_to_textField.setText(String.format("%.3f", X_from.getValue()));
        }
        X_to.setMin(X_from.getValue());
        X_from_textField.setText(String.format("%.3f", X_from.getValue()));
        update();
    }

    public void handleX_from1(MouseEvent mouseEvent) { //setting up the x_from value from slider
        if (X_from1.getValue() >= X_to1.getValue()){
            if (X_from1.getValue() == 0.0){
                X_from1.setMax(0.001);
                X_from1.setValue(0.001);
                X_to1.setMin(0.001);
                X_to1.setValue(0.001);
            }
            X_to_textField1.setText(String.format("%.3f", X_from1.getValue()));
        }
        X_to1.setMin(X_from1.getValue());
        X_from_textField1.setText(String.format("%.3f", X_from1.getValue()));
        update1();
    }

    public void handleX_to(MouseEvent mouseEvent){ //setting up the x_to value from slider
        if (X_from.getValue() >= X_to.getValue()){
            if (X_from.getValue() == 0.0){
                X_from.setMax(0.001);
                X_from.setValue(0.001);
                X_to.setMin(0.001);
                X_to.setValue(0.001);
            }
            X_from.setValue(X_to.getValue() + 0.001);
            X_from_textField.setText(String.format("%.3f", X_to.getValue()));
        }
        X_from.setMax(X_to.getValue());
        X_to_textField.setText(String.format("%.3f", X_to.getValue()));
        update();
    }

    public void handleX_to1(MouseEvent mouseEvent) { //setting up the x_to value from slider
        if (X_from1.getValue() >= X_to1.getValue()){
            if (X_from1.getValue() == 0.0){
                X_from1.setMax(0.001);
                X_from1.setValue(0.001);
                X_to1.setMin(0.001);
                X_to1.setValue(0.001);
            }
            X_from1.setValue(X_to1.getValue() + 0.001);
            X_from_textField1.setText(String.format("%.3f", X_to1.getValue()));
        }
        X_from1.setMax(X_to1.getValue());
        X_to_textField1.setText(String.format("%.3f", X_to1.getValue()));
        update1();
    }

    public void handleY_0(MouseEvent mouseEvent) { //setting up the Y_0 value from slider
        Y_0_textField.setText(String.format("%.3f", Y_0.getValue()));
        update();
    }

    public void handleY_01(MouseEvent mouseEvent) { //setting up the Y_0 value from slider
        Y_0_textField1.setText(String.format("%.3f", Y_01.getValue()));
        update1();
    }

    public void handleN_steps(MouseEvent mouseEvent) { //setting up the N steps value from slider
        N_stepsSpinner.getValueFactory().setValue((int)N_steps.getValue());
        update();
    }

    public void handleX_from_textField(KeyEvent actionEvent) { //setting up X_from value from textField
        if (isValid(X_from_textField.getText())){
            double number = Double.parseDouble(normalize(X_from_textField.getText()));
            if (number>=X_from.getMin() && number <=X_from.getMax()) {
                X_from.setValue(number);
                X_to.setMin(number);
                if (X_from.getValue() > X_to.getMin()) {
                    X_to.setMin(X_from.getValue());
                }
                update();
            }
        }
    }

    public void handleX_to_textField(KeyEvent actionEvent) {//setting up X_to value from textField
        if (isValid(X_to_textField.getText())){
            double number = Double.parseDouble(normalize(X_to_textField.getText()));
            if (number>=X_to.getMin() && number <=X_to.getMax()) {
                X_to.setValue(Double.parseDouble(normalize(X_to_textField.getText())));
                X_from.setMax(X_to.getValue());
                update();
            }
        }
    }

    public void handleY_0_textField(KeyEvent actionEvent) {//setting up Y_0 value from textField
        if (isValid(Y_0_textField.getText())){
            double number = Double.parseDouble(normalize(Y_0_textField.getText()));
            if (number>=Y_0.getMin() && number <=Y_0.getMax()) {
                Y_0.setValue(Double.parseDouble(normalize(Y_0_textField.getText())));
                update();
            }
        }
    }

    public void handleY_0_textField1(KeyEvent actionEvent) {//setting up Y_0 value from textField
        if (isValid(Y_0_textField1.getText())){
            double number = Double.parseDouble(normalize(Y_0_textField1.getText()));
            if (number>=Y_01.getMin() && number <=Y_01.getMax()) {
                Y_01.setValue(Double.parseDouble(normalize(Y_0_textField1.getText())));
                update1();
            }
        }
    }

    public void updateN_steps(int newValue) {//setting up N_steps value from textField
        N_steps.setValue(newValue);
        update();
    }

    private void update(){ //updating the graphs with changes
        X_from_value = X_from.getValue();
        X_to_value = X_to.getValue();
        Y_0_value = Y_0.getValue();
        N_steps_value = (int)N_steps.getValue();

        mathsGraph.setParameters(X_from_value, X_to_value, N_steps_value);
        MyLTEGraph.setParameters(X_from_value, X_to_value, N_steps_value);
        MyGTEGraph.setParameters(X_from_value, X_to_value, N_steps_value);

        X.setLowerBound(X_from_value - 0.1*(X_to_value - X_from_value));
        X.setUpperBound(X_to_value + 0.1*(X_to_value -X_from_value));
        LTEX.setLowerBound(X_from_value - 0.1*(X_to_value - X_from_value));
        LTEX.setUpperBound(X_to_value + 0.1*(X_to_value -X_from_value));
        GTEX.setLowerBound(X_from_value - 0.1*(X_to_value - X_from_value));
        GTEX.setUpperBound(X_to_value + 0.1*(X_to_value - X_from_value));


        double c0 = 1/(Math.exp(X_from_value) - Y_0_value) - X_from_value;
        double Y_bound = -1/(c0 + X_to_value) + Math.exp(X_to_value);

        Y.setLowerBound(Y_0_value - 0.1*Y_bound);
        Y.setUpperBound(1.1*Y_bound);
        Y.setTickUnit(Y_bound*2/1000);

        if (X_to_value!= X_from_value){
            ExactSolutionCheckBox.setDisable(false);
            EulerCheckBox.setDisable(false);
            ImprovedEulerCheckBox.setDisable(false);
            RungeKuttaCheckBox.setDisable(false);
        } else {
            mathsGraph.remove(0);
            mathsGraph.remove(1);
            mathsGraph.remove(2);
            mathsGraph.remove(3);

            MyLTEGraph.remove(4);
            MyLTEGraph.remove(5);
            MyLTEGraph.remove(6);

            MyGTEGraph.remove(4);
            MyGTEGraph.remove(5);
            MyGTEGraph.remove(6);

            ExactSolutionCheckBox.setDisable(true);
            EulerCheckBox.setDisable(true);
            ImprovedEulerCheckBox.setDisable(true);
            RungeKuttaCheckBox.setDisable(true);
        }

        euler = new Euler(X_from_value, X_to_value, Y_0_value, N_steps_value);
        improvedEuler = new ImprovedEuler(X_from_value, X_to_value, Y_0_value, N_steps_value);
        rungeKutta = new RungeKutta(X_from_value, X_to_value, Y_0_value, N_steps_value);

        double LTEY_bound = Math.min(Y_bound, LTE_Y_bound());
        LTEY.setLowerBound(-LTEY_bound/10);
        LTEY.setUpperBound(1.1*LTEY_bound);
        LTEY.setTickUnit(LTEY_bound*2/1000);

        double GTEY_bound = Math.min(Y_bound, GTE_Y_bound());
        GTEY.setLowerBound(-GTEY_bound/10);
        GTEY.setUpperBound(1.1*GTEY_bound);
        GTEY.setTickUnit(GTEY_bound*2/1000);

        if (EulerCheckBox.isSelected()){
            mathsGraph.remove(0);
            MyLTEGraph.remove(4);
            MyGTEGraph.remove(4);
            plotLine(lineGraph, mathsGraph, euler.getX(), euler.getY(), 0);
            plotLine(LTEGraph, MyLTEGraph, euler.getX(), euler.getLTE_Y(), 4);
            plotLine(GTEGraph, MyGTEGraph, euler.getX(), euler.getGTE_Y(), 4);
        }

        if (ImprovedEulerCheckBox.isSelected()){
            mathsGraph.remove(1);
            MyLTEGraph.remove(5);
            MyGTEGraph.remove(5);
            plotLine(lineGraph, mathsGraph, improvedEuler.getX(), improvedEuler.getY(), 1);
            plotLine(LTEGraph, MyLTEGraph, improvedEuler.getX(), improvedEuler.getLTE_Y(), 5);
            plotLine(GTEGraph, MyGTEGraph, improvedEuler.getX(), improvedEuler.getGTE_Y(), 5);
        }

        if (RungeKuttaCheckBox.isSelected()){
            mathsGraph.remove(2);
            MyLTEGraph.remove(6);
            MyGTEGraph.remove(6);
            plotLine(lineGraph, mathsGraph, rungeKutta.getX(), rungeKutta.getY(), 2);
            plotLine(LTEGraph, MyLTEGraph, rungeKutta.getX(), rungeKutta.getLTE_Y(), 6);
            plotLine(GTEGraph, MyGTEGraph, rungeKutta.getX(), rungeKutta.getGTE_Y(), 6);
        }

        if (ExactSolutionCheckBox.isSelected()){
            mathsGraph.remove(3);
            double c = 1/(Math.exp(X_from_value) - Y_0_value) - X_from_value;
            plotLine(x -> -1/(c + x) + Math.exp(x));

        }
    }

    private void update1(){ //updating the graphs with changes
        X_from_value1 = X_from1.getValue();
        X_to_value1 = X_to1.getValue();
        Y_0_value1 = Y_01.getValue();
        N_from_value = (int)N_from1.getValue();
        N_to_value = (int)N_to1.getValue();
        int N_size = N_to_value - N_from_value;

        mathsGraph1.setParameters(X_from_value1, X_to_value1);
        MyLTEGraph1.setParameters(N_from_value, N_to_value, N_size);
        MyGTEGraph1.setParameters(N_from_value, N_to_value, N_size);

        X1.setLowerBound(X_from_value1 - 0.1*(X_to_value1 - X_from_value1));
        X1.setUpperBound(X_to_value1 + 0.1*(X_to_value1 -X_from_value1));
        LTEX1.setLowerBound(N_from_value - 0.1*(N_to_value - N_from_value));
        LTEX1.setUpperBound(N_to_value + 0.1*(N_to_value -N_from_value));
        GTEX1.setLowerBound(N_from_value - 0.1*(N_to_value - N_from_value));
        GTEX1.setUpperBound(N_to_value + 0.1*(N_to_value - N_from_value));


        double c0 = 1/(Math.exp(X_from_value1) - Y_0_value1) - X_from_value1;
        double Y1_bound1 = -1/(c0 + X_to_value1) + Math.exp(X_to_value1);

        Y1.setLowerBound(Y_0_value1 - 0.1*Y1_bound1);
        Y1.setUpperBound(1.1*Y1_bound1);
        Y1.setTickUnit(Y1_bound1*2/1000);

        if (X_to_value1!= X_from_value1){
            ExactSolutionCheckBox1.setDisable(false);
            EulerCheckBox1.setDisable(false);
            ImprovedEulerCheckBox1.setDisable(false);
            RungeKuttaCheckBox1.setDisable(false);
        } else {
            mathsGraph1.remove(0);

            MyLTEGraph1.remove(4);
            MyLTEGraph1.remove(5);
            MyLTEGraph1.remove(6);

            MyGTEGraph1.remove(4);
            MyGTEGraph1.remove(5);
            MyGTEGraph1.remove(6);

            ExactSolutionCheckBox1.setDisable(true);
            EulerCheckBox1.setDisable(true);
            ImprovedEulerCheckBox1.setDisable(true);
            RungeKuttaCheckBox1.setDisable(true);
        }

        ArrayList<Euler> eulerList = new ArrayList<>();
        ArrayList<ImprovedEuler> improvedEulerList = new ArrayList<>();
        ArrayList<RungeKutta> rungeKuttaList = new ArrayList<>();

        N_values = new ArrayList<>();

        greatestLTE_Euler = new ArrayList<>();
        greatestLTE_ImprovedEuler = new ArrayList<>();
        greatestLTE_RungeKutta = new ArrayList<>();

        greatestGTE_Euler = new ArrayList<>();
        greatestGTE_ImprovedEuler = new ArrayList<>();
        greatestGTE_RungeKutta = new ArrayList<>();

        for (int i = N_from_value; i<=N_to_value; i++){
            N_values.add((double)i);
            Euler nextEuler = new Euler(X_from_value1, X_to_value1, Y_0_value1, i);
            eulerList.add(nextEuler);
            greatestLTE_Euler.add(nextEuler.getGreatestLTE());
            greatestGTE_Euler.add(nextEuler.getGreatestGTE());
        }

        for (int i = N_from_value; i<=N_to_value; i++){
            ImprovedEuler nextImprovedEuler = new ImprovedEuler(X_from_value1, X_to_value1, Y_0_value1, i);
            improvedEulerList.add(nextImprovedEuler);
            greatestLTE_ImprovedEuler.add(nextImprovedEuler.getGreatestLTE());
            greatestGTE_ImprovedEuler.add(nextImprovedEuler.getGreatestGTE());
        }

        for (int i = N_from_value; i<=N_to_value; i++){
            RungeKutta nextRungeKutta = new RungeKutta(X_from_value1, X_to_value1, Y_0_value1, i);
            rungeKuttaList.add(nextRungeKutta);
            greatestLTE_RungeKutta.add(nextRungeKutta.getGreatestLTE());
            greatestGTE_RungeKutta.add(nextRungeKutta.getGreatestGTE());
        }

        double LTEY_bound1 = Math.min(Y1_bound1, LTE_Y_bound1());
        LTEY1.setLowerBound(-LTEY_bound1/10);
        LTEY1.setUpperBound(1.1*LTEY_bound1);
        LTEY1.setTickUnit(LTEY_bound1*2/1000);

        double GTEY_bound1 = Math.min(Y1_bound1, GTE_Y_bound1());
        GTEY1.setLowerBound(-GTEY_bound1/10);
        GTEY1.setUpperBound(1.1*GTEY_bound1);
        GTEY1.setTickUnit(GTEY_bound1*2/1000);

        if (EulerCheckBox1.isSelected()){
            MyLTEGraph1.remove(4);
            MyGTEGraph1.remove(4);
            plotLine(LTEGraph1, MyLTEGraph1, N_values, greatestLTE_Euler, 4);
            plotLine(GTEGraph1, MyGTEGraph1, N_values, greatestGTE_Euler, 4);
        }

        if (ImprovedEulerCheckBox1.isSelected()){
            MyLTEGraph1.remove(5);
            MyGTEGraph1.remove(5);
            plotLine(LTEGraph1, MyLTEGraph1, N_values, greatestLTE_ImprovedEuler, 5);
            plotLine(GTEGraph1, MyGTEGraph1, N_values, greatestGTE_ImprovedEuler, 5);
        }

        if (RungeKuttaCheckBox1.isSelected()){
            MyLTEGraph1.remove(6);
            MyGTEGraph1.remove(6);
            plotLine(LTEGraph1, MyLTEGraph1, N_values, greatestLTE_RungeKutta, 6);
            plotLine(GTEGraph1, MyGTEGraph1, N_values, greatestGTE_RungeKutta, 6);
        }

        if (ExactSolutionCheckBox1.isSelected()){
            mathsGraph1.remove(3);
            double c = 1/(Math.exp(X_from_value1) - Y_0_value1) - X_from_value1;
            plotLine1(x -> -1/(c + x) + Math.exp(x));

        }
    }

    private double LTE_Y_bound(){//setting bounds for error graph
        double result = 10.0;
        if (EulerCheckBox.isSelected() && !EulerCheckBox.isDisabled()){
            if (ImprovedEulerCheckBox.isSelected() && !ImprovedEulerCheckBox.isDisabled()){
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()){
                    result = Math.max(euler.getGreatestLTE(), Math.max(improvedEuler.getGreatestLTE(), rungeKutta.getGreatestLTE()));
                } else {
                    result = Math.max(euler.getGreatestLTE(), improvedEuler.getGreatestLTE());
                }
            } else {
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()){
                    result = Math.max(euler.getGreatestLTE(), rungeKutta.getGreatestLTE());
                } else {
                    result = euler.getGreatestLTE();
                }
            }
        } else {
            if (ImprovedEulerCheckBox.isSelected() && !ImprovedEulerCheckBox.isDisabled()) {
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()) {
                    result = Math.max(improvedEuler.getGreatestLTE(), rungeKutta.getGreatestLTE());
                } else {
                    result = improvedEuler.getGreatestLTE();
                }
            } else {
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()) {
                    result = rungeKutta.getGreatestLTE();
                }
            }
        }
        return result;
    }

    private double LTE_Y_bound1(){//setting bounds for error graph
        double result = 10.0;
        if (EulerCheckBox1.isSelected() && !EulerCheckBox1.isDisabled()){
            if (ImprovedEulerCheckBox1.isSelected() && !ImprovedEulerCheckBox1.isDisabled()){
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()){
                    result = Math.max(Collections.max(greatestLTE_Euler), Math.max(Collections.max(greatestLTE_ImprovedEuler), Collections.max(greatestLTE_RungeKutta)));
                } else {
                    result = Math.max(Collections.max(greatestLTE_Euler), Collections.max(greatestLTE_ImprovedEuler));
                }
            } else {
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()){
                    result = Math.max(Collections.max(greatestLTE_Euler), Collections.max(greatestLTE_RungeKutta));
                } else {
                    result = Collections.max(greatestLTE_Euler);
                }
            }
        } else {
            if (ImprovedEulerCheckBox1.isSelected() && !ImprovedEulerCheckBox1.isDisabled()) {
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()) {
                    result = Math.max(Collections.max(greatestLTE_ImprovedEuler), Collections.max(greatestLTE_RungeKutta));
                } else {
                    result = Collections.max(greatestLTE_ImprovedEuler);
                }
            } else {
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()) {
                    result = Collections.max(greatestLTE_RungeKutta);
                }
            }
        }
        return result;
    }

    private double GTE_Y_bound1(){//setting bounds for error graph
        double result = 10.0;
        if (EulerCheckBox1.isSelected() && !EulerCheckBox1.isDisabled()){
            if (ImprovedEulerCheckBox1.isSelected() && !ImprovedEulerCheckBox1.isDisabled()){
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()){
                    result = Math.max(Collections.max(greatestGTE_Euler), Math.max(Collections.max(greatestGTE_ImprovedEuler), Collections.max(greatestGTE_RungeKutta)));
                } else {
                    result = Math.max(Collections.max(greatestGTE_Euler), Collections.max(greatestGTE_ImprovedEuler));
                }
            } else {
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()){
                    result = Math.max(Collections.max(greatestGTE_Euler), Collections.max(greatestGTE_RungeKutta));
                } else {
                    result = Collections.max(greatestGTE_Euler);
                }
            }
        } else {
            if (ImprovedEulerCheckBox1.isSelected() && !ImprovedEulerCheckBox1.isDisabled()) {
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()) {
                    result = Math.max(Collections.max(greatestGTE_ImprovedEuler), Collections.max(greatestGTE_RungeKutta));
                } else {
                    result = Collections.max(greatestGTE_ImprovedEuler);
                }
            } else {
                if (RungeKuttaCheckBox1.isSelected() && !RungeKuttaCheckBox1.isDisabled()) {
                    result = Collections.max(greatestGTE_RungeKutta);
                }
            }
        }
        return result;
    }

    private double GTE_Y_bound(){//setting bounds for error graph
        double result = 10.0;
        if (EulerCheckBox.isSelected() && !EulerCheckBox.isDisabled()){
            if (ImprovedEulerCheckBox.isSelected() && !ImprovedEulerCheckBox.isDisabled()){
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()){
                    result = Math.max(euler.getGreatestGTE(), Math.max(improvedEuler.getGreatestGTE(), rungeKutta.getGreatestGTE()));
                } else {
                    result = Math.max(euler.getGreatestGTE(), improvedEuler.getGreatestGTE());
                }
            } else {
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()){
                    result = Math.max(euler.getGreatestGTE(), rungeKutta.getGreatestGTE());
                } else {
                    result = euler.getGreatestGTE();
                }
            }
        } else {
            if (ImprovedEulerCheckBox.isSelected() && !ImprovedEulerCheckBox.isDisabled()) {
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()) {
                    result = Math.max(improvedEuler.getGreatestGTE(), rungeKutta.getGreatestGTE());
                } else {
                    result = improvedEuler.getGreatestGTE();
                }
            } else {
                if (RungeKuttaCheckBox.isSelected() && !RungeKuttaCheckBox.isDisabled()) {
                    result = rungeKutta.getGreatestGTE();
                }
            }
        }
        return result;
    }

    private boolean isValid(String str){//checking if value from textField is valid
        if (str.length() == 0){
            return false;
        }
        if ((str.charAt(0) < 48 || str.charAt(0) > 57) && str.length() == 1){
            return false;
        }
        if (!(str.charAt(0) == '-' || str.charAt(0)>=48 && str.charAt(0) <= 57)){
            return false;
        }
        if (str.charAt(0) == '-' && !(str.charAt(1)>=48 && str.charAt(1) <= 57)){
            return false;
        }
        int numOfDots = 0;
        for (int i = 1; i < str.length(); i++){
            if (str.charAt(i) == ','){
                numOfDots++;
                if (numOfDots > 1) return false;
            } else {
                if (str.charAt(i) < 48 || str.charAt(i) > 57) {
                    return false;
                }
            }
        }
        return true;
    }

    private String normalize(String str){//normalizing values from textField
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ','){
                result.append('.');
            } else {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    public void handleN_from1(MouseEvent mouseEvent) {
        N_from_value = (int)N_from1.getValue();
        N_fromTextField1.setText(String.valueOf(N_from_value));
        N_to1.setMin(N_from_value);
        update1();
    }

    public void handleN_to1(MouseEvent mouseEvent) {
        N_to_value = (int)N_to1.getValue();
        N_toTextField1.setText(String.valueOf(N_to_value));
        N_from1.setMax(N_to_value);
        update1();
    }

    public void handleN_fromtextField1(KeyEvent keyEvent) {
        if (isValid(N_fromTextField1.getText())){
            double number1 = Double.parseDouble(normalize(N_fromTextField1.getText()));
            int number = (int)number1;
            if (number>=N_from1.getMin() && number <=N_from1.getMax()) {
                N_from1.setValue(number);
                N_to1.setMin(number);
                if (N_from1.getValue() > N_to1.getMin()) {
                    N_to1.setMin(N_from1.getValue());
                    if (N_to1.getValue() < N_from1.getValue()){
                        N_to1.setValue(number);
                    }
                }
                update1();
            }
        }
    }

    public void handleN_totextField(KeyEvent keyEvent) {
        if (isValid(N_toTextField1.getText())){
            double number1 = Double.parseDouble(normalize(N_toTextField1.getText()));
            int number = (int)number1;
            if (number>=N_to1.getMin() && number <=N_to1.getMax()) {
                N_to1.setValue(number);
                N_from1.setMax(number);
                if (N_from1.getValue() > N_to1.getMin()) {
                    N_from1.setMax(N_to1.getValue());
                    if (N_to1.getValue() < N_from1.getValue()){
                        N_from1.setValue(number);
                    }
                }
                update1();
            }
        }
    }

    public void handleX_from_textField1(KeyEvent keyEvent) {
        if (isValid(X_from_textField1.getText())){
            double number = Double.parseDouble(normalize(X_from_textField1.getText()));
            if (number>=X_from1.getMin() && number <=X_from1.getMax()) {
                X_from1.setValue(number);
                X_to1.setMin(number);
                if (X_from1.getValue() > X_to1.getMin()) {
                    X_to1.setMin(X_from1.getValue());
                }
                update1();
            }
        }
    }

    public void handleX_to_textField1(KeyEvent keyEvent) {
        if (isValid(X_to_textField1.getText())){
            double number = Double.parseDouble(normalize(X_to_textField1.getText()));
            if (number>=X_to1.getMin() && number <=X_to1.getMax()) {
                X_to1.setValue(Double.parseDouble(normalize(X_to_textField1.getText())));
                X_from1.setMax(X_to1.getValue());
                update1();
            }
        }
    }
}

