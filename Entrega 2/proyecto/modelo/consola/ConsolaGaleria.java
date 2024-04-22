package consola;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.ControladorSubasta;
import compraSubastaPiezas.Oferta;
import staff.Administrador;
import staff.Cajero;
import staff.ControladorEmpleados;
import galeria.Galeria;
import piezas.Pieza;
import piezas.PiezaFisica;
import piezas.PiezaVirtual;
import staff.Empleado;
import staff.Operador;

public class ConsolaGaleria {

	    private ControladorEmpleados unosEmpleados;
	    private ControladorOfertasFijas ofertas;
	    private ControladorSubasta subastasP;
	    private Galeria galeria;

	    /**
	     * Es un método que corre la aplicación y realmente no hace nada interesante: sólo muestra cómo se podría utilizar la clase Aerolínea para hacer pruebas.
	     */
	    public void cargarDatos() {
	    	try
	        {
	            unosEmpleados = new ControladorEmpleados( );
	            galeria = new Galeria();
	            Scanner myObj = new Scanner(System.in);
	            System.out.println("Ingrese el nombre del archivo: ");
	            String datos = myObj.nextLine() + ".json";
	            unosEmpleados.cargarEmpleados("./datos/" + datos);
	            galeria.cargarDatos("./datos/" + datos);
	            subastasP = new ControladorSubasta(galeria);
	            ofertas = new ControladorOfertasFijas(galeria);
	            ofertas.cargarOfertas();
	        } catch( IOException e )
	        {
	            e.printStackTrace();
	        }
	    	
	    }
	    
	    
	    public void correrAplicacion( ) {
	        Scanner myObj = new Scanner(System.in);
	        boolean continuar = true;

	        while (continuar) {
	            System.out.println("1. probar funciones de un empleado\n"
	                    + "2. probar funciones de un comprador\n"
	                    + "3. Salir");
	            String decision = myObj.nextLine();

	            switch (decision) {
	                case "1":
	                    probarEmpleado();
	                    break;
	                case "2":
	                    probarComprador();
	                    break;
	                case "3":
	                    continuar = false;
	                    System.out.println("Saliendo de la aplicación...");
	                    break;
	                default:
	                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
	                    break;
	            }
	        }
	    }
	    public void probarComprador() {
	        try {
	            Collection<Comprador> compradores = galeria.getCompradores();
	            for (Comprador comprador : compradores) {
	                System.out.println("-------------------------");
	                System.out.println("Nombre: " + comprador.getNombre());
	                System.out.println("Capital: " + comprador.getCapital());
	                System.out.println("topeDeCompras: " + comprador.getTopeDeCompras());
	                System.out.println("es verificado: " + comprador.isVerificado());
	                System.out.println("metodoDePago: " + comprador.getMetodoPago());
	                System.out.println("-------------------------");
	            }
	            Scanner myObj = new Scanner(System.in);
	            System.out.println("Digite el nombre del comprador que desea: ");
	            String usuario = myObj.nextLine();
	            Comprador comprador = galeria.getComprador(usuario);
	            funcionalidadesComprador(comprador);
	        } catch (Exception e) {
	            System.out.println("Error al probar compradores: " + e.getMessage());
	            // Aquí puedes decidir si deseas manejar el error de alguna manera específica o simplemente continuar con la ejecución del programa.
	        }
	    }
	    
