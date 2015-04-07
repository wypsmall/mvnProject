package com.neo.test.annotation;

public class User {
	@ValidAnnotation(description="用户名",minLength=6,maxLength=32,nullable=false)
    private String userName;
     
    private String password;
     
    @ValidAnnotation(description="邮件地址",nullable=false,regexType=RegexType.EMAIL)
    private String email;
     
     
    public User(){}
     
    public User(String userName, String password, String email) {
        super();
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
     
     
     
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
