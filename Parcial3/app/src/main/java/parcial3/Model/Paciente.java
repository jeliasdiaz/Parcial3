package parcial3.Model;

import java.util.List;

public class Paciente implements Comparable<Paciente> {
    private String id;
    private int edad;
    private List<String> antecedentes;
    private List<String> dolencias;
    private SignosVitales signosVitales;
    private int nivelDolor;
    private List<String> factoresSociales;
    private int puntosPrioridad;

    public Paciente(String id, int edad, List<String> antecedentes, List<String> dolencias, SignosVitales signosVitales,
            int nivelDolor, List<String> factoresSociales) {
        this.id = id;
        this.edad = edad;
        this.antecedentes = antecedentes;
        this.dolencias = dolencias;
        this.signosVitales = signosVitales;
        this.nivelDolor = nivelDolor;
        this.factoresSociales = factoresSociales;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public int getEdad() {
        return edad;
    }

    public List<String> getAntecedentes() {
        return antecedentes;
    }

    public List<String> getDolencias() {
        return dolencias;
    }

    public SignosVitales getSignosVitales() {
        return signosVitales;
    }

    public int getNivelDolor() {
        return nivelDolor;
    }

    public List<String> getFactoresSociales() {
        return factoresSociales;
    }

    public int getPuntosPrioridad() {
        return puntosPrioridad;
    }

    public void setPuntosPrioridad(int puntosPrioridad) {
        this.puntosPrioridad = puntosPrioridad;
    }

    @Override
    public int compareTo(Paciente otro) {
        // Primero comparar por puntos de prioridad
        int comparacionPrioridad = Integer.compare(otro.getPuntosPrioridad(), this.getPuntosPrioridad());
        if (comparacionPrioridad != 0) {
            return comparacionPrioridad;
        }
        
        // Si tienen la misma prioridad, comparar por nivel de dolor
        return Integer.compare(otro.getNivelDolor(), this.getNivelDolor());
    }
}
