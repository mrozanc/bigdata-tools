package fr.rozanc.bigdata.spark.sql

import org.apache.spark.sql.Dataset
import kotlin.reflect.KProperty1

/**
 * Get a column by referencing the underlying type property given it was created using Kotlin.
 */
fun <T> Dataset<T>.col(property: KProperty1<T, Any>) = col(property.name)
