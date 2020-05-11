package com.yudahendriawan.ProjectTugasAkhir;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Node {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("source")
    private int source;

    @Expose
    @SerializedName("destination")
    private int destination;

    @Expose
    @SerializedName("distance")
    private double distance;

    @Expose
    @SerializedName("roadDensity")
    private double roadDensity;


    private ArrayList<Node> node;


//    @Expose
//    @SerializedName("id")
//    private String id;
//
//    @Expose
//    @SerializedName("source")
//    private String source;
//
//    @Expose
//    @SerializedName("destination")
//    private String destination;
//
//    @Expose
//    @SerializedName("distance")
//    private String distance;
//
//    @Expose
//    @SerializedName("roadDensity")
//    private String roadDensity;

    public Node(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }
    public Node(int source, int destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Node(int source, int destination, double distance, double roadDensity) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.roadDensity = roadDensity;
    }

    Node() {
    }

    public ArrayList<Node> getNode() {
        return node;
    }

    public void setNode(ArrayList<Node> node) {
        this.node = node;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public double getRoadDensity() {
        return roadDensity;
    }

    public void setRoadDensity(double roadDensity) {
        this.roadDensity = roadDensity;
    }

    public int getSource() {
        return source;
    }

    public void setSource(double source) {
        this.source = (int) source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(double destination) {
        this.destination = (int) destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", source=" + source +
                ", destination=" + destination +
                ", distance=" + distance +
                ", roadDensity=" + roadDensity +
                '}';
    }

    //BATAS


//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getDestination() {
//        return destination;
//    }
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }
//
//    public String getDistance() {
//        return distance;
//    }
//
//    public void setDistance(String distance) {
//        this.distance = distance;
//    }
//
//    public String getRoadDensity() {
//        return roadDensity;
//    }
//
//    public void setRoadDensity(String roadDensity) {
//        this.roadDensity = roadDensity;
//    }
}
