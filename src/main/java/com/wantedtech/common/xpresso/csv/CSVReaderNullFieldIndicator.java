package com.wantedtech.common.xpresso.csv;

/**
 * <p>
 * Enumeration used to tell the CSVParser what to consider null.
 * </p>
 * <p>
 * EMPTY_SEPARATORS - two sequential separators are null.
 * EMPTY_QUOTES - two sequential quotes are null
 * BOTH - both are null
 * NEITHER - default.  Both are considered empty string.
 * </p>
 */
public enum CSVReaderNullFieldIndicator {
    EMPTY_SEPARATORS,
    EMPTY_QUOTES,
    BOTH,
    NEITHER;
}
