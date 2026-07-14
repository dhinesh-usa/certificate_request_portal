package com.examly.springapp.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CertificateGeneratorService {

    @Value("${certificate.storage-path}")
    private String storagePath;

    public String generate(Long requestId, String studentName, String courseName, LocalDate completionDate) throws IOException {
        File dir = new File(storagePath);
        if (!dir.exists()) dir.mkdirs();

        String fileName = "certificate_" + requestId + ".pdf";
        String filePath = storagePath + "/" + fileName;

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                float pageWidth = page.getMediaBox().getWidth();

                // Border
                content.setLineWidth(3f);
                content.addRect(20, 20, pageWidth - 40, page.getMediaBox().getHeight() - 40);
                content.stroke();

                // Title
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 28);
                content.newLineAtOffset(centerX(pageWidth, PDType1Font.HELVETICA_BOLD, 28, "Certificate of Completion"), 720);
                content.showText("Certificate of Completion");
                content.endText();

                // Subtitle
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 14);
                content.newLineAtOffset(centerX(pageWidth, PDType1Font.HELVETICA, 14, "This is to certify that"), 670);
                content.showText("This is to certify that");
                content.endText();

                // Student Name
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 22);
                content.newLineAtOffset(centerX(pageWidth, PDType1Font.HELVETICA_BOLD, 22, studentName), 630);
                content.showText(studentName);
                content.endText();

                // Course line
                String courseLine = "has successfully completed the course:";
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 14);
                content.newLineAtOffset(centerX(pageWidth, PDType1Font.HELVETICA, 14, courseLine), 590);
                content.showText(courseLine);
                content.endText();

                // Course Name
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 18);
                content.newLineAtOffset(centerX(pageWidth, PDType1Font.HELVETICA_BOLD, 18, courseName), 555);
                content.showText(courseName);
                content.endText();

                // Date
                String dateStr = "Date of Completion: " + completionDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(centerX(pageWidth, PDType1Font.HELVETICA, 12, dateStr), 500);
                content.showText(dateStr);
                content.endText();
            }

            document.save(filePath);
        }

        return "/api/certificates/download/" + fileName;
    }

    private float centerX(float pageWidth, PDType1Font font, int fontSize, String text) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        return (pageWidth - textWidth) / 2;
    }
}
