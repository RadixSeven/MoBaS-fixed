/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



public class Statistics
{
    public static double[] data;
    public static int size;

    public Statistics(double[] data)
    {
        this.data = data;
        size = data.length;
    }

    public static double getMean()
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
            return sum/size;
    }

        public static double getVariance()
        {
            double mean = getMean();
            double temp = 0;
            for(double a :data)
                temp += (mean-a)*(mean-a);
                return temp/size;
        }

        public static double getStdDev()
        {
            return Math.sqrt(getVariance());
        }

       public double[] normalized(){
           double mean = getMean();
           double std = getStdDev();
           double[] ndata=new double[size];
           for(int i=0;i<size;i++)
               ndata[i] = (data[i] - mean) / std ;
           return ndata;
       }
}