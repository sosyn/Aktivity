/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasper;

import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author sosyn
 */
@Stateless
public class Jasper extends Thread {

    private static final long serialVersionUID = 1L;
    private String jsonCesta = "";

    /**
     * @return the jsonCesta
     */
    public String getJsonCesta() {
        return jsonCesta;
    }

    /**
     * @param jsonCesta the jsonCesta to set
     */
    public void setJsonCesta(String jsonCesta) {
        this.jsonCesta = jsonCesta;
    }

    @Override
    public void run() {
        try {
            File sourceFile = new File("e:\\NetBeansProjects\\Aktivity\\src\\java\\sestavy\\Cesta.jasper");
            File destFile = new File(sourceFile.getParent(), sourceFile.getName() + ".pdf");

            //Load compiled jasper report that we created on first section.
            JasperReport report = (JasperReport) JRLoader.loadObject(sourceFile);
            //Convert json string to byte array.
            ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(getJsonCesta().getBytes());
            //Create json datasource from json stream
            JsonDataSource ds = new JsonDataSource(jsonDataStream);
            //Create HashMap to add report parameters
            Map parameters = new HashMap();
            //Add title parameter. Make sure the key is same name as what you named the parameters in jasper report.
            parameters.put("title", "Jasper PDF Cesta");
            parameters.put("name", "Ivo");
            parameters.put("value", "Sosýn");
            //Create Jasper Print object passing report, parameter json data source.
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
            //Export and save pdf to file
            JRPdfExporter jrPdfExporter = new JRPdfExporter();
            jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));

            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setEncrypted(true);
            configuration.set128BitKey(true);
            configuration.setUserPassword("jasper");
            configuration.setOwnerPassword("reports");
            configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);

            //jrPdfExporter.setConfiguration(configuration);
            jrPdfExporter.exportReport();

            // JasperExportManager.exportReportToPdfFile(jasperPrint, "e:\\NetBeansProjects\\Aktivity\\src\\java\\sestavy\\CestaPDF.PDF");
        } catch (JRException ex) {
            Logger.getLogger(Jasper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
