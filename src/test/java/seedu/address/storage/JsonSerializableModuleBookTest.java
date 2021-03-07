package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ModuleBook;
import seedu.address.testutil.TypicalTasks;

public class JsonSerializableModuleBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModuleBookTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksModuleBook.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskModuleBook.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskModuleBook.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableModuleBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableModuleBook.class).get();
        ModuleBook addressBookFromFile = dataFromFile.toModelType();
        ModuleBook typicalTasksModuleBook = TypicalTasks.getTypicalModuleBook();
        assertEquals(addressBookFromFile, typicalTasksModuleBook);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModuleBook dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableModuleBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        JsonSerializableModuleBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
                JsonSerializableModuleBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableModuleBook.MESSAGE_DUPLICATE_TASK,
                dataFromFile::toModelType);
    }

}
