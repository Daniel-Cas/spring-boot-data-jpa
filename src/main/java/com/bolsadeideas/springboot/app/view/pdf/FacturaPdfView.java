package com.bolsadeideas.springboot.app.view.pdf;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {


    @Override
    protected void buildPdfDocument(Map<String, Object> model, com.lowagie.text.Document document, com.lowagie.text.pdf.PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        Factura factura = (Factura) model.get("factura");

        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingAfter(21);
        tabla.addCell("Datos del cliente");
        tabla.addCell(factura.getCliente().getNombre()+ " " + factura.getCliente().getApellido() );
        tabla.addCell(factura.getCliente().getEmail());



        PdfPTable tabla2 = new PdfPTable(1);
        tabla2.setSpacingAfter(21);
        tabla2.addCell("Datos de la factura");
        tabla2.addCell("Folio: " + factura.getId());
        tabla2.addCell("Descripción " + factura.getDescripcion() );
        tabla2.addCell("Fecha: "+ factura.getCreateAt() );

        document.add(tabla);
        document.add(tabla2);



    }
}
