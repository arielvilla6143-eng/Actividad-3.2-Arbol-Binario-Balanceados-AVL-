/*
ARIEL VILLA OLIVAREZ
ANGEL EMILIANO REYNOSO LÓPEZ
JULIAN EMMANUEL MENDEZ RODRIGUEZ
 */
package back_end;

/**
 *
 * @author villa
 */
public class NodoAVL {
    Empleado dato;
    NodoAVL izquierdo;
    NodoAVL derecho;
    int altura;

    public NodoAVL(Empleado dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 1;
    }
}
