package com.thinkgem.jeesite.modules.mobile.entity.Mobile;

/**
 * @author 刘智科
 * @Title: $file_name
 * @Package $package_name
 * @Description: $todo
 */
public class Data {
    private int state;//点状态1:down 2:move 3:up
    private int x; //x
    private int y; //y
    private int color; //笔颜色
    private float penwidth; //笔宽度
    private int intype; //输入类型


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPenwidth() {
        return penwidth;
    }

    public void setPenwidth(float penwidth) {
        this.penwidth = penwidth;
    }

    public int getIntype() {
        return intype;
    }

    public void setIntype(int intype) {
        this.intype = intype;
    }
}
