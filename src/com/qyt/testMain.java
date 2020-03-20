package com.qyt;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bean.WeiboDoc;

import DataMining.DBHander;
import DataMining.Doc;
import DataMining.Global;
import DataMining.splitSystem;
import DataMining.splitSystem.wordCount;
import weibo4j.model.User;

public class testMain {

	public static void main(String args[]) throws Exception {
		CollectorWeiBo weiboData = new CollectorWeiBo("crawl", true);
		// 启动这个程序，需要设置uid
		// 启动
		weiboData.set_uID("5960445128");
		weiboData.addAllseedqyt(weiboData.get_uID());
		weiboData.setThreads(1);
		// crawler.executeInterval = 1500;
		weiboData.sleepTime = 7000;
		// 测试获取全文方法
		// System.out.println(crawler.getFullText("4080915241593181"));
		// 关闭文件
		weiboData.start(100);

		if (weiboData.out != null)
			weiboData.out.close();
		// 接收数据
		List<WeiboDoc> weiboList = weiboData.getWeiboList();
		for (WeiboDoc weibo : weiboList) {
			System.out.println(weibo);
		}
	}

	@Test
	public void test1() {
		DBHander dd = new DBHander();
		dd.connectDB();

		splitSystem sys = new splitSystem();
		// wordCount w=new wordCount();
		// w.begin_analyzer(doc);
		sys.beginSplit();
	}

	/**
	 * 测试，db的这个方法能不能插入数据
	 */

	@Test
	public void test2() {
		DBHander db = new DBHander();
		db.connectDB();
		WeiboDoc doc = new WeiboDoc();
		doc.setId("247");
		doc.setText(
				"隔着屏幕都能感觉到心痛，愿一切都安好。ߙߙߙ<span class=\"url-icon\"><img alt=[作揖] src=\"//h5.sinaimg.cn/m/emoticon/icon/others/h_zuoyi-cb12e18fd5.png\" style=\"width:1em; height:1em;\" /></span><span class=\"url-icon\"><img alt=[作揖] src=\"//h5.sinaimg.cn/m/emoticon/icon/others/h_zuoyi-cb12e18fd5.png\" style=\"width:1em; height:1em;\" /></span><span class=\"url-icon\"><img alt=[作揖] src=\"//h5.sinaimg.cn/m/emoticon/icon/others/h_zuoyi-cb12e18fd5.png\" style=\"width:1em; height:1em;\" /></span>");
		db.addDocqyt(doc);
	}

	/**
	 * 测试ik分词器
	 */
	@Test
	public void test3() {
		Doc doc = new Doc();
		doc.setCon("噗噗噗噗@@@@我是一个学生，你不是，他也不是@@@@！！！我你他");
		
		Analyzer analyzer = new IKAnalyzer(true);
		StringReader reader = new StringReader(doc.getDocCon());
		List<String> tmpList = new ArrayList<String>();
		// 获取Lucene的TokenStream对象
		TokenStream ts = null;
		try {
			ts = analyzer.tokenStream("myfield", reader);
			// 获取词元文本属性
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			// 重置TokenStream（重置StringReader）
			ts.reset();
			// 迭代获取分词结果,加入到tmplist中待处理
			while (ts.incrementToken()) {
				if (Global.markNolex(term.toString()) == false) {
					tmpList.add(term.toString());
				}
			}
			
			
			for(String  str:tmpList) {
				System.out.println(str);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
