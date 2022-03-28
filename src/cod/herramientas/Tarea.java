package cod.herramientas;

import cod.ventana.Pinguino;

public class Tarea {

    private char objetivoADevolver = Pinguino.ACCION_MOVER_RANDOM;
    String parametros = "";

    public Tarea(String s) {
        String[] a = s.split(" ");

        switch (a[0].toLowerCase()) {
            case "hablame":
            case "di":
            case "dime":
            case "conversame":
                objetivoADevolver = Pinguino.ACCION_HABLAR;
                parametros = s.substring(a[0].length()).trim();

                break;
            case "metete":
                objetivoADevolver = Pinguino.ACCION_IR_AL_IGLU;
                break;
            case "vete":
                objetivoADevolver = Pinguino.DESPEDIRSE;
                parametros = "Antes de irme, te quiero decir, que gracias por dejarme existir este poco tiempo";

                break;
            case "juega":
            case "persigueme":
                objetivoADevolver = Pinguino.ACCION_IR_A_POR_RATON;
                break;
            case "":
            default:
        }
    }

    public Object[] cumplirTarea() {
        return new Object[]{objetivoADevolver, parametros};
    }
}
