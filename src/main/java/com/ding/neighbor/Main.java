package com.ding.neighbor;


import com.ding.neighbor.util.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Main {
    private static int count = 500;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static ArrayList<Point> pointsM;
    public static ArrayList<Point> getPoints() {
        return pointsM;
    }
    public static void main(String[] args) {
        pointsM = generatePoints();
        Neighbor.launch(Neighbor.class,args);
    }
    public static ArrayList<Point> generatePoints() {
        ArrayList<Point> pointsT = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            pointsT.add(new Point((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT)));
        }
        return pointsT;
    }
    public static void drawTour(GraphicsContext gc, ArrayList<Point> tour, Color color, int pointRadius) {
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
    public static double calculateTourLength(ArrayList<Point> tour) {
        double length = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            length += tour.get(i).distance(tour.get(i + 1));
        }

        return length;
    }
}
