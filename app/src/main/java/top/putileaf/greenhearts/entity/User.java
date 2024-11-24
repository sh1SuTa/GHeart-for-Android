package top.putileaf.greenhearts.entity;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

    private Integer id;//主键ID

    private String username;//用户名


    private String password;//密码

    private String nickname;//昵称

    private String email;//邮箱

    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private Integer vip;//判断是否成员
}
