package galeria;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.Propietario;
import persistencia.CentralPersistencia;
import persistencia.IPersistencia;
import piezas.Pieza;
import staff.Empleado;

public class Galeria {
	
	private Map<String, PiezaConPrecioFijo> piezasOfertasFijas;
	private Map<String, PiezaEnSubasta> piezasSubasta;
	private Map<String, Pieza> inventario; 
	private Map<String, Comprador> compradores; 
	private Map<String, Propietario> propietarios; 

	public Galeria() {
		inventario = new HashMap<String, Pieza>( );
		piezasOfertasFijas = new HashMap<String, PiezaConPrecioFijo>( );
		piezasSubasta = new HashMap<String, PiezaEnSubasta>( );
		compradores = new HashMap<String, Comprador>( );
		propietarios = new HashMap<String, Propietario>( );
	}
	
	public void addPiezas(Pieza pieza) {
		inventario.put(pieza.getTitulo(), pieza);
	}
	
	public Collection<Pieza> getInventario() {
		return inventario.values();
	}
	
	public void deletePiezaInventario(String id) {
		inventario.remove(id);
	}
	
	public void addPiezasSubasta(PiezaEnSubasta pieza) {
		String id = pieza.getPieza().getTitulo();
		piezasSubasta.put(id, pieza);
	}
	
	
	public Map<String, PiezaEnSubasta> getPiezasEnSubasta() {
		return piezasSubasta;
	}
	
	public PiezaEnSubasta getPiezaEnSubasta(String id) {
		return piezasSubasta.get(id);
	}
	
	public void addPiezasFijas(PiezaConPrecioFijo pieza) {
		String id = pieza.getPieza().getTitulo();
		piezasOfertasFijas.put(id, pieza);
	}
	
	public Map<String, PiezaConPrecioFijo> getPiezasEnOfertaFija() {
		return piezasOfertasFijas;
	}
	
	public Collection<PiezaConPrecioFijo> getEnOfertaFija() {
		return piezasOfertasFijas.values();
	}
	
	public PiezaConPrecioFijo getPiezaEnOfertaFija(String id) {
		return piezasOfertasFijas.get(id);
	}
	
	
	public void addComprador(Comprador comprador) {
		String id = comprador.getNombre();
		compradores.put(id, comprador);
	}
	
	public Collection<Comprador> getCompradores() {
		return compradores.values();
	}
	
	public Comprador getComprador(String id) {
		return compradores.get(id);
	}
	
	public void addPropietario(Propietario propietario) {
		String id = propietario.getNombre();
		propietarios.put(id, propietario);
	}
	
	public Map<String, Propietario> getPropietarios() {
		return propietarios;
	}
	
	public Propietario getPropietario(String id) {
		return propietarios.get(id);
	}
	
	public void cargarDatos( String archivo ) throws IOException{
		IPersistencia cargador = CentralPersistencia.getPersistencia();
		cargador.cargarCompradores(archivo, this);
		cargador.cargarPiezas(archivo, this);
		cargador.cargarPropietarios(archivo, this);
    }
	
	/*public void cargarCompradores( String archivo ) throws IOException{
		IPersistencia cargador = CentralPersistencia.getPersistencia();
		
    }
	public void cargarPropietarios( String archivo ) throws IOException{
		IPersistencia cargador = CentralPersistencia.getPersistencia();
		
    }*/
	
}
