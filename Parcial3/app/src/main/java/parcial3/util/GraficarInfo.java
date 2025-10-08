package parcial3.Util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.io.File;
import java.util.List;
import parcial3.Model.Paciente;

public class GraficarInfo {
    public static void generarGraficoBarras(int alta, int media, int baja) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(alta, "Pacientes", "Alta");
            dataset.addValue(media, "Pacientes", "Media");
            dataset.addValue(baja, "Pacientes", "Baja");

            JFreeChart barChart = ChartFactory.createBarChart(
                "Pacientes Atendidos por Prioridad",
                "Prioridad",
                "Cantidad",
                dataset
            );
            File outputFile = new File("C:/Users/david/Documents/GitHub/Parcial3/Parcial3/app/imagenesReporte/reporte_grafico.png");
            ChartUtils.saveChartAsPNG(outputFile, barChart, 600, 400);
        } catch (Exception e) {
            System.err.println("Error generando gráfico: " + e.getMessage());
        }
    }

    public static void generarGraficoPastel(int alta, int media, int baja) {
        try {
            DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
            dataset.setValue("Alta", alta);
            dataset.setValue("Media", media);
            dataset.setValue("Baja", baja);
            JFreeChart pieChart = ChartFactory.createPieChart(
                "Proporción de Pacientes Atendidos por Prioridad",
                dataset,
                true, true, false);
            File outputFile = new File("C:/Users/david/Documents/GitHub/Parcial3/Parcial3/app/imagenesReporte/reporte_pastel.png");
            ChartUtils.saveChartAsPNG(outputFile, pieChart, 600, 400);
        } catch (Exception e) {
            System.err.println("Error generando gráfico de pastel: " + e.getMessage());
        }
    }

    public static void generarGraficoLineasTiempos(List<Paciente> pacientesAtendidos, List<Long> tiemposEspera) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            int idx = 1;
            for (int i = 0; i < pacientesAtendidos.size(); i++) {
                double esperaSeg = tiemposEspera.get(i) / 1000.0;
                dataset.addValue(esperaSeg, "Tiempo de espera (s)", "Paciente " + idx);
                idx++;
            }
            JFreeChart lineChart = ChartFactory.createLineChart(
                "Tiempos de Espera por Paciente",
                "Paciente",
                "Tiempo de espera (segundos)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
            File outputFile = new File("C:/Users/david/Documents/GitHub/Parcial3/Parcial3/app/imagenesReporte/reporte_tiempos.png");
            ChartUtils.saveChartAsPNG(outputFile, lineChart, 700, 400);
        } catch (Exception e) {
            System.err.println("Error generando gráfico de líneas: " + e.getMessage());
        }
    }

    public static void mostrarTablaPacientes(List<Paciente> pacientesAtendidos, List<Long> tiemposEspera) {
        try {
            String[] columnNames = {"ID", "Edad", "Dolor", "Prioridad", "Tiempo de espera (s)"};
            Object[][] data = new Object[pacientesAtendidos.size()][5];
            for (int i = 0; i < pacientesAtendidos.size(); i++) {
                Paciente p = pacientesAtendidos.get(i);
                data[i][0] = p.getId();
                data[i][1] = p.getEdad();
                data[i][2] = p.getNivelDolor();
                data[i][3] = p.getPuntosPrioridad();
                data[i][4] = String.format("%.2f", tiemposEspera.get(i) / 1000.0);
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("Tabla de Pacientes Atendidos");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.setSize(600, 300);
            frame.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error mostrando tabla de pacientes: " + e.getMessage());
        }
    }
}
