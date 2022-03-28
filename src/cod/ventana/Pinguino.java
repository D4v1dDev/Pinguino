package cod.ventana;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import cod.Inicio;
import cod.herramientas.CargadorRecursos;
import cod.herramientas.Tarea;

import javax.swing.*;

public class Pinguino extends VentanaTranslucida {

	private int x = 500, y = 500;
	private static String COMENTARIO="";

	public final static char ACCION_HABLAR='q', ACCION_IR_A_POR_RATON='m',ACCION_MOVER_RANDOM='0',ACCION_IR_AL_IGLU='i',DESPEDIRSE='d';

	private final static Random r = new Random();
	private final static BufferedImage[][] sprites = new BufferedImage[3][4];

	private static Robot robot = null;
	private final Iglu iglu;

	private char objetivo;
	private boolean fijarCursor = false;
	private byte sprite = 1;
	private static final long serialVersionUID = 1L;
	private boolean enAnimacion = true;
	private int animacion = 0;

	public Pinguino(Iglu iglu) {
		super(48 + 300, 48 + 30);
		this.iglu = iglu;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		BufferedImage img = CargadorRecursos.obtenerImagenTranslucida("res/pato.png");

		for (int x = 0; x < sprites.length; x++) {
			for (int y = 0; y < sprites[x].length; y++) {
				sprites[x][y] = img.getSubimage(x * 48, y * 48, 48, 48);
			}
		}
		final boolean[] firstTime = {true};
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				if (g instanceof Graphics2D) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.clearRect(0,0,getWidth(),getHeight());
					g2d.drawImage(sprites[animacion/10][sprite], 0, 20, null);
					if (!COMENTARIO.equals("")) {
						g2d.setColor(Color.white);
						g2d.fillRect(0, 15,  (COMENTARIO.length() * 6) + 10, 20);
						g2d.setColor(Color.BLACK);
						g2d.drawString(COMENTARIO, 3, 30);
					}
				}
			}
		};
		setContentPane(panel);
		setLocation(iglu.getX() + iglu.getWidth(), iglu.getY() + iglu.getHeight() / 2);
		setVisible(true);
	}

	public void moverAlAzar() {

		//Relocalizar Cursor
		if (objetivo == ACCION_IR_A_POR_RATON) {
			x = (int) MouseInfo.getPointerInfo().getLocation().getX() - 24;
			y = (int) MouseInfo.getPointerInfo().getLocation().getY() - 25;
		}

		if(objetivo == ACCION_IR_AL_IGLU && !iglu.isVisible()){
			//Obtener la x e y de la ventana que se muestra y va a cerrarla
		}

		//Dirigirse hacia el objetivo
		if (getX() == x) {
			if (getY() == y) {
				//Al llegar al objetivo
				if (objetivo == ACCION_IR_A_POR_RATON) {
					fijarCursor = true;
				} else if (objetivo == ACCION_IR_AL_IGLU) {
					sprite = 0;
					enAnimacion = false;
					animacion = 15;
					fijarCursor = false;

					COMENTARIO="";
					repaint();
					setVisible(false);
					dormir(-1);
					Tarea t = iglu.obtenerTarea();
					if(t!=null) {
						cambiarObjetivo((char) t.cumplirTarea()[0], (String) t.cumplirTarea()[1]);
						return;
					}
				}
				cambiarObjetivo();
			} else {
				if (getY() < y) {
					sprite = 0;
					incrementarPos(0, 1);
				} else {
					sprite = 3;
					incrementarPos(0, -1);
				}
			}
		} else {
			if (getX() < x) {
				sprite = 2;
				incrementarPos(1, 0);
			} else {
				sprite = 1;
				incrementarPos(-1, 0);
			}
		}
	}

	private void cambiarObjetivo() {
		byte a = (byte) r.nextInt(100);

		if (a < 10) {
			cambiarObjetivo(ACCION_MOVER_RANDOM);
		} else if (a < 20) {
			cambiarObjetivo(ACCION_IR_A_POR_RATON);
		} else if (a < 30) {
			cambiarObjetivo(ACCION_HABLAR);
		} else {
			cambiarObjetivo(ACCION_IR_AL_IGLU);
		}
	}

	public void cambiarObjetivo(char obj){
		cambiarObjetivo(obj,"");
	}

	public void cambiarObjetivo(char obj, String parametros){

		switch (obj) {
			case ACCION_HABLAR:
				objetivo = ACCION_HABLAR;
				fijarCursor = false;
				sprite = 0;
				enAnimacion = false;
				animacion = 15;
				System.out.println(parametros);
				if(parametros.equals("")){
					final String[] COMENTARIOS = {"","Hola, ¿Qué tal?", "Si necesitas algo escibelo en el cartel de mi iglú", "No me discrimines por no ser un pato"};
					COMENTARIO=COMENTARIOS[r.nextInt(COMENTARIOS.length - 1) + 1];
				}else{
					COMENTARIO= parametros;
				}
				repaint();
				dormir(-1);
				break;
			case ACCION_MOVER_RANDOM:
				objetivo = ACCION_MOVER_RANDOM;
				x = r.nextInt(1200);
				y = r.nextInt(700);
				enAnimacion = true;
				break;
			case ACCION_IR_A_POR_RATON:
				objetivo = ACCION_IR_A_POR_RATON;
				enAnimacion = true;
				break;
			case ACCION_IR_AL_IGLU:
				objetivo = ACCION_IR_AL_IGLU;
				x = iglu.getX() + 60;
				y = iglu.getY();
				enAnimacion = true;
				break;
			case DESPEDIRSE:
				objetivo = DESPEDIRSE;
				fijarCursor = false;
				sprite = 0;
				enAnimacion = false;
				animacion = 15;
				COMENTARIO = "Antes de irme...";
				repaint();
				dormir(2500);
				COMENTARIO = "Te quiero agradecer por darme un momento de vida y libertad";
				repaint();
				dormir(6500);
				COMENTARIO = "Para ti no será importante pero para mi si, gracias";
				repaint();
				dormir(4000);
				COMENTARIO = "Te tengo cariño en verdad, pero bueno no molesto mas, adios";
				repaint();
				dormir(5000);
				Inicio.apagar();
			default:
				cambiarObjetivo();
		}
	}

	public void actualizar() {
		moverAlAzar();

		if (fijarCursor) {
			robot.mouseMove(getX() + 24, getY() + 45);
			if (r.nextInt(1000) == r.nextInt(1000)) fijarCursor = false;
		}

		if (enAnimacion) {
			animacion++;
			animacion %= 30;
		}
	}

	private void incrementarPos(int i, int j) {
		setLocation(getX() + i, getY() + j);
	}

	private void dormir(int i) {
		try {
			if (i < 0) {
				Thread.sleep(r.nextInt(100) * 80 + 4000);
			} else {
				Thread.sleep(i);
			}
			setVisible(true);
			COMENTARIO="";
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}