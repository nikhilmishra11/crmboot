package com.nam.crm.model.jpa;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="pnj_complain_master")
public class PNJComplainMaster implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3114888248857069007L;

	@Id
	@Column(name="id", unique=true, nullable=false)
	private String id;
	
	@Column(name="customer_id", unique=true, nullable=false)
	private String customer_id;
	
	@Column(name="complain_id", unique=true, nullable=false)
	private String complain_id;
	
	@Column(name="complain_type", unique=true, nullable=false)
	private String complain_type;
	
	@Column(name="complain_status", unique=true, nullable=false)
	private String complain_status;
	
/*	@Column(name="complain_register_date",  nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp complain_register_date;
	
	@Column(name="complain_closing_date",  nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp complain_closing_date;

    @Column(name="payment_date",  nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp payment_date;
	
*
*/	
	@Column(name="complain_register_date", unique=true, nullable=true)
	private String complain_register_date;
	
	@Column(name="complain_closing_date", unique=true, nullable=true)
	private String complain_closing_date;
	
	@Column(name="payment_date", unique=true, nullable=true)
	private String payment_date;
	
	@Column(name="complain_remarks", unique=true, nullable=false)
	private String complain_remarks;
	
	@Column(name="assign_technician", unique=true, nullable=false)
	private String assign_technician;
	
	@Column(name="complain_resolver_remarks", unique=true, nullable=false)
	private String complain_resolver_remarks;
	
	@Column(name="payment_amount", nullable=false)
	private double payment_amount;
	
	@Column(name="payment_status", nullable=false)
	private String payment_status;
	
	@Column(name="service_offered", nullable=false)
	private String service_offered;
	
	@Column(name="payment_mode", nullable=false)
	private String payment_mode;
	
	@Column(name="payment_remarks", nullable=false)
	private String payment_remarks;
	
	@Column(name="payment_account_number", nullable=false)
	private String payment_account_number;
	
	@Column(name="customer_feedback")
	private String customer_feedback;
	
	@Column(name="details_filled_by", nullable=false)
	private String details_filled_by;
	
	@Column(name="name_on_card", nullable=false)
	private String name_on_card;
	
	@Column(name="card_number", nullable=false)
	private String card_number;
	
	@Column(name="card_exp_date", nullable=false)
	private String card_exp_date;
	
	@Column(name="card_cvv", nullable=false)
	private String card_cvv;
	
	@Column(name="e_chq_name", nullable=false)
	private String e_chq_name;
	
	@Column(name="e_chq_account", nullable=false)
	private String e_chq_account;
	
	@Column(name="e_chq_routing_no", nullable=false)
	private String e_chq_routing_no;
	
	@Column(name="e_chq_no", nullable=false)
	private String e_chq_no;
	
	@Column(name="p_chq_name", nullable=false)
	private String p_chq_name;
	
	@Column(name="p_chq_account", nullable=false)
	private String p_chq_account;
	
	@Column(name="p_chq_routing_no", nullable=false)
	private String p_chq_routing_no;
	
	@Column(name="p_chq_no", nullable=false)
	private String p_chq_no;
	
	@Column(name="online_bill_pay", nullable=false)
	private String online_bill_pay;
	
	@Column(name="money_order", nullable=false)
	private String money_order;
	
	@Column(name="other_payment_details", nullable=false)
	private String other_payment_details;
	
	@Column(name="admin_remark", nullable=false)
	private String admin_remark;
	
	@Column(name="feedbk")
	private String feedbk;
	
	@Column(name="user_type")
	private String user_type;
	
	@Column(name="customer_name", nullable=false)
	private String customer_name;
	
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private Set<PNJCustomerDetailsMaster> pnjCustomerDetailsMasters;
	
	public Set<PNJCustomerDetailsMaster> getPnjCustomerDetailsMasters() {
		return pnjCustomerDetailsMasters;
	}

	public void setPnjCustomerDetailsMasters(Set<PNJCustomerDetailsMaster> pnjCustomerDetailsMasters) {
		this.pnjCustomerDetailsMasters = pnjCustomerDetailsMasters;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getFeedbk() {
		return feedbk;
	}

	public void setFeedbk(String feedbk) {
		this.feedbk = feedbk;
	}

	public String getAdmin_remark() {
		return admin_remark;
	}

	public void setAdmin_remark(String admin_remark) {
		this.admin_remark = admin_remark;
	}

	public String getName_on_card() {
		return name_on_card;
	}

	public void setName_on_card(String name_on_card) {
		this.name_on_card = name_on_card;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getCard_exp_date() {
		return card_exp_date;
	}

	public void setCard_exp_date(String card_exp_date) {
		this.card_exp_date = card_exp_date;
	}

	public String getCard_cvv() {
		return card_cvv;
	}

	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}

	public String getE_chq_name() {
		return e_chq_name;
	}

	public void setE_chq_name(String e_chq_name) {
		this.e_chq_name = e_chq_name;
	}

	public String getE_chq_account() {
		return e_chq_account;
	}

	public void setE_chq_account(String e_chq_account) {
		this.e_chq_account = e_chq_account;
	}

	public String getE_chq_routing_no() {
		return e_chq_routing_no;
	}

	public void setE_chq_routing_no(String e_chq_routing_no) {
		this.e_chq_routing_no = e_chq_routing_no;
	}

	public String getE_chq_no() {
		return e_chq_no;
	}

	public void setE_chq_no(String e_chq_no) {
		this.e_chq_no = e_chq_no;
	}

	public String getP_chq_name() {
		return p_chq_name;
	}

	public void setP_chq_name(String p_chq_name) {
		this.p_chq_name = p_chq_name;
	}

	public String getP_chq_account() {
		return p_chq_account;
	}

	public void setP_chq_account(String p_chq_account) {
		this.p_chq_account = p_chq_account;
	}

	public String getP_chq_routing_no() {
		return p_chq_routing_no;
	}

	public void setP_chq_routing_no(String p_chq_routing_no) {
		this.p_chq_routing_no = p_chq_routing_no;
	}

	public String getP_chq_no() {
		return p_chq_no;
	}

	public void setP_chq_no(String p_chq_no) {
		this.p_chq_no = p_chq_no;
	}

	public String getOnline_bill_pay() {
		return online_bill_pay;
	}

	public void setOnline_bill_pay(String online_bill_pay) {
		this.online_bill_pay = online_bill_pay;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getOther_payment_details() {
		return other_payment_details;
	}

	public void setOther_payment_details(String other_payment_details) {
		this.other_payment_details = other_payment_details;
	}

	public String getDetails_filled_by() {
		return details_filled_by;
	}

	public void setDetails_filled_by(String details_filled_by) {
		this.details_filled_by = details_filled_by;
	}
	
	public String feedBack_Test(String customer_feedback,String insert_Date) {
		return customer_feedback+" :: FEEDBACK @"+insert_Date+" ,";
	}
	
	public String getCustomer_feedback() {
		return customer_feedback;
	}

	public void setCustomer_feedback(String customer_feedback) {
		this.customer_feedback = customer_feedback;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getComplain_id() {
		return complain_id;
	}

	public void setComplain_id(String complain_id) {
		this.complain_id = complain_id;
	}

	public String getComplain_type() {
		return complain_type;
	}

	public void setComplain_type(String complain_type) {
		this.complain_type = complain_type;
	}

	public String getComplain_status() {
		return complain_status;
	}

	public void setComplain_status(String complain_status) {
		this.complain_status = complain_status;
	}

	public String getComplain_register_date() {
		return complain_register_date;
	}

	public void setComplain_register_date(String complain_register_date) {
		this.complain_register_date = complain_register_date;
	}

	public String getComplain_closing_date() {
		return complain_closing_date;
	}

	public void setComplain_closing_date(String complain_closing_date) {
		this.complain_closing_date = complain_closing_date;
	}

	public String getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}

	public String getComplain_remarks() {
		return complain_remarks;
	}

	public void setComplain_remarks(String complain_remarks) {
		this.complain_remarks = complain_remarks;
	}

	public String getAssign_technician() {
		return assign_technician;
	}

	public void setAssign_technician(String assign_technician) {
		this.assign_technician = assign_technician;
	}

	public String getComplain_resolver_remarks() {
		return complain_resolver_remarks;
	}

	public void setComplain_resolver_remarks(String complain_resolver_remarks) {
		this.complain_resolver_remarks = complain_resolver_remarks;
	}

	public double getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(double payment_amount) {
		this.payment_amount = payment_amount;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public String getService_offered() {
		return service_offered;
	}

	public void setService_offered(String service_offered) {
		this.service_offered = service_offered;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public String getPayment_remarks() {
		return payment_remarks;
	}

	public void setPayment_remarks(String payment_remarks) {
		this.payment_remarks = payment_remarks;
	}

	public String getPayment_account_number() {
		return payment_account_number;
	}

	public void setPayment_account_number(String payment_account_number) {
		this.payment_account_number = payment_account_number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PNJComplainMaster))
			return false;
		PNJComplainMaster other = (PNJComplainMaster) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String toString() {
		return complain_id+", "+customer_id+", "+complain_remarks+", "+complain_status +" "+payment_mode
				+" FeedBk ::"+feedbk +","+" C-FeeBk :: "+customer_feedback;
	}
	
	

}
