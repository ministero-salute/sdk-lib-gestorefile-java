package it.mds.sdk.gestorefile;

import it.mds.sdk.gestorefile.exception.XSDNonSupportedException;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * La classe implementa l'interfaccia GestoreFile e i suoi metodi astratti
 */
@Slf4j
public class GestoreFileXMLImpl extends AbstractSecurityModel implements GestoreFile {

    @Override
    public <T> void scriviDto(T dto, String path) {
        try {
            Class obj = dto.getClass();
            jakarta.xml.bind.JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{obj}, null);
            Marshaller jaxbMarshaller = getJAXBMarshaller(jaxbContext);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
            java.io.StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(dto, sw);
            this.scriviFile(path, sw.toString());
        } catch (JAXBException | IOException e) {
            log.error("Impossibile scrivere il file xml ", e);
        }
    }

    @Override
    public <T> void scriviDto(T dto, String path, URL urlXsd) throws SAXException, JAXBException, IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FilenameUtils.normalize(path)), StandardCharsets.UTF_8))) {
            Class obj = dto.getClass();
            jakarta.xml.bind.JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{obj}, null);
            Marshaller jaxbMarshaller = getJAXBMarshaller(jaxbContext);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(urlXsd);
            jaxbMarshaller.setSchema(schema);
            jaxbMarshaller.marshal(dto, bw);
        } catch (SAXException | JAXBException | IOException e) {
            log.error("XSD del file {} non validato. ", path, e);
            throw new XSDNonSupportedException("XSD non validato ", e);
        }
    }

    public <T> void scriviDtoFragment(T dto, String path, URL urlXsd) {
        try {
            jakarta.xml.bind.JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{dto.getClass()}, null);
            Marshaller jaxbMarshaller = getJAXBMarshaller(jaxbContext);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(urlXsd);
            jaxbMarshaller.setSchema(schema);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            BufferedWriter bw =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FilenameUtils.normalize(path), true),
                            StandardCharsets.UTF_8));
            jaxbMarshaller.marshal(dto, bw);
            bw.close();
        } catch (SAXException | JAXBException | IOException e) {
            log.error("XSD non validato. ", e);
            throw new XSDNonSupportedException("XSD non validato ", e);
        }
    }

    public <T> void scriviDtoFragment(T dto, String path) {
        try {
            jakarta.xml.bind.JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{dto.getClass()}, null);
            Marshaller jaxbMarshaller = getJAXBMarshaller(jaxbContext);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            BufferedWriter bw =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FilenameUtils.normalize(path), true),
                            StandardCharsets.UTF_8));
            jaxbMarshaller.marshal(dto, bw);
            bw.close();
        } catch (JAXBException | IOException e) {
            log.error("XSD del file {} non validato. ", path, e);
            throw new XSDNonSupportedException("XSD non validato ", e);
        }
    }

    public Marshaller getJAXBMarshaller(jakarta.xml.bind.JAXBContext jaxbContext) throws JAXBException {
        return jaxbContext.createMarshaller();
    }
}
