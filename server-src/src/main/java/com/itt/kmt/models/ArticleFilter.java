package com.itt.kmt.models;

/**
 *  Enum value to specify the valid filters.
 * @author ashish.y
 *
 */
public enum ArticleFilter {
    /** valid values of article filter. **/
    ASSIGNED("approver"), CREATED("createdBy");
    
    /** parameter to set the value.  **/
    private final String name;

    /** 
     * sets the value of Article filter.
     * @param name , name to be set for article filter.
     */
    private ArticleFilter(final String name) {
        this.name = name;
    }

    /** 
     * override toString method.
     * @return name , returns the name. 
     */
    @Override
    public String toString() {
        return name;
    }
}
