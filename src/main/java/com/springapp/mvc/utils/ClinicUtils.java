package com.springapp.mvc.utils;

import com.springapp.mvc.dto.SearchForm;
import org.springframework.util.StringUtils;

public class ClinicUtils {

    //Checks if SearchForm is empty or not
    public static boolean isEmpty(SearchForm search) {
        if (StringUtils.isEmpty(search.getFirstname()) && StringUtils.isEmpty(search.getLastname())
                && StringUtils.isEmpty(search.getMobile()) && StringUtils.isEmpty(search.getDependent())
                && StringUtils.isEmpty(search.getRefferedBy())) {
            return true;
        }
        return false;
    }
}
