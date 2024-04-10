package com.ding.neighbor;

import com.ding.neighbor.util.AntColonyTSP;
import com.ding.neighbor.util.Point;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.ding.neighbor.Main.calculateTourLength;
import static com.ding.neighbor.Main.drawTour;

public class AntColony {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int POINT_RADIUS = 5;

    private static ArrayList<Point> points = Main.getPoints();
    private static ArrayList<Point> antColonyTour = new ArrayList<>();

    public static void my(Stage stage, int numAnts, int numIterations, double evaporationRate, double alpha, double beta) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.setCenter(canvas);

        stage.setTitle("Муравьиный алгоритм");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();

        AntColonyTSP antColonyTSP = new AntColonyTSP(points, numAnts, numIterations, evaporationRate, alpha, beta);
        antColonyTour = antColonyTSP.runAntColonyOptimization();
        drawTour(canvas.getGraphicsContext2D(), antColonyTour, Color.GREEN, POINT_RADIUS);
        double antColonyTourLength = calculateTourLength(antColonyTour);
        System.out.println("Муравьиный алгоритм: " + antColonyTourLength);
    }

}

