package ir.amv.os.openrewrite.recipes

import org.openrewrite.ExecutionContext
import org.openrewrite.Option
import org.openrewrite.Recipe
import org.openrewrite.TreeVisitor
import org.openrewrite.xml.XPathMatcher
import org.openrewrite.xml.tree.Xml

class EnsurePluginExecution(
    @Option(
        displayName = "Annotation Type",
        description = "The fully qualified name of the annotation.",
        example = "org.junit.Test"
    )
    val groupId: String,
    @Option(
        displayName = "Annotation Type",
        description = "The fully qualified name of the annotation.",
        example = "org.junit.Test"
    )
    val artifactId: String,
) : Recipe() {

    val PLUGINS_MATCHER = XPathMatcher("/project/build/plugins")

    override fun getDisplayName(): String = "Apply testable infra patcher"

    override fun getDescription(): String = "$displayName."

    override fun getVisitor(): TreeVisitor<*, ExecutionContext> =
        visitMavenTag { tag, executionContext ->
            tag
                .takeIf { PLUGINS_MATCHER.matches(cursor) }
                ?.let { pluins ->
                    pluins.findPlugin()
                        ?: run {
                            val content = pluins.content!!.toList()
                            pluins.withContent(content)
                                .findPlugin()!!
                        }
                            .let { plugin ->
                                if (plugin.getChild("executions").isPresent) {
                                    doAfterVisit()
                                }
                            }
//                        ?.
                    false
                }
            tag
        }

    context (ImprovedMavenVisitor)
    private fun Xml.Tag.findPlugin() =
        getChildren("plugin")
            .firstOrNull { it.hasMavenSpec(groupId, artifactId) }

}
