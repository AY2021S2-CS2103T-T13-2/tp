package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.module.model.Model;
import seedu.module.model.ModuleManager;
import seedu.module.model.task.Task;

/**
 * Refresh the deadline of the module book.
 */
public class RefreshCommand extends Command {

    public static final String COMMAND_WORD = "refresh";

    public static final String MESSAGE_SUCCESS = "All tasks are up to date!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Task> taskList = model.getFilteredTaskList();
        for (Task taskToCheck : taskList) {
            Task taskToBeReplaced = taskToCheck;
            boolean isUpdated = false;
            while (taskToCheck.isRecurring() && !taskToCheck.isUpToDate()) {
                taskToCheck = Task.updateRecurrenceTask(taskToCheck);
                isUpdated = true;
            }
            if (isUpdated) {
                model.setTask(taskToBeReplaced, taskToCheck);
            }
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
