package compraSubastaPiezas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import staff.Administrador;
import staff.Cajero;
import galeria.Galeria;
import piezas.Pieza;

public class ControladorOfertasFijas {
	private Map<String, Oferta> ofertas;
	private Galeria galeria;
	
	
	public ControladorOfertasFijas(Galeria galeria) {
		ofertas = new HashMap<String, Oferta>();
		this.galeria = galeria;
	}
	
	public Map<String, Oferta> getOfertas(){
		return ofertas;
	}
	
	public Oferta getOferta(String id) {
		return ofertas.get(id);
	}
	
	public void ofertarPieza(Comprador comprador, int valor, String id) {
		PiezaConPrecioFijo pieza = galeria.getPiezaEnOfertaFija(id);
		if(valor >= pieza.getPrecio()) {
			Oferta oferta = new Oferta(pieza.getPrecio(), comprador,  pieza.getPieza(), "pendiente de revision por administrador");
			String key = "Oferta hecha por: " + comprador.getNombre() + " " + pieza.getPieza().getTitulo();
			ofertas.put(key, oferta);
			galeria.getPiezasEnOfertaFija().remove(id);
		}
	}
	
	public void verificarOfertaAdministrador(Administrador administrador, String id) {
		Oferta oferta = ofertas.get(id);
		String idComprador = oferta.getComprador().getNombre();
		Comprador comprador = galeria.getComprador(idComprador);
		if (comprador.isVerificado() == false) {
			comprador.setVerificado(true);
			comprador.setTopeDeCompras(2);
			oferta.setEstado("pendiente de revision por cajero");
		} else {
			comprador.setTopeDeCompras(comprador.getTopeDeCompras() - 1);
			if(comprador.getTopeDeCompras() <= 0) {
				PiezaConPrecioFijo pieza = new PiezaConPrecioFijo(oferta.getValor(), oferta.getPieza(), "disponible");
				galeria.addPiezasFijas(pieza);
				ofertas.remove(id);
			} else {
				oferta.setEstado("pendiente de revision por cajero");
			}
		}
	}
	
	public void verificarOfertaCajero(Cajero cajero, String id) {
		Oferta oferta = ofertas.get(id);
		String idComprador = oferta.getComprador().getNombre();
		Comprador comprador = galeria.getComprador(idComprador);
		if(comprador.getCapital() >= oferta.getValor()) {
			comprador.setCapital(comprador.getCapital() - oferta.getValor());
			darPiezaAComprador(comprador.getNombre(), oferta.getPieza());
			galeria.deletePiezaInventario(id);
			oferta.setEstado("pendiente de confirmacion de venta");
		} else {
			PiezaConPrecioFijo pieza = new PiezaConPrecioFijo(oferta.getValor(), oferta.getPieza(), "disponible");
			galeria.addPiezasFijas(pieza);
			ofertas.remove(id);
		}
	}
	
	public void darPiezaAComprador(String id, Pieza pieza) {
		Map<String, Propietario> propietarios = galeria.getPropietarios();
		if(propietarios.containsKey(id)) {
			Propietario propietario = propietarios.get(id);
			propietario.getPiezas().put(pieza.getTitulo(), pieza);
		} else {
			Map<String, Pieza> piezas = new HashMap<String, Pieza>();
			piezas.put(pieza.getTitulo(), pieza);
			Propietario nuevoPropietario = new Propietario(id, null, null, piezas);
			galeria.addPropietario(nuevoPropietario);
		}
		
	}
	
	public void confirmarVenta(Administrador administrador, String id) {
		ofertas.remove(id);
		System.out.println("se verifico venta");
	}
	
	public void revisarOferta(Administrador administrador, Cajero cajero, String id) {
		Oferta oferta = ofertas.get(id);
		if (cajero == null) {
			if(oferta.getEstado().equals("pendiente de revision por administrador")) 
				verificarOfertaAdministrador(administrador, id);
			else if(oferta.getEstado().equals("pendiente de confirmacion de venta")) 
				confirmarVenta(administrador, id);
		} else if(administrador == null) {
			if(oferta.getEstado().equals("pendiente de revision por cajero"))
				verificarOfertaCajero(cajero, id);
		}
	}
	
	public void cargarOfertas() {
		Collection<PiezaConPrecioFijo> ofertas1 = galeria.getEnOfertaFija();
		List<String> ids = new ArrayList<String>();
		for (Iterator<PiezaConPrecioFijo> iterator = ofertas1.iterator(); iterator.hasNext();) {
			PiezaConPrecioFijo piezaConPrecioFijo = (PiezaConPrecioFijo) iterator.next();
			if(piezaConPrecioFijo.getIdComprador().compareTo("disponible") != 0) {
				Pieza pieza = piezaConPrecioFijo.getPieza();
				Comprador comprador = galeria.getComprador(piezaConPrecioFijo.getIdComprador());
				Oferta oferta = new Oferta(piezaConPrecioFijo.getPrecio(), comprador,  pieza, "pendiente de revision por administrador");
				String key = "Oferta hecha por: " + comprador.getNombre() + " " + pieza.getTitulo();
				ofertas.put(key, oferta);
				ids.add(pieza.getTitulo());
				
			}
		}
		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i);
			galeria.getPiezasEnOfertaFija().remove(id);
		}
	}
	
}