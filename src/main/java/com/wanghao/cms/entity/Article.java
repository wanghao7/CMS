package com.wanghao.cms.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
/**
 * 文章
 * @author hp
 *
 */
//es数据库名 cms_es  表名称 article
@Document(indexName="cms_es",type="article")
public class Article implements Serializable {

	@Id//id是Integer类型
	private Integer id;
	//使用注解   分析器 用ik分词器 ik_smart  创建索引 并储存 客户输入的类型用ik分词器进行分词  客户输入的类型是text类型
	@Field(analyzer="ik_smart",index=true,store=true,searchAnalyzer="ik_smart",type=FieldType.text)
	private String  title;  //标题
	@Field(analyzer="ik_smart",index=true,store=true,searchAnalyzer="ik_smart",type=FieldType.text)
	private String  content;//文章内容
	private String  picture;//图片地址
	private String  abstrac;//摘要
	private int     channelId;//栏目
	private int     categoryId;//分类
	private int     userId;//用户ID
	private int 	hits; //点击数量
	private int 	hot;//是否热门
	private int 	status;//文章的状态  0 ，待审核    1 审核通过   2 拒绝 
	private int 	deleted; // 是否被删除
	private Date 	created  ; //创建时间
	private Date 	updated   ;// 最后的修改时间
	private int 	commentCnt  ; // 评论数量
	private int 	articleType   ; // 文章的类型 文字0     图片  1  
	
	private Channel channel      ;//栏目 频道
	private Category  category     ; //分类
	private User user ;
	private int complainCnt;// 投诉的数量
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getAbstrac() {
		return abstrac;
	}
	public void setAbstrac(String abstrac) {
		this.abstrac = abstrac;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
	public int getArticleType() {
		return articleType;
	}
	public void setArticleType(int articleType) {
		this.articleType = articleType;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getComplainCnt() {
		return complainCnt;
	}
	public void setComplainCnt(int complainCnt) {
		this.complainCnt = complainCnt;
	}
	public Article(Integer id, String title, String content, String picture, String abstrac, int channelId,
			int categoryId, int userId, int hits, int hot, int status, int deleted, Date created, Date updated,
			int commentCnt, int articleType, Channel channel, Category category, User user, int complainCnt) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.picture = picture;
		this.abstrac = abstrac;
		this.channelId = channelId;
		this.categoryId = categoryId;
		this.userId = userId;
		this.hits = hits;
		this.hot = hot;
		this.status = status;
		this.deleted = deleted;
		this.created = created;
		this.updated = updated;
		this.commentCnt = commentCnt;
		this.articleType = articleType;
		this.channel = channel;
		this.category = category;
		this.user = user;
		this.complainCnt = complainCnt;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content=" + content + ", picture=" + picture + ", abstrac="
				+ abstrac + ", channelId=" + channelId + ", categoryId=" + categoryId + ", userId=" + userId + ", hits="
				+ hits + ", hot=" + hot + ", status=" + status + ", deleted=" + deleted + ", created=" + created
				+ ", updated=" + updated + ", commentCnt=" + commentCnt + ", articleType=" + articleType + ", channel="
				+ channel + ", category=" + category + ", user=" + user + ", complainCnt=" + complainCnt + "]";
	}
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
}
