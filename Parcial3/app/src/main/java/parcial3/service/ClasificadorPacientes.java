
// Implementación del clasificador de pacientes. Gestiona las colas de prioridad y la lógica de clasificación.
package parcial3.Service;

import java.util.PriorityQueue;

import parcial3.Iface.IClasificadorPacientes;
import parcial3.Model.Paciente;
import parcial3.Util.PuntosPaciente;

public class ClasificadorPacientes implements IClasificadorPacientes {
    // Cola de pacientes de alta prioridad
    private PriorityQueue<Paciente> colaAlta;
    // Cola de pacientes de media prioridad
    private PriorityQueue<Paciente> colaMedia;
    // Cola de pacientes de baja prioridad
    private PriorityQueue<Paciente> colaBaja;

    // Constructor. Inicializa las colas de prioridad
    public ClasificadorPacientes() {
        colaAlta = new PriorityQueue<>();
        colaMedia = new PriorityQueue<>();
        colaBaja = new PriorityQueue<>();
    }

    // Clasifica un paciente en la cola correspondiente según sus puntos
    public void clasificarPaciente(Paciente paciente) {
        int puntos = PuntosPaciente.calcularPuntosTotales(paciente);
        paciente.setPuntosPrioridad(puntos);
        if (puntos >= 300) {
            colaAlta.offer(paciente);
        } else if (puntos >= 150) {
            colaMedia.offer(paciente);
        } else {
            colaBaja.offer(paciente);
        }
    }

    // Atiende al siguiente paciente según la prioridad (alta, luego media, luego
    // baja)
    public Paciente atenderSiguientePaciente() {
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
    // Devuelve la cola de alta prioridad
    public PriorityQueue<Paciente> getColaAlta() {
        return colaAlta;
    }

    // Devuelve la cola de media prioridad
    public PriorityQueue<Paciente> getColaMedia() {
        return colaMedia;
    }

    // Devuelve la cola de baja prioridad
    public PriorityQueue<Paciente> getColaBaja() {
        return colaBaja;
    }

    // Métodos para verificar si hay pacientes en espera
    // Indica si hay pacientes en espera en alguna cola
    public boolean hayPacientesEnEspera() {
        return !colaAlta.isEmpty() || !colaMedia.isEmpty() || !colaBaja.isEmpty();
    }

    // Devuelve el total de pacientes en espera
    public int getTotalPacientesEnEspera() {
        return colaAlta.size() + colaMedia.size() + colaBaja.size();
    }
}