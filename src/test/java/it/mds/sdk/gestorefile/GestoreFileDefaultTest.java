package it.mds.sdk.gestorefile;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@ExtendWith(MockitoExtension.class)
//@Slf4j
class GestoreFileDefaultTest {

    GestoreFileDefault gestoreFile = new GestoreFileDefault();

    @Test
    void scriviDtoTestOk() {
        String path = "";
        URL url = Mockito.mock(URL.class);
        assertThrows(UnsupportedOperationException.class, () -> gestoreFile.scriviDto(null, path));
    }

    @Test
    void scriviDto2TestOk() {
        String path = "";
        URL url = Mockito.mock(URL.class);
        assertThrows(UnsupportedOperationException.class, () -> gestoreFile.scriviDto(null, path, url));
    }


    @Test
    void scriviDtoFragmentTestOk() {
        String path = "";
        URL url = Mockito.mock(URL.class);
        assertThrows(UnsupportedOperationException.class, () -> gestoreFile.scriviDtoFragment(null, path, url));
    }

    @Test
    void scriviDtoFragmentTestNoXsdOk() {
        String path = "";
        URL url = Mockito.mock(URL.class);
        assertThrows(UnsupportedOperationException.class, () -> gestoreFile.scriviDtoFragment(null, path));
    }
}
