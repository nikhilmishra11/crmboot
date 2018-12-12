package com.nam.crm.web.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

public class PNJUtill {

	 public static String generateCompalinId() {
		  Random rnd = new Random(System.currentTimeMillis());
		  String num=String.valueOf(rnd.nextInt(900) + 5);
		  Timestamp stamp = new Timestamp(System.currentTimeMillis());
		  Date date = new Date(stamp.getTime());
		  DateFormat formatter = new SimpleDateFormat("YYYYMMdd");
		  String str=(formatter.format(date));
		  String complain_id="SR"+str+num; // SR20170912
		  return complain_id;
	 }
	 
	 public static String generateCustomerId() {
		  Random rnd = new Random(System.currentTimeMillis());
		  String num=String.valueOf(rnd.nextInt(90000) + 5);
		  Timestamp stamp = new Timestamp(System.currentTimeMillis());
		  Date date = new Date(stamp.getTime());
		  DateFormat formatter = new SimpleDateFormat("YYYYMMdd");
		  String str=(formatter.format(date));
		  String customer_id="MS"+str+"P"+num; // MS20170925675O
		  return customer_id;
	 }
	 
	 public static int generatedId() {
		  Random rnd = new Random(System.currentTimeMillis());
		  int i= (rnd.nextInt(90000) + 5);
		  return i;
	 }
	 
	 public static String replaceLastFour(String s) {
	     /*int length = s.length();
	     if (length < 4) return "Error: The provided string is not greater than four characters long.";
	     return s.substring(0, length - 4) + "********";*/
		 String mask = s.replaceAll("\\w(?=\\w{4})", "*");
		 return mask;
		 
     }
	 
	 public static String maskEmail(String email) {
		 email= email.replaceAll("(?<=.{3}).(?=.*@)", "*");
		 return email;
	 }
	 
	 public static String generateUUId() {
		 UUID uniqueKey = UUID.randomUUID();
		 return uniqueKey.toString();
	 }
	 
	 public static String removeLeftWhiteSpace(String str) {
		 return str.replaceAll("^\\s+", "");
	 }
	
}
