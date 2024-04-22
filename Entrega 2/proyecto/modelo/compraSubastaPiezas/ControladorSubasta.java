package compraSubastaPiezas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import galeria.Galeria;
import piezas.Pieza;
import staff.Administrador;
import staff.Cajero;
import staff.Operador;

public class ControladorSubasta {
	 
	private Galeria galeria;
	private Map<String, PiezaEnSubasta> historialPiezasEnSubasta;

	public ControladorSubasta(Galeria galeria) {
		this.galeria = galeria;
	}
	
	public void addPiezaASubasta(PiezaEnSubasta pieza) {
		galeria.addPiezasSubasta(pieza);
	}
	
	public void ofertarPieza(Comprador comprador,int valor, String id) {
		
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getEstado().compareTo("disponible") == 0) {
			if((valor >= pieza.getValorInicial()) && (comprador.isVerificado() == true)) {
				Oferta nuevaOferta = new Oferta(valor, comprador, pieza.getPieza(), "Oferta en subasta");
				pieza.addOfertas(comprador.getNombre(), nuevaOferta);
			}
		}
		
	}
	public void elegirPiezaGanadora(Administrador adminitrador, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getOfertas().size() >= 2) {
			Collection<Oferta> ofertas = pieza.getOfertas().values();
			Oferta ofertaGanadora = new Oferta(0, null, null, null);
			for (Oferta oferta : ofertas) {
				if((oferta.getValor() > ofertaGanadora.getValor()) || (ofertaGanadora == (Oferta) null))
					ofertaGanadora = oferta;
			}
			pieza.setGanador(ofertaGanadora.getComprador().getNombre());
			pieza.setEstado("finalizado pendiente por revision de pago");
		}
	}
	public void verificarPagoGanador(Cajero cajero, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getEstado().equals("finalizado pendiente por revision de pago")) {
			Comprador ganador = galeria.getComprador(pieza.getGanador());
			if(pieza.getOfertas().get(pieza.getGanador()).getValor() <= ganador.getCapital()) {
				Pieza piezaGanador = pieza.getPieza();
				galeria.deletePiezaInventario(pieza.getPieza().getTitulo());
				ganador.setCapital(ganador.getCapital() - pieza.getOfertas().get(pieza.getGanador()).getValor());
				pieza.setEstado("finalizado");
				if(galeria.getPropietarios().containsKey(ganador.getNombre())) {
					galeria.getPropietario(ganador.getNombre()).getPiezas().put(piezaGanador.getTitulo(), piezaGanador);
				}else {
					Map<String, Pieza> piezas = new HashMap<String, Pieza>();
					piezas.put(piezaGanador.getTitulo(), piezaGanador);
					Propietario nuevoPropietario = new Propietario(ganador.getNombre(), null, null, piezas);
					galeria.addPropietario(nuevoPropietario);
				}
			} else {
				pieza.setEstado("disponible");
				pieza.getOfertas().remove(pieza.getGanador());
				pieza.setGanador(null);
			}
		}
	}
	
	public void revisarSubasta(Administrador administrador, Cajero cajero, Operador operador, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(administrador != null) {
			if(pieza.getEstado().compareTo("disponible") == 0) {
				elegirPiezaGanadora(administrador, id);
			}
		}else if(cajero != null) {
			if(pieza.getEstado().compareTo("finalizado pendiente por revision de pago") == 0)
				verificarPagoGanador(cajero, id);
		}else if(operador != null) {
			if(pieza.getEstado().compareTo("finalizado") == 0)
				registrarDatosSubasta(operador, id);
		}
	}

	public void registrarDatosSubasta(Operador operador, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getEstado().equals("finalizado")) {
			String llave = "ganador: " + pieza.getGanador() + ", pieza: " + id;
			historialPiezasEnSubasta.put(llave, pieza);
			galeria.getPiezasEnSubasta().remove(id);
		}
		
	}
	
	


}
