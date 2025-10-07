package parcial3.Model;

public class SignosVitales {
    private int frecuenciaCardiaca;
    private String presionArterial;
    private double saturacionOxigeno;
    private double temperatura;

    public SignosVitales(int frecuenciaCardiaca, String presionArterial, double saturacionOxigeno, double temperatura) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.presionArterial = presionArterial;
        this.saturacionOxigeno = saturacionOxigeno;
        this.temperatura = temperatura;
    }

    // Getters
    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public double getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public double getTemperatura() {
        return temperatura;
    }
}