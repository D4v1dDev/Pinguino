package cod.ventana;

import cod.herramientas.CargadorRecursos;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class VentanaTranslucida extends JFrame{
	
	private static final long serialVersionUID = 1L;
	

	
	
	public VentanaTranslucida(int ancho,int alto) {

		super("Pinguino");
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		setSize(new Dimension(ancho,alto));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(CargadorRecursos.obtenerImagenTranslucida(("src/res/penguin.png")).getSubimage(48,0,48,48));

		setLayout(null);
		setAlwaysOnTop(true);
		setFocusable(false);
	}

	public void actualizar() {}
}
