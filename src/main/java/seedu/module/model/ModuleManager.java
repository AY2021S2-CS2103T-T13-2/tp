package seedu.module.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.module.model.task.Module;
import seedu.module.model.task.Task;

/**
 * Represents a collection of Modules, and the list of Tasks associated with each.
 */
public class ModuleManager {

    private static final ObservableList<Module> moduleList = FXCollections.observableArrayList();
    private static final ObservableList<PieChart.Data> modulePieChartData = FXCollections.observableArrayList();
    private static HashMap<Module, List<Task>> mappingOfModulesToTasks;
    private static HashMap<Module, Integer> moduleWorkLoadDistribution;
    private static HashMap<Module, Integer> moduleLowWorkLoadDistribution;
    private static HashMap<Module, Integer> moduleMediumWorkLoadDistribution;
    private static HashMap<Module, Integer> moduleHighWorkLoadDistribution;
    private static List<String> supportedModulesInStr;
    private static final String[] arrOfModules = {"CS1101S", "CS1231S", "CS2030", "CS2040S", "CS2101",
        "CS2103T", "CS2105", "CS2106", "CS3230", "CS3243", "CS3244", "IS1103", "ST2131"};

    /**
     * A ModuleManager which manages the mapping of each module to its
     * associated List of Tasks.
     */
    public ModuleManager() {
        supportedModulesInStr = initListOfModulesAccepted();
        rebuildMapping();
    }

    /**
     * Initialize a List of Modules in String format.
     */
    public static void initSupportedModulesInStr() {
        supportedModulesInStr = initListOfModulesAccepted();
    }

    /**
     * Check whether a Module is valid to be used (supported).
     *
     * @param module Module
     * @return True if Module exists in our List of supported Modules.
     */
    public static boolean moduleIsValid(String module) {
        return supportedModulesInStr.contains(module);
    }

    /**
     * Inserts a Task into a Module's List of Tasks mapping.
     * If module already exists in the mappings, update the List of Tasks in the mapping.
     * If module does not exist in the mappings, create a new entry in the mappings.
     *
     * @param module Module
     * @param task Task
     */
    public static void insertTaskToMapping(Module module, Task task) {
        if (mappingOfModulesToTasks == null) {
            rebuildMapping();
        }
        if (mappingOfModulesToTasks.containsKey(module)) {
            List<Task> newList = mappingOfModulesToTasks.get(module);
            //must ensure Module exists in the listOfValidModules
            newList.add(task);
            mappingOfModulesToTasks.put(module, newList);
        } else {
            List<Task> newList = new ArrayList<>();
            newList.add(task);
            mappingOfModulesToTasks.put(module, newList);
            moduleWorkLoadDistribution.put(module, task.getWorkload().getWorkloadLevel());
        }
        putIntoCorrectWorkloadDistribution(module, task);
        setExistingModuleList();
        setModulePieChartData();
    }

    /**
     * Modifies the workload distribution after adding the task.
     *
     * @param module the corresponding module of the task
     * @param task the task that need to be inserted into mapping book.
     */
    public static void putIntoCorrectWorkloadDistribution(Module module, Task task) {
        int workloadLevel = task.getWorkload().getWorkloadLevel();
        moduleWorkLoadDistribution.put(module,
            moduleWorkLoadDistribution.get(module) + workloadLevel);
        switch(workloadLevel) {
        case 1:
            moduleLowWorkLoadDistribution.put(module,
                moduleLowWorkLoadDistribution.getOrDefault(module, 0) + 1);
            break;
        case 2:
            moduleMediumWorkLoadDistribution.put(module,
                moduleMediumWorkLoadDistribution.getOrDefault(module, 0) + 1);
            break;
        case 3:
            moduleHighWorkLoadDistribution.put(module,
                moduleHighWorkLoadDistribution.getOrDefault(module, 0) + 1);
            break;
        default:
            assert false;
        }
    }

    /**
     * Gets workload information of the {@code module}
     *
     * @param module the module to be checked.
     * @return return the workload information of one module.
     */
    public static String getModuleWorkloadInformation(Module module) {
        String moduleWorkLoadInformation = String.format("low workload tasks:%d\n"
                + "medium workload tasks:%d\n"
                + "high workload tasks:%d\n",
            moduleLowWorkLoadDistribution.getOrDefault(module, 0),
            moduleMediumWorkLoadDistribution.getOrDefault(module, 0),
            moduleHighWorkLoadDistribution.getOrDefault(module, 0));
        return moduleWorkLoadInformation;
    }

    /**
     * Deletes a Task from a Module's List of Tasks mapping.
     * If module already exists in the mappings, update the List of Tasks in the mapping.
     * If module does not exist in the mappings, do nothing.
     *
     * @param module Module
     * @param task Task
     */
    public static void deleteTaskFromMapping(Module module, Task task) {
        assert(module != null && task != null);
        assert(mappingOfModulesToTasks.containsKey(module));
        List<Task> newList = mappingOfModulesToTasks.get(module);
        //must ensure Module exists in the listOfValidModules
        newList.remove(task);
        moduleWorkLoadDistribution.put(module,
            moduleWorkLoadDistribution.get(module) - task.getWorkload().getWorkloadLevel());
        if (newList.isEmpty()) { //remove the module(key) from mapping if no task is associated with it
            mappingOfModulesToTasks.remove(module);
        } else {
            mappingOfModulesToTasks.put(module, newList);
        }
        setExistingModuleList();
        setModulePieChartData();
    }

    /**
     * Returns the mapping of Modules to Tasks.
     */
    public static HashMap<Module, List<Task>> getMappingOfModulesToTasks() {
        return mappingOfModulesToTasks;
    }

    /**
     * Discards all current mappings of Modules to Tasks.
     */
    public static void rebuildMapping() {
        mappingOfModulesToTasks = new HashMap<>();
        moduleWorkLoadDistribution = new HashMap<>();
        moduleLowWorkLoadDistribution = new HashMap<>();
        moduleMediumWorkLoadDistribution = new HashMap<>();
        moduleHighWorkLoadDistribution = new HashMap<>();
        moduleList.clear();
        modulePieChartData.clear();
    }

    /**
     * Returns a list of existing Modules.
     */
    public static List<String> getListOfSupportingModules() {
        return supportedModulesInStr;
    }

    /**
     * Initialize list of valid/accepted modules with basic modules.
     *
     * @return List of String associated with basic modules
     */
    private static List<String> initListOfModulesAccepted() {
        List<String> listOfModules = new ArrayList<>(Arrays.asList(arrOfModules));
        return listOfModules;
    }

    /**
     * Fills the observable list of existringModules with the keySet of the mapping.
     */
    public static void setExistingModuleList() {
        List<Module> existingModules = List.copyOf(mappingOfModulesToTasks.keySet());
        moduleList.setAll(existingModules);
    }

    /**
     * Fills the observable list of existringModules with the keySet of the mapping.
     */
    public static void setModulePieChartData() {
        modulePieChartData.clear();
        for (int i = 0; i < moduleList.size(); i++) {
            Module module = moduleList.get(i);
            modulePieChartData.add(new PieChart.Data(module.toString(), moduleWorkLoadDistribution.get(module)));
        }
    }

    /**
     * @return Observable List of existing modules.
     */
    public static ObservableList<Module> getExistingModuleList() {
        return moduleList;
    }

    /**
     * @return Observable List of existing modules data.
     */
    public static ObservableList<PieChart.Data> getModulePieChartData() {
        return modulePieChartData;
    }
}
