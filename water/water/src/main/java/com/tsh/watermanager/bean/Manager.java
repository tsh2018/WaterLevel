package com.tsh.watermanager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Manager {
    public int magId;
    public String magName;
    public String magPassward;
    public String magEmail;
    public String magNum;
    public String magImg;
    public String magRoot;
    public String magContent;
    public String magAddress;
    public String token;
}
