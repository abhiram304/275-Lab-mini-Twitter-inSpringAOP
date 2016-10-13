package edu.sjsu.cmpe275.aop.aspect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	private HashMap<String, Integer> UserTweetStore=new HashMap<String, Integer>();
	private HashMap<String, Set<String>> followingsStore=new HashMap<String, Set<String>>();
	private int longestMsgLen;
	private String mostProductiveUser;
	private String mostActiveUser;

	public int getLongestMsgLen() {
		return longestMsgLen;
	}


	public String getMostProductiveUser() {
		return mostProductiveUser;
	}


	public void setMostProductiveUser(String mostProductiveUser) {
		this.mostProductiveUser = mostProductiveUser;
	}


	public String getMostActiveUser() {
		return mostActiveUser;
	}


	public void setMostActiveUser(String mostActiveUser) {
		this.mostActiveUser = mostActiveUser;
	}


	public void setLongestMsgLen(int longestMsgLen) {
		this.longestMsgLen = longestMsgLen;
	}

	//public String mostProductiveUser= null;
	//public String mostFollowingUser=null;
	@Autowired TweetStatsImpl stats;
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..)) && args(user, tweet)")
	public void tweetAdvice(JoinPoint joinPoint, String user, String tweet)throws IllegalArgumentException{
		//longestMsgLen=0;
		Integer lengthOfTweet=tweet.length();
		System.out.println("IN THE STATS ASPECT ----HASHMAP STORING-----");
		System.out.printf("The user message is:  %s",tweet);
		if(lengthOfTweet<=140){
			//System.out.printf("%s Length less than 140 good", tweet);
			//Integer foundUserTweet=UserTweetStore.get(user);
			if(lengthOfTweet>this.getLengthLongestMsg()){
				this.setLongestMsgLen(lengthOfTweet);
			}
			if(!UserTweetStore.containsKey(user)){
				UserTweetStore.put(user,lengthOfTweet);
			}
			else{
				
				int temp=UserTweetStore.get(user).intValue();
				temp+=lengthOfTweet;
				UserTweetStore.put(user, temp);
			}
		}
		else{
			System.out.printf("The Tweet %s is not inserted as it's length is greater than 	140: ",tweet);
			throw new IllegalArgumentException("Message Length Exceeded");
		}
		//System.out.println(Arrays.asList(UserTweetStore));
	}
	
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..)) && args(follower, followee)")
	public void followAdvice(JoinPoint joinPoint, String follower, String followee){
		Set<String> temp= followingsStore.get(follower);
		System.out.printf("followweeee %s", followee);
		if(temp == null){
			temp= new HashSet<String>();
			temp.add(followee);
			followingsStore.put(follower, temp);
			System.out.println(temp);
		}else{
			temp.add(followee);
		}
		System.out.println("Successfully added followers and followee");
	}
	
	
	public HashMap<String, Integer> getTweetMap(){
		return UserTweetStore;
	}
	
	public HashMap<String, Set<String>> getFollowingsMap(){
		return followingsStore;
	}
	
	public Integer getLengthLongestMsg(){
		return longestMsgLen;
	}
	
	
	
	
	
	@After("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void dummyAfterAdvice(JoinPoint joinPoint) {
		System.out.printf("After the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		//stats.resetStats();
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..))")
	public void dummyBeforeAdvice(JoinPoint joinPoint) {
		System.out.printf("Before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	}
	
}
