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

public class LocalSearch {

    private static ArrayList<Point> points = Main.getPoints();
    private static ArrayList<Point> bestTour;
    private static double bestTourLength = Double.MAX_VALUE;

    public static void init(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(800, 600);
        root.setCenter(canvas);

        primaryStage.setTitle("Локальный поиск");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        search();

        drawTour(canvas.getGraphicsContext2D(), bestTour, Color.PURPLE,5); // Визуализация лучшего найденного пути
        double tourLength = calculateTourLength(bestTour);
        System.out.println("Локальный поиск(2): " + tourLength);
    }

    public static void search() {
        bestTour = new ArrayList<>(points);
        Collections.shuffle(bestTour);

        while (true) {
            boolean improved = false;
            for (int i = 0; i < points.size() - 1; i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    ArrayList<Point> newTour = twoOptSwap(bestTour, i, j);
                    double newLength = calculateTourLength(newTour);
                    if (newLength < bestTourLength) {
                        bestTour = newTour;
                        bestTourLength = newLength;
                        improved = true;
                    }
                }
            }
            if (!improved) {
                break; // Если не произошло улучшений, завершаем поиск
            }
        }
    }

    private static ArrayList<Point> twoOptSwap(ArrayList<Point> tour, int i, int j) {
        ArrayList<Point> newTour = new ArrayList<>(tour.subList(0, i)); // До точки i
        newTour.addAll(reverseSubList(tour, i, j)); // Обратный порядок между i и j
        newTour.addAll(tour.subList(j, tour.size())); // После точки j
        return newTour;
    }

    private static ArrayList<Point> reverseSubList(ArrayList<Point> list, int start, int end) {
        ArrayList<Point> reversed = new ArrayList<>();
        for (int i = end - 1; i >= start; i--) {
            reversed.add(list.get(i));
        }
        return reversed;
    }


}