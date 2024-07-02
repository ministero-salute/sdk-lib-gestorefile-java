/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.gestorefile;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * La classe implementa l'interfaccia GestoreFile e i suoi 2 metodi astratti
 */
@Slf4j
public class GestoreFileCSVImpl extends AbstractSecurityModel implements GestoreFile {

    @Override
    public <T> void scriviDto(T dto, String path) {throw new UnsupportedOperationException();
    }

    @Override
    public <T> void scriviDto(T dto, String path, URL pathXSD) {
        log.debug("{}.scriviDto ", this.getClass().getName());
        throw new UnsupportedOperationException("Operazione non supportata per gestore file CSV");
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
