package persistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.Oferta;
import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.Propietario;
import galeria.Galeria;
import staff.ControladorEmpleados;
import staff.Empleado;
import staff.Administrador;
import staff.Operador;
import staff.Cajero;
import piezas.Pieza;
import piezas.PiezaFisica;
import piezas.PiezaVirtual;

public class PersistenciaJson implements IPersistencia{
	
	private static final String NOMBRE_EMPLEADO = "nombre";
    private static final String TIPO_EMPLEADO = "tipoEmpleado";
    private static final String ISFISICO = "isFisico";

    public void cargarEmpleados( String archivo, ControladorEmpleados empleados)  throws IOException
    {
	        String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
	        JSONObject raiz = new JSONObject( jsonCompleto );
	
	        crearEmpleados( empleados, raiz.getJSONArray( "empleados" ) );
    }
    
	private void crearEmpleados( ControladorEmpleados empleados, JSONArray jEmpleados ) {
        int numEmpleados = jEmpleados.length( );
        for( int i = 0; i < numEmpleados; i++ )
        {
            JSONObject empleado = jEmpleados.getJSONObject( i );
            String tipoEmpleado = empleado.getString( TIPO_EMPLEADO );
            Empleado nuevoEmpleado = null;
            if( Cajero.CAJERO.equals( tipoEmpleado ) )
            {
                String nombre = empleado.getString( NOMBRE_EMPLEADO );
                String contraseña = empleado.getString("contraseña");
                nuevoEmpleado = new Cajero( nombre , contraseña);
                empleados.addEmpleado(nuevoEmpleado);
            }
            else if(Administrador.ADMINISTRADOR.equals(tipoEmpleado))
            {
            	String nombre = empleado.getString( NOMBRE_EMPLEADO );
            	String contraseña = empleado.getString("contraseña");
            	nuevoEmpleado = new Administrador( nombre, contraseña );
            	empleados.addEmpleado(nuevoEmpleado);
            }
            else if(Operador.OPERADOR.equals(tipoEmpleado))
            {
            	String nombre = empleado.getString( NOMBRE_EMPLEADO );
            	String contraseña = empleado.getString("contraseña");
            	nuevoEmpleado = new Operador( nombre, contraseña );
            	empleados.addEmpleado(nuevoEmpleado);
            }
			
 
        }
    }
	
