
package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	ProcessionRepository	processionRepository;


	public Procession create() {
		Procession result;
		result = new Procession();
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
		Procession result;
		if (p.getId() == 0) {
			final Member[][] formation = new Member[p.getMaxRow()][p.getMaxColumn()];
			p.setFormation(formation);
			p.setMoment(new Date());
			p.setTicker(this.tickerGenerator());
			result = this.processionRepository.save(p);
		} else {
			final Procession old = this.findOne(p.getId());
			if (old.getMaxRow() != p.getMaxRow() || old.getMaxColumn() != p.getMaxColumn()) {
				final Member[][] newFormation = this.formationEdit(p, old);
				p.setFormation(newFormation);
			}
			result = this.processionRepository.save(p);
		}
		return result;
	}

	private Member[][] formationEdit(final Procession p, final Procession old) {
		final Member[][] result = new Member[p.getMaxRow()][p.getMaxColumn()];
		final Member[][] oldFormation = old.getFormation();

		for (int i = 0; i < old.getMaxRow(); i++)
			for (int j = 0; j < old.getMaxColumn(); j++) {
				final Member m = oldFormation[i][j];
				result[i][j] = m;
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
