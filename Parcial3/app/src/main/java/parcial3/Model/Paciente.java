
// Representa a un paciente con todos sus datos relevantes para el triaje.
package parcial3.Model;

import java.util.List;

public class Paciente implements Comparable<Paciente> {
    // Identificador único del paciente
    private String id;
    // Edad del paciente
    private int edad;
    // Lista de antecedentes médicos
    private List<String> antecedentes;
    // Lista de dolencias actuales
    private List<String> dolencias;
    // Signos vitales del paciente
    private SignosVitales signosVitales;
    // Nivel de dolor reportado
    private int nivelDolor;
    // Factores sociales relevantes
    private List<String> factoresSociales;
    // Puntos de prioridad calculados
    private int puntosPrioridad;
    // Momento en que el paciente ingresó al sistema
    private long tiempoIngreso;

    // Constructor que inicializa todos los datos del paciente
    public Paciente(String id, int edad, List<String> antecedentes, List<String> dolencias, SignosVitales signosVitales,
            int nivelDolor, List<String> factoresSociales) {
        this.id = id;
        this.edad = edad;
        this.antecedentes = antecedentes;
        this.dolencias = dolencias;
        this.signosVitales = signosVitales;
        this.nivelDolor = nivelDolor;
        this.factoresSociales = factoresSociales;
        this.tiempoIngreso = System.currentTimeMillis();
    }

    // Devuelve el tiempo de ingreso del paciente
    public long getTiempoIngreso() {
        return tiempoIngreso;
    }

    // Permite modificar el tiempo de ingreso (por si se requiere)
    public void setTiempoIngreso(long tiempoIngreso) {
        this.tiempoIngreso = tiempoIngreso;
    }

    // Getters and setters
    // Devuelve el ID del paciente
    public String getId() {
        return id;
    }

    // Devuelve la edad del paciente
    public int getEdad() {
        return edad;
    }

    // Devuelve la lista de antecedentes médicos
    public List<String> getAntecedentes() {
        return antecedentes;
    }

    // Devuelve la lista de dolencias actuales
    public List<String> getDolencias() {
        return dolencias;
    }

    // Devuelve los signos vitales
    public SignosVitales getSignosVitales() {
        return signosVitales;
    }

    // Devuelve el nivel de dolor
    public int getNivelDolor() {
        return nivelDolor;
    }

    // Devuelve los factores sociales
    public List<String> getFactoresSociales() {
        return factoresSociales;
    }

    // Devuelve los puntos de prioridad
    public int getPuntosPrioridad() {
        return puntosPrioridad;
    }

    // Permite modificar los puntos de prioridad
    public void setPuntosPrioridad(int puntosPrioridad) {
        this.puntosPrioridad = puntosPrioridad;
    }

    // Permite comparar pacientes por prioridad y nivel de dolor (para ordenar en
    // las colas)
    @Override
    public int compareTo(Paciente otro) {
        int comparacionPrioridad = Integer.compare(otro.getPuntosPrioridad(), this.getPuntosPrioridad());
        if (comparacionPrioridad != 0) {
            return comparacionPrioridad;
        }
        return Integer.compare(otro.getNivelDolor(), this.getNivelDolor());
    }
}
