package com.springapp.mvc.utils;

import com.springapp.mvc.dto.SearchForm;

public class ClinicUtils {

    //Checks if SearchForm is empty or not
    public static boolean isEmpty(SearchForm search) {
        if (search.getFirstname().isEmpty() && search.getLastname().isEmpty() && search.getMobile().isEmpty() && search.getDependent().isEmpty()
                && search.getRefferedBy().isEmpty()) {
            return true;
        }
        return false;
    }
}
