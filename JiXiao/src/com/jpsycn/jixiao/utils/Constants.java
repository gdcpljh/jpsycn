package com.jpsycn.jixiao.utils;

public class Constants {

	public static final String DOMIN_URL = "http://oa.jpsycn.com:8080/jxgl/";
	public static final String LOGIN_URL = DOMIN_URL
			+ "access/login.action?GRSN=9154169";

	public static final String NOTE_TOKEN_URL = DOMIN_URL
			+ "log/log_toadd_note.action";

	public static final String ADD_NOTE_URL = DOMIN_URL
			+ "log/log_doadd_note.action";

	public static final String REPLY_URL = DOMIN_URL
			+ "targetSystem/addReply.action";

	public static final String BBS_LIST_URL = DOMIN_URL
			+ "/targetSystem/listTopic.action";

	public static final String BBS_TYPE_URL = DOMIN_URL
			+ "targetSystem/bbstype_toFirstPage2.action";

	public static final String BBS_DETAIL = DOMIN_URL
			+ "targetSystem/showBBS.action";

	public static final String TOKEN_REGEX = "\\<input.*?name\\s*=\\s*\"?struts.token\"?\\s+value=\"?(.*?)\"?\\s*/>";
	public static final String BBS_LIST_REGEX = "\\<a\\s*href\\s*=\\s*\"\\s*http://oa.jpsycn.com:8080/jxgl//targetSystem/showBBS.action\\?id=(\\d+)&bbsId=999710\"><font\\s*color='.*'>(.*)</font>";

}
