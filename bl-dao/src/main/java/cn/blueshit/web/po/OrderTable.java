package cn.blueshit.web.po;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class OrderTable {

    private int id;
    private long orderId;
    private String descs;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDescs() {
        return descs;
    }

    public void setDesc(String descs) {
        this.descs = descs;
    }

    @Override
    public String toString() {
        return "OrderTable{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", desc='" + descs + '\'' +
                '}';
    }
}
