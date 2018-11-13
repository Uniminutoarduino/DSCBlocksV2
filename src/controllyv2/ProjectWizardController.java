 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllyv2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jssc.SerialPortList;

/**
 * FXML Controller class
 *
 * @author Jonathan
 */
public class ProjectWizardController implements Initializable {//This scene opens the project Wizard.
   @FXML
    public Button Projectlocation;//Button for the project
    @FXML
    private Button Compilerlocation;//Button for compilerLocation
    @FXML
    private Button Aceptar;//Button for OK
    @FXML
    private Button Cancelar;//Button forCancel
    @FXML
    private CheckBox Board1;//CheckBox Board dsPIC33FJ128GP804.
     @FXML
    private CheckBox Board2;//CheckBox Board dsPIC33FJ128MC802.


    public ListView PortView; //Listiview for the COM ports Available
    public String[] portNames;//String with the COM ports Available
    public static String PortNameSel; //Puerto seleccionado
    @FXML
    TextField Rutac;
    /**
     * Initializes the controller class.
     */
    static WebView viewb; //Instance of the WebView.
    //metodos cambian...
    public static String ProjectPath=null;
    public static int BoardType=-1; //Board type.
    static boolean ProjectconfigureOK=false;
    public String CompilerPath=null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       final ObservableList PortsAvailable = FXCollections.observableArrayList();//Crear port List
        String ruta=new Filereaderwriter().Openfile("src/Route/Route.txt");//Route for the compiler XC16.
        if(!ruta.isEmpty()){
            Rutac.setText(ruta);//Show route on specific text
            CompilerPath=ruta;//Compiler route on specific route.
        }
        final Stage dialogStage = new Stage();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final DirectoryChooser directoryChoosercomp = new DirectoryChooser();
        ListingPorts(PortsAvailable);
        PortView.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> ov, String t, String t1) {
              PortNameSel=t1;
           }
           
        });
        Board1.setOnAction(e -> handleButtonAction(e));
        Board2.setOnAction(e -> handleButtonAction(e));

       //Select folder location
        Projectlocation.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                 directoryChooser.setTitle("Select your project location...");
                  File file = directoryChooser.showDialog(null);
                    if (file != null) {
                        ProjectPath=file.getAbsolutePath();
                        //System.out.println(file.getAbsolutePath());
                       // openFile(file);
                    }
                    }
                    
                        });
        //Code Select compiler location
         Compilerlocation.setOnAction(new EventHandler<ActionEvent>() {//Event for compiler location...
            @Override public void handle(ActionEvent e) {
                 directoryChoosercomp.setTitle("Select your compiler location...");
                  File file = directoryChoosercomp.showDialog(null);
                    if (file != null) {
                        CompilerPath=file.getAbsolutePath();
                        Rutac.setText(CompilerPath);
                        new Filereaderwriter().writerroute(CompilerPath);//Writes the new route for the compiler.
                    }
                    }
                    
                        });
         //Code aceptar
          Aceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(ProjectPath!=null&&CompilerPath!=null&&BoardType!=-1){
                     LoadBlockly();//Load Blockly
                     CopiarArchivosLibrearias();
                     ProjectconfigureOK=true;
                     new Path().PathR(ProjectPath, CompilerPath);//Send the parameters for the compiler
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                    }else{
                       ProjectconfigureOK=false;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error in path locations or board");
                        alert.setHeaderText("Error in access...");
                        alert.setContentText("Please check both the project location, compiler location or board selected...");
                        alert.showAndWait();

                }
            }

       
            });    
              //Code to  cancel event button...
             Cancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                        final Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        final Image imageWarning = new Image(getClass().getResourceAsStream("about.png"));//Detectar imagen
                        final Button Cerrar=new Button("Accept");//Crear boton.
                        Cerrar.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent arg0) {
                               ((Node)(arg0.getSource())).getScene().getWindow().hide();  
                            }
                        });
                        Text t = TextBuilder.create().text("This is a text sample").build();
                        t.setText("Please first configure project settings in the file menu...\n");
                        t.setFont(Font.font ("Verdana", 14));
                        t.setFill(Color.RED);
                        dialogStage.setScene(new Scene(VBoxBuilder.create().
                        children(t, Cerrar).                 
                        //children(new Text("Controlly es una plataforma de codigo abierto\nbasada en Blockly para el proyecto ControlBlocks Card.\nCreditos: Designer: (Ing Jonathan Alvarez Ariza) Tecnologia en electronica UMD, semillero de control automatico.\nContacto:jalvarez@uniminuto.edu\n"), Cerrar).
                        alignment(Pos.CENTER).padding(new Insets(5)).build()));
                        dialogStage.setTitle("Message...");
                        dialogStage.getIcons().add(imageWarning);
                        dialogStage.showAndWait();
                        ((Node)(e.getSource())).getScene().getWindow().hide();//Ocultar ventana de project wizard.
                            }
            
                        });
        
           
    }

   
    private void ListingPorts(ObservableList PortsAvailable) {//List available ports
        portNames = SerialPortList.getPortNames();  
        //en el sistema
                            for(int i = 0; i < portNames.length; i++){
                              //System.out.println(portNames[i]);
                              PortsAvailable.add(portNames[i]);
                            }
        PortView.setItems(PortsAvailable);
    }
    
    public void CargarWebView(WebView view) {
         viewb=view;
         //new GUIController().CargarWebview(viewb);
      }
    
    public void LoadBlockly(){//Cargar Blockly...
      WebView view=viewb;
      System.out.println(view.getWidth());
      new GUIController().CargarWebview(viewb);
    }
    
     public void CopiarArchivosLibrearias() {//This method copies the libraries needed for the APP into user's folder.
        //funcionamiento de la interfaz.
       try {
           File FolderSource=new File("src/osa");
           File FolderDest=new File(ProjectPath+"/osa");
           File source = new File("src/libraries/user.c");
           File dest = new File(ProjectPath+"/user.c");
           File sourceb = new File("src/libraries/user.h");
           File destb = new File(ProjectPath+"/user.h");
           File sourcec = new File("src/libraries/control.h");
           File destc = new File(ProjectPath+"/control.h");
           File sourced = new File("src/libraries/control.c");
           File destd = new File(ProjectPath+"/control.c");
           File sourcee = new File("src/libraries/Data.csv");
           File deste = new File(ProjectPath+"/Data.csv");
           File sourcef = new File("src/libraries/Hardwareprofile.h");
           File destf = new File(ProjectPath+"/Hardwareprofile.h");
           new Filereaderwriter().copyFileUsingChannel(source,dest);//Copiar archivos de usuario en
           //lugar especificado para el poryecto.
           new Filereaderwriter().copyFolder(FolderSource,FolderDest);//Copiar folder de RTOS osa
           new Filereaderwriter().copyFileUsingChannel(sourceb,destb);
           new Filereaderwriter().copyFileUsingChannel(sourcec,destc);
           new Filereaderwriter().copyFileUsingChannel(sourced,destd);
           new Filereaderwriter().copyFileUsingChannel(sourcee,deste);
           new Filereaderwriter().copyFileUsingChannel(sourcef,destf);
       } catch (IOException ex) {
           Logger.getLogger(ProjectWizardController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    public void handleButtonAction(ActionEvent e) {//Check what board was selected...
        if(Board1.isSelected()){
        BoardType=0;
        }else if (Board2.isSelected()){
        BoardType=1;    
        }
    }

}



    

    

