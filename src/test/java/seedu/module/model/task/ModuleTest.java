package seedu.module.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.module.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null));
    }

    @Test
    public void constructor_invalidModule_throwsIllegalArgumentException() {
        String invalidModule = "";
        assertThrows(IllegalArgumentException.class, () -> new Module(invalidModule));
    }

    @Test
    public void isValidModule() {
        // null module
        assertThrows(NullPointerException.class, () -> Module.isValidModuleFormat(null));

        // blank module
        assertFalse(Module.isValidModuleFormat("")); // empty string
        assertFalse(Module.isValidModuleFormat(" ")); // spaces only

        // missing parts
        assertFalse(Module.isValidModuleFormat("1101")); // Only Module Code
        assertFalse(Module.isValidModuleFormat("CS")); // Only Major Name

        // invalid parts
        assertFalse(Module.isValidModuleFormat("CS110")); // shorter length of code
        assertFalse(Module.isValidModuleFormat("CS11111")); // longer length of code
        assertFalse(Module.isValidModuleFormat("Computer Science 1101")); // Invalid Major Name

        // valid module
        assertTrue(Module.isValidModuleFormat("CS1101S"));
        assertTrue(Module.isValidModuleFormat("CS3243")); // minimal
        assertTrue(Module.isValidModuleFormat("DSA1101")); // alphabets only
    }

    @Test
    public void equals() {
        Module firstModule = new Module("CS1101S");
        Module secondModule = new Module("CS1101S");
        Module thirdModule = new Module("CS2030");
        assertTrue(firstModule.equals(firstModule));
        assertTrue(firstModule.equals(secondModule));
        assertFalse(firstModule.equals(thirdModule));
    }
}
