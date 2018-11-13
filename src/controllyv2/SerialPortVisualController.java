/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllyv2;



import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * FXML Controller class
 *
 * @author Jonathan
 */
public class SerialPortVisualController implements Initializable {

    /**
     * Initializes the controller class.
     */
   
    @FXML
    public TextArea serialarea;
    @FXML
    private Button serialabrir;
    @FXML
    private Button serialcerrar;
    SerialPort serialPort;
    @Override
    public void initialize(URL url, ResourceBundle rb) {//This class opens the Serial Visualizer.
        // TODO
        
         eventoBotAbrir();//Event for the button open.
         eventoBotCerrar();//Event for the button close.
       
    }

    private void eventoBotAbrir() {//Event handler for the buttons open, close
          serialabrir.setOnAction(new EventHandler<ActionEvent>() {//Open
           @Override public void handle(ActionEvent e) {
             eventoAbrir();
        
        }

              
         });
    }

    private void eventoBotCerrar() {
            serialcerrar.setOnAction(new EventHandler<ActionEvent>() {//Close
           @Override public void handle(ActionEvent e) {
             eventoCerrar();
        
        }

                
         });
    }
    
    public void eventoAbrir() {//Method to open SerialPort
                  serialPort = new SerialPort(ProjectWizardController.PortNameSel);//Seleccion de puerto establecido.
        if(serialPort.isOpened()){
            
            try {
                serialPort.closePort();
            } catch (SerialPortException ex) {
                Logger.getLogger(SerialPortVisualController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error serial port");//Si el puerto esta activado por otra aplicación entonces generar alarma.
                alert.setHeaderText("Error serial port...");
                alert.setContentText("The serial port is opened by another application...");
                alert.showAndWait();
            }
               }else{
           
            try {
                serialPort.openPort();//Open serial port
                serialPort.setParams(SerialPort.BAUDRATE_57600, //Baudrate de la tarjeta 57600.
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
            } catch (SerialPortException ex) {
                Logger.getLogger(SerialPortVisualController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
                    }
              }

    
    
    private void eventoCerrar() {//Method to close serial port
                     if(serialPort.isOpened()){//Si puerto serial esta abierto.
                         try {
                             serialPort.removeEventListener();
                             serialPort.closePort();//Close PORT
                         } catch (SerialPortException ex) {
                             Logger.getLogger(SerialPortVisualController.class.getName()).log(Level.SEVERE, null, ex);
                             Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error serial port");
                            alert.setHeaderText("Error serial port...");
                            alert.setContentText("The serial port is not opened...");
                            alert.showAndWait();
                         }
                    }
                }

class SerialPortReader implements SerialPortEventListener { //Class to adds a listener for data incoming.
   
   @Override
   public void serialEvent(SerialPortEvent event) {//Event handler...for SerialPORT.
    if(event.isRXCHAR() && event.getEventValue() > 0){
        try {
        //Se ha detectado un dato en el puerto serial.
        //  StringBuilder message = new StringBuilder();//String builder procesa los datos de mejor forma que
        //string
        
        byte buffer[] = serialPort.readBytes();//ReadBytes.
        final String readed = new String(buffer);
        serialarea.appendText(readed);//Send bytes to the Text are for the Serial Visualizer
        } catch (SerialPortException ex) {
            Logger.getLogger(SerialPortVisualController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
   }
   }
}
}
    
    

