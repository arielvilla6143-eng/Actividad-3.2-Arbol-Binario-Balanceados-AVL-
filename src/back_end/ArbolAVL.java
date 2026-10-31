/*
ARIEL VILLA OLIVAREZ
ANGEL EMILIANO REYNOSO LÓPEZ
JULIAN EMMANUEL MENDEZ RODRIGUEZ
 */
package back_end;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    private int obtenerAltura(NodoAVL nodo) {
        if (nodo == null) return 0;
        return nodo.altura;
    }

    private int factorEquilibrio(NodoAVL nodo) {
        if (nodo == null) return 0;
        return obtenerAltura(nodo.derecho) - obtenerAltura(nodo.izquierdo);
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;
        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;

        return y;
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;
        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;

        return x;
    }

    public void insertar(Empleado empleado) throws IllegalArgumentException {
        raiz = insertarRecursivo(raiz, empleado);
    }

    private NodoAVL insertarRecursivo(NodoAVL nodo, Empleado empleado) {
        if (nodo == null) {
            return new NodoAVL(empleado);
        }

        if (empleado.getId() < nodo.dato.getId()) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, empleado);
        } else if (empleado.getId() > nodo.dato.getId()) {
            nodo.derecho = insertarRecursivo(nodo.derecho, empleado);
        } else {
            throw new IllegalArgumentException("El ID ya existe en el sistema.");
        }

        nodo.altura = 1 + Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho));
        int fe = factorEquilibrio(nodo);

        if (fe > 1 && empleado.getId() > nodo.derecho.dato.getId()) {
            return rotacionIzquierda(nodo);
        }
        if (fe < -1 && empleado.getId() < nodo.izquierdo.dato.getId()) {
            return rotacionDerecha(nodo);
        }
        if (fe > 1 && empleado.getId() < nodo.derecho.dato.getId()) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }
        if (fe < -1 && empleado.getId() > nodo.izquierdo.dato.getId()) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        return nodo;
    }

    public void eliminar(Integer id) {
        raiz = eliminarRecursivo(raiz, id);
    }

    private NodoAVL eliminarRecursivo(NodoAVL nodo, Integer id) {
        if (nodo == null) return nodo;

        if (id < nodo.dato.getId()) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, id);
        } else if (id > nodo.dato.getId()) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, id);
        } else {
            if ((nodo.izquierdo == null) || (nodo.derecho == null)) {
                NodoAVL temp = null;
                if (temp == nodo.izquierdo) temp = nodo.derecho;
                else temp = nodo.izquierdo;

                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                NodoAVL temp = nodoConValorMinimo(nodo.derecho);
                nodo.dato = temp.dato;
                nodo.derecho = eliminarRecursivo(nodo.derecho, temp.dato.getId());
            }
        }

        if (nodo == null) return nodo;

        nodo.altura = Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho)) + 1;
        int fe = factorEquilibrio(nodo);

        if (fe > 1 && factorEquilibrio(nodo.derecho) >= 0) return rotacionIzquierda(nodo);
        if (fe > 1 && factorEquilibrio(nodo.derecho) < 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }
        if (fe < -1 && factorEquilibrio(nodo.izquierdo) <= 0) return rotacionDerecha(nodo);
        if (fe < -1 && factorEquilibrio(nodo.izquierdo) > 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        return nodo;
    }

    private NodoAVL nodoConValorMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierdo != null) actual = actual.izquierdo;
        return actual;
    }

    public Empleado buscar(Integer id) {
        NodoAVL resultado = buscarRecursivo(raiz, id);
        return (resultado != null) ? resultado.dato : null;
    }

    private NodoAVL buscarRecursivo(NodoAVL nodo, Integer id) {
        if (nodo == null || nodo.dato.getId().equals(id)) return nodo;
        if (nodo.dato.getId() > id) return buscarRecursivo(nodo.izquierdo, id);
        return buscarRecursivo(nodo.derecho, id);
    }

    public int profundidad() {
        return obtenerAltura(raiz);
    }

    public int amplitud() {
        if (raiz == null) return 0;
        int maxNodos = 0;
        Queue<NodoAVL> cola = new LinkedList<>();
        cola.add(raiz);
        
        while (!cola.isEmpty()) {
            int nodosEnNivel = cola.size();
            maxNodos = Math.max(maxNodos, nodosEnNivel);
            
            for (int i = 0; i < nodosEnNivel; i++) {
                NodoAVL actual = cola.poll();
                if (actual.izquierdo != null) cola.add(actual.izquierdo);
                if (actual.derecho != null) cola.add(actual.derecho);
            }
        }
        return maxNodos;
    }

    public void destruirArbol() {
        raiz = null;
    }

    public List<Empleado> mostrarInOrden() {
        List<Empleado> lista = new ArrayList<>();
        inOrdenRecursivo(raiz, lista);
        return lista;
    }
    private void inOrdenRecursivo(NodoAVL nodo, List<Empleado> lista) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierdo, lista);
            lista.add(nodo.dato);
            inOrdenRecursivo(nodo.derecho, lista);
        }
    }

    public List<Empleado> mostrarPreOrden() {
        List<Empleado> lista = new ArrayList<>();
        preOrdenRecursivo(raiz, lista);
        return lista;
    }
    private void preOrdenRecursivo(NodoAVL nodo, List<Empleado> lista) {
        if (nodo != null) {
            lista.add(nodo.dato);
            preOrdenRecursivo(nodo.izquierdo, lista);
            preOrdenRecursivo(nodo.derecho, lista);
        }
    }

    public List<Empleado> mostrarPostOrden() {
        List<Empleado> lista = new ArrayList<>();
        postOrdenRecursivo(raiz, lista);
        return lista;
    }
    private void postOrdenRecursivo(NodoAVL nodo, List<Empleado> lista) {
        if (nodo != null) {
            postOrdenRecursivo(nodo.izquierdo, lista);
            postOrdenRecursivo(nodo.derecho, lista);
            lista.add(nodo.dato);
        }
    }
}
