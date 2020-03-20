package com.qyt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.DB.DBWeiBoDoc;
import com.bean.WeiboDoc;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DataMining.DBHander;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class CollectorWeiBo extends BreadthCrawler {

	public String get_uID() {
		return _uID;
	}

	public void set_uID(String _uID) {
		this._uID = _uID;
	}

	public static int getPageIndex() {
		return pageIndex;
	}

	public static void setPageIndex(int pageIndex) {
		CollectorWeiBo.pageIndex = pageIndex;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public static HashMap<String, Integer> getMapUrl() {
		return mapUrl;
	}

	public static void setMapUrl(HashMap<String, Integer> mapUrl) {
		CollectorWeiBo.mapUrl = mapUrl;
	}

	public String getSeedUrl() {
		return seedUrl;
	}

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public String getRootUrl() {
		return RootUrl;
	}

	public void setRootUrl(String rootUrl) {
		RootUrl = rootUrl;
	}

	public String getOutFilePath() {
		return outFilePath;
	}

	public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}

	public FileOutputStream getOut() {
		return out;
	}

	public void setOut(FileOutputStream out) {
		this.out = out;
	}

	public String _uID;
	static int pageIndex;// 爬取的初始页面
	public long sleepTime;
	public int maxPage;
	public static HashMap<String, Integer> mapUrl = new HashMap<String, Integer>(); // 用于记录已经爬取的页面
	public String seedUrl;// 种子页
	public String RootUrl;// 根路径（前缀）
	public String outFilePath;// 爬取内容文件存储路径
	public FileOutputStream out;

	public CollectorWeiBo(String crawlPath, boolean autoParse) throws IOException {
		super(crawlPath, autoParse);
		// 初始化配置
		pageIndex = 1;// 起始页索引
		outFilePath = "E:\\final_stuty\\WEB\\WebCollector\\a.txt";// 爬取内容文件存储路径 from=1087095010&wm=20005_0002&
																	// from=1087095010&wm=20005_0002&

		// 种子页
		// seedUrl =
		// "https://m.weibo.cn/api/container/getIndex?type=uid&value=1774978073&containerid=1076031774978073&page=1";
		// RootUrl =
		// "https://m.weibo.cn/api/container/getIndex?type=uid&value=1774978073&containerid=1076031774978073";

		// maxPage =getMaxPage(seedUrl);//最大页初始化

		// addAllseeds();//添加所有种子

		// this.addSeed(seedUrl);//添加种子
		// 文件读写
//		File file = new File(outFilePath);
//		if (!file.exists())
//			file.createNewFile();
//		try {
//			out = new FileOutputStream(file, true);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // 如果追加方式用true,默认false

	}

	/**
	 * 创建一个全局的List
	 */

	List<WeiboDoc> WeiboList = new ArrayList<WeiboDoc>();

	public List getWeiboList() {
		return WeiboList;
	}

	public void setWeiboList(List weiboList) {
		WeiboList = weiboList;
	}

	public void visit(Page page, CrawlDatums next) {
		String url = page.getUrl();
		String currentPage = null;
		String[] strArray = url.split("page=");
		if (strArray.length > 1)
			currentPage = strArray[1];
		System.out.println("page: " + currentPage + " maxPage: " + maxPage);
		// 有且仅有第一页的json文件中会有最总页数
		// 内容解析
		String text = "";
		// text = paeseByJson(page.getHtml());
		List<WeiboDoc> weiBoDataList = paeseByJson(page.getHtml());
		// 如果未解析到结果，那么就将该页面重新加入待爬取队列
//		if (text == "") {
//			this.addSeed(url);
//			return;
//		}
		if (weiBoDataList.size() < 1) {
			this.addSeed(url);
			return;
		}
		// AllList.add(weiBoDataList);
		// 当前页面爬取成功后
		// 防止重复爬取，对爬过的url做标记
		if (mapUrl.get(url) != null) {
			return;
		} else {
			mapUrl.put(url, 1);

		}
		for (WeiboDoc we : weiBoDataList) {
			WeiboList.add(we);
		}

		// 将爬取的当前页内容写入文件
//		try {
//			out.write(weiBoDataList.toString().getBytes("utf-8"));
//			// System.out.println(text);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// 线程休眠

		try {
			Thread.sleep(this.sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public HttpResponse getResponse(CrawlDatum crawlDatum) {
		HttpRequest request = null;
		try {
			request = new HttpRequest(crawlDatum.getUrl());
			request.setUserAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		;

		// 当前页面请求失败
		HttpResponse response = null;
		try {
			response = request.response();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String faileUrl = crawlDatum.getUrl();
			System.out.println("当前页请求失败，重新添加入队列：" + faileUrl);
			this.addSeed(faileUrl);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * show 获取第一页中微博的总页数
	 */
	int getMaxPage(String rooturl) {
		System.out.println(rooturl);//
		int code = 0;
		int maxPage = 1;
		String jsonStr = getHtmlByUrl(rooturl);
		// System.out.println(jsonStr);
		JsonParser parse = new JsonParser(); // 创建json解析器
		JsonObject jsonObject = (JsonObject) parse.parse(jsonStr);
		String index = jsonObject.get("data").getAsJsonObject().get("cardlistInfo").getAsJsonObject().get("total")
				.getAsString();
		System.out.println(index);
		maxPage = (Integer.parseInt(index) - 1) / 10 + 1;
		System.out.println("最大的页面数：    " + maxPage);
		return maxPage;
	}

	/**
	 * show 将所有页面的种子添加进入队列
	 */
	void addAllseeds() {

		for (pageIndex = 1; pageIndex <= maxPage; pageIndex++) {
			String index = Integer.toString(pageIndex);
			System.out.println("---最大的页数----" + maxPage);
			String nextUrl = RootUrl + "&page=" + index;
			this.addSeed(nextUrl);
		}
	}

	// 判断一个任务
	boolean taskEnd = false;

	/**
	 * qyt魔改 show 将所有页面的种子添加进入队列
	 * 
	 * @param _uID
	 */
	public void addAllseedqyt(String _uID) {
		// List list = new ArrayList<String>();
		// list.add(_uID);
		// list.add("2219794733");
		// list.add("2240223962");
		// for (int i = 0; i < list.size(); i++) {
		seedUrl = "https://m.weibo.cn/api/container/getIndex?type=uid&value=" + _uID + "&containerid=107603" + _uID
				+ "&page=1";
		RootUrl = "https://m.weibo.cn/api/container/getIndex?type=uid&value=" + _uID + "&containerid=107603" + _uID;
		maxPage = getMaxPage(seedUrl);// 最大页初始化
		for (pageIndex = 1; pageIndex <= 2; pageIndex++) {
			String index = Integer.toString(pageIndex);
			System.out.println("---最大的页数----" + maxPage);
			String nextUrl = RootUrl + "&page=" + index;
			this.addSeed(nextUrl);
		}
		// }
		taskEnd = true;
	}

	DBWeiBoDoc db = new DBWeiBoDoc();

	/**
	 * show 解析获取到的json页面
	 * 
	 * @return text 返回的是当前页的微博内容
	 */
	public List<WeiboDoc> paeseByJson(String jsonStr) {
		List list = new ArrayList();
		String text = "";
		// System.out.println(jsonStr);
		JsonParser parse = new JsonParser(); // 创建json解析器
		JsonObject jsonObject = (JsonObject) parse.parse(jsonStr); // 创建jsonObject对象
		JsonArray arrayCards = jsonObject.get("data").getAsJsonObject().get("cards").getAsJsonArray(); // 得到为json的数组
		// JsonArray array =
		// arrayCards.get(0).getAsJsonObject().get("card_group").getAsJsonArray();
//        for(int i=0;i<arrayCards.size();i++){
//            JsonObject subObject=arrayCards.get(i).getAsJsonObject();
//            //对进行了文本省略的条目进行替换。
//            if(subObject.toString().indexOf("...全文")!=-1){
//                subObject = replaceFullContrnt(subObject);
//            }
//            text+=subObject.toString()+"\n";
//        }

		System.out.println(arrayCards.size() + "------------------------------------------------------");
		for (int i = 0; i < arrayCards.size(); i++) {
			JsonObject subObject = (JsonObject) arrayCards.get(i).getAsJsonObject();
			if (subObject != null) {
				// 创建微博文档对象
				WeiboDoc weiboDoc = null;
				if (subObject.has("mblog")) {
					JsonObject json = subObject.get("mblog").getAsJsonObject();
					if (json == null) {
						// 一般第一条不行
						continue;
					} else {
						// 对进行了文本省略的条目进行替换。
						if (json.toString().indexOf("...全文") != -1) {
							json = replaceFullContrnt(json);
						}
						Gson gson = new Gson();
						// 把json转成bean
						weiboDoc = gson.fromJson(json, WeiboDoc.class);
						weiboDoc.setCreatedAt(json.get("created_at").getAsString());
						System.out.println(weiboDoc);
						// 插入数据库里
//						try {
//							//db.insertWeiBoDoc(weiboDoc);
//						} catch (SQLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							System.out.println(e.toString());
//						}
						list.add(weiboDoc);
					}
				}
			}
		}
		// System.out.println(text);
		// System.out.println(list);
		return list;
	}

	/**
	 * show 检测单条微博中是否存在有未显示全文的情况，如果有就进行替换。
	 * 
	 * @param jsonObject 单条博文的json对象
	 */
	JsonObject replaceFullContrnt(JsonObject jsonObject) {
		// 微博正文
		if (jsonObject.get("text").toString().indexOf("...全文") != -1) {
			String id = jsonObject.get("id").toString();
			jsonObject.addProperty("text", getFullTextById(id));
		}
		// 如果有转发
		if (jsonObject.has("retweeted_status")) {
			JsonObject retweetedObject = jsonObject.getAsJsonObject("retweeted_status");
			if (retweetedObject.get("text").toString().indexOf("...全文") != -1) {
				String id = retweetedObject.get("id").toString();
				jsonObject.getAsJsonObject("retweeted_status").addProperty("text", getFullTextById(id));
			}
		}

		return jsonObject;
	}

	/**
	 * show 由于抓取列表页的时候，部分博文只显示部分，而未显示全部，所以通过博文的id获取内容页的所有内容进行替换
	 * 
	 * @param id 单独条博文的id
	 * @return text 博文的全部内容
	 */
	String getFullTextById(String id) {
		/*
		 * "text": "2017年3月2日，龙泉寺藏经办公室贤超法师、贤世法师、贤威法师、贤听法师，龙泉寺人工智能与信息技术中心贤度法师，以及龙泉寺弘宣部贤
		 * 信法师，应邀赴微软研发中心参访。中心相关负责人为法师们介绍了微软在云计算、物联网、大数据和人工智能方面的新技术及产品应用，包括城市空气质量监测
		 * 、智慧楼宇、 ​​​<a href=
		 * \"/single?id=4080915241593181\"<span class=\"surl-text\">...全文</span></a>",
		 */
		// 内容页json
		// url：http://m.weibo.cn/statuses/extend?id=4080915241593181&retcode=6102
		String url = "http://m.weibo.cn/statuses/extend?id=" + id + "&retcode=6102";
		String html = getHtmlByUrl(url);
		JsonParser parse = new JsonParser(); // 创建json解析器
		JsonObject jsonObject = (JsonObject) parse.parse(html); // 创建jsonObject对象
		String text = jsonObject.get("longTextContent").toString(); // 得到为json的数组
		return text;
	}

	/**
	 * show 通过给定的url返回指定的html或者json内容
	 */
	public String getHtmlByUrl(String url) {
		String text = null;
		int code = 0;
		while (code != 200) {
			try {
				StringBuffer html = new StringBuffer();
				URL Url = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
				// 这个很必要，否则就是403
				conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				code = conn.getResponseCode();
				InputStreamReader isr = new InputStreamReader(conn.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String temp;
				while ((temp = br.readLine()) != null) {
					html.append(temp).append("\n");
				}
				br.close();
				isr.close();
				text = html.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				code=0;
			}
		}

		return text;
	}

	/**
	 * 启动方法
	 * 
	 * @param args
	 * @throws IOException
	 * @throws Exception
	 */
	public void startCollectWeiBo(String _uID) throws IOException {
		CollectorWeiBo crawler = new CollectorWeiBo("crawl", true);
		crawler.set_uID(_uID);

		// 执行代码
		crawler.addAllseedqyt(crawler.get_uID());
		crawler.setThreads(1);
		// crawler.executeInterval = 1500;
		crawler.sleepTime = 7000;
		// 测试获取全文方法
		// System.out.println(crawler.getFullText("4080915241593181"));
		// 关闭文件
		try {
			crawler.start(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			crawler.out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		CollectorWeiBo crawler = new CollectorWeiBo("crawl", true);

		crawler.set_uID("");
		crawler.addAllseedqyt(crawler.get_uID());
		crawler.setThreads(1);
		// crawler.executeInterval = 1500;
		crawler.sleepTime = 7000;
		// 测试获取全文方法
		// System.out.println(crawler.getFullText("4080915241593181"));
		// 关闭文件
		crawler.start(100);
		if (crawler.out != null)
			crawler.out.close();

		System.out.println("--------------------------" + crawler.getWeiboList().size());

//		for(WeiboDoc weiboDoc:crawler.getAllList()) {
//			System.out.println(weiboDoc);
//		}
	}

}
