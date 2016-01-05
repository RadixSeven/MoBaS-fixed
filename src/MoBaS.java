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
import java.util.Vector;
import javax.swing.JFrame;

/**
 *
 * @author Marzieh
 */
public class MoBaS {

    /**
     * @param args the command line arguments
     */
    public MoBaS(String associationFile, String mappingFile, String ppiFileName, String backgroundAssoc, int whichPanel) throws FileNotFoundException, IOException {
       
        String method_forReward = "multiply";     
        String method_forPenalty = "multiply";
        
      
        HashMap<String,String> mapping = Mapping.setMapping(mappingFile);
        /**
         * Compute Original subnetworks
         */
        if(whichPanel == 4){
            PPN ppi = new PPN();
            // Read Protein Names
            ppi.getPPIname(ppiFileName);
            //get the score of each node
            double[] nodescore = ppi.InitNodeScore(associationFile,mapping);
            //You can choose to normalize the score of nodes through the netwrok or not
    //        nodescore = ppi.NormalizeNodeScore(nodescore);
            //get the background score of each node based on permuted genotype/phenotype           
            double[] permscore = ppi.NodeBackgroundScore(backgroundAssoc,mapping);
            //Create PPI Network  
            ppi.createPPI(ppiFileName,nodescore,method_forReward);
            //Start finding Components
            ComponentFinder CF = new ComponentFinder(ppi.ppi, nodescore, permscore, method_forReward, method_forPenalty);
            Components cps =  CF.cmp;

            String filename = "Original_subnet";
            FileWriter fw = new FileWriter(new File(filename));

    //        System.out.println(sco[ii]+" "+sco[jj]+" "+sco[kk]+" "+cps.components.elementAt(0).size());
            for(int i=0;i<cps.components.size();i++){
                Vector c = cps.components.elementAt(i);
                fw.write(c.size()+"\t"+cps.componentScore.elementAt(i)+"\t");
                for(int j=0;j<c.size();j++){
                    fw.write(ppi.index_gene.get((Integer)c.elementAt(j))+"\t");
                }
                fw.write("\n");
            }
            fw.close();
            } 
        /**
         * Compute permuted genotype subnetworks
         */
        if(whichPanel == 5){
             PPN ppi = new PPN();
            // Read Protein Names
            ppi.getPPIname(ppiFileName);
            //get the background score of each node based on permuted genotype/phenotype           
                double[] permscore = ppi.NodeBackgroundScore(backgroundAssoc,mapping);
            //get the score of each node from permuted data
            File permutedFolder = new File(backgroundAssoc);
            for (File fileEntry : permutedFolder.listFiles()){
                 double[] nodescore = ppi.InitNodeScore(fileEntry.getPath(),mapping);
                //You can choose to normalize the score of nodes through the netwrok or not
        //        nodescore = ppi.NormalizeNodeScore(nodescore);
                
                //Create PPI Network  
                ppi.createPPI(ppiFileName,nodescore,method_forReward);
                //Start finding Components
                ComponentFinder CF = new ComponentFinder(ppi.ppi, nodescore, permscore, method_forReward, method_forPenalty);
                Components cps =  CF.cmp;

                String filename = fileEntry.getPath()+"_permutedSubnet";
                FileWriter fw = new FileWriter(new File(filename));

        //        System.out.println(sco[ii]+" "+sco[jj]+" "+sco[kk]+" "+cps.components.elementAt(0).size());
                for(int i=0;i<cps.components.size();i++){
                    Vector c = cps.components.elementAt(i);
                    fw.write(c.size()+"\t"+cps.componentScore.elementAt(i)+"\t");
                    for(int j=0;j<c.size();j++){
                        fw.write(ppi.index_gene.get((Integer)c.elementAt(j))+"\t");
                    }
                    fw.write("\n");
                }
                fw.close();
            } 
        }
         /**
         * Compute permuted PPI subnetworks
         */
        if(whichPanel == 6){
             PPN ppi = new PPN();
            // Read Protein Names
             File permutedFolder = new File(ppiFileName);
            ppi.getPPIname(permutedFolder.listFiles()[1].getPath());
            //Score each node
              double[] nodescore = ppi.InitNodeScore(associationFile,mapping);
               
            //get the background score of each node based on permuted genotype/phenotype           
                double[] permscore = ppi.NodeBackgroundScore(backgroundAssoc,mapping);
                
            //get the score of each node from permuted data
            
            for (File fileEntry : permutedFolder.listFiles()){
                //Create PPI Network  
                ppi.createPPI(fileEntry.getPath(),nodescore,method_forReward);
                //Start finding Components
                ComponentFinder CF = new ComponentFinder(ppi.ppi, nodescore, permscore, method_forReward, method_forPenalty);
                Components cps =  CF.cmp;

                String filename = fileEntry.getPath()+"_permutedPPISubnet";
                FileWriter fw = new FileWriter(new File(filename));

        //        System.out.println(sco[ii]+" "+sco[jj]+" "+sco[kk]+" "+cps.components.elementAt(0).size());
                for(int i=0;i<cps.components.size();i++){
                    Vector c = cps.components.elementAt(i);
                    fw.write(c.size()+"\t"+cps.componentScore.elementAt(i)+"\t");
                    for(int j=0;j<c.size();j++){
                        fw.write(ppi.index_gene.get((Integer)c.elementAt(j))+"\t");
                    }
                    fw.write("\n");
                }
                fw.close();
            } 
        }
        
        
    }

  
}
