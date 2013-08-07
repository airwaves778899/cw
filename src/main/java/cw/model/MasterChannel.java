package cw.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 大分類
 *
 */
public class MasterChannel {
	private int id;
	private String name;

	private Set<SubChannel> subChannels;

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

	public Set<SubChannel> getSubChannels() {
		return subChannels;
	}

	public void setSubChannels(Set<SubChannel> subChannels) {
		this.subChannels = subChannels;
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
	        .toHashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		if(obj instanceof MasterChannel){
			final MasterChannel other = (MasterChannel) obj;
			
			return new EqualsBuilder()
					.append(id, other.id)
					.append(name, other.name)
					.isEquals();
		}else{
			return false;
		}
	}
}
