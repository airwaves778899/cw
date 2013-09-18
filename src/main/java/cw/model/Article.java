package cw.model;

import io.searchbox.annotations.JestId;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 文章
 */
public class Article {
	@JestId
	private int id;
	
	private String title;
	private String author;
	private String mimeType;
	private String languageCode;
	private String description;
	private String plainText;
	private String url;
	private String date;
	private int subChannelId;
	
	private Set<KeyWord> keyWords;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSubChannelId() {
		return subChannelId;
	}

	public void setSubChannelId(int subChannelId) {
		this.subChannelId = subChannelId;
	}

	public Set<KeyWord> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(Set<KeyWord> keyWords) {
		this.keyWords = keyWords;
	}
	
	public void addKeyWord(KeyWord keyWord){
		if(this.keyWords==null){
			this.keyWords = new HashSet<KeyWord>();
		}
		
		this.keyWords.add(keyWord);
	}
	
	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode(){
	    return new HashCodeBuilder()
	        .append(this.id)
	        .append(this.title)
	        .append(this.author)
	        .append(this.mimeType)
	        .append(this.languageCode)
	        .append(this.description)
	        .append(this.url)
	        .toHashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		if(obj instanceof KeyWord){
			final Article other = (Article) obj;
			
			return new EqualsBuilder()
					.append(id, other.id)
					.isEquals();
		}else{
			return false;
		}
	}
}
