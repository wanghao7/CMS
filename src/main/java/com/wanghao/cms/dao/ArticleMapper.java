package com.wanghao.cms.dao;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Category;
import com.wanghao.cms.entity.Channel;
import com.wanghao.cms.entity.Comment;
import com.wanghao.cms.entity.Complain;
import com.wanghao.cms.entity.Link;
import com.wanghao.cms.entity.Slide;

public interface ArticleMapper {

	/**
	 * 根据用户获取文章的列表
	 * @param id
	 * @return
	 */
	List<Article> listByUser(int id);

	//逻辑删除
	@Update("UPDATE cms_article SET deleted=1 WHERE id=#{id}")
	int delete(Integer id);

	/**
	 * 获取    所有     栏目的方法
	 * @return
	 */
	@Select("SELECT id,name FROM cms_channel")
	List<Channel> getChannels();

	
	/**
	 * 根据栏目id 获取分类 2级
	 * @cid  ： 栏目的id
	 * @return
	 */
	@Select("SELECT id,name FROM cms_category WHERE channel_id = #{value}")
	List<Category> getCategorisByCid(int cid);

	/**
	 * 添加文章 
	 * @param article
	 * @return
	 */
	@Insert("insert into cms_article(title,content,picture,channel_id,category_id,user_id,hits,hot,status,deleted,created,updated,commentCnt,articleType) "
			+ " VALUES(#{title},#{content},#{picture},#{channelId},#{categoryId},#{userId},0,0,0,0,now(),now(),0,#{articleType})")	
	int add(Article article);

	/**
	 * 修改之前查找文章
	 * @param id
	 * @return
	 */
	
	Article getById(int id);

	/**
	 * 修改并且默认把状态改为未通过
	 * @param article
	 * @return
	 */
	@Update("UPDATE cms_article SET title=#{title},content=#{content},picture=#{picture},channel_id=#{channelId},"
			+ " category_id=#{categoryId},status=0,"
			+ "updated=now() WHERE id=#{id} ")
	int update(Article article);

	
	List<Article> list(@Param("status")int status);

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT id,title,channel_id channelId , category_id categoryId,status ,hot "
			+ " FROM cms_article WHERE id = #{value} ")
	Article getInfoById(int id);


	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("UPDATE cms_article SET hot=#{hot} WHERE id=#{myid}")
	int setHot(@Param("myid") int id, @Param("hot") int status);

	
	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("UPDATE cms_article SET status=#{myStatus} WHERE id=#{myid}")
	int CheckStatus(@Param("myid") int id, @Param("myStatus") int status);

	/**
	 * 热门文章
	 * @return
	 */
	List<Article> hostList();
//最新文章
	List<Article> lastList(int pageSize);
//轮播图
	List<Slide> getSlides();
	/**
	 * 根据频道和栏目获取文章
	 * @param channleId
	 * @param catId
	 * @return
	 */
	List<Article> getArticles(@Param("channelId")int channelId,@Param("catId") int catId);

	/**
	 * 根据频道获取栏目
	 * @param channelId
	 * @return
	 */
	@Select("SELECT id,name FROM cms_category where channel_id=#{value}")
	@ResultType(Category.class)
	List<Category> getCategoriesByChannelId(int channelId);

	/**
	 * 添加数据(评论)
	 * @param comment
	 * @return
	 */
	@Insert("INSERT INTO cms_comment(articleId,userId,content,created) "
			+ "VALUES(#{articleId},#{userId},#{content},NOW());")
	int addComment(Comment comment);

	/**
	 * 文章评论加一
	 * @param userId
	 * @return
	 */
	@Update("UPDATE cms_article SET commentCnt=commentCnt+1 WHERE id=#{value}")
	int increaseCommentCount(int userId);

	/**
	 * 获取评论
	 * @param articleId
	 * @return
	 */
	@Select(" SELECT c.id,c.articleId,c.userId,u.username username,c.content,c.created FROM cms_comment c "
			+ " LEFT JOIN cms_user u on u.id=c.userId "
			+ "WHERE articleId=#{value} ORDER BY c.created DESC")
	List<Comment> getComments(int articleId);

	@Select(" SELECT id,url,name,created FROM cms_link  ORDER BY created DESC")
	List<Link> getLink();

	
	
	/**
	 * 添加评论
	 * @param complain
	 * @return
	 */
	@Insert("INSERT INTO cms_complain(article_id,user_id,complain_type,"
			+ "compain_option,src_url,picture,content,email,mobile,created)"
			+ "   VALUES(#{articleId},#{userId},"
			+ "#{complainType},#{compainOption},#{srcUrl},#{picture},#{content},#{email},#{mobile},now())")
	int addCoplain(Complain complain);

	
	/**
	 * 更新文章表的字段
	 * @param articleId
	 */
	@Update("UPDATE cms_article SET complainCnt=complainCnt+1,status=if(complainCnt>10,2,status)  "
			+ " WHERE id=#{value}")
	void increaseComplainCnt(Integer articleId);

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	List<Complain> getComplains(int articleId);

}
