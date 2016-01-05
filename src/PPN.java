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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Marzieh
 */
public class PPN {
    
    public HashSet<String> ppiNames = new HashSet();
    public HashMap<String, Integer> gene_index = new HashMap<String, Integer>();
    public HashMap<Integer,String> index_gene = new HashMap<Integer,String>();
    private Scanner scan;
    private int numberofNode;
    private double[] NodeScore ;
    private double[][] NodePermScore ;
    
    public HashMap<Integer,Vector<Neighbor>> ppi;
    private String ppi_file;
    
    
    
    public void getPPIname(String filename) throws FileNotFoundException{
        scan = new Scanner(new File(filename));
        int GeneIndex=0;
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");            
            ppiNames.add(fields[0]);
            ppiNames.add(fields[1]);
        }
        
        for(String s:ppiNames){
            gene_index.put(s,GeneIndex);
            GeneIndex++;
        }
        numberofNode = ppiNames.size();
    }
    
    //This method compute the mean and variance of the score of the network
    private double[] getMeanVar(String filename) throws FileNotFoundException{
        scan = new Scanner(new File(filename));
        double[] scores = new double[numberofNode];
        java.util.Arrays.fill(scores,0);
        while(scan.hasNext()){
            String line = scan.nextLine();
        } 
        return scores;
    }
    
    public double[] InitNodeScore(String filename, HashMap<String,String> mapping) throws FileNotFoundException{
       
        NodeScore = new double[numberofNode];       
        java.util.Arrays.fill(NodeScore,0);
       
        scan = new Scanner(new File(filename));
          
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            String pval = fields[1];
            String snpName = fields[0];
            String geneName = mapping.get(snpName);
            Integer index = gene_index.get(geneName);
           
            if(gene_index.containsKey(geneName)){
                //map the best SNP pvalue to a gene
                double prePval = NodeScore[index];
                double newPval = -1 * Math.log10(Double.parseDouble(pval));
                if(newPval > prePval)
                    NodeScore[index] = newPval ; 
            } 
        }
        return NodeScore;
    }
    
   
    
    public double[] NormalizeNodeScore(double[] arr){
        Statistics stat = new Statistics(arr);
        double mean = stat.getMean();
        double var = stat.getVariance();
        for(int i=0;i<arr.length;i++)
            arr[i] = (arr[i] - mean) / var ;
        return arr;
    }
    
    public double[] NodeBackgroundScore(String path,HashMap<String,String> mapping) throws FileNotFoundException{
        double[] BackgroundScore = new double[numberofNode];
        //Create a matrix which show the pvalue of each gene at each permutation
        InitNodePermuteScore(path,mapping);
        //Get the mean over all permutation tests
        for(int i=0;i<numberofNode;i++){
            double[] tmp = getrow(NodePermScore, i);
            Statistics stat = new Statistics(tmp);
            double mean = stat.getMean();
            BackgroundScore[i] = mean;
        }
        return BackgroundScore;
    }
    
    public void InitNodePermuteScore(String path,HashMap<String,String> mapping) throws FileNotFoundException{
        File folder = new File(path);
         
        NodePermScore = new double[numberofNode][folder.listFiles().length];
        
        int counter = 0;
        for (final File fileEntry : folder.listFiles()) {
            
            scan = new Scanner(fileEntry.getAbsoluteFile());
            
            while(scan.hasNext()){
                String line = scan.nextLine();
                String[] fields = line.split("\t");
                
                String pval = fields[1];
                String snpName = fields[0];
                String geneName = mapping.get(snpName);
                Integer index = gene_index.get(geneName);
           
                if(gene_index.containsKey(geneName)){
                    //map the best SNP pvalue to a gene
                    double prePval = NodePermScore[index][counter];
                    double newPval = -1 * Math.log10(Double.parseDouble(pval));
                    if(newPval > prePval)
                        NodePermScore[index][counter] = newPval ; 
                }               
            }
            counter++;
        }
        
        //Normalize the NodeScore
//        for(int i=0;i<1001;i++){
//            double[] tmp = NormalizeNodeScore(getcol(NodePermScore,i));
//            putcol(NodePermScore, tmp, i);
//        }
            
    }
    
    public void createPPI(String ppi_filename,double[] raws ,String method) throws FileNotFoundException{
        ppi = new HashMap();
        scan = new Scanner(new File(ppi_filename));
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            if(fields.length>=2){

            //change the weihgt function
            if(!fields[0].equalsIgnoreCase(fields[1])){
            if(gene_index.get(fields[0]) != null && gene_index.get(fields[1]) != null){
                double weight = 0;
               if(method.equals("multiply"))
                     weight =  (raws[gene_index.get(fields[0])] * raws[gene_index.get(fields[1])]);
               else if(method.equals("min"))
                    weight =  Math.min(raws[gene_index.get(fields[0])], raws[gene_index.get(fields[1])]);
               else if(method.equals("max"))
                    weight =  Math.max(raws[gene_index.get(fields[0])], raws[gene_index.get(fields[1])]);



                Neighbor n = new Neighbor(((gene_index.get(fields[1]))), weight ,gene_index.get(fields[0]),1);
                Vector neighbors = ppi.get(((gene_index.get(fields[0]))));


                if(neighbors == null){
                    neighbors = new Vector();
                    neighbors.add(n);
                }
                else{
                    neighbors.add(n);
                }
                ppi.put(((gene_index.get(fields[0]))),neighbors);
               
            }
        }
        }}
    }
   
    
     
    public void createPermutedPPI(String ppi_filename,double[] raws ,String method) throws FileNotFoundException{
        ppi = new HashMap();
        scan = new Scanner(new File(ppi_filename));
        while(scan.hasNext()){
            String line = scan.nextLine();
            int tmp = line.indexOf("\t");
            String[] fields = new String[2];
            fields[0] = line.substring(0, tmp);
            fields[1] = line.substring(tmp+2);
           
            //change the weihgt function
            
                double weight = 0;
               if(method.equals("multiply"))
                     weight =  (raws[(Integer.parseInt(fields[0]))] * raws[(Integer.parseInt(fields[1]))]);
               else if(method.equals("min"))
                    weight =  Math.min(raws[Integer.parseInt(fields[0])], raws[Integer.parseInt(fields[1])]);
               else if(method.equals("max"))
                    weight =  Math.max(raws[Integer.parseInt(fields[0])], raws[Integer.parseInt(fields[1])]);



                Neighbor n = new Neighbor(((Integer.parseInt(fields[1]))), weight ,Integer.parseInt(fields[0]),1);
                Vector neighbors = ppi.get(((Integer.parseInt(fields[0]))));


                if(neighbors == null){
                    neighbors = new Vector();
                    neighbors.add(n);
                }
                else{
                    neighbors.add(n);
                }
                ppi.put(((Integer.parseInt(fields[0]))),neighbors);
               
            
        }

    }
   
    
    
    public int[][] createAdjMatrix(String ppi_filename) throws FileNotFoundException, IOException{
        int[][] ppiadj = new int[numberofNode][numberofNode];
        scan = new Scanner(new File(ppi_filename));
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            ppiadj[gene_index.get(fields[0])][gene_index.get(fields[1])]=1;
            ppiadj[gene_index.get(fields[1])][gene_index.get(fields[0])]=1;            
        }
      
        FileWriter fw = new FileWriter(new File(ppi_filename+"adj"));
        for(int i=0;i<numberofNode-1;i++){
            for(int j=(i+1);j<numberofNode;j++){
                if(ppiadj[i][j] ==1)
                    fw.write(i+"\t"+j+"\n");
            }
        }
        fw.close();
        return ppiadj;
    }
    
    
    
    //get the file which has the genes in a component and convert the file with gene index of components
    public void GenerateIndexFile(String filename) throws FileNotFoundException, IOException{
        Scanner scan = new Scanner(new File(filename));
        FileWriter fw = new FileWriter(new File(filename+"Index"));
        FileWriter fw2 = new FileWriter(new File(filename+"pvals"));
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            fw.write(fields[0]+"\t"+fields[1]+"\t");
            fw2.write(fields[0]+"\t"+fields[1]+"\t");
            for(int i=2;i<fields.length;i++){
                fw.write(gene_index.get(fields[i])+"\t");
                fw2.write(NodeScore[gene_index.get(fields[i])]+"\t");
            }
            fw.write("\n");
            fw2.write("\n");
        }
        fw.close();
        fw2.close();
    }
    
    
    
    //Utilities
    private double[] getcol(double[][] arr, int columnOfInterest ){
        double[] colArray = new double[numberofNode];
        for(int row = 0; row < numberofNode; row++)
        {
            colArray[row] = arr[row][columnOfInterest];
        }
        return colArray;
    }
            
     private double[] getrow(double[][] arr, int rowOfInterest ){
        double[] rowArray = new double[98];
//        System.out.println(arr.length+" "+rowArray.length+" "+rowOfInterest);
        for(int row = 0; row < 98; row++)
        {
//            System.out.println(row+" "+rowOfInterest);
            rowArray[row] = arr[rowOfInterest][row];
        }
        return rowArray;
    }
     
     private double[][] putcol(double[][] arr,double[] val, int colofInterest){
         for (int i=0;i<numberofNode;i++)
             arr[i][colofInterest] = val[i];
         return arr;
     }
     
     
     
}
