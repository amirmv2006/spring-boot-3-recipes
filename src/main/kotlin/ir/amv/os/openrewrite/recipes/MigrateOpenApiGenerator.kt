package ir.amv.os.openrewrite.recipes

import org.openrewrite.ExecutionContext
import org.openrewrite.Recipe
import org.openrewrite.TreeVisitor
import org.openrewrite.maven.MavenVisitor
import org.openrewrite.xml.AddOrUpdateChild.addOrUpdateChild
import org.openrewrite.xml.XmlVisitor
import org.openrewrite.xml.tree.Xml
import org.openrewrite.xml.tree.Xml.Tag

class MigrateOpenApiGenerator : Recipe() {

    override fun getDisplayName(): String = "Migrate OpenAPI maven plugin to spring boot 3"

    override fun getDescription(): String = "Migrate OpenAPI maven plugin to spring boot 3."

    override fun getVisitor(): TreeVisitor<*, ExecutionContext> {
        return object : MavenVisitor<ExecutionContext>() {
            override fun visitTag(tag: Xml.Tag, ctx: ExecutionContext): Xml {
                val result = super.visitTag(tag, ctx) as Xml.Tag
                return result
                    .takeIf { isPluginTag("org.openapitools", "openapi-generator-maven-plugin") }
                    ?.let { plugin ->
                        OpenApiConfigurationVisitor<String>().visitNonNull(plugin, "", cursor.parentOrThrow)
                    }
                    ?: result
            }
        }
    }

    class OpenApiConfigurationVisitor<P : Any> : XmlVisitor<P>() {
        override fun visitTag(tag: Tag, p: P): Xml {
            return super.visitTag(tag, p)
                .let { result ->
                    result
                        .takeIf { it is Tag && it.name == "configOptions" }
                        ?.let { it as Tag }
                        ?.takeIf { !it.getChild("useSpringBoot3").isPresent }
                        ?.let { configOptions ->
                            addOrUpdateChild(
                                configOptions,
                                Tag.build("<useSpringBoot3>true</useSpringBoot3>"),
                                cursor.parentOrThrow
                            )
                        }
                        ?: result
                }
                .let { result ->
                    result
                        .takeIf { it is Tag && it.name == "configOptions" }
                        ?.let { it as Tag }
                        ?.takeIf { "springfox" == it.getChildValue("documentationProvider").orElse(null) }
                        ?.let { configOptions ->
                            addOrUpdateChild(
                                configOptions,
                                Tag.build("<documentationProvider>springdoc</documentationProvider>"),
                                cursor.parentOrThrow
                            )
                        }
                        ?: result
                }
        }
    }
}