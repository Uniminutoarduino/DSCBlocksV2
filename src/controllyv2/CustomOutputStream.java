/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllyv2;

import java.io.IOException;
import java.io.OutputStream;
import javafx.scene.control.TextArea;

/**
 *
 * @author Jonathan
 */
public class CustomOutputStream extends OutputStream {
    private TextArea textArea;

    public CustomOutputStream(TextArea textArea) {
        this.textArea = textArea;
    }


    public void write(int b) throws IOException {
        // redirects data to the text area
        //textArea.append(String.valueOf((char)b));
        textArea.appendText(String.valueOf((char)b));
        // scrolls the text area to the end of data
        //textArea.setCaretPosition(textArea.getDocument().getLength());
        // keeps the textArea up to date
        //textArea.u(textArea.getGraphics());
    }
}