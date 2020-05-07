package com.yudahendriawan.directionmapbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

class Graph {

    int vertices;
    LinkedList<Node>[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<Node>();
        }
    }

    Graph() {
    }

    public void addEdge() {
        //add edge

        double[][] data = new double[][]{
                {0, 1, 476.62, 1},
                {1, 0, 476.62, 1},
                {1, 2, 237.12, 1},
                {2, 1, 237.12, 1},
                {2, 3, 1700, 1},
                {3, 2, 1700, 1},
                {2, 7, 387.43, 1},
                {7, 2, 387.43, 1},
                {3, 4, 296.78, 1},
                {4, 3, 296.78, 1},
                {3, 5, 521.57, 1},
                {5, 3, 521.57, 1},
                {4, 5, 700, 1},
                {5, 4, 700, 1},
                {3, 6, 555.67, 1},
                {6, 3, 555.67, 1},
                {6, 8, 2450, 1},
                {8, 6, 2450, 1},
                {8, 9, 767.19, 1},
                {9, 8, 767.19, 1},
                {9, 10, 437.66, 1},
                {10, 9, 437.66, 1},
                {10, 11, 550, 1},
                {11, 10, 550, 1},
                {7, 15, 1000, 1},
                {15, 7, 1000, 1},
                {15, 14, 1330, 1},
                {14, 15, 1330, 1},
                {6, 14, 1170, 1},
                {14, 6, 1170, 1},
                {15, 16, 741.91, 1},
                {16, 15, 741.91, 1},
                {16, 13, 1000, 1},
                {13, 16, 1000, 1},
                {13, 14, 1000, 1},
                {14, 13, 1000, 1},
                {8, 12, 1260, 1},
                {12, 8, 1260, 1},
                {12, 30, 1740, 1},
                {30, 12, 1740, 1},
                {12, 13, 3490, 1},
                {13, 12, 3490, 1},
                {16, 17, 2450, 1},
                {17, 16, 2450, 1},
                {18, 17, 450, 1},
                {17, 18, 450, 1},
                {18, 19, 1400, 1},
                {19, 18, 1400, 1},
                {19, 20, 3700, 1},
                {20, 19, 3700, 1},
                {16, 24, 1960, 1},
                {24, 16, 1960, 1},
                {13, 25, 2050, 1},
                {25, 13, 2050, 1},
                {21, 17, 2160, 1},
                {17, 21, 2160, 1},
                {21, 22, 1400, 1},
                {22, 21, 1400, 1},
                {22, 24, 800, 1},
                {24, 22, 800, 1},
                {24, 25, 922.881, 1},
                {25, 24, 922.88, 1},
                {25, 26, 1500, 1},
                {26, 25, 1500, 1},
                {22, 23, 700, 1},
                {23, 22, 700, 1},
                {26, 27, 650, 1},
                {27, 26, 650, 1},
                {27, 28, 1500, 1},
                {28, 27, 1500, 1},
                {28, 29, 1100, 1},
                {29, 28, 1100, 1},
                {28, 30, 2800, 1},
                {30, 28, 2800, 1}

//            {0, 1, 10},
//            {0, 2, 40},
//            {1, 2, 30},
//            {1, 3, 10},
//            {2, 3, 30},
//            {3, 4, 10},
//            {4, 0, 20},
//            {4, 1, 20},
//            {4, 5, 30}
        };

        ArrayList<Node> list = new ArrayList<>();
        for (double[] dataKu : data) {

            Node node = new Node();

            node.setSource(dataKu[0]);
            node.setDestination(dataKu[1]);
            node.setDistance(dataKu[2]);
            node.setRoadDensity(dataKu[3]);

            list.add(node);
            adjacencyList[node.getSource()].add(node);
        }
    }

    public ArrayList<Double> getListWisata() {

        Double[] list = {0.0, 4.0, 5.0, 7.0, 9.0, 10.0, 11.0,
                18.0, 19.0, 20.0, 22.0, 23.0, 27.0, 29.0};
        ArrayList<Double> listWisata = new ArrayList<>(Arrays.asList(list));

        return listWisata;
    }

    public String[] getLongLat() {
        String[] longLat = {
                "0", "-7.294455, 112.803655",
                "1", "-7.291139, 112.801781",
                "2", "-7.290344, 112.799743",
                "3", "-7.276026, 112.801839",
                "4", "-7.275934, 112.802721",
                "5", "-7.276591, 112.805451",
                "6", "-7.274644, 112.797816",
                "7", "-7.290437, 112.796369",
                "8", "-7.252922, 112.795378",
                "9", "-7.254267, 112.801853",
                "10", "-7.249400, 112.800501",
                "11", "-7.247226, 112.802257",
                "12", "-7.249934, 112.784387",
                "13", "-7.280402, 112.780948",
                "14", "-7.279238, 112.790340",
                "15", "-7.290124, 112.787328",
                "16", "-7.289523, 112.780715",
                "17", "-7.311321, 112.780646",
                "18", "-7.311799, 112.782312",
                "19", "-7.312360, 112.788902",
                "20", "-7.318214, 112.784242",
                "21", "-7.306572, 112.761765",
                "22", "-7.294329, 112.761751",
                "23", "-7.297431, 112.760045",
                "24", "-7.287419, 112.763051",
                "25", "-7.279441, 112.762185",
                "26", "-7.266159, 112.760785",
                "27", "-7.265257, 112.758042",
                "28", "-7.265829, 112.756734",
                "29", "-7.272280, 112.759526",
                "30", "-7.245489, 112.769081"};
        return longLat;
    }

}
