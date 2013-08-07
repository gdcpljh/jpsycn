package com.jpsycn.jixiao.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupUtil {

	/**
	 * 解析html网页，根据提供的选择条件，查找符合提交的内容
	 * @param doc
	 * @param pattern
	 * @return
	 */
	public static List<String> parse(Document doc, String pattern) {
		List<String> list = new ArrayList<String>();

		Elements elements2 = doc.select(pattern);

		Iterator<Element> it = elements2.iterator();
		while (it.hasNext()) {
			Element type = (Element) it.next();
			list.add(type.text());
		}
		return list;
	}
}
