package de.tsa.homecosts.entities;

public enum CategoryType {

    INCOME {
        @Override
        public String toString() {
            return "Income";
        }
    },
    OUTCOME {
        @Override
        public String toString() {
            return "Expenditure";
        }
    }

}
