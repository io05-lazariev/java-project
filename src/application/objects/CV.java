package application.objects;

import java.io.File;
import java.net.MalformedURLException;

import javax.print.Doc;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

public class CV {
    
    public static void makeCV(PdfWriter writer, Human human) throws MalformedURLException {
        PdfDocument cvPdf = new PdfDocument(writer);
        cvPdf.addNewPage();
        Document pdfDoc = new Document(cvPdf);
        File profileImageFile = human.getProfileImage();
        if (profileImageFile != null) {
            ImageData profileImageData = ImageDataFactory.create(profileImageFile.getPath());
            Image profileImage = new Image(profileImageData);
            profileImage.setFixedPosition(0, 0);
            pdfDoc.add(profileImage);
        }
        Paragraph name = new Paragraph(human.getFullName());
        pdfDoc.add(name);
        pdfDoc.close();
    }

}
