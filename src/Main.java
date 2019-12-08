/*
Alunos: Carlos Henrique Ponicano da Silva
        Vinicius Luis da Silva

Trabalho 02 - Algoritmo de Exclusão Mútua
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    static Integer processesCreated = 0;
    private static Map<Integer, Process> processes;
    
    public static void main(String[] args) {
        Process p = new Process(processesCreated, new HashMap<>());
        Main.processesCreated++;

        new Thread(() -> {
            while (true) {
                Process randomProcess = getRandom();
                randomProcess.orderToCreateProcess();
                Util.sleep(Constants.NEW_PROCESS_TIME);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Util.sleep(Constants.KILL_COORDINATOR);
                getRandom().getCoordinator().kill();
            }
        }).start();

        
    }

    static void updateProcesses(Map<Integer, Process> processes) {
        Main.processes = Util.cloneProcessesMap(processes);
    }

    private static Process getRandom(boolean coordinator) {
        Random r = new Random();
        int randomNumber;
        Process randomProcess;
        do {
            randomNumber = r.nextInt(processes.keySet().size());
            randomProcess = processes.get(randomNumber);
        } while (randomProcess == null || !randomProcess.isActive() || (!coordinator && randomProcess.isCoordinator()));
        return randomProcess;
    }

    private static Process getRandom() {
        return getRandom(true);
    }

}
