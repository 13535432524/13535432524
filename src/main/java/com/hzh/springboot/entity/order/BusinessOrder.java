package com.hzh.springboot.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 
 * @TableName business_order
 */
@Alias("BusinessOrder")
@TableName(value ="business_order")
@Data
public class BusinessOrder implements Serializable {
    /**
     * 
     */
    @TableId(value = "orderId")
    private String orderid;

    /**
     * 
     */
    @TableField(value = "code")
    private String code;

    /**
     * 
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 
     */
    @TableField(value = "update_date")
    private Date updateDate;

    /**
     * 
     */
    @TableField(value = "organizationId")
    private String organizationid;

    /**
     * 
     */
    @TableField(value = "follower")
    private String follower;

    /**
     * 
     */
    @TableField(value = "finish_num")
    private Integer finishNum;

    /**
     * 
     */
    @TableField(value = "cutbed_num")
    private Integer cutbedNum;

    /**
     * 
     */
    @TableField(value = "deliver_num")
    private Integer deliverNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BusinessOrder other = (BusinessOrder) that;
        return (this.getOrderid() == null ? other.getOrderid() == null : this.getOrderid().equals(other.getOrderid()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getOrganizationid() == null ? other.getOrganizationid() == null : this.getOrganizationid().equals(other.getOrganizationid()))
            && (this.getFollower() == null ? other.getFollower() == null : this.getFollower().equals(other.getFollower()))
            && (this.getFinishNum() == null ? other.getFinishNum() == null : this.getFinishNum().equals(other.getFinishNum()))
            && (this.getCutbedNum() == null ? other.getCutbedNum() == null : this.getCutbedNum().equals(other.getCutbedNum()))
            && (this.getDeliverNum() == null ? other.getDeliverNum() == null : this.getDeliverNum().equals(other.getDeliverNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderid() == null) ? 0 : getOrderid().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getOrganizationid() == null) ? 0 : getOrganizationid().hashCode());
        result = prime * result + ((getFollower() == null) ? 0 : getFollower().hashCode());
        result = prime * result + ((getFinishNum() == null) ? 0 : getFinishNum().hashCode());
        result = prime * result + ((getCutbedNum() == null) ? 0 : getCutbedNum().hashCode());
        result = prime * result + ((getDeliverNum() == null) ? 0 : getDeliverNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderid=").append(orderid);
        sb.append(", code=").append(code);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", organizationid=").append(organizationid);
        sb.append(", follower=").append(follower);
        sb.append(", finishNum=").append(finishNum);
        sb.append(", cutbedNum=").append(cutbedNum);
        sb.append(", deliverNum=").append(deliverNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}