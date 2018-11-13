/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 NOTES: Although the name of the methods and classes are in spanish, the comments are in english, follow the structure...
 */
package controllyv2;

import static controllyv2.ProjectWizardController.ProjectPath;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.swing.JOptionPane;
import org.LiveGraph.LiveGraph;

/**
 * FXML Controller class
 *
 * @author Jonathan
 */
public class GUIController implements Initializable {
    @FXML
    private Button wizard;
    @FXML
    private Button save;
    @FXML
    private Button upload;
    @FXML
    private Button usb;
    @FXML
    private Button plot;
    @FXML
    private Button serialport;
    @FXML
    private Button help;
    @FXML
    private ToolBar Toolbar;
    @FXML
    private MenuBar Toolmenu;
    @FXML
    public WebView view;
    @FXML
    public TextArea lineacomando;
    public static Stage stage;
    @FXML
    private MenuItem configure;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem program;
    @FXML
    private MenuItem plotb;
    @FXML
    private MenuItem about;
    @FXML
    private ProgressBar progreso;//Progress bar
    @FXML
    private ProgressIndicator indicador;//Progress indicator.
    int exitValueP1=-1,exitValueP2=-1,exitValueP3=-1;
    static String val="";//
    static String RutaProyecto; //Project Path
    static int OpcionButton=0; //Opcion is a variable to know if the user click on compile, save y load XML. The values are
    //1 compile, 2//save, 3//LoadXML
    static String Varplotter; //This variable gets the number of variables to plot
    int proceso=0;
    static WebView engines;
    public String[] Boards={"33FJ128GP804", "33FJ128MC802"}; //Boards selection in the app.
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {//Method to initialize the main scene(Java FX) if the APP.
        // TODO
        view.getEngine().loadContent("");//Always empty web page, in the starting of the application.
        ConfigurarGUI();//Method to load the images and events for the GUI.
        EventoBotConfigurar();//Method to configure the event for the button (Project Wizard) of the GUI.
        EventoMenuConfigurar();//Method to configure the events for the Menus in the GUI.
        EventoMenuCerrar();//Method to configure the event Close of the GUI.
        EventoBotSaveLoadXML();//Method to configure the events SAVE and LOAD of the GUI.
        eventoWebView();//Method to load Blockly in the WebView
        EventoBotprogramar();//Method to configure the event for the button (Program) of the GUI.
        EventoBotPlot();//Method to configure the event for the button (Plot) of the GUI.
        EventoBotSerial();//Method to configure the event for the button (SerialVisualizer) of the GUI.
        EventoBotayuda();//Method to configure the event for the button (Help) of the GUI.
        view.setContextMenuEnabled(false);
        createContextMenu(view);//Create menu for the GUI.
        
}
    
 

