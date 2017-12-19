package de.tsa.homecosts.entities;

/**
 * Enumeration class for device types.
 *
 * @author AppPlusMobile Systemhaus GmbH 2017, all rights reserved, any use is prohibited.
 * @since 1.1
 *
 */
public enum CategoryType {

    INCOME {
        @Override
        public String toString() {
            return "EINKOMMEN";
        }
    },
    OUTCOME {
        @Override
        public String toString() {
            return "AUSGABE";
        }
    }
}
