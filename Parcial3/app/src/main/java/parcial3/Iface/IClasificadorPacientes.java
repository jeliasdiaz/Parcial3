package parcial3.Iface;

import parcial3.Model.Paciente;
import java.util.PriorityQueue;

public interface IClasificadorPacientes {
    // Clasifica un paciente en la cola correspondiente.
    void clasificarPaciente(Paciente paciente);

    // Atiende al siguiente paciente según la prioridad.
    Paciente atenderSiguientePaciente();

    // Devuelve la cola de alta prioridad.
    PriorityQueue<Paciente> getColaAlta();

    PriorityQueue<Paciente> getColaMedia();

    PriorityQueue<Paciente> getColaBaja();

    // Indica si hay pacientes en espera.
    boolean hayPacientesEnEspera();

    // Devuelve el total de pacientes en espera.
    int getTotalPacientesEnEspera();
}
