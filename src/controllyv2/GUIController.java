/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private ProgressBar progreso;//Barra de progreso
    @FXML
    private ProgressIndicator indicador;//Indicador de progreso
    int exitValueP1=-1,exitValueP2=-1,exitValueP3=-1;//Estas variables permiten 
    static String val="";//Esta variable debe ser static de lo contrario no se escribe en el archivo main.c el archivo equivalente de los bloques.
    static String RutaProyecto; //Ruta de proyecto. La ruta se obtiene seleccionando el directorio
    static int OpcionButton=0; //Opcion button permite indicar que boton se ha oprimido entre compilar, save y load XML. los valores son
    //1 compilar, 2//save XML, 3//LoadXML
    static String Varplotter; //Esta variable permite saber el numero de variables a graficar mediante el plotter.
    int proceso=0;
    static WebView engines;
    public String[] Boards={"33FJ128GP804", "33FJ128MC802"};
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      //  wizard.setStyle("-fx-background-color: transparent;");
        //CargarWebview();
        //engines=view;
        //CargarWebview();
        view.getEngine().loadContent("");//Siempre iniciar la plantilla en blanco de Blockly
        ConfigurarGUI();
        EventoBotConfigurar();//Evento para configurar proyecto.
        EventoMenuConfigurar();//Evento para menu configurar proyecto.
        EventoMenuCerrar();//Evento cerrar
        EventoBotSaveLoadXML();//Eventos de los botones Save y load XML
        eventoWebView();
        EventoBotprogramar();//Evento para programacion
        EventoBotPlot();//Evento boton plot...
        EventoBotSerial();//Evento boton plot...
        EventoBotayuda();//configurar evento de boton ayuda...
        view.setContextMenuEnabled(false);
        createContextMenu(view);//Se crea el menu para que aparezcan las opociones correctas
        
}
    
 

    public  void CargarWebview(WebView viewb) {//este método carga el webView con Blockly de acuerdo a las convenciones mencionadas.
        //Debe seguirse este proceso para cargar todos los archivos de Blockly, si alguno falla la aplicación no carga 
        //Correctamente.
        
        WebEngine engine=viewb.getEngine();
        String path = System.getProperty("user.dir");  //Se debe incluir user dir para que 
        System.out.println(path);  
        //path +=  "/src/controllyv2/BlocklyEx/blockly/apps/blocklyduino/index.html";  //Cargar Blockly con los bloques realizados para dsPIC.
        if(ProjectWizardController.BoardType==0){//Si board es 804 cargar este blockly
        //path +=   "/src/controllyv2/blocklyB/apps/blocklyduino/index.html";
        path +=   "/src/controllyv2/BlocklyOPt/demos/code/index.html";
        }else if(ProjectWizardController.BoardType==1){//De lo contrario cargar este blockle
        path +=   "/src/controllyv2/BlocklyOPt/demos/code/index.html";    
        
        }
        path=path.replaceAll("\\\\", "/"); //Reemplazar la URL de carga con la forma correcta segub Java.
        System.out.println(path);
        engine.getLoadWorker().stateProperty().addListener(//Este listener permite prevenir error al ejecutar script
                //Si no se ha cargado la pagina y se ejecuta esta funcion el sistema no encuentra JSON objects y 
                //devuelve error, el listener permite que se ejecute la funcion si  y solo si la pagina ha cargado.
            new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                    if (newValue == Worker.State.SUCCEEDED) {
                    //BorrarWorkspace(engine);//Borrar workspace...
                }
            }
           
                  
                
            });
        
        engine.setOnAlert(new EventHandler<WebEvent<String>>(){

            @Override
            public void handle(WebEvent<String> arg0) {            
               //JOptionPane.showMessageDialog(null,  arg0.getData(),"Mensaje", JOptionPane.INFORMATION_MESSAGE);
               val=arg0.getData();
               System.out.println(val);
            }

        });
        engine.setJavaScriptEnabled(true); //Habilitar Javascript para hacer llamados desde la aplicacion.
        try{
        engine.load("file:///" + path);
        }catch(Exception Ex){
            System.out.println(Ex);
        }
       
        //engine.load("https://blockly-demo.appspot.com/static/demos/code/index.html"); 
        //engine.load("http://www.seconlearning.com/DSCBlockV2/BlocklyDSC/demos/code/index.html");
        //engine.load("file:///C:/Users/Jonathan/Desktop/FXBlockly-dev/src/main/java/me/mouse/fxblockly/index.html");
        
        //engine.load("file:////src/controllyv2/BlocklyOPt/demos/code/index.html");
        //BorrarWorkspace(engine);//Borrar workspace...

    }

    public void ConfigurarGUI() {//Este m[etodo configura la apariencia visual de los botones, barras de herramientas, etc de Controlly
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
        reload.setOnAction(new EventHandler<ActionEvent>() {//Este menu permite recargar la p[agina en este caso Blockly.
            @Override public void handle(ActionEvent e) {
                webView.getEngine().reload();
            }
        });
        MenuItem Cambiovariable = new MenuItem("Rename variable");//Este menu permite renombrar la variable seleccionada.
        Cambiovariable.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            
                    CrearNuevaVariable();
                    }

               
             
        });
        MenuItem Copiar = new MenuItem("Copy");//Este menu permite copiar el codigo generado de manera
        //Que se pueda exportar a otra ventana que se desee.
        Copiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            
                        String selection = (String) webView.getEngine()
                        .executeScript("window.getSelection().toString()");//Se copia el c[odigo seleccionado 
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
        
        public void CrearNuevaVariable(){//Este método cambia el valor de la variable existente en Blockly
            //Invoca la funcion RenameVar la cual esta en la pagina index.html en Blockly...
            // Create the custom dialog.
            Dialog<Pair<String, String>> dialog = new Dialog<>(); //Crear la nueva ventana de dialogo.
            dialog.setTitle("New variable's name dialog"); //Configurar el titulo del dialogo.
            dialog.setHeaderText("Please fill the information...");//Subtitulo del cuadro
            dialog.setGraphic(new ImageView(new Image("/Images/variable.png")));//Figura del cuadro de texto.
            ButtonType OKnewvar = new ButtonType("OK", ButtonData.OK_DONE);//Agregar al dialogo boton de OK
            dialog.getDialogPane().getButtonTypes().addAll(OKnewvar , ButtonType.CANCEL);

            // Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField oldvariable = new TextField(); //Agregar los campos de texto de nueva variable y old variable
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
                        EjecutarScriptnewVar(oldvariable.getText(),newvariable.getText());//Ejecutar funcion JavaScript esta esta dentro del
                        //del archivo index.html es una funcion...
                    }
                  
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

          
        }

    public void EjecutarScriptnewVar(String oldvar, String newvar) {//Este metodo cambia los nombres de las variables
        //En funcion de los datos oldvariable y newvariable. El metodo invoca la funcion Javascript Renamevar...
         final WebEngine engineB=view.getEngine();
         engineB.executeScript("Renamevar('"+oldvar+"','"+newvar+"')"); //De esta manera se llama a una funcion 
    }
    
    public void EventoBotConfigurar(){//A traves de este metodo se configura el proyecto
        //El metodo llama a project wizard y carga el Stage en este caso
            wizard.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
            @Override public void handle(ActionEvent e) {
                loadProjectConfig();//Configurar projecto, abrir stage con project locations.
                    }
                    
                        });
    }
    
       public void EventoBotProgramar(){//A traves de este metodo se configura el proyecto
        //El metodo llama a project wizard y carga el Stage en este caso
            wizard.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
            @Override public void handle(ActionEvent e) {
                eventoProgramar();//Este evento permite llamar a programacion desde 
                //boton programar...
                    }
                    
                        });
    }
       
   public void EventoBotPlot(){//A traves de este metodo se configura el proyecto
        //El metodo llama a project wizard y carga el Stage en este caso
            plot.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
            @Override public void handle(ActionEvent e) {
                NumVariablesPlot();//Pedir al usuario el numero de variables a graficar...
                    }
                    
                        });
    }
   
   
      public void EventoBotSerial(){//A traves de este metodo se configura el proyecto
        //El metodo llama a project wizard y carga el Stage en este caso
            serialport.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
            @Override public void handle(ActionEvent e) {
                loadSerialTerminal();//Configurar visor serial...
                    }
                    });
    }
       
     public void NumVariablesPlot() {//File dialog para pedir el numero de variables a graficar.
         //Este metodo es llamado desde la accion del boton plotter.
     TextInputDialog dialog = new TextInputDialog("# of variables to plot");
     dialog.setTitle("Variables to plot");
     dialog.setHeaderText("Please, indicate the number of variables to plot. Don't forget to put the respective \ncode through Controlly in otherwise the plotter doesn't plot...");
     dialog.setContentText("Please indicate the number to variables to plot:");

     // Traditional way to get the response value.
     Optional<String> result = dialog.showAndWait();
     if (result.isPresent()){//Si resultado esta...
         //System.out.println("Your name: " + result.get());
         Varplotter=result.get(); //Esta variable permite saber el numero de variables a graficar mediante el plotter.
     
            
            try{
             double num=Double.parseDouble(Varplotter);//Convertir el numero para saber si es o no un numero de lo contrario 
             //enviar error al usuario...
         try {
             new Filereaderwriter().WriteCSV(); //Escribe .CSV file con el formato de Liveplot.
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
        
        public void loadProjectConfig(){//M[etodo para cargar project configuration...
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Project wizard.fxml"));
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Project configuration");
            stage.setTitle("Controlly console V2.0");
            stage.show();
            save.setDisable(false);//Activar Boton guardar..
            plot.setDisable(false);//Activar boton plot...
            serialport.setDisable(false);//Activar boton de interfaz serial.
            new ProjectWizardController().CargarWebView(view);
        } catch (IOException ex) {
            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
        
        
           public void loadSerialTerminal(){//M[etodo para cargar visor del puerto serial.
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

    public void EventoMenuCerrar() {//Evento para cerrar APP
            close.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            Platform.exit();//Cerrar aplicacion, platform funciona adecuadamente.
    }
});
    }
    
       public void eventoWebView(){//Eventowebview para tomar paramentros de las funciones Javascript...
               WebEngine engineb=view.getEngine(); 
               engineb.setOnAlert(new EventHandler<WebEvent<String>>(){
                @Override
                public void handle(WebEvent<String> arg0) {
                    val=arg0.getData(); 
                    System.out.println(val);
                    if(OpcionButton==1||OpcionButton==2){//Si  opcionButton es 1 se carga archivo XML
                        //Si dos se guarda archivo XML.
                    val=val.replaceAll("\"","'");//Replace all double quotes for simple quote.
                    }
                }
         });
        }

    public void EventoBotSaveLoadXML() {
       // if(ProjectWizardController.ProjectconfigureOK){
        if(ProjectWizardController.ProjectPath!=null){
        save.setDisable(false);
        plot.setDisable(false);
        }
        
        upload.setDisable(false);
        WebEngine engineb=view.getEngine(); 
        save.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
        @Override public void handle(ActionEvent e) {
             OpcionButton=2;
             engineb.executeScript("GuardarXML()");//Ejecutar guardar archivo XML
             //FileChooser fileChooser = new FileChooser();
             //fileChooser.setTitle("Save project file");
             //File file = fileChooser.showSaveDialog(stage);
             File file=new File(Path.Project);//Guardar archivo en carpeta de proyecto
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
    
        
        
        upload.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
        @Override public void handle(ActionEvent e) {
                        CargarWebview(view);
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Load project file");
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                           String XmlRead=new Filereaderwriter().OpenfileXML(file.getAbsolutePath());
                          // System.out.println(XmlRead);
                           //engineb.executeScript("AbrirXML(\""+XmlRead+"\")");//Send the read file to Blockly core
                           //For renderize.
                           XmlRead=XmlRead.replaceAll("\"","'");//se debe reemplazar todos las comilla dobles por comillas sencillas para cargar el archivo guardado en blockly
                           //engineb.executeScript("AbrirXML(\"<xml>  <block type='osc' id='=.gn3}S*;iiLJfAuSdeR' x='290' y='90'></block></xml>\")");
                           OpcionButton=1;
                           engineb.executeScript("AbrirXML(\""+XmlRead+"\")");
                           ProjectPath=file.getParent();
                           new Filereaderwriter().SetCompilerLocation(ProjectPath);//Configurar la ruta del compilador y del projecto cuando
                           //se cargue el archivo xml esta es una opcion alternativa al project wizard debido a que el usaurio puede presionar
                           //el boton de upload bloque...
                           System.out.println(ProjectPath);
                           save.setDisable(false);
                        }
            
                 //MensajeError();//Error de mensaje en caso de no lanzar project wizard.
             
        }
         });
   

    }
    
    public void EventoBotprogramar(){//Evento boton programar...
            program.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
           @Override public void handle(ActionEvent e) {
             eventoProgramar();//Llamar a evento para programar target en este caso dsPIC 33FJ128GP804
        
        }
         });
            usb.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
           @Override public void handle(ActionEvent e) {
             eventoProgramar();//Llamar a evento para programar target en este caso dsPIC 33FJ128GP804
        
        }
         });
    }
    
        public void EventoBotayuda(){//Evento boton ayuda
            help.setOnAction(new EventHandler<ActionEvent>() {//Forma de generar la accion sobre el menu item
           @Override public void handle(ActionEvent e) {
               
               try {
            Parent root = FXMLLoader.load(getClass().getResource("Help.fxml"));
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Help");
            //stage.setTitle("Controlly console V2.0");
            //stage.setMaximized(true);//Permitir maximización de la ventana.
            stage.show();
            
            if (Desktop.isDesktopSupported()) {//Abrir los recursos de ayuda en búscador del sistema
                try {
                    Desktop.getDesktop().browse(new URI("http://seconlearning.com/xerte/preview.php?template_id=5"));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //save.setDisable(false);//Activar Boton guardar..
            //plot.setDisable(false);//Activar boton plot...
            //serialport.setDisable(false);//Activar boton de interfaz serial.
            //new ProjectWizardController().CargarWebView(view);
        } catch (IOException ex) {
            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
       /*    Alert alertB = new Alert(Alert.AlertType.INFORMATION);
           
           alertB.setTitle("Info");
           alertB.setHeaderText("Help...");
           alertB.setContentText("Link help (browser open):\nhttp://seconlearning.com/xerte/play.php?template_id=5");
           alertB.showAndWait();  
           eventoAyuda();//Llamar a evento para programar target en este caso dsPIC 33FJ128GP804
        */
        }
         });
            
    }
        
    public void eventoAyuda(){//Evento de ayuda
        WebEngine engine=view.getEngine();//Obtener webView para cargar ayuda para el usuario a traves de Xerte.
        engine.load("http://seconlearning.com/xerte/play.php?template_id=5");//Cargar esta pagina web para el usuario
    }
    
    public void eventoProgramar(){//Este metodo ejecuta los parametros del compilador
         //Lo invoca en orden para la generacion de un archivo .hex.
         String ruta;//Este string contiene todos los comandos para compilar an orden de generacion
         String Comando;//Este string contiene los parametros de retorno del compilador XC16
         Alert alertB = new Alert(Alert.AlertType.INFORMATION);
         alertB.setTitle("Info");
         alertB.setHeaderText("Info process...");
         alertB.setContentText("Please wait until the process is finished...");
         alertB.showAndWait();   
         try{//Si el usuario abrio ControlBlockly entonces ejecutar estas lineas....
         WebEngine engine=view.getEngine();
         OpcionButton=3;
         System.out.println("aqui0");
         engine.executeScript("ContenidoProgramacion()"); //De esta manera se llama a una funcion 
         System.out.println("aqui1");
         new Filereaderwriter().deleteFile(Path.Project+"/main.c");//Borrar archivos intermedios....
         Crearmainfile(val);//Crear archivo main.c con el contenido de programacion devuelto por los bloques de blockly...
         lineacomando.clear();//Borra el texto de la consola de comandos.
         lineacomando.setStyle("-fx-font-family: monospace; -fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 16px;");//Estilo de letra
         //para consola de comandos...
         lineacomando.appendText("Deleting intermediate files...\n");//Borrar archivos intermedios...
         new Filereaderwriter().deleteFile(Path.Project+"/main.o");//Borrar archivos intermedios....
         new Filereaderwriter().deleteFile(Path.Project+"/user.o");//Borrar archivos intermedios....
         new Filereaderwriter().deleteFile(Path.Project+"/control.o");//Borrar archivos intermedios....
         new Filereaderwriter().deleteFile(Path.Project+"/archivoelf.elf ");//Borrar archivos intermedios....
         new Filereaderwriter().deleteFile(Path.Project+"/archivoelf.hex");//Borrar archivos intermedios....
         lineacomando.appendText("Compiling...\n");
         progreso.setProgress(0);
         indicador.setProgress(0);
         lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe  "+Path.Project+"/main.c"+" -o "+Path.Project+"/main.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+" -I "+Path.Project+"Documents/UNIMINUTO/2018-I/Robot Social/osa\" -I "+Path.Project+"Documents/UNIMINUTO/2018-I/Robot Social/osa/OSA Files\\n");
         //process = runTime.exec("C:/Program Files (x86)/Microchip/xc16/v1.21/bin/xc16-gcc.exe  C:/Users/Jonathan/Documents/Temporales/Compilacion/main.c -o C:/Users/Jonathan/Documents/Temporales/Compilacion/main.o -c -mcpu=33FJ128MC802"); //Comando 1.
        //String ruta="C:/Program Files (x86)/Microchip/xc16/v1.21/bin/xc16-gcc.exe  C:/Users/Jonathan/Documents/Temporales/Compilacion/main.c -o C:/Users/Jonathan/Documents/Temporales/Compilacion/main.o -c -mcpu=33FJ128MC802";
         ruta=Path.Compiler+"/bin/xc16-gcc.exe"+"  \""+Path.Project+"\"/main.c"+" -o \""+Path.Project+"\"/main.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+" -MMD -MF \""+Path.Project+"\"/main.o.d  -g -omf=elf  -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles -O0 -msmart-io=1 -Wall -msfr-warn=off"; //mcpu es la cpu o core.
         //La forma correcta es la siguiente se deben agregar comillas a la ruta esto para ejecutar rutas que contengan espacios...
         //"C:\Program Files (x86)\Microchip\xc16\v1.30\bin\xc16-gcc.exe"   "../../Documents/UNIMINUTO/2018-I/Robot Social/Robot Social/osa.c"  -o build/default/production/_ext/1659002919/osa.o  -c -mcpu=33FJ128GP804  -MMD -MF "build/default/production/_ext/1659002919/osa.o.d"        -g -omf=elf -DXPRJ_default=default  -legacy-libc    -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa" -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa/OSA Files" -O0 -msmart-io=1 -Wall -msfr-warn=off  
         Comando=executeCommand(ruta);
         //compilar osa.c***************************************************************************************************************************
        //osa.c es el archivo para compilar el RTOS (osa) para dsPIC. 
        //"C:\Program Files (x86)\Microchip\xc16\v1.30\bin\xc16-gcc.exe"   "../../Documents/UNIMINUTO/2018-I/Robot Social/Robot Social/osa.c"  -o build/default/production/_ext/1659002919/osa.o  -c -mcpu=33FJ128GP804  -MMD -MF "build/default/production/_ext/1659002919/osa.o.d"        -g -omf=elf -DXPRJ_default=default  -legacy-libc    -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa" -I"../../Documents/UNIMINUTO/2018-I/Robot Social/osa/OSA Files" -O0 -msmart-io=1 -Wall -msfr-warn=off  
         ruta=Path.Compiler+"/bin/xc16-gcc.exe"+"  \""+Path.Project+"\"/osa/osa.c"+" -o \""+Path.Project+"\"/osa.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+" -MMD -MF \""+Path.Project+"\"/osa.o.d  -g -omf=elf  -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles  -O0 -msmart-io=1 -Wall -msfr-warn=off"; //mcpu es la cpu o core.
         Comando=executeCommand(ruta);
        //Compilar user.c**************************************************************************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe  "+Path.Project+"/user.c"+" -o "+Path.Project+"/user.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"\n");
         ruta=Path.Compiler+"/bin/xc16-gcc.exe  \""+Path.Project+"\"/user.c"+" -o \""+Path.Project+"\"/user.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"";
         //lineacomando.appendText(ruta);
         Comando=executeCommand(ruta);
        //Compilar control.c (Rutinas de control)**************************************************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe  "+Path.Project+"/control.c"+" -o "+Path.Project+"/control.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"\n");
         ruta=Path.Compiler+"/bin/xc16-gcc.exe"+"  \""+Path.Project+"\"/control.c"+" -o \""+Path.Project+"\"/control.o -c -mcpu="+Boards[ProjectWizardController.BoardType]+"";
         progreso.setProgress(0.333);
         indicador.setProgress(0.3333);
         Comando=executeCommand(ruta);
         //Generar archivo .elf retornar ocupacion memoria dsPIC 33FJ128GP804***********************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-gcc.exe -o "+Path.Project+"/archivoelf.elf  "+Path.Project+"/main.o  "+Path.Project+"/osa.o  "+Path.Project+"/user.o  "+Path.Project+"/control.o    -mcpu="+Boards[ProjectWizardController.BoardType]+" -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles -omf=elf -Wl,--defsym=__MPLAB_BUILD=1,,--script=p"+Boards[ProjectWizardController.BoardType]+".gld,--stack=16,--report-mem\n");
         ruta=Path.Compiler+"/bin/xc16-gcc.exe -o \""+Path.Project+"\"/archivoelf.elf  \""+Path.Project+"\"/main.o \""+Path.Project+"\"/osa.o \""+Path.Project+"\"/user.o \""+Path.Project+"\"/control.o -mcpu="+Boards[ProjectWizardController.BoardType]+" -omf=elf -I \""+Path.Project+"\"/osa -I \""+Path.Project+"\"/osa/OSAFiles -Wl,--defsym=__MPLAB_BUILD=1,,--script=p"+Boards[ProjectWizardController.BoardType]+".gld,--stack=16,--report-mem";
         Comando=executeCommand(ruta);
         //Generar .hex file************************************************************************************************************************
         lineacomando.appendText(Path.Compiler+"/bin/xc16-bin2hex  "+Path.Project+"/archivoelf.elf\n");
         ruta=Path.Compiler+"/bin/xc16-bin2hex  \""+Path.Project+"\"/archivoelf.elf";
         Comando=executeCommand(ruta);
         progreso.setProgress(0.666);
         indicador.setProgress(0.666);
         //Conectar con el bootloader,enviar comando de reset**************************************************************************************
         lineacomando.appendText("Compilation done...\n");
         lineacomando.appendText("Connecting with bootloader...\n");
         String ConsoleCommand="src/ds30LoaderConsole.exe -f=\""+Path.Project+"\"/archivoelf.hex -d=dsPIC"+Boards[ProjectWizardController.BoardType]+" -k="+ProjectWizardController.PortNameSel+" -r=57600 --customplacement=1 --writef --non-interactive --customsize=1 --no-goto-bl --ht=3000 --polltime=200 --timeout=5000 --resettime=1000 -q=6 --reset-baudrate=57600";//En este caso 
         //q=6 es el comando de reset...
         Comando=executeCommand(ConsoleCommand);//Ejecutar comando de consola, en esteb caso se llama al bootloader con la
         //La linea de comandos mostrada anteriormente.
         progreso.setProgress(1.0);
         indicador.setProgress(1.0);
         Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Process finish");
        alert.setContentText("Process finished, please check the output console for the result...");
        alert.showAndWait(); 
         }catch(Exception Ex){//Si no se puede cargar la anterior funcion JavaScript aun no se ha cargado Blockly
             //El usuario intenta una opcion erronea  generar aviso de error.
           System.out.println(Ex);
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error path locations");
           alert.setHeaderText("Error in access...");
           alert.setContentText("Please first open a file...");
           alert.showAndWait();    
         }
         
        
    }
    
      public boolean Crearmainfile(String codigo) {//Este metodo permite 
          //Crear el archivo main.c con el c[odigo generado por los bloques (Blockly).
              PrintWriter writer = null;
              String PathProject=Path.Project;
                    try {
                        writer = new PrintWriter(PathProject+"/main.c", "UTF-8");//Creacion Main.c
                        writer.println(codigo);
                        writer.close();
                        //System.out.println(codigo);
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    } finally {
                        return true;
                    }
            }
    
    public String executeCommand(String command) {//Metodo para ejecutar comandos desde consola...
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
    
    

   
        public void BorrarWorkspace(WebEngine engine){//Borrar workspace...
            engine.executeScript("Borrarwork()");
        }

    public void LlamarPlotterConsole() {//Este metodo llama al plotter para graficar los datos que se estan enviando dede la aplicacion...
        try {
                Parent root = FXMLLoader.load(getClass().getResource("Playconsole.fxml"));
                Scene scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Data console...");
                stage.show();
                LiveGraph app = LiveGraph.application();//Crear una instancia para Liveplot, lanzar Liveplot...
                app.exec(new String[0]);//Abrir Live Plot
            } catch (IOException ex) {
                Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
        
    }
    
}
