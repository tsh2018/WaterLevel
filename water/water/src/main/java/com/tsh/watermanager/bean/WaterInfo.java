package com.tsh.watermanager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaterInfo {
    public int id;
    public int waterNum;
    public int waterHeight;
    public float waterSpeed;
    public float waterAcceleration;
    public float changeAce;
    public float changeSpeed;
    public Date measureTime;
    public int predictionHeight;
}
