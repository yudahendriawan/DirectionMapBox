package com.yudahendriawan.directionmapbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author user
 */
public class DepthFirstSearch {

    double sum_of_distance = 0;
    double sum_of_road_density = 0;
    ArrayList<ArrayList<Double>> listOfDistance = new ArrayList<>();//"ini"
    ArrayList<ArrayList<Double>> temp = new ArrayList<>();//"ini"
    ArrayList<Double> listWisata;
    ArrayList<ArrayList<Double>> listOfRoadDensity = new ArrayList<>();

    public ArrayList<ArrayList<Double>> print(Graph graph, int start,
                                              int end, double distance, double roadDensity, String path, boolean[] visited) {

        //membuat wadah untuk path yang terbentuk
        String newPath = path + "," + start;
        visited[start] = true;

        //inisiasi distanceInteger
        ArrayList<Double> distanceInteger;//"ini"
        ArrayList<Double> roadDensityInteger;

        //proses DFS
        LinkedList<Node> list = graph.adjacencyList[start];
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            sum_of_distance = distance;
            sum_of_road_density = roadDensity;

            if (node.destination != end && visited[node.destination] == false) {

                //menhitung jarak
                sum_of_distance = sum_of_distance + node.distance;
                sum_of_road_density = sum_of_road_density + node.roadDensity;

                //rekursif
                print(graph, node.destination, end, sum_of_distance, sum_of_road_density, newPath, visited);
            } else if (node.destination == end) {

                //menghitung jarak
                sum_of_distance = sum_of_distance + node.distance;
                sum_of_road_density = sum_of_road_density + node.roadDensity;

                //menambahkan wadah dengan node.destination
                String result = newPath + "," + node.destination;

                //menghapus comma pada index 0
                String real_result = result.substring(1, result.length());

                //membuat array dengan cara split komma
                String[] elements = real_result.split(",");

                //merubah array menjadi list
                List<String> hasil = Arrays.asList(elements);

                //mengubah list menjaddi arraylist agar menjaddi dinamis
                ArrayList<String> distanceString = new ArrayList<String>(hasil);

                //mengubah type arraylist menjadi double agar bisa diolah
                distanceInteger = new ArrayList<Double>();
                ;
                for (int j = 0; j < distanceString.size(); j++) {
                    distanceInteger.add(Double.parseDouble(distanceString.get(j)));
                }

                //menghitung jarak pada masing-masing alternatif yang terbuat
                double distance_ = (sum_of_distance - graph.vertices);
                double roadDensity_ = sum_of_road_density - graph.vertices;


                //menambahkan jarak pada array di index 0
                distanceInteger.add(0, distance_);

                //menghitung banyaknya wisata yang dilalui
                double sum = 0;
                Graph g = new Graph();
                for (int a = 0; a < distanceInteger.size(); a++) {
                    if (g.getListWisata().contains(distanceInteger.get(a))) {
                        sum += 1;
                    }
                }

                //menambahkan banyaknya wisata di list index 1
                distanceInteger.add(1, sum);
                distanceInteger.add(2, roadDensity_);

                //menambahkan listOfDistance yang telah terbuat ke arrayList
                listOfDistance.add(distanceInteger);

            }

        }
        //remove from path
        visited[start] = false;

        //mereset jarak untuk menghitung jarak pada jalur baru
        sum_of_distance = 0;
        sum_of_road_density = 0;

        //mendapatkan nilai listOfDistance dan disimpan di temp
        return temp = listOfDistance;//"ini"
        // return listOfDistance;
    }

    public void hasil() {//"ini buat ngeprint doang"
        System.out.println(temp);
    }

    public void printAllPaths(Graph graph, int start, int end) {
        boolean[] visited = new boolean[graph.vertices];
        visited[start] = true;
        // double distance = 0;
        print(graph, start, end, graph.vertices, graph.vertices, "", visited);
    }

}
