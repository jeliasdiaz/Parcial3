
// Clase utilitaria para mostrar el estado de las colas y simular la atención de pacientes.
package parcial3.Util;

import parcial3.Model.Paciente;
import parcial3.Service.ClasificadorPacientes;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class MostrarInfo {
    // Separador visual para la salida de texto
    private static final String SEPARADOR = "\n" + "=".repeat(50) + "\n";

    // Devuelve un string con el estado actual de las colas de atención
    public static String mostrarEstadoColas(ClasificadorPacientes clasificador) {
        StringBuilder informacionSalida = new StringBuilder();
        informacionSalida.append(SEPARADOR);
        informacionSalida.append("ESTADO ACTUAL DE LAS COLAS DE ATENCIÓN\n");
        informacionSalida.append(SEPARADOR);

        informacionSalida.append(mostrarCola("COLA ALTA PRIORIDAD", clasificador.getColaAlta()));
        informacionSalida.append(mostrarCola("COLA MEDIA PRIORIDAD", clasificador.getColaMedia()));
        informacionSalida.append(mostrarCola("COLA BAJA PRIORIDAD", clasificador.getColaBaja()));
        return informacionSalida.toString();
    }

    // Devuelve un string con la información de una cola específica
    private static String mostrarCola(String titulo, PriorityQueue<Paciente> cola) {
        StringBuilder informacionSalida = new StringBuilder();
        List<Paciente> pacientes = new ArrayList<>(cola);
        informacionSalida.append(titulo).append("\n");
        informacionSalida.append("-".repeat(30)).append("\n");
        if (pacientes.isEmpty()) {
            informacionSalida.append("(Vacía)\n");
        } else {
            for (Paciente paciente : pacientes) {
                informacionSalida.append(String.format("ID: %s | Edad: %d | Dolor: %d | Dolencias: %s%n",
                    paciente.getId(), paciente.getEdad(), paciente.getNivelDolor(),
                    String.join(", ", paciente.getDolencias())));
            }
        }
        informacionSalida.append("\n");
        return informacionSalida.toString();
    }

    // Simula el proceso de atención de pacientes y devuelve el resultado como string
    public static String simularAtencion(ClasificadorPacientes clasificador) {
        StringBuilder informacionSalida = new StringBuilder();
        informacionSalida.append(SEPARADOR);
        informacionSalida.append("SIMULACIÓN DE PROCESO DE ATENCIÓN\n");
        informacionSalida.append(SEPARADOR);

        int ciclo = 1;
        int pacientesAtendidosAlta = 0;
        int pacientesAtendidosMedia = 0;
        int pacientesAtendidosBaja = 0;

        // Para registro de espera
        List<Paciente> pacientesAtendidos = new ArrayList<>();
        List<Long> tiemposEspera = new ArrayList<>();

        while (clasificador.hayPacientesEnEspera()) {
            informacionSalida.append(String.format("Ciclo de atención #%d%n", ciclo++));
            informacionSalida.append("-".repeat(30)).append("\n");

            // Calcula turnos proporcionales según colas no vacías
            int totalTurnos = 6;
            int alta = !clasificador.getColaAlta().isEmpty() ? 3 : 0;
            int media = !clasificador.getColaMedia().isEmpty() ? 2 : 0;
            int baja = !clasificador.getColaBaja().isEmpty() ? 1 : 0;
            int suma = alta + media + baja;
            // Si alguna cola está vacía, redistribuir turnos
            if (suma < totalTurnos && suma > 0) {
                int faltan = totalTurnos - suma;
                if (alta > 0) alta += (int)Math.round(faltan * (3.0/6));
                if (media > 0) media += (int)Math.round(faltan * (2.0/6));
                if (baja > 0) baja += (int)Math.round(faltan * (1.0/6));
            }

            // Atiende pacientes de alta prioridad
            Paciente p = null;
            for (int i = 0; i < alta && !clasificador.getColaAlta().isEmpty(); i++) {
                p = clasificador.getColaAlta().poll();
                informacionSalida.append(mostrarAtencion(p, "ALTA"));
                pacientesAtendidosAlta++;
                pacientesAtendidos.add(p);
                tiemposEspera.add(System.currentTimeMillis() - p.getTiempoIngreso());
            }
            // Atiende pacientes de media prioridad
            for (int i = 0; i < media && !clasificador.getColaMedia().isEmpty(); i++) {
                p = clasificador.getColaMedia().poll();
                informacionSalida.append(mostrarAtencion(p, "MEDIA"));
                pacientesAtendidosMedia++;
                pacientesAtendidos.add(p);
                tiemposEspera.add(System.currentTimeMillis() - p.getTiempoIngreso());
            }
            // Atiende pacientes de baja prioridad
            for (int i = 0; i < baja && !clasificador.getColaBaja().isEmpty(); i++) {
                p = clasificador.getColaBaja().poll();
                informacionSalida.append(mostrarAtencion(p, "BAJA"));
                pacientesAtendidosBaja++;
                pacientesAtendidos.add(p);
                tiemposEspera.add(System.currentTimeMillis() - p.getTiempoIngreso());
            }

            informacionSalida.append("\n");
        }

        informacionSalida.append(mostrarEstadisticas(pacientesAtendidosAlta, pacientesAtendidosMedia, pacientesAtendidosBaja));

    // Muestra el paciente con mayor tiempo de espera
        if (!pacientesAtendidos.isEmpty()) {
            long maxEspera = -1;
            Paciente pacienteMax = null;
            for (int i = 0; i < pacientesAtendidos.size(); i++) {
                if (tiemposEspera.get(i) > maxEspera) {
                    maxEspera = tiemposEspera.get(i);
                    pacienteMax = pacientesAtendidos.get(i);
                }
            }
            if (pacienteMax != null) {
                informacionSalida.append("Paciente con mayor tiempo de espera antes de ser atendido:\n");
                informacionSalida.append(String.format("ID: %s | Edad: %d | Espera: %.2f segundos\n", pacienteMax.getId(), pacienteMax.getEdad(), maxEspera/1000.0));
            }
        }
        return informacionSalida.toString();
    }

    // Devuelve un string con la información de la atención de un paciente
    private static String mostrarAtencion(Paciente paciente, String prioridad) {
        StringBuilder informacionSalida = new StringBuilder();
        informacionSalida.append(String.format("Atendiendo paciente %s (Prioridad: %s)%n", paciente.getId(), prioridad));
        informacionSalida.append(String.format("  Dolencias: %s%n", String.join(", ", paciente.getDolencias())));
        informacionSalida.append(String.format("  Antecedentes: %s%n", String.join(", ", paciente.getAntecedentes())));
        informacionSalida.append(String.format("  Factores sociales: %s%n", String.join(", ", paciente.getFactoresSociales())));
        informacionSalida.append("\n");
        return informacionSalida.toString();
    }

    // Devuelve un string con las estadísticas finales de la simulación
    private static String mostrarEstadisticas(int alta, int media, int baja) {
        StringBuilder informacionSalida = new StringBuilder();
        int total = alta + media + baja;
        informacionSalida.append(SEPARADOR);
        informacionSalida.append("ESTADÍSTICAS FINALES\n");
        informacionSalida.append(SEPARADOR);
        informacionSalida.append(String.format("Total de pacientes atendidos: %d%n", total));
        informacionSalida.append(String.format("Pacientes de alta prioridad: %d (%.1f%%)%n", 
            alta, (alta * 100.0) / total));
        informacionSalida.append(String.format("Pacientes de media prioridad: %d (%.1f%%)%n", 
            media, (media * 100.0) / total));
        informacionSalida.append(String.format("Pacientes de baja prioridad: %d (%.1f%%)%n", 
            baja, (baja * 100.0) / total));
        informacionSalida.append(SEPARADOR);
        return informacionSalida.toString();
    }
}