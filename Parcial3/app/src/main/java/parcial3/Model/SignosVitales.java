
// Representa los signos vitales de un paciente.
package parcial3.Model;

public class SignosVitales {
    // Frecuencia cardíaca del paciente
    private int frecuenciaCardiaca;
    // Presión arterial del paciente
    private String presionArterial;
    // Saturación de oxígeno
    private double saturacionOxigeno;
    // Temperatura corporal
    private double temperatura;

    // Constructor que inicializa todos los signos vitales
    public SignosVitales(int frecuenciaCardiaca, String presionArterial, double saturacionOxigeno, double temperatura) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.presionArterial = presionArterial;
        this.saturacionOxigeno = saturacionOxigeno;
        this.temperatura = temperatura;
    }

    // Devuelve la frecuencia cardíaca
    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    // Devuelve la presión arterial
    public String getPresionArterial() {
        return presionArterial;
    }

    // Devuelve la saturación de oxígeno
    public double getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    // Devuelve la temperatura corporal
    public double getTemperatura() {
        return temperatura;
    }

}