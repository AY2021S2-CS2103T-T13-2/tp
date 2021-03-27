package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.module.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.module.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.module.commons.core.Messages;
import seedu.module.commons.core.index.Index;
import seedu.module.commons.core.optionalfield.OptionalField;
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

/**
 * Deletes a tag from an existing task in the module book.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deleteTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tag from task identified "
            + "by the index number used in the last person listing. "
            + "Only one tag deletion per command.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + "t/Midterm";

    public static final String MESSAGE_DELETE_TAG_TASK_SUCCESS = "Deleted Tag from Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one tag must be provided to delete.";
    public static final String MESSAGE_TAG_NOT_EXISTS = "This tag does not exist.";

    private final Index index;
    private final Tag tag;

    /**
     * @param index of the task in the filtered task list
     * @param tag associated with the task to be deleted
     */
    public DeleteTagCommand(Index index, Tag tag) {
        requireAllNonNull(index, tag);
        this.index = index;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) { //if index provided does not exist
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToTag = lastShownList.get(index.getZeroBased());
        Set<Tag> oldTags = taskToTag.getTags();
        Set<Tag> newTags = deleteTag(oldTags, this.tag);

        if (oldTags.equals(newTags)) {
            //no change to tags deleted as it does not exists
            throw new CommandException(MESSAGE_TAG_NOT_EXISTS);
        }

        Task editedTask;
        Name name = taskToTag.getName();
        OptionalField<Time> startTime = taskToTag.getStartTimeWrapper();
        Time deadline = taskToTag.getDeadline();
        Module module = taskToTag.getModule();
        Description description = taskToTag.getDescription();
        Workload workload = taskToTag.getWorkload();
        DoneStatus newDoneStatus = new DoneStatus(false);
        OptionalField<Recurrence> recurrence = taskToTag.getRecurrenceWrapper();

        editedTask = new Task(name, startTime, deadline, module, description, workload, newDoneStatus, recurrence,
                newTags);

        model.setTask(taskToTag, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_TASK_SUCCESS, editedTask));
    }

    /**
     * Delete tag from set of old tags
     *
     * @param oldTags set of old tags
     * @param toBeDeletedTag Tag to be deleted
     * @return set of new tags
     */
    private Set<Tag> deleteTag(Set<Tag> oldTags, Tag toBeDeletedTag) {
        Set<Tag> newTags = new HashSet<>(oldTags);
        newTags.remove(toBeDeletedTag);
        return newTags;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand e = (DeleteTagCommand) other;
        return index.equals(e.index)
                && tag.equals(e.tag);
    }
}
