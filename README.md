# DSCBlocksV2
Github for project DSCBlocks V2 (Open Source)



Developer: Jonathan Álvarez Ariza
Correspondence: jalvarez@uniminuto.edu
Research Gate: https://www.researchgate.net/profile/Jonathan_Alvarez_Ariza

Description: 
DSCBlocks is an application to program DsPICs series 33FJXXXX through Visual Blocks from Microchip Inc. The application compiles 
In order to open the application, click in ControllyV2.jar
The application was tested in JDK version 1.8. The application was developed in JavaFX.

##Java Classes explanation:

GUIController: Main class of the aplication, opens the different complements as Project Wizard, Plotter, Serial Visualizer, Save and Load.
Filereaderwriter: Copies the different folders and files needed to the aplication inside the user's folder. 
Path: Save the paths for the compiler and user folder.
Help: Opens the help for the application in the local browser.
PlayConsole: Opens the plotter and asks the number of variables to plot. This class adjust the elements employed for the class LivePlot.
Project Wizard: Opens the wizard, ask the route for the user's folder, the DSC (dsPIC 33FJ128GP804 or 33FJ128MC802) and the serial port (COMx) in order to 
communicate with the development board.
SerialPortVisual: Opens the serial port Visualizer. 

Each class contains the respective fxml file (scene file) of JavaFX.

##Other files:

session.lgdfs
session.lgdss
session.lggs

Do not remove this files. These files contains the header elements for the plotter LiveGraph!

The main web page for the application is located in the folder \src\controllyv2\BlocklyOPt\demos\code\index.html
Generator for XC16 compiler for the DSCs is located at src\controllyv2\BlocklyOPt\generators\dart\base2.js
Blocks (graphical appearance) for the peripherals for the DSC are located at src\controllyv2\BlocklyOPt\blocks\base2.js
Libraries for UART, Control and HardwareProfile (macros) for the development board are located in the folder libraries.
RTOS (OSA) is located at the folder osa.
ds30Console for the bootloader is located at the folder src (Do not remove!)

Any change in the code (generator or blocks) must be reconfigurated in Blockly. To do this, save the file and click the Python Scrip Build.py 
(src\controllyv2\BlocklyOPt\build.py). 
You must have Python version 2.7 or higher.
