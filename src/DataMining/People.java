package DataMining;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.collation.tokenattributes.CollatedTermAttributeImpl;

import com.bean.WeiboDoc;
import com.qyt.CollectorWeiBo;
import com.qyt.CollectorWeiBoClose;
import com.util.Utilqyt;

import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

public class People {
	/**
	 * 人物
	 */
	private String _uID;

	private Friendships fm;
	private Timeline tm;

	public People(String ID) {
		this._uID = ID;
	}

	public String getID() {
		return _uID;
	}

	// 得到朋友列表，并加入人物队列
	public boolean getFriends(PeopleQueue queue) throws Exception {
		//fm = new Friendships(Global.access_token);
		// fm.client.setToken(Global.access_token);
		try {
			//UserWapper users = fm.getFriendsByID(_uID);
			CollectorWeiBoClose crawler=new CollectorWeiBoClose("crawl", true);
			crawler.set_uID(_uID);
			crawler.addAllseedqyt(crawler.get_uID());
			crawler.setThreads(1);
			// crawler.executeInterval = 1500;
			crawler.sleepTime = 7000;
			// 测试获取全文方法
			// System.out.println(crawler.getFullText("4080915241593181"));
			// 关闭文件
			crawler.start(100);
			/**
			 * 上面那段代码是获得全部关注的用户信息
			 */
			
			if(crawler.out!=null)
			crawler.out.close();
			
			 
			List<User> users=crawler.getWeiboList();
			for (User u : users) {
				int flag = 0;
				for (String t : Global.usedIDList) {
					if (t.equals(u.getId())) {
						flag = 1;
					}
				}
				if (flag == 0) {
					// 朋友入队
					People tmpPeople = new People(u.getId());
					//就是把People放到list里
					if (queue.putIn(tmpPeople) == false) {
						// 记录断点
						throw new WeiboException("putinQueue Error!");
					}
				}

			}
		} catch (WeiboException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return true;
	}

	public boolean getDoc() throws Exception {
		tm = new Timeline(Global.access_token);
		// tm.client.setToken(Global.access_token);
		Global.usedIDList.add(_uID);
		try {
			// StatusWapper status = tm.getUserTimeline();
		//	StatusWapper status = tm.getUserTimelineByUid(_uID);
			CollectorWeiBo weiboData = new CollectorWeiBo("crawl", true);
			//启动这个程序，需要设置uid
			//启动
			weiboData.set_uID(_uID);
			weiboData.addAllseedqyt(weiboData.get_uID());
			weiboData.setThreads(1);
			// crawler.executeInterval = 1500;
			weiboData.sleepTime = 7000;
			// 测试获取全文方法
			// System.out.println(crawler.getFullText("4080915241593181"));
			// 关闭文件
			weiboData.start(100);
			
			if(weiboData.out!=null)
				weiboData.out.close();
			//接收数据
			List<WeiboDoc> weiboList = weiboData.getWeiboList();
			//因为我爬数据，只循环了两次，所以这里最多有20条数据。
			for (WeiboDoc s : weiboList) {

				String conString;
				if (s.getRetweetedStatus() == null)
					conString = s.getText();
				else {
					conString = (s.getRetweetedStatus().getText());
				}
				conString=Utilqyt.replaceAllD(conString);
				conString=Utilqyt.emo(conString);
				s.setText(conString);
				if (Global.markNoMean(conString) == false) {
					// 加入文档词典		这里就是把文档添加到数据库里
					if (Global.dBer.addDocqyt(s) == false)
						throw new WeiboException("addDoc wrong");
					// 进行分词
					System.out.println("doc:" + conString);
					if (s.getRetweetedStatus() == null)
						Global.split.setDoc(s.getText());
					else {
						Global.split.setDoc(s.getRetweetedStatus().getText());
					}
					//这里才是真正的分词。
					Global.split.beginSplit();
				}
			}
//			for (Status s : status.getStatuses()) {
//				String conString;
//				if (s.getRetweetedStatus() == null)
//					conString = s.getText();
//				else {
//					conString = (s.getRetweetedStatus().getText());
//				}
//				if (Global.markNoMean(conString) == false) {
//					// 加入文档词典
//					if (Global.dBer.addDoc(s) == false)
//						throw new WeiboException("addDoc wrong");
//					// 进行分词
//					System.out.println("doc:" + conString);
//					if (s.getRetweetedStatus() == null)
//						Global.split.setDoc(s.getText());
//					else {
//						Global.split.setDoc(s.getRetweetedStatus().getText());
//					}
//					Global.split.beginSplit();
//				}
		//	}
		} catch (WeiboException e) {
			e.printStackTrace();
			System.out.println("Error:" + e.getMessage());
			return false;
		}
		return true;
	}
}