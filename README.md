# DSCBlocksV2
Github for project DSCBlocks V2 (Open Source)



Developer: Jonathan Álvarez Ariza

Correspondence: jalvarez@uniminuto.edu

Research Gate: https://www.researchgate.net/profile/Jonathan_Alvarez_Ariza

This work is supported the Control research Incubator (SeCon) in the program of Technology of Electronics at Uniminuto University (Bogotá, Colombia) http://www.uniminuto.edu/

Description: 
* DSCBlocks is an application to program DsPICs series 33FJXXXGP/MCXXX from Microchip Inc. through Algorithm Visualizations (Google Blockly). 

For a quick glance, see the web version of the app at http://www.seconlearning.com/DSCBlockV2/BlocklyOPt/demos/code/index.html

The application compiles and generates the code for the DSC in the convention of the Microchip XC16 compiler.
The application was tested in JDK version 1.8 and developed in JavaFX.

This a video of the different platform functionalities:


[![DSCBlocksV2 Functioning!](https://img.youtube.com/vi/893QkAonpLo/0.jpg)](https://www.youtube.com/watch?v=893QkAonpLo "DSCBlocks functioning")

## Installation

Just create a Netbeans IDE project. Copies the content of the SRC folder inside the classpath of your project. Add the Java libraries LiveGraph (Plotter) and jssc (Serial Port) provided in the full .zip file of the APP available at http://www.seconlearning.com/DSCBlockV2/DSCBlocksFull.zip

The development board (dsPIC33FJ128GP804) (Schematic and PCB) is in the file ControllyBoard3.0.rar. The PCB was built in Eagle Cadsoft 7.3 version. If you want to change the hardware go to the file hardwareprofile.h at /src/libraries/Hardwareprofile.h and edit it according to your needs.

The version of development board for dsPIC33FJ128MC802 is in the file TestBoard.rar

Due to the development boards use a FTDI232RL chip, the correct driver in order to transfer the .hex file to the device can be found in the file: CDM 2.08.28 WHQL Certified.zip

## Java Classes explanation:

* GUIController: Main class of the application, opens the different complements as Project Wizard, Plotter, Serial Visualizer, Save and Load.
* Filereaderwriter: Copies the different folders and files needed in the aplication inside the user's folder. 
* Path: Saves the paths for the compiler and user folder.
* Help: Opens the help for the application in the local browser.
* PlayConsole: Opens the plotter and asks the number of variables to plot. This class adjusts the elements employed for the class LivePlot which is a Java plotter. The information of the class (LiveGraph) can be found at: https://sourceforge.net/projects/live-graph/.
* Project Wizard: Opens the wizard, asks the route for the user's folder, the DSC (dsPIC 33FJ128GP804 or 33FJ128MC802) and the serial port (COMx) in order to communicate with the development board.
* SerialPortVisual: Opens the serial port Visualizer. 

Each class contains the respective fxml file (scene file) of JavaFX.


## Other files:

When you download the application (see the folder's tree). The following files must be inside of your App folder.

session.lgdfs

session.lgdss

session.lggs

Do not remove this files. These files contains the header elements for the plotter LiveGraph!

**The main web page for the application is located in the folder \src\controllyv2\BlocklyOPt\demos\code\index.html**

**The Generator for XC16 compiler for the DSCs is located at src\controllyv2\BlocklyOPt\generators\dart\base2.js**

**The Blocks (graphical appearance) for the peripherals and ports for the DSC are located at src\controllyv2\BlocklyOPt\blocks\base2.js**

**Libraries for UART, Control and HardwareProfile (macros) for the development board are located in the folder "libraries".**

**RTOS (Real Time Operating System) (OSA) is located at the folder OSA.**

**ds30Console.exe for the bootloader is located at the folder src (Do not remove!)**

**Inside the SRC folder exists a folder called Route with a file Route.txt that saves the route of the compiler XC16 in your OS. (Do not remove!)**

Any change in the code (generator or blocks) must be reconfigurated in Blockly. To do this, save the file and click in Python Script Build.py (src\controllyv2\BlocklyOPt\build.py). You must have a internet connection and Python version 2.7 or higher.

Don't forget in order to compile Blockly, the closure library folder, see the following link: https://developers.google.com/blockly/guides/modify/web/closure


## Examples

Some examples can be found in the folder (Examples), for example:

1. Turn on LED
2. RTOS (OSA)
3. ReadPin
4. ADC

The examples are in .xml file. Open the application and load them with the button (upload).


## Learning Materials

The learning materials can be found in the following link: 
http://seconlearning.com/xerte/play.php?template_id=5

Part I is in spanish version (DSC fundamentals)

Part II (Application tutorial) is in spanish-english version

**Thank you for your interest in this Open (Hardware-Software) project.**

###

