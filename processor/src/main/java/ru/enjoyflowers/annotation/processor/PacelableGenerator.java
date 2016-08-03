package ru.enjoyflowers.annotation.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import ru.enjoyflowers.annotation.GenParcelable;


@SupportedAnnotationTypes("ru.enjoyflowers.annotation.GenParcelable")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PacelableGenerator extends AbstractProcessor {
    private Set<TypeElement> annotatedClassSet;
    private Map<String, TypeElement> classMap;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            annotatedClassSet = (Set<TypeElement>) roundEnv.getElementsAnnotatedWith(GenParcelable.class);
            classMap = new HashMap<>();
            for (TypeElement el : annotatedClassSet) {
                classMap.put(el.asType().toString(), el);
            }
            for (TypeElement el : annotatedClassSet) {
                List<VariableElement> fldList = getFields(el);
                String className = el.asType().toString();
                int index = className.lastIndexOf(".");
                String packageName = (index == -1) ? "" : className.substring(0, index);
                String simpleClassName = (index == -1) ? className : className.substring(index + 1);

                StringBuilder builder = new StringBuilder();
                if (index != -1) {
                    builder.append("package " + packageName + ";\n\n");
                }
                builder.append("import android.os.Parcel;\n\n")
                        .append("public class ")
                        .append(simpleClassName + "Generated extends " + className + " {\n");
                writeWriteToParcel(builder, className, simpleClassName, fldList, roundEnv);
                builder.append("}\n");
                JavaFileObject source = processingEnv.getFiler().createSourceFile(className + "Generated");
                Writer writer = source.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
        }
        return true;
    }

    private void writeWriteToParcel(StringBuilder builder, String className, String simpleClassName, List<VariableElement> fldList, RoundEnvironment roundEnv) {
        builder.append("\tpublic void writeToParcel(" + simpleClassName + " object, Parcel dest, int flags) {\n");
        for (VariableElement fld : fldList) {
            String fldName = fld.toString();
            String elemType = fld.asType().toString();
            if (fld.asType().getKind() == TypeKind.ARRAY) {
                elemType = elemType.substring(0, elemType.indexOf("["));
                if (classMap.keySet().contains(elemType)) {
                    builder.append("\t\tdest.writeInt(object." + fldName + ".length);\n");
                    builder.append("\t\tfor(" + elemType + " v : object." + fldName + "){\n");
                    builder.append("\t\t\tv.writeToParcel(dest, flags);\n");
                    builder.append("\t\t}\n");
                } else {
                    // Показать предупреждение
                }
            } else {
                switch (elemType) {
                    case "int":
                        builder.append("\t\tdest.writeInt(object." + fldName + ");\n");
                        break;
                    case "java.lang.String":
                        builder.append("\t\tdest.writeString(object." + fldName + ");\n");
                        break;
                    case "long":
                        builder.append("\t\tdest.writeLong(object." + fldName + ");\n");
                        break;
                    case "double":
                        builder.append("\t\tdest.writeDouble(object." + fldName + ");\n");
                        break;
                }


            }
        }
        builder.append("\t}\n");
    }

    private List<VariableElement> getFields(Element clsElem) {
        List<VariableElement> res = new ArrayList<>();
        for (VariableElement fld : ElementFilter.fieldsIn(clsElem.getEnclosedElements())) {
            if (fld.getModifiers().contains(Modifier.STATIC))
                continue;
            if (!fld.getModifiers().contains(Modifier.PUBLIC))
                continue;
            res.add(fld);
        }
        return res;
    }
}
