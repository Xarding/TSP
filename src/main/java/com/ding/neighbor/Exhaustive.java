package com.ding.neighbor;

import com.ding.neighbor.util.Point;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.ding.neighbor.Main.calculateTourLength;
import static com.ding.neighbor.Main.drawTour;

public class Exhaustive {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int POINT_RADIUS = 5;

    private static ArrayList<Point> points = Main.getPoints();
    private static ArrayList<Point> exhaustiveTour = points;
    private static double minTourLength = Double.MAX_VALUE;

    public static void my(Stage stage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.setCenter(canvas);

        stage.setTitle("Полный перебор");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();

        exhaustiveSearch(points.size(), new ArrayList<>()); // Полный перебор
        drawTour(canvas.getGraphicsContext2D(), exhaustiveTour, Color.BLUE,POINT_RADIUS); // Визуализация пути полного перебора
        double nnTourLength = calculateTourLength(exhaustiveTour);
        System.out.println("Полный перебор: " + nnTourLength);
    }

    private static void exhaustiveSearch(int n, ArrayList<Point> prefix) {
        if (prefix.size() == n) {
            double tourLength = calculateTourLength(prefix);
            if (tourLength < minTourLength) {
                minTourLength = tourLength;
                exhaustiveTour = new ArrayList<>(prefix);
            }
        } else {
            for (Point point : points) {
                if (!prefix.contains(point)) {
                    ArrayList<Point> newPrefix = new ArrayList<>(prefix);
                    newPrefix.add(point);
                    exhaustiveSearch(n, newPrefix);
                }
            }
        }
    }


}






