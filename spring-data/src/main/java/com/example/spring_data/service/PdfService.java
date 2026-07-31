package com.example.spring_data.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PdfService {

    @Value("${pdf.output.path}")
    private String outputPath;

    @Value("${pdf.existing.path}")
    private String existingPath;

    @Value("${pdf.modified.path}")
    private String modifiedPath;

    @Value("${pdf.sample.image}")
    private String imagePath;

    public void generatePdf() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Pdf generated from spring boot");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, 660);
                contentStream.showText("Another added text");
                contentStream.endText();

                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    PDImageXObject image = PDImageXObject.createFromFile(imagePath, document);
                    contentStream.drawImage(image, 50, 400, 200, 150);
                }
            }

            document.save(outputPath);
            System.out.println("Pdf generated: " + outputPath);

        } catch (IOException e) {
            System.err.println("Error generating pdf " + e.getMessage());
        }
    }

    public void modifyExistingPdf() {
        File existingFile = new File(existingPath);
        if (!existingFile.exists()) {
            System.err.println("Specified file doesn't exist");
            return;
        }

        try (PDDocument document = Loader.loadPDF(existingFile)) {
            PDPage page = document.getPage(0);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                contentStream.newLineAtOffset(50, 100);
                contentStream.showText("Added new text to pdf");
                contentStream.endText();
            }

            document.save(modifiedPath);
            System.out.println("Pdf modified: " + modifiedPath);

        } catch (IOException e) {
            System.err.println("Error generating pdf " + e.getMessage());
        }
    }
}
