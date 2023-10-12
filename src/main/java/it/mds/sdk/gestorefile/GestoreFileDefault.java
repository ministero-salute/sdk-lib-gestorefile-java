package it.mds.sdk.gestorefile;

import java.net.URL;

/**
 * Classe che implementa Gestore file, da usare ogni volta che non si ha bisogno di leggere e persistere
 * oggetti specifici su file.
 */
public class GestoreFileDefault implements GestoreFile {
    @Override
    public <T> void scriviDto(T dto, String path) {
        throw new UnsupportedOperationException("Operazione non supportata per gestore di default");
    }

    @Override
    public <T> void scriviDto(T dto, String path, URL urlXsd) {
        throw new UnsupportedOperationException("Operazione non supportata per gestore di default");
    }

    @Override
    public <T> void scriviDtoFragment(T dto, String path, URL urlXsd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void scriviDtoFragment(T dto, String path) {
        throw new UnsupportedOperationException();
    }
}
