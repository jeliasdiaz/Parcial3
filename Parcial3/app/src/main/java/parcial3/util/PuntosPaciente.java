
// Clase utilitaria para calcular los puntos de prioridad de un paciente según diferentes criterios.
package parcial3.Util;

import parcial3.Model.Paciente;

import java.util.List;

public class PuntosPaciente {
    // Puntos asignados para cada nivel de prioridad
    private static final int PUNTOS_ALTA = 100;
    private static final int PUNTOS_MEDIA = 50;
    private static final int PUNTOS_BAJA = 20;

    // Listas configurables para antecedentes, dolencias y factores sociales
    private static final List<String> ANTECEDENTES_CRITICOS = List.of("cáncer", "cancer", "insuficiencia", "epoc");
    private static final List<String> ANTECEDENTES_MEDIOS = List.of("hipertensión", "hipertension", "diabetes");
    private static final List<String> DOLENCIAS_CRITICAS = List.of("dolor torácico", "dolor toracico", "dificultad respiratoria", "trauma", "hemorragia");
    private static final List<String> DOLENCIAS_MEDIAS = List.of("fractura", "fiebre alta", "dolor abdominal", "vómito", "vomito");
    private static final List<String> FACTORES_CRITICOS = List.of("embarazada", "menor sin acompañante", "vive solo");
    private static final List<String> FACTORES_MEDIOS = List.of("apoyo familiar");

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
        for (String antecedente : paciente.getAntecedentes()) {
            String antecedenteMin = antecedente.toLowerCase();
            if (ANTECEDENTES_CRITICOS.stream().anyMatch(antecedenteMin::contains)) {
                return PUNTOS_ALTA;
            } else if (ANTECEDENTES_MEDIOS.stream().anyMatch(antecedenteMin::contains)) {
                maxPuntos = Math.max(maxPuntos, PUNTOS_MEDIA);
            }
        }
        return maxPuntos;
    }

    // Calcula los puntos según el tipo de dolencia actual
    private static int calcularPuntosDolencia(Paciente paciente) {
        int maxPuntos = PUNTOS_BAJA;
        for (String dolencia : paciente.getDolencias()) {
            String dolenciaMin = dolencia.toLowerCase();
            if (DOLENCIAS_CRITICAS.stream().anyMatch(dolenciaMin::contains)) {
                return PUNTOS_ALTA;
            } else if (DOLENCIAS_MEDIAS.stream().anyMatch(dolenciaMin::contains)) {
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
        for (String factor : paciente.getFactoresSociales()) {
            String factorMin = factor.toLowerCase();
            // Casos especiales: "menor sin acompañante" y "vive solo" con edad > 70
            if (FACTORES_CRITICOS.stream().anyMatch(factorMin::contains)
                || (factorMin.contains("menor") && factorMin.contains("sin acompañante"))
                || (factorMin.contains("vive solo") && paciente.getEdad() > 70)) {
                return PUNTOS_ALTA;
            } else if (FACTORES_MEDIOS.stream().anyMatch(factorMin::contains)) {
                maxPuntos = Math.max(maxPuntos, PUNTOS_MEDIA);
            }
        }
        return maxPuntos;
    }
}