package com.example.sony.banteriorprototype.data.Survey;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class SurveyData implements Serializable{
    public int questionary_id;
    public String questionary;
    public List<SurveyItem> item;
}
