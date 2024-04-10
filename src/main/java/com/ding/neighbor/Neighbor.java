package com.ding.neighbor;

import com.ding.neighbor.util.Point;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Neighbor extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int POINT_RADIUS = 5;

    private ArrayList<Point> points = Main.getPoints();
    private ArrayList<Point> nnTour = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.setCenter(canvas);

        primaryStage.setTitle("Ближайший сосед");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

        nearestNeighbor(); // Выполнение алгоритма ближайшего соседа
        drawTour(canvas.getGraphicsContext2D(), nnTour, Color.RED,POINT_RADIUS); // Визуализация пути ближайшего соседа

        double nnTourLength = calculateTourLength(nnTour);
        System.out.println("Ближайший сосед: " + nnTourLength);

        //Stage stage = new Stage();
        //Exhaustive.my(stage);
        //stage.show();

        Stage stage2 = new Stage();
        AntColony.my(stage2,40,20,0.15,2.0,20.0);
        stage2.show();

        Stage stage3 = new Stage();
        LocalSearch.init(stage3);
        stage3.show();


        //Stage stage5 = new Stage();
        //RandomSearch.init(stage5);
        //stage5.show();


    }

    private static double calculateTourLength(ArrayList<Point> tour) {
        double length = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            length += tour.get(i).distance(tour.get(i + 1));
        }

        return length;
    }
    private static void drawTour(GraphicsContext gc, ArrayList<Point> tour, Color color, int pointRadius) {
        gc.setStroke(color);
        gc.setLineWidth(2.0);
        for (int i = 0; i < tour.size() - 1; i++) {
            Point from = tour.get(i);
            Point to = tour.get(i + 1);
            gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
        }

        // Рисуем точки
        gc.setFill(Color.BLACK);
        for (Point point : tour) {
            gc.fillOval(point.getX() - pointRadius / 2, point.getY() - pointRadius / 2, pointRadius, pointRadius);
        }
    }

    private void nearestNeighbor() {
        double min_length = Double.MAX_VALUE;
        ArrayList<Point> pre = new ArrayList<>();
        for(Point p : points) {
            ArrayList<Point> remainingPoints = new ArrayList<>(points);
            remainingPoints.remove(p);
            Point start = p;
            ArrayList<Point> temp = new ArrayList<>();
            temp.add(start);
            while (!remainingPoints.isEmpty()) {
                Point current = temp.get(temp.size() - 1);
                Point nearest = findNearest(current, remainingPoints);
                temp.add(nearest);
                remainingPoints.remove(nearest);
            }
            double length = calculateTourLength(temp);
            if(length < min_length) {
                pre = temp;
                min_length = length;
            }
        }
        nnTour = pre;
    }

    private Point findNearest(Point from, ArrayList<Point> candidates) {
        Point nearest = null;
        double minDist = Double.MAX_VALUE;
        for (Point candidate : candidates) {
            double dist = from.distance(candidate);
            if (dist < minDist) {
                minDist = dist;
                nearest = candidate;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        launch(args);
    }
}






