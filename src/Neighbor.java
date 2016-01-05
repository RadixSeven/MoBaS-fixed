/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Marzieh
 */
public class Neighbor {

    int index_neighbor;
    int index_source;
    double weight;
    double absolute_weight;
    String neighbor;
    String source;


    public Neighbor(int index_neighbor, double weight, int source, double absolute_weight) {
        this.index_neighbor = index_neighbor;
        this.weight = weight;
        this.index_source = source;
        this.absolute_weight = absolute_weight;
    }

     public Neighbor(String neighbor, double weight, String source, double absolute_weight) {
        this.neighbor = neighbor;
        this.weight = weight;
        this.source = source;
        this.absolute_weight = absolute_weight;
    }

    public Neighbor(){}

    
}