	public void cargarPiezas( String archivo, Galeria galeria) throws IOException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        crearPiezas( galeria, raiz.getJSONArray( "piezas" ) );
    }
	
	public void crearPiezas(Galeria galeria, JSONArray jPiezas) {
		int numPiezas = jPiezas.length( );
        for( int i = 0; i < numPiezas; i++ )
        {
            JSONObject pieza = jPiezas.getJSONObject( i );
            boolean isFisico = pieza.getBoolean(ISFISICO);
            Pieza nuevaPieza = null;
            if( PiezaFisica.ISFISICO == isFisico)
            {
                String titulo = pieza.getString( "titulo" );
                String anio = pieza.getString( "anio" );
                String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                String autor = pieza.getString( "autor" );
                boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                String tipo = pieza.getString( "tipo" );
                double profundidad = pieza.getDouble( "profundidad" );
                double alto = pieza.getDouble( "alto" );
                double ancho = pieza.getDouble( "ancho" );
                double peso = pieza.getDouble( "peso" );
                boolean needsElectricity = pieza.getBoolean( "needsElectricity" );
                boolean needsOther = pieza.getBoolean( "needsOther" );
                nuevaPieza = new PiezaFisica(tipo, titulo, anio, lugarDeCreacion, autor, isPrecioFijo, profundidad,  alto, ancho, peso, needsElectricity, needsOther);
                galeria.addPiezas(nuevaPieza);
                if(isPrecioFijo == true) {
                	int precio = pieza.getInt("precio");
                	String idComprador = pieza.getString("idComprador");
                	PiezaConPrecioFijo nuevaPiezaPrecio = new PiezaConPrecioFijo(precio, nuevaPieza, idComprador);
                	galeria.addPiezasFijas(nuevaPiezaPrecio);
                } else {
                	float valorMinimo = pieza.getFloat("valorMinimo");
                	float valorInicial = pieza.getFloat("valorInicial");
                	String estado = pieza.getString("estado");
                	Map<String, Oferta> ofertas = new HashMap<String, Oferta>();
                	JSONArray jPujadores = pieza.getJSONArray("ofertas");
                    for (int j = 0; j < jPujadores.length(); j++) {
                        JSONObject pujador = jPujadores.getJSONObject(j);
                        String idComprador = pujador.getString("idComprador");
                        int valor = pujador.getInt("valor");
                        Oferta oferta = new Oferta(valor, galeria.getComprador(idComprador), nuevaPieza, "enSubasta");
                        ofertas.put(idComprador, oferta);
                        
                    }
                	PiezaEnSubasta nuevaPiezaSubasta = new PiezaEnSubasta(valorMinimo, valorInicial, estado, nuevaPieza,  ofertas);
                	galeria.addPiezasSubasta(nuevaPiezaSubasta);
                	System.out.println("aaa");
                }
            }
            else if(PiezaVirtual.ISFISICO == isFisico)
            {
            	String titulo = pieza.getString( "titulo" );
                String anio = pieza.getString( "anio" );
                String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                String autor = pieza.getString( "autor" );
                boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                String tipo = pieza.getString( "tipo" );
                String formato = pieza.getString( "formato" );
            	nuevaPieza = new PiezaVirtual(tipo, formato, titulo,  anio,  lugarDeCreacion, autor, isPrecioFijo);
            	if(isPrecioFijo == true) {
                	int precio = pieza.getInt("precio");
                	String idComprador = pieza.getString("idComprador");
                	PiezaConPrecioFijo nuevaPiezaPrecio = new PiezaConPrecioFijo(precio, nuevaPieza, idComprador);
                	galeria.addPiezasFijas(nuevaPiezaPrecio);
                } else {
                	float valorMinimo = pieza.getFloat("valorMinimo");
                	float valorInicial = pieza.getFloat("valorInicial");
                	String estado = pieza.getString("estado");
                	HashMap<String, Oferta> ofertas = new HashMap<String, Oferta>();
                	JSONArray jPujadores = pieza.getJSONArray("ofertas");
                    for (int j = 0; j < jPujadores.length(); j++) {
                        JSONObject pujador = jPujadores.getJSONObject(j);
                        String idComprador = pujador.getString("idComprador");
                        int valor = pujador.getInt("valor");
                        Oferta oferta = new Oferta(valor, galeria.getComprador(idComprador), nuevaPieza, "enSubasta");
                        ofertas.put(idComprador, oferta);
                    }
                	PiezaEnSubasta nuevaPiezaSubasta = new PiezaEnSubasta(valorMinimo, valorInicial, estado, nuevaPieza,  ofertas);
                	galeria.addPiezasSubasta(nuevaPiezaSubasta);
                }
            }
        }
	}
	public void cargarCompradores(String archivo, Galeria galeria) throws IOException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        crearCompradores( galeria, raiz.getJSONArray( "compradores" ) );
	}
	private void crearCompradores( Galeria galeria, JSONArray jCompradores) {
        int numCompradores = jCompradores.length( );
        for( int i = 0; i < numCompradores; i++ )
        {
            JSONObject comprador = jCompradores.getJSONObject( i );
            Comprador nuevoComprador = null;
            String nombre = comprador.getString( "nombre" );
            int capital = comprador.getInt("capital");
            boolean isVerificado = comprador.getBoolean("isVerificado");
            String metodoPago = comprador.getString("metodo-pago");
            int topeCompras = comprador.getInt("tope-compras");
            nuevoComprador = new Comprador(isVerificado, nombre,  metodoPago, capital, topeCompras );
            galeria.addComprador(nuevoComprador);
        }
    }
	
	public void cargarPropietarios(String archivo, Galeria galeria) throws IOException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );
        
        crearPropietarios( galeria, raiz.getJSONArray( "propietarios" ) );
	}

	private void crearPropietarios( Galeria galeria, JSONArray jPropietarios) {
        int numPropietarios = jPropietarios.length( );
        for( int i = 0; i < numPropietarios; i++ )
        {
            JSONObject propietario = jPropietarios.getJSONObject( i );
            Propietario nuevoPropietario = null;
            String nombre = propietario.getString( "nombre" );
            String numero = propietario.getString("numero");
            String correo = propietario.getString("correo");
            Map<String, Pieza> piezasPropietario = new HashMap<String, Pieza>();
            JSONArray jPiezas = propietario.getJSONArray("piezas");
            for (int j = 0; j < jPiezas.length(); j++) {
                JSONObject pieza = jPiezas.getJSONObject(j);
                boolean isFisico = pieza.getBoolean(ISFISICO);
                Pieza nuevaPieza = null;
                if( PiezaFisica.ISFISICO == isFisico)
                {
                    String titulo = pieza.getString( "titulo" );
                    String anio = pieza.getString( "anio" );
                    String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                    String autor = pieza.getString( "autor" );
                    boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                    String tipo = pieza.getString( "tipo" );
                    double profundidad = pieza.getDouble( "profundidad" );
                    double alto = pieza.getDouble( "alto" );
                    double ancho = pieza.getDouble( "ancho" );
                    double peso = pieza.getDouble( "peso" );
                    boolean needsElectricity = pieza.getBoolean( "needsElectricity" );
                    boolean needsOther = pieza.getBoolean( "needsOther" );
                    nuevaPieza = new PiezaFisica(tipo, titulo, anio, lugarDeCreacion, autor, isPrecioFijo, profundidad,  alto, ancho, peso, needsElectricity, needsOther);
                    piezasPropietario.put(titulo, nuevaPieza);
                }
                else if(PiezaVirtual.ISFISICO == isFisico)
                {
                	String titulo = pieza.getString( "titulo" );
                    String anio = pieza.getString( "anio" );
                    String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                    String autor = pieza.getString( "autor" );
                    boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                    String tipo = pieza.getString( "tipo" );
                    String formato = pieza.getString( "formato" );
                	nuevaPieza = new PiezaVirtual(tipo, formato, titulo,  anio,  lugarDeCreacion, autor, isPrecioFijo);
                	piezasPropietario.put(titulo, nuevaPieza);
                }
            }
            nuevoPropietario = new Propietario(nombre, numero, correo, piezasPropietario);
            galeria.addPropietario(nuevoPropietario);
        }
    }

	/*private void salvarEmpleados( ControladorEmpleados empleados, JSONObject jobject )
    {
        JSONArray jEmpleados = new JSONArray( );
        for( Empleado empleado : aerolinea.getClientes( ) )
        {
            // Acá también se utilizaron dos estrategias para salvar los clientes.
            // Para los clientes naturales, esta clase extrae la información de los objetos y la organiza para que luego sea salvada.
            // Para los clientes corporativos, la clase ClienteCorporativo hace todo lo que está en sus manos para persistir un cliente
            if( ClienteNatural.NATURAL.equals( cliente.getTipoCliente( ) ) )
            {
                JSONObject jCliente = new JSONObject( );
                jCliente.put( NOMBRE_CLIENTE, cliente.getIdentificador( ) );
                jClientes.put( jCliente );
            }
            else
            {
                ClienteCorporativo cc = ( ClienteCorporativo )cliente;
                JSONObject jCliente = cc.salvarEnJSON( );
                jClientes.put( jCliente );
            }
        }

        jobject.put( "clientes", jClientes );
    }*/
}
