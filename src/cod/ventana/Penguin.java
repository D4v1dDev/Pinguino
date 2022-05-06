package cod.ventana;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import cod.Inicio;
import cod.herramientas.CargadorRecursos;

import javax.swing.*;

public class Penguin extends VentanaTranslucida {

	private int x = 500, y = 500;
	private static String COMENTARIO="";

	public final static char ACCION_HABLAR='q', ACCION_IR_A_POR_RATON='m',ACCION_MOVER_RANDOM='0',DESPEDIRSE='d',MOVER_COLA='c';

	private final static Random r = new Random();
	private final static BufferedImage[][] sprites = new BufferedImage[3][5];

	private static Robot robot = null;

	private char objetivo;
	private boolean fijarCursor = false;
	private byte sprite = 1;
	private static final long serialVersionUID = 1L;
	private boolean enAnimacion = true;
	private int animacion = 0;

	public Penguin() {
		super(48 + 300, 48 + 30);

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		BufferedImage img = CargadorRecursos.obtenerImagenTranslucida("src/res/penguin.png");

		for (int x = 0; x < sprites.length; x++) {
			for (int y = 0; y < sprites[x].length; y++) {
				sprites[x][y] = img.getSubimage(x * 48, y * 48, 48, 48);
			}
		}
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				if (g instanceof Graphics2D) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.clearRect(0,0,getWidth(),getHeight());
					g2d.drawImage(sprites[animacion/10][sprite], 0, 20, null);
					if (!COMENTARIO.equals("")) {
						g2d.setColor(Color.white);
						int longitudFrase = (COMENTARIO.length() * 6) + 10;
						if(longitudFrase < getWidth()){
							g2d.fillRect(0, 15,  longitudFrase, 20);
							g2d.setColor(Color.BLACK);
							g2d.drawString(COMENTARIO, 3, 30);
						}else{
							g2d.fillRect(0,0,longitudFrase/2, 35);
							g2d.setColor(Color.BLACK);
							g2d.drawString(COMENTARIO.substring(0,COMENTARIO.length()/2)+"-", 3, 15);
							g2d.drawString(COMENTARIO.substring(COMENTARIO.length()/2), 3, 30);
						}
					}
				}
			}
		};
		setContentPane(panel);
		setVisible(true);
	}

	public void moverAlAzar() {

		if(objetivo == MOVER_COLA){
			sprite = 4;
			if(r.nextInt(1000)==0){
				cambiarObjetivo();
			}
			return;
		}

		//Relocalizar Cursor
		if (objetivo == ACCION_IR_A_POR_RATON) {
			x = (int) MouseInfo.getPointerInfo().getLocation().getX() - 24;
			y = (int) MouseInfo.getPointerInfo().getLocation().getY() - 25;
		}

		//Dirigirse hacia el objetivo
		if (getX() == x) {
			if (getY() == y) {
				//Al llegar al objetivo
				if (objetivo == ACCION_IR_A_POR_RATON) {
					fijarCursor = true;
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
		final byte MAX = 100;
		final byte a = (byte) r.nextInt(MAX);



		if(a==0) {
			cambiarObjetivo(DESPEDIRSE);
			return;
		}
		if (a < MAX * 50 / 100 ) {
			cambiarObjetivo(ACCION_MOVER_RANDOM);
		} else if (a < MAX * 55 / 100) {
			cambiarObjetivo(ACCION_IR_A_POR_RATON);
		} else if (a < MAX * 90 / 100){
			cambiarObjetivo(ACCION_HABLAR);
		}else{
			cambiarObjetivo(MOVER_COLA);
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
				if(parametros.equals("")){
					final String[] COMENTARIOS = {"","Hola, ¿Qué tal?", "Puff, estoy muy cansado", "No me discrimines por no ser un pato","¿Cuando me vas a dar vacaciones?","Me gustaria sentir el frio polar", "Dios, es que hoy te ves malditamente bien","Soy perfecto, lo se","¿Quieres que seamos amigos?","Hay gente que está derritiendo el ártico, personalmente me ocuparé de ellos"};
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
			case MOVER_COLA:
				objetivo = MOVER_COLA;
				x=getX();
				y=getY();
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
		if(getX() > Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()){
			if(i > 0){
				setSize(getWidth()-i,getHeight());
				setLocation(getX() + i, getY() + j);
			}else if(i < 0){
				setSize(getWidth()+i,getHeight());
			}
		}
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