package com.jpsycn.jixiao.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jpsycn.jixiao.bean.LogBean;

public class SysUtils {


	/**
	 * 获取cookies
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> login(String username,
			String password) throws IOException {

		Map<String, String> map = new HashMap<String, String>();
		map.put("LogName", username);
		map.put("path", Constants.DOMIN_URL);
		map.put("username", username);
		map.put("oldpass", password);
		map.put("savecheck", "2");
		map.put("userpass", MD5.getMD5(password.getBytes()));

		Response res = Jsoup.connect(Constants.LOGIN_URL).data(map)
				.method(Method.POST).execute();

		Map<String, String> cookies = res.cookies();

		return cookies;
	}

	/**
	 * 获取 部门目标体系 列表
	 * 
	 * @param cookies
	 * @param bbsId
	 *            部门id 软件开发 999710
	 * @throws IOException
	 */
	public static SortedMap<Integer, String> getBBSList(
			Map<String, String> cookies, String bbsId) throws IOException {

		Map<String, String> para = new HashMap<String, String>();
		para.put("bbsId", bbsId);
		Document doc = Jsoup.connect(Constants.BBS_LIST_URL).timeout(1000*10).data(para)
				.cookies(cookies).get();

		Elements elements1 = doc.select("tr[class=tab_tr01]");
		Elements elements2 = doc.select("tr[class=tab_tr02]");

		Iterator<Element> iterator1 = elements1.iterator();

		SortedMap<Integer, String> map = new TreeMap<Integer, String>();

		while (iterator1.hasNext()) {
			String[] split = iterator1.next().text().split(" ");
			map.put(Integer.valueOf(split[0]), split[1]);
		}

		Iterator<Element> iterator2 = elements2.iterator();
		while (iterator2.hasNext()) {
			String[] split = iterator2.next().text().split(" ");
			map.put(Integer.valueOf(split[0]), split[1]);
		}
		return map;
	}

	/**
	 * 查看某一个目标体系的详细信息
	 * 
	 * @param cookies
	 * @param bbsId
	 *            部门id
	 * @param bbsDetailId
	 *            帖子id
	 * @return
	 * @throws IOException
	 */
	public static List<String> getBBSDetail(Map<String, String> cookies,
			String bbsId, String bbsDetailId) throws IOException {
		Map<String, String> para = new HashMap<String, String>();
		para.put("bbsId", bbsId);
		para.put("id", bbsDetailId);
		Document doc = Jsoup.connect(Constants.BBS_DETAIL).data(para)
				.cookies(cookies).get();

		Elements elements = doc.select("td[class=bbs_content]");
		Iterator<Element> iterator = elements.iterator();
		List<String> list = new ArrayList<String>();
		while (iterator.hasNext()) {
			list.add(iterator.next().text());
		}
		return list;
	}

	public static void addReply(Map<String, String> cookies, String bbsId,
			String id, String content) throws IOException {

		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("bbsId", bbsId);
		map.put("reply.content", content);

		Jsoup.connect(Constants.REPLY_URL).data(map).cookies(cookies).execute();

	}

	public static void addLog(Map<String,String> cookies,String time,String content) throws IOException {
		
		Document doc = Jsoup.connect(Constants.NOTE_TOKEN_URL).cookies(cookies).get();
		
		Elements elements = doc.select("input[name=struts.token]");
		Iterator<Element> iterator = elements.iterator();

		String token="";
		if (iterator.hasNext()) {
			token=iterator.next().val();
		}
		

		Map<String, String> map = new HashMap<String, String>();

		map.put("enote.stime", time);
		map.put("enote.content", content);
		map.put("struts.token.name", "struts.token");
		map.put("struts.token", token);

		Jsoup.connect(Constants.ADD_NOTE_URL).data(map).cookies(cookies).execute();
	}
	
	/**
	 * 获取日志列表
	 * @param cookies
	 * @param year
	 * @param month
	 */
	public static void getLogBeanList(Map<String, String> cookies,String year,String month) {
		try {

			Map<String, String> para = new HashMap<String, String>();
			para.put("y", year);
			para.put("m", month);

			Document doc = Jsoup.connect(Constants.LOG_LIST).data(para)
					.cookies(cookies).get();

			List<String> dates = JsoupUtil.parse(doc, "td[nowrap=nowrap]");

			List<String> contents = JsoupUtil.parse(doc,"td[style=padding:5px 8px 5px 8px;]");

			List<LogBean> beans = covertToLogBean(dates, contents);

			System.out.println(beans);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<LogBean> covertToLogBean(List<String> dates,
			List<String> contents) {

		List<LogBean> list = new ArrayList<LogBean>();
		LogBean b = null;
		String temp = null;
		for (int i = 0; i < contents.size(); i++) {
			b = new LogBean();
			b.setContent(contents.get(i));
			temp = dates.get(i);
			String[] split = temp.split(" ");

			b.setWriteTime(DateUtil.strToDate((split[0] + " " + split[1]).substring(5)));
			b.setDate(DateUtil.strToDate2(split[2].substring(6, 16)));
			b.setStatus(split[3].substring(5, 7));
			list.add(b);
		}
		return list;
	}
}
