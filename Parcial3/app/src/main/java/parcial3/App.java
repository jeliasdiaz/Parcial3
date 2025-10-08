
// Clase principal del sistema. Aquí inicia la ejecución del programa.
package parcial3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parcial3.Service.ClasificadorPacientes;
import parcial3.Service.LectorPacientes;
import parcial3.Util.MostrarInfo;


public class App {
    // Logger para registrar información del sistema
    private final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        // Crea el clasificador de pacientes
        ClasificadorPacientes clasificador = new ClasificadorPacientes();
        // Crea el cargador de pacientes y lo asocia al clasificador
        LectorPacientes cargador = new LectorPacientes(clasificador);

        // Carga los pacientes desde un archivo JSON
        logger.info("Cargando pacientes desde archivo JSON...");
        cargador.cargarPacientesDesdeJSON();

        // Obtiene el estado inicial de las colas como texto
        String estadoColas = MostrarInfo.mostrarEstadoColas(clasificador);
        // Simula el proceso de atención y obtiene el resultado como texto
        logger.info("Iniciando simulación de atención...");
        String resultadoAtencion = MostrarInfo.simularAtencion(clasificador);
        // Imprime el estado de las colas y el resultado de la simulación
        System.out.print(estadoColas);
        System.out.print(resultadoAtencion);
    }
}
