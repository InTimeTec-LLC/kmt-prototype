package com.itt.utility;

import org.springframework.core.convert.converter.Converter;

import com.itt.kmt.models.ArticleFilter;

/** handle the default value of enum to be null. **/
public class ArticleFilterConverter implements Converter<String, ArticleFilter> {
    /** override the default defination of convert.
     * @param source , the source string.
     * @return ArticleFilter , returns article filter value.
     *  */
    @Override
    public ArticleFilter convert(final String source) {
        try {
            return ArticleFilter.valueOf(source);
        } catch (Exception e) {
            return null;
        }

    }
}
