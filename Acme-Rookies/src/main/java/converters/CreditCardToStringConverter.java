
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Company;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<Company, String> {

	@Override
	public String convert(final Company company) {
		String result;

		if (company == null)
			result = null;
		else
			result = String.valueOf(company.getId());

		return result;
	}
}
