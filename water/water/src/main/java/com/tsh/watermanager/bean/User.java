package com.tsh.watermanager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    public int userId;
    public String userName;
    public String userPassward;
    public   String userEmail;
    public   String userNum;
    public   String userImg;
    public String userContent;
    public String token;
}
