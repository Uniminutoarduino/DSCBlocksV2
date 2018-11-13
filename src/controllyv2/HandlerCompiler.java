/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllyv2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.scene.control.Alert;

/**
 *
 * @author Jonathan
 */
public class HandlerCompiler extends Thread{
    
    String Command;
    HandlerCompiler (String command){
        this.Command=command;
    }
    
    @Override
    public void run(){
      StringBuilder output = new StringBuilder();
                                    
                                    Process p;
                                    try {
                                        
                                        p = Runtime.getRuntime().exec(Command);
                                        BufferedReader reader =
                                                new BufferedReader(new InputStreamReader(p.getInputStream()));
                                        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                        p.waitFor();
                                        String line = "";
                                        String s = "";
                                        while ((s = stdError.readLine()) != null) {
                                            new GUIController().lineacomando.appendText(s+"\n");
                                        }
                                        while ((line = reader.readLine())!= null) {
                                            output.append(line).append("\n");
                                            new GUIController().lineacomando.appendText(line+"\n");
                                        }
                                        
                                    } catch (IOException | InterruptedException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error compiler path location");
                                        alert.setHeaderText("Error in access...");
                                        alert.setContentText("The compiler location is mistaken, please configure a new project with the properly route of the compiler...");
                                        alert.showAndWait();
                                    }
                                    //return output.toString();  
    }
}
