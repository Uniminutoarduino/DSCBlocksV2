/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllyv2;

/**
 *
 * @author Jonathan
 */
public class Path {
   static String Project=null,Compiler=null;
    
   public void PathR(String ProjectPath, String CompilerPath){//Metodo para acceder a path de proyecto y compilador.
 
      Project=ProjectPath;//Asignar rutas de projecto y compilador.
      Compiler=CompilerPath;
      Project=Project.replaceAll("\\\\","/");//Cambiar estilo de ruta de compilador y projecto
      Compiler=Compiler.replaceAll("\\\\","/");
      System.out.println(Project+" "+Compiler);
    
   }
   

    
}
