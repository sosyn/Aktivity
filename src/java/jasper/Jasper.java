/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasper;

import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author sosyn
 */
public class Jasper implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] jsonCesta = new byte[]{};
    private byte[] pdfAsByte = new byte[]{};

    /**
     * @param jsonCesta the jsonCesta to set
     */
    public void setJsonCesta(byte[] jsonCesta) {
        this.jsonCesta = jsonCesta;
    }

    /**
     * @return the pdfAsByte
     */
    public byte[] getPdfAsByte() {
        return pdfAsByte;
    }

    public void makePdf() {
        try {
            File sourceFile = new File("e:\\NetBeansProjects\\Aktivity\\src\\java\\sestavy\\Cesta.jasper");
            File destFile = new File(sourceFile.getParent(), sourceFile.getName() + ".pdf");

            //Load compiled jasper report that we created on first section.
            JasperReport report = (JasperReport) JRLoader.loadObject(sourceFile);
            //Convert json string to byte array.
            ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(this.jsonCesta);
            //Create json datasource from json stream
            JsonDataSource ds = new JsonDataSource(jsonDataStream);
            //Create HashMap to add report parameters
            Map parameters = new HashMap();
            //Add title parameter. Make sure the key is same name as what you named the parameters in jasper report.
            parameters.put("title", "Jasper PDF Cesta");
            parameters.put("name", "Ivo");
            parameters.put("value", "Sosýn");
            // parameters.put(JRParameter.REPORT_LOCALE,new Locale("cs_CZ"));
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

            // Export do Html jako byte
            HtmlExporter htmlExporter = new HtmlExporter();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(50000);
            htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(baos));
            htmlExporter.exportReport();

            this.pdfAsByte = baos.toByteArray();
            // System.out.println("this.pdfAsByte: " + new String(this.pdfAsByte, "UTF-8"));
        } catch (JRException ex) {
            Logger.getLogger(Jasper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
