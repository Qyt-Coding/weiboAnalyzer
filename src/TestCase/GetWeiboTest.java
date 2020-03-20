package TestCase;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import DataMining.dataMining;

public class GetWeiboTest {

		//case1 测试数据获取模块
		@Test
		public void testMining() throws Exception {
			dataMining testdata = new dataMining();
			testdata.mainDataMining();
		}

}
