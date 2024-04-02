import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestClass {

    @Test
    @DisplayName("Test")
    public void testExample() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        CarsDBRepository repository = new CarsDBRepository(props);
        System.out.println("Tests passed");
    }
}
