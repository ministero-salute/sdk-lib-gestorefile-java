package it.mds.sdk.gestorefile.factory;

import it.mds.sdk.gestorefile.GestoreFile;
import it.mds.sdk.gestorefile.GestoreFileCSVImpl;
import it.mds.sdk.gestorefile.GestoreFileDefault;
import it.mds.sdk.gestorefile.GestoreFileXMLImpl;

/**
 * La classe è il factory dell'interfaccia GestoreFile
 */
public final class GestoreFileFactory {

    private GestoreFileFactory() {
        throw new IllegalStateException("Not allowed");
    }

    public static GestoreFile getGestoreFile(String tipoFile) {
        if ("CSV".equalsIgnoreCase(tipoFile)) {
            return new GestoreFileCSVImpl();
        }
        if ("XML".equalsIgnoreCase(tipoFile)) {
            return new GestoreFileXMLImpl();
        }
        return new GestoreFileDefault();
    }
}