    public  void CargarWebview(WebView viewb) {//This method loads Blockly in the WebView.
        
        WebEngine engine=viewb.getEngine();
        String path = System.getProperty("user.dir");  //dir is the main folder of the 
        System.out.println(path);  
        if(ProjectWizardController.BoardType==0){//if board is dsPIC804, load this Blockly
        path +=   "/src/controllyv2/BlocklyOPt/demos/code/index.html";
        }else if(ProjectWizardController.BoardType==1){//if board is dsPIC802, load this Blockly
        path +=   "/src/controllyv2/BlocklyOPt/demos/code/index.html";    
        
        }
        path=path.replaceAll("\\\\", "/"); //Adjust the URL in correct form.
        System.out.println(path);
        engine.getLoadWorker().stateProperty().addListener(//This listener allows to prevent an error when a script
            //is invoked.
            new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                    if (newValue == Worker.State.SUCCEEDED) {
                    //BorrarWorkspace(engine);//clear workspace...
                }
            }
            });
        
        engine.setOnAlert(new EventHandler<WebEvent<String>>(){//This callback collect the code of the blocks

            @Override
            public void handle(WebEvent<String> arg0) {            
               val=arg0.getData();
               System.out.println(val);
            }

        });
        engine.setJavaScriptEnabled(true); //Enable JavaScript in the WebView.
        try{
        engine.load("file:///" + path);
        }catch(Exception Ex){
            System.out.println(Ex);
        }
    }

    public void ConfigurarGUI() {//This method configures the appareance (menus, buttons, toolbar) in the APP.
        Toolbar.setStyle("-fx-background-insets: 0.0, 0.0 0.0 0.0 0.0;");
        wizard.setGraphic(new ImageView(new Image("/Images/wizard.png")));
        save.setGraphic(new ImageView(new Image("/Images/save.png")));
        upload.setGraphic(new ImageView(new Image("/Images/upload.png")));
        usb.setGraphic(new ImageView(new Image("/Images/usb.png")));
        plot.setGraphic(new ImageView(new Image("/Images/plot.png")));
        configure.setGraphic(new ImageView(new Image("/Images/configure.png")));
        close.setGraphic(new ImageView(new Image("/Images/close.png")));
        program.setGraphic(new ImageView(new Image("/Images/program.png")));
        plotb.setGraphic(new ImageView(new Image("/Images/plotb.png")));
        about.setGraphic(new ImageView(new Image("/Images/about.png")));
        serialport.setGraphic(new ImageView(new Image("/Images/serial.png")));
        help.setGraphic(new ImageView(new Image("/Images/help.png")));
        save.setDisable(true);
        serialport.setDisable(true);
        plot.setDisable(true);//Desactivar boton plot...
    }
    
    
        public void createContextMenu(final WebView webView) {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem reload = new MenuItem("Reload");
        reload.setOnAction(new EventHandler<ActionEvent>() {//This menu allows to reload the WebView.
            @Override public void handle(ActionEvent e) {
                webView.getEngine().reload();
            }
        });
        MenuItem Cambiovariable = new MenuItem("Rename variable");//This Menu allows to rename a Blockly variable
        Cambiovariable.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            
                    CrearNuevaVariable();
                    }

               
             
        });
        MenuItem Copiar = new MenuItem("Copy");//This menu copy the content blocks in the respective tab.
        Copiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            
                        String selection = (String) webView.getEngine()
                        .executeScript("window.getSelection().toString()");//Copy the selected code in tha tab.
                        //de manera que se exporte mediante clipboard
                        final Clipboard clipboard = Clipboard.getSystemClipboard();
                        final ClipboardContent content = new ClipboardContent();
                        content.putString(selection);
                        clipboard.setContent(content);
                    }
        });
        
       // savePage.setOnAction(e -> System.out.println("Save page..."));
        MenuItem hideImages = new MenuItem("Hide Images");
      //  hideImages.setOnAction(e -> System.out.println("Hide Images..."));
        contextMenu.getItems().add(reload);
        contextMenu.getItems().add(Cambiovariable);
        contextMenu.getItems().add(Copiar);
        view.addEventHandler(MouseEvent.MOUSE_CLICKED,
        new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.SECONDARY)  
                contextMenu.show(view, e.getScreenX(), e.getScreenY());
        }
});
    }
        
        public void CrearNuevaVariable(){//This method renames a preloaded variable in Blockly.
            Dialog<Pair<String, String>> dialog = new Dialog<>(); //Create new dialog window
            dialog.setTitle("New variable's name dialog"); //Configure the title of the dialog
            dialog.setHeaderText("Please fill the information...");//Subtitle of the dialog
            dialog.setGraphic(new ImageView(new Image("/Images/variable.png")));//Icon Image in the dialog
            ButtonType OKnewvar = new ButtonType("OK", ButtonData.OK_DONE);//Add OK button to dialog
            dialog.getDialogPane().getButtonTypes().addAll(OKnewvar , ButtonType.CANCEL);

            // Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField oldvariable = new TextField(); //Shows a textfields for the user (old variable name, new variable name)
            oldvariable.setPromptText("Old variable");
            TextField newvariable = new TextField();
            newvariable.setPromptText("New variable");

            grid.add(new Label("Define the name of the variable to change:"), 0, 0);
            grid.add(oldvariable, 1, 0);
            grid.add(new Label("Define the new name of the variable:"), 0, 1);
            grid.add(newvariable, 1, 1);

            dialog.getDialogPane().setContent(grid);

            // Request focus on the username field by default.
            Platform.runLater(() -> oldvariable.requestFocus());

            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == OKnewvar ) {
                    //return new Pair<>(newvariable.getText(), oldvariable.getText());
                    if (newvariable.getText().trim().isEmpty()||oldvariable.getText().trim().isEmpty()){
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error variable's name");
                        alert.setHeaderText("Input error");
                        alert.setContentText("Please assign the names properly...");
                        alert.showAndWait();
                    }else{
                        EjecutarScriptnewVar(oldvariable.getText(),newvariable.getText());//Execute this function in Blockly,
                        //see the html file \src\controllyv2\BlocklyOPt\demos\code\index.html
                    }
                  
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

          
        }

    public void EjecutarScriptnewVar(String oldvar, String newvar) {//This method executes a Script for rename variable
         engineB.executeScript("Renamevar('"+oldvar+"','"+newvar+"')"); //
    }
    
    public void EventoBotConfigurar(){//This method adds an event for the button Project Wizard.
            wizard.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
            @Override public void handle(ActionEvent e) {
                loadProjectConfig();//Open scene with the project wizard
                    }
                    
                        });
    }
    
       public void EventoBotProgramar(){//Method to add an event for the button program.
            wizard.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                eventoProgramar();//Invoke the method program...
                //boton programar...
                    }
                    
                        });
    }
       
   public void EventoBotPlot(){//Method to add an event for the button Plot.
            plot.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                NumVariablesPlot();//Get the number of variables to plot.
                    }
                    
                        });
    }
   
   
      public void EventoBotSerial(){//Method to add an event for the button Serial.
            serialport.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loadSerialTerminal();//Load Serial Visualizer.
                    }
                    });
    }
       
     public void NumVariablesPlot() {//Dialog to get the number of variables to plot.
     TextInputDialog dialog = new TextInputDialog("# of variables to plot");
     dialog.setTitle("Variables to plot");
     dialog.setHeaderText("Please, indicate the number of variables to plot. Don't forget to put the respective \ncode through Controlly in otherwise the plotter doesn't plot...");
     dialog.setContentText("Please indicate the number to variables to plot:");

     // Traditional way to get the response value.
     Optional<String> result = dialog.showAndWait();
     if (result.isPresent()){//Si resultado esta...
         //System.out.println("Your name: " + result.get());
         Varplotter=result.get(); //Get the number of variables to plot.
     
            
            try{
             double num=Double.parseDouble(Varplotter);//Convertir the input for the number of variables in double.
         try {
             new Filereaderwriter().WriteCSV(); //Load the .CSV file for the Plotter. LiveGraph needs a CSV file in order
             //to save the data of the user.
             LlamarPlotterConsole();//LLamar a plotter console para 
         } catch (IOException ex) {
             Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
         }
                 }catch (NumberFormatException nfe){
                     Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error in type of variable...");
                    alert.setContentText("Please, the number of variables must be an integer...");
                    alert.showAndWait();
                 }
                 }
                                        
    result.ifPresent(name -> System.out.println("Your name: " + name));
     }
    
        public void EventoMenuConfigurar(){//Configurar evento para Menuconfigurar en menu File...
        configure.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
        loadProjectConfig();
    }
});
        }
        
        public void loadProjectConfig(){//M[etod to load the project configuration.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Project wizard.fxml"));//Load the fxml file
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Project configuration");
            stage.setTitle("Controlly console V2.0");
            stage.show();
            save.setDisable(false);//Enable save button..
            plot.setDisable(false);//Enable plot Button...
            serialport.setDisable(false);//Enable Serialport button.
            new ProjectWizardController().CargarWebView(view);
        } catch (IOException ex) {
            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
        
        
           public void loadSerialTerminal(){//Method to load a SerialVisualizer
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SerialPortVisual.fxml"));
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Serialport visor");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }

    public void EventoMenuCerrar() {//Event to close the APP
            close.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            Platform.exit();//Cerrar aplicacion, platform funciona adecuadamente.
    }
});
    }
    
       public void eventoWebView(){//Event to save and load the XML of Blockly.
               WebEngine engineb=view.getEngine(); 
               engineb.setOnAlert(new EventHandler<WebEvent<String>>(){
                @Override
                public void handle(WebEvent<String> arg0) {
                    val=arg0.getData(); 
                    System.out.println(val);
                    if(OpcionButton==1||OpcionButton==2){//If  opcionButton load the XML file
                        //Si dos se guarda archivo XML.
                    val=val.replaceAll("\"","'");//Replace all double quotes for simple quote.
                    }
                }
         });
        }

    public void EventoBotSaveLoadXML() {//Event to save or load the XML file.
        if(ProjectWizardController.ProjectPath!=null){
        save.setDisable(false);
        plot.setDisable(false);
        }
        
        upload.setDisable(false);
        WebEngine engineb=view.getEngine(); 
        save.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
             OpcionButton=2;
             engineb.executeScript("GuardarXML()");
             File file=new File(Path.Project);
             if (file != null) {
             new Filereaderwriter().writeXML(file.getAbsolutePath()+"/fileblockly.xml", val);//Save file in specific location.
              Alert alertB = new Alert(Alert.AlertType.INFORMATION);
                alertB.setTitle("Info");
                alertB.setHeaderText("Info process...");
                alertB.setContentText("Your file will be saved into the folder of your project...");
                alertB.showAndWait(); 
             }
        
        }
         });
    
        
        
        upload.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
                        CargarWebview(view);
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Load project file");
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                           String XmlRead=new Filereaderwriter().OpenfileXML(file.getAbsolutePath());
                           XmlRead=XmlRead.replaceAll("\"","'");
                           OpcionButton=1;
                           engineb.executeScript("AbrirXML(\""+XmlRead+"\")");
                           ProjectPath=file.getParent();
                           new Filereaderwriter().SetCompilerLocation(ProjectPath);//Save the XML file in User's folder.
                           System.out.println(ProjectPath);
                           save.setDisable(false);
                        }
            
             
        }
         });
   

    }
    
    public void EventoBotprogramar(){//Event to Program...
            program.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
           @Override public void handle(ActionEvent e) {
             eventoProgramar();//Llamar a evento para programar target en este caso dsPIC 33FJ128GP804
        
        }
         });
            usb.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
             eventoProgramar();
        
        }
         });
    }
    
        public void EventoBotayuda(){//Event ot help
            help.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
               
               try {
            Parent root = FXMLLoader.load(getClass().getResource("Help.fxml"));
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Help");
            stage.show();
            
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("http://seconlearning.com/xerte/preview.php?template_id=5"));//Open a browser,
                    //and load this URL for the educational resources in Xerte.
                } catch (URISyntaxException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
         });
            
    }
        
    public void eventoAyuda(){//Help Event II.
        WebEngine engine=view.getEngine();
        engine.load("http://seconlearning.com/xerte/play.php?template_id=5");
    }
    
    public void eventoProgramar(){//Program Event.
         //The event execute in order the step to get the .Hex file from the compiler XC16.
         String ruta;//This string contains the different commands for the compiler in console mode.
         String Comando;//This string contains the commands returned by the compiler.
         Alert alertB = new Alert(Alert.AlertType.INFORMATION);
         alertB.setTitle("Info");
         alertB.setHeaderText("Info process...");
         alertB.setContentText("Please wait until the process is finished...");
         alertB.showAndWait();   
         try{
         WebEngine engine=view.getEngine();
         OpcionButton=3;
         System.out.println("aqui0");
         engine.executeScript("ContenidoProgramacion()"); //Call to the function ContenidoProgramacion to get the code of the Blocks
             //See  \src\controllyv2\BlocklyOPt\demos\code\index.html
         new Filereaderwriter().deleteFile(Path.Project+"/main.c");//Erase intermediate files ....
         Crearmainfile(val);//Create a main file with the code got from Blockly.
         lineacomando.clear();//Clear the command prompt in the app.
         lineacomando.setStyle("-fx-font-family: monospace; -fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 16px;");//Estilo de letra
         lineacomando.appendText("Deleting intermediate files...\n");//Erase intermediate files .. Each time to compile the files 
             //must be erased
         new Filereaderwriter().deleteFile(Path.Project+"/main.o");
         new Filereaderwriter().deleteFile(Path.Project+"/user.o");
         new Filereaderwriter().deleteFile(Path.Project+"/control.o");
         new Filereaderwriter().deleteFile(Path.Project+"/archivoelf.elf ");
         new Filereaderwriter().deleteFile(Path.Project+"/archivoelf.hex");
         lineacomando.appendText("Compiling...\n");
         progreso.setProgress(0);
         indicador.setProgress(0);
         //Compile main.c***************************************************************************************************************************
         //lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe  "+Path.Project+"/main.c"+" -o "+Path.Project+"/main.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+" -I "+Path.Project+"Documents/UNIMINUTO/2018-I/Robot Social/osa\" -I "+Path.Project+"Documents/UNIMINUTO/2018-I/Robot Social/osa/OSA Files\\n");
         //process = runTime.exec("C:/Program Files (x86)/Microchip/xc16/v1.21/bin/xc16-gcc.exe  C:/Users/Jonathan/Documents/Temporales/Compilacion/main.c -o C:/Users/Jonathan/Documents/Temporales/Compilacion/main.o -c -mcpu=33FJ128MC802"); //Comando 1.
        //String ruta="C:/Program Files (x86)/Microchip/xc16/v1.21/bin/xc16-gcc.exe  C:/Users/Jonathan/Documents/Temporales/Compilacion/main.c -o C:/Users/Jonathan/Documents/Temporales/Compilacion/main.o -c -mcpu=33FJ128MC802";
         ruta=Path.Compiler+"/bin/xc16-gcc.exe"+"  \""+Path.Project+"\"/main.c"+" -o \""+Path.Project+"\"/main.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+" -MMD -MF \""+Path.Project+"\"/main.o.d  -g -omf=elf  -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles -O0 -msmart-io=1 -Wall -msfr-warn=off"; //mcpu eis cpu or core. This is command line for XC16.
         //La forma correcta es la siguiente se deben agregar comillas a la ruta esto para ejecutar rutas que contengan espacios...
         //"C:\Program Files (x86)\Microchip\xc16\v1.30\bin\xc16-gcc.exe"   "../../Documents/UNIMINUTO/2018-I/Robot Social/Robot Social/osa.c"  -o build/default/production/_ext/1659002919/osa.o  -c -mcpu=33FJ128GP804  -MMD -MF "build/default/production/_ext/1659002919/osa.o.d"        -g -omf=elf -DXPRJ_default=default  -legacy-libc    -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa" -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa/OSA Files" -O0 -msmart-io=1 -Wall -msfr-warn=off  
         Comando=executeCommand(ruta);
         //compile osa.c***************************************************************************************************************************
        //osa.c es el archivo para compilar el RTOS (osa) para dsPIC. 
        //"C:\Program Files (x86)\Microchip\xc16\v1.30\bin\xc16-gcc.exe"   "../../Documents/UNIMINUTO/2018-I/Robot Social/Robot Social/osa.c"  -o build/default/production/_ext/1659002919/osa.o  -c -mcpu=33FJ128GP804  -MMD -MF "build/default/production/_ext/1659002919/osa.o.d"        -g -omf=elf -DXPRJ_default=default  -legacy-libc    -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa" -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa/OSA Files" -O0 -msmart-io=1 -Wall -msfr-warn=off  
         ruta=Path.Compiler+"/bin/xc16-gcc.exe"+"  \""+Path.Project+"\"/osa/osa.c"+" -o \""+Path.Project+"\"/osa.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+" -MMD -MF \""+Path.Project+"\"/osa.o.d  -g -omf=elf  -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles  -O0 -msmart-io=1 -Wall -msfr-warn=off"; //mcpu es la cpu o core.
         Comando=executeCommand(ruta);
        //Compile user.c**************************************************************************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe  "+Path.Project+"/user.c"+" -o "+Path.Project+"/user.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"\n");
         ruta=Path.Compiler+"/bin/xc16-gcc.exe  \""+Path.Project+"\"/user.c"+" -o \""+Path.Project+"\"/user.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"";
         //lineacomando.appendText(ruta);
         Comando=executeCommand(ruta);
        //Compile control.c (Rutinas de control)**************************************************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe  "+Path.Project+"/control.c"+" -o "+Path.Project+"/control.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"\n");
         ruta=Path.Compiler+"/bin/xc16-gcc.exe"+"  \""+Path.Project+"\"/control.c"+" -o \""+Path.Project+"\"/control.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"";
         progreso.setProgress(0.333);
         indicador.setProgress(0.3333);
         Comando=executeCommand(ruta);
         //Generate .elf file to return the memory distribution of the dsPIC 33FJ128GP804***********************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe -o "+Path.Project+"/archivoelf.elf  "+Path.Project+"/main.o  "+Path.Project+"/osa.o  "+Path.Project+"/user.o  "+Path.Project+"/control.o    -mcpu="+Boards[ProjectWizardController.BoardType]+" -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles -omf=elf -Wl,--defsym=__MPLAB_BUILD=1,,--script=p"+Boards[ProjectWizardController.BoardType]+".gld,--stack=16,--report-mem\n");
         ruta=Path.Compiler+"/bin/xc16-gcc.exe -o \""+Path.Project+"\"/archivoelf.elf  \""+Path.Project+"\"/main.o \""+Path.Project+"\"/osa.o \""+Path.Project+"\"/user.o \""+Path.Project+"\"/control.o -mcpu="+Boards[ProjectWizardController.BoardType]+" -omf=elf -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles -Wl,--defsym=__MPLAB_BUILD=1,,--script=p"+Boards[ProjectWizardController.BoardType]+".gld,--stack=16,--report-mem";
         Comando=executeCommand(ruta);
         //Generar .hex file************************************************************************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-bin2hex  "+Path.Project+"/archivoelf.elf\n");
         ruta=Path.Compiler+"/bin/xc16-bin2hex  \""+Path.Project+"\"/archivoelf.elf";
         Comando=executeCommand(ruta);
         progreso.setProgress(0.666);
         indicador.setProgress(0.666);
         //Conects with the bootloader,Send a reset command**************************************************************************************
         lineacomando.appendText("Compilation done...\n");
         lineacomando.appendText("Connecting with bootloader...\n");
         String ConsoleCommand="src/ds30LoaderConsole.exe -f=\""+Path.Project+"\"/archivoelf.hex -d=dsPIC"+Boards[ProjectWizardController.BoardType]+" -k="+ProjectWizardController.PortNameSel+" -r=57600 --customplacement=1 --writef --non-interactive --customsize=1 --no-goto-bl --ht=3000 --polltime=200 --timeout=5000 --resettime=1000 -q=6 --reset-baudrate=57600";//En este caso 
         //q=6 es el comando de reset...
         Comando=executeCommand(ConsoleCommand);//Execute a bootloader ds30 with the .hex file genetared by the compiler XC16.
         progreso.setProgress(1.0);
         indicador.setProgress(1.0);
         Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Process finish");
        alert.setContentText("Process finished, please check the output console for the result...");
        alert.showAndWait(); 
         }catch(Exception Ex){
           System.out.println(Ex);
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error path locations");
           alert.setHeaderText("Error in access...");
           alert.setContentText("Please first open a file...");
           alert.showAndWait();    
         }
         
        
    }
    
      public boolean Crearmainfile(String codigo) {//Method tho create the main file for the DSC
              PrintWriter writer = null;
              String PathProject=Path.Project;
                    try {
                        writer = new PrintWriter(PathProject+"/main.c", "UTF-8");//Creacion Main.c
                        writer.println(codigo);
                        writer.close();
                    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    } finally {
                        return true;
                    }
            }
    
    public String executeCommand(String command) {//Method to write in console the different commands executed.
                                    StringBuilder output = new StringBuilder();
                                    Process p=null;
                                
                                    try {
                                        
                                        p = Runtime.getRuntime().exec(command);
                                        BufferedReader reader =
                                                new BufferedReader(new InputStreamReader(p.getInputStream()));
 
                                        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                       
                                        String line = "";
                                        String s = "";
                                   
                                        while ((line = reader.readLine())!= null) {
                                            output.append(line).append("\n");
                                            lineacomando.appendText(line+"\n");
                                            //System.out.println(line);
                                        }

                                        p.waitFor();
                                        p.destroy();
                                    } catch (IOException | InterruptedException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error compiler path location");
                                        alert.setHeaderText("Error in access...");
                                        alert.setContentText("The compiler location is mistaken, please configure a new project with the properly route of the compiler...");
                                        alert.showAndWait();
                                    }
            return output.toString();
            
    }
    
    

   
        public void BorrarWorkspace(WebEngine engine){//Clear workspace...
            engine.executeScript("Borrarwork()");
        }

    public void LlamarPlotterConsole() {//This method opens the Scene Plotter
        try {
                Parent root = FXMLLoader.load(getClass().getResource("Playconsole.fxml"));
                Scene scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Data console...");
                stage.show();
                LiveGraph app = LiveGraph.application();//Crear an instance of plotter LiveGraph.
                app.exec(new String[0]);//Open the Plot
            } catch (IOException ex) {
                Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
        
    }
    
}
