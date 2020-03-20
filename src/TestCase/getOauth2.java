package TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

public class getOauth2 {
	public static void main(String[] args) throws WeiboException, IOException {
		Oauth oauth = new Oauth();
		BareBonesBrowserLaunch.openURL(oauth.authorize("code","1"));
		System.out.println(oauth.authorize("code","1"));
		System.out.print("Hit enter when it's done.[Enter]:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();
		try {
			System.out.println(oauth.getAccessTokenByCode(code));
		} catch (WeiboException e) {
			if (401 == e.getStatusCode()) {
			} else {
				e.printStackTrace();
			}
		}
	}
}
