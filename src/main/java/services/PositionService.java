
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;


	public Position create() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		final Position position = new Position();

		return position;
	}

	public Collection<Position> findAll() {
		Collection<Position> positions;

		positions = this.positionRepository.findAll();

		return positions;
	}

	public Position findOne(final Integer positionId) {
		Position position;

		Assert.notNull(positionId);
		position = this.positionRepository.findOne(positionId);
		Assert.notNull(position);

		return position;
	}

	public Position save(final Position position) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Position result;

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		result = this.positionRepository.save(position);

		return result;
	}

	public void delete(final Position position) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.notNull(position);
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		if (this.getPositionsNotUsed().contains(position))
			this.positionRepository.delete(position.getId());
	}

	private Collection<Position> getPositionsNotUsed() {
		Collection<Position> result;

		result = this.positionRepository.getPositionsNotUsed();

		return result;
	}

}
