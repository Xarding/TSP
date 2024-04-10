package com.ding.neighbor.util;

import com.ding.neighbor.util.Point;

import java.util.ArrayList;
import java.util.Random;

import static com.ding.neighbor.Main.calculateTourLength;

public class AntColonyTSP {
    private ArrayList<Point> points;
    private int numAnts;
    private int numIterations;
    private double evaporationRate;
    private double alpha;
    private double beta;
    private double[][] pheromones;

    public AntColonyTSP(ArrayList<Point> points, int numAnts, int numIterations, double evaporationRate, double alpha, double beta) {
        this.points = points;
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.evaporationRate = evaporationRate;
        this.alpha = alpha;
        this.beta = beta;
        this.pheromones = new double[points.size()][points.size()];
        initializePheromones();
    }

    private void initializePheromones() {
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    pheromones[i][j] = 0.1; // Начальное значение феромонов
                }
            }
        }
    }

    public ArrayList<Point> runAntColonyOptimization() {
        ArrayList<Point> bestTour = null;
        double shortestLength = Double.MAX_VALUE;

        for (int iteration = 0; iteration < numIterations; iteration++) {
            ArrayList<ArrayList<Point>> antTours = new ArrayList<>();
            for (int ant = 0; ant < numAnts; ant++) {
                ArrayList<Point> tour = constructAntTour();
                antTours.add(tour);
                double tourLength = calculateTourLength(tour);
                if (tourLength < shortestLength) {
                    shortestLength = tourLength;
                    bestTour = new ArrayList<>(tour);
                }
            }
            updatePheromones(antTours);
        }
        return bestTour;
    }

    private ArrayList<Point> constructAntTour() {
        Random random = new Random();
        ArrayList<Point> tour = new ArrayList<>();
        ArrayList<Point> remainingPoints = new ArrayList<>(points);
        Point startPoint = remainingPoints.remove(random.nextInt(remainingPoints.size()));
        tour.add(startPoint);

        while (!remainingPoints.isEmpty()) {
            Point currentPoint = tour.get(tour.size() - 1);
            Point nextPoint = chooseNextPoint(currentPoint, remainingPoints);
            tour.add(nextPoint);
            remainingPoints.remove(nextPoint);
        }
        return tour;
    }

    private Point chooseNextPoint(Point currentPoint, ArrayList<Point> remainingPoints) {
        Random random = new Random();
        double totalProbability = 0;
        double[] probabilities = new double[remainingPoints.size()];
        for (int i = 0; i < remainingPoints.size(); i++) {
            Point nextPoint = remainingPoints.get(i);
            double pheromone = Math.pow(pheromones[currentPoint.getIndex()][nextPoint.getIndex()], alpha);
            double attractiveness = Math.pow(1.0 / currentPoint.distance(nextPoint), beta);
            probabilities[i] = pheromone * attractiveness;
            totalProbability += probabilities[i];
        }
        double randomValue = random.nextDouble() * totalProbability;
        double cumulativeProbability = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return remainingPoints.get(i);
            }
        }
        return remainingPoints.get(remainingPoints.size() - 1);
    }

    private void updatePheromones(ArrayList<ArrayList<Point>> antTours) {
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    pheromones[i][j] *= (1.0 - evaporationRate);
                }
            }
        }
        for (ArrayList<Point> tour : antTours) {
            double tourLength = calculateTourLength(tour);
            for (int i = 0; i < tour.size() - 1; i++) {
                int fromIndex = tour.get(i).getIndex();
                int toIndex = tour.get(i + 1).getIndex();
                pheromones[fromIndex][toIndex] += 1.0 / tourLength;
                pheromones[toIndex][fromIndex] += 1.0 / tourLength;
            }
        }
    }


}
