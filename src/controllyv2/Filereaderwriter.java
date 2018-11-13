/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllyv2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan
 */
public class Filereaderwriter {
    
    
public String Openfile(String url){ //File open and reader in specific url.       
       FileReader fr = null; //File reader
       String lines = " ";
       String linesRead;
    try {
        fr = new FileReader(url);//Create instance of file
        BufferedReader textread=new BufferedReader(fr);//read file
        while((linesRead=textread.readLine())!=null){
          lines=linesRead;
        }
        
        textread.close();//Close reader file.
        
        
    } catch (FileNotFoundException ex) {
        Logger.getLogger(Filereaderwriter.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Filereaderwriter.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lines;
   }


public String OpenfileXML(String url){ //File open and reader in specific url.    XML file   
       FileReader fr = null; //File reader
       String lines = " ";
       String linesRead;
    try {
        fr = new FileReader(url);//Create instance of file
        BufferedReader textread=new BufferedReader(fr);//read file
        while((linesRead=textread.readLine())!=null){
          lines+=linesRead;//Append all lines of file XML creted
        }
        
        textread.close();//Close reader file.
        
        
    } catch (FileNotFoundException ex) {
        Logger.getLogger(Filereaderwriter.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Filereaderwriter.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lines;
   }

 public int writerroute(String url){//Escribir la ruta del compilador en el lugar indicado....
     //Este archivo es Route...
   FileWriter fw;
    try {
        fw = new FileWriter("src/Route/Route.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(url);
        bw.close();
        return 0;
    } catch (IOException ex) {
        Logger.getLogger(Filereaderwriter.class.getName()).log(Level.SEVERE, null, ex);
        return -1;
    }
 }
 
 
 
 
  public int writeXML(String url, String TextXML){//Writes XML file of the blocks of Blockly.
   FileWriter fw;
    try {
        fw = new FileWriter(url);//Sabe file in the especific URL
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(TextXML);
        bw.close();
        return 0;
    } catch (IOException ex) {
        Logger.getLogger(Filereaderwriter.class.getName()).log(Level.SEVERE, null, ex);
        return -1;
    }
 }
  
public int WriteCSV() throws IOException{//Write specific .CSV file in URL.
    
   FileWriter fw;
   char i=0;
   char b=0;
   int variables=Integer.parseInt(GUIController.Varplotter); //Numero de variables mostradas por el 
   String Variableapp=""; //Este string contiene la linea que se necesita para liveplotter deacuerdo a 
   //su formato.
   System.out.println("Hello"+GUIController.Varplotter);
   //usuario antes de iniciar 
   String Cero="";
    try {
        fw = new FileWriter(ProjectWizardController.ProjectPath+"/Data.csv");//Save file in the especific URL con el numbre Data.csv.
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append("##;##");
        bw.append("\r\n");
        bw.append("@Controlly DEMOV1");
        bw.append("\r\n");
        for(i=1;i<=variables;i++){//Anexar eal vector la ruta estructurada.
          Variableapp+="Variable"+Character.toString((char) (i+97));
          Cero+="0";//Append cero para prevenir errores.
          if(i<=(variables-1)){ //Colocar ; a la linea de la variable.
          Variableapp+=";";
          Cero+=";";
        }
        }
        bw.append(Variableapp);
        bw.append("\r\n");
        bw.append(Cero);
        bw.append("\r\n");
        bw.append("\r\n");
        bw.flush();
        bw.close();
        return 0;
    } catch (IOException ex) {
        Logger.getLogger(Filereaderwriter.class.getName()).log(Level.SEVERE, null, ex);
        return -1;
    }
 }
 
 
  public static void copyFileUsingChannel(File source, File dest) throws IOException {//Copy files necesaries for compilation.
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
           }finally{
               sourceChannel.close();
               destChannel.close();
           }
    }
  
  
    public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException //Este metodo copia todo el directorio con los subarchivos de este.
    {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory())
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists())
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files)
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }
  
  public void SetCompilerLocation(String ProjectLoc){//Este m[etodo lee la ruta del compilador.
      String ruta=new Filereaderwriter().Openfile("src/Route/Route.txt");//Abrir y leer archivo de la ruta por defecto del compilador XC16.
        if(!ruta.isEmpty()){
            new Path().PathR(ProjectLoc, ruta);//Configurar las rutas del projecto y del compilador en la clase Path...
        }
  }
  public void deleteFile(String Ruta){
  File file = new File(Ruta);

    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
  }
}
