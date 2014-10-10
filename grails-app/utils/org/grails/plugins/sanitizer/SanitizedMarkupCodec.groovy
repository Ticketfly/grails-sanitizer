package org.grails.plugins.sanitizer
/**
 * Codec that allows you to sanitize a String
 * <br />def clean = myText.encodeAsSanitizedMarkup()
 * <p>Encoder only available, there is no decoder to create an unsanitary string</p>
 * @author daniel
 *
 */
class SanitizedMarkupCodec {

	def markupSanitizerService
    def grailsApplication

	/**
	 * Encode a string as sanitized Markup
	 */
	def encode = { dirtyMarkup ->

		if(!markupSanitizerService){
			markupSanitizerService = grailsApplication.mainContext.getBean("markupSanitizerService")
		}

		MarkupSanitizerResult result = markupSanitizerService.sanitize(dirtyMarkup)

		if(result.isInvalidMarkup() && !grailsApplication.config?.sanitizer?.trustSanitizer){
			// just return empty string...
			return ""
		}else{
			return result.cleanString
		}
	}
}
