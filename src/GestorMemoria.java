import java.util.ArrayList;
import java.util.List;

public class GestorMemoria {

    // Capacidad total de memoria simulada (en MB o unidades)
    private final int memoriaTotal = 1024;

    // Cantidad de memoria disponible actualmente
    private int memoriaDisponible = memoriaTotal;

    // Lista de procesos que se están ejecutando
    private final List<Proceso> enEjecucion = new ArrayList<>();

    // Lista de procesos que están esperando memoria para poder ejecutarse
    private final List<Proceso> enEspera = new ArrayList<>();

    // Lista de procesos que ya han finalizado
    private final List<Proceso> finalizados = new ArrayList<>();

    // --- MÉTODOS GETTER ---
    // Retorna la memoria total
    public int getMemoriaTotal() {
        return memoriaTotal;
    }

    // Retorna la memoria disponible
    public int getMemoriaDisponible() {
        return memoriaDisponible;
    }

    // Retorna la lista de procesos en ejecución
    public List<Proceso> getEnEjecucion() {
        return enEjecucion;
    }

    // Retorna la lista de procesos en espera
    public List<Proceso> getEnEspera() {
        return enEspera;
    }

    // Retorna la lista de procesos finalizados
    public List<Proceso> getFinalizados() {
        return finalizados;
    }

    /**
     * Agrega un nuevo proceso al sistema.
     * Si hay memoria suficiente, lo ejecuta directamente.
     * Si no hay memoria suficiente, lo envía a la cola de espera.
     * 
     * @param p Proceso a agregar
     * @param actualizarUI Acción para actualizar la interfaz de usuario
     */
    public void agregarProceso(Proceso p, Runnable actualizarUI) {
        // Si el proceso cabe en la memoria disponible, lo ejecuta
        if (p.getMemoria() <= memoriaDisponible) {
            ejecutarProceso(p, actualizarUI);
        } 
        // Si no hay memoria suficiente, se coloca en espera
        else {
            enEspera.add(p);
            actualizarUI.run(); // Actualiza la interfaz para reflejar el cambio
        }
    }

    /**
     * Inicia la ejecución de un proceso, reduciendo la memoria disponible
     * y simulando su tiempo de ejecución con un hilo.
     */
    private void ejecutarProceso(Proceso p, Runnable actualizarUI) {
        // Agrega el proceso a la lista de ejecución
        enEjecucion.add(p);

        // Resta la memoria que consume el proceso
        memoriaDisponible -= p.getMemoria();

        // Actualiza la interfaz gráfica
        actualizarUI.run();

        // Crea un nuevo hilo para simular el tiempo de ejecución del proceso
        new Thread(() -> {
            try {
                // Simula el tiempo de ejecución en segundos
                Thread.sleep(p.getDuracion() * 1000L);
            } catch (InterruptedException e) {
                System.err.println("Error en proceso: " + e.getMessage());
            }
            // Al terminar, libera la memoria y pasa el proceso a finalizados
            finalizarProceso(p, actualizarUI);
        }).start();
    }

    /**
     * Finaliza un proceso, liberando memoria y verificando si
     * hay procesos en espera que ahora pueden ejecutarse.
     */
    private void finalizarProceso(Proceso p, Runnable actualizarUI) {
        // Quita el proceso de la lista de ejecución
        enEjecucion.remove(p);

        // Libera la memoria usada por el proceso
        memoriaDisponible += p.getMemoria();

        // Mueve el proceso a la lista de finalizados
        finalizados.add(p);

        // Si hay procesos en espera, intenta ejecutarlos
        if (!enEspera.isEmpty()) {
            // Copia la lista para evitar problemas de concurrencia
            List<Proceso> pendientes = new ArrayList<>(enEspera);
            for (Proceso proc : pendientes) {
                // Si el proceso en espera cabe en memoria, se ejecuta
                if (proc.getMemoria() <= memoriaDisponible) {
                    enEspera.remove(proc);
                    ejecutarProceso(proc, actualizarUI);
                }
            }
        }
        // Actualiza la interfaz tras la finalización
        actualizarUI.run();
    }
}
