
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository		positionRepository;

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private Validator				validator;


	public Position reconstruct(final Position position, final BindingResult binding) {
		Position result;

		if (position.getId() == 0)
			result = position;
		else {
			result = this.positionRepository.findOne(position.getId());
			result.setRoleEn(position.getRoleEn());
			result.setRoleEs(position.getRoleEs());
			this.validator.validate(result, binding);
		}

		return result;
	}

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
		Assert.isTrue(this.getPositionsNotUsed().contains(position));
		this.positionRepository.delete(position.getId());
	}

	private Collection<Position> getPositionsNotUsed() {
		Collection<Position> result;

		result = this.administratorRepository.getPositionsNotUsed();

		return result;
	}

	public Position getDefaultPosition() {
		Position defaultPosition;

		defaultPosition = this.positionRepository.getDefaultPosition();

		return defaultPosition;
	}

	public Boolean existRole(final String roleEn, final String roleEs) {
		Boolean res = false;
		final List<String> listaEn = new ArrayList<String>();
		final List<String> listaEs = new ArrayList<String>();
		for (final Position p : this.positionRepository.findAll()) {
			listaEs.add(p.getRoleEs());
			listaEn.add(p.getRoleEn());
		}
		if (!(listaEn.contains(roleEn)) && !(listaEs.contains(roleEs)))
			res = true;
		return res;
	}
}
