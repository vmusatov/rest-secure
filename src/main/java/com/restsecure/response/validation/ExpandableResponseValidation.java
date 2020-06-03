package com.restsecure.response.validation;

import com.restsecure.response.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for response validations that supports joining with other response validations
 */
@SuppressWarnings("unchecked")
public abstract class ExpandableResponseValidation<T extends ExpandableResponseValidation<T>> implements ResponseValidation {

    protected List<ResponseValidation> joinedValidations;

    public ExpandableResponseValidation() {
        this.joinedValidations = new ArrayList<>();
    }

    /**
     * Subclasses must validate the response
     *
     * @param response Response
     */
    protected abstract void validateResponse(Response response);

    /**
     * Validation of the response with the current validation and all joined validations
     *
     * @param response Response
     */
    @Override
    public final void validate(Response response) {
        validateResponse(response);
        joinedValidations.forEach(validation -> validation.validate(response));
    }

    /**
     * Joining another validation
     *
     * @param validation Response validation
     * @return this
     */
    public T join(ResponseValidation validation) {
        this.joinedValidations.add(validation);
        return (T) this;
    }

    /**
     * Syntactic sugar for greater readability.<br>
     * For example:
     * <pre>
     *     validation()
     *          .header("name", equalTo("value"))
     *          .header("name2", equalTo("value2"))
     *          .and()
     *          .cookie("name", equalTo("value"))
     *          .cookie("name2", equalTo("value2"))
     * </pre>
     *
     * @return this
     */
    public T and() {
        return (T) this;
    }
}
