package parcial3.util;

import parcial3.Model.Paciente;
import parcial3.service.ClasificadorPacientes;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class Presentador {
    private static final String SEPARADOR = "\n" + "=".repeat(50) + "\n";

    public static void mostrarEstadoColas(ClasificadorPacientes clasificador) {
        System.out.println(SEPARADOR);
        System.out.println("ESTADO ACTUAL DE LAS COLAS DE ATENCIÓN");
        System.out.println(SEPARADOR);

        mostrarCola("COLA ALTA PRIORIDAD", clasificador.getColaAlta());
        mostrarCola("COLA MEDIA PRIORIDAD", clasificador.getColaMedia());
        mostrarCola("COLA BAJA PRIORIDAD", clasificador.getColaBaja());
    }

    private static void mostrarCola(String titulo, PriorityQueue<Paciente> cola) {
        List<Paciente> pacientes = new ArrayList<>(cola);
        System.out.println(titulo);
        System.out.println("-".repeat(30));
        
        if (pacientes.isEmpty()) {
            System.out.println("(Vacía)");
        } else {
            for (Paciente p : pacientes) {
                System.out.printf("ID: %s | Edad: %d | Dolor: %d | Dolencias: %s%n",
                    p.getId(), p.getEdad(), p.getNivelDolor(), 
                    String.join(", ", p.getDolencias()));
            }
        }
        System.out.println();
    }

    public static void simularAtencion(ClasificadorPacientes clasificador) {
        System.out.println(SEPARADOR);
        System.out.println("SIMULACIÓN DE PROCESO DE ATENCIÓN");
        System.out.println(SEPARADOR);

        int ciclo = 1;
        int pacientesAtendidosAlta = 0;
        int pacientesAtendidosMedia = 0;
        int pacientesAtendidosBaja = 0;

        while (clasificador.hayPacientesEnEspera()) {
            System.out.printf("Ciclo de atención #%d%n", ciclo++);
            System.out.println("-".repeat(30));

            // Atender 3 de alta prioridad
            for (int i = 0; i < 3 && !clasificador.getColaAlta().isEmpty(); i++) {
                Paciente p = clasificador.getColaAlta().poll();
                mostrarAtencion(p, "ALTA");
                pacientesAtendidosAlta++;
            }

            // Atender 2 de media prioridad
            for (int i = 0; i < 2 && !clasificador.getColaMedia().isEmpty(); i++) {
                Paciente p = clasificador.getColaMedia().poll();
                mostrarAtencion(p, "MEDIA");
                pacientesAtendidosMedia++;
            }

            // Atender 1 de baja prioridad
            if (!clasificador.getColaBaja().isEmpty()) {
                Paciente p = clasificador.getColaBaja().poll();
                mostrarAtencion(p, "BAJA");
                pacientesAtendidosBaja++;
            }

            System.out.println();
        }

        mostrarEstadisticas(pacientesAtendidosAlta, pacientesAtendidosMedia, pacientesAtendidosBaja);
    }

    private static void mostrarAtencion(Paciente paciente, String prioridad) {
        System.out.printf("Atendiendo paciente %s (Prioridad: %s)%n", paciente.getId(), prioridad);
        System.out.printf("  Dolencias: %s%n", String.join(", ", paciente.getDolencias()));
        System.out.printf("  Antecedentes: %s%n", String.join(", ", paciente.getAntecedentes()));
        System.out.printf("  Factores sociales: %s%n", String.join(", ", paciente.getFactoresSociales()));
        System.out.println();
    }

    private static void mostrarEstadisticas(int alta, int media, int baja) {
        int total = alta + media + baja;
        System.out.println(SEPARADOR);
        System.out.println("ESTADÍSTICAS FINALES");
        System.out.printf("Total de pacientes atendidos: %d%n", total);
        System.out.printf("Pacientes de alta prioridad: %d (%.1f%%)%n", 
            alta, (alta * 100.0) / total);
        System.out.printf("Pacientes de media prioridad: %d (%.1f%%)%n", 
            media, (media * 100.0) / total);
        System.out.printf("Pacientes de baja prioridad: %d (%.1f%%)%n", 
            baja, (baja * 100.0) / total);
        System.out.println(SEPARADOR);
    }
}