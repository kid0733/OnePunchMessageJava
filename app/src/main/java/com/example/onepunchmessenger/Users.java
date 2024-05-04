package com.example.onepunchmessenger;

public class Users {

    public Users(){
        profileImg = "";
        mail = "";
        username = "";
        password = "";
        userId = "";
        lastMessage = "";
        status = "";
    }

    //init variables
    String profileImg,mail,username,password,userId,lastMessage,status;

    //getters and setters for the values in Firebase
    public Users(String userId, String username, String mail, String password, String profileImg, String status){
        this.userId=userId;
        this.username=username;
        this.mail=mail;
        this.password=password;
        this.profileImg=profileImg;
        this.status=status;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
