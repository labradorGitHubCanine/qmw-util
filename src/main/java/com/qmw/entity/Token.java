package com.qmw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Token {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String content;
    private Timestamp updateTime;
    private Timestamp createTime;

}
