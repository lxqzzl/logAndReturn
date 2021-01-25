package com.test.ge.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * TestDO实体类
 *
 * @author lxq
 */

@Data
@Accessors(chain = true)
@TableName("user_test")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="TestDO对象model", description="TestDO原始数据类型，与表中字段一一对应")
public class TestDO implements Serializable {

    private static final long serialVersionUID = 8351748135027597729L;

    /**
     *  主键id(数据库自增)
     */
    @ApiModelProperty(value = "主键id(数据库自增)")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     *  uuid（后台定义）
     */
    @ApiModelProperty(value = "uuid（后台定义）")
    @TableField("uuid")
    private String uuid;
    /**
     *  创建时间(时间戳)，格式为YY-MM-DD hh:mm:ss
     */
    @ApiModelProperty(value = "创建时间(时间戳)，格式为YY-MM-DD hh:mm:ss")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;
    /**
     *  创建人id（系统默认为0）
     */
    @ApiModelProperty(value = "创建人id（系统默认为0）")
    @JSONField(serialize = false)
    @TableField("gmt_create_id")
    private Long gmtCreateId;
    /**
     *  最近修改时间(时间戳)，格式为YY-MM-DD hh:mm:ss
     */
    @ApiModelProperty(value = "最近修改时间(时间戳)，格式为YY-MM-DD hh:mm:ss")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(serialize = false)
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
    /**
     *  修改人id（系统默认为0）
     */
    @ApiModelProperty(value = "修改人id（系统默认为0）")
    @JSONField(serialize = false)
    @TableField("gmt_modified_id")
    private Long gmtModifiedId;

    /**
     *  是否已删除
     */
    @ApiModelProperty(value = "是否已删除")
    @TableField("delete_flag")
    private Boolean deleteFlag;

    /**
     *  是否启用
     */
    @ApiModelProperty(value = "是否启用")
    @TableField("enable")
    private Boolean enable;

    /**
     *  备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    /**
     *  预留字段
     */
    @ApiModelProperty(value = "预留字段")
    @JSONField(serialize = false)
    @TableField("spare")
    private String spare;

    /**
     *  名称
     */
    @ApiModelProperty(value = "名称")
    @TableField("test_name")
    private String testName;
    /**
     *  年龄
     */
    @ApiModelProperty(value = "年龄")
    @TableField("test_age")
    @Min(value = 1, message = "年龄必须为大于1的纯数字")
    private Integer testAge;
    /**
     *  性别
     */
    @ApiModelProperty(value = "性别")
    @TableField("test_gender")
    private Integer testGender;

}
