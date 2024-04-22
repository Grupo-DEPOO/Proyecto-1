package persistencia;

import java.io.IOException;

import galeria.Galeria;
import staff.ControladorEmpleados;

public interface IPersistencia {

	public  void cargarEmpleados( String archivo, ControladorEmpleados empleados ) throws IOException;
	public void cargarPiezas(String archivo, Galeria galeria) throws IOException;
	public void cargarCompradores(String archivo, Galeria galeria) throws IOException;
	public void cargarPropietarios(String archivo, Galeria galeria) throws IOException;

}
