
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import security.LoginService;
import domain.Item;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository	itemRepository;

	@Autowired
	private Validator		validator;

	@Autowired
	private ProviderService	providerService;


	public Item create() {
		final Item res = new Item();
		res.setDescription("");
		res.setLink(new HashSet<String>());
		res.setName("");
		res.setPictures(new HashSet<String>());
		res.setProvider(null);
		return res;
	}

	public Collection<Item> findAll() {
		return this.itemRepository.findAll();
	}

	public Item findOne(final int id) {
		return this.itemRepository.findOne(id);
	}

	public Item save(final Item i) {
		final Item saved;
		if (i.getId() != 0)
			Assert.isTrue(this.itemsByProvider().contains(i));
		saved = this.itemRepository.save(i);
		return saved;
	}

	//RECONSTRUCT
	public Item reconstruct(final Item item, final BindingResult binding) {
		Item res;
		if (item.getId() != 0) {
			res = this.findOne(item.getId());
			final Item copy = new Item();
			copy.setId(res.getId());
			copy.setVersion(res.getVersion());
			copy.setName(item.getName());
			copy.setDescription(item.getDescription());
			copy.setLink(item.getLink());
			copy.setPictures(item.getPictures());
			copy.setProvider(res.getProvider());
			res = copy;
		} else {
			item.setProvider(this.providerService.providerUserAccount(LoginService.getPrincipal().getId()));
			res = item;
		}

		this.validator.validate(res, binding);
		return res;

	}

	public Collection<Item> itemsByProvider() {
		return this.itemRepository.getItemsByProvider(this.providerUserAccountId());
	}
	private int providerUserAccountId() {
		return LoginService.getPrincipal().getId();
	}

	public List<Object[]> getAvgMinMaxDesvNumberItemByProvider() {
		return this.itemRepository.getAvgMinMaxDesvNumberItemByProvider();
	}

	public void delete(final Item item) {
		Assert.isTrue(this.itemsByProvider().contains(item));
		this.itemRepository.delete(item);
	}

	public Collection<Item> itemsByProviderId(final Integer userAccountProviderId) {
		return this.itemRepository.getItemsByProvider(userAccountProviderId);
	}

}
