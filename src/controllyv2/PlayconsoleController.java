/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllyv2;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * FXML Controller class
 *
 * @author Jonathan
 */
public class PlayconsoleController implements Initializable {
@FXML 
public Button Play; //Play button and stop button.
public Button Stop;
public Slider Sliderc;//Slider...
public Label RefVal;
 FileWriter writer = null;
 List<Float> listdatos = new ArrayList<Float>();//Arraylist de flotantes. lista de datos.
    /**
     *  FileWriter writer = null;
     * Initializes the controller class.
     */
    static SerialPort serialPort;//Serial Port event
    int contadorvariables=0; //Conteo de variables de acuerdo a lo definido por el usuario
    StringBuilder message = new StringBuilder();
    StringBuilder messageB = new StringBuilder();//MessageB gets the data incoming the serial port
    //esto se hace porque se recibe basura del UART y se deben validad los datos...
    public void initialize(URL url, ResourceBundle rb) {//Method to inizialize the SerialPort.
        // TODO
        Play.setGraphic(new ImageView(new Image("/Images/play.png")));
        Stop.setGraphic(new ImageView(new Image("/Images/stop.png")));
        Play.setTooltip(new Tooltip("Open port and run plotter"));
        Stop.setTooltip(new Tooltip("Stop plotter close port"));
        Play.setText("");
        Stop.setText("");
    
        serialPort = new SerialPort(ProjectWizardController.PortNameSel);//Selects the serial port (COMx) of the User.
        Play.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
             
                try {
                    try { 
                    writer = new FileWriter(ProjectWizardController.ProjectPath+"/Data.csv",true);
                } catch (IOException ex) {
                    Logger.getLogger(PlayconsoleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    if(serialPort.isOpened()){
                        serialPort.closePort();
                    }else{
                    serialPort.openPort();//Open serial port
                    serialPort.setParams(SerialPort.BAUDRATE_57600, //Baudrate de la tarjeta 57600.
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlayconsoleController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error in SerialPort");
                    alert.setHeaderText("Error in access...");
                    alert.setContentText("Please, close the port and try again...");
                    alert.showAndWait();
                }
                    }
                    
                        });
         Stop.setOnAction(new EventHandler<ActionEvent>() {//Event for the STOp button. It stops the data sampling of the serial port.
            @Override public void handle(ActionEvent e) {
            
                try {
                    if(serialPort.isOpened()){//Check if port is opened
                    serialPort.removeEventListener();
                    serialPort.closePort();//Close PORT
                    }
                    try {
                        writer.close();
                    } catch (IOException ex) {
                        Logger.getLogger(PlayconsoleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlayconsoleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }
                    
                        });

         
        /* Sliderc.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {//New val 
                //Event listener for Slider.
               String Reference;
               RefVal.setText(String.valueOf(t1.floatValue()));
               if(serialPort.isOpened()){//If serial Port is opened send the value of reference.
                   try {
                   //If serial port is opened then send the
                   //specific value of reference.
                      BigDecimal da = new BigDecimal(t1.doubleValue());
                       da = da.setScale(2, RoundingMode.HALF_UP);//2 digitos decimales para escala
                       Reference=String.valueOf(da);//Convertir el valor de da con dos digitos decimales.
                       Reference+="\n";//Anexar salto de linea
                       byte comandoref=0x04;//Comando reset sobre dsPIC.
                       serialPort.writeByte(comandoref);//Numero 4 cambia la referenci sobre el sistema.
                       System.out.println(Reference);
                       serialPort.writeString(Reference);//Send reference to System.
                   } catch (SerialPortException ex) {
                       Logger.getLogger(PlayconsoleController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   
               }
            }
         
        });*/
         
        
    } 
    
  
    
    class SerialPortReader implements SerialPortEventListener { //Class for the event Serial port
        public void serialEvent(SerialPortEvent event) {//Event handler...for SerialPORT.
    if(event.isRXCHAR() && event.getEventValue() > 0){
        try {
            byte buffer[] = serialPort.readBytes();//ReadBytes.
            for (byte b: buffer) {
                    if (b == '\n') {//If there exists a linebreak a new variable is received.
                       contadorvariables++; //
                        if(contadorvariables>=Integer.parseInt(GUIController.Varplotter)){//If linebreak correspond to the number of variables
                         //indicated by the user, then...
                            
                    //    Platform.runLater(new Runnable() {//Para ejecutar una tarea simple o compleja
                            //se instancia a Platform. runnable.
                        //    @Override public void run() {
                        //System.out.println(message);
                             contadorvariables=0;//Reiniciar contador de variables.
                              if(Integer.parseInt(GUIController.Varplotter)>1){//Si valplotter>1 hacer esto
                                try {
                                    //processMessage(toProcess);
                                    
                                    
                                //    try{
                                    int Contadorvalorescorrectos=0;//Checks if the data are correct
                                    String[] array = message.toString().split(";", -1);
                                    float[] datos=new float[Integer.parseInt(GUIController.Varplotter)];
                                    for(int valarr=0;valarr<array.length;valarr++){
                                        try{
                                        double num=Double.parseDouble(array[valarr]);
                                        datos[valarr]=Float.valueOf(array[valarr]);
                                        Contadorvalorescorrectos++;
                                        }catch (NumberFormatException nfe){
                                            
                                        }
                                    }
                                    //listdatos.add(Float.valueOf(messageB.toString()));
                                    //System.out.println(Arrays.toString(listdatos.toArray()));
                                    //System.out.println(messageB);
                                    //System.out.println(messageB);
                                    //double num=Double.parseDouble(Cvariable);//Convertir el numero para saber si es o no un numero de lo contrario 
                                   // if(listdatos.size()==Integer.parseInt(GUIController.Varplotter)){//Si se agregaron los datos en forma correcta
                                        //agregar para plotter.
                                    message.append("\r\n"); //Append message
                                    if(Contadorvalorescorrectos==Integer.parseInt(GUIController.Varplotter)){
                                    Arrays.sort(datos);
                                    String valueC="";
                                     for(int valarr=0;valarr<Integer.parseInt(GUIController.Varplotter);valarr++){
                                         valueC+=String.valueOf(datos[valarr]);
                                         if(valarr<Integer.parseInt(GUIController.Varplotter)-1){
                                         valueC+=";";
                                         }
                                      }
                                    System.out.println(valueC);
                                    writer.append(valueC); //Write data incoming of the serial port in the .CSV file.
                                    writer.flush();
                                    }
                                    //listdatos.clear();
                                    Contadorvalorescorrectos=0;
                                   // }catch (NumberFormatException nfe){
                                  //   System.out.println("Error");   
                                  //  }
                                        
                                   
                                } catch (IOException ex) {
                                    Logger.getLogger(PlayconsoleController.class.getName()).log(Level.SEVERE, null, ex);
                                } 
                              }else{
                                  try{
                                    String Cvariable;
                                    Cvariable=message.toString();
                                    double num=Double.parseDouble(Cvariable);//Convertir el numero para saber si es o no un numero de lo contrario  
                                     message.append("\r\n"); //LineBreak for each data line.
                                    writer.append(message); //Write data incoming of the serial port in the .CSV file.
                                    writer.flush();
                                  }catch(NumberFormatException nfe){
                                      
                                  } catch (IOException ex) {
                                Logger.getLogger(PlayconsoleController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                              }
                                
                         //  }
                       // });
                        message.setLength(0);//Restart the string that contains all data for new sampling.
                        }else{
                        if(contadorvariables<=(Integer.parseInt(GUIController.Varplotter)-1)){
                        message.append(";"); //Append ;. The columns of the CSV file is delimited by this character.
                        }
                        }
                        
                
                   }else{
                        
                      message.append((char)b);  
                      //messageB.append((char)b);  
                    }                
        }
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
            System.out.println("serialEvent");
        }
    }
}
   }
    
}
