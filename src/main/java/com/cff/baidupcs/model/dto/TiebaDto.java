package com.cff.baidupcs.model.dto;

import java.util.List;

public class TiebaDto {
	BaiduDto baidu;
	String tbs;
	List<Bar> bars;
	int likeForumNum; // 关注的贴吧数
	int postNum; // 发言次数

	public BaiduDto getBaidu() {
		return baidu;
	}

	public void setBaidu(BaiduDto baidu) {
		this.baidu = baidu;
	}

	public String getTbs() {
		return tbs;
	}

	public void setTbs(String tbs) {
		this.tbs = tbs;
	}

	public List<Bar> getBars() {
		return bars;
	}

	public void setBars(List<Bar> bars) {
		this.bars = bars;
	}

	public int getLikeForumNum() {
		return likeForumNum;
	}

	public void setLikeForumNum(int likeForumNum) {
		this.likeForumNum = likeForumNum;
	}

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	class Bar {
		String fid; // 贴吧fid
		String name; // 名字
		String level; // 个人等级
		int exp; // 个人经验

		public String getFid() {
			return fid;
		}

		public void setFid(String fid) {
			this.fid = fid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public int getExp() {
			return exp;
		}

		public void setExp(int exp) {
			this.exp = exp;
		}
	}
}
