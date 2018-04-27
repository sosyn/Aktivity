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
import java.util.HashMap;
import java.util.Map;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
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
@Singleton
@ConcurrencyManagement(CONTAINER)
@AccessTimeout(value = 8, unit = SECONDS)
public class Jasper {
    
    private static final long serialVersionUID = 1L;
    
    @Lock(LockType.WRITE)
    public byte[] makePdf(byte[] jsonCesta) {
        byte[] pdfAsByte = new byte[]{};
        if (jsonCesta == null || jsonCesta.length == 0) {
            return pdfAsByte;
        }
        try {
            File sourceFile = new File("d:\\NetBeansProjects\\Aktivity\\src\\java\\sestavy\\Cesta.jasper");
            File destFile = new File(sourceFile.getParent(), sourceFile.getName() + ".pdf");

            //Load compiled jasper report that we created on first section.
            JasperReport report = (JasperReport) JRLoader.loadObject(sourceFile);
            //Convert json string to byte array.
            ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsonCesta);
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

            // Export do Html jako byte
            HtmlExporter htmlExporter = new HtmlExporter();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(50000);
            htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(baos));
            htmlExporter.exportReport();

            pdfAsByte = baos.toByteArray();
            // System.out.println("this.pdfAsByte: " + new String(this.pdfAsByte, "UTF-8"));
        } catch (JRException ex) {
            Logger.getLogger(Jasper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pdfAsByte;
    }
}