	    public void funcionalidadesComprador(Comprador comprador) {
	    	Scanner myObj = new Scanner(System.in);
	    	System.out.println("1.Hacer una oferta a una pieza\n2.Hacer una oferta a una subasta");
	    	String decision = myObj.nextLine();
	    	if(decision.compareTo("1") == 0) {
	    		System.out.println("Piezas a oferta fija disponibles: ");
	    		Collection<PiezaConPrecioFijo> piezasAoferta = galeria.getEnOfertaFija(); 
	    		for (PiezaConPrecioFijo piezaConPrecioFijo : piezasAoferta) {
	    			System.out.println("-------------------------");
	    			System.out.println("Pieza: " + piezaConPrecioFijo.getIdComprador());
	    			System.out.println("Pieza: " + piezaConPrecioFijo.getPieza().getTitulo());
	    			System.out.println("Precio: " + piezaConPrecioFijo.getPrecio());
	    			System.out.println("-------------------------");
					
				}
	    		Scanner myObj2 = new Scanner(System.in);
		    	System.out.println("Digite el nombre de la pieza que desea hacer una oferta: ");
		    	String id = myObj2.nextLine();
		    	Scanner myObj3 = new Scanner(System.in);
		    	System.out.println("Digite el dinero que desea ofertar recuerde que debe ofrecer mas del precio de la pieza: ");
		    	int valor = myObj3.nextInt();
		    	ofertas.ofertarPieza(comprador, valor, id);
	    	} else if(decision.compareTo("2") == 0) {
	    		System.out.println("Subasta disponibles: ");
	    		Collection<PiezaEnSubasta> subastas = galeria.getPiezasEnSubasta().values();
	    		for (PiezaEnSubasta piezaEnSubasta : subastas) {
	    			if(piezaEnSubasta.getEstado().compareTo("disponible") == 0) {
		    			System.out.println("-------------------------");
		    			System.out.println("Valor inicial: " + piezaEnSubasta.getValorInicial());
		    			System.out.println("Estado: " + piezaEnSubasta.getEstado());
		    			System.out.println("Pieza: " + piezaEnSubasta.getPieza().getTitulo());
		    			System.out.println("-------------------------");
	    			}
				}
	    		
	    		Scanner myObj2 = new Scanner(System.in);
		    	System.out.println("Digite el nombre de la pieza que desea hacer una oferta: ");
		    	String id = myObj2.nextLine();
		    	Scanner myObj3 = new Scanner(System.in);
		    	System.out.println("Digite el dinero que desea ofertar recuerde que debe ofrecer mas del precio de la pieza: ");
		    	int valor = myObj3.nextInt();
		    	subastasP.ofertarPieza(comprador, valor, id);
	    	}
	    	
	    }
	    
	    
	    public void probarEmpleado() {
	        try {
	            Collection<Empleado> empleados = unosEmpleados.getEmpleados();
	            for (Empleado empleado : empleados) {
	                System.out.println("-------------------------");
	                System.out.println(empleado.getTipoEmpleado());
	                System.out.println(empleado.getNombre());
	                System.out.println(empleado.getContraseña());
	                System.out.println("-------------------------");
	            }
	            Scanner myObj = new Scanner(System.in);
	            System.out.println("Digite su usuario: ");
	            String usuario = myObj.nextLine();
	            Scanner myObj2 = new Scanner(System.in);
	            System.out.println("Digite su contraseña: ");
	            String contraseña = myObj2.nextLine();
	            Empleado empleado = unosEmpleados.login(usuario, contraseña);
	            funcionalidadesEmpleado(empleado);
	        } catch (Exception e) {
	            System.out.println("Error al probar empleados: " + e.getMessage());
	        }
	    }
	    
