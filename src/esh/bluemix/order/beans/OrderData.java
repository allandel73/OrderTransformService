package esh.bluemix.order.beans;

public class OrderData {

	 private String _id = "";
	 private String _rev = null;
	 private String orderNum = "";
	 
	 public OrderData(String masterOrderNumber) {
		orderNum = masterOrderNumber;
		_id = masterOrderNumber;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String toString() {
	   return "{ id: " + _id + ",\nrev: " + _rev + ",\norderNum " + orderNum + "\n}";
	}
	
}
