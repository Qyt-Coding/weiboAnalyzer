package DataAnalyzer;

public class dataAnalyzer {
	public void mainAnalyzer(){
		//初始化数据库
		Global.dBer.connectDB();
		zplKMeans kMeans;
		//把
		kMeans = new zplKMeans(20);
		kMeans.readVectorSet();
		kMeans.initRandom();
		kMeans.start();
		kMeans.showAll();
		Global.dBer.closeDB();
	}
}
