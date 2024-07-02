/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.gestorefile;

import it.mds.sdk.gestorefile.exception.XSDNonSupportedException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GestoreFileXMLImplTest {

    GestoreFileXMLImpl gestoreFileXML;
    String mockedString = "mock";

    JAXBContext jaxbContext;
    SchemaFactory schemaFactory;
    Marshaller marshaller;
    Class[] clazz;

    MockedStatic<JAXBContextFactory> mockedStatic;
    MockedStatic<SchemaFactory> factoryMockedStatic;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mockedStatic = mockStatic(JAXBContextFactory.class);
        factoryMockedStatic = mockStatic(SchemaFactory.class);
    }

    @Test
    void scriviDtoTest() throws JAXBException {

        gestoreFileXML = spy(new GestoreFileXMLImpl());
        clazz = new Class[]{mockedString.getClass()};

        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenThrow(new JAXBException(""));

        given(jaxbContext.createMarshaller()).willReturn(marshaller);
        Mockito.when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);

        gestoreFileXML.scriviDto(mockedString, "path");
    }

    @Test
    void scriviDtoTestKO() throws JAXBException {

        gestoreFileXML = spy(new GestoreFileXMLImpl());
        clazz = new Class[]{mockedString.getClass()};

        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenReturn(jaxbContext);

        given(jaxbContext.createMarshaller()).willReturn(marshaller);
        Mockito.when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);

        gestoreFileXML.scriviDto(mockedString, "path");

    }

    @Test
    void scriviDtoTest2() throws JAXBException, SAXException, IOException {

        gestoreFileXML = spy(new GestoreFileXMLImpl());
        clazz = new Class[]{mockedString.getClass()};

        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);
        schemaFactory = Mockito.mock(SchemaFactory.class);
        Schema schema = Mockito.mock(Schema.class);
        URL url = Mockito.mock(URL.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenReturn(jaxbContext);
        factoryMockedStatic.when(() -> SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)).thenReturn(schemaFactory);

        given(jaxbContext.createMarshaller()).willReturn(marshaller);
        given(schemaFactory.newSchema(url)).willReturn(schema);
        Mockito.when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);

        gestoreFileXML.scriviDto(mockedString, "path", url);

    }

    @Test
    void scriviDtoTest2ko() throws JAXBException, SAXException, IOException {

        gestoreFileXML = spy(new GestoreFileXMLImpl());
        clazz = new Class[]{mockedString.getClass()};

        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);
        schemaFactory = Mockito.mock(SchemaFactory.class);
        Schema schema = Mockito.mock(Schema.class);
        URL url = Mockito.mock(URL.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenThrow(new JAXBException(""));
        factoryMockedStatic.when(() -> SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)).thenReturn(schemaFactory);

        given(jaxbContext.createMarshaller()).willReturn(marshaller);
        given(schemaFactory.newSchema(url)).willReturn(schema);
        when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);

        Assertions.assertThrows(XSDNonSupportedException.class, () -> gestoreFileXML.scriviDto(mockedString, "path", url));

    }

    @Test
    void scriviDtoFragment() throws JAXBException, SAXException {

        gestoreFileXML = new GestoreFileXMLImpl();
        clazz = new Class[]{mockedString.getClass()};
        URL url = Mockito.mock(URL.class);
        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);
        schemaFactory = Mockito.mock(SchemaFactory.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenReturn(jaxbContext);
        factoryMockedStatic.when(() -> SchemaFactory.newInstance(any())).thenReturn(schemaFactory);

        when(schemaFactory.newSchema(url)).thenReturn(null);
        given(Mockito.mock(JAXBContext.class).createMarshaller()).willReturn(Mockito.mock(Marshaller.class));
        Mockito.when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);
        doNothing().when(marshaller).marshal(any(Object.class), any(BufferedWriter.class));

        gestoreFileXML.scriviDtoFragment(mockedString, "path", url);

    }

    @Test
    void scriviDtoFragmentKO() throws JAXBException, SAXException {

        gestoreFileXML = new GestoreFileXMLImpl();
        clazz = new Class[]{mockedString.getClass()};
        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);
        URL url = Mockito.mock(URL.class);
        SchemaFactory schemaFactory = Mockito.mock(SchemaFactory.class);
        Schema schema = Mockito.mock(Schema.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenThrow(new JAXBException(""));
        factoryMockedStatic.when(() -> SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)).thenReturn(schemaFactory);

        given(schemaFactory.newSchema(url)).willReturn(schema);
        given(Mockito.mock(JAXBContext.class).createMarshaller()).willReturn(Mockito.mock(Marshaller.class));
        Mockito.when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);
        doNothing().when(marshaller).marshal(any(Object.class), any(BufferedWriter.class));

        Assertions.assertThrows(XSDNonSupportedException.class, () -> gestoreFileXML.scriviDtoFragment(mockedString, "path", url));
    }

    @Test
    void scriviDtoFragmentNoXsd() throws JAXBException, SAXException {

        gestoreFileXML = new GestoreFileXMLImpl();
        clazz = new Class[]{mockedString.getClass()};
        URL url = Mockito.mock(URL.class);
        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);
        schemaFactory = Mockito.mock(SchemaFactory.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenReturn(jaxbContext);
        factoryMockedStatic.when(() -> SchemaFactory.newInstance(any())).thenReturn(schemaFactory);

        when(schemaFactory.newSchema(url)).thenReturn(null);
        given(Mockito.mock(JAXBContext.class).createMarshaller()).willReturn(Mockito.mock(Marshaller.class));
        Mockito.when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);
        doNothing().when(marshaller).marshal(any(Object.class), any(BufferedWriter.class));

        gestoreFileXML.scriviDtoFragment(mockedString, "path");

    }

    @Test
    void scriviDtoFragmentNoXsdKO() throws JAXBException, SAXException {

        gestoreFileXML = new GestoreFileXMLImpl();
        clazz = new Class[]{mockedString.getClass()};
        jaxbContext = Mockito.mock(JAXBContext.class);
        marshaller = Mockito.mock(Marshaller.class);
        URL url = Mockito.mock(URL.class);
        SchemaFactory schemaFactory = Mockito.mock(SchemaFactory.class);
        Schema schema = Mockito.mock(Schema.class);

        mockedStatic.when(() -> JAXBContextFactory.createContext(clazz, null)).thenThrow(new JAXBException(""));
        factoryMockedStatic.when(() -> SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)).thenReturn(schemaFactory);

        given(schemaFactory.newSchema(url)).willReturn(schema);
        given(Mockito.mock(JAXBContext.class).createMarshaller()).willReturn(Mockito.mock(Marshaller.class));
        Mockito.when(gestoreFileXML.getJAXBMarshaller(jaxbContext)).thenReturn(marshaller);
        doNothing().when(marshaller).marshal(any(Object.class), any(BufferedWriter.class));

        Assertions.assertThrows(XSDNonSupportedException.class, () -> gestoreFileXML.scriviDtoFragment(mockedString, "path"));
    }

    @AfterEach
    void closeMocks() {
        mockedStatic.close();
        factoryMockedStatic.close();
    }
}
