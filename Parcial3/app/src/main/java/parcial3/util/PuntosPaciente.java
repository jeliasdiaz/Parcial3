
// Clase utilitaria para calcular los puntos de prioridad de un paciente según diferentes criterios.
package parcial3.Util;

import parcial3.Model.Paciente;

public class PuntosPaciente {
    // Puntos asignados para cada nivel de prioridad
    private static final int PUNTOS_ALTA = 100;
    private static final int PUNTOS_MEDIA = 50;
    private static final int PUNTOS_BAJA = 20;

    // Calcula la suma total de puntos de prioridad para un paciente
    public static int calcularPuntosTotales(Paciente paciente) {
        int puntos = 0;
        puntos += calcularPuntosEdad(paciente);
        puntos += calcularPuntosAntecedentes(paciente);
        puntos += calcularPuntosDolencia(paciente);
        puntos += calcularPuntosSignosVitales(paciente);
        puntos += calcularPuntosNivelDolor(paciente);
        puntos += calcularPuntosFactorSocial(paciente);
        return puntos;
    }

    // Calcula los puntos según la edad del paciente
    private static int calcularPuntosEdad(Paciente paciente) {
        int edad = paciente.getEdad();
        if (edad < 1 || edad > 70) {
            return PUNTOS_ALTA;
        } else if (edad >= 40 && edad <= 70) {
            return PUNTOS_MEDIA;
        }
        return PUNTOS_BAJA;
    }

    // Calcula los puntos según los antecedentes médicos del paciente
    private static int calcularPuntosAntecedentes(Paciente paciente) {
        int maxPuntos = PUNTOS_BAJA;
        String antecedenteMin = null;
        for (String antecedente : paciente.getAntecedentes()) {
            antecedenteMin = antecedente.toLowerCase();
            if (antecedenteMin.contains("cáncer") || antecedenteMin.contains("cancer") ||
                    antecedenteMin.contains("insuficiencia") || antecedenteMin.contains("epoc")) {
                return PUNTOS_ALTA; // Si encuentra uno grave, retorna inmediatamente alta prioridad
            } else if (antecedenteMin.contains("hipertensión") || antecedenteMin.contains("hipertension") ||
                    antecedenteMin.contains("diabetes")) {
                maxPuntos = Math.max(maxPuntos, PUNTOS_MEDIA);
            }
        }
        return maxPuntos;
    }

    // Calcula los puntos según el tipo de dolencia actual
    private static int calcularPuntosDolencia(Paciente paciente) {
        int maxPuntos = PUNTOS_BAJA;
        String dolenciaMin = null;
        for (String dolencia : paciente.getDolencias()) {
            dolenciaMin = dolencia.toLowerCase();
            if (dolenciaMin.contains("dolor torácico") || dolenciaMin.contains("dolor toracico") ||
                    dolenciaMin.contains("dificultad respiratoria") || dolenciaMin.contains("trauma") ||
                    dolenciaMin.contains("hemorragia")) {
                return PUNTOS_ALTA; // Si encuentra una dolencia crítica, retorna inmediatamente alta prioridad
            } else if (dolenciaMin.contains("fractura") || dolenciaMin.contains("fiebre alta") ||
                    dolenciaMin.contains("dolor abdominal") || dolenciaMin.contains("vómito") ||
                    dolenciaMin.contains("vomito")) {
                maxPuntos = Math.max(maxPuntos, PUNTOS_MEDIA);
            }
        }
        return maxPuntos;
    }

    // Calcula los puntos según los signos vitales
    private static int calcularPuntosSignosVitales(Paciente paciente) {
        if (paciente.getSignosVitales().getSaturacionOxigeno() < 90 ||
                paciente.getSignosVitales().getTemperatura() > 40) {
            return PUNTOS_ALTA;
        } else if (paciente.getSignosVitales().getSaturacionOxigeno() >= 90 &&
                paciente.getSignosVitales().getSaturacionOxigeno() <= 94 ||
                paciente.getSignosVitales().getTemperatura() >= 38 &&
                        paciente.getSignosVitales().getTemperatura() <= 39.9) {
            return PUNTOS_MEDIA;
        }
        return PUNTOS_BAJA;
    }

    // Calcula los puntos según el nivel de dolor
    private static int calcularPuntosNivelDolor(Paciente paciente) {
        int dolor = paciente.getNivelDolor();
        if (dolor >= 8 && dolor <= 10) {
            return PUNTOS_ALTA;
        } else if (dolor >= 5 && dolor <= 7) {
            return PUNTOS_MEDIA;
        }
        return PUNTOS_BAJA;
    }

    // Calcula los puntos según los factores sociales
    private static int calcularPuntosFactorSocial(Paciente paciente) {
        int maxPuntos = PUNTOS_BAJA;
        String factorMin = null;
        for (String factor : paciente.getFactoresSociales()) {
            factorMin = factor.toLowerCase();
            if (factorMin.contains("embarazada") ||
                    (factorMin.contains("menor") && factorMin.contains("sin acompañante")) ||
                    (factorMin.contains("vive solo") && paciente.getEdad() > 70)) {
                return PUNTOS_ALTA; // Si encuentra un factor crítico, retorna inmediatamente alta prioridad
            } else if (factorMin.contains("apoyo familiar")) {
                maxPuntos = Math.max(maxPuntos, PUNTOS_MEDIA);
            }
        }
        return maxPuntos;
    }
}