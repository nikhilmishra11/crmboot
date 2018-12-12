package com.nam.crm.model.jpa;


public class ComplainMasterObject {
	
	/**
	 * Customer Details
	 */
	private String customer_id;
	private String customer_name;
	private String customer_email_id;
	private String customer_contact_no;
	private String customer_contact_no_alternative;
	private String customer_address;
	private String customer_created_on;
	private String customer_remarks;
	private String best_time_to_reach;
	private String no_sale;
	private String details_filled_by;
	/**
	 * Complain Details
	 */
	private String complain_id;
	private String complain_type;
	private String complain_status;
	private String complain_register_date;
	private String complain_closing_date;
	private String payment_date;
	private String complain_remarks;
	private String assign_technician;
	private String complain_resolver_remarks;
	private double payment_amount;
	private String payment_status;
	private String service_offered;
	private String payment_mode;
	private String payment_remarks;
	private String payment_account_number;
	private String customer_feedback;
	private String name_on_card;
	private String card_number;
	private String card_exp_date;
	private String card_cvv;
	private String e_chq_name;
	private String e_chq_account;
	private String e_chq_routing_no;
	private String e_chq_no;
	private String p_chq_name;
	private String p_chq_account;
	private String p_chq_routing_no;
	private String p_chq_no;
	private String online_bill_pay;
	private String money_order;
	private String other_payment_details;
	private String admin_remark;
	private String feedbk;

	
	public ComplainMasterObject(String customer_id, String customer_name, String customer_email_id,
			String customer_contact_no, String customer_contact_no_alternative, String customer_address,
			String customer_created_on, String customer_remarks, String best_time_to_reach, String no_sale,
			String details_filled_by, String complain_id, String complain_type, String complain_status,
			String complain_register_date, String complain_closing_date, String payment_date, String complain_remarks,
			String assign_technician, String complain_resolver_remarks, double payment_amount, String payment_status,
			String service_offered, String payment_mode, String payment_remarks, String payment_account_number,
			String customer_feedback, String name_on_card, String card_number, String card_exp_date, String card_cvv,
			String e_chq_name, String e_chq_account, String e_chq_routing_no, String e_chq_no, String p_chq_name,
			String p_chq_account, String p_chq_routing_no, String p_chq_no, String online_bill_pay, String money_order,
			String other_payment_details, String admin_remark, String feedbk) {
		
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.customer_email_id = customer_email_id;
		this.customer_contact_no = customer_contact_no;
		this.customer_contact_no_alternative = customer_contact_no_alternative;
		this.customer_address = customer_address;
		this.customer_created_on = customer_created_on;
		this.customer_remarks = customer_remarks;
		this.best_time_to_reach = best_time_to_reach;
		this.no_sale = no_sale;
		this.details_filled_by = details_filled_by;
		this.complain_id = complain_id;
		this.complain_type = complain_type;
		this.complain_status = complain_status;
		this.complain_register_date = complain_register_date;
		this.complain_closing_date = complain_closing_date;
		this.payment_date = payment_date;
		this.complain_remarks = complain_remarks;
		this.assign_technician = assign_technician;
		this.complain_resolver_remarks = complain_resolver_remarks;
		this.payment_amount = payment_amount;
		this.payment_status = payment_status;
		this.service_offered = service_offered;
		this.payment_mode = payment_mode;
		this.payment_remarks = payment_remarks;
		this.payment_account_number = payment_account_number;
		this.customer_feedback = customer_feedback;
		this.name_on_card = name_on_card;
		this.card_number = card_number;
		this.card_exp_date = card_exp_date;
		this.card_cvv = card_cvv;
		this.e_chq_name = e_chq_name;
		this.e_chq_account = e_chq_account;
		this.e_chq_routing_no = e_chq_routing_no;
		this.e_chq_no = e_chq_no;
		this.p_chq_name = p_chq_name;
		this.p_chq_account = p_chq_account;
		this.p_chq_routing_no = p_chq_routing_no;
		this.p_chq_no = p_chq_no;
		this.online_bill_pay = online_bill_pay;
		this.money_order = money_order;
		this.other_payment_details = other_payment_details;
		this.admin_remark = admin_remark;
		this.feedbk = feedbk;
	}
	
	public ComplainMasterObject() {
		super();
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_email_id() {
		return customer_email_id;
	}

	public void setCustomer_email_id(String customer_email_id) {
		this.customer_email_id = customer_email_id;
	}

	public String getCustomer_contact_no() {
		return customer_contact_no;
	}

	public void setCustomer_contact_no(String customer_contact_no) {
		this.customer_contact_no = customer_contact_no;
	}

	public String getCustomer_contact_no_alternative() {
		return customer_contact_no_alternative;
	}

	public void setCustomer_contact_no_alternative(String customer_contact_no_alternative) {
		this.customer_contact_no_alternative = customer_contact_no_alternative;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_created_on() {
		return customer_created_on;
	}

	public void setCustomer_created_on(String customer_created_on) {
		this.customer_created_on = customer_created_on;
	}

	public String getCustomer_remarks() {
		return customer_remarks;
	}

	public void setCustomer_remarks(String customer_remarks) {
		this.customer_remarks = customer_remarks;
	}

	public String getBest_time_to_reach() {
		return best_time_to_reach;
	}

	public void setBest_time_to_reach(String best_time_to_reach) {
		this.best_time_to_reach = best_time_to_reach;
	}

	public String getNo_sale() {
		return no_sale;
	}

	public void setNo_sale(String no_sale) {
		this.no_sale = no_sale;
	}

	public String getDetails_filled_by() {
		return details_filled_by;
	}

	public void setDetails_filled_by(String details_filled_by) {
		this.details_filled_by = details_filled_by;
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

	public String getCustomer_feedback() {
		return customer_feedback;
	}

	public void setCustomer_feedback(String customer_feedback) {
		this.customer_feedback = customer_feedback;
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

	public String getAdmin_remark() {
		return admin_remark;
	}

	public void setAdmin_remark(String admin_remark) {
		this.admin_remark = admin_remark;
	}

	public String getFeedbk() {
		return feedbk;
	}

	public void setFeedbk(String feedbk) {
		this.feedbk = feedbk;
	}

	public String toString() {
		return customer_name+","+complain_id+","+complain_remarks+","+payment_amount+","+payment_mode+","+payment_date;
	}
	

}
