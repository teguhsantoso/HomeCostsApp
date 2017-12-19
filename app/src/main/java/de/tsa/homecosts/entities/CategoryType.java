package de.tsa.homecosts.entities;

public enum CategoryType {

    INCOME {
        @Override
        public String toString() {
            return "Einkommen";
        }
    },
    OUTCOME {
        @Override
        public String toString() {
            return "Ausgabe";
        }
    }

}
