package compraSubastaPiezas;
import java.util.Map;

import piezas.Pieza;
public class Propietario{

    private String Nombre;
    private String NumeroDeTelefono;
    private String Correo;
    private Map<String, Pieza> Piezas;

    // Constructor
    public Propietario(String Nombre, String NumeroDeTelefono, String Correo, Map<String, Pieza> Piezas) {
        this.Nombre = Nombre;
        this.NumeroDeTelefono = NumeroDeTelefono;
        this.Correo = Correo;
        this.Piezas = Piezas;
    }

    // Getters
    public String getNombre() {
        return Nombre;
    }

    public String getNumeroDeTelefono() {
        return NumeroDeTelefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public Map<String, Pieza> getPiezas() {
        return Piezas;
    }
    
    public Pieza getPieza(String nombre){
        Pieza pieza1 = Piezas.get(nombre);
        return pieza1;
    }
    
}