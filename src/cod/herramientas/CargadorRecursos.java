package cod.herramientas;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CargadorRecursos {
	
	public static BufferedImage obtenerImagenTranslucida(String ruta) {

		/*
		EL MALDITO FALLO AQUI ESQUE EN EL JAR NO SE CARGA LA IMAGEN DEL PINGUINO
		 */

		Image imagen = null;
		try {
			System.out.println(new File(ruta).getAbsolutePath());
			imagen = ImageIO.read(new File(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		}

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

		BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.TRANSLUCENT);

		Graphics g = imagenAcelerada.getGraphics();
		g.drawImage(imagen, 0, 0, null);
		g.dispose();

		return imagenAcelerada;
	}
}