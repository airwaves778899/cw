package cw.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 次分類
 *
 */
public class SubChannel {
	private int id;
	private String name;
	private int masterChannelId;
	
	private MasterChannel masterChannel;

	private Set<Article> articles;

	private Set<KeyWord> keyWords;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMasterChannelId() {
		return masterChannelId;
	}

	public void setMasterChannelId(int masterChannelId) {
		this.masterChannelId = masterChannelId;
	}

	public MasterChannel getMasterChannel() {
		return masterChannel;
	}

	public void setMasterChannel(MasterChannel masterChannel) {
		this.masterChannel = masterChannel;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	public Set<KeyWord> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(Set<KeyWord> keyWords) {
		this.keyWords = keyWords;
	}
	
	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode(){
	    return new HashCodeBuilder()
	        .append(id)
	        .append(name)
	        .append(masterChannelId)
	        .toHashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		if(obj instanceof SubChannel){
			final SubChannel other = (SubChannel) obj;
			
			return new EqualsBuilder()
					.append(id, other.id)
					.append(name, other.name)
					.append(masterChannelId, other.masterChannelId)
					.isEquals();
		}else{
			return false;
		}
	}
}
