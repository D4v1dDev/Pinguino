package cod.ventana;

import cod.herramientas.Tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DialogoCartel extends JFrame implements KeyListener {

    private final JTextField campoTarea;


    public DialogoCartel() {
        setSize(300,150 );
        setLocationRelativeTo(null);
        setTitle("Deje Su Mensaje al Pinguino");
        setBackground(new Color(142,100,32));
        campoTarea = new JTextField();
        campoTarea.setBackground(new Color(142,100,32));
        add(campoTarea);
        campoTarea.addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()=='\n'){
            Iglu.añadirTarea(new Tarea(campoTarea.getText()));
            dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
