package it.mds.sdk.gestorefile;

import it.mds.sdk.gestorefile.factory.GestoreFileFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
//@Slf4j
class GestoreFileCSVImplTest {

    private static final String FILE_TEST_CSV = "src/test/resources/test.csv";
    GestoreFileCSVImpl gestoreFile = new GestoreFileCSVImpl();

    @AfterAll
    public static void deleteCreatedFiles() {
        File file = new File(FILE_TEST_CSV);
        file.delete();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void scriviDtoTestOk() {
        Object obj = Mockito.mock(Object.class);
        String path = "";
        URL url = Mockito.mock(URL.class);
        assertThrows(UnsupportedOperationException.class, () -> gestoreFile.scriviDto(obj, path, url));
    }

    @Test
    void scriviDtoTestOkVuoto() {
        Object obj = Mockito.mock(Object.class);
        String path = "";
        assertThrows(UnsupportedOperationException.class, () -> gestoreFile.scriviDto(null, path));
        //gestoreFile.scriviDto(obj, path);
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

    @Test
    void testFactory() {
        GestoreFile gestoreFile = GestoreFileFactory.getGestoreFile("CSV");
        assertNotNull(gestoreFile);
        assertTrue(gestoreFile instanceof GestoreFileCSVImpl);

        GestoreFile gestoreFile1 = GestoreFileFactory.getGestoreFile("CVS");
        assertTrue(gestoreFile1 instanceof GestoreFileDefault);
    }

    @Test
    void scriviFileOK() throws IOException {
        GestoreFile gestoreFile = GestoreFileFactory.getGestoreFile("CSV");
        Path resourceDirectory = Paths.get("src/test/resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        File file = new File(absolutePath + FileSystems.getDefault().getSeparator() + "output.csv");

        assertTrue(gestoreFile.scriviFile(file.getAbsolutePath(), "Nome]-[Cognome"));
        Exception exception = assertThrows(java.io.FileNotFoundException.class, () -> {
            gestoreFile.scriviFile("", "Test false");
        });

    }

    @Test
    void leggiFileOK() throws IOException, URISyntaxException {
        GestoreFile gestoreFile = GestoreFileFactory.getGestoreFile("CSV");
        Path resourceDirectory = Paths.get("src/test/resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        Path pathReturn = gestoreFile.leggiFile(absolutePath.replace("\\", "/") + "output.csv");
        assertTrue(pathReturn instanceof Path);
        assertFalse(!(pathReturn instanceof Path));
    }

}