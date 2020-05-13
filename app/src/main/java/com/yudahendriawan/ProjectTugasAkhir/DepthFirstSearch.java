package com.yudahendriawan.ProjectTugasAkhir;

import android.util.Log;

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
    double[][] data;

    public ArrayList<ArrayList<Double>> print(Graph graph, int start,
                                              int end, double distance, double roadDensity, String path, boolean[] visited) {

        //membuat wadah untuk path yang terbentuk
        String newPath = path + "," + start;
        visited[start] = true;
        //   graph.addEdgeDB();
        //inisiasi distanceInteger
        ArrayList<Double> distanceInteger;//"ini"
        ArrayList<Double> roadDensityInteger;
        boolean test = true;

        //proses DFS
        LinkedList<Node> list = graph.adjacencyList[start];

        for (int i = 0; i < list.size(); i++) {
            //   Node node = new Node();
            Node node = list.get(i);

            sum_of_distance = distance;
            sum_of_road_density = roadDensity;

//

            if (node.getDestination() != end && !visited[node.getDestination()]) {

                //menhitung jarak
                sum_of_distance = sum_of_distance + node.getDistance();
                sum_of_road_density = sum_of_road_density + node.getRoadDensity();

                //rekursif
                print(graph, node.getDestination(), end, sum_of_distance, sum_of_road_density, newPath, visited);
            } else if (node.getDestination() == end) {

                //menghitung jarak
                sum_of_distance = sum_of_distance + node.getDistance();
                sum_of_road_density = sum_of_road_density + node.getRoadDensity();

                //menambahkan wadah dengan node.destination
                String result = newPath + "," + node.getDestination();

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

                if (graph.getWisata() == null) {
                    Log.d("wisataDFS", "null");
                } else {

                }

                for (int a = 0; a < distanceInteger.size(); a++) {
                    if (graph.getWisata().contains(distanceInteger.get(a))) {
                        sum += 1;
                    }
                }

                //menambahkan banyaknya wisata di list index 1
                distanceInteger.add(1, sum);
                distanceInteger.add(2, roadDensity_);

                //menambahkan listOfDistance yang telah terbuat ke arrayList
                listOfDistance.add(distanceInteger);


                while (test) {
                    Log.d("wisataDFS", graph.wisata.toString());
                    Log.d("adjacency", graph.adjacencyList[start].toString());
                    test = false;
                }

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
        Log.d("printaku", graph.halo);
        //graph.adjacencyList[start];
        // double distance = 0;
        print(graph, start, end, graph.vertices, graph.vertices, "", visited);
    }

    public ArrayList<ArrayList<Double>> getTemp() {
        return temp;
    }

    public void setTemp(ArrayList<ArrayList<Double>> temp) {
        this.temp = temp;
    }

}
