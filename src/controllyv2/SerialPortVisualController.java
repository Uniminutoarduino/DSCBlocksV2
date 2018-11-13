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
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         eventoBotAbrir();//Evento boton abrir.
         eventoBotCerrar();//evento boton cerrar.
       
    }

    private void eventoBotAbrir() {//Eventos de botones
          serialabrir.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
           @Override public void handle(ActionEvent e) {
             eventoAbrir();//Llamar a evento para programar target en este caso dsPIC 33FJ128GP804
        
        }

              
         });
    }

    private void eventoBotCerrar() {
            serialcerrar.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
           @Override public void handle(ActionEvent e) {
             eventoCerrar();//Llamar a evento para programar target en este caso dsPIC 33FJ128GP804
        
        }

                
         });
    }
    
    public void eventoAbrir() {//evento para abrir puerto serial
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

    
    
    private void eventoCerrar() {//Evento para cerrar puerto serial.
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

class SerialPortReader implements SerialPortEventListener { //Clase para la lectura de puerto serial
   
   @Override
   public void serialEvent(SerialPortEvent event) {//Event handler...for SerialPORT.
    if(event.isRXCHAR() && event.getEventValue() > 0){
        try {
        //Se ha detectado un dato en el puerto serial.
        //  StringBuilder message = new StringBuilder();//String builder procesa los datos de mejor forma que
        //string
        
        byte buffer[] = serialPort.readBytes();//ReadBytes.
        final String readed = new String(buffer);
        serialarea.appendText(readed);//Enviar texto recibido a textarea.
        } catch (SerialPortException ex) {
            Logger.getLogger(SerialPortVisualController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
   }
   }
}
}
    
    

