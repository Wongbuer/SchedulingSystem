package com.wong.ffwb.scheduling_system.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PreferenceContent implements Serializable {
    @Serial
    private static final long serialVersionUID = -1157579516668582618L;

    private String workingTimePreference;
    private String workingHoursPreference;
    private String workingDayPreference;
}
