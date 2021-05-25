package com.einvoicemerchant.utils.convert;

import java.util.*;

public class MapConverter {
	   /**
		 * Map转换成url参数
		 *
		 * @param map
		 * @param isSort
		 *            是否排序
		 * @param removeKey
		 *            不包含的key元素集
		 * @return
		 */
		public static String getOrderedQueryStr(Map<String, Object> map, boolean isSort, Set<String> removeKey) {
			StringBuffer param = new StringBuffer();
			List<String> msgList = new ArrayList<String>();
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				Object value = map.get(key);
				if (removeKey != null && removeKey.contains(key)) {
					continue;
				}
				msgList.add(key + "=" + value);
			}

			if (isSort) {
				// 排序
				Collections.sort(msgList);
			}

			for (int i = 0; i < msgList.size(); i++) {
				String msg = (String) msgList.get(i);
				if (i > 0) {
					param.append("&");
				}
				param.append(msg);
			}

			return param.toString();
		}

}

