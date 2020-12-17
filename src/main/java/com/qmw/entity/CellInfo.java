package com.qmw.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * excel表格信息
 * create 2020-12-14
 *
 * @author qmw
 */
@Data
@Accessors(chain = true)
public class CellInfo {
    private String value;
    private float height;
    private float width;
    private int rowspan = 1; // 行扩展
    private int colspan = 1; // 列扩展
    private boolean exists = true; // 在合并行列的时候用于判断该单元格是否存在
}
