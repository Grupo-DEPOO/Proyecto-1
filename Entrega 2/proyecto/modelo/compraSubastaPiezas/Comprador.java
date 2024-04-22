package compraSubastaPiezas;

public class Comprador {
	
	private boolean isVerificado;
	private String nombre;
	private String metodoPago;
	private int capital;
	private int topeDeCompras;
	
	public Comprador(boolean isVerificado, String nombre, String metodoPago, int capital, int topeDeCompras) {
		this.isVerificado = isVerificado;
		this.nombre = nombre;
		this.metodoPago = metodoPago;
		this.capital = capital;
		this.topeDeCompras = topeDeCompras;
	}

	public boolean isVerificado() {
		return isVerificado;
	}

	public void setVerificado(boolean isVerificado) {
		this.isVerificado = isVerificado;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public int getTopeDeCompras() {
		return topeDeCompras;
	}

	public void setTopeDeCompras(int topeDeCompras) {
		this.topeDeCompras = topeDeCompras;
	}

	public String getNombre() {
		return nombre;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	
}
