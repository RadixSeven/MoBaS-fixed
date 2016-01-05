
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marzieh
 */
public class Mapping {
    
   
    public static HashMap<String,String> setMapping(String mappingFile) throws FileNotFoundException{
        HashMap<String,String> snp_gene = new HashMap<>();
        Scanner scan = new Scanner(new File(mappingFile));
        while(scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split("\t");
            snp_gene.put(fields[0], fields[1]);            
        }
        return snp_gene;
              
    }
    
    
    
}
