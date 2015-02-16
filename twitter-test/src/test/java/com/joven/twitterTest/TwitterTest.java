package com.joven.twitterTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterTest {

	private final String CONSUMER_KEY = "951PdMoVOZKApgRl4iMLA";
	private final String CONSUMER_SECRET = "1iwnaEmWNhKxdEUcgyeyNGEJPF9THxfVFrvgD9sjt0";
	
	@Test
	public void test() throws TwitterException, IOException {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (null == accessToken) {
			System.out.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			String pin = br.readLine();
			try {
				if (pin.length() > 0) {
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				} else {
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
		}

		System.out.println("twitter.userId: "+ twitter.verifyCredentials().getId());
		System.out.println("accessToken.token: "+ accessToken.getToken());
		System.out.println("accessToken.tokenSecret: "+ accessToken.getTokenSecret());
		
		System.exit(0);
	}
	
	@Test
	public void requestTokenTest() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		RequestToken requestToken = twitter.getOAuthRequestToken();
		
		System.out.println("authUrl: "+ requestToken.getAuthorizationURL());
		System.out.println("requestToken.token: "+ requestToken.getToken());
		System.out.println("requestToken.tokenSecret: "+ requestToken.getTokenSecret());
	}
	
	@Test
	public void oauthVerifierTest() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		
		String oauthVerifier = "QvQ6eAApwJSjTNxNYBAZvXtFNxgCgwNe";
		String token = "PSeIquJiZRi3nZUesPI8g7Qye8PR1Yym";
		String tokenSecret = "ayCvulmwQ6C4UmZZHuc8z3TKpUUVpTzB";
		RequestToken requestToken = new RequestToken(token, tokenSecret);
		
		AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
        twitter.setOAuthAccessToken(accessToken);
        User user = twitter.verifyCredentials();
		
        System.out.println("twitter.userId: "+ user.getId());
		System.out.println("accessToken.token: "+ accessToken.getToken());
		System.out.println("accessToken.tokenSecret: "+ accessToken.getTokenSecret());
	}
	
	@Test
	public void accessTokenTest() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

		String token = "580012011-0BBGsHu6oOelOUBSmvFZ0uNGsLkc6QPXZFxRjemz";
		String tokenSecret = "Kv4ByW3yQ5FoJryQ1S5mBsvNc8GlusOvrfDUNkWik8";
		AccessToken accessToken = new AccessToken(token, tokenSecret);
		
		twitter.setOAuthAccessToken(accessToken);

		User user = twitter.verifyCredentials();
		System.out.println("twitter.userId: "+ user.getId());
		System.out.println("twitter.userName: "+ user.getName());
	}
	
}
