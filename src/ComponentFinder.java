/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Marzieh
 */
public final class ComponentFinder {
    
    private  String noEdgePenaltyScore;
    private  String PenaltyScore;
    private  double[] raws_hat;
    private  double[] raws;
    private  HashMap<Integer,Vector<Neighbor>> ppi;
    static int numberofNode ;
    private boolean AllNode;
    public static Components cmp;
    
    public ComponentFinder(HashMap<Integer,Vector<Neighbor>> ppi, double[] NodeScore, double[] NodePermScore, String EdgePenaltyScore, String noEdgePenaltyScore){
        this.ppi = ppi;
        this.raws = NodeScore;
        this.raws_hat = NodePermScore;
        this.PenaltyScore = EdgePenaltyScore;
        this.noEdgePenaltyScore = noEdgePenaltyScore;
        numberofNode = NodeScore.length;
        ComponentFinder.cmp = GreedyFinder();
    }
    
    
    // @param raws are the node score
    //@param raws_hat are the permuted node score
    public Components GreedyFinder() {
        
        //Coloring all nodes;
        String[] nodeColor = new String[numberofNode];
        for (int i=0;i<nodeColor.length;i++){
            nodeColor[i] = "white";
        }

     
        AllNode= false;
        Vector<Vector> AllComponents = new Vector<>();
        Vector<Double> compScore = new Vector<>();
        int numberofc = 0;
        
        double[] traws=new double[raws.length];
        System.arraycopy(raws, 0, traws, 0, raws.length);

        int[] sorted_raws = sort(traws);
        

        while(!AllNode ){
            Vector<Integer> component = new Vector<>();

            double componentScore = 0;
            Vector<Neighbor> SearchSpace = new Vector<>();
//            int startingNode = randomgenerator.nextInt(ppi.size()-1)+1;
            int startingNode = getStartingNode(sorted_raws,nodeColor);

            Vector<Integer> firstStep   = new Vector<>();
       
            if(nodeColor[startingNode].equals("white")){
                nodeColor[startingNode] = "gray";
                component.add(startingNode);
                Vector<Neighbor> neighs = ppi.get(startingNode);

                if(neighs != null){
                    firstStep.add(startingNode);

                for(int i=0;i<neighs.size();i++)
                    SearchSpace.add(neighs.elementAt(i));
                    Neighbor candid=new Neighbor();
                    while( candid != null && !SearchSpace.isEmpty()){
                        Vector V = findNextCandid(SearchSpace, component, componentScore, nodeColor);
                        candid = (Neighbor)V.elementAt(0);
                        if(candid != null){

                            component.add(candid.index_neighbor);
                            nodeColor[candid.index_neighbor] = "gray";

                            SearchSpace = UpdateSearchSpace(firstStep,SearchSpace, component, candid);
                            componentScore = (Double)V.elementAt(1);
                            double componentPenalty = (Double) V.elementAt(2);                            
                        }
                    }
                }       
                
                if(component.size() > 1){
                    AllComponents.add(component);
                    compScore.add(componentScore);
                }

            }
            AllNode = AllNodeCover(nodeColor);
            numberofc++;            
        }
        Components cmps = new Components(AllComponents,compScore);
                
        return cmps;
    }
    
    
    //*********************************************
    //Utility for GreedyFinder
    //*********************************************
    
    


