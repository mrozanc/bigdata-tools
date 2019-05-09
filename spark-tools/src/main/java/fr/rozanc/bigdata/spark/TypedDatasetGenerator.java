package fr.rozanc.bigdata.spark;

import org.apache.spark.sql.Encoder;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TypedDatasetGenerator {

    public void forType(Class<?> datasetTypeParameter) {
        Encoder<?> encoder = findEncoder(datasetTypeParameter);
        if (encoder == null) return;
        System.out.println("public class " + datasetTypeParameter.getSimpleName() + "Dataset extends org.apache.spark.sql.Dataset<" + datasetTypeParameter.getCanonicalName() + "> {");
        System.out.println("    public class Cols {");
        Arrays.stream(encoder.schema().fieldNames()).forEach(f -> System.out.println("        public org.apache.spark.sql.Column " + f + "() { return col(\""+ f + "\"); }"));
        System.out.println("    }");
        System.out.println("    public final Cols cols = new Cols();");
        System.out.println("}");
    }

    @SuppressWarnings("unchecked")
    public <T> Encoder<T> findEncoder(Class<T> encodableType) {
        try {
            Field field = encodableType.getDeclaredField("encoder");
            if (field.getType().isAssignableFrom(Encoder.class)) {
                return (Encoder<T>) field.get(null);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignore) {}
        return null;
    }
}
