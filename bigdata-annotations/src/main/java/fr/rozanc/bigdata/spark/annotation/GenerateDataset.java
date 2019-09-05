package fr.rozanc.bigdata.spark.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a {@link java.io.Serializable} class to generate a typed Dataset based on this type.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface GenerateDataset {
    /**
     * If the {@link GenerateDataset#destinationPackage()} has this value,
     * the Dataset will be created in the same package as the Dataset model.
     */
    String DEFAULT_PACKAGE = "-";

    /**
     * Specifies the class name of the generated Dataset.
     *
     * @return the dataset class name
     */
    String className() default "";

    /**
     * Specifies the package where will be created the Dataset.
     *
     * @return the package where
     */
    String destinationPackage() default DEFAULT_PACKAGE;
}