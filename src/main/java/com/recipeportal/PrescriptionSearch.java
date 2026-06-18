package com.recipeportal;

import java.util.List;

public interface PrescriptionSearch {

    List<Prescription> search(SearchCriteria criteria);
}
