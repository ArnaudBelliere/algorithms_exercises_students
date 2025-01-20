package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

    /**
     * The class TaskScheduler allows
     * to declare a set of tasks with their dependencies.
     * You have to implement the method:
     *      boolean isValid(List<String> schedule)
     * allowing to verify if the given schedule does
     * not violate any dependency constraint.
     *
     * Example:
     *
     *         TaskScheduler scheduler = new TaskScheduler();
     *         scheduler.addTask("A", Arrays.asList());
     *         scheduler.addTask("B", Arrays.asList("A"));
     *         scheduler.addTask("C", Arrays.asList("A"));
     *         scheduler.addTask("D", Arrays.asList("B", "C"));
     *
     *    The dependency graph is represented as follows:
     *
     *          │─────► B │
     *        A─│         ├─────►D
     *          │─────► C │
     *
     *
     *         assertTrue(scheduler.isValid(Arrays.asList("A", "B", "C", "D")));
     *         assertFalse(scheduler.isValid(Arrays.asList("A", "D", "C", "B"))); // D cannot be scheduled before B
     *
     *  Feel free to use existing java classes.
     */
    public class TaskScheduler {
        private Map<String, List<String>> graph;


        public TaskScheduler() {
            this.graph = new HashMap<>();
        }

        /**
         * Adds a task with the given dependencies to the scheduler.
         * The task cannot be scheduled until all of its dependencies have been completed.
         */
        public void addTask(String task, List<String> dependencies) {
            this.graph.put(task, dependencies);
        }

        /**
         * Verify if the given schedule is valid, that is it does not violate the dependencies
         * and every task in the graph occurs exactly once in it.
         * The time complexity of the method should be in O(V+E) where
         * V = number of tasks, and E = number of requirements.
         * @param schedule a list of tasks to be scheduled in the order they will be executed.
         */
        public boolean isValid(List<String> schedule) {
            // TODO
            System.out.println("\n New call :");
            System.out.println(" The schedule : " + schedule);
            //System.out.println(schedule.get(0));
            HashMap<String, Integer> visited = new HashMap<>();
            for (String assign : schedule){
                if (visited.containsKey(assign)){
                    return false;
                }
                System.out.println("Current task : "+ assign +", dependencies : " + graph.get(assign));
                for ( String depen : graph.get(assign)){
                    if (!visited.containsKey(depen)){
                        return false;
                    }
                }
                visited.put(assign,1);
            }
            for ( String i : graph.keySet()){
                if(!visited.containsKey(i)){
                    return false;
                }
            }
            return true;
        }


    }