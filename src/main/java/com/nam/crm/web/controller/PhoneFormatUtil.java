package com.nam.crm.web.controller;

public final class PhoneFormatUtil {
	
	 public static String phoneFormat(String s) {
	  // String mask = s.replaceAll("\\w(?=\\w{4})", "*");
		 String mask = s.replace("-","").replaceAll("\\w(?=\\w{4})", "*");
		 return mask;
     }
	 
	 public static String emailFormat(String email) {
		 email= email.replaceAll("(?<=.{2}).(?=.*@)", "*");
		 return email;
	 }
	
/*    static String exStr;
    public static java.lang.String phoneFormat(java.lang.String str)  {

    //    MaskFormatter mf;
        try {
            mf = new MaskFormatter("###-###-####");
            mf.setValueContainsLiteralCharacters(false);
            exStr = mf.valueToString(str);
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }
        return exStr;
    }*/
}