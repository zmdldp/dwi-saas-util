package com.dwi.basic.datasource.plugin.domain.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dwi.basic.base.entity.Entity;
import com.dwi.basic.datasource.plugin.domain.enumeration.TenantConnectTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 数据源
 * </p>
 *
 * @author dwi
 * @since 2020-11-19
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_datasource_config")
@ApiModel(value = "DatasourceConfig", description = "数据源")
@AllArgsConstructor
public class DatasourceConfig extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    @TableField(value = "name", condition = LIKE)
    @Excel(name = "名称")
    private String name;
    
    /**
     * 服务
     */
    @ApiModelProperty(value = "服务")
    @NotEmpty(message = "服务不能为空")
    @Length(max = 100, message = "服务长度不能超过100")
    @TableField(value = "application", condition = LIKE)
    @Excel(name = "服务")
    private String application;
    
    /**
     * 连接类型
     * #TenantConnectTypeEnum{LOCAL:本地;REMOTE:远程}
     */
    @ApiModelProperty(value = "连接类型")
    @TableField("connect_type")
    @Excel(name = "连接类型", width = 20, replace = {"本地_LOCAL", "远程_REMOTE", "_null"})
    private TenantConnectTypeEnum connectType;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @NotEmpty(message = "账号不能为空")
    @Length(max = 255, message = "账号长度不能超过255")
    @TableField(value = "username", condition = LIKE)
    @Excel(name = "账号")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    @Length(max = 255, message = "密码长度不能超过255")
    @TableField(value = "password", condition = LIKE)
    @Excel(name = "密码")
    private String password;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    @NotEmpty(message = "链接不能为空")
    @Length(max = 255, message = "链接长度不能超过255")
    @TableField(value = "url", condition = LIKE)
    @Excel(name = "链接")
    private String url;

    /**
     * 驱动
     */
    @ApiModelProperty(value = "驱动")
    @NotEmpty(message = "驱动不能为空")
    @Length(max = 255, message = "驱动长度不能超过255")
    @TableField(value = "driver_class_name", condition = LIKE)
    @Excel(name = "驱动")
    private String driverClassName;

    @ApiModelProperty(value = "数据源名")
    @TableField(exist = false)
    private String poolName;

    @Builder
    public DatasourceConfig(Long id, LocalDateTime createTime, Long createdBy, LocalDateTime updateTime, Long updatedBy,
                            String name, String application, TenantConnectTypeEnum connectType, String username, String password, String url, String driverClassName) {
        this.id = id;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.updateTime = updateTime;
        this.updatedBy = updatedBy;
        this.name = name;
        this.application = application;
        this.connectType = connectType;
        this.username = username;
        this.password = password;
        this.url = url;
        this.driverClassName = driverClassName;
    }

}
