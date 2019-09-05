package fr.rozanc.bigdata.spark.generator

import com.google.auto.service.AutoService
import com.google.common.collect.ImmutableSet
import fr.rozanc.bigdata.spark.annotation.GenerateDataset

import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic
import javax.tools.JavaFileObject
import java.io.IOException
import java.io.PrintWriter
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors

@SupportedAnnotationTypes("fr.rozanc.bigdata.spark.annotation.GenerateDataset")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor::class)
class TypedDatasetProcessor : AbstractProcessor() {

    private val generator = TypedDatasetGenerator()

    override fun process(annotations: Set<TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        if (annotations == null || roundEnv == null) {
            return false
        }

        annotations.forEach { element ->
            val annotatedElements = roundEnv.getElementsAnnotatedWith(element)
            val (serializableElements, nonSerializableElements) = annotatedElements.partition { it.isSerializable() }
            nonSerializableElements.forEach {
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR,
                        "@GenerateDataset must be applied on a serializable element", it)
            }
            serializableElements.forEach { serializableElement ->
                var processGeneration = true

                val typeClassName = (serializableElement as TypeElement).qualifiedName.toString()
                val generateDatasetAnnotation = serializableElement.getAnnotation(GenerateDataset::class.java)

                val className: String
                className = if ("" == generateDatasetAnnotation.className) {
                    typeClassName.substring(typeClassName.lastIndexOf('.') + 1) + "Dataset"
                } else {
                    generateDatasetAnnotation.className
                }
                if (!isValidClassName(className)) {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR,
                            "Invalid @GenerateDataset#className \"$className\"", serializableElement)
                    processGeneration = false
                }

                val destinationPackage: String
                destinationPackage = if (GenerateDataset.DEFAULT_PACKAGE == generateDatasetAnnotation.destinationPackage) {
                    typeClassName.substring(0, typeClassName.lastIndexOf('.'))
                } else {
                    generateDatasetAnnotation.destinationPackage
                }

                if (!isValidPackage(destinationPackage)) {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR,
                            "Invalid @GenerateDataset#destinationPackage \"$destinationPackage\"", serializableElement)
                    processGeneration = false
                }

                val fieldNames = findAllFields(serializableElement, SKIP_FIELD_MODIFIERS)
                        .map { f -> f.simpleName.toString() }

                val serialVersionUid = findSerialVersionUid(serializableElement)

                if (processGeneration) {
                    try {
                        val builderFile = processingEnv.filer.createSourceFile("$destinationPackage.$className")
                        PrintWriter(builderFile.openWriter()).use { out ->
                            generator.write(className, destinationPackage, typeClassName, fieldNames, serialVersionUid, out)
                        }
                    } catch (e: IOException) {
                        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR,
                                "Unable to write class $destinationPackage.$className")
                    }
                }
            }
        }

        return true
    }

    companion object {

        private val SKIP_FIELD_MODIFIERS = ImmutableSet.of(Modifier.STATIC, Modifier.TRANSIENT)

        private fun findSerialVersionUid(typeElement: TypeElement): Long {
            val serialVersionUidElement = typeElement.enclosedElements.stream()
                    .filter { e ->
                        ("serialVersionUID" == e.simpleName.toString()
                                && TypeKind.LONG == e.asType().kind
                                && e.modifiers.containsAll(listOf(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)))
                    }
                    .findFirst()
            if (serialVersionUidElement.isPresent) {
                val value = (serialVersionUidElement.get() as VariableElement).constantValue as Long
                return value xor TypedDatasetGenerator.GENERATOR_VERSION
            }
            return TypedDatasetGenerator.GENERATOR_VERSION
        }

        private fun findAllFields(typeElement: TypeElement, exceptModifiers: Set<Modifier>): Set<Element> {
            val fieldElements = LinkedHashSet<Element>()
            findAllFields(typeElement, exceptModifiers, fieldElements)
            return fieldElements
        }

        private fun findAllFields(typeElement: TypeElement,
                                  exceptModifiers: Set<Modifier>,
                                  fieldElements: MutableSet<Element>) {
            val parentTypeElement = toTypeElement(typeElement.superclass)
            if ("java.lang.Object" != parentTypeElement!!.qualifiedName.toString()) {
                findAllFields(parentTypeElement, exceptModifiers, fieldElements)
            }
            fieldElements.addAll(typeElement.enclosedElements
                    .filter { enclosedElement -> ElementKind.FIELD == enclosedElement.kind
                            && containsNone(enclosedElement.modifiers, exceptModifiers) })
        }

        private fun <T> containsNone(allElements: Collection<T>, elementsToFind: Collection<T>): Boolean {
            for (element in elementsToFind) {
                if (allElements.contains(element)) {
                    return false
                }
            }
            return true
        }

        private fun findInterfaces(element: TypeElement): Set<TypeElement> {
            val interfaces = LinkedHashSet<TypeElement>()
            findInterfaces(element, interfaces)
            return interfaces
        }

        private fun findInterfaces(element: TypeElement, interfaces: MutableSet<TypeElement>) {
            val elementInterfaces = element.interfaces.mapNotNull { toTypeElement(it) }
            interfaces.addAll(elementInterfaces)

            val parentTypeElement = toTypeElement(element.superclass)
            if ("java.lang.Object" != parentTypeElement!!.qualifiedName.toString()) {
                findInterfaces(parentTypeElement, interfaces)
            }
        }

        private fun toTypeElement(typeMirror: TypeMirror): TypeElement? {
            return if (typeMirror is DeclaredType) {
                typeMirror.asElement() as TypeElement
            } else null
        }

        private fun Element.isSerializable(): Boolean {
            return this is TypeElement && findInterfaces(this).stream()
                    .anyMatch { i -> "java.io.Serializable" == i.qualifiedName.toString() }
        }

        private fun isValidClassName(className: String): Boolean {
            return SourceVersion.isName(className)
        }

        private fun isValidPackage(packageName: String): Boolean {
            if (packageName.startsWith(".") || packageName.endsWith(".") || packageName.contains("..")) {
                return false
            }
            for (piece in packageName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                if (!SourceVersion.isName(piece)) {
                    return false
                }
            }
            return true
        }
    }
}
