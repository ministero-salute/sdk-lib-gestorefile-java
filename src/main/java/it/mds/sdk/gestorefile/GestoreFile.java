/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.gestorefile;

import jakarta.xml.bind.JAXBException;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface GestoreFile {

    static final int DIM_MINIMA = 0;
    static final int DIM_MASSIMA = 500;
    static final String DEFAULT_PATH = "";

    <T> void scriviDto(T dto, String path);

    <T> void scriviDto(T dto, String path, URL urlXxd) throws SAXException, JAXBException, IOException;

    <T> void scriviDtoFragment(T dto, String path, URL urlXsd);

    <T> void scriviDtoFragment(T dto, String path);

    /**
     * Il metodo prende in input una stringa che contiene il path del file da leggere e ritorna un oggetto Path
     *
     * @param rawPath del file da controllare per evitare injection
     * @return Path
     * @throws IOException e
     */
    default Path leggiFile(String rawPath) throws IOException {
        String path = FilenameUtils.normalize(rawPath);
        if (path != null && path.length() > DIM_MINIMA && path.length() < DIM_MASSIMA) {
            return Paths.get(path);
        }
        return Paths.get(DEFAULT_PATH);
    }

    /**
     * Il metodo prende in input una stringa che contiene il path del file che vogliamo scrivere e
     * una stringa che contiene il suo contenuto.
     *
     * @param path    path del file
     * @param content contenuto
     * @return Boolean ritorna true quando l'operazione va a buon fine e false viceversa.
     */
    default Boolean scriviFile(String path, String content) throws IOException {
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FilenameUtils.normalize(path)), StandardCharsets.UTF_8))) {
            bufferedWriter.write(content);
            return true;
        }
    }

    /**
     * Scrive il content andando in append sul file specificato dal path.
     *
     * @param path    file sul quale scrivere
     * @param content informazioni da scrivere in append
     * @return true se l'operazione è andata a buon fine, false altrimenti
     * @throws IOException in caso di errore IO nella scrittura del file
     */
    default Boolean scriviFileAppend(String path, String content) throws IOException {
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FilenameUtils.normalize(path),
                             true), StandardCharsets.UTF_8))) {
            bufferedWriter.write(content);
            return true;
        }
    }
}
