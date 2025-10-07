package parcial3.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import parcial3.Model.Paciente;
import parcial3.Model.SignosVitales;

class PacienteJSON {
    String id;
    int edad;
    List<String> antecedentes;
    List<String> dolencias;
    SignosVitalesJSON signosVitales;
    int nivelDolor;
    List<String> factoresSociales;
}

class SignosVitalesJSON {
    int frecuenciaCardiaca;
    String presionArterial;
    double saturacionOxigeno;
    double temperatura;
}

public class CargadorPacientes {
    private final ClasificadorPacientes clasificador;

    public CargadorPacientes(ClasificadorPacientes clasificador) {
        this.clasificador = clasificador;
    }

    public void cargarPacientesDesdeJSON(String rutaArchivo) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(rutaArchivo))) {
            Gson gson = new Gson();
            List<PacienteJSON> pacientesJSON = gson.fromJson(reader,
                    new TypeToken<List<PacienteJSON>>(){}.getType());

            for (PacienteJSON pJSON : pacientesJSON) {
                SignosVitales signosVitales = new SignosVitales(
                    pJSON.signosVitales.frecuenciaCardiaca,
                    pJSON.signosVitales.presionArterial,
                    pJSON.signosVitales.saturacionOxigeno,
                    pJSON.signosVitales.temperatura
                );

                Paciente paciente = new Paciente(
                    pJSON.id,
                    pJSON.edad,
                    pJSON.antecedentes,
                    pJSON.dolencias,
                    signosVitales,
                    pJSON.nivelDolor,
                    pJSON.factoresSociales
                );

                clasificador.clasificarPaciente(paciente);
            }
        }
    }
}