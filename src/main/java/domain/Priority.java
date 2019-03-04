
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Priority extends DomainEntity {

	private Map<String, String>	name;


	@ElementCollection(targetClass = String.class)
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}

}
