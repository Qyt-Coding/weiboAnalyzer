package com.qyt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.util.Utilqyt;

import DataMining.DBHander;

public class testMain2 {

	public static void main(String args[]) {
//		for (int j = 0; j < 10; j++) {
//			for (int i = 0; i < 100; i++) {
//				System.out.println(i + "----");
//				if (i == 50) {
//					System.out.println("break" + j + "   i---" + i);
//					break;
//				}
//			}
//		}

	}
	
	
	@Test
	public  void  test1() {
	//	double  d=Math.log10(10);
	//	System.out.println(d);
	}
	
	
	
	@Test
	public  void test2() {
//		String  str="//<a href='/n/me同學'>@me同學</a>://<a href='/n/鱼蛋大人'>@鱼蛋大人</a>:<span class=\"url-icon\"><img alt=[话筒] src=\"//h5.sinaimg.cn/m/emoticon/icon/others/o_huatong-a3c5f9bcc2.png\" style=\"width:1em; height:1em;\" /></span>//\r\n" + 
//				"<a href='/n/酒心蜜桃酥'>@酒心蜜桃酥</a>:转发微博";
//		System.out.println(str);
//		System.out.println("|---------------------------------");
//		str=Utilqyt.replaceAllD(str);
//		System.out.println(str);
	}
	
	@Test
	public  void test3() {
		//DBHander db=new DBHander();
		//db.connectDB();
		//db.addLexicon("我",4,1);
	}
	
	@Test
	public  void test4() {
//		String  str= "";
//		String  str1="我是中国人😂";
//		String  str2=Utilqyt.emo(str1);
//		System.out.println(str2);
	}
	
	
	@Test
	public  void  test5() {
//		String  url="https://m.weibo.cn/api/container/getIndex?containerid=231051_-_followers_-_5210843684";
//		String text = null;
//		int code = 0;
//		try {
//			while (code != 200) {
//				StringBuffer html = new StringBuffer();
//				URL Url = new URL(url);
//				HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
//				// 这个很必要，否则就是403
//				conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//				code = conn.getResponseCode();
//				InputStreamReader isr = new InputStreamReader(conn.getInputStream());
//				BufferedReader br = new BufferedReader(isr);
//				String temp;
//				while ((temp = br.readLine()) != null) {
//					html.append(temp).append("\n");
//				}
//				br.close();
//				isr.close();
//				text = html.toString();
//			}
//			
//			
//			System.out.println(text);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	@Test
	public  void test6() {
		String  str="<a href='xxx'>  xxx<qq>";
		
	}
}
