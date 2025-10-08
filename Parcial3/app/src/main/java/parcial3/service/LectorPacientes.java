
// Clase encargada de cargar pacientes desde un archivo JSON y clasificarlos.
package parcial3.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parcial3.Model.Paciente;
import parcial3.Model.SignosVitales;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Clase auxiliar para mapear los datos de un paciente desde JSON
class PacienteJSON {
    String id;
    int edad;
    List<String> antecedentes;
    List<String> dolencias;
    SignosVitalesJSON signosVitales;
    int nivelDolor;
    List<String> factoresSociales;
}

// Clase auxiliar para mapear los signos vitales desde JSON
class SignosVitalesJSON {
    int frecuenciaCardiaca;
    String presionArterial;
    double saturacionOxigeno;
    double temperatura;
}

// Carga pacientes desde archivo y los pasa al clasificador
public class LectorPacientes {
    // Logger para registrar información de carga
    private static final Logger logger = LogManager.getLogger(LectorPacientes.class);
    // Ruta del archivo JSON de pacientes
    private static final String RUTA_JSON = "C:\\Users\\david\\Documents\\GitHub\\Parcial3\\Parcial3\\app\\src\\main\\resources\\pacientes.json";
    // Lista de pacientes leídos del archivo
    private static List<Paciente> pacientesLeidos = new ArrayList<>();
    // Indica si el archivo ya fue leído
    private static boolean archivoLeido = false;
    // Referencia al clasificador de pacientes
    private final ClasificadorPacientes clasificador;
    // Gson para deserializar JSON
    private final Gson gson;

    // Constructor que recibe el clasificador
    public LectorPacientes(ClasificadorPacientes clasificador) {
        this.clasificador = clasificador;
        this.gson = new Gson();
    }

    // Lee el archivo JSON y clasifica los pacientes
    public void cargarPacientesDesdeJSON() {
        if (archivoLeido)
            return;
        try (FileReader fileReader = new FileReader(RUTA_JSON)) {
            logger.info("Leyendo el archivo de pacientes: {}", RUTA_JSON);
            Type listType = new TypeToken<List<PacienteJSON>>() {
            }.getType();
            List<PacienteJSON> pacientesJSON = gson.fromJson(fileReader, listType);
            Paciente paciente;
            for (PacienteJSON pacienteJSON : pacientesJSON) {
                SignosVitales signosVitales = new SignosVitales(
                        pacienteJSON.signosVitales.frecuenciaCardiaca,
                        pacienteJSON.signosVitales.presionArterial,
                        pacienteJSON.signosVitales.saturacionOxigeno,
                        pacienteJSON.signosVitales.temperatura);
                        paciente = new Paciente(
                        pacienteJSON.id,
                        pacienteJSON.edad,
                        pacienteJSON.antecedentes,
                        pacienteJSON.dolencias,
                        signosVitales,
                        pacienteJSON.nivelDolor,
                        pacienteJSON.factoresSociales);
                pacientesLeidos.add(paciente);
                clasificador.clasificarPaciente(paciente);
            }
            archivoLeido = true;
            logger.info("Archivo de pacientes leído exitosamente.");
        } catch (IOException e) {
            logger.error("Error al leer el archivo: {}", e.getMessage());
            throw new RuntimeException("Error al leer el archivo de pacientes: " + e.getMessage());
        }
    }

    // Devuelve la lista de pacientes leídos
    public List<Paciente> getPacientesLeidos() {
        return pacientesLeidos;
    }
}