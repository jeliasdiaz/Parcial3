package parcial3.Util;

// Clase utilitaria para mostrar el estado de las colas y simular la atención de pacientes.

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
        informacionSalida.append("ESTADO ACTUAL DE LAS COLAS DE ATENCION\n");
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
        String dolenciasTexto;
        informacionSalida.append(titulo).append("\n");
        informacionSalida.append("-".repeat(30)).append("\n");
        if (pacientes.isEmpty()) {
            informacionSalida.append("(Vacía)\n");
        } else {
            for (Paciente paciente : pacientes) {
                dolenciasTexto = concatenarTexto(paciente.getDolencias());
                informacionSalida.append(String.format("ID: %s | Edad: %d | Dolor: %d | Dolencias: %s%n",
                        paciente.getId(), paciente.getEdad(), paciente.getNivelDolor(),
                        dolenciasTexto));
            }
        }
        informacionSalida.append("\n");
        return informacionSalida.toString();
    }

    // Método auxiliar para construir texto de una lista sin usar String.join
    private static String concatenarTexto(List<String> lista) {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            texto.append(lista.get(i));
            if (i < lista.size() - 1) {
                texto.append(", ");
            }
        }
        return texto.toString();
    }

    // Simula el proceso de atención de pacientes y devuelve el resultado como
    // string
    public static String simularAtencion(ClasificadorPacientes clasificador) {
        StringBuilder informacionSalida = new StringBuilder();
        informacionSalida.append(SEPARADOR);
        informacionSalida.append("SIMULACION DE PROCESO DE ATENCION\n");
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

            // Calcula turnos proporcionales según colas no vacías (sin ternarios)
            int totalTurnos = 6;
            int alta = 0, media = 0, baja = 0;
            if (!clasificador.getColaAlta().isEmpty())
                alta = 3;
            if (!clasificador.getColaMedia().isEmpty())
                media = 2;
            if (!clasificador.getColaBaja().isEmpty())
                baja = 1;
            int sumaEspacios = alta + media + baja;
            // Si alguna cola está vacía, redistribuir turnos
            if (sumaEspacios < totalTurnos && sumaEspacios > 0) {
                int turnosPorAsignar = totalTurnos - sumaEspacios;
                if (alta > 0)
                    alta += (int) Math.round(turnosPorAsignar * (3.0 / 6));
                if (media > 0)
                    media += (int) Math.round(turnosPorAsignar * (2.0 / 6));
                if (baja > 0)
                    baja += (int) Math.round(turnosPorAsignar * (1.0 / 6));
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

        informacionSalida
                .append(mostrarEstadisticas(pacientesAtendidosAlta, pacientesAtendidosMedia, pacientesAtendidosBaja));

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
                informacionSalida.append(String.format("ID: %s | Edad: %d | Espera: %.2f segundos\n",
                        pacienteMax.getId(), pacienteMax.getEdad(), maxEspera / 1000.0));
            }
        }
        // Llamar a los métodos de GraficarInfo para los gráficos y la tabla
        GraficarInfo.generarGraficoBarras(pacientesAtendidosAlta, pacientesAtendidosMedia, pacientesAtendidosBaja);
        GraficarInfo.generarGraficoPastel(pacientesAtendidosAlta, pacientesAtendidosMedia, pacientesAtendidosBaja);
        GraficarInfo.generarGraficoLineasTiempos(pacientesAtendidos, tiemposEspera);
        GraficarInfo.mostrarTablaPacientes(pacientesAtendidos, tiemposEspera);
        return informacionSalida.toString();
    }

    // Devuelve un string con la información de la atención de un paciente
    private static String mostrarAtencion(Paciente paciente, String prioridad) {
        StringBuilder informacionSalida = new StringBuilder();
        informacionSalida
                .append(String.format("Atendiendo paciente %s (Prioridad: %s)%n", paciente.getId(), prioridad));
        informacionSalida.append(String.format("  Dolencias: %s%n", String.join(", ", paciente.getDolencias())));
        informacionSalida.append(String.format("  Antecedentes: %s%n", String.join(", ", paciente.getAntecedentes())));
        informacionSalida
                .append(String.format("  Factores sociales: %s%n", String.join(", ", paciente.getFactoresSociales())));
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