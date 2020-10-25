package me.ste.stevesseries.components.component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface ComponentFriendlyName {
    /**
     * The display name of the component
     * @return display name
     */
    String value();
}