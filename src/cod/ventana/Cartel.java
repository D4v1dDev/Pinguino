package cod.ventana;

import cod.herramientas.CargadorRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Cartel extends VentanaTranslucida implements MouseListener {
    private static final BufferedImage ic = CargadorRecursos.obtenerImagenTranslucida("res/cartel.png");

    public Cartel(int x, int y) {
        super(30, 42);

        setLocation(x,y);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.drawImage(ic,0,0,null);
                }
            }
        };
        panel.addMouseListener(this);
        setContentPane(panel);
        setAlwaysOnTop(false);
        setVisible(true);
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new DialogoCartel();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
