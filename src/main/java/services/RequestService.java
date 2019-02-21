
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;


	public Request create() {
		Request result;
		result = new Request();
		return result;
	}

	public Request findOne(final int id) {
		final Request result = this.requestRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Request> findAll() {
		Collection<Request> result;
		result = this.requestRepository.findAll();
		return result;
	}

	public Request save(final Request r) {
		Assert.notNull(r);
		Request result;
		result = this.requestRepository.save(r);
		return result;
	}

	public void delete(final Request r) {
		Assert.notNull(r);
		Assert.isTrue(r.getStatus().equals("PENDING"));
		this.requestRepository.delete(r);
	}

	public Request acceptRequest(final Request r) {
		Assert.notNull(r);
		Assert.isTrue(r.getStatus().equals("PENDING"));
		r.setStatus("APPROVED");
		final Request result = this.save(r);
		return result;
	}

	public Request rejectRequest(final Request r) {
		Assert.notNull(r);
		Assert.isTrue(r.getStatus().equals("PENDING"));
		r.setStatus("REJECTED");
		final Request result = this.save(r);
		return result;
	}
}
