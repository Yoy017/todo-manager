package ch.heigvd.dai.util;

public enum Status {
    PENDING("En attente"),          // Tâche en attente
    IN_PROGRESS("En cours"),        // Tâche en cours
    DONE("Terminé");                // Tâche terminée

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}