    private  double[] ComputeScore( Vector v, int newNode, double score){
        double r = raws[newNode];
        Vector<Neighbor> neigh = ppi.get(newNode);
        double penalty=0;
        double[] score_penalty=new double[2];

       
        
        for(int i=0;i<v.size();i++){
            
            Neighbor n = find(neigh,(Integer)v.elementAt(i));
            double pen = 0;
            if(n != null){
                //For new scoring method
//                double pen = ((((NodeDegree[n.index_neighbor] * NodeDegree[newNode]) / EdgeNumber) * (raws_hat[n.index_neighbor] * raws_hat[newNode]) ));

                if(PenaltyScore.equals("multiply"))
                     pen =  (raws_hat[n.index_neighbor] * raws_hat[newNode]) ;
                if(PenaltyScore.equals("min"))
                    pen =  Math.min(raws_hat[n.index_neighbor] , raws_hat[newNode]) ;
                if(PenaltyScore.equals("noIntPenalty"))
                    pen = 0;
                 if(PenaltyScore.equals("max"))
                     pen =  Math.max(raws_hat[n.index_neighbor] , raws_hat[newNode]) ;

                penalty += pen;
                double s = (n.weight - pen);
                score = score + s;
            }
            else{
//                double  pen = ((((NodeDegree[(Integer)v.elementAt(i)] * NodeDegree[newNode]) / EdgeNumber) * (raws_hat[(Integer)v.elementAt(i)] * raws_hat[newNode]) ));
                 if(noEdgePenaltyScore.equals("multiply"))
                     pen =  (raws_hat[(Integer)v.elementAt(i)] * raws_hat[newNode]) ;
                if(noEdgePenaltyScore.equals("min"))
                    pen =  Math.min(raws_hat[(Integer)v.elementAt(i)] , raws_hat[newNode]) ;
                if(noEdgePenaltyScore.equals("noNotIntPenalty"))
                    pen = 0;
                   if(noEdgePenaltyScore.equals("max"))
                    pen =  Math.max(raws_hat[(Integer)v.elementAt(i)] , raws_hat[newNode]) ;

                penalty += pen;
                double s = 0 - pen;
                score = score + s;
            }
        }

        score_penalty[0]=score;
        score_penalty[1]=penalty;
        return score_penalty;
    }



    private static boolean AllNodeCover(String[] s){
        int c=0;
        for(int i=0;i<s.length;i++){
            if(s[i].equals("gray"))
                c++;
        }
//        System.out.println(c);
        if(c>=numberofNode)
            return true;
        return false;
    }


     private static Neighbor find(Vector<Neighbor> v, int index){
         
        for(int i=0;i<v.size();i++){
//            System.out.println(v.size()+" "+v.elementAt(i).index_neighbor+" "+index);
            if(v.elementAt(i).index_neighbor == index)
                return v.elementAt(i);
        }
        return null;
    }

     private static boolean findNB(Vector<Neighbor> v, int index){
        for(int i=0;i<v.size();i++){
            if(v.elementAt(i).index_neighbor == index)
                return true;
        }
        return false;
    }


      private static boolean findB(Vector v, int index){
        for(int i=0;i<v.size();i++){
            if((Integer)v.elementAt(i) == index)
                return true;
        }
        return false;
    }



    private  Vector<Object> findNextCandid(Vector<Neighbor> SearchSpace, Vector component, double ComponentScore, String[] nodeColor ) {
        Neighbor temp = null;
        Vector<Object> V = new Vector<>();
        double penalty=0;
        double maxscore = ComponentScore;

        for(int i=0;i<SearchSpace.size();i++){
            Neighbor candid = SearchSpace.elementAt(i);
            double[] bestScore = ComputeScore(component, candid.index_neighbor, ComponentScore);

            //allow overlap or not if you change white condition
            if(bestScore[0] > maxscore && nodeColor[candid.index_neighbor].equals("white")){  
                temp = candid;
                maxscore = bestScore[0];
                penalty = bestScore[1];
            }
        }       

        V.add(temp);
        V.add(new Double(maxscore));
        V.add(new Double(penalty));
        return V;
    }


    private  Vector<Neighbor> UpdateSearchSpace(Vector firststep,Vector<Neighbor> searchSpace, Vector component, Neighbor newnode){
        //removing new added node from search space
        for(int i=0;i<searchSpace.size();i++){
            if(searchSpace.elementAt(i).index_neighbor == newnode.index_neighbor)
                searchSpace.removeElementAt(i);
        }

        //Adding the neighbors of new node that are not in component
        Vector<Neighbor> neigh = ppi.get(newnode.index_neighbor);
        //To make sure that the deppth is 1, The searchspace just contain neighbors and neighbor of neighobrs :D
//        if(findB(firststep,newnode.index_source)){
         for(int i=0;i<neigh.size();i++){
            if(!findB(component, neigh.elementAt(i).index_neighbor) && !findNB(searchSpace,neigh.elementAt(i).index_neighbor))
                searchSpace.add(neigh.elementAt(i));
          }
//        }

        return searchSpace;
    }

