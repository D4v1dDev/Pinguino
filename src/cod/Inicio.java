package cod;

import cod.ventana.Penguin;

public class Inicio {

	private static boolean encendido = true;

	public static void main(String[] args) {

		long t=System.nanoTime();
		final Penguin penguin = new Penguin();


		while(encendido) {
			if(t+10000000L<System.nanoTime()) {
				penguin.actualizar();
				penguin.repaint();
				t=System.nanoTime();
			}
		}
		penguin.dispose();
		System.exit(0);
	}

	public static void apagar(){
		encendido = false;
	}
}
