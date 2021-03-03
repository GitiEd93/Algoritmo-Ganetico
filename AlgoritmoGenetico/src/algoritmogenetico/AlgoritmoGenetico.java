/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico;

/**
 *
 * @author EduardoG
 */
import java.util.Random;


public class AlgoritmoGenetico {

    private int generaciones=0;

    public static void main(String[] args) {
        
        //1.-Inicializar la población
        
        double poblacionIni[][] = new double[100][3];
        
        
        for(int i=0;i<100;i++){
            
            //x1 y x2 entre -20 y 20
            int x1 = (int)(Math.random()*((-20)-20+1))+20;
            int x2 = (int)(Math.random()*((-20)-20+1))+20;
        
            //función yi=f(Pi)
        
            double yi=Math.pow(x1,2)*Math.sin(x1)+Math.pow(x2,2)*Math.sin(x2);
        
            poblacionIni[i][0]=yi;
            poblacionIni[i][1]=x1;
            poblacionIni[i][2]=x2;
            
        }
        
        
        AlgoritmoGenetico obj= new AlgoritmoGenetico();
        obj.proceso(poblacionIni);
           
    }
    
    public  void proceso(double poblacion[][]){
        
        //2.-Seleccionar los padres
        //Arreglo donde se almacenan los padres con derecho a hijos
        double reproductores[][] = new double[50][3];
        
        //Método del Torneo Elitista
        
        for(int i=0; i<50; i++){
 
            int lugar1 = (int)(Math.random()*((0)-99+1))+99;
            int lugar2 = (int)(Math.random()*((0)-99+1))+99;
            
            //Se verifica que no se enfrenten los mismos
            if(lugar1!=lugar2){
                //Se queda el mejor
                if(poblacion[lugar1][0]>poblacion[lugar2][0]){
                    reproductores[i][0]=poblacion[lugar1][0];
                    reproductores[i][1]=poblacion[lugar1][1];
                    reproductores[i][2]=poblacion[lugar1][2];
                      
                }else{
                    reproductores[i][0]=poblacion[lugar2][0];
                    reproductores[i][1]=poblacion[lugar2][1];
                    reproductores[i][2]=poblacion[lugar2][2];
                }
            }else{
                i--;
            }
            
        }
        
        
        //3.-Hacer el cruzamiento
        //Area externa
        
        double NuevaGen[][] = new double[100][3];
        
        
        for(int i=0;i<100;i++){
            
            //Alpha siendo número random entre Vmin y Vmax
            double alpha = (int)(Math.random()*((0)-100+1))+100;
            alpha=alpha/100;
            
            //Se selecciona la posición de los dos padres para el cruzamiento
            int lugar1 = (int)(Math.random()*((0)-49+1))+49;
            int lugar2 = (int)(Math.random()*((0)-49+1))+49;
            
            if(lugar1!=lugar2){
                
                //Fórmula de cruzamiento
                double x1=alpha*reproductores[lugar1][1]+(1-alpha)*reproductores[lugar2][1];
                double x2=alpha*reproductores[lugar1][2]+(1-alpha)*reproductores[lugar2][2];
                
                NuevaGen[i][1]=x1;
                NuevaGen[i][2]=x2;
                
            }else{
                i--;
            }
            
      
        }
        
         //4.-Mutación
        
        for(int i =0;i<100;i++){
            //Se genera el número Gaussiano
            Random random = new Random();
            double g1 = 0.02*random.nextGaussian();
            double g2 = 0.02*random.nextGaussian();
            
            //Se mutan los Hijos
            NuevaGen[i][1]=NuevaGen[i][1]+g1;
            NuevaGen[i][2]=NuevaGen[i][2]+g2;
        
            NuevaGen[i][0]=Math.pow(NuevaGen[i][1],2)*Math.sin(NuevaGen[i][1])+Math.pow(NuevaGen[i][2],2)*Math.sin(NuevaGen[i][2]);
            
        }
        
        generaciones++;
        
        if(generaciones==2000){
            System.out.println("\n\n ULTIMA GENERACION:\n");
            int mejor=0;
            for(int i=0;i<100;i++){
                System.out.println((i+1)+": yi = "+NuevaGen[i][0]+"; x1 = "+NuevaGen[i][1]+"; x2 = "+NuevaGen[i][2]);
                if(i>0){
                    if(NuevaGen[i][0]>NuevaGen[mejor][0]){
                        mejor=i;
                    }
                }
            }
            System.out.println("\n\nEL PUNTO MAS OPTIMO ES:");
            System.out.println((mejor+1)+": yi = "+NuevaGen[mejor][0]+"; x1 = "+NuevaGen[mejor][1]+"; x2 = "+NuevaGen[mejor][2]);
        }
        
        //5.-Seleccionar y repetir
        
        while(generaciones<2000){
            proceso(NuevaGen);
            
        }
        
    }
    
    
}
