package com.ss.training.utopia.agent.entity;

public class FlightQuery {
    private String departId;
    private String arriveId;
    private String dateBegin;
    private String dateEnd;
    private Float price;

	public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getArriveId() {
        return arriveId;
    }

    public void setArriveId(String arriveId) {
        this.arriveId = arriveId;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public FlightQuery(String departId, String arriveId, String dateBegin, String dateEnd, Float price) {
        this.departId = departId;
        this.arriveId = arriveId;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.price = price;
    }
    
}