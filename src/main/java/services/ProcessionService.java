
package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	ProcessionRepository	processionRepository;
	@Autowired
	Validator				validator;
	@Autowired
	ActorService			actorService;
	@Autowired
	BrotherhoodService		brotherhoodService;


	public Procession reconstruct(final Procession p, final BindingResult binding) {
		Procession result;
		if (p.getId() == 0) {
			p.setTicker(this.tickerGenerator());
			result = p;
			this.validator.validate(p, binding);
		} else {
			result = this.processionRepository.findOne(p.getId());
			p.setBrotherhood(result.getBrotherhood());
			p.setFloats(result.getFloats());
			p.setTicker(result.getTicker());
			p.setVersion(result.getVersion());
			p.setMoment(result.getMoment());
			p.setIsFinal(result.getIsFinal());
			this.validator.validate(p, binding);
			result = p;
		}
		return result;

	}

	public Procession create() {
		Procession result;
		result = new Procession();
		result.setIsFinal(false);
		return result;
	}

	public Procession findOne(final int id) {
		Procession result;
		result = this.processionRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Procession> findAll() {
		final Collection<Procession> result = this.processionRepository.findAll();
		return result;
	}

	public Procession save(final Procession p) {
		Assert.notNull(p);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Procession result;
		if (p.getId() == 0) {
			p.setTicker(this.tickerGenerator());
			p.setBrotherhood(b);
			result = this.processionRepository.save(p);
		} else {
			Assert.isTrue(p.getBrotherhood().equals(b));
			result = this.processionRepository.save(p);
		}
		return result;
	}

	public Procession saveDraft(final Procession p) {
		Assert.notNull(p);
		p.setIsFinal(false);
		final Procession result = this.save(p);
		return result;
	}

	public Procession saveFinal(final Procession p) {
		Assert.notNull(p);
		p.setIsFinal(true);
		final Procession result = this.save(p);
		return result;
	}

	//TODO delete de procession, se deben borrar todas las request asociadas, ¿agregacion?

	public Collection<Procession> getProcessionsByBrotherhood(final Brotherhood b) {
		Collection<Procession> result;
		result = this.processionRepository.getProcessionsByBrotherhood(b);
		return result;
	}

	private String tickerGenerator() {
		String dateRes = "";
		String numericRes = "";
		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		dateRes = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());

		for (int i = 0; i < 5; i++) {
			final Random random = new Random();
			numericRes = numericRes + alphanumeric.charAt(random.nextInt(alphanumeric.length() - 1));
		}

		return dateRes + "-" + numericRes;
	}
}