	    public void funcionalidadesEmpleado(Empleado empleado) {
	    	System.out.println("Usted es un: " + empleado.getTipoEmpleado());
	    	Scanner myObj = new Scanner(System.in);
	    	System.out.println("1. Ofertas\n" + "2. Subastas\n" + "3. Añadir pieza a galeria: ");
	    	String decision = myObj.nextLine();
	    	if(decision.compareTo("1") == 0) {
	    		Collection<Oferta> ofertasFijas = ofertas.getOfertas().values();
	    		if(empleado.getTipoEmpleado().compareTo("Administrador") == 0) {
		    		for (Oferta oferta : ofertasFijas) {
		    			if((oferta.getEstado().compareTo("pendiente de revision por administrador") == 0) || (oferta.getEstado().compareTo("pendiente de confirmacion de venta") == 0)) {
		    				System.out.println("---------------------------------------------------");
		    				System.out.println("id: " + "Oferta hecha por: " + oferta.getComprador().getNombre() + " " + oferta.getPieza().getTitulo());
		    				System.out.println(oferta.getEstado());
		    				System.out.println("---------------------------------------------------");
		    			}
		    			
					}
		    		Scanner myObj2 = new Scanner(System.in);
			    	System.out.println("1. verificar oferta\n" + "2. agregar pieza" + "3. salir");
			    	String decision2 = myObj2.nextLine();
			    	if(decision2.compareTo("1") == 0) {
			    		Scanner myObj3 = new Scanner(System.in);
				    	System.out.println("digite el id de la oferta");
				    	String id = myObj3.nextLine();
				    	ofertas.revisarOferta((Administrador) empleado, (Cajero)null, id);
				    	if(ofertas.getOferta(id).getEstado() != null) {
				    		System.out.println(ofertas.getOferta(id).getEstado());
				    		}
			    	} else if(decision2.compareTo("2") == 0) {
			    		Scanner myObj20 = new Scanner(System.in);
				    	System.out.println("Nombre de la pieza: ");
				    	String nombre = myObj20.nextLine();
				    	Scanner myObj3 = new Scanner(System.in);
				    	System.out.println("Anio de la pieza: ");
				    	String anio = myObj.nextLine();
				    	Scanner myObj4 = new Scanner(System.in);
				    	System.out.println("Lugar de creacion: ");
				    	String lugar = myObj4.nextLine();
				    	Scanner myObj5 = new Scanner(System.in);
				    	System.out.println("Autor: ");
				    	String autor = myObj5.nextLine();
				    	Scanner myObj6 = new Scanner(System.in);
				    	System.out.println("Es fisica: ");
				    	boolean isFisica = myObj6.nextBoolean();
				    	if(isFisica) {
				    		Scanner myObj11 = new Scanner(System.in);
					    	System.out.println("tipo: ");
					    	String tipo = myObj11.nextLine();
					    	Scanner myObj7 = new Scanner(System.in);
					    	System.out.println("profundidad: ");
					    	double profundidad = myObj7.nextDouble();
					    	Scanner myObj8 = new Scanner(System.in);
					    	System.out.println("alto: ");
					    	double alto = myObj8.nextDouble();
					    	Scanner myObj9 = new Scanner(System.in);
					    	System.out.println("ancho: ");
					    	double ancho = myObj9.nextDouble();
					    	Scanner myObj10 = new Scanner(System.in);
					    	System.out.println("peso: ");
					    	double peso = myObj10.nextDouble();
					    	PiezaFisica pieza = new PiezaFisica(tipo, nombre, anio, lugar, autor, false, profundidad, alto, ancho, peso, false, false);
					    	galeria.addPiezas(pieza);
				    	} else {
				    		Scanner myObj7 = new Scanner(System.in);
					    	System.out.println("tipo: ");
					    	String tipo = myObj7.nextLine();
					    	Scanner myObj8 = new Scanner(System.in);
					    	System.out.println("formato: ");
					    	String formato = myObj8.nextLine();
					    	PiezaVirtual piezaVirtual = new PiezaVirtual(tipo, formato, nombre, anio, lugar, autor, false);
					    	galeria.addPiezas(piezaVirtual);
				    	}
			    	} else if(decision.compareTo("3") == 0) {}
			    		
		    	
		    	}else if(empleado.getTipoEmpleado().compareTo("Cajero") == 0) {
		    		for (Oferta oferta : ofertasFijas) {
		    			if(oferta.getEstado().compareTo("pendiente de revision por cajero") == 0){
		    				System.out.println("---------------------------------------------------");
		    				System.out.println("id: " + "Oferta hecha por: " + oferta.getComprador().getNombre() + " " + oferta.getPieza().getTitulo());
		    				System.out.println(oferta.getEstado());
		    				System.out.println("---------------------------------------------------");
		    			}
		    			
					}
		    		Scanner myObj2 = new Scanner(System.in);
			    	System.out.println("1. verificar oferta\n" + "2. salir");
			    	String decision2 = myObj2.nextLine();
			    	if(decision2.compareTo("1") == 0) {
			    		Scanner myObj3 = new Scanner(System.in);
				    	System.out.println("digite el id de la oferta");
				    	String id = myObj3.nextLine();
				    	ofertas.revisarOferta((Administrador)null, (Cajero) empleado, id);
				    	System.out.println(ofertas.getOferta(id).getEstado());
			    	} else if(decision2.compareTo("2") == 0) {}
		    	}
	    	}
	    	if(decision.compareTo("2") == 0) {
	    		Collection<PiezaEnSubasta> subastas = galeria.getPiezasEnSubasta().values();
	    		if(empleado.getTipoEmpleado().compareTo("Administrador") == 0) {
	    			for (PiezaEnSubasta piezaEnSubasta : subastas) {
	    				System.out.println(piezaEnSubasta.getOfertas());
	    				if((piezaEnSubasta.getOfertas().size() >= 2) && (piezaEnSubasta.getEstado().compareTo("disponible") == 0)) {
	    					Collection<Oferta> ofertas = piezaEnSubasta.getOfertas().values();
	    					System.out.println("subasta disponible para revisar:");
	    					System.out.println("id: " + piezaEnSubasta.getPieza().getTitulo());
	    					System.out.println("Ofertas: ");
	    					for (Iterator iterator = ofertas.iterator(); iterator.hasNext();) {
								Oferta oferta = (Oferta) iterator.next();
								System.out.println("---------------------------------------------------");
								System.out.println(oferta.getComprador().getNombre());
	    						System.out.println(oferta.getComprador().getMetodoPago());
	    						System.out.println(oferta.getValor());
	    						System.out.println("---------------------------------------------------");
								
							}
	    					
	    				}
						
					}
	    			Scanner myObj2 = new Scanner(System.in);
			    	System.out.println("1. verificar oferta\n" + "2. salir");
			    	String decision2 = myObj2.nextLine();
			    	if(decision2.compareTo("1") == 0) {
			    		Scanner myObj3 = new Scanner(System.in);
				    	System.out.println("digite el id de la oferta");
				    	String id = myObj3.nextLine();
				    	subastasP.revisarSubasta((Administrador) empleado, (Cajero) null,(Operador) null, id);
				    	System.out.println(galeria.getPiezaEnSubasta(id).getEstado());
			    	} else if(decision2.compareTo("2") == 0) {
			    	}
	    		} else if(empleado.getTipoEmpleado().compareTo("Cajero") == 0) {
	    			for (PiezaEnSubasta piezaEnSubasta : subastas) {
	    				if(piezaEnSubasta.getEstado().compareTo("finalizado pendiente por revision de pago") == 0) {
	    					Collection<Oferta> ofertas = piezaEnSubasta.getOfertas().values();
	    					System.out.println("subasta disponible para revisar:");
	    					System.out.println("ganador: " + piezaEnSubasta.getGanador());
	    					System.out.println("id: " + piezaEnSubasta.getPieza().getTitulo());
	    					System.out.println(piezaEnSubasta.getEstado());
	    					System.out.println("Ofertas: ");
	    					for (Oferta oferta : ofertas) {
	    						System.out.println("---------------------------------------------------");
	    						System.out.println(oferta.getComprador().getNombre());
	    						System.out.println(oferta.getComprador().getMetodoPago());
	    						System.out.println(oferta.getValor());
	    						System.out.println("---------------------------------------------------");
							}
	    					
	    				}
						
					}
	    			Scanner myObj2 = new Scanner(System.in);
			    	System.out.println("1. verificar oferta\n" + "2. salir");
			    	String decision2 = myObj2.nextLine();
			    	if(decision2.compareTo("1") == 0) {
			    		Scanner myObj3 = new Scanner(System.in);
				    	System.out.println("digite el id de la oferta");
				    	String id = myObj3.nextLine();
				    	subastasP.revisarSubasta((Administrador) null, (Cajero) empleado,(Operador) null, id);
				    	System.out.println(galeria.getPiezaEnSubasta(id).getEstado());
			    	} else if(decision2.compareTo("2") == 0) {
			    		correrAplicacion();
			    	}
	    		}
	    		else if(empleado.getTipoEmpleado().compareTo("Operador") == 0) {
	    			for (PiezaEnSubasta piezaEnSubasta : subastas) {
	    				if(piezaEnSubasta.getEstado().compareTo("finalizado") == 0) {
	    					Collection<Oferta> ofertas = piezaEnSubasta.getOfertas().values();
	    					System.out.println("subasta disponible para revisar:");
	    					System.out.println("ganador: " + piezaEnSubasta.getGanador());
	    					System.out.println("id: " + piezaEnSubasta.getPieza().getTitulo());
	    					System.out.println(piezaEnSubasta.getEstado());
	    					System.out.println("Ofertas: ");
	    					for (Oferta oferta : ofertas) {
	    						System.out.println("---------------------------------------------------");
	    						System.out.println(oferta.getComprador().getNombre());
	    						System.out.println(oferta.getComprador().getMetodoPago());
	    						System.out.println(oferta.getValor());
	    						System.out.println("---------------------------------------------------");
							}
	    					
	    				}
						
					}
	    			Scanner myObj2 = new Scanner(System.in);
			    	System.out.println("1. verificar oferta\n" + "2. salir");
			    	String decision2 = myObj2.nextLine();
			    	if(decision2.compareTo("1") == 0) {
			    		Scanner myObj3 = new Scanner(System.in);
				    	System.out.println("digite el id de la oferta");
				    	String id = myObj3.nextLine();
				    	subastasP.revisarSubasta((Administrador) null, (Cajero) null,(Operador) empleado, id);
			    	} else if(decision2.compareTo("2") == 0) {
			    	}
	    		}
	    	} 
	    	
	    }
	    
	    
	    public static void main( String[] args )
	    {   
	        ConsolaGaleria ca = new ConsolaGaleria( );
	        ca.cargarDatos();
	        ca.correrAplicacion();
	    }
}
