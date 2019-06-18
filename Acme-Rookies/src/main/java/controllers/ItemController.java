
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService		itemService;
	@Autowired
	private ProviderService	providerService;


	@RequestMapping(value = "/listProvider", method = RequestMethod.GET)
	public ModelAndView listForProvider(@RequestParam final Integer providerId) {
		final Provider provider = this.providerService.findOneAnonymous(providerId);
		final ModelAndView result;
		final Collection<Item> items = this.itemService.itemsByProviderId(provider.getUserAccount().getId());

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("providerId", providerId);
		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Item> items = this.itemService.findAll();

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer itemId) {
		final ModelAndView result;
		final Item item = this.itemService.findOne(itemId);

		result = new ModelAndView("item/show");
		result.addObject("item", item);
		return result;

	}

}
