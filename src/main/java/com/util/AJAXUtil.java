package com.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.serializer.PropertyFilter;

public class AJAXUtil {
  /**
   * 输出响应到客户端
   * */
 public static void printString(HttpServletResponse response,String s){
	 response.setCharacterEncoding("utf-8");
	 try {
		PrintWriter out=response.getWriter();
		out.print(s);
		out.flush();
		out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
 /**
  * 过滤属性
  * */
 public static PropertyFilter filterPropertys(final String...propNames){
	 PropertyFilter propertyFilter=new PropertyFilter() {
		
		@Override
		public boolean apply(Object arg0, String propName, Object arg2) {
			for (String pname :propNames) {
				if(propName.equals(pname)){
					return false;//过滤此属性
				}
			}
			return true;
		}
	};
	 
	 
	return propertyFilter;
}
}
