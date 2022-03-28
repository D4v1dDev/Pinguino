package cod;

import cod.ventana.Iglu;
import cod.ventana.Pinguino;

public class Inicio {

	private static boolean encendido = true;

	public static void main(String[] args) {

		long t=System.nanoTime();
		final Pinguino pinguino = new Pinguino(new Iglu(0,390));


		while(encendido) {
			if(t+10000000L<System.nanoTime()) {
				pinguino.actualizar();
				pinguino.repaint();
				t=System.nanoTime();
			}
		}
		pinguino.dispose();
		System.exit(0);
	}

	public static void apagar(){
		encendido = false;
	}
}
