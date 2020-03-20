package DataMining;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bean.WeiboDoc;

import weibo4j.model.Status;

public class DBHander {

	private int docCount; // 文档的总数 初始化的时候就给他们赋值了
	private int lexCount; // lex表示分词后总数
	private Connection conn;
	private Statement statement;
	private PreparedStatement ptmt;

	/**
	 * 数据库处理者
	 */
	public DBHander() {
		docCount = 0;
		lexCount = 0;
		conn = null;
		statement = null;
	}

	public String convertCharset(String old) {
		try {
			return new String(old.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Connection getCon() {
		return conn;
	}

	public boolean initUsedList() {
		String cmdString = "select useid from usedlist;";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			while (resultSet.next()) {
				String id = resultSet.getString("useid");
				Global.usedIDList.add(id);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	public boolean insertUsedList(String id) {
		String cmdString = "select * from usedlist where useid='" + id + "';";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			if (resultSet.next()) {
				// 如果存在这条数据，那么不走下面的步骤
				return true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cmdString = "insert into usedlist(useid) values('" + id + "');";
		statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(cmdString);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	public boolean connectDB() {
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/zplweibo?useUnicode=true&characterEncoding=utf8";
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "123456";
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				System.out.println("Succeeded connecting to the Database!");
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public void initValue() {
		// statement.executeUpdate(cmdString);
		try {
			statement = conn.createStatement();
			ResultSet set = null;
			String cmdString = "select count(*) from doc";

			set = statement.executeQuery(cmdString);
			if (set != null) {
				int a = 0;
				if (set.next())
					a = set.getInt(1);
				docCount = a;

				a = 0;
				cmdString = "select count(*) from lexcion";
				set = statement.executeQuery(cmdString);
				if (set.next())
					a = set.getInt(1);
				lexCount = a;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getDocCount() {
		return docCount;
	}

	public boolean closeDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean addDoc(String s, int i) {
//更新数据库

		String cmdString = "insert into doc(docid,DocText) values(" + i + ",'" + convertCharset(s) + "');";
		try {
			statement = conn.createStatement();
			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			// e.printStackTrace();

		}
		return true;
	}

	public boolean addDoc(Status aDocCon) {
		Doc tmpDoc = new Doc();
		statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		docCount++;
		tmpDoc.setDocID(docCount);

		if (aDocCon.getRetweetedStatus() == null)
			tmpDoc.setCon(aDocCon.getText());
		else {
			tmpDoc.setCon(aDocCon.getRetweetedStatus().getText());
		}

		// 更新数据库
		String cmdString = "insert into doc(DocID,DocText) values(" + tmpDoc.getDocID() + ",'"
				+ convertCharset(tmpDoc.getDocCon()) + "');";
		try {

			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			docCount--;
			return true;

		}
		Global.docList.add(tmpDoc);
		return true;
	}

	/**
	 * qyt 新增方法
	 * 
	 * @param aLexicon
	 * @param tf
	 * @param docID
	 * @return
	 */
	public boolean addDocqyt(WeiboDoc weiboDoc) {
		//Doc tmpDoc = new Doc();
		statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		docCount++;
		// 这个是id，自动增加用的
		//tmpDoc.setDocID(docCount);
		//aDocCon.setId(docCount);
		// 转发的那个地方
		//if (aDocCon.getRetweetedStatus() == null)
			//tmpDoc.setCon(aDocCon.getText());
	//	else {
			//tmpDoc.setCon(aDocCon.getRetweetedStatus().getText());
		//}

		// 更新数据库
		String cmdString = "insert into doc(DocID,DocText,authorId,authorName) values(" +  docCount + ","
				+ "'"
				+ convertCharset(weiboDoc.getText()) + "',"+weiboDoc.getUser().getId()+","+weiboDoc.getUser().getScreen_name()+");";
		try {

			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("----------------出错了");
			System.out.println(e.toString());
			System.out.println("cmdString-->"+cmdString);
			docCount--;
			return true;

		}
		Global.docList.add(tmpDoc);
		return true;
	}

	// word 单词 tf 这个单词出现的次数 docID为文档的ID
	public boolean addLexicon(String word, int tf, int docID) {

		String cmdString;
		statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		Liexicon s = null;
		// 判断是否是旧单词
		s = IslexExist(word);
		if (s != null) {
			try {
				//开启事务
				conn.setAutoCommit(false);
				s.updatedocF();
				cmdString = "update lexcion set idf=" + s.getIDF() + ",docf=" + s.getDocF() + " where lexiconID="
						+ s.getID() + ";";

				statement.executeUpdate(cmdString);
				cmdString = null;
				// 更新倒排列表
				cmdString = "insert into postingitem(lexiconID,docid,tf) values(" + s.getID() + "," + docID + "," + tf+");";
				statement.executeUpdate(cmdString);
				cmdString = null;
			} catch (SQLException e1) {
				e1.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}finally {
				try {
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else {
			// 新的单词
			lexCount++;
			Liexicon sLiexicon = new Liexicon(lexCount, word, 0);// lexCount这个是，自动增长的，
			// 加入词典
			cmdString = "insert into lexcion(lexiconID,IDF,lexicon,docF) values(" + sLiexicon.getID() + ","
					+ sLiexicon.getIDF() + ",'" + convertCharset(sLiexicon.getLexicon()) + "'," + sLiexicon.getDocF()
					+ ");";
			try {
				statement.executeUpdate(cmdString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			cmdString = null;
			// 建立倒排列表

			cmdString = "insert into postingfile(lexiconID) values(" + lexCount + ");";
			try {
				statement.executeUpdate(cmdString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			cmdString = null;
			cmdString = "insert into postingitem(lexiconID,docid,tf) values(" + sLiexicon.getID() + "," + docID + ","
					+ tf + ");";
			try {
				statement.executeUpdate(cmdString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return true;

	}

	public Liexicon IslexExist(int lexID) {
		Liexicon sLiexicon = null;
		String cmdString = "select * from lexcion where lexID=" + lexID + ";";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			while (resultSet.next()) {
				double idf = Double.parseDouble(resultSet.getString("idf"));
				String lexString = resultSet.getString("lexicon");
				int docF = resultSet.getInt("docF");
				sLiexicon = new Liexicon(lexID, lexString, docF);
				sLiexicon.setIDF(idf);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sLiexicon;
	}

	public Liexicon IslexExist(String word) {
		Liexicon sLiexicon = null;
		String cmdString = "select * from lexcion where lexicon='" + convertCharset(word) + "';";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			while (resultSet.next()) {
				double idf = Double.parseDouble(resultSet.getString("idf"));
				int lexID = resultSet.getInt("lexiconID");
				int docF = resultSet.getInt("docF");// 单词出现次数
				sLiexicon = new Liexicon(lexID, word, docF);
				sLiexicon.setIDF(idf);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sLiexicon;
	}

}