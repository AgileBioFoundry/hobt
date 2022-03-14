package org.abf.hobt.dto;

/**
 * Represents the type of attribute created for organism
 */
public enum AttributeType {
    TEXT_INPUT,         // single input box from user
    MULTI_CHOICE,       // user selects from pre-defined options
    MULTI_CHOICE_PLUS   // user selects from pre-defined options or enters their own value
}
