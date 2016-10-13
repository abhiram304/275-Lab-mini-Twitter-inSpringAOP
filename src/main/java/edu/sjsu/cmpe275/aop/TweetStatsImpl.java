package edu.sjsu.cmpe275.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import edu.sjsu.cmpe275.aop.aspect.StatsAspect;


public class TweetStatsImpl implements TweetStats {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	@Autowired
	private StatsAspect statsObj;

	@Override
	public void resetStats() {
		// TODO Auto-generated method stub
		statsObj.setLongestMsgLen(0);
	}
    
	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		//System.out.printf("Longest tweet:  ", statsObj.getLengthLongestMsg());
		return statsObj.getLengthLongestMsg();
	}

	@Override
	public String getMostActiveFollower() {
		// TODO Auto-generated method stub
		HashMap<String, Set<String>> followings= statsObj.getFollowingsMap();
		//System.out.println("AAAAAAAAAAAAAAa");
		//System.out.println(Arrays.asList(followings));
		String activeFollower=null;
		int max=0;
		for(String temp: followings.keySet()){
			if(followings.get(temp).size()>max){
				activeFollower=temp;
			}
		}
		statsObj.setMostActiveUser(activeFollower);
		return activeFollower;
	}

	@Override
	public String getMostProductiveUser() {
		// TODO Auto-generated method stub
		Integer max=0;
		String prodUser=null;
		HashMap<String, Integer> UserTweets= statsObj.getTweetMap();
		for(String temp:UserTweets.keySet()){
			System.out.println("user: "+temp+" length:"+UserTweets.get(temp));
			if(UserTweets.get(temp)>max){
				max=UserTweets.get(temp);
				prodUser=temp;
			}
		}
		statsObj.setMostProductiveUser(prodUser);
		return prodUser;
	}
}



