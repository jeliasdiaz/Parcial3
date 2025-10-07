package parcial3.service;

import java.util.PriorityQueue;
import parcial3.Model.Paciente;
import parcial3.util.PuntosPaciente;

public class ClasificadorPacientes {
    private PriorityQueue<Paciente> colaAlta;
    private PriorityQueue<Paciente> colaMedia;
    private PriorityQueue<Paciente> colaBaja;

    public ClasificadorPacientes() {
        // Inicializar las colas con el comparador natural de Paciente
        colaAlta = new PriorityQueue<>();
        colaMedia = new PriorityQueue<>();
        colaBaja = new PriorityQueue<>();
    }

    public void clasificarPaciente(Paciente paciente) {
        // Calcular puntos totales del paciente
        int puntos = PuntosPaciente.calcularPuntosTotales(paciente);
        paciente.setPuntosPrioridad(puntos);

        // Clasificar en la cola correspondiente según los puntos
        if (puntos >= 300) { // Si tiene 3 o más condiciones de alta prioridad
            colaAlta.offer(paciente);
        } else if (puntos >= 150) { // Si tiene 3 o más condiciones de media prioridad
            colaMedia.offer(paciente);
        } else {
            colaBaja.offer(paciente);
        }
    }

    public Paciente atenderSiguientePaciente() {
        // Implementar la lógica del ciclo 3-2-1
        // Esta implementación básica solo retorna el siguiente paciente de mayor prioridad
        if (!colaAlta.isEmpty()) {
            return colaAlta.poll();
        } else if (!colaMedia.isEmpty()) {
            return colaMedia.poll();
        } else if (!colaBaja.isEmpty()) {
            return colaBaja.poll();
        }
        return null;
    }

    // Getters para las colas
    public PriorityQueue<Paciente> getColaAlta() {
        return colaAlta;
    }

    public PriorityQueue<Paciente> getColaMedia() {
        return colaMedia;
    }

    public PriorityQueue<Paciente> getColaBaja() {
        return colaBaja;
    }

    // Métodos para verificar si hay pacientes en espera
    public boolean hayPacientesEnEspera() {
        return !colaAlta.isEmpty() || !colaMedia.isEmpty() || !colaBaja.isEmpty();
    }

    public int getTotalPacientesEnEspera() {
        return colaAlta.size() + colaMedia.size() + colaBaja.size();
    }
}