    private static boolean find(Vector<String> v,String s){
        for(int i=0;i<v.size();i++){
            if((v.elementAt(i)).equals(s))
                return true;
        }
        return false;
    }

    private static double mean(Vector<Double> raws){
        double m=0;
        for(int i=0;i<raws.size();i++){
            m += raws.elementAt(i);
        }
        m = m / raws.size();
        return m;
    }

    private static int getMin(double[] r){
        int tmp_index = -1;
        double tmp_score = 1000;
        for(int i=0;i<r.length;i++){
            if((r[i] < tmp_score) && (r[i] > 0)){
                tmp_index = i;
                tmp_score = r[i];
            }
        }
        return tmp_index;
    }

    private static int getMax(double[] r){
        int tmp_index = -1;
        double tmp_score = -1;
        for(int i=0;i<r.length;i++){
            if(r[i] > tmp_score){
                tmp_index = i;
                tmp_score = r[i];
            }
        }
        return tmp_index;
    }


    private static double getScore( Vector v, HashMap<Integer,Vector<Neighbor>> ppi,double[] raws, double[] raws_hat ){
        double score = 0;
      for(int j=0;j<v.size()-1;j++){
         int newNode = (Integer)v.elementAt(j);
        double r = raws[newNode];
        Vector<Neighbor> neigh = ppi.get(newNode);
        if(neigh != null){
//        double penalty=0;
//        double[] score_penalty=new double[2];
        for(int i=j;i<v.size();i++){
//            System.out.println(neigh.size()+"\t"+v.elementAt(i));
            if((Integer)v.elementAt(i) != newNode){
                Neighbor n = find(neigh,(Integer)v.elementAt(i));
                if(n != null){
                //For new scoring method
//                double pen = ((((NodeDegree[n.index_neighbor] * NodeDegree[newNode]) / EdgeNumber) * (raws_hat[n.index_neighbor] * raws_hat[newNode]) ));
                    double pen = (( (raws_hat[n.index_neighbor] * raws_hat[newNode]) ));
//                penalty += pen;
                    double s = (n.weight - pen);
                    score = score + s;
                }
                else{
//                double pen = ((((NodeDegree[(Integer)v.elementAt(i)] * NodeDegree[newNode]) / EdgeNumber) * (raws_hat[(Integer)v.elementAt(i)] * raws_hat[newNode]) ));
                    double pen = (((raws_hat[(Integer)v.elementAt(i)] * raws_hat[newNode]) ));
//                penalty += pen;
                    double s = (0 - pen);
                    score = score + s;
                }
            }


        }
          }
      }
      return score;
    }

    private static int[] sort(double[] raws){
        double[] integers = raws;
        int last = integers.length;
        int[] Indices = new int[last];
        for(int i=0;i<last;i++)
            Indices[i] = i;

        for ( int i = 0 ; i < last ; i++) {
            for (int j = 0 ; j < last-1 ; j++){
                if (integers[j] > integers[j+1]){
                    double temp = integers[j];
                    integers[j] = integers[j+1];
                    integers[j+1] = temp;
                    //Update the Indices
                    int temp2 = Indices[j];
                    Indices[j] = Indices[j+1];
                    Indices[j+1] = temp2;
                }
            }
        }
//        System.out.println("Indices");
        return Indices;
    }


