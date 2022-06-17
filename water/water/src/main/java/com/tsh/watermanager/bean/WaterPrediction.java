package com.tsh.watermanager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaterPrediction {
    public int waterNum;
    public int id;
    public Time timeFirst;
    public Time timeSecond;
    public Time timeThrid;
    public Time timeForth;
    public int typeStaId;
    public int waterPreLevel;
}
