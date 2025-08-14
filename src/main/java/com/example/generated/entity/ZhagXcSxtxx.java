package com.example.generated.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * 摄像头基本信息 实体类
 * 
 * @author QuickCode Generator
 * @date 2025-08-14 22:19:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZhagXcSxtxx {
    
    /**
     * ID
     */
    private String id;
    
    /**
     * 编号
     */
    private String bh;
    
    /**
     * 名称
     */
    private String mc;
    
    /**
     * 部门名称
     */
    private String bmmc;
    
    /**
     * 部门编号
     */
    private String bmbh;
    
    /**
     * 场所名称
     */
    private String csmc;
    
    /**
     * 摄像头编码
     */
    private String sxtbh;
    
    /**
     * 摄像头所属目录编码
     */
    private String sxtssmubm;
    
    /**
     * 逻辑删除标志 1:正常 0:删除
     */
    private java.math.BigDecimal jlbz;
    
    /**
     * 排序
     */
    private java.math.BigDecimal sort;
    
    /**
     * 类型 01:办案区 02:接处警大厅 03:案管区 04:其他
     */
    private String lx;
    
}