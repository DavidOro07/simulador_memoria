public class Proceso {

    // Variable estática que lleva el conteo único de IDs para procesos
    private static int contadorId = 1;

    // Identificador único de este proceso (PID)
    private final int pid;

    // Nombre descriptivo del proceso
    private final String nombre;

    // Memoria que consume el proceso (en megabytes)
    private final int memoria;

    // Duración que simula el tiempo que dura el proceso (en segundos)
    private final int duracion;

    /**
     * Constructor de la clase Proceso.
     * Inicializa el ID único, nombre, memoria y duración.
     * Si no se proporciona un nombre válido, asigna un nombre por defecto "Proceso-[pid]".
     * 
     * @param nombre Nombre del proceso (puede ser null o vacío)
     * @param memoria Memoria que consume el proceso
     * @param duracion Duración en segundos del proceso
     */
    public Proceso(String nombre, int memoria, int duracion) {
        this.pid = contadorId++; // Asigna un ID único y aumenta el contador

        // Si el nombre es null o cadena vacía, asigna un nombre por defecto con el PID
        this.nombre = (nombre == null || nombre.isEmpty()) ? "Proceso-" + pid : nombre;

        this.memoria = memoria;     // Asigna la memoria
        this.duracion = duracion;   // Asigna la duración
    }

    // --- MÉTODOS GETTER ---

    /** Devuelve la memoria que consume el proceso */
    public int getMemoria() {
        return memoria;
    }

    /** Devuelve la duración en segundos del proceso */
    public int getDuracion() {
        return duracion;
    }

    /** Devuelve el nombre del proceso */
    public String getNombre() {
        return nombre;
    }

    /** Devuelve el identificador único (PID) del proceso */
    public int getPid() {
        return pid;
    }

    /**
     * Método sobrescrito para representar el proceso como String.
     * Muestra el formato: [pid] nombre - memoriaMB - duracionSegundos
     * 
     * Ejemplo: [3] Proceso-3 - 100MB - 5s
     */
    @Override
    public String toString() {
        return String.format("[%d] %s - %dMB - %ds", pid, nombre, memoria, duracion);
    }
}
