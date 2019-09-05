package fr.rozanc.bigdata.spark.generator

import org.apache.spark.sql.Dataset
import java.io.PrintWriter
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Parameter
import java.lang.reflect.ParameterizedType
import java.util.regex.Pattern

class TypedDatasetGenerator {

    companion object {
        const val GENERATOR_VERSION = 1L
    }

    fun write(className: String,
              destinationPackage: String,
              underlyingType: String,
              fieldNames: List<String>,
              serialVersionUid: Long,
              printWriter: PrintWriter) {
        printWriter.printf("package %s;%n", destinationPackage)
        printWriter.println()
        printWriter.println("@SuppressWarnings(\"deprecation\")")
        printWriter.printf("public class %s extends org.apache.spark.sql.Dataset<%s> {%n", className, underlyingType)
        printWriter.println()
        printWriter.printf("    private static final long serialVersionUID = %dL;%n", serialVersionUid)
        printWriter.println()
        printWriter.println("    public class Cols {")
        fieldNames.forEach { f -> printWriter.printf("        public static final String %s = \"%s\";%n", f, f) }
        fieldNames.forEach { f -> printWriter.printf("        public org.apache.spark.sql.Column %s() { return col(%s); }%n", f, f) }
        printWriter.println("    }")
        printWriter.println()
        printWriter.printf("    public static final org.apache.spark.sql.Encoder<%s> ENCODER = org.apache.spark.sql.Encoders.bean(%s.class);%n", underlyingType, underlyingType)
        printWriter.println()
        printWriter.printf("    public static %s from(final org.apache.spark.sql.Dataset<%s> dataset) {%n", className, underlyingType)
        printWriter.printf("        return new %s(dataset.sparkSession(), dataset.logicalPlan());%n", className)
        printWriter.println("    }")
        printWriter.println()
        printWriter.printf("    private %s(org.apache.spark.sql.SparkSession sparkSession, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan) {%n", className)
        printWriter.println("        super(sparkSession, logicalPlan, ENCODER);")
        printWriter.println("    }")
        printWriter.println()
        printWriter.println("    public final Cols cols = new Cols();")
        generateWrapperDatasetMethods(className, underlyingType, printWriter)
        printWriter.println("}")
    }

    private fun generateWrapperDatasetMethods(className: String,
                                              underlyingType: String,
                                              printWriter: PrintWriter) {
        val datasetTypeParameterName = Dataset::class.java.typeParameters[0].name
        Dataset::class.java.declaredMethods
                .filter { m ->
                    (m.modifiers and Modifier.PUBLIC and Modifier.FINAL.inv() and Modifier.STATIC.inv() != 0
                            && Dataset::class.java.isAssignableFrom(m.returnType)
                            && m.genericReturnType is ParameterizedType
                            && (m.genericReturnType as ParameterizedType).actualTypeArguments.size == 1
                            && (m.genericReturnType as ParameterizedType).actualTypeArguments[0].typeName == datasetTypeParameterName)
                }
                .forEach { m -> writeWrapperMethod(className, underlyingType, m, datasetTypeParameterName, printWriter) }
    }

    private fun writeWrapperMethod(className: String,
                                   underlyingType: String,
                                   method: Method,
                                   datasetTypeParameterName: String,
                                   printWriter: PrintWriter) {
        printWriter.println()
        printWriter.println("    /**")
        printWriter.println("     * {@inheritDoc}")
        printWriter.println("     */")
        printWriter.println("    @Override")
        printWriter.printf("    public %s%s %s(%s) {%n",
                getMethodTypeParametersString(method, datasetTypeParameterName, className),
                className,
                method.name,
                method.parameters.joinToString(", ") { it.asString(datasetTypeParameterName, underlyingType) })
        printWriter.printf("        final org.apache.spark.sql.Dataset<%s> dataset = super.%s(%s);%n", underlyingType, method.name, method.parameters.joinToString(", ") { it.name })
        printWriter.println("        return from(dataset);")
        printWriter.println("    }")
    }

    private fun getMethodTypeParametersString(method: Method, datasetTypeParameterName: String, className: String): String {
        val separator = ", "
        val parameterTypes = method.typeParameters
        val stringBuilder = StringBuilder()
        if (parameterTypes.isNotEmpty()) {
            stringBuilder.append("<")
            for (typeVariable in parameterTypes) {
                stringBuilder.append(typeVariable.typeName.replace("^${Pattern.quote(datasetTypeParameterName)}$", className)).append(separator)
            }
            if (stringBuilder.length > 1) {
                stringBuilder.setLength(stringBuilder.length - separator.length)
            }
            stringBuilder.append("> ")
        }
        return stringBuilder.toString()
    }

    private fun Parameter.asString(datasetTypeParameterName: String, underlyingType: String): String {
        val stringBuilder = StringBuilder(type.canonicalName)
        if (parameterizedType is ParameterizedType
                && (parameterizedType as ParameterizedType).actualTypeArguments.isNotEmpty()) {
            stringBuilder.append('<')
            stringBuilder.append((parameterizedType as ParameterizedType).actualTypeArguments.joinToString(", ") {
                if (it.typeName == datasetTypeParameterName) {
                    underlyingType
                } else {
                    it.typeName
                }
            })
            stringBuilder.append(">")
        }
        if (isVarArgs) {
            stringBuilder.setLength(stringBuilder.length - 2)
            stringBuilder.append("...")
        }
        stringBuilder.append(" ").append(name)
        return stringBuilder.toString()
    }
}
