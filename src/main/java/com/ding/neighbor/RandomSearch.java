package com.ding.neighbor;

import com.ding.neighbor.util.Point;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

import static com.ding.neighbor.Main.calculateTourLength;
import static com.ding.neighbor.Main.drawTour;

public class RandomSearch {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int POINT_RADIUS = 5;

    private static ArrayList<Point> points = Main.getPoints();
    private static ArrayList<Point> tour = points;
    private static double minTourLength = Double.MAX_VALUE;

    public static void init(Stage stage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.setCenter(canvas);

        stage.setTitle("ВЫФВЫФ  ");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();

        searchR(points.size());
        drawTour(canvas.getGraphicsContext2D(), tour, Color.BLUE,POINT_RADIUS); // Визуализация пути полного перебора
        double nnTourLength = calculateTourLength(tour);
        System.out.println("Случайный перебор: " + nnTourLength);
    }

    private static void searchR(int n) {
        for(int i = 0;i < 2000000/n;i++) {
            ArrayList<Point> pre = new ArrayList<>(points);
            Collections.shuffle(pre);

            double currlen = calculateTourLength(pre);
            if(currlen < minTourLength) {
                minTourLength = currlen;
                tour = new ArrayList<>(pre);
            }
        }
    }


}
