package com.wyl.self.entity;


import java.io.Serializable;
import java.util.List;

public class PageInfo implements Serializable {
    private static final long serialVersionUID = -6333015871784412687L;

    public PageInfo() {
        //默认为第一页，每页显示15条
        page = 1;
        rp = 15;
    }

    /**
     * 当前页
     */
    private int page = -1;
    /**
     * 总记录数
     */
    private int total;
    /**
     * 每页显示几条数据
     */
    private int rp;
    /**
     * 是否为可用的页--
     */
    private boolean usePager;
    /**
     * 查询返回记录数
     */
    private List rows;
    /**
     * 最大查询记录数
     */
    private int rowLimit = -1;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public boolean isUsePager() {
        return usePager;
    }

    public void setUsePager(boolean usePager) {
        this.usePager = usePager;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(int rowLimit) {
        this.rowLimit = rowLimit;
    }
}

