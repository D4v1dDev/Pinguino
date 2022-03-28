package cod.ventana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cod.herramientas.CargadorRecursos;
import cod.herramientas.Tarea;

import javax.swing.*;

public class Iglu extends VentanaTranslucida{

	private static final long serialVersionUID = 1L;

	private static ArrayList<Tarea> tareas = new ArrayList<Tarea>();

	private final Cartel cartel;
	private static final BufferedImage ic = CargadorRecursos.obtenerImagenTranslucida("res/iglu.png");
	
	public Iglu(int x,int y) {
		super(100,58);
		setLocation(x,y);

		cartel=new Cartel(x+105,y+20);

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				if (g instanceof Graphics2D) {
					Graphics2D g2d = (Graphics2D)g;
					g2d.drawImage(ic,0,0,null);
				}
			}
		};
		setAlwaysOnTop(true);
		setContentPane(panel);
		setVisible(true);
	}

	public static void añadirTarea(Tarea tarea) {
		tareas.add(tarea);
	}

	public Tarea  obtenerTarea(){
		if(tareas.size()<=0){
			return null;
		}
		Tarea t = tareas.get(0);
		tareas.remove(t);
		return t;
	}

}