    private  int getStartingNode(int[] sorted_raws, String[] nodeColor){
//        for(int i=sorted_raws.length-1;i>-1;i--){
////            int randoms = randomgenerator.nextInt(sorted_raws.length-1);
//            if(nodeColor[sorted_raws[i]].equalsIgnoreCase("white")){                
//                return sorted_raws[i];
//            }
//        }
        //random selection
//        int tmp = 0;
//        while( (tmp < 10)){
//        int randoms = (int)(Math.random()*sorted_raws.length-1);
//        if(nodeColor[randoms].equalsIgnoreCase("white"))
//            return randoms;
//        tmp++;
//        }
        for(int i=sorted_raws.length-1;i>-1;i--){
//            int randoms = randomgenerator.nextInt(sorted_raws.length-1);
            if(nodeColor[sorted_raws[i]].equalsIgnoreCase("white")){                
                return sorted_raws[i];
            }
        }
        return -1;

    }
    
    
    
    //For reproducibility
    public void Reproducibility_check(String filename,HashMap<String, Integer> gene_index) throws FileNotFoundException, IOException{
      Scanner scan = new Scanner(new File(filename));
       FileWriter fw = new FileWriter(new File(filename+"InWTCCC"));
//        fw.write("size \t score in dbGAP  \t score in WTCCC \n");
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            Vector<Integer> subnet = new Vector<>();
//            System.out.println(fields.length);
            for(int i=2;i<fields.length;i++){
                Integer index = gene_index.get(fields[i]);
                if(index != null)
                    subnet.add(index);
//                System.out.println(fields[i]);
            }
            double score = getScore(subnet, ppi,raws, raws_hat);
            fw.write(fields[0]+"\t"+fields[1]+"\t"+score+"\n");
        }
        fw.close();
    }

    public void Reproducibility_Overlap(String subnetfilename1, String subnetfilename2,String outf) throws FileNotFoundException, IOException{
        Scanner scan = new Scanner(new File(subnetfilename1));
        Vector<Vector<String>> subnets1 = new Vector<Vector<String>>();
        Vector<String> subnets1Score = new Vector<>();
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            Vector<String> subnet = new Vector<>();
            for(int i=2;i<fields.length;i++){
                subnet.add(fields[i]);
            }
            subnets1.add(subnet);
            subnets1Score.add(fields[1]);
        } 
        scan = new Scanner(new File(subnetfilename2));
        Vector<Vector<String>> subnets2 = new Vector<Vector<String>>();
        Vector<String> subnets2Score = new Vector<>();
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            Vector<String> subnet = new Vector<>();
            for(int i=2;i<fields.length;i++){
                subnet.add(fields[i]);
            }
            subnets2.add(subnet);
            subnets2Score.add(fields[1]);
        } 
        
        FileWriter fw = new FileWriter(new File(outf));
        
        for(int i=0;i<11;i++){
            
            int overlap=0;
            int rank=0;
            int s = 0;
            Vector<String> subnet = subnets1.elementAt(i);
            fw.write((i+1)+"\t"+subnet.size()+"\t");
            for(int j=0;j<(subnets2.size());j++){
                Vector<String> sub = subnets2.elementAt(j);
                int numover = getOverlap(subnet,sub);
//                if(i==j)
//                    fw.write(numover + "\t");
                if(numover > overlap){
                        overlap = numover;
                        rank = (j+1);
                        s = sub.size();
                }
//                   fw.write((i+1)+"\t"+subnet.size()+"\t"+(j+1)+"\t"+sub.size()+"\t"+numover+"\n");                                                 
            }
            fw.write(rank+"\t"+s+"\t"+overlap+"\t"+subnets1Score.elementAt(i)+"\t"+subnets2Score.elementAt(rank)+"\t"+"\n");
        }
        fw.close();
    }
    
    public int getOverlap(Vector<String> sub1, Vector<String> sub2){
        int overlap = 0;
        for(int i=0;i<sub1.size();i++){
            for(int j=0;j<sub2.size();j++){
                if(sub1.elementAt(i).equalsIgnoreCase(sub2.elementAt(j))){
                    overlap++;
                    break;
                }
            }
        }
        return overlap;
    }
    
}
