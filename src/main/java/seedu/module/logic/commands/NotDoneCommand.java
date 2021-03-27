package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.module.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Set;

import seedu.module.commons.core.Messages;
import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.tag.Tag;
import seedu.module.model.task.Description;
import seedu.module.model.task.DoneStatus;
import seedu.module.model.task.Module;
import seedu.module.model.task.Name;
import seedu.module.model.task.Recurrence;
import seedu.module.model.task.Task;
import seedu.module.model.task.Time;
import seedu.module.model.task.Workload;

public class NotDoneCommand extends Command {
    public static final String COMMAND_WORD = "notdone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the displayed task list as not done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NOT_DONE_TASK_SUCCESS = "Not done Task: %1$s";
    public static final String MESSAGE_TASK_ALREADY_NOT_DONE = "Task is already not done!";

    private final Index index;

    public NotDoneCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToMarkNotDone = lastShownList.get(index.getZeroBased());
        Task doneTask = createNotDoneTask(taskToMarkNotDone);

        if (taskToMarkNotDone.equals(doneTask) && model.hasTask(doneTask)) {
            throw new CommandException(MESSAGE_TASK_ALREADY_NOT_DONE);
        }

        model.setTask(taskToMarkNotDone, doneTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_NOT_DONE_TASK_SUCCESS, doneTask));
    }

    private static Task createNotDoneTask(Task taskToMarkNotDone) {
        assert taskToMarkNotDone != null;

        Name name = taskToMarkNotDone.getName();
        Time startTime = taskToMarkNotDone.getStartTime();
        Time deadline = taskToMarkNotDone.getDeadline();
        Module module = taskToMarkNotDone.getModule();
        Description description = taskToMarkNotDone.getDescription();
        Workload workload = taskToMarkNotDone.getWorkload();
        DoneStatus newDoneStatus = new DoneStatus(false);
        Recurrence recurrence = taskToMarkNotDone.getRecurrence();
        Set<Tag> tags = taskToMarkNotDone.getTags();

        if (!taskToMarkNotDone.isRecurring() && !taskToMarkNotDone.isDeadline()) {
            return new Task(name, startTime, deadline, module, description, workload, newDoneStatus, tags);

        } else if (!taskToMarkNotDone.isRecurring() && taskToMarkNotDone.isDeadline()) {
            return new Task(name, deadline, module, description, workload, newDoneStatus, tags);

        } else if (taskToMarkNotDone.isRecurring() && !taskToMarkNotDone.isDeadline()) {
            return new Task(name, startTime, deadline, module, description, workload, newDoneStatus, recurrence, tags);

        } else {
            return new Task(name, deadline, module, description, workload, newDoneStatus, recurrence, tags);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotDoneCommand // instanceof handles nulls
                && index.equals(((NotDoneCommand) other).index)); // state check
    }
}
