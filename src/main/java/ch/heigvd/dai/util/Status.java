package ch.heigvd.dai.util;

public enum Status {
    PENDING("En attente"),          // Tâche en attente
    IN_PROGRESS("En cours"),        // Tâche en cours
    DONE("Terminé");               // Tâche terminée

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    // Méthode pour retrouver une valeur d'énumération à partir du label
    public static Status fromLabel(String label) {
        for (Status status : Status.values()) {
            if (status.getLabel().equalsIgnoreCase(label)) {
                return status;
            }
        }
        return Status.IN_PROGRESS;
    }
}