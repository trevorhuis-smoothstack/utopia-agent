package com.ss.training.utopia.agent.entity;

import java.io.Serializable;

public class FlightQuery implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4716972120664582013L;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((arriveId == null) ? 0 : arriveId.hashCode());
        result = prime * result + ((dateBegin == null) ? 0 : dateBegin.hashCode());
        result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
        result = prime * result + ((departId == null) ? 0 : departId.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FlightQuery other = (FlightQuery) obj;
        if (arriveId == null) {
            if (other.arriveId != null)
                return false;
        } else if (!arriveId.equals(other.arriveId))
            return false;
        if (dateBegin == null) {
            if (other.dateBegin != null)
                return false;
        } else if (!dateBegin.equals(other.dateBegin))
            return false;
        if (dateEnd == null) {
            if (other.dateEnd != null)
                return false;
        } else if (!dateEnd.equals(other.dateEnd))
            return false;
        if (departId == null) {
            if (other.departId != null)
                return false;
        } else if (!departId.equals(other.departId))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        return true;
    }
    
}