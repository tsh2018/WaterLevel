package com.tsh.watermanager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlarmWater {
    public int alarmLevel;
    public int alarmHigh;
    public int alarmTime;
    public int alarmSum;
}
