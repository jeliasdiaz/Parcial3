package parcial3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parcial3.service.CargadorPacientes;
import parcial3.service.ClasificadorPacientes;
import parcial3.util.Presentador;
import java.io.IOException;

public class App {
    private final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        try {
            // Inicializar el sistema
            ClasificadorPacientes clasificador = new ClasificadorPacientes();
            CargadorPacientes cargador = new CargadorPacientes(clasificador);

            // Cargar pacientes desde JSON
            logger.info("Cargando pacientes desde archivo JSON...");
            cargador.cargarPacientesDesdeJSON("C:\\Users\\diazm\\OneDrive\\Escritorio\\Parcial3\\Parcial3\\app\\src\\main\\resources\\pacientes.json");

            // Mostrar estado inicial de las colas
            Presentador.mostrarEstadoColas(clasificador);

            // Simular proceso de atención
            logger.info("Iniciando simulación de atención...");
            Presentador.simularAtencion(clasificador);

        } catch (IOException e) {
            logger.error("Error al cargar el archivo JSON: " + e.getMessage());
        }
    }
}
