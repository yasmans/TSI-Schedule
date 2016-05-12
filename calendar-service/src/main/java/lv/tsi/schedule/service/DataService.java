package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.ReferenceData;

import java.util.List;
import java.util.Map;

public interface DataService {
    Map<String,List<ReferenceData>> getReferenceData(String lang, String[] types);
}
