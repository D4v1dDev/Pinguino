package cod.ventana;

import java.awt.*;

import java.awt.image.BufferStrategy;

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

		setLayout(null);
		setAlwaysOnTop(true);
	}

	public void actualizar() {}
}
