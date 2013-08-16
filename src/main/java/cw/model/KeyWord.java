package cw.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class KeyWord {
	private int id;
	private String word;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

//	@Override
//	public String toString() {
//	    return ToStringBuilder.reflectionToString(this);
//	}
	@Override
	public String toString() {
		return "KeyWord [id=" + id + ", word=" + word + "]";
	}
	
	@Override
	public int hashCode(){
	    return new HashCodeBuilder()
	        .append(id)
	        .append(word)
	        .toHashCode();
	}

	@Override
	public boolean equals(final Object obj){
		if(obj instanceof KeyWord){
			final KeyWord other = (KeyWord) obj;
			
			return new EqualsBuilder()
					.append(id, other.id)
					.append(word, other.word)
					.isEquals();
		}else{
			return false;
		}
	}
